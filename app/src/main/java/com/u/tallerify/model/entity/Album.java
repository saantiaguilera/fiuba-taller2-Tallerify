package com.u.tallerify.model.entity;

import android.support.annotation.NonNull;
import java.util.List;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by saguilera on 3/12/17.
 */
@SuppressWarnings("unused")
public class Album extends Entity implements Playable {

    private @NonNull List<String> images;
    private @NonNull String name;
    private @NonNull List<Song> tracks;
    private @NonNull List<Artist> artists;

    protected Album() {
        super();
    }

    @NonNull
    @Override
    public List<String> urls() {
        return Observable.from(tracks)
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .map(new Func1<Song, String>() {
                @Override
                public String call(final Song song) {
                    return song.url();
                }
            }).toList()
            .observeOn(AndroidSchedulers.mainThread())
            .toBlocking()
            .first();
    }

    public @NonNull List<String> pictures() {
        return images;
    }

    @NonNull
    @Override
    public String fullName() {
        return name();
    }

    public @NonNull String name() {
        return name;
    }

    public @NonNull List<Song> songs() {
        return tracks;
    }

    public @NonNull List<Artist> artists() {
        return artists;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Album)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        final Album album = (Album) o;

        if (!images.equals(album.images)) {
            return false;
        }
        if (!name.equals(album.name)) {
            return false;
        }
        if (tracks != null && !tracks.equals(album.tracks)) {
            return false;
        }
        if (artists != null && !artists.equals(album.artists)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + images.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + tracks.hashCode();
        result = 31 * result + artists.hashCode();
        return result;
    }

}