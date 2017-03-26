package com.u.tallerify.model.entity;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.io.Serializable;
import java.util.List;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by saguilera on 3/14/17.
 */
public class Playlist extends Entity implements Serializable, Playable {

    private @NonNull String name;
    private @NonNull String description;
    private @NonNull List<Song> tracks;
    private @NonNull User owner;

    protected Playlist() {
        super();
    }

    protected Playlist(@NonNull Playlist.Builder builder) {
        super(builder);
        name = builder.name;
        tracks = builder.tracks;
        owner = builder.owner;
        description = builder.description;
    }

    public @NonNull String description() {
        return description;
    }

    public @NonNull String name() {
        return name;
    }

    public @NonNull List<Song> songs() {
        return tracks;
    }

    public @NonNull User creator() {
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

        if (!name.equals(playlist.name)) {
            return false;
        }
        if (!tracks.equals(playlist.tracks)) {
            return false;
        }
        if (description != null && !description.equals(playlist.description)) {
            return false;
        }
        return owner.equals(playlist.owner);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + tracks.hashCode();
        result = 31 * result + owner.hashCode();
        if (description != null) {
            result = 31 * result + description.hashCode();
        }
        return result;
    }

    @Nullable
    @Override
    public List<String> urls() {
        return Observable.from(tracks)
            .observeOn(Schedulers.io())
            .map(new Func1<Song, String>() {
                @Override
                public String call(final Song song) {
                    return song.url();
                }
            }).toList()
            .observeOn(AndroidSchedulers.mainThread())
            .toBlocking()
            .first();
    }

    @Nullable
    @Override
    public List<String> pictures() {
        return songs().isEmpty() ? null : songs().get(0).pictures(); // TODO aca hacemos un collage con 4 distintos tipo spotify (?)
    }

    @NonNull
    @Override
    public String fullName() {
        return name();
    }

    public static class Builder extends Entity.Builder<Playlist> {

        @Nullable String name;
        @Nullable List<Song> tracks;
        @Nullable User owner;
        @Nullable String description;

        public Builder() {
            super();
        }

        public Builder(@NonNull Playlist playlist) {
            super(playlist);
            name(playlist.name());
            tracks(playlist.songs());
            owner(playlist.creator());
            description(playlist.description());
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
            this.tracks = songs;
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
            buildable &= tracks != null;
            buildable &= owner != null;
            buildable &= description != null;
            return buildable;
        }

    }

}
