package com.robust_binaries.paperback_harbor_app;

public class BookCardModel {
    int imageId;
    String bookTitle;
    String authorName;
    String supportingText;

    public BookCardModel(int imageId, String bookTitle, String authorName, String supportingText) {
        this.imageId = imageId;
        this.bookTitle = bookTitle;
        this.authorName = authorName;
        this.supportingText = supportingText;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getSupportingText() {
        return supportingText;
    }

    public void setSupportingText(String supportingText) {
        this.supportingText = supportingText;
    }
}
