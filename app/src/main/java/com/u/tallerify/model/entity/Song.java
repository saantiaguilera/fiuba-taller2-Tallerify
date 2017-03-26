package com.u.tallerify.model.entity;

import android.support.annotation.NonNull;
import java.util.Collections;
import java.util.List;

/**
 * Created by saguilera on 3/12/17.
 */
@SuppressWarnings("unused")
public class Song extends Entity implements Playable {

    private @NonNull String href;
    private @NonNull String name;
    private @NonNull Album album;
    private @NonNull List<Artist> artists;
    private long duration;

    protected Song() {
        super();
    }

    public @NonNull String url() {
        return href;
    }

    public @NonNull String name() {
        return name;
    }

    public @NonNull Album album() {
        return album;
    }

    public @NonNull List<Artist> artists() {
        return artists;
    }

    public long duration() {
        return duration;
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

        if (!href.equals(song.href)) {
            return false;
        }
        if (!name.equals(song.name)) {
            return false;
        }
        if (!album.equals(song.album)) {
            return false;
        }
        if (duration != song.duration) {
            return false;
        }
        if (artists != song.artists) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + href.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + album.hashCode();
        result = 31 * result + artists.hashCode();
        result = 31 * result + Long.valueOf(duration).hashCode();
        return result;
    }

    @NonNull
    @Override
    public List<String> urls() {
        return Collections.singletonList(href);
    }

    @NonNull
    @Override
    public List<String> pictures() {
        return album.pictures();
    }

    @NonNull
    @Override
    public String fullName() {
        return name() + " - " + artistsName();
    }

    public @NonNull String artistsName() {
        String name = null;
        for (Artist artist : artists()) {
            if (name == null) {
                name = artist.fullName();
            } else {
                name += " Ft. " + artist.fullName();
            }
        }
        return name == null ? "" : name;
    }

}