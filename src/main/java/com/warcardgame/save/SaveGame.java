package com.warcardgame.save;

import com.warcardgame.model.Game;

import java.io.*;

public class SaveGame {

    private static final String SAVE_FILE = "savegame.dat";

    /**
     * Saves the current game state to a file.
     *
     * @param game The Game object representing the current game state to be saved.
     */
    public static void saveGame(Game game) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SAVE_FILE))) {
            oos.writeObject(game);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads a previously saved game state from a file.
     *
     * @return The Game object representing the saved game state, or null if an error occurs.
     */
    public static Game loadGame() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SAVE_FILE))) {
            return (Game) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
