package com.example.midterm;

/**
 * This method is called from the MyRecycAdapter. Each book item in the recycler view
 * can be clicked on. When the book is clicked on, a populated book object is sent to
 * the ListActivity and show_details_fragment where the user will can see all details
 * about book stored in the database
 */
interface myAdapterClickListener {
    void cardItemClicked(Book book);
}
