package com.u.tallerify.model.entity;

import android.support.annotation.Nullable;
import java.util.List;

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
    public List<Song> asPlaylist() {
        return tracks;
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