package com.u.tallerify.entry_points;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.request.ImageRequest;
import com.u.tallerify.R;
import com.u.tallerify.model.entity.Song;
import com.u.tallerify.utils.CurrentPlay;
import com.u.tallerify.utils.PlayManager;
import com.u.tallerify.utils.PlayUtils;
import java.util.concurrent.Executors;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class PlayService extends Service {

    private static final int SERVICE_NOTIFICATION_ID = 1;

    public static final @NonNull String SERVICE_START = PlayService.class.getName() + "#start";
    public static final @NonNull String SERVICE_SONG_REPRODUCE = PlayService.class.getName() + "#reproduce";
    public static final @NonNull String SERVICE_NEXT_SONG = PlayService.class.getName() + "#next_song";

    private @Nullable Subscription subscription;

    @Nullable Song song;

    @Override
    public void onCreate() {
        super.onCreate();
    }
 
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.getAction().equals(SERVICE_START)) {
            observeSong();
        } else if (intent.getAction().equals(SERVICE_SONG_REPRODUCE)) {
            PlayUtils.playState();
        } else if (intent.getAction().equals(SERVICE_NEXT_SONG)) {
            PlayUtils.forward();
        }

        showNotification();

        return START_STICKY;
    }

    private void observeSong() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }

        subscription = CurrentPlay.instance().observeCurrentPlay()
            .observeOn(Schedulers.computation())
            .subscribeOn(Schedulers.computation())
            .subscribe(new Action1<CurrentPlay>() {
                @Override
                public void call(final CurrentPlay currentPlay) {
                    if (currentPlay.hasValueChanged(CurrentPlay.KEY_SONG) ||
                            currentPlay.hasValueChanged(CurrentPlay.KEY_PLAYSTATE)) {
                        showNotification();
                    }

                    notifyManager(currentPlay);
                }
            });
    }

    void notifyManager(@NonNull CurrentPlay newPlay) {
        if (newPlay.hasValueChanged(CurrentPlay.KEY_PLAYSTATE) &&
                !newPlay.hasValueChanged(CurrentPlay.KEY_SONG)) {
            switch (newPlay.playState()) {
                case PLAYING:
                    PlayManager.instance().resume();
                    break;
                case PAUSED:
                    PlayManager.instance().pause();
                    break;
            }
        }

        if (newPlay.hasValueChanged(CurrentPlay.KEY_SONG)) {
            PlayManager.instance().start(this);
        }

        if (newPlay.hasValueChanged(CurrentPlay.KEY_TIME) &&
                !newPlay.hasValueChanged(CurrentPlay.KEY_SONG)) {
            PlayManager.instance().seek(newPlay.time());
        }
    }
 
    void showNotification() {
        song = CurrentPlay.instance().song();

        Fresco.getImagePipeline().fetchDecodedImage(
                ImageRequest.fromUri(Uri.parse(song.pictures().get(0))), this
            )
            .subscribe(new BaseBitmapDataSubscriber() {
                @Override
                protected void onNewResultImpl(@javax.annotation.Nullable final Bitmap bitmap) {
                    Song thisSong = CurrentPlay.instance().song();
                    if (thisSong != song) return;

                    RemoteViews compactView = new RemoteViews(getPackageName(),
                        R.layout.notification_compact);
                    RemoteViews expandedView = new RemoteViews(getPackageName(),
                        R.layout.notification_expanded);

                    expandedView.setImageViewBitmap(R.id.notification_expanded_album_art, bitmap);
                    compactView.setImageViewBitmap(R.id.notification_compact_album_art, bitmap);

                    Intent notificationIntent = new Intent(PlayService.this, MainActivity.class);
                    notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    PendingIntent pendingIntent = PendingIntent.getActivity(PlayService.this, 0,
                        notificationIntent, 0);

                    Intent playIntent = new Intent(PlayService.this, PlayService.class);
                    playIntent.setAction(SERVICE_SONG_REPRODUCE);
                    PendingIntent pendingPlayIntent = PendingIntent.getService(PlayService.this, 0,
                        playIntent, 0);

                    Intent nextIntent = new Intent(PlayService.this, PlayService.class);
                    nextIntent.setAction(SERVICE_NEXT_SONG);
                    PendingIntent pendingNextIntent = PendingIntent.getService(PlayService.this, 0,
                        nextIntent, 0);

                    compactView.setOnClickPendingIntent(R.id.notification_compact_play, pendingPlayIntent);
                    expandedView.setOnClickPendingIntent(R.id.notification_expanded_play, pendingPlayIntent);

                    compactView.setOnClickPendingIntent(R.id.notification_compact_next, pendingNextIntent);
                    expandedView.setOnClickPendingIntent(R.id.notification_expanded_next, pendingNextIntent);

                    switch (CurrentPlay.instance().playState()) {
                        case PAUSED:
                            compactView.setImageViewResource(R.id.notification_compact_play,
                                R.drawable.ic_play_arrow_black_36dp);
                            expandedView.setImageViewResource(R.id.notification_expanded_play,
                                R.drawable.ic_play_arrow_black_36dp);
                            break;
                        case PLAYING:
                            compactView.setImageViewResource(R.id.notification_compact_play,
                                R.drawable.ic_pause_black_36dp);
                            expandedView.setImageViewResource(R.id.notification_expanded_play,
                                R.drawable.ic_pause_black_36dp);
                    }

                    compactView.setTextViewText(R.id.notificaton_compact_name, thisSong.fullName());

                    expandedView.setTextViewText(R.id.notification_expanded_track_name, thisSong.name());
                    expandedView.setTextViewText(R.id.notification_expanded_artist_name, thisSong.artistsName());

                    final Notification notification = new NotificationCompat.Builder(PlayService.this)
                        .setCustomContentView(compactView)
                        .setCustomBigContentView(expandedView)
                        .setOngoing(true)
                        .setContentIntent(pendingIntent)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .build();

                    Observable.just(null)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<Object>() {
                            @Override
                            public void call(final Object o) {
                                startForeground(SERVICE_NOTIFICATION_ID, notification);
                            }
                        });
                }

                @Override
                protected void onFailureImpl(final DataSource<CloseableReference<CloseableImage>> dataSource) {}
            }, Executors.newSingleThreadExecutor());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
            subscription = null;
        }

        PlayManager.instance().release();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}