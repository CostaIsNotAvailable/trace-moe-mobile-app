package com.example.tracemoeapplication.screenslide;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.tracemoeapplication.MainActivity;
import com.example.tracemoeapplication.R;
import com.example.tracemoeapplication.dtos.MatchDto;
import com.example.tracemoeapplication.dtos.MatchListDto;

public class ScreenSlidePagerActivity extends FragmentActivity {

    private ViewPager2 viewPager;
    private FragmentStateAdapter pagerAdapter;
    private MatchListDto matchList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_slide);

        Intent intent = getIntent();

        // Instances
        matchList = (MatchListDto) intent.getSerializableExtra(MainActivity.EXTRA_MATCH_LIST);
        viewPager = findViewById(R.id.pager);
        pagerAdapter = new ScreenSlidePagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);

        viewPager.setPageTransformer(new ZoomOutPageTransformer());
    }

    public MatchListDto getMatchList() {
        return matchList;
    }
}
