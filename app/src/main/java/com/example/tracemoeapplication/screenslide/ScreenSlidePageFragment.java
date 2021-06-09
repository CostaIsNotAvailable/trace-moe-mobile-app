package com.example.tracemoeapplication.screenslide;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.tracemoeapplication.R;
import com.example.tracemoeapplication.dtos.MatchDto;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.concurrent.TimeUnit;

public class ScreenSlidePageFragment extends Fragment {
    private MatchDto match;

    public ScreenSlidePageFragment(MatchDto _match){
        super();
        match = _match;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_screen_slide_page, container, false);

        //Instances
        ImageView imageView = view.findViewById(R.id.matchImageView);
        TextView matchTitleTextView = view.findViewById(R.id.matchTitleTextView);
        TextView matchEpisodeTextView = view.findViewById(R.id.matchEpisodeTextView);
        TextView matchTimeTextView = view.findViewById(R.id.matchTimeTextView);
        TextView matchSimilarityTextView = view.findViewById(R.id.matchSimilarityTextView);

        // Set view content
        Picasso.get().load(match.getImage()).into(imageView);
        matchTitleTextView.setText(match.getFilename());
        matchEpisodeTextView.setText(getString(R.string.episode_slide_text,match.getEpisode()));
        matchTimeTextView.setText(getString(R.string.time_slide_text, formatTime((long) match.getFrom()), formatTime((long) match.getTo())));
        matchSimilarityTextView.setText(getString(R.string.similarity_slide_text, formatPercentage(match.getSimilarity())));

        return view;
    }

    private String formatTime(long _seconds){
        long hours = TimeUnit.SECONDS.toHours(_seconds);
        long minutes = TimeUnit.SECONDS.toMinutes(_seconds) - TimeUnit.HOURS.toMinutes(hours);
        long seconds = _seconds - TimeUnit.MINUTES.toSeconds(TimeUnit.SECONDS.toMinutes(_seconds));

        String hoursString = hours < 10 ? "0" + hours : Long.toString(hours);
        String minutesString = minutes < 10 ? "0" + minutes : Long.toString(minutes);
        String secondsString = seconds < 10 ? "0" + seconds : Long.toString(seconds);

        return getString(R.string.time_form_text, hoursString, minutesString, secondsString);
    }

    private String formatPercentage(double _percentage){
        NumberFormat formatter = NumberFormat.getPercentInstance();
        formatter.setMinimumFractionDigits(1);
        return formatter.format(_percentage);
    }
}
