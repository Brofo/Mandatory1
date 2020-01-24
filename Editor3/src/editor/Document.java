/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editor;

import editor.display.CharacterDisplay;
import java.util.LinkedList;

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
    private LinkedList<Character> data;

    public Document(CharacterDisplay display) {
        this.display = display;
        data = new LinkedList<>();
        cursorCol = cursorRow = 0;
        display.displayCursor(' ', cursorRow, cursorCol);
    }

    public void insertChar(char c) {
        data.add(c);
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

        data.remove(cursorRow);
        cursorCol = cursorCol - 1;

        if (cursorCol < 0 && cursorRow > 0) {
            cursorRow = cursorRow -1;
            cursorCol = CharacterDisplay.WIDTH -1;
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

            int updatedCursorPositionValue = ((cursorRow+1) * cursorCol);

            while (data.size() < updatedCursorPositionValue) {
                data.add(' ');
            }
            displayCursor();
    }



    private void displayCursor() {
        display.displayCursor(data.get(cursorRow),
                cursorRow, cursorCol);
    }
}