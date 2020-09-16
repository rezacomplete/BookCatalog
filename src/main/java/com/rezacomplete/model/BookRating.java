package com.rezacomplete.model;

public class BookRating {

    private String bookId;
    private double rating;

    public BookRating() {
    }

    public BookRating(String bookId, double rating) {
        this.bookId = bookId;
        this.rating = rating;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
