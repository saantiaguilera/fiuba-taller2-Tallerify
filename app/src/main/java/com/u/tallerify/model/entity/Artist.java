package com.u.tallerify.model.entity;

import android.support.annotation.NonNull;
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

    private @NonNull List<String> images;
    private @NonNull String name;
    private @NonNull List<Album> albums;

    protected Artist() {
        super();
    }

    @NonNull
    @Override
    public List<String> urls() {
        return Observable.from(albums)
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .map(new Func1<Album, List<String>>() {
                @Override
                public List<String> call(final Album album) {
                    return album.urls();
                }
            }).toList()
            .map(new Func1<List<List<String>>, List<String>>() {
                @Override
                public List<String> call(final List<List<String>> lists) {
                    List<String> strings = new ArrayList<>();
                    for (List<String> innerStrings : lists) {
                        strings.addAll(innerStrings);
                    }
                    return strings;
                }
            }).observeOn(AndroidSchedulers.mainThread())
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

    public @NonNull List<Album> albums() {
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

        if (!images.equals(artist.images)) {
            return false;
        }
        if (!name.equals(artist.name)) {
            return false;
        }
        if (albums != null && !albums.equals(artist.albums)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + images.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + albums.hashCode();
        return result;
    }

}