package com.u.tallerify.model.entity;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.io.Serializable;
import java.util.List;

/**
 * Created by saguilera on 3/14/17.
 */
public class Playlist extends Entity implements Serializable, Playable {

    private @Nullable String name;
    private @Nullable String description;
    private @Nullable List<Song> songs;
    private @Nullable User owner;
    private @Nullable List<String> images;

    protected Playlist() {
        super();
    }

    protected Playlist(@NonNull Playlist.Builder builder) {
        super(builder);
        name = builder.name;
        songs = builder.songs;
        owner = builder.owner;
        description = builder.description;
        images = builder.pictures;
    }

    public @Nullable String description() {
        return description;
    }

    public @Nullable String name() {
        return name;
    }

    public @Nullable List<Song> songs() {
        return songs;
    }

    public @Nullable User creator() {
        return owner;
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

        if (name != null ? !name.equals(playlist.name) : playlist.name != null) {
            return false;
        }
        if (description != null ? !description.equals(playlist.description) : playlist.description != null) {
            return false;
        }
        if (songs != null ? !songs.equals(playlist.songs) : playlist.songs != null) {
            return false;
        }
        if (owner != null ? !owner.equals(playlist.owner) : playlist.owner != null) {
            return false;
        }
        return images != null ? images.equals(playlist.images) : playlist.images == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (songs != null ? songs.hashCode() : 0);
        result = 31 * result + (owner != null ? owner.hashCode() : 0);
        result = 31 * result + (images != null ? images.hashCode() : 0);
        return result;
    }

    @Nullable
    @Override
    public List<Song> asPlaylist() {
        return songs;
    }

    @Nullable
    @Override
    public List<String> pictures() {
        return images;
    }

    @Nullable
    @Override
    public String fullName() {
        return name();
    }

    public static class Builder extends Entity.Builder<Playlist> {

        @Nullable String name;
        @Nullable List<Song> songs;
        @Nullable User owner;
        @Nullable String description;
        @Nullable List<String> pictures;

        public Builder() {
            super();
        }

        public Builder(@NonNull Playlist playlist) {
            super(playlist);
            name(playlist.name());
            tracks(playlist.songs());
            owner(playlist.creator());
            description(playlist.description());
            pictures(playlist.pictures());
        }

        public final @NonNull Playlist.Builder pictures(@NonNull final List<String> pictures) {
            this.pictures = pictures;
            return this;
        }

        public final @NonNull Playlist.Builder description(@NonNull final String description) {
            this.description = description;
            return this;
        }

        public final @NonNull Playlist.Builder name(@NonNull final String name) {
            this.name = name;
            return this;
        }

        public final @NonNull Playlist.Builder tracks(@NonNull final List<Song> songs) {
            this.songs = songs;
            return this;
        }

        public final @NonNull Playlist.Builder owner(@NonNull final User creator) {
            this.owner = creator;
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
            buildable &= owner != null;
            buildable &= description != null;
            return buildable;
        }

    }

}
