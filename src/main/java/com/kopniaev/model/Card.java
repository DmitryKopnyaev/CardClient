package com.kopniaev.model;

import java.util.Date;
import java.util.Objects;

public class Card {
    private int id;
    private String question;
    private String answer;
    private Category category;
    private Date creationDate;

    public Card() {
    }

    public Card(String question, String answer, Category category) {
        this.question = question;
        this.answer = answer;
        this.category = category;
    }

    public Card(int id, String question, String answer, Category category, Date creationDate) {
        this.id = id;
        this.question = question;
        this.answer = answer;
        this.category = category;
        this.creationDate = creationDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return id == card.id &&
                Objects.equals(question, card.question) &&
                Objects.equals(answer, card.answer) &&
                Objects.equals(category, card.category) &&
                Objects.equals(creationDate, card.creationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, question, answer, category, creationDate);
    }

    @Override
    public String toString() {
        return "id=" + id +
                ", question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                ", category=" + category.getName() +
                ", creationDate=" + creationDate;
    }
}
