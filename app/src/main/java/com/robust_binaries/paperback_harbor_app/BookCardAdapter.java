package com.robust_binaries.paperback_harbor_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class BookCardAdapter extends ArrayAdapter<BookCardModel> {

    public BookCardAdapter(Context context, ArrayList<BookCardModel> bookCardModelsArrayList){
        super(context,R.layout.book_card_list_item,bookCardModelsArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        BookCardModel bookCardModel = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.book_card_list_item,parent,false);
        }

        ImageView bookImage = convertView.findViewById(R.id.book_image);
        TextView bookTitle = convertView.findViewById(R.id.book_title);
        TextView authorName = convertView.findViewById(R.id.author_name);
        TextView supportingText = convertView.findViewById(R.id.supporting_text);

        bookImage.setImageResource(bookCardModel.imageId);
        bookTitle.setText(bookCardModel.bookTitle);
        authorName.setText(bookCardModel.authorName);
        supportingText.setText(bookCardModel.supportingText);

        return convertView;
    }
}
