package com.example.midterm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Default application activity. User is prompted with two buttons: view the list,
 * or enter a new book.
 */

public class HomeActivity extends AppCompatActivity {

    CardView startViewList, startNewBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        startViewList = findViewById(R.id.startViewList);
        startNewBook = findViewById(R.id.startAddBook);

        // Button to start activity that will list all books within database
        startViewList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ListActivity.class);
                HomeActivity.this.startActivity(intent);
            }
        });

        // Button to start activity where user can enter a new book into the database
        startNewBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, AddBookActivity.class);
                HomeActivity.this.startActivity(intent);
            }
        });
    }
}