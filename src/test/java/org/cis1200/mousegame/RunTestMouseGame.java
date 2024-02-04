package org.cis1200.mousegame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.NoSuchElementException;

public class RunTestMouseGame implements Runnable {
    public void run() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1920, 1080);
        Container contentPanel = frame.getContentPane();
        contentPanel.setLayout(new FlowLayout());
        PlayerInputPopUp playerIPU;
        SpecialMoveDir smd1 = new SpecialMoveDir('N', 6, 'E', 3);
        SpecialMoveDir smd2 = new SpecialMoveDir('S', 3, 'W', 6);
        MouseGame mouseGame = new MouseGame(6, 6, 4, smd1, "Joseph",
                4,4, 8, smd2, "Timothy");
        contentPanel.add(mouseGame.getMouseView());
        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                try {
                    mouseGame.saveState();
                    System.out.println("successfully saved");
                } catch (NullPointerException f) {
                    System.out.println("failure");
                    throw new NoSuchElementException();
                }
            }
        });
    }
}
