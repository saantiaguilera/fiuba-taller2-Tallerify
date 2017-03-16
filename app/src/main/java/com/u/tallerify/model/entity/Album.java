package com.u.tallerify.model.entity;

import android.support.annotation.NonNull;
import java.util.List;

/**
 * Created by saguilera on 3/12/17.
 */
@SuppressWarnings("unused")
public class Album extends Entity {

    private @NonNull Picture picture;
    private @NonNull String name;
    private @NonNull List<Song> songs;
    private @NonNull Artist artist;

    protected Album() {
        super();
    }

    public @NonNull Picture picture() {
        return picture;
    }

    public @NonNull String name() {
        return name;
    }

    public @NonNull List<Song> songs() {
        return songs;
    }

    public @NonNull Artist artist() {
        return artist;
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

        if (!picture.equals(album.picture)) {
            return false;
        }
        if (!name.equals(album.name)) {
            return false;
        }
        if (songs != null && !songs.equals(album.songs)) {
            return false;
        }
        if (artist != null && !artist.equals(album.artist)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + picture.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + songs.hashCode();
        result = 31 * result + artist.hashCode();
        return result;
    }

}