package com.u.tallerify.view.base.music_player.internal;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.u.tallerify.R;
import com.u.tallerify.utils.FrescoImageController;
import com.u.tallerify.view.RxView;
import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Created by saguilera on 3/14/17.
 */
public class MusicPlayerCompactView extends LinearLayout {

    private @NonNull SimpleDraweeView compactImage;
    private @NonNull TextView compactTitle;
    private @NonNull ImageView compactPlay;
    private @NonNull ImageView compactNext;

    @Nullable PublishSubject<Void> nextSongSubject;
    @Nullable PublishSubject<Void> playStateSubject;

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

        compactTitle.setSelected(true);
    }

    public void setPlaying() {
        compactPlay.setImageResource(R.drawable.ic_play_arrow_black_36dp);
    }

    public void setPaused() {
        compactPlay.setImageResource(R.drawable.ic_pause_black_36dp);
    }

    public void setTitle(@NonNull String title) {
        compactTitle.setText(title);
    }

    public void setImageUrl(@NonNull String url) {
        FrescoImageController.create()
            .load(url)
            .cacheChoice(ImageRequest.CacheChoice.SMALL)
            .into(compactImage);
    }

    public @NonNull Observable<Void> observeNextSongClicks() {
        if (nextSongSubject == null) {
            nextSongSubject = PublishSubject.create();
            RxView.dispatchClicks(compactNext, nextSongSubject);
        }

        return nextSongSubject;
    }

    public @NonNull Observable<Void> observePlayStateClicks() {
        if (playStateSubject == null) {
            playStateSubject = PublishSubject.create();
            RxView.dispatchClicks(compactPlay, playStateSubject);
        }

        return playStateSubject;
    }

}
