package org.cis1200.mousegame;

import javax.swing.*;
import java.awt.*;

public class GridButton extends JButton {

    private GridState gs;
    private static int size;
    private final boolean inP1Lab;
    public GridButton(boolean inP1Lab) {
        super();
        gs = GridState.None;
        size = 25;
        this.inP1Lab = inP1Lab;
        setEnabled(false);
    }

    public void changeState(GridState newGS) {
        this.gs = newGS;
    }

    public GridState getState() {
        return gs;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        switch (gs) {
            case None:
                UIManager.getColor("Panel.background");
                break;
            case Trap:
                setBackground(UIManager.getColor("Panel.background"));
                g.drawLine(0, 0, size, size);
                g.drawLine(0, size, size, 0);
                break;
            case Laser:
                setBackground(Color.red);
                break;
            case LaserTrap:
                setBackground(Color.red);
                g.drawLine(0, 0, size, size);
                g.drawLine(0, size, size, 0);
                break;
            case Mouse:
                setBackground(Color.green);
                break;
            case MouseCaught:
                setBackground(Color.green);
                g.drawLine(0, 0, size, size);
                g.drawLine(0, size, size, 0);
                break;
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(size, size);
    }
}
