package com.example.midterm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import java.util.Calendar;

/**
 * This activity handles all operations necessary for the user to edit a book in the database.
 * This activity is passed a book from the sending List Activity. This book will automotically
 * populate all the fields. The user can edit any fields before hitting a save button. When the
 * button is pressed, all fields will be validated. If validation passes, the book will be saved
 * and the user will be sent to the Home Activity. If validation fails, the user will be shown
 * which fields are invalid.
 */

public class EditBookActivity extends AppCompatActivity {
    BookDatabase bookDb;

    EditText title;
    AutoCompleteTextView actvGenre;
    EditText author;
    EditText yearPublished;
    EditText description;
    EditText pageCount;
    EditText lastRead;
    RatingBar rating;
    Button btnSaveEdit;
    Button btnGoBack;

    final Calendar myCalendar = Calendar.getInstance();

    // Standard onCreate method, initializes all views that user needs to edit book
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_book);

        // Get book to be edited from previous activity
        Book bookToEdit = (Book)getIntent().getSerializableExtra("EditBook");

        // Set up title input from edited book
        title = findViewById(R.id.etTitle);
        title.setText(bookToEdit.getTitle());

        // Set up genre input from edited book
        ArrayAdapter<Genre> adapter = new ArrayAdapter<>(
                this, android.R.layout.select_dialog_singlechoice, Genre.values());
        actvGenre = findViewById(R.id.actvGenre);
        actvGenre.setText(bookToEdit.getGenre().toString());
        actvGenre.setThreshold(0);
        actvGenre.setAdapter(adapter);
        actvGenre.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                bookToEdit.setGenre(Genre.values()[position]);
            }
        });

        // Set up author input from edited book
        author = findViewById(R.id.etAuthor);
        author.setText(bookToEdit.getAuthor());

        // Set up year published input from edited book
        yearPublished = findViewById(R.id.etPublishDate);
        yearPublished.setInputType(InputType.TYPE_CLASS_NUMBER);
        yearPublished.setText(String.valueOf(bookToEdit.getYearPublished()));

        // Set up description input from edited book
        description = findViewById(R.id.etDescription);
        description.setText(bookToEdit.getDescription());

        // Set page count from edited book
        pageCount = findViewById(R.id.etPageCount);
        pageCount.setInputType(InputType.TYPE_CLASS_NUMBER);
        pageCount.setText(String.valueOf(bookToEdit.getPageCount()));

        // Set up last read date from edited book
        lastRead = findViewById(R.id.etLastRead);
        lastRead.setInputType(InputType.TYPE_CLASS_NUMBER);
        lastRead.setText(String.valueOf(bookToEdit.getLastRead()));

        // Set up rating input from edited book
        rating = findViewById(R.id.ratingBar);
        rating.setRating(bookToEdit.getRating());

        // Button to go back to List Activity
        btnGoBack = findViewById(R.id.btnBack);
        btnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Button to save edited book
        btnSaveEdit = findViewById(R.id.btnSaveEdit);
        btnSaveEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookDb = BookDatabase.getInstance(EditBookActivity.this);

                // Get all user input fields to be validated
                int[] ids = new int[]{
                        R.id.etTitle,
                        R.id.etAuthor,
                        R.id.etPublishDate,
                        R.id.etPageCount,
                        R.id.etLastRead,
                        R.id.etDescription
                };

                // Validate method called on all fields necessary for edited book
                // If true, save book to database and show confirmation dialog
                // If false, toast displaying error message for user
                if (!ValidateFields(ids)){
                    bookToEdit.setTitle(title.getText().toString());
                    bookToEdit.setGenre(Genre.valueOf(actvGenre.getText().toString()));
                    bookToEdit.setAuthor(author.getText().toString());
                    bookToEdit.setYearPublished(Integer.parseInt(yearPublished.getText().toString()));
                    bookToEdit.setDescription(description.getText().toString());
                    bookToEdit.setPageCount(Integer.parseInt(pageCount.getText().toString()));
                    bookToEdit.setLastRead(String.valueOf(myCalendar.get(Calendar.YEAR)));
                    bookToEdit.setRating(rating.getRating());
                    bookDb.bookDAO().updateBook(bookToEdit);

                    // Create confirm edit dialog
                    Dialog dialog = new Dialog(EditBookActivity.this);
                    dialog.setContentView(R.layout.edit_book_dialog);

                    // Confirm button within dialog to end all current activities and go back to HomeActivity
                    Button btnConfirm = dialog.findViewById(R.id.btnConfirm);
                    btnConfirm.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // Code for how to start new activity and clear all previous from:
                            // https://stackoverflow.com/questions/6330260/finish-all-previous-activities
                            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                    });
                    dialog.show();
                }

                // If any inputs invalid, display error toast message
                else {
                    Toast.makeText(EditBookActivity.this, "Please enter all fields before continuing",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Idea for validating multiple views at one time from:
    // https://stackoverflow.com/questions/17076239/validating-multiple-edittexts
    // Method that takes array of input views and validates that they are not empty
    // If any view text field is empty, return an error and stop the user from saving book
    public boolean ValidateFields(int[] ids){

        boolean isEmpty = false;

        // Check EditText views
        for (int id : ids){
            EditText et = findViewById(id);
            if (TextUtils.isEmpty(et.getText().toString())){
                et.setError("Must enter value");
                isEmpty = true;
            }
        }

        // Check AutoCompleteTextView view
        if (TextUtils.isEmpty(actvGenre.getText().toString())){
            actvGenre.setError("Must enter value");
            isEmpty = true;
        }

        return isEmpty;
    }
}