package com.u.tallerify.model.entity;

import android.support.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by saguilera on 3/12/17.
 */
@SuppressWarnings("unused")
public class Artist extends Entity implements Playable {

    private @Nullable List<String> images;
    private @Nullable String name;
    private @Nullable List<Album> albums;

    protected Artist() {
        super();
    }

    @Nullable
    @Override
    public List<Song> asPlaylist() {
        return Observable.from(albums)
            .observeOn(Schedulers.computation())
            .subscribeOn(Schedulers.computation())
            .map(new Func1<Album, List<Song>>() {
                @Override
                public List<Song> call(final Album album) {
                    List<Song> songs = album.asPlaylist();
                    return songs == null ? new ArrayList<Song>() : songs;
                }
            })
            .toList()
            .map(new Func1<List<List<Song>>, List<Song>>() {
                @Override
                public List<Song> call(final List<List<Song>> lists) {
                    List<Song> songs = new ArrayList<>();
                    for (List<Song> innerSongs : lists) {
                        songs.addAll(innerSongs);
                    }
                    return songs;
                }
            })
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

    public @Nullable List<Album> albums() {
        return albums;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Artist)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        final Artist artist = (Artist) o;

        if (images != null ? !images.equals(artist.images) : artist.images != null) {
            return false;
        }
        if (name != null ? !name.equals(artist.name) : artist.name != null) {
            return false;
        }
        return albums != null ? albums.equals(artist.albums) : artist.albums == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (images != null ? images.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (albums != null ? albums.hashCode() : 0);
        return result;
    }

}