package com.example.junio.captaweb.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.junio.captaweb.R;
import com.example.junio.captaweb.holder.BookViewHolder;
import com.example.junio.captaweb.model.Book;

import java.util.List;

/**
 * Created by junio on 01/06/16.
 */
public class BookAdapter extends RecyclerView.Adapter<BookViewHolder> {
    LayoutInflater inflater;
    Context c;
    List<Book> mList;
    public BookAdapter(Context c, List<Book> l){
        inflater=LayoutInflater.from(c);
        mList=l;
    }

    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v= inflater.inflate(R.layout.item_list_book,parent,false);
        BookViewHolder bvh=new BookViewHolder(v);


        return bvh;
    }

    @Override
    public void onBindViewHolder(BookViewHolder holder, int position) {

        holder.id.setText(String.valueOf(mList.get(position).getId()));
        holder.title.setText(mList.get(position).getTitle());
        holder.author.setText(mList.get(position).getAuthor());


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
