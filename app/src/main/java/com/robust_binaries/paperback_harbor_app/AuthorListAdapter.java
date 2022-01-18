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

public class AuthorListAdapter extends ArrayAdapter<AuthorListModel> {

    public AuthorListAdapter(@NonNull Context context, ArrayList<AuthorListModel> authorListModelArrayList) {
        super(context, R.layout.author_list_item,authorListModelArrayList );
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        AuthorListModel authorListModel = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.author_list_item,parent,false);
        }

        ImageView author_image = convertView.findViewById(R.id.author_image);
        TextView author_name = convertView.findViewById(R.id.author_name);

        author_image.setImageResource(authorListModel.imageId);
        author_name.setText(authorListModel.author_name);
        return convertView;
    }
}
