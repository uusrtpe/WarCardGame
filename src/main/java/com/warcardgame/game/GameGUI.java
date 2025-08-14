package com.warcardgame.game;


import com.warcardgame.model.Card;
import com.warcardgame.model.Game;
import com.warcardgame.save.SaveGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameGUI extends CommonGUI {
    private JFrame frame;
    private JPanel mainPanel;
    private JLabel playerCardLabel;
    private JLabel computerCardLabel;
    private JLabel playerScoreLabel;
    private JLabel computerScoreLabel;
    private JLabel resultLabel;
    private JButton playButton;
    private Game game;
    private PlayButtonListener playButtonListener;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GameGUI().createAndShowGUI());
    }

    /**
     * Initializes and displays the main game GUI.
     * Sets up the frame, menu bar, and main panel.
     * Prompts the player to enter their name and starts a new game.
     */
    public void createAndShowGUI() {

        String playerName = getPlayerName();

        game = new Game(playerName);

        frame = new JFrame("War Card Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 700);

        JMenuBar menuBar = getjMenuBar();
        frame.setJMenuBar(menuBar);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.GREEN);

        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(new GridLayout(2, 2));
        cardPanel.setBackground(Color.GREEN);

        playerCardLabel = new JLabel(scaleImageIcon("/images/card_back.png"));
        playerCardLabel.setHorizontalAlignment(JLabel.CENTER);
        cardPanel.add(playerCardLabel);

        computerCardLabel = new JLabel(scaleImageIcon("/images/card_back.png"));
        computerCardLabel.setHorizontalAlignment(JLabel.CENTER);
        cardPanel.add(computerCardLabel);

        playerScoreLabel = new JLabel("Total score: 0", SwingConstants.CENTER);
        cardPanel.add(playerScoreLabel);

        computerScoreLabel = new JLabel("Total score: 0", SwingConstants.CENTER);
        cardPanel.add(computerScoreLabel);

        resultLabel = new JLabel("Result: ", SwingConstants.CENTER);

        playButton = new JButton("Play");
        playButtonListener = new PlayButtonListener();
        playButton.addActionListener(playButtonListener);

        mainPanel.add(cardPanel, BorderLayout.CENTER);
        mainPanel.add(resultLabel, BorderLayout.NORTH);
        mainPanel.add(playButton, BorderLayout.SOUTH);


        frame.getContentPane().add(mainPanel);
        frame.setVisible(true);
    }

    private JMenuBar getjMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Game");
        JMenuItem newGameItem = new JMenuItem("New Game");
        JMenuItem saveGameItem = new JMenuItem("Save Game");
        JMenuItem openGameItem = new JMenuItem("Open Game");

        newGameItem.addActionListener(e -> newGame());
        saveGameItem.addActionListener(e -> saveGame());
        openGameItem.addActionListener(e -> openGame());

        gameMenu.add(newGameItem);
        gameMenu.add(saveGameItem);
        gameMenu.add(openGameItem);
        menuBar.add(gameMenu);

        JMenu aboutMenu = new JMenu("About");
        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Developed by Doruk Acar", "About", JOptionPane.INFORMATION_MESSAGE));
        aboutMenu.add(aboutItem);
        menuBar.add(aboutMenu);
        return menuBar;
    }

    /**
     * Prompts the user to enter their name using a dialog box.
     *
     * @return the entered player name.
     */
    private String getPlayerName() {
        return JOptionPane.showInputDialog(frame, "Enter your name:", "Player Name", JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * Starts a new game by reinitializing the game state and updating the UI.
     * Resets the player's and computer's card labels and score labels.
     * Enables the play button.
     */
    private void newGame() {
        String playerName = getPlayerName();
        game = new Game(playerName);
        playerCardLabel.setIcon(scaleImageIcon("/images/card_back.png"));
        computerCardLabel.setIcon(scaleImageIcon("/images/card_back.png"));

        playerScoreLabel.setText("Total score: 0 | Cards left: " + game.getPlayer().getDeckSize());
        computerScoreLabel.setText("Total score: 0 | Cards left: " + game.getComputer().getDeckSize());

        playerScoreLabel.setText("Total score: 0");
        computerScoreLabel.setText("Total score: 0");
        resultLabel.setText("Result: ");
        playButton.setEnabled(true);
    }

    /**
     * Saves the current game state using the SaveGame class.
     * Displays a confirmation message to the player.
     */
    private void saveGame() {
        SaveGame.saveGame(game);
        JOptionPane.showMessageDialog(frame, "Game saved successfully!", "Save Game", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Loads a previously saved game state using the SaveGame class.
     * Updates the UI with the loaded game state.
     * Displays a success or error message based on the outcome.
     */
    private void openGame() {
        game = SaveGame.loadGame();
        if (game != null) {
            updateUI();
            playerCardLabel.setIcon(scaleImageIcon(game.getLastCardOfPlayer().getImagePath()));
            computerCardLabel.setIcon(scaleImageIcon(game.getLastCardOfComputer().getImagePath()));
            JOptionPane.showMessageDialog(frame, "Game loaded successfully!", "Open Game", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(frame, "Failed to load game!", "Open Game", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Updates the UI elements based on the current game state.
     * Updates the player's and computer's score labels, card labels, and result label.
     * Enables the play button if the game is not over.
     */
    private void updateUI() {
        playerScoreLabel.setText("Total score: " + game.getPlayer().getScore() + " | Cards left: " + game.getPlayer().getDeckSize());
        computerScoreLabel.setText("Total score: " + game.getComputer().getScore() + " | Cards left: " + game.getComputer().getDeckSize());
        playerCardLabel.setIcon(scaleImageIcon("/images/card_back.png"));
        computerCardLabel.setIcon(scaleImageIcon("/images/card_back.png"));
        resultLabel.setText("Result: ");
        playButton.setEnabled(true);
    }

    private class PlayButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // Player and computer draw a card
            Card playerCard = game.playerDrawCard();
            Card computerCard = game.computerDrawCard();

            // Ensure both players have drawn a card
            if (playerCard != null && computerCard != null) {
                // Update the card labels with the drawn cards
                playerCardLabel.setIcon(scaleImageIcon(playerCard.getImagePath()));
                computerCardLabel.setIcon(scaleImageIcon(computerCard.getImagePath()));

                // Determine the round winner and update scores accordingly
                if (playerCard.getValue() > computerCard.getValue()) {
                    resultLabel.setText(game.getPlayer().getName() + " wins this round!");
                    game.getPlayer().increaseScore(playerCard.getValue() + computerCard.getValue());
                    game.getPlayer().addCard(playerCard);
                    game.getPlayer().addCard(computerCard);
                } else if (playerCard.getValue() < computerCard.getValue()) {
                    resultLabel.setText("Computer wins this round!");
                    game.getComputer().increaseScore(playerCard.getValue() + computerCard.getValue());
                    game.getComputer().addCard(computerCard);
                    game.getComputer().addCard(playerCard);
                } else {
                    // Handle the war scenario where both cards have the same value
                    List<Card> warGroundCardList = new ArrayList<>();


                    /**
                     * The firstWar boolean is used to distinguish the initial round of war from subsequent rounds,
                     * ensuring that special actions, like collecting initial cards back to the players' decks,
                     * occur only once per war cycle.
                     */
                    boolean firstWar = true;

                    // Loop until the war is resolved
                    while (playerCard.getValue() == computerCard.getValue()) {
                        // Check if either player has insufficient cards for war
                        if (game.getComputer().getDeckSize() < 3) {
                            resultLabel.setText(game.getPlayer().getName() + " wins the game!  Computer doesn't have enough card.");
                            playButton.setEnabled(false);
                            break;
                        }

                        if (game.getPlayer().getDeckSize() < 3) {
                            resultLabel.setText("Computer wins the game! " + game.getPlayer().getName() + " doesn't have enough card. ");
                            playButton.setEnabled(false);
                            break;
                        }

                        // Notify players about the war
                        JOptionPane.showMessageDialog(frame, "Now you will play 2 face-down cards and 1 face-up card. \n" +
                                        "The player with the higher face-up card wins!",
                                "War Time!", JOptionPane.INFORMATION_MESSAGE);

                        // Draw 2 face-down cards and 1 face-up card for both players
                        Card[] playerWarCards = new Card[3];
                        Card[] computerWarCards = new Card[3];

                        playerWarCards[0] = game.playerDrawCard();
                        playerWarCards[1] = game.playerDrawCard();
                        playerWarCards[2] = game.playerDrawCard();

                        computerWarCards[0] = game.computerDrawCard();
                        computerWarCards[1] = game.computerDrawCard();
                        computerWarCards[2] = game.computerDrawCard();


                        // Show the last drawn cards (face-up cards)
                        playerCardLabel.setIcon(scaleImageIcon(playerWarCards[2].getImagePath()));
                        computerCardLabel.setIcon(scaleImageIcon(computerWarCards[2].getImagePath()));

                        Card tempPlayerCard = playerCard;
                        Card tempComputerCard = computerCard;

                        // Compare the last drawn cards
                        playerCard = playerWarCards[2];
                        computerCard = computerWarCards[2];

                        if (playerCard.getValue() > computerCard.getValue()) {
                            resultLabel.setText(game.getPlayer().getName() + " wins the war!");
                            if (firstWar) {
                                game.getPlayer().addCard(tempPlayerCard);
                                game.getPlayer().addCard(tempComputerCard);
                            }
                            game.getPlayer().increaseScore(playerCard.getValue() + computerCard.getValue());
                            game.resolveWar(playerWarCards, computerWarCards, warGroundCardList);
                        } else if (playerCard.getValue() < computerCard.getValue()) {
                            resultLabel.setText("Computer wins the war!");
                            if (firstWar) {
                                game.getComputer().addCard(tempPlayerCard);
                                game.getComputer().addCard(tempComputerCard);
                            }
                            game.getComputer().increaseScore(playerCard.getValue() + computerCard.getValue());
                            game.resolveWar(playerWarCards, computerWarCards, warGroundCardList);
                        } else {
                            if (firstWar) {
                                warGroundCardList.add(tempPlayerCard);
                                warGroundCardList.add(tempComputerCard);
                                firstWar = false;
                            }

                            warGroundCardList.addAll(Arrays.asList(playerWarCards));
                            warGroundCardList.addAll(Arrays.asList(computerWarCards));
                        }


                    }

                }

                // Update the score labels and check if the game is over
                playerScoreLabel.setText("Total score: " + game.getPlayer().getScore() + " | Cards left: " + game.getPlayer().getDeckSize());
                computerScoreLabel.setText("Total score: " + game.getComputer().getScore() + " | Cards left: " + game.getComputer().getDeckSize());

                // If the game is over, disable the play button and announce the winner
                if (game.isGameOver()) {
                    playButton.setEnabled(false);
                    if (game.getPlayer().getDeckSize() > game.getComputer().getDeckSize()) {
                        resultLabel.setText(game.getPlayer().getName() + " wins the game!");
                    } else {
                        resultLabel.setText("Computer wins the game!");
                    }
                }
            } else {
                // Handle the scenario where a player cannot draw a card (game over)
                playButton.setEnabled(false);
                if (game.getPlayer().getDeckSize() > game.getComputer().getDeckSize()) {
                    resultLabel.setText(game.getPlayer().getName() + " wins the game!");
                } else {
                    resultLabel.setText("Computer wins the game!");
                }
            }
        }

    }
}
