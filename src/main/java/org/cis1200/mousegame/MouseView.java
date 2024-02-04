package org.cis1200.mousegame;

import java.awt.*;
import javax.swing.*;


import static java.awt.BorderLayout.*;

public class MouseView extends JPanel {
    private LabDisplay player1Lab;
    private LabDisplay player2Lab;
    private ActionToolbar actionToolbar;
    private TextDisplay textDisplay;

    public MouseView(String p1Name, String p2Name) {
        super();
        BorderLayout layout = new BorderLayout();
        setLayout(layout);
        player1Lab = new LabDisplay(true);
        player2Lab = new LabDisplay(false);
        actionToolbar = new ActionToolbar();
        textDisplay = new TextDisplay(p1Name, p2Name);
        add(player1Lab, LINE_START);
        add(player2Lab, CENTER);
        add(actionToolbar, PAGE_END);
        add(textDisplay, LINE_END);
    }

    public LabDisplay getPlayer1Lab() {
        return player1Lab;
    }

    public LabDisplay getPlayer2Lab() {
        return player2Lab;
    }

    public ActionToolbar getActionToolbar() {
        return actionToolbar;
    }

    public TextDisplay getTextDisplay() {
        return textDisplay;
    }



}
