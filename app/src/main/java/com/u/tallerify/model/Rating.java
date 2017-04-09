package com.u.tallerify.model;

import android.support.annotation.NonNull;
import com.u.tallerify.model.entity.Song;
import javax.annotation.Nullable;

/**
 * Created by saguilera on 4/9/17.
 */

public class Rating {

    @NonNull Song song;
    int rate = 0;

    private Rating() {}

    Rating(@NonNull Builder builder) {
        song = builder.song;
        rate = builder.rating;
    }

    public @NonNull Song song() {
        return song;
    }

    public int rating() {
        return rate;
    }

    public @NonNull Builder newBuilder() {
        return new Builder(this);
    }

    public static class Builder implements Preconditions<Rating> {

        @Nullable Song song;
        int rating = (int) NO_VALUE;

        public Builder() {
        }

        public Builder(@NonNull Rating rating) {
            this.song = rating.song;
            this.rating = rating.rate;
        }

        public @NonNull Builder song(@NonNull Song song) {
            this.song = song;
            return this;
        }

        public @NonNull Builder rating(int rating) {
            this.rating = rating;
            return this;
        }

        @Override
        public boolean buildable() {
            return song != null && rating != (int) NO_VALUE;
        }

        @NonNull
        @Override
        public Rating build() {
            if (buildable()) {
                return new Rating(this);
            }
            throw new IllegalStateException("Rating malformed, please check all params are supplied");
        }

    }

}
