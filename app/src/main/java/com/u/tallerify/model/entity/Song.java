package com.u.tallerify.model.entity;

import android.support.annotation.NonNull;

/**
 * Created by saguilera on 3/12/17.
 */
@SuppressWarnings("unused")
public class Song extends Entity {

    private @NonNull String url;
    private @NonNull String name;
    private @NonNull Album album;

    protected Song() {
        super();
    }

    public @NonNull String url() {
        return url;
    }

    public @NonNull String name() {
        return name;
    }

    public @NonNull Album album() {
        return album;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Song)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        final Song song = (Song) o;

        if (!url.equals(song.url)) {
            return false;
        }
        if (!name.equals(song.name)) {
            return false;
        }
        if (!album.equals(song.album)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + url.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + album.hashCode();
        return result;
    }

}