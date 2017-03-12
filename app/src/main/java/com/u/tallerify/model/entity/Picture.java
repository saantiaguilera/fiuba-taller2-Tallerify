package com.u.tallerify.model.entity;

import android.support.annotation.NonNull;
import java.io.Serializable;

/**
 * Model for a picture.
 *
 * They will be available in three different sizes
 *
 * Created by saguilera on 3/12/17.
 */
@SuppressWarnings("unused")
public class Picture extends Entity implements Serializable {

    private @NonNull String large;
    private @NonNull String medium;
    private @NonNull String thumb;

    protected Picture() {
        super();
    }

    public @NonNull String large() {
        return large;
    }

    public @NonNull String medium() {
        return medium;
    }

    public @NonNull String thumb() {
        return thumb;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Picture)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        final Picture picture = (Picture) o;

        if (!large.equals(picture.large)) {
            return false;
        }
        if (!medium.equals(picture.medium)) {
            return false;
        }
        return thumb.equals(picture.thumb);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + large.hashCode();
        result = 31 * result + medium.hashCode();
        result = 31 * result + thumb.hashCode();
        return result;
    }
}
