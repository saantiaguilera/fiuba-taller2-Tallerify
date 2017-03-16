package com.u.tallerify.model.entity;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.io.Serializable;
import java.util.List;

/**
 * Created by saguilera on 3/14/17.
 */

public class Playlist extends Entity implements Serializable {

    private @NonNull String name;
    private @NonNull List<Song> songs;
    private @NonNull User creator;

    protected Playlist() {
        super();
    }

    protected Playlist(@NonNull Playlist.Builder builder) {
        super(builder);
        name = builder.name;
        songs = builder.songs;
        creator = builder.creator;
    }

    public @NonNull String name() {
        return name;
    }

    public @NonNull List<Song> songs() {
        return songs;
    }

    public @NonNull User creator() {
        return creator;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Playlist)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        final Playlist playlist = (Playlist) o;

        if (!name.equals(playlist.name)) {
            return false;
        }
        if (!songs.equals(playlist.songs)) {
            return false;
        }
        return creator.equals(playlist.creator);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + songs.hashCode();
        result = 31 * result + creator.hashCode();
        return result;
    }

    public static class Builder extends Entity.Builder<Playlist> {

        @Nullable String name;
        @Nullable List<Song> songs;
        @Nullable User creator;

        public Builder() {
            super();
        }

        public Builder(@NonNull Playlist playlist) {
            super(playlist);
            name(playlist.name());
            songs(playlist.songs());
            creator(playlist.creator());
        }

        public final @NonNull Playlist.Builder name(@NonNull final String name) {
            this.name = name;
            return this;
        }

        public final @NonNull Playlist.Builder songs(@NonNull final List<Song> songs) {
            this.songs = songs;
            return this;
        }

        public final @NonNull Playlist.Builder creator(@NonNull final User creator) {
            this.creator = creator;
            return this;
        }

        @Override
        public @NonNull Playlist build() {
            if (buildable())
                return new Playlist(this);
            else throw new IllegalStateException("Builder has an illegal state");
        }

        @Override
        public boolean buildable() {
            boolean buildable = super.buildable();
            buildable &= name != null;
            buildable &= songs != null;
            buildable &= creator != null;
            return buildable;
        }

    }

}
