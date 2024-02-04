package org.cis1200.mousegame;

import javax.swing.*;
import java.io.*;

public class PlayerInputPopUp extends JOptionPane {

    private final InitialValues initialValues;

    PlayerInputPopUp() {
        super();
        String[] directions = new String[] {"North", "South", "East", "West"};
        Integer[] numbers = new Integer[Lab.getDim()];
        int r = 0;
        for (int i = 0; i < Lab.getDim(); i++) {
            r++;
            numbers[i] = r;
        }
        message = getInstructions();
        showMessageDialog(null, message);
        String p1Name = showInputDialog("Player 1 Input Name");
        checkNullString(p1Name);
        int p1X0;
        try {
            p1X0 = (Integer) showInputDialog(null, "Choose Initial X Position of Mouse",
                    "Player 1", JOptionPane.QUESTION_MESSAGE, null, numbers, numbers[0]);
        } catch (NullPointerException e) {
            throw new IllegalArgumentException();
        }
        int p1Y0;
        try {
            p1Y0 = (Integer) showInputDialog(null, "Choose Initial Y Position of Mouse",
                    "Player 1", JOptionPane.QUESTION_MESSAGE, null, numbers, numbers[0]);
        } catch (NullPointerException e) {
            throw new IllegalArgumentException();
        }
        int p1StandardDist;
        try {
            p1StandardDist = (Integer) showInputDialog(null, "Choose a Standard Move Distance",
                    "Player 1", JOptionPane.QUESTION_MESSAGE, null, numbers, numbers[0]);
        } catch (NullPointerException e) {
            throw new IllegalArgumentException();
        }
        String p1Dir1 = (String) showInputDialog(null, "Choose First Direction of Special Move",
                "Player 1", JOptionPane.QUESTION_MESSAGE, null, directions, directions[0]);
        checkNullString(p1Dir1);
        int p1Dist1;
        try {
            p1Dist1 = (Integer) showInputDialog(null, "Choose First Distance of Special Move",
                    "Player 1", JOptionPane.QUESTION_MESSAGE, null, numbers, numbers[0]);
        } catch (NullPointerException e) {
            throw new IllegalArgumentException();
        }
        String p1Dir2 = (String) showInputDialog(null, "Choose Second Direction of Special Move",
                "Player 1", JOptionPane.QUESTION_MESSAGE, null, directions, directions[0]);
        checkNullString(p1Dir2);
        int p1Dist2;
        try {
            p1Dist2 = (Integer) showInputDialog(null,
                    "Choose Second Distance of Special Move",
                    "Player 1", JOptionPane.QUESTION_MESSAGE, null, numbers, numbers[0]);
        } catch (NullPointerException e) {
            throw new IllegalArgumentException();
        }
        String p2Name = showInputDialog("Player 2 Input Name");
        checkNullString(p2Name);
        int p2X0;
        try {
            p2X0 = (Integer) showInputDialog(null, "Choose Initial X Position of Mouse",
                    "Player 2", JOptionPane.QUESTION_MESSAGE, null, numbers, numbers[0]);
        } catch (NullPointerException e) {
            throw new IllegalArgumentException();
        }
        int p2Y0;
        try {
            p2Y0 = (Integer) showInputDialog(null, "Choose Initial Y Position of Mouse",
                    "Player 2", JOptionPane.QUESTION_MESSAGE, null, numbers, numbers[0]);
        } catch (NullPointerException e) {
            throw new IllegalArgumentException();
        }
        int p2StandardDist;
        try {
            p2StandardDist = (Integer) showInputDialog(null, "Choose a Standard Move Distance",
                    "Player 2", JOptionPane.QUESTION_MESSAGE, null, numbers, numbers[0]);
        } catch (NullPointerException e) {
            throw new IllegalArgumentException();
        }
        String p2Dir1 = (String) showInputDialog(null, "Choose First Direction of Special Move",
                "Player 2", JOptionPane.QUESTION_MESSAGE, null, directions, directions[0]);
        checkNullString(p2Dir1);
        int p2Dist1;
        try {
            p2Dist1 = (Integer) showInputDialog(null, "Choose First Distance of Special Move",
                    "Player 2", JOptionPane.QUESTION_MESSAGE, null, numbers, numbers[0]);
        } catch (NullPointerException e) {
            throw new IllegalArgumentException();
        }
        String p2Dir2 = (String) showInputDialog(null, "Choose Second Direction of Special Move",
                "Player 2", JOptionPane.QUESTION_MESSAGE, null, directions, directions[0]);
        checkNullString(p2Dir2);
        int p2Dist2;
        try {
            p2Dist2 = (Integer) showInputDialog(null, "Choose Second Distance of Special Move",
                    "Player 2", JOptionPane.QUESTION_MESSAGE, null, numbers, numbers[0]);
        } catch (NullPointerException e) {
            throw new IllegalArgumentException();
        }
        initialValues = new InitialValues(p1Name, p1X0, p1Y0, p1StandardDist,
                p1Dir1, p1Dist1, p1Dir2, p1Dist2,
                p2Name, p2X0, p2Y0, p2StandardDist, p2Dir1, p2Dist1, p2Dir2, p2Dist2);
    }

    public InitialValues getIV() {
        return initialValues;
    }

    private void checkNullString(String s) {
        if (s == null) {
            throw new IllegalArgumentException();
        }
    }

    public static char charOfStringDir(String dir) {
        switch (dir) {
            case "North":
                return 'N';
            case "South":
                return 'S';
            case "East":
                return 'E';
            case "West":
                return 'W';
            default:
                throw new IllegalArgumentException();
        }
    }

    public record InitialValues(String p1Name, int p1X0, int p1Y0, int p1StandardDist,
                                String p1Dir1, int p1Dist1, String p1Dir2, int p1Dist2,
                                String p2Name, int p2X0, int p2Y0, int p2StandardDist,
                                String p2Dir1, int p2Dist1, String p2Dir2, int p2Dist2) {
    }

    public static String getInstructions() throws IllegalArgumentException {
        try {
            FileReader reader = new FileReader("MouseGameInstructions.txt");
            BufferedReader bufferedReader = new BufferedReader(reader);
            String string = "";
            while (bufferedReader.ready()) {
                string = string + bufferedReader.readLine() + "\n";
            }
            return string;
        } catch (Exception e) {
            return "FileNotFound";
        }
    }
}
