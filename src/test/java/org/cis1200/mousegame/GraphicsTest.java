package org.cis1200.mousegame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;

public class GraphicsTest implements Runnable {
    public void run() {
        JFrame frame = new JFrame();
        int dim = 15;
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1920, 1080);
        Container contentPanel = frame.getContentPane();
        contentPanel.setLayout(new FlowLayout());
        PlayerInputPopUp pipu;
        MouseView mouseView2;
        try {
            pipu = new PlayerInputPopUp();
            contentPanel.add(pipu);
            mouseView2 = new MouseView(pipu.getIV().p1Name(), pipu.getIV().p2Name());
            pipu.setVisible(false);
            contentPanel.add(mouseView2);
        }  catch (IllegalArgumentException e) {
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSED));
        }
        /*MouseView mouseView = new MouseView("Joseph", "Timothy");
        contentPanel.add(mouseView);
        mouseView.textDisplay.updateDisplayMoveNotifier(new MoveNotifier(true, 3, 'N', false,
                3, 4, new Point(2, 3), false, true, false));
        mouseView.player1Lab.addLaser(false, 4);
        mouseView.player1Lab.addTrap(5, 5);
        mouseView.player1Lab.enableTrapButtons(); */
        frame.setVisible(true);

        /*
        LabDisplay labDisplay = new LabDisplay(true);
        contentPanel.add(labDisplay);
        LabDisplay labDisplay2 = new LabDisplay(false);
        contentPanel.add(labDisplay2);
        ActionToolbar actionToolbar = new ActionToolbar();
        contentPanel.add(actionToolbar);
        TextDisplay textDisplay = new TextDisplay("Joseph", "Timothy");
        contentPanel.add(textDisplay);
        textDisplay.updateDisplayLaserNotifier(new LaserNotifier(true, 3, true,
                5, true));
        textDisplay.updateDisplayMoveNotifier(new MoveNotifier(true, 3, 'N', false,
                2,0, new Point(3, 3), false, true, false));
        //textDisplay.gameOverDialog(false);
        actionToolbar.enableP1Turn();
        labDisplay.addLaser(true, 2);
        labDisplay.addLaser(true, 5);
        labDisplay.addLaser(false, 2);
        labDisplay.addLaser(false, 5);
        labDisplay.displayMouse(new Point(1, 4));
        labDisplay.addTrap(15, 15);
        labDisplay.addTrap(14, 15);
        labDisplay.disableButtons();
        labDisplay.enableTrapButtons();
         */
    }
}
