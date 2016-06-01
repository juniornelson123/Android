package com.example.junio.captaweb.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.junio.captaweb.R;

/**
 * Created by junio on 01/06/16.
 */
public class BookViewHolder extends RecyclerView.ViewHolder{

    public TextView id,title,author;
    public BookViewHolder(View itemView) {
        super(itemView);


        id=(TextView) itemView.findViewById(R.id.id_book);
        title=(TextView) itemView.findViewById(R.id.title_book);
        author=(TextView) itemView.findViewById(R.id.author_book);
    }
}
