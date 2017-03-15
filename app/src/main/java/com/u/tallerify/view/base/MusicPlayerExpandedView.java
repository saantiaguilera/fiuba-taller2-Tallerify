package com.u.tallerify.view.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.u.tallerify.R;

/**
 * Created by saguilera on 3/14/17.
 */

public class MusicPlayerExpandedView extends OverscrollScrollView {

    private @NonNull SimpleDraweeView expandImage;
    private @NonNull ProgressBar expandTrackBar;
    private @NonNull TextView expandTrackTime;
    private @NonNull TextView expandTrackTimeLeft;
    private @NonNull TextView expandTrackName;
    private @NonNull TextView expandTrackArtist;
    private @NonNull ImageView expandPreviousTrack;
    private @NonNull ImageView expandNextTrack;
    private @NonNull ImageView expandPlayPause;
    private @NonNull ProgressBar expandVolumeBar;

    public MusicPlayerExpandedView(final Context context) {
        this(context, null);
    }

    public MusicPlayerExpandedView(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MusicPlayerExpandedView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        inflate(context, R.layout.view_music_player_expanded, this);

        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT));
        setFillViewport(true);

        expandImage = (SimpleDraweeView) findViewById(R.id.view_music_player_expanded_image);
        expandNextTrack = (ImageView) findViewById(R.id.view_music_player_expanded_next_track);
        expandPlayPause = (ImageView) findViewById(R.id.view_music_player_expanded_play_pause);
        expandPreviousTrack = (ImageView) findViewById(R.id.view_music_player_expanded_previous_track);
        expandTrackArtist = (TextView) findViewById(R.id.view_music_player_expanded_track_artist);
        expandTrackName = (TextView) findViewById(R.id.view_music_player_expanded_track_name);
        expandTrackBar = (ProgressBar) findViewById(R.id.view_music_player_expanded_track_progress);
        expandTrackTime = (TextView) findViewById(R.id.view_music_player_expanded_track_time);
        expandTrackTimeLeft = (TextView) findViewById(R.id.view_music_player_expanded_track_time_left);
        expandVolumeBar = (ProgressBar) findViewById(R.id.view_music_player_expanded_volume);
    }

}
