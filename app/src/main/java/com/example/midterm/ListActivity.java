package com.example.midterm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

/**
 * Activity to list all books in database.
 */

public class ListActivity extends AppCompatActivity implements myAdapterClickListener{

    BookDatabase bookDB;
    List<Book> bookList;
    RecyclerView rclView;
    Spinner sortSpinner;
    ImageButton btnHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        // Get copy of books from db instance
        bookDB = BookDatabase.getInstance(this);
        bookList = bookDB.bookDAO().getBookList();

        // Set up spinner that will display options for sorting book list
        sortSpinner = findViewById(R.id.sortSpinner);
        String[] sortByChoices = new String[]{"Title", "Author"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, sortByChoices);
        sortSpinner.setAdapter(spinnerAdapter);

        // Set up recycler view
        rclView = findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rclView.setLayoutManager(linearLayoutManager);

        // Spinner to select how books will be sorted for display
        sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                // Snap helper, helps to center recycler view items in view
                SnapHelper snapHelper = new LinearSnapHelper();
                rclView.setOnFlingListener(null);
                snapHelper.attachToRecyclerView(rclView);
                switch (position){
                    case 0:
                        bookList = bookDB.bookDAO().getBookListByTitle();
                        break;
                    case 1:
                        bookList = bookDB.bookDAO().getBookListByAuthor();
                        break;
                }

                // Set recycler view based on switch selection
                rclView.setAdapter(new MyRecycAdapter(bookList, ListActivity.this::cardItemClicked));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Button to take user to home page
        btnHome = findViewById(R.id.btnHome);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code for how to start new activity and clear all previous from:
                // https://stackoverflow.com/questions/6330260/finish-all-previous-activities
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

    }

    // Callback method that will run when users clicks on book in recycler view
    // Custom dialog will appear, populated with details about selected book
    @Override
    public void cardItemClicked(Book book) {
        // Create dialog and set book detail fields
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.show_details_dialog);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        TextView title = dialog.findViewById(R.id.relTitle);
        title.setText(book.getTitle());

        TextView genre = dialog.findViewById(R.id.relGenre);
        genre.setText(book.getGenre().toString());

        TextView author = dialog.findViewById(R.id.relAuthor);
        author.setText(book.getAuthor());

        TextView yearPub = dialog.findViewById(R.id.relYearPub);
        yearPub.setText(String.valueOf(book.getYearPublished()));

        TextView description = dialog.findViewById(R.id.relDescription);
        description.setText(book.getDescription());

        TextView pageCout = dialog.findViewById(R.id.relPageCount);
        pageCout.setText(String.valueOf(book.getPageCount()));

        TextView lastRead = dialog.findViewById(R.id.relLastRead);
        lastRead.setText(book.getLastRead());

        RatingBar ratingbar = dialog.findViewById(R.id.ratingBar);
        ratingbar.setRating(book.getRating());

        // Button that will dismiss dialog
        ImageButton btnBack = dialog.findViewById(R.id.btnClose);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();

        // Button to take user to EditBookActivity to edit book details
        Button editButton = dialog.findViewById(R.id.btnEdit);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListActivity.this, EditBookActivity.class);
                intent.putExtra("EditBook", book);
                ListActivity.this.startActivity(intent);
            }
        });

        // Button to delete currently viewed book
        Button deleteButton = dialog.findViewById(R.id.btnDelete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create confirm delete dialog
                Dialog dialog = new Dialog(ListActivity.this);
                dialog.setContentView(R.layout.delete_book_dialog);

                // Confirm button that will delete book from database and return user to HomeActivity
                Button btnConfirm = dialog.findViewById(R.id.btnConfirm);
                btnConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bookDB.bookDAO().deleteBook(book);
                        // Code for how to start new activity and clear all previous from:
                        // https://stackoverflow.com/questions/6330260/finish-all-previous-activities
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                });

                // Cancel button that will take cancel dialog and take user back book details dialog
                Button btnCancel = dialog.findViewById(R.id.btnCancel);
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });

                dialog.show();
            }
        });
    }
}