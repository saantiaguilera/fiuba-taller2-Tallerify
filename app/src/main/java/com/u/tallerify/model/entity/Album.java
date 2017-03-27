package com.u.tallerify.model.entity;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

    private @Nullable List<String> images;
    private @Nullable String name;
    private @Nullable List<Song> tracks;
    private @Nullable List<Artist> artists;

    protected Album() {
        super();
    }

    @Nullable
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

    public @Nullable List<String> pictures() {
        return images;
    }

    @Nullable
    @Override
    public String fullName() {
        return name();
    }

    public @Nullable String name() {
        return name;
    }

    public @Nullable List<Song> songs() {
        return tracks;
    }

    public @Nullable List<Artist> artists() {
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

        if (images != null ? !images.equals(album.images) : album.images != null) {
            return false;
        }
        if (name != null ? !name.equals(album.name) : album.name != null) {
            return false;
        }
        if (tracks != null ? !tracks.equals(album.tracks) : album.tracks != null) {
            return false;
        }
        return artists != null ? artists.equals(album.artists) : album.artists == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (images != null ? images.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (tracks != null ? tracks.hashCode() : 0);
        result = 31 * result + (artists != null ? artists.hashCode() : 0);
        return result;
    }

}