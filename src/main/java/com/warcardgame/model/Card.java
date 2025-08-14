package com.warcardgame.model;

import java.io.Serializable;

public class Card implements Serializable {

    private final String suit;

    private final int value;

    public Card(String suit, int value) {
        this.suit = suit;
        this.value = value;
    }

    public String getSuit() {
        return suit;
    }

    public int getValue() {
        return value;
    }

    public String getImagePath() {
        return "/images/" + value + "_of_" + suit + ".png";
    }

    @Override
    public String toString() {
        return "Card{" +
                "suit='" + suit + '\'' +
                ", value=" + value +
                '}';
    }
}