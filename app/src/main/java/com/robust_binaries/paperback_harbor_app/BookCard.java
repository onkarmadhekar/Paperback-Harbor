package com.robust_binaries.paperback_harbor_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.robust_binaries.paperback_harbor_app.databinding.ActivityBookCardBinding;

import java.util.ArrayList;

public class BookCard extends AppCompatActivity {

    ActivityBookCardBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBookCardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        int[] imageId = {R.drawable.atomic_habits,R.drawable.harry_potter_,R.drawable.thinkandgrowrich,R.drawable.atomic_habits,R.drawable.harry_potter_,R.drawable.thinkandgrowrich,R.drawable.atomic_habits,R.drawable.harry_potter_,R.drawable.thinkandgrowrich};
        String[] bookTitles = {"Atomic Habits: An Easy and Proven Way to Build Good Habits and Break Bad Ones","Harry Potter- Magic World","Think and Grow Rich -Unleash the secret of success","Atomic Habits: An Easy and Proven Way to Build Good Habits and Break Bad Ones","Harry Potter- Magic World","Think and Grow Rich -Unleash the secret of success","Atomic Habits: An Easy and Proven Way to Build Good Habits and Break Bad Ones","Harry Potter- Magic World","Think and Grow Rich -Unleash the secret of success"};
        String[] authorNames = {"By James Clear", "By J.K. Rowling","By Napoleon Hill","By James Clear", "By J.K. Rowling","By Napoleon Hill","By James Clear", "By J.K. Rowling","By Napoleon Hill"};
        String[] supportingText = {"The #1 New York Times bestseller. Over 3 million copies sold!Tiny Changes, Remarkable ResultsNo matter your goals, Atomic Habits offers a proven framework for improving—every day.","arry Potter is a series of seven fantasy novels written by British author J. K. Rowling. The novels chronicle the lives of a young wizard, Harry Potter, and his friends Hermione Granger and Ron Weasley, all of whom are students at Hogwarts School of Witchcraft and Wizardry.","Think and Grow Rich is a book written by Napoleon Hill in 1937 and promoted as a personal development and self-improvement book. He claimed to be inspired by a suggestion from business magnate and later-philanthropist Andrew Carnegie.","The #1 New York Times bestseller. Over 3 million copies sold!Tiny Changes, Remarkable ResultsNo matter your goals, Atomic Habits offers a proven framework for improving—every day.","arry Potter is a series of seven fantasy novels written by British author J. K. Rowling. The novels chronicle the lives of a young wizard, Harry Potter, and his friends Hermione Granger and Ron Weasley, all of whom are students at Hogwarts School of Witchcraft and Wizardry.","Think and Grow Rich is a book written by Napoleon Hill in 1937 and promoted as a personal development and self-improvement book. He claimed to be inspired by a suggestion from business magnate and later-philanthropist Andrew Carnegie.","The #1 New York Times bestseller. Over 3 million copies sold!Tiny Changes, Remarkable ResultsNo matter your goals, Atomic Habits offers a proven framework for improving—every day.","arry Potter is a series of seven fantasy novels written by British author J. K. Rowling. The novels chronicle the lives of a young wizard, Harry Potter, and his friends Hermione Granger and Ron Weasley, all of whom are students at Hogwarts School of Witchcraft and Wizardry.","Think and Grow Rich is a book written by Napoleon Hill in 1937 and promoted as a personal development and self-improvement book. He claimed to be inspired by a suggestion from business magnate and later-philanthropist Andrew Carnegie."};

        ArrayList<BookCardModel> bookCardModelArrayList = new ArrayList<>();

        for(int i = 0; i < imageId.length; i++){
            BookCardModel bookCardModel = new BookCardModel(imageId[i],bookTitles[i],authorNames[i],supportingText[i]);
            bookCardModelArrayList.add(bookCardModel);
        }

        BookCardAdapter bookCardAdapter = new BookCardAdapter(BookCard.this,bookCardModelArrayList);
        binding.bookCardListView.setAdapter(bookCardAdapter);

        binding.bookCardListView.setClickable(true);
        binding.bookCardListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(BookCard.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}