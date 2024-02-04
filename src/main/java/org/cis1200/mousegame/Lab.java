package org.cis1200.mousegame;

import java.awt.*;
import java.util.Random;

public class Lab {
    private final boolean playerOneLab;
    private int round;
    private int [][] traps;
    private int [] nsLasers;
    private int [] ewLasers;
    private final Point mouseLoc;
    private static final int dim = 12;
    private final Random rng;
    private final SpecialMoveDir smd;

    private final int standardMoveDist;

    public Lab(boolean playerOneLab, int standardMoveDist,
               SpecialMoveDir specialMoveDir, int xStart, int yStart) {
        this.standardMoveDist = standardMoveDist;
        this.playerOneLab = playerOneLab;
        round = 1;
        nsLasers = new int[dim];
        ewLasers = new int[dim];
        traps = new int[dim][dim];
        rng = new Random();
        if (inBound(xStart) && inBound(yStart)) {
            mouseLoc = new Point(xStart, yStart);
        } else {
            throw new IllegalArgumentException();
        }
        if (inBound(specialMoveDir.dist1()) && inBound(specialMoveDir.dist2())
                && validDir(specialMoveDir.dir1()) && validDir(specialMoveDir.dir2())) {
            smd = specialMoveDir;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void reset(int xStart, int yStart) {
        if (!inBound(xStart) || !inBound(yStart)) {
            throw new IllegalArgumentException();
        } else {
            mouseLoc.setLocation(xStart, yStart);
            nsLasers = new int[dim];
            ewLasers = new int[dim];
            traps = new int[dim][dim];
        }
    }

    public Point getMouseLoc() {
        return mouseLoc;
    }
    //static methods for calculations and checking values
    public static boolean inBound(int i) {
        return i > 0 && i < dim + 1;
    }
    public static int getDim() {
        return dim;
    }
    public static boolean validDir(char d) {
        return d == 'N' || d == 'S' || d == 'E' || d == 'W';
    }

    //public method for each possible action
    public LaserNotifier addLaser(int e, boolean isNS) {
        if (!inBound(e)) {
            throw new IllegalArgumentException();
        } else {
            round += 1;
            if (isNS) {
                nsLasers[e - 1] = 1;
                return new LaserNotifier(playerOneLab, round - 1, true, e,  mouseLoc.x == e);
            } else {
                ewLasers[e - 1] = 1;
                return new LaserNotifier(playerOneLab, round - 1, false, e,  mouseLoc.y == e);
            }
        }
    }

    public TrapNotifier addTrap(int x, int y) {
        if (!inBound(x) || !inBound(y)) {
            throw new IllegalArgumentException();
        } else {
            round += 1;
            traps[y - 1][x - 1] = 1;
            Point trapLoc = new Point(x, y);
            return new TrapNotifier(playerOneLab, round - 1, x, y, trapLoc.equals(mouseLoc));
        }
    }

    public MoveNotifier standardMove(char dir, int dist) {
        round += 1;
        return moveMouse(dir, dist, false);
    }

    public MoveNotifier standardMove(char dir) {
        round += 1;
        return moveMouse(dir, standardMoveDist, false);
    }
    public MoveNotifier specialMove() {
        round += 1;
        MoveNotifier mn1 = moveMouse(smd.dir1(), smd.dist1(), false);
        MoveNotifier mn2 = moveMouse(smd.dir2(), smd.dist2(), false);
        int totalLasers = mn1.lasersTriggered() + mn2.lasersTriggered();
        boolean gameOverBoth = mn1.gameOver() || mn2.gameOver();
        Point trapLocBoth;
        if (mn1.gameOver()) {
            trapLocBoth = mn1.trapLoc();
        } else if (mn2.gameOver()) {
            trapLocBoth = mn2.trapLoc();
        } else {
            trapLocBoth = null;
        }
        return new MoveNotifier(!playerOneLab, round - 1, smd.dir2(), true, 0, totalLasers,
                new Point(mouseLoc), mn2.onNS(), mn2.onEW(), gameOverBoth, trapLocBoth);
    }

    public MoveNotifier spook(char dir) {
        int dist = rng.nextInt(1, 7);
        return moveMouse(dir, dist, true);
    }

    public MoveNotifier replaySpook(char dir, int dist) {
        return moveMouse(dir, dist, true);
    }

    private MoveNotifier moveMouse(char dir, int dist, boolean isSpook) {
        if (!validDir(dir)) {
            throw new IllegalArgumentException();
        }
        int lasersTriggered = 0;
        boolean gameOver = false;
        int spookDist;
        boolean playerBool;
        Point trapLoc = null;
        if (isSpook) {
            spookDist = dist;
            playerBool = playerOneLab;
        } else {
            spookDist = 0;
            playerBool = !playerOneLab;
        }
        int x0 = mouseLoc.x;
        int y0 = mouseLoc.y;
        if (dir == 'N') {
            if (y0 - dist < 1) {
                mouseLoc.move(x0, 1);
            } else {
                mouseLoc.translate(0, -dist);
            }
            for (int i = y0 - 1; i > mouseLoc.y - 1; i--) {
                if (ewLasers[i - 1] == 1) {
                    lasersTriggered += 1;
                }
                if (traps[i - 1][x0 - 1] == 1) {
                    gameOver = true;
                    trapLoc = new Point(x0, i);
                    break;
                }
            }
        }
        if (dir == 'S') {
            if (y0 + dist > dim) {
                mouseLoc.move(x0, dim);
            } else {
                mouseLoc.translate(0, dist);
            }
            for (int i = y0 + 1; i < mouseLoc.y + 1; i++) {
                if (ewLasers[i - 1] == 1) {
                    lasersTriggered += 1;
                }
                if (traps[i - 1][x0 - 1] == 1) {
                    gameOver = true;
                    trapLoc = new Point(x0, i);
                    break;
                }
            }
        }
        if (dir == 'W') {
            if (x0 - dist < 1) {
                mouseLoc.move(1, y0);
            } else {
                mouseLoc.translate(-dist, 0);
            }
            for (int i = x0 - 1; i > mouseLoc.x - 1; i--) {
                if (nsLasers[i - 1] == 1) {
                    lasersTriggered += 1;
                }
                if (traps[y0 - 1][i - 1] == 1) {
                    gameOver = true;
                    trapLoc = new Point(i, y0);
                    break;
                }
            }
        }
        if (dir == 'E') {
            if (x0 + dist > dim) {
                mouseLoc.move(dim, y0);
            } else {
                mouseLoc.translate(dist, 0);
            }
            for (int i = x0 + 1; i < mouseLoc.x + 1; i++) {
                if (nsLasers[i - 1] == 1) {
                    lasersTriggered += 1;
                }
                if (traps[y0 - 1][i - 1] == 1) {
                    gameOver = true;
                    trapLoc = new Point(i, y0);
                    break;
                }
            }
        }
        return new MoveNotifier(playerBool, round - 1, dir, false, spookDist, lasersTriggered,
                new Point(mouseLoc), nsLasers[mouseLoc.x - 1] == 1,ewLasers[mouseLoc.y - 1] == 1,
                gameOver, trapLoc);
    }
}
