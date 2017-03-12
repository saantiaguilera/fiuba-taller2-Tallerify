package com.u.tallerify.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

/**
 * Enum for the gender model
 *
 * Created by saguilera on 3/12/17.
 */
@SuppressWarnings("unused")
public enum Gender implements Serializable {
    @SerializedName("male")
    MALE,
    @SerializedName("female")
    FEMALE
}
