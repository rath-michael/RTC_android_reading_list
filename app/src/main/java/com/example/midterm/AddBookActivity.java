package com.example.midterm;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * This activity handles all operations necessary for the user to add a new book to the database.
 * User is prompted with all fields necessary for a new book. When the user is complete, a save
 * button can be hit that will validate the users entries. If all fields are complete, the book will
 * be saved. If any fields are incomplete, the user will be shown which fields are invalid.
 */

public class AddBookActivity extends AppCompatActivity {
    BookDatabase bookDb;
    Book newBook;

    EditText title;
    AutoCompleteTextView actvGenre;
    EditText author;
    EditText yearPublished;
    EditText description;
    EditText pageCount;
    EditText lastRead;
    RatingBar rating;
    Button btnSaveNewBook;
    Button btnGoBack;

    final Calendar myCalendar = Calendar.getInstance();

    // Standard onCreate method, initializes all views that user needs to enter new book
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        newBook = new Book();

        // Set variable inputs needed for title, author, and description
        title = findViewById(R.id.etTitle);
        author = findViewById(R.id.etAuthor);
        description = findViewById(R.id.etDescription);

        // Set up genre input for new book
        ArrayAdapter<Genre> adapter = new ArrayAdapter<>(
                this, android.R.layout.select_dialog_singlechoice, Genre.values());
        actvGenre = findViewById(R.id.actvGenre);
        actvGenre.setThreshold(0);
        actvGenre.setAdapter(adapter);
        actvGenre.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                newBook.setGenre(Genre.values()[position]);
            }
        });

        // Set up year published input for new book
        yearPublished = findViewById(R.id.etPublishDate);
        yearPublished.setInputType(InputType.TYPE_CLASS_NUMBER);

        // Set page count input for new book
        pageCount = findViewById(R.id.etPageCount);
        pageCount.setInputType(InputType.TYPE_CLASS_NUMBER);

        // Set up last read date input for new book
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "MM/dd/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                lastRead.setText(sdf.format(myCalendar.getTime()));
            }
        };

        lastRead = findViewById(R.id.etLastRead);
        lastRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddBookActivity.this, date,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        // Set up rating input for new book
        rating = findViewById(R.id.ratingBar);

        // Button to go back to Home Activity
        btnGoBack = findViewById(R.id.btnBack);
        btnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Button to save new book
        btnSaveNewBook = findViewById(R.id.btnSaveNewBook);
        btnSaveNewBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookDb = BookDatabase.getInstance(AddBookActivity.this);

                // Get all user input fields to be validated
                int[] ids = new int[]{
                        R.id.etTitle,
                        R.id.etAuthor,
                        R.id.etPublishDate,
                        R.id.etPageCount,
                        R.id.etLastRead,
                        R.id.etDescription
                };

                // Validate method called on all fields necessary for new book
                // If true, save new book to database and show confirmation dialog
                // If false, toast displaying error message for user
                if (!ValidateFields(ids)){
                    newBook.setTitle(title.getText().toString());
                    newBook.setGenre(Genre.valueOf(actvGenre.getText().toString()));
                    newBook.setAuthor(author.getText().toString());
                    newBook.setYearPublished(Integer.parseInt(yearPublished.getText().toString()));
                    newBook.setDescription(description.getText().toString());
                    newBook.setPageCount(Integer.parseInt(pageCount.getText().toString()));
                    newBook.setLastRead(String.valueOf(myCalendar.get(Calendar.YEAR)));
                    newBook.setRating(rating.getRating());
                    bookDb.bookDAO().insertBook(newBook);

                    // Create confirm new book dialog
                    Dialog dialog = new Dialog(AddBookActivity.this);
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
                    Toast.makeText(AddBookActivity.this, "Please enter all fields before continuing",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Idea for validating multiple views at one time from:
    // https://stackoverflow.com/questions/17076239/validating-multiple-edittexts
    // Method that takes array of input views and validates that they are not empty
    // If any view text field is empty, return an error and stop the user from saving new book
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
