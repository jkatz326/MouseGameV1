package org.cis1200.mousegame;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

public class ActionToolbar extends JPanel {
    private final LinkedList<JButton> actions;
    private final JButton standardMove;
    private final JButton specialMove;
    private final JButton newLaser;
    private final JButton newTrap;
    private final JButton spook;
    private final JButton north;
    private final JButton south;
    private final JButton east;
    private final JButton west;
    private final JButton nextTurn;
    private final CardLayout cardLayout;
    private JButton lastActionP1;
    private JButton lastActionP2;

    public ActionToolbar() {
        super();
        lastActionP1 = null;
        lastActionP2 = null;
        //initialize action buttons
        actions = new LinkedList<JButton>();
        JPanel actionPanel = new JPanel();
        actionPanel.setLayout(new BoxLayout(actionPanel, BoxLayout.X_AXIS));
        standardMove = new JButton("Standard Move");
        specialMove = new JButton("Special Move");
        newLaser = new JButton("Add Laser");
        newTrap = new JButton("Add Trap");
        spook = new JButton("Spook");
        actions.add(standardMove);
        actions.add(specialMove);
        actions.add(newLaser);
        actions.add(newTrap);
        actions.add(spook);
        for (JButton b : actions) {
            b.setFont(new Font("Arial", Font.PLAIN, 20));
            actionPanel.add(b);
        }
        //initialize direction buttons
        LinkedList<JButton> directions = new LinkedList<JButton>();
        JPanel directionPanel = new JPanel();
        directionPanel.setLayout(new BoxLayout(directionPanel, BoxLayout.X_AXIS));
        north = new JButton("North");
        south = new JButton("South");
        east = new JButton("East");
        west = new JButton("West");
        directions.add(north);
        directions.add(south);
        directions.add(east);
        directions.add(west);
        for (JButton b : directions) {
            b.setFont(new Font("Arial", Font.PLAIN, 20));
            directionPanel.add(b);
        }
        //initialize Next Turn Button
        JPanel nextTurnPanel = new JPanel();
        nextTurn = new JButton("Next Turn");
        nextTurnPanel.add(nextTurn);
        //blank panel
        JPanel blankPanel = new JPanel();
        //put it all together in Card Panel
        cardLayout = new CardLayout();
        setLayout(cardLayout);
        add(actionPanel);
        add(directionPanel);
        add(nextTurnPanel);
        add(blankPanel);
        cardLayout.addLayoutComponent(actionPanel, "Actions");
        cardLayout.addLayoutComponent(directionPanel, "Directions");
        cardLayout.addLayoutComponent(nextTurnPanel, "Next Turn");
        cardLayout.addLayoutComponent(blankPanel, "Blank");
        cardLayout.show(this, "Actions");
    }
    public void enableP1Turn() {
        cardLayout.show(this , "Actions");
        for (JButton b : actions) {
            b.setEnabled(!(b == lastActionP1));
        }
    }
    public void enableP2Turn() {
        cardLayout.show(this , "Actions");
        for (JButton b : actions) {
            b.setEnabled(!(b == lastActionP2));
        }
    }
    public void setLastActionP1(JButton lastActionP1) {
        this.lastActionP1 = lastActionP1;
    }

    public void setLastActionP2(JButton lastActionP2) {
        this.lastActionP2 = lastActionP2;
    }

    public void enableDirections() {
        cardLayout.show(this, "Directions");
    }
    public void enableNextTurn() {
        cardLayout.show(this, "Next Turn");
    }
    public void turnOff() {
        cardLayout.show(this, "Blank");
    }
    public JButton getStandardMove() {
        return standardMove;
    }
    public JButton getSpecialMove() {
        return specialMove;
    }
    public JButton getNewLaser() {
        return newLaser;
    }
    public JButton getNewTrap() {
        return newTrap;
    }
    public JButton getSpook() {
        return spook;
    }
    public JButton getNorth() {
        return north;
    }
    public JButton getSouth() { return south; }
    public JButton getEast() {
        return east;
    }
    public JButton getWest() {
        return west;
    }
    public JButton getNextTurn() {
        return nextTurn;
    }
}
