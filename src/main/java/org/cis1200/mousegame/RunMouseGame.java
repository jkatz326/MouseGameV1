package org.cis1200.mousegame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.NoSuchElementException;

public class RunMouseGame implements Runnable {
    public void run() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1920, 1080);
        Container contentPanel = frame.getContentPane();
        contentPanel.setLayout(new FlowLayout());
        PlayerInputPopUp playerIPU;
        MouseGame mouseGame = null;
        try {
            playerIPU = new PlayerInputPopUp();
            contentPanel.add(playerIPU);
            SpecialMoveDir specialMoveDir1 =
                    new SpecialMoveDir(PlayerInputPopUp.charOfStringDir(playerIPU.
                    getIV().p1Dir1()), playerIPU.getIV().p1Dist1(),
                    PlayerInputPopUp.charOfStringDir(playerIPU.getIV().p1Dir2()),
                    playerIPU.getIV().p1Dist2());
            SpecialMoveDir specialMoveDir2 = new SpecialMoveDir(PlayerInputPopUp.charOfStringDir(
                    playerIPU.getIV().p2Dir1()), playerIPU.getIV().p2Dist1(),
                    PlayerInputPopUp.charOfStringDir(playerIPU.getIV().p2Dir2()),
                    playerIPU.getIV().p2Dist2());
            mouseGame = new MouseGame(playerIPU.getIV().p1X0(), playerIPU.getIV().p1Y0(),
                    playerIPU.getIV().p1StandardDist(), specialMoveDir1, playerIPU.getIV().p1Name(),
                    playerIPU.getIV().p2X0(), playerIPU.getIV().p2Y0(),
                    playerIPU.getIV().p2StandardDist(), specialMoveDir2,
                    playerIPU.getIV().p2Name());
            playerIPU.setVisible(false);
            contentPanel.add(mouseGame.getMouseView());
        } catch (IllegalArgumentException e) {
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSED));
        }
        frame.setVisible(true);
        MouseGame finalMouseGame = mouseGame;
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                try {
                    finalMouseGame.saveState();
                } catch (NullPointerException f) {
                    throw new NoSuchElementException();
                }
            }
        });
    }

}