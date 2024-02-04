package org.cis1200.mousegame;

import javax.swing.*;
import java.awt.*;

public class LabDisplay extends JPanel {
    private final GridButton[][] grid;
    private final LaserButton[] columns;
    private final LaserButton[] rows;
    private final int dim;
    private final boolean isP1Lab;
    public static final char[] alphabet =
            new char[] {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
            'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S',
            'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    public LabDisplay(boolean isP1Lab) {
        super();
        this.isP1Lab = isP1Lab;
        dim = Lab.getDim();
        grid = new GridButton[dim][dim];
        columns = new LaserButton[dim];
        rows = new LaserButton[dim];
        setLayout(new GridLayout(dim + 1, dim + 1));
        JLabel spacer = new JLabel();
        spacer.setSize(25, 25);
        add(spacer);
        for (int i = 0; i < dim; i++) {
            char let = alphabet[i];
            LaserButton ref = new LaserButton(let);
            columns[i] = ref;
            add(ref);
        }
        for (int j = 1; j < dim + 1; j++) {
            for (int k = 0; k < dim + 1; k++) {
                if (k == 0) {
                    LaserButton ref = new LaserButton(j);
                    rows[j - 1] = ref;
                    add(ref);
                } else {
                    GridButton ref = new GridButton(isP1Lab);
                    grid[j - 1][k - 1] = ref;
                    add(ref);
                }
            }
        }
    }

    public GridButton[][] getGrid() {
        return grid;
    }
    public LaserButton[] getColumns() {
        return columns;
    }
    public LaserButton[] getRows() {
        return rows;
    }
    public void addLaser(boolean isRow, int index) {
        if (!Lab.inBound(index)) {
            throw new IllegalArgumentException();
        } else {
            if (isRow) {
                for (int i = 0; i < dim; i++) {
                    GridButton ref = grid[index - 1][i];
                    if (ref.getState() == GridState.Trap) {
                        ref.changeState(GridState.LaserTrap);
                    } else {
                        ref.changeState(GridState.Laser);
                    }
                }
            } else {
                for (int i = 0; i < dim; i++) {
                    GridButton ref = grid[i][index - 1];
                    if (ref.getState() == GridState.Trap) {
                        ref.changeState(GridState.LaserTrap);
                    } else {
                        ref.changeState(GridState.Laser);
                    }
                }
            }
        }
    }
    public void addTrap(int row, int col) {
        if (!(Lab.inBound(row) && Lab.inBound(col))) {
            throw new IllegalArgumentException();
        } else {
            GridButton ref = grid[row - 1][col - 1];
            if (ref.getState() == GridState.Laser) {
                ref.changeState(GridState.LaserTrap);
            } else {
                ref.changeState(GridState.Trap);
            }
        }
    }

    public void enableTrapButtons() {
        for (GridButton[] a: grid) {
            for (GridButton b: a) {
                b.setEnabled(!(b.getState() == GridState.Trap));
            }
        }
    }

    public void enableLaserButtons() {
        for (LaserButton b: rows) {
            b.setEnabled(!b.isActivated());
        }
        for (LaserButton b: columns) {
            b.setEnabled(!b.isActivated());
        }
    }
    public void disableButtons() {
        for (GridButton[] a: grid) {
            for (GridButton b: a) {
                b.setEnabled(false);
            }
        }
        for (LaserButton b: rows) {
            b.setEnabled(false);
        }
        for (LaserButton b: columns) {
            b.setEnabled(false);
        }
    }




}
