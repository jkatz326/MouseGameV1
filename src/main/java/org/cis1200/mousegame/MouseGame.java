package org.cis1200.mousegame;

import javax.swing.*;
import java.awt.Point;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

public class MouseGame {
    private int roundNum;
    private boolean isP1Turn;
    private boolean isSpook;
    private int spookBypass;
    private final Lab p1Lab;
    private final Lab p2Lab;
    private final MouseView mv;
    private final LinkedList<String> actionQueue;
    private final String startingValues;


    public MouseGame(int p1MouseX, int p1MouseY, int dist1, SpecialMoveDir smd1, String p1Name,
                     int p2MouseX, int p2MouseY, int dist2, SpecialMoveDir smd2, String p2Name)
            throws IllegalArgumentException {
        startingValues = "SV" + "\n" + p1MouseX + "\n" + p1MouseY + "\n" + dist1 + "\n" +
                smd1.dir1() + "\n" + smd1.dist1() + "\n" + smd1.dir2() + "\n" +
                smd1.dist2() + "\n" + p1Name + "\n" +
                p2MouseX + "\n" + p2MouseY + "\n" + dist2 + "\n" +
                smd2.dir1() + "\n" + smd2.dist1() + "\n" + smd2.dir2() + "\n" +
                smd2.dist2() + "\n" + p2Name + "\n";
        roundNum = 1;
        spookBypass = 0;
        isP1Turn = true;
        p1Lab = new Lab(true, dist2, smd2, p2MouseX, p2MouseY);
        p2Lab = new Lab(false, dist1, smd1, p1MouseX, p1MouseY);
        mv = new MouseView(p1Name, p2Name);
        actionQueue = new LinkedList<String>();
        JButton nextTurnButton = mv.getActionToolbar().getNextTurn();
        nextTurnButton.addActionListener(e -> {
            if (isP1Turn) {
                mv.getActionToolbar().enableP2Turn();
            } else {
                mv.getActionToolbar().enableP1Turn();
                roundNum += 1;
            }
            isP1Turn = !isP1Turn;
            mv.getTextDisplay().updateDisplayStartOfTurn(isP1Turn, roundNum);
        });
        // Adding Traps
        JButton newTrapButton = mv.getActionToolbar().getNewTrap();
        newTrapButton.addActionListener(e -> {
            mv.getActionToolbar().turnOff();
            if (isP1Turn) {
                mv.getPlayer1Lab().enableTrapButtons();
                mv.getActionToolbar().setLastActionP1(mv.getActionToolbar().getNewTrap());
            } else {
                mv.getPlayer2Lab().enableTrapButtons();
                mv.getActionToolbar().setLastActionP2(mv.getActionToolbar().getNewTrap());
            }
        });
        GridButton[][] player1LabGrid = mv.getPlayer1Lab().getGrid();
        GridButton[][] player2LabGrid = mv.getPlayer2Lab().getGrid();
        for (int i = 0; i < Lab.getDim(); i++) {
            for (int j = 0; j < Lab.getDim(); j++) {
                GridButton b = player1LabGrid[j][i];
                int fI = i;
                int fJ = j;
                b.addActionListener(e -> {
                    TrapNotifier tn = p1Lab.addTrap(fI + 1, fJ + 1);
                    b.changeState(GridState.Trap);
                    mv.getPlayer1Lab().addTrap(fJ + 1, fI + 1);
                    mv.getPlayer1Lab().disableButtons();
                    if (tn.gameOver()) {
                        gameOver(true, new Point(fI + 1, fJ + 1));
                        return;
                    }
                    mv.getActionToolbar().enableNextTurn();
                    mv.getTextDisplay().updateDisplayTrapNotifier(tn);
                    String stringRep = "trap" + "\n" + 1 + "\n" + fI + "\n" + fJ + "\n";
                    actionQueue.add(stringRep);
                });
            }
        }
        for (int i = 0; i < Lab.getDim(); i++) {
            for (int j = 0; j < Lab.getDim(); j++) {
                GridButton b = player2LabGrid[j][i];
                int fI = i;
                int fJ = j;
                b.addActionListener(e -> {
                    TrapNotifier tn = p2Lab.addTrap(fI + 1, fJ + 1);
                    b.changeState(GridState.Trap);
                    mv.getPlayer2Lab().addTrap(fJ + 1, fI + 1);
                    mv.getPlayer2Lab().disableButtons();
                    if (tn.gameOver()) {
                        gameOver(false, new Point(fI + 1, fJ + 1));
                        return;
                    }
                    mv.getActionToolbar().enableNextTurn();
                    mv.getTextDisplay().updateDisplayTrapNotifier(tn);
                    String stringRep = "trap" + "\n" + 2 + "\n" + fI + "\n" + fJ + "\n";
                    actionQueue.add(stringRep);
                });
            }
        }
        //Adding Lasers
        JButton newLaserButton = mv.getActionToolbar().getNewLaser();
        newLaserButton.addActionListener(e -> {
            mv.getActionToolbar().turnOff();
            if (isP1Turn) {
                mv.getPlayer1Lab().enableLaserButtons();
                mv.getActionToolbar().setLastActionP1(mv.getActionToolbar().getNewLaser());
            } else {
                mv.getPlayer2Lab().enableLaserButtons();
                mv.getActionToolbar().setLastActionP2(mv.getActionToolbar().getNewLaser());
            }
        });
        LaserButton[] p1NSLaserButtons = mv.getPlayer1Lab().getColumns();
        LaserButton[] p1EWLaserButtons = mv.getPlayer1Lab().getRows();
        LaserButton[] p2NSLaserButtons = mv.getPlayer2Lab().getColumns();
        LaserButton[] p2EWLaserButtons = mv.getPlayer2Lab().getRows();
        for (LaserButton b : p1NSLaserButtons) {
            int index = b.getIndex();
            b.addActionListener(e -> {
                LaserNotifier ln = p1Lab.addLaser(index, true);
                mv.getPlayer1Lab().addLaser(false, index);
                mv.getPlayer1Lab().disableButtons();
                b.updateIsActivated();
                mv.getActionToolbar().enableNextTurn();
                mv.getTextDisplay().updateDisplayLaserNotifier(ln);
                String stringRep = "laser" + "\n" + 1 + "\n" + true + "\n" + index + "\n";
                actionQueue.add(stringRep);
            });
        }
        for (LaserButton b : p1EWLaserButtons) {
            int index = b.getIndex();
            b.addActionListener(e -> {
                LaserNotifier ln = p1Lab.addLaser(index, false);
                mv.getPlayer1Lab().addLaser(true, index);
                mv.getPlayer1Lab().disableButtons();
                b.updateIsActivated();
                mv.getActionToolbar().enableNextTurn();
                mv.getTextDisplay().updateDisplayLaserNotifier(ln);
                String stringRep = "laser" + "\n" + 1 + "\n" + false + "\n" + index + "\n";
                actionQueue.add(stringRep);
            });
        }
        for (LaserButton b : p2NSLaserButtons) {
            int index = b.getIndex();
            b.addActionListener(e -> {
                LaserNotifier ln = p2Lab.addLaser(index, true);
                mv.getPlayer2Lab().addLaser(false, index);
                mv.getPlayer2Lab().disableButtons();
                b.updateIsActivated();
                mv.getActionToolbar().enableNextTurn();
                mv.getTextDisplay().updateDisplayLaserNotifier(ln);
                String stringRep = "laser" + "\n" + 2 + "\n" + true + "\n" + index + "\n";
                actionQueue.add(stringRep);
            });
        }
        for (LaserButton b : p2EWLaserButtons) {
            int index = b.getIndex();
            b.addActionListener(e -> {
                LaserNotifier ln = p2Lab.addLaser(index, false);
                mv.getPlayer2Lab().addLaser(true, index);
                mv.getPlayer2Lab().disableButtons();
                b.updateIsActivated();
                mv.getActionToolbar().enableNextTurn();
                mv.getTextDisplay().updateDisplayLaserNotifier(ln);
                String stringRep = "laser" + "\n" + 2 + "\n" + false + "\n" + index + "\n";
                actionQueue.add(stringRep);
            });
        }
        //Moving
        JButton specialMoveButton = mv.getActionToolbar().getSpecialMove();
        specialMoveButton.addActionListener(e -> {
            MoveNotifier mn;
            if (isP1Turn) {
                mn = p2Lab.specialMove();
                mv.getActionToolbar().setLastActionP1(mv.getActionToolbar().getSpecialMove());
                if (mn.gameOver()) {
                    gameOver(false, mn.trapLoc());
                    return;
                }
            } else {
                mn = p1Lab.specialMove();
                mv.getActionToolbar().setLastActionP2(mv.getActionToolbar().getSpecialMove());
                if (mn.gameOver()) {
                    gameOver(true, mn.trapLoc());
                    return;
                }
            }
            mv.getActionToolbar().enableNextTurn();
            mv.getTextDisplay().updateDisplayMoveNotifier(mn);
            String stringRep = "special"  + "\n" + isP1Turn  + "\n";
            actionQueue.add(stringRep);
        });
        JButton standardMoveButton = mv.getActionToolbar().getStandardMove();
        standardMoveButton.addActionListener(e -> {
            isSpook = false;
            mv.getActionToolbar().enableDirections();
            if (isP1Turn) {
                mv.getActionToolbar().setLastActionP1(mv.getActionToolbar().getStandardMove());
            } else {
                mv.getActionToolbar().setLastActionP2(mv.getActionToolbar().getStandardMove());
            }
        });
        JButton spookButton = mv.getActionToolbar().getSpook();
        spookButton.addActionListener(e -> {
            isSpook = true;
            mv.getActionToolbar().enableDirections();
            if (isP1Turn) {
                mv.getActionToolbar().setLastActionP1(mv.getActionToolbar().getSpook());
            } else {
                mv.getActionToolbar().setLastActionP2(mv.getActionToolbar().getSpook());
            }
        });
        JButton northButton = mv.getActionToolbar().getNorth();
        northButton.addActionListener(e -> {
            MoveNotifier mn;
            if (!(spookBypass == 0)) {
                if (isP1Turn) {
                    mn = p1Lab.replaySpook('N', spookBypass);
                    if (mn.gameOver()) {
                        gameOver(true, mn.trapLoc());
                        return;
                    }
                } else {
                    mn = p2Lab.replaySpook('N', spookBypass);
                    if (mn.gameOver()) {
                        gameOver(false, mn.trapLoc());
                        return;
                    }
                }
            }
            if (isSpook) {
                if (isP1Turn) {
                    mn = p1Lab.spook('N');
                    if (mn.gameOver()) {
                        gameOver(true, mn.trapLoc());
                        return;
                    }
                } else {
                    mn = p2Lab.spook('N');
                    if (mn.gameOver()) {
                        gameOver(false, mn.trapLoc());
                        return;
                    }
                }
            } else {
                if (isP1Turn) {
                    mn = p2Lab.standardMove('N');
                    if (mn.gameOver()) {
                        gameOver(false, mn.trapLoc());
                        return;
                    }
                } else {
                    mn = p1Lab.standardMove('N');
                    if (mn.gameOver()) {
                        gameOver(true, mn.trapLoc());
                        return;
                    }
                }
            }
            mv.getActionToolbar().enableNextTurn();
            mv.getTextDisplay().updateDisplayMoveNotifier(mn);
            String stringRep = "move" + "\n" + "N" + "\n" + isSpook + "\n"
                    + isP1Turn + "\n"  + mn.spookDist()  + "\n";
            actionQueue.add(stringRep);
        });
        JButton southButton = mv.getActionToolbar().getSouth();
        southButton.addActionListener(e -> {
            MoveNotifier mn;
            if (!(spookBypass == 0)) {
                if (isP1Turn) {
                    mn = p1Lab.replaySpook('S', spookBypass);
                    if (mn.gameOver()) {
                        gameOver(true, mn.trapLoc());
                        return;
                    }
                } else {
                    mn = p2Lab.replaySpook('S', spookBypass);
                    if (mn.gameOver()) {
                        gameOver(false, mn.trapLoc());
                        return;
                    }
                }
            }
            if (isSpook) {
                if (isP1Turn) {
                    mn = p1Lab.spook('S');
                    if (mn.gameOver()) {
                        gameOver(true, mn.trapLoc());
                        return;
                    }
                } else {
                    mn = p2Lab.spook('S');
                    if (mn.gameOver()) {
                        gameOver(false, mn.trapLoc());
                        return;
                    }
                }
            } else {
                if (isP1Turn) {
                    mn = p2Lab.standardMove('S');
                    if (mn.gameOver()) {
                        gameOver(false, mn.trapLoc());
                        return;
                    }
                } else {
                    mn = p1Lab.standardMove('S');
                    if (mn.gameOver()) {
                        gameOver(true, mn.trapLoc());
                        return;
                    }
                }
            }
            mv.getActionToolbar().enableNextTurn();
            mv.getTextDisplay().updateDisplayMoveNotifier(mn);
            String stringRep = "move" + "\n" + "S" + "\n" + isSpook + "\n"
                    + isP1Turn + "\n"  + mn.spookDist()  + "\n";
            actionQueue.add(stringRep);
        });
        JButton eastButton = mv.getActionToolbar().getEast();
        eastButton.addActionListener(e -> {
            MoveNotifier mn;
            if (!(spookBypass == 0)) {
                if (isP1Turn) {
                    mn = p1Lab.replaySpook('E', spookBypass);
                    if (mn.gameOver()) {
                        gameOver(true, mn.trapLoc());
                        return;
                    }
                } else {
                    mn = p2Lab.replaySpook('E', spookBypass);
                    if (mn.gameOver()) {
                        gameOver(false, mn.trapLoc());
                        return;
                    }
                }
            }
            if (isSpook) {
                if (isP1Turn) {
                    mn = p1Lab.spook('E');
                    if (mn.gameOver()) {
                        gameOver(true, mn.trapLoc());
                        return;
                    }
                } else {
                    mn = p2Lab.spook('E');
                    if (mn.gameOver()) {
                        gameOver(false, mn.trapLoc());
                        return;
                    }
                }
            } else {
                if (isP1Turn) {
                    mn = p2Lab.standardMove('E');
                    if (mn.gameOver()) {
                        gameOver(false, mn.trapLoc());
                        return;
                    }
                } else {
                    mn = p1Lab.standardMove('E');
                    if (mn.gameOver()) {
                        gameOver(true, mn.trapLoc());
                        return;
                    }
                }
            }
            mv.getActionToolbar().enableNextTurn();
            mv.getTextDisplay().updateDisplayMoveNotifier(mn);
            String stringRep = "move" + "\n" + "E" + "\n" + isSpook + "\n"
                    + isP1Turn + "\n"  + mn.spookDist()  + "\n";
            actionQueue.add(stringRep);
        });
        JButton westButton = mv.getActionToolbar().getWest();
        westButton.addActionListener(e -> {
            MoveNotifier mn;
            if (!(spookBypass == 0)) {
                if (isP1Turn) {
                    mn = p1Lab.replaySpook('W', spookBypass);
                    if (mn.gameOver()) {
                        gameOver(true, mn.trapLoc());
                        return;
                    }
                } else {
                    mn = p2Lab.replaySpook('W', spookBypass);
                    if (mn.gameOver()) {
                        gameOver(false, mn.trapLoc());
                        return;
                    }
                }
            }
            if (isSpook) {
                if (isP1Turn) {
                    mn = p1Lab.spook('W');
                    if (mn.gameOver()) {
                        gameOver(true, mn.trapLoc());
                        return;
                    }
                } else {
                    mn = p2Lab.spook('W');
                    if (mn.gameOver()) {
                        gameOver(false, mn.trapLoc());
                        return;
                    }
                }
            } else {
                if (isP1Turn) {
                    mn = p2Lab.standardMove('W');
                    if (mn.gameOver()) {
                        gameOver(false, mn.trapLoc());
                        return;
                    }
                } else {
                    mn = p1Lab.standardMove('W');
                    if (mn.gameOver()) {
                        gameOver(true, mn.trapLoc());
                        return;
                    }
                }
            }
            mv.getActionToolbar().enableNextTurn();
            mv.getTextDisplay().updateDisplayMoveNotifier(mn);
            String stringRep = "move" + "\n" + "W" + "\n" + isSpook + "\n"
                    + isP1Turn + "\n"  + mn.spookDist()  + "\n";
            actionQueue.add(stringRep);
        });
    }
    public MouseView getMouseView() {
        return mv;
    }
    public void gameOver(boolean p1Winner, Point locCaught) {
        mv.getActionToolbar().turnOff();
        mv.getTextDisplay().gameOverDialog(p1Winner);
        if (p1Winner) {
            Point finalLoc2 = p2Lab.getMouseLoc();
            GridButton[][] p1Grid = mv.getPlayer1Lab().getGrid();
            GridButton[][] p2Grid = mv.getPlayer2Lab().getGrid();
            p1Grid[locCaught.y - 1][locCaught.x - 1].changeState(GridState.MouseCaught);
            p2Grid[finalLoc2.y - 1][finalLoc2.x - 1].changeState(GridState.Mouse);
        } else {
            Point finalLoc1 = p1Lab.getMouseLoc();
            GridButton[][] p1Grid = mv.getPlayer1Lab().getGrid();
            GridButton[][] p2Grid = mv.getPlayer2Lab().getGrid();
            p1Grid[finalLoc1.y - 1][finalLoc1.x - 1].changeState(GridState.Mouse);
            p2Grid[locCaught.y - 1][locCaught.x - 1].changeState(GridState.MouseCaught);
        }
    }

    public void saveState() {
        try {
            File file = new File("SavedState.pdf");
            FileWriter writer = new FileWriter(file, false);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);
            bufferedWriter.write(startingValues);
            for (String s : actionQueue) {
                bufferedWriter.write(s);
            }
            bufferedWriter.write("END" + "\n" + roundNum  + "\n" + isP1Turn);
            bufferedWriter.close();
        } catch (IOException e) {

        }
    }

    public void replayLaser(boolean isNS, int index) {
        System.out.println("For Later");
        return;
    }

    public void replayTrap(int jIndex, int iIndex, boolean isP1) {
        System.out.println("For Later 2");
        return;
    }

    public void replaySpecial(boolean isP1) {
        System.out.println("For Later 3");
        return;
    }

    public void replayMove(char dir, boolean isSpook, boolean isP1Turn, int spookDist) {
        System.out.println("For Later 4");
        return;
    }


}
