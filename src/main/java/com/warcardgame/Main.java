package com.warcardgame;

import com.warcardgame.game.GameGUI;

public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> new GameGUI().createAndShowGUI());
    }
}