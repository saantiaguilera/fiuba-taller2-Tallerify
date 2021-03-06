package com.u.tallerify.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import com.u.tallerify.model.entity.Album;
import com.u.tallerify.model.entity.Artist;
import com.u.tallerify.model.entity.Playable;
import com.u.tallerify.model.entity.Playlist;
import com.u.tallerify.model.entity.Song;
import com.u.tallerify.networking.interactor.album.AlbumInteractor;
import com.u.tallerify.networking.interactor.artist.ArtistInteractor;
import com.u.tallerify.networking.interactor.playlist.PlaylistInteractor;
import java.util.ArrayList;
import java.util.List;
import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Created by saguilera on 4/7/17.
 */

public final class PlayUtils {

    public static void playState() {
        if (CurrentPlay.instance() != null) {
            CurrentPlay.instance().newBuilder()
                .playState(CurrentPlay.instance().playState() == CurrentPlay.PlayState.PLAYING ?
                    CurrentPlay.PlayState.PAUSED :
                    CurrentPlay.PlayState.PLAYING)
                .build();
        }
    }

    public static void shuffle() {
        if (CurrentPlay.instance() != null) {
            CurrentPlay.instance().newBuilder()
                .shuffle(!CurrentPlay.instance().shuffle())
                .build();
        }
    }

    public static void incrementTime() {
        if (CurrentPlay.instance() != null) {
            if (CurrentPlay.instance().time() + 1 == CurrentPlay.instance().duration()) {
                forward();
            } else {
                CurrentPlay.instance().newBuilder()
                    .time(CurrentPlay.instance().time() + 1)
                    .build();
            }
        }
    }

    public static void time(int time) {
        if (CurrentPlay.instance() != null) {
            CurrentPlay.instance().newBuilder()
                .time(time)
                .build();
        }
    }

    public static void forward() {
        if (CurrentPlay.instance() != null) {
            if (CurrentPlay.instance().playlist().isEmpty()) {
                CurrentPlay.instance().newBuilder()
                    .playState(CurrentPlay.PlayState.PAUSED)
                    .time(0)
                    .build();
                return;
            }

            List<Song> playlist = new ArrayList<>(CurrentPlay.instance().playlist());

            if (CurrentPlay.instance().repeat() == CurrentPlay.RepeatMode.ALL ||
                    playlist.isEmpty()) {
                playlist.add(CurrentPlay.instance().song());
            }

            Song nextSong = playlist.isEmpty() ?
                null :
                playlist.get(0);

            if (nextSong != null) {
                playlist.remove(0);

                CurrentPlay.instance().newBuilder()
                    .song(nextSong)
                    .time(0)
                    .playlist(playlist)
                    .build();
            }
        }
    }

    public static void backwards() {
        if (CurrentPlay.instance() != null) {
            // Magic number. If more than 3 seconds passed, reset the current song only
            if (CurrentPlay.instance().time() > 3) {
                CurrentPlay.instance().newBuilder()
                    .time(0)
                    .build();
                return;
            }

            List<Song> playlist = new ArrayList<>(CurrentPlay.instance().playlist());
            Song nextSong = playlist.isEmpty() ?
                null :
                playlist.get(playlist.size() - 1);

            if (nextSong != null) {
                playlist.remove(playlist.size() - 1);
                playlist.add(0, CurrentPlay.instance().song());

                CurrentPlay.instance().newBuilder()
                    .song(nextSong)
                    .time(0)
                    .playlist(playlist)
                    .build();
            }
        }
    }

    public static void repeat() {
        if (CurrentPlay.instance() != null) {
            CurrentPlay.RepeatMode repeatMode;
            switch (CurrentPlay.instance().repeat()) {
                case NONE:
                    repeatMode = CurrentPlay.RepeatMode.ALL;
                    break;
                case ALL:
                default:
                    repeatMode = CurrentPlay.RepeatMode.NONE;
            }

            CurrentPlay.instance().newBuilder()
                .repeat(repeatMode)
                .build();
        }
    }

    public static void playlistBy(final int integer) {
        if (CurrentPlay.instance() != null) {
            final List<Song> newList = new ArrayList<>();
            final List<Song> playlist = new ArrayList<>(CurrentPlay.instance().playlist());

            Observable.range(0, playlist.size())
                .doOnNext(new Action1<Integer>() {
                    @Override
                    public void call(final Integer position) {
                        if (position >= integer) {
                            newList.add(playlist.get(position));
                        }
                    }
                })
                .doOnCompleted(new Action0() {
                    @Override
                    public void call() {
                        if (CurrentPlay.instance().repeat() == CurrentPlay.RepeatMode.ALL ||
                                newList.isEmpty()) {
                            newList.add(CurrentPlay.instance().song());
                            for (int i = 0; i < integer; ++i) {
                                newList.add(playlist.get(i));
                            }
                        }
                    }
                })
                .toBlocking()
                .subscribe();

            Song nextSong = newList.get(0);
            newList.remove(0);

            CurrentPlay.instance().newBuilder()
                .song(nextSong)
                .time(0)
                .playlist(newList)
                .build();
        }
    }

    public static @NonNull Observable<List<Song>> songs(@NonNull Context context, @NonNull Playable playable) {
        Observable<List<Song>> observable = null;
        if (playable instanceof Artist) {
            observable = ArtistInteractor.instance()
                .songs(context.getApplicationContext(), (Artist) playable);
        }

        if (playable instanceof Song) {
            observable = Observable.just(playable.asPlaylist());
        }

        if (playable instanceof Album) {
            observable = AlbumInteractor.instance()
                .songs(context.getApplicationContext(), (Album) playable);
        }

        if (playable instanceof Playlist) {
            observable = PlaylistInteractor.instance()
                .songs(context.getApplicationContext(), (Playlist) playable);
        }

        if (observable == null) {
            throw new IllegalStateException("This is done from a really lazy person who didnt want to do more inherits. " +
                "Please add your instance to this method :)");
        }

        return observable;
    }

}
