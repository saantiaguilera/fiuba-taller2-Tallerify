package com.u.tallerify.model.entity;

import android.support.annotation.NonNull;
import java.util.List;

/**
 * Created by saguilera on 3/12/17.
 */
@SuppressWarnings("unused")
public class Artist extends Entity {

    private @NonNull Picture picture;
    private @NonNull String name;
    private @NonNull List<Album> albums;

    protected Artist() {
        super();
    }

    public @NonNull Picture picture() {
        return picture;
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

        if (!picture.equals(artist.picture)) {
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
        result = 31 * result + picture.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + albums.hashCode();
        return result;
    }

}