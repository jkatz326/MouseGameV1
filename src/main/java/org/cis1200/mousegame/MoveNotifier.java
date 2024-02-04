package org.cis1200.mousegame;

import java.awt.*;

public record MoveNotifier(boolean isPlayer1, int round, char dir, boolean isSpecial, int spookDist,
                           int lasersTriggered, Point endPoint, boolean onNS, boolean onEW,
                           boolean gameOver, Point trapLoc)  {
}
