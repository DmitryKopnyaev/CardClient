package com.kopniaev.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kopniaev.model.Card;
import com.kopniaev.util.Constants;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CardRepository implements ConnectServer {
    private ObjectMapper objectMapper = new ObjectMapper();
    private Card card;
    private List<Card> cards;

    public CardRepository(int idCategory, String method) throws IOException {
        if (method.equals("GET")) {
            try (BufferedReader bufferedReader = connect(Constants.SERVER_NAME
                    + "/card/cat/" + idCategory, method, null)) {
                this.cards = objectMapper.readValue(bufferedReader, new TypeReference<ArrayList<Card>>() {
                });
            }
        } else if (method.equals("DELETE")) {
            try (BufferedReader bufferedReader = connect(Constants.SERVER_NAME
                    + "/card/" + idCategory, method, null)) {
                this.card = objectMapper.readValue(bufferedReader, Card.class);
            }
        }
    }

    public CardRepository(Card card, String method) throws IOException {
        if (method.equals("PUT")) {
            System.out.println(this.objectMapper.writeValueAsString(card));
            try (BufferedReader bufferedReader = connect(Constants.SERVER_NAME
                    + "/card", method, this.objectMapper.writeValueAsString(card))) {
                this.card = objectMapper.readValue(bufferedReader, Card.class);
            }
        }
    }

    public CardRepository(int id, Card card) throws IOException {
        try (BufferedReader bufferedReader = connect(Constants.SERVER_NAME
                + "/card/" + id, "POST", this.objectMapper.writeValueAsString(card))) {
            this.card = objectMapper.readValue(bufferedReader, Card.class);
        }
    }

    public Card getCard() {
        return this.card;
    }

    public List<Card> getCards() {
        return this.cards;
    }
}
