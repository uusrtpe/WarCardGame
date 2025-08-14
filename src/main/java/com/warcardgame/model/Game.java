package com.warcardgame.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Game implements Serializable {

    private final Player player;

    private final Player computer;

    private final List<Card> deck;

    private Card lastCardOfPlayer;
    private Card lastCardOfComputer;

    public Game(String playerName) {
        this.player = new Player(playerName);
        this.computer = new Player("Computer");
        this.deck = createDeck();
        shuffleDeck();
        dealCards();
    }

    private List<Card> createDeck() {
        List<Card> deck = new ArrayList<>();
        String[] suits = {"hearts", "diamonds", "clubs", "spades"};
        for (String suit : suits) {
            for (int value = 2; value <= 14; value++) {
                deck.add(new Card(suit, value));
            }
        }
        return deck;
    }

    private void shuffleDeck() {
        Collections.shuffle(deck);
    }

    private void dealCards() {
        int deckSize = deck.size();
        for (int i = 0; i < deckSize; i++) {
            if (i % 2 == 0) {
                player.addCard(deck.get(i));
            } else {
                computer.addCard(deck.get(i));
            }
        }
    }

    public void resolveWar(Card[] playerWarCards, Card[] computerWarCards, List<Card> warGroundCardList) {
        int playerWarScore = playerWarCards[2].getValue();
        int computerWarScore = computerWarCards[2].getValue();

        if (playerWarScore > computerWarScore) {
            player.addCardsToDeck(playerWarCards);
            player.addCardsToDeck(computerWarCards);
            if (!warGroundCardList.isEmpty()) {
                player.addCardsToDeck(warGroundCardList.toArray(new Card[0]));
                warGroundCardList.clear();
            }
        } else if (playerWarScore < computerWarScore) {
            computer.addCardsToDeck(playerWarCards);
            computer.addCardsToDeck(computerWarCards);
            if (!warGroundCardList.isEmpty()) {
                computer.addCardsToDeck(warGroundCardList.toArray(new Card[0]));
                warGroundCardList.clear();
            }
        }
    }

    public Card playerDrawCard() {
        lastCardOfPlayer = player.drawCard();
        return lastCardOfPlayer;
    }

    public Card computerDrawCard() {
        lastCardOfComputer = computer.drawCard();
        return lastCardOfComputer;
    }

    public Player getPlayer() {
        return player;
    }

    public Player getComputer() {
        return computer;
    }

    public boolean isGameOver() {
        return player.getDeckSize() == 0 || computer.getDeckSize() == 0;
    }

    public Card getLastCardOfPlayer() {
        return lastCardOfPlayer;
    }

    public Card getLastCardOfComputer() {
        return lastCardOfComputer;
    }

    @Override
    public String toString() {
        return "Game{" +
                "player=" + player +
                ", computer=" + computer +
                ", deck=" + deck +
                ", lastCardOfPlayer=" + lastCardOfPlayer +
                ", lastCardOfComputer=" + lastCardOfComputer +
                '}';
    }
}
