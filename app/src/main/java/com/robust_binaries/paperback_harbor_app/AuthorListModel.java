package com.robust_binaries.paperback_harbor_app;

public class AuthorListModel {

    String author_name;
    //String authorDescription;
    int imageId;

    public AuthorListModel(String author_name, int imageId) {
        this.author_name = author_name;
        // this.authorDescription = authorDescription;
        this.imageId = imageId;
    }
}
