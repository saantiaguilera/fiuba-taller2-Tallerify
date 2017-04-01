package com.u.tallerify.view.abstracts.internals;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.u.tallerify.R;
import com.u.tallerify.utils.FrescoImageController;

/**
 * Created by saguilera on 3/15/17.
 */
public class FixedListChildView extends LinearLayout {

    private @NonNull SimpleDraweeView imageView;
    private @NonNull TextView titleView;

    public FixedListChildView(final Context context) {
        this(context, null);
    }

    public FixedListChildView(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FixedListChildView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        inflate(context, R.layout.view_music_player_next_song, this);

        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
            getResources().getDimensionPixelSize(R.dimen.view_music_player_next_song_height)));
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);

        imageView = (SimpleDraweeView) findViewById(R.id.view_music_player_next_song_image);
        titleView = (TextView) findViewById(R.id.view_music_player_next_song_name);
    }

    public void setImageUrl(@NonNull String url) {
        FrescoImageController.create()
            .load(url)
            .cacheChoice(ImageRequest.CacheChoice.SMALL)
            .into(imageView);
    }

    public void setTitle(@NonNull String name) {
        titleView.setText(name);
    }

}
