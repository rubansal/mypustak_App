package com.mypushtak.app.Singleton;

public class WishlistData {
private int book_id;
private int user_id;
private int wl_id;
private int price;
private String title;
private String thumb;
private String author;
private String category;

    public WishlistData() {
    }

    public int getBook_id() {

        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getWl_id() {
        return wl_id;
    }

    public void setWl_id(int wl_id) {
        this.wl_id = wl_id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public WishlistData(int book_id, int user_id, int wl_id, int price, String title, String thumb, String author, String category) {

        this.book_id = book_id;
        this.user_id = user_id;
        this.wl_id = wl_id;
        this.price = price;
        this.title = title;
        this.thumb = thumb;
        this.author = author;
        this.category = category;
    }
}
