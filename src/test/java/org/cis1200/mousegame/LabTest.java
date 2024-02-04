package org.cis1200.mousegame;

import org.junit.jupiter.api.*;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
public class LabTest {
    int dim = 12;

    SpecialMoveDir smd = new SpecialMoveDir('S', 3, 'E', 4);

    @Test
    public void testLabConstructorAndResetOutOfBounds() {
        assertThrows(IllegalArgumentException.class, () -> {
            Lab test1 = new Lab(true, 2, smd, -8, 4);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            Lab test2 = new Lab(true, 2, smd, 6, -2);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            Lab test3 = new Lab(true, 2, smd, dim + 1, 8);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            Lab test4 = new Lab(true, 2, smd, 5, dim + 3);
        });
        Lab test5 = new Lab(true, 2, smd, 1, dim);
        assertThrows(IllegalArgumentException.class, () -> {
            test5.reset(0, 8);
        });
    }

    @Test
    public void testLabConstructorInvalidInputs() {
        assertThrows(IllegalArgumentException.class, () -> {
            Lab lab1 = new Lab(true, 2, smd,dim + 2, 7);
        });
        SpecialMoveDir smd2 = new SpecialMoveDir('n', 3, 'E', 4);
        assertThrows(IllegalArgumentException.class, () -> {
            Lab lab2 = new Lab(true, 2, smd2, 3, 7);
        });
        SpecialMoveDir smd3 = new SpecialMoveDir('N', dim + 3, 'E', 4);
        assertThrows(IllegalArgumentException.class, () -> {
            Lab lab2 = new Lab(true, 2, smd3, 3, 7);
        });
    }
    @Test
    public void testAddLaserAndTrapsOutOfBounds() {
        Lab lab = new Lab(true, 2, smd, 4, 4);
        assertThrows(IllegalArgumentException.class, () -> {
            lab.addLaser(0, false);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            lab.addLaser(dim + 1, false);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            lab.addTrap(dim + 1, 8);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            lab.addTrap(4, 0);
        });
    }

    @Test
    public void testMoveInvalidDirection() {
        Lab lab = new Lab(true, 2, smd, dim, 4);
        assertThrows(IllegalArgumentException.class, () -> {
            lab.standardMove('n', 8);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            lab.spook('2');
        });
    }

    @Test
    public void testMoveOutOfBounds() {
        Lab lab = new Lab(true, 2, smd,1, 1);
        MoveNotifier mn1 = lab.standardMove('N', 9);
        assertEquals(new Point(1, 1), mn1.endPoint());
        MoveNotifier mn2 = lab.standardMove('E', dim + 6);
        assertEquals(new Point(dim, 1), mn2.endPoint());
        MoveNotifier mn3 = lab.standardMove('S', 3);
        assertEquals(new Point(dim, 4), mn3.endPoint());
        MoveNotifier mn4 = lab.standardMove('S', dim - 4);
        assertEquals(new Point(dim, dim), mn4.endPoint());
        MoveNotifier mn5 = lab.standardMove('W', dim + 2);
        assertEquals(new Point(1, dim), mn5.endPoint());
    }

    @Test
    public void testSpecialMove() {
        Lab lab1 = new Lab(true, 2, smd, 1, 1);
        MoveNotifier mn1 = lab1.specialMove();
        assertEquals(new Point(5, 4), mn1.endPoint());
        SpecialMoveDir smd2 = new SpecialMoveDir('N', 8, 'W', 2);
        Lab lab2 = new Lab(true, 2, smd2, 5, 4);
        MoveNotifier mn2 = lab2.specialMove();
        assertEquals(new Point(3, 1), mn2.endPoint());
    }

    @Test
    public void testSpook() {
        Lab lab = new Lab(true, 2, smd, 1, 1);
        MoveNotifier mn1 = lab.spook('E');
        int spookLevel1 = mn1.spookDist();
        assertTrue(spookLevel1 > 0 && spookLevel1 < 7);
        Point endPoint1 = mn1.endPoint();
        MoveNotifier mn2 = lab.spook('S');
        int spookLevel2 = mn2.spookDist();
        assertTrue(spookLevel2 > 0 && spookLevel2 < 7);
    }
    @Test
    public void testLasers() {
        Lab lab = new Lab(true, 2, smd, 1, 1);
        assertTrue(lab.addLaser(1, true).isMouseOn());
        assertFalse(lab.addLaser(3, true).isMouseOn());
        assertFalse(lab.addLaser(5, true).isMouseOn());
        assertFalse(lab.addLaser(3, false).isMouseOn());
        assertFalse(lab.addLaser(5, false).isMouseOn());
        MoveNotifier mn1 = lab.standardMove('E', 6);
        assertEquals(new Point(7, 1), mn1.endPoint());
        assertEquals(2, mn1.lasersTriggered());
        assertFalse(mn1.onNS());
        assertFalse(mn1.onEW());
        MoveNotifier mn2 = lab.standardMove('S', 4);
        assertEquals(new Point(7, 5), mn2.endPoint());
        assertEquals(2, mn2.lasersTriggered());
        assertFalse(mn2.onNS());
        assertTrue(mn2.onEW());
        MoveNotifier mn3 = lab.standardMove('W', 6);
        assertEquals(new Point(1, 5), mn3.endPoint());
        assertEquals(3, mn3.lasersTriggered());
        assertTrue(mn3.onNS());
        assertTrue(mn3.onEW());
    }

    @Test
    public void testTraps() {
        SpecialMoveDir smdTrap = new SpecialMoveDir('S', 8, 'E', 6);
        Lab lab = new Lab(true, 2, smdTrap, 4, 5);
        assertFalse(lab.addTrap(3,3).gameOver());
        assertFalse(lab.addTrap(2, 3).gameOver());
        MoveNotifier mn1 = lab.standardMove('N', 2);
        assertFalse(mn1.gameOver());
        MoveNotifier mn2 = lab.standardMove('W', 1);
        assertEquals(new Point(3, 3), mn2.endPoint());
        assertTrue(mn2.gameOver());
        MoveNotifier mn3 = lab.standardMove('E', 2);
        assertFalse(mn3.gameOver());
        MoveNotifier mn4 = lab.standardMove('W', 9);
        assertEquals(new Point(1, 3), mn4.endPoint());
        assertTrue(mn4.gameOver());
        MoveNotifier mn5 = lab.specialMove();
        assertEquals(new Point(7, 11), mn5.endPoint());
        assertFalse(mn5.gameOver());
        assertFalse(lab.addTrap(7, 8).gameOver());
        MoveNotifier mn6 = lab.standardMove('N', 6);
        assertEquals(new Point(7, 5), mn6.endPoint());
        assertTrue(mn6.gameOver());
        MoveNotifier mn7 = lab.standardMove('N', 2);
        assertFalse(mn7.gameOver());
        assertEquals(new Point(7, 3), mn7.endPoint());
        assertFalse(lab.addTrap(7, 4).gameOver());
        assertTrue(lab.addTrap(7, 3).gameOver());
    }

    @Test
    public void testStringOfChar() {
        assertEquals("North", TextDisplay.stringOfChar('N'));
        assertThrows(IllegalArgumentException.class, () -> {
            TextDisplay.stringOfChar('n');
        });
    }

    @Test
    public void testMouseLoc() {
        Lab lab = new Lab(true, 8, smd, 2, 2);
        lab.addTrap(2, 6);
        lab.addTrap(2, 8);
        MoveNotifier mn = lab.standardMove('S');
        assertEquals(new Point(2, 6), mn.trapLoc());
    }
    @Test
    public void savedState2() {
        MouseGame mouseGame = new MouseGame(2, 2, 2, smd, "Player 1",
            2, 2, 2, smd, "Player 2");
        mouseGame.saveState();
        try {
            File file = new File("SavedState.pdf");
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            assertTrue(bufferedReader.ready());
            while (bufferedReader.ready()) {
                System.out.println(bufferedReader.readLine());
            }
        } catch (IOException e) {
            fail();
        }
    }

}
