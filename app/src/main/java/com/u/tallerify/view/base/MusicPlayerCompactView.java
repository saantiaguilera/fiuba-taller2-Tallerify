package com.u.tallerify.view.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.u.tallerify.R;

/**
 * Created by saguilera on 3/14/17.
 */

public class MusicPlayerCompactView extends LinearLayout {

    private @NonNull SimpleDraweeView compactImage;
    private @NonNull TextView compactTitle;
    private @NonNull ImageView compactPlay;
    private @NonNull ImageView compactNext;

    public MusicPlayerCompactView(final Context context) {
        this(context, null);
    }

    public MusicPlayerCompactView(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MusicPlayerCompactView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        inflate(context, R.layout.view_music_player_compact, this);

        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
            getResources().getDimensionPixelSize(R.dimen.view_music_player_height)));
        setOrientation(HORIZONTAL);

        compactImage = (SimpleDraweeView) findViewById(R.id.view_music_player_compact_image);
        compactTitle = (TextView) findViewById(R.id.view_music_player_compact_name);
        compactNext = (ImageView) findViewById(R.id.view_music_player_compact_next_track);
        compactPlay = (ImageView) findViewById(R.id.view_music_player_compact_play_pause);
    }

}
