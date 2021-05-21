package com.example.newsreader;

public class News {
    String title;
    String url;
    String imageUrl;

    public News(String title, String url, String imageUrl) {
        this.title = title;
        this.url = url;
        this.imageUrl = imageUrl;
    }

    public String getUrl() {
        return url;
    }
    public String getTitle() {
        return title;
    }
    public String getImageUrl() {
        return imageUrl;
    }
}