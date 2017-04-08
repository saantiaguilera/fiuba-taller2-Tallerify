package com.u.tallerify.model.entity;

import android.support.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by saguilera on 3/12/17.
 */
@SuppressWarnings("unused")
public class Song extends Entity implements Playable {

    private @Nullable String href;
    private @Nullable String name;
    private @Nullable Album album;
    private @Nullable List<Artist> artists;

    protected Song() {
        super();
    }

    public @Nullable String url() {
        return href;
    }

    public @Nullable String name() {
        return name;
    }

    public @Nullable Album album() {
        return album;
    }

    public @Nullable List<Artist> artists() {
        return artists;
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

        if (href != null ? !href.equals(song.href) : song.href != null) {
            return false;
        }
        if (name != null ? !name.equals(song.name) : song.name != null) {
            return false;
        }
        if (album != null ? !album.equals(song.album) : song.album != null) {
            return false;
        }
        return artists != null ? artists.equals(song.artists) : song.artists == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (href != null ? href.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (album != null ? album.hashCode() : 0);
        result = 31 * result + (artists != null ? artists.hashCode() : 0);
        return result;
    }

    @Nullable
    @Override
    public List<Song> asPlaylist() {
        List<Song> list = new ArrayList<>();
        list.add(this);
        return list;
    }

    @Nullable
    @Override
    public List<String> pictures() {
        return album.pictures();
    }

    @Nullable
    @Override
    public String fullName() {
        return name() + " - " + artistsName();
    }

    public @Nullable String artistsName() {
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