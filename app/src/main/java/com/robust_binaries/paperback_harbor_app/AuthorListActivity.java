package com.robust_binaries.paperback_harbor_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import java.util.ArrayList;
import com.robust_binaries.paperback_harbor_app.databinding.ActivityAuthorListBinding;

public class AuthorListActivity extends AppCompatActivity {



    ActivityAuthorListBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAuthorListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        int[] imageId = {R.drawable.jk,R.drawable.abdul_kalam,R.drawable.james_clear,R.drawable.jk,R.drawable.abdul_kalam,R.drawable.james_clear,R.drawable.jk,R.drawable.abdul_kalam,R.drawable.james_clear};
        String[] authorNames = {"J.K.Rowling","A.P.J.Abdul Kalam","James Clear","J.K.Rowling","A.P.J.Abdul Kalam","James Clear","J.K.Rowling","A.P.J.Abdul Kalam","James Clear"};

        ArrayList<AuthorListModel> authorListModelArrayList = new ArrayList<>();

        for(int i =0; i < imageId.length; i++){
            AuthorListModel authorListModel = new AuthorListModel(authorNames[i],imageId[i]);
            authorListModelArrayList.add(authorListModel);
        }

        AuthorListAdapter authorListAdapter = new AuthorListAdapter(AuthorListActivity.this,authorListModelArrayList);
        binding.authorListView.setAdapter(authorListAdapter);

        binding.authorListView.setClickable(true);
        binding.authorListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(AuthorListActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}

