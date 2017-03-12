package com.u.tallerify.model;

import android.support.annotation.NonNull;

/**
 * Preconditions contract that ALL builders of Models must avide.
 *
 * Created by saguilera on 3/12/17.
 */
public interface Preconditions<T> {

    /**
     * Custom value for longs when they are unsetted.
     */
    long NO_VALUE = -1;

    /**
     * Method for knowing if the current builder can build the desired object
     * or is still lacking some properties to be setted
     *
     * NOTE: Each model must validate on its own with this. Its not a must to use it,
     * since some builders can be always built or dont need to enforce properties.
     *
     * @return true if Builder can perform building, false otherwise
     */
    boolean buildable();

    /**
     * Build a new instance based on the properties of the builder
     * @return new instance of T
     */
    @NonNull T build();

}
