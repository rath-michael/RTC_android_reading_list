package com.example.midterm;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * RecyclerView adapter which sets up each individual view within the RecyclerView
 */

public class MyRecycAdapter extends RecyclerView.Adapter<MyRecycAdapter.handler>{
    private myAdapterClickListener listener = null;
    private List<Book> bookList;

    public MyRecycAdapter(List<Book> bookList, myAdapterClickListener listener){
        this.bookList = bookList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public handler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recycler_view_item, parent, false);
        return new handler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull handler holder, int position) {
        holder.title.setText(bookList.get(position).getTitle());
        holder.author.setText(bookList.get(position).getAuthor());

    }

    @Override
    public int getItemCount() { return bookList.size(); }

    class handler extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView title;
        private TextView author;

        public handler(@NonNull View itemView){
            super(itemView);
            title  = itemView.findViewById(R.id.txtTitle);
            author = itemView.findViewById(R.id.txtAuthor);
            itemView.setOnClickListener(this);
        }

        // Use callback method to send selected book to ListActivity, which will
        // be displayed in show_details_dialog
        @Override
        public void onClick(View v) {
            listener.cardItemClicked(bookList.get(getAdapterPosition()));
        }
    }
}
