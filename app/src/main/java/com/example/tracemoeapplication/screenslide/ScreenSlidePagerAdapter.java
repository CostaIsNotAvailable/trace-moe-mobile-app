package com.example.tracemoeapplication.screenslide;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.tracemoeapplication.dtos.MatchDto;
import com.example.tracemoeapplication.dtos.MatchListDto;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ScreenSlidePagerAdapter extends FragmentStateAdapter {
    private MatchListDto matchList;

    public ScreenSlidePagerAdapter(ScreenSlidePagerActivity screenSlidePagerActivity){
        super(screenSlidePagerActivity);
        matchList = screenSlidePagerActivity.getMatchList();
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        List<MatchDto> matches = new ArrayList(matchList.getResult());
        MatchDto match = matches.get(position);
        return new ScreenSlidePageFragment(match);
    }

    @Override
    public int getItemCount() {
        return matchList.getResult().size();
    }
}
