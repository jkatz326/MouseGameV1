package org.cis1200.mousegame;

import javax.swing.*;
import java.awt.*;

public class TextDisplay extends JTextArea {
    private final String p1Name;
    private final String p2Name;
    public TextDisplay(String p1Name, String p2Name) {
        super();
        if (p1Name == null || p2Name == null) {
            throw new IllegalArgumentException();
        }
        this.p1Name = p1Name;
        this.p2Name = p2Name;
        setFont(new Font("Arial", Font.PLAIN, 14));
        setEditable(false);
        setBackground(UIManager.getColor("Panel.background"));
        setText("Round 1" + "\n" + p1Name + "'s Turn");
    }

    private String nameFromBool(boolean isPlayer1) {
        if (isPlayer1) {
            return p1Name;
        } else {
            return p2Name;
        }
    }

    public static String stringOfChar(char dir) {
        if (!Lab.validDir(dir)) {
            throw new IllegalArgumentException();
        }
        switch (dir) {
            case 'N':
                return "North";
            case 'S':
                return "South";
            case 'E':
                return "East";
            case 'W':
                return "West";
            default:
                throw new IllegalArgumentException();
        }
    }

    private static String spookText(int dist) {
        if (dist < 1 || dist > 6) {
            throw new IllegalArgumentException();
        } else if (dist == 1 || dist == 2) {
            return "a little spooked";
        } else if (dist == 3 || dist == 4) {
            return "quite spooked";
        } else {
            return "extremely spooked";
        }
    }

    public void updateDisplayStartOfTurn(boolean isP1, int roundNum) {
        String pName = nameFromBool(isP1);
        setText("Round " + roundNum + "\n" + pName + "'s Turn");
    }
    public void updateDisplayTrapNotifier(TrapNotifier tn) {
        String pName = nameFromBool(tn.isPlayer1());
        setText("Round " + tn.round() + "\n"  + pName + "'s Turn"
            + "\n" + "Trap added at " + LabDisplay.alphabet[tn.xPos() - 1] + tn.yPos());
    }

    public void updateDisplayLaserNotifier(LaserNotifier ln) {
        String pName = nameFromBool(ln.isPlayer1());
        String mouseOwner = nameFromBool(!ln.isPlayer1());
        String pos;
        if (ln.isNS()) {
            pos = "Column " + LabDisplay.alphabet[ln.index() - 1];
        } else {
            pos = "Row " + ln.index();
        }
        setText("Round " + ln.round() + "\n"  + pName + "'s Turn"
                + "\n" + "Laser added at " + pos);
        if (ln.isMouseOn()) {
            append("\n" + mouseOwner + "'s mouse has been detected");
        }
    }
    public void updateDisplayMoveNotifier(MoveNotifier mn) {
        String pName = nameFromBool(mn.isPlayer1());
        String mouseOwner;
        if (mn.spookDist() == 0) {
            mouseOwner = pName;
        } else {
            mouseOwner = nameFromBool(!mn.isPlayer1());
        }
        setText("Round " + mn.round() + "\n"  + pName + "'s Turn");
        if (mn.isSpecial()) {
            append("\n" + mouseOwner + "'s mouse used Special Move");
        } else if (mn.spookDist() == 0) {
            append("\n" + mouseOwner + "'s mouse moved " + stringOfChar(mn.dir()));
        } else {
            append("\n" + mouseOwner + "'s mouse moved " + stringOfChar(mn.dir()));
            append("\n" + mouseOwner + "'s mouse was " + spookText(mn.spookDist()));
        }
        append("\n" + mouseOwner + "'s mouse triggered " + mn.lasersTriggered() + " lasers");
        if (mn.onNS()) {
            append("\n" + mouseOwner + "'s mouse is on Laser " +
                    LabDisplay.alphabet[mn.endPoint().x - 1]);
        }
        if (mn.onEW()) {
            append("\n" + mouseOwner + "'s mouse is on Laser " + mn.endPoint().y);
        }
    }
    public void gameOverDialog(boolean p1Winner) {
        setFont(new Font("Arial", Font.BOLD, 14));
        if (p1Winner) {
            setText("Game Over" + "\n" + p1Name + " Wins!");
        } else {
            setText("Game Over" + "\n" + p2Name + " Wins!");
        }

    }

}
