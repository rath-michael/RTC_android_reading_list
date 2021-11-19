package com.example.midterm;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

/**
 * Book class / Book database entity
 */

@Entity
public class Book implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    public int Id;

    @ColumnInfo(name = "Title")
    private String Title;

    @ColumnInfo(name = "Genre")
    private Genre Genre;

    @ColumnInfo(name = "Author")
    private String Author;

    @ColumnInfo(name = "YearPublished")
    private int YearPublished;

    @ColumnInfo(name = "Description")
    private String Description;

    @ColumnInfo(name = "PageCount")
    private int PageCount;

    @ColumnInfo(name = "LastRead")
    private String LastRead;

    @ColumnInfo(name = "Rating")
    private float Rating;

    public Book(){

    }

    public Book(String title, Genre genre, String author, int yearPublished, String description,
                int pageCount, String lastRead, float rating){
        Title = title;
        Genre = genre;
        Author = author;
        YearPublished = yearPublished;
        Description = description;
        PageCount = pageCount;
        LastRead = lastRead;
        Rating = rating;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public Genre getGenre() {
        return Genre;
    }

    public void setGenre(Genre genre) {
        Genre = genre;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public int getYearPublished() {
        return YearPublished;
    }

    public void setYearPublished(int yearPublished) {
        YearPublished = yearPublished;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getPageCount() {
        return PageCount;
    }

    public void setPageCount(int pageCount) {
        PageCount = pageCount;
    }

    public String getLastRead() {
        return LastRead;
    }

    public void setLastRead(String lastRead) {
        LastRead = lastRead;
    }

    public float getRating() {
        return Rating;
    }

    public void setRating(float rating) {
        Rating = rating;
    }
}
