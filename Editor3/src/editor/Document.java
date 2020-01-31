/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editor;

import editor.display.CharacterDisplay;

import java.util.HashMap;

/**
 * This class represents the document being edited. Using a 2d array to hold the
 * document content is probably not a very good choice. Fixing this class is the
 * main focus of the exercise. In addition to designing a better data model, you
 * must add methods to do at least basic editing: write and delete text, and
 * moving the cursor
 *
 * @author evenal
 */
public class Document {

    private CharacterDisplay display;
    private int cursorRow;
    private int cursorCol;
    private int totalCharSpaces;
    private HashMap<Integer, Character> data;

    public Document(CharacterDisplay display) {
        this.display = display;
        data = new HashMap<>();
        cursorCol = cursorRow = 0;
        display.displayCursor(' ', cursorRow, cursorCol);

        //Assigning empty spaces to all the key values in the Hash Map.
        totalCharSpaces = (CharacterDisplay.WIDTH * CharacterDisplay.HEIGHT);
        int i = 0;
        while (data.size() < totalCharSpaces) {
            data.put(i, ' ');
            i++;
        }
    }

    public void insertChar(char c) {
        int currentPosition = ((cursorRow+1) * cursorCol);
        data.put(currentPosition, c);

        display.displayChar(c, cursorRow, cursorCol);
        cursorCol++;
        if (cursorCol >= CharacterDisplay.WIDTH) {
            cursorCol = 0;
            cursorRow++;
        }
        displayCursor();
    }

    /**
     * This method moves the cursor back one space, and removes the character
     * at this position.
     */
    public void backspace() {
        int currentPosition = ((cursorRow+1) * cursorCol);
        cursorCol = cursorCol - 1;

        if (cursorCol < 0) {
            cursorCol = 0;
            if (cursorRow > 0) {
                cursorRow = cursorRow -1;
                cursorCol = CharacterDisplay.WIDTH -1;
                data.replace(currentPosition, ' ');
            }
        }
        else {
            data.replace(currentPosition, ' ');
        }

        display.displayChar(' ', cursorRow, cursorCol);
        displayCursor();
    }

    public void moveCursor(int decValue) {

            switch (decValue) {
                case 23:
                    if (cursorRow > 0) {
                        cursorRow = cursorRow - 1;
                        break;
                    }
                case 19:
                    if (cursorRow < CharacterDisplay.HEIGHT - 1) {
                        cursorRow = cursorRow + 1;
                        break;
                    }
                case 1:
                    if (cursorCol > 0) {
                        cursorCol = cursorCol - 1;
                        break;
                    }
                case 4:
                    cursorCol = cursorCol + 1;
                    break;
            }

            displayCursor();
    }



    private void displayCursor() {
        int currentPosition = ((cursorRow+1) * cursorCol);
        display.displayCursor(data.get(currentPosition),
                cursorRow, cursorCol);
    }
}