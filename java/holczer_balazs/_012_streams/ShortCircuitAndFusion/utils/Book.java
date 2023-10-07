package utils;

public class Book {
    
    private String author;
    private String title;
    private int pages;
    private Type type;


    public Book(String title, String author, int pages, Type type) {
        this.author = author;
        this.title = title;
        this.pages = pages;
        this.type = type;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPages() {
        return this.pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public Type getType() {
        return this.type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "{" +
            " author='" + getAuthor() + "'" +
            ", title='" + getTitle() + "'" +
            ", pages='" + getPages() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }

}
