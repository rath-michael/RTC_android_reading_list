package com.example.midterm;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * Database DAO. Contains all methods necessary for application to access database
 */

@Dao
public interface BookDAO {
    @Query("SELECT * FROM Book")
    List<Book> getBookList();

    @Query("SELECT * FROM Book ORDER BY Title ASC")
    List<Book> getBookListByTitle();

    @Query("SELECT * FROM Book ORDER BY Author ASC")
    List<Book> getBookListByAuthor();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertBook(Book book);

    @Update
    void updateBook(Book book);

    @Delete
    void deleteBook(Book book);
}
