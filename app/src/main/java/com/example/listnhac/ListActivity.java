package com.example.listnhac;

import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import androidx.viewpager2.widget.ViewPager2;

public class ListActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private Button btnPlaylist, btnSavedList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.viewPager);
        btnPlaylist = findViewById(R.id.btnPlaylist);
        btnSavedList = findViewById(R.id.btnSavedList);


        ViewPagerAdapter adapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(adapter);


        btnPlaylist.setOnClickListener(v -> viewPager.setCurrentItem(0));
        btnSavedList.setOnClickListener(v -> viewPager.setCurrentItem(1));


        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    btnPlaylist.setEnabled(false);
                    btnSavedList.setEnabled(true);
                } else {
                    btnPlaylist.setEnabled(true);
                    btnSavedList.setEnabled(false);
                }
            }
        });
    }
}

