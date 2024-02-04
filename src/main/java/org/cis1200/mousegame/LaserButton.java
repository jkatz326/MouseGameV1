package org.cis1200.mousegame;

import javax.swing.*;
import java.awt.*;

public class LaserButton extends JButton {
    private final boolean isRow;
    private final int index;
    private static int size;
    private boolean activated;

    public LaserButton(char letter) {
        super(String.valueOf(letter));
        size = 30;
        isRow = false;
        index = letter - 64;
        activated = false;
        if (!Lab.inBound(index)) {
            throw new IllegalArgumentException();
        }
        setBorder(null);
        setEnabled(false);
    }


    public LaserButton(int num) {
        super(String.valueOf(num));
        if (!Lab.inBound(num)) {
            throw new IllegalArgumentException();
        }
        isRow = true;
        index = num;
        activated = false;
        setBorder(null);
        setEnabled(false);
    }
    public int getIndex() {
        return index;
    }
    public boolean isActivated() {
        return activated;
    }

    public void updateIsActivated() {
        activated = true;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(size, size);
    }
}
