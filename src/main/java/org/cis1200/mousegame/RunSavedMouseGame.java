package org.cis1200.mousegame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.NoSuchElementException;

public class RunSavedMouseGame implements Runnable {
    public void run() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1920, 1080);
        Container contentPanel = frame.getContentPane();
        contentPanel.setLayout(new FlowLayout());
        PlayerInputPopUp playerIPU;
        MouseGame mouseGame = null;
        try {
            File file = new File("SavedState.pdf");
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            if (bufferedReader.ready()) {
                String startString = bufferedReader.readLine();
                if (startString.equals("SV")) {
                    int p1MouseX = Integer.parseInt(bufferedReader.readLine());
                    int p1MouseY = Integer.parseInt(bufferedReader.readLine());
                    int dist1 = Integer.parseInt(bufferedReader.readLine());
                    char m1dir1 = bufferedReader.readLine().charAt(0);
                    int m1dist1 = Integer.parseInt(bufferedReader.readLine());
                    char m1dir2 = bufferedReader.readLine().charAt(0);
                    int m1dist2 = Integer.parseInt(bufferedReader.readLine());
                    SpecialMoveDir smd1 = new SpecialMoveDir(m1dir1, m1dist1, m1dir2, m1dist2);
                    String p1Name = bufferedReader.readLine();
                    int p2MouseX = Integer.parseInt(bufferedReader.readLine());
                    int p2MouseY = Integer.parseInt(bufferedReader.readLine());
                    int dist2 = Integer.parseInt(bufferedReader.readLine());
                    char m2dir1 = bufferedReader.readLine().charAt(0);
                    int m2dist1 = Integer.parseInt(bufferedReader.readLine());
                    char m2dir2 = bufferedReader.readLine().charAt(0);
                    int m2dist2 = Integer.parseInt(bufferedReader.readLine());
                    SpecialMoveDir smd2 = new SpecialMoveDir(m2dir1, m2dist1, m2dir2, m2dist2);
                    String p2Name = bufferedReader.readLine();
                    mouseGame = new MouseGame(p1MouseX, p1MouseY, dist1, smd1, p1Name,
                            p2MouseX, p2MouseY, dist2, smd2, p2Name);
                }
            } else {
                throw new RuntimeException();
            }
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFound");
            throw new RuntimeException(e);
        } catch (IOException e) {
            System.out.println("IO");
            throw new RuntimeException(e);
        }


        contentPanel.add(mouseGame.getMouseView());
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