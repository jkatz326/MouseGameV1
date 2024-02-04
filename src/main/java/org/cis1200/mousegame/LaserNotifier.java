package org.cis1200.mousegame;


public record LaserNotifier(boolean isPlayer1, int round,
                            boolean isNS, int index, boolean isMouseOn) {

}
