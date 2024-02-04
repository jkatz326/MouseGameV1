package org.cis1200.mousegame;

public record TrapNotifier(boolean isPlayer1, int round, int xPos,
                           int yPos, boolean gameOver) { }
