package com.warcardgame.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

public class Player implements Serializable {

    private final String name;

    private int score;

    private final Queue<Card> deck;

    public Player(String name) {
        this.name = name;
        this.score = 0;
        this.deck = new LinkedList<>();
    }

    public void addCard(Card card) {
        deck.add(card);
    }

    public void addCardsToDeck(Card[] cards) {
        Collections.addAll(deck, cards);
    }

    public Card drawCard() {
        return deck.poll();
    }

    public int getDeckSize() {
        return deck.size();
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public void increaseScore(int points) {
        this.score += points;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", score=" + score +
                ", deck=" + deck +
                '}';
    }
}