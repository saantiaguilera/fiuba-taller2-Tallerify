package com.u.tallerify.model.entity;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Model for the user
 *
 * Created by saguilera on 3/12/17.
 */
public class User extends Entity implements Serializable {

    private @Nullable String userName;
    private @Nullable String firstName;
    private @Nullable String lastName;
    private @Nullable String country;
    private @Nullable String email;
    private @Nullable Date birthday;
    private @Nullable List<String> images;
    private @Nullable List<User> contacts;

    protected User() {
        super();
    }

    protected User(@NonNull Builder builder) {
        super(builder);
        userName = builder.name;
        email = builder.email;
        birthday = builder.birthday;
        images = builder.images;
        contacts = builder.contacts;
        firstName = builder.firstName;
        lastName = builder.lastName;
        country = builder.country;
    }

    public @Nullable Date birthday() {
        return birthday;
    }

    public @Nullable List<String> pictures() {
        return images;
    }

    public @Nullable String email() {
        return email;
    }

    public @Nullable String firstName() {
        return firstName;
    }

    public @Nullable String lastName() {
        return lastName;
    }

    public @Nullable String country() {
        return country;
    }

    public @Nullable String name() {
        return userName;
    }

    public @Nullable List<User> contacts() {
        return contacts;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        final User user = (User) o;

        if (userName != null ? !userName.equals(user.userName) : user.userName != null) {
            return false;
        }
        if (firstName != null ? !firstName.equals(user.firstName) : user.firstName != null) {
            return false;
        }
        if (lastName != null ? !lastName.equals(user.lastName) : user.lastName != null) {
            return false;
        }
        if (country != null ? !country.equals(user.country) : user.country != null) {
            return false;
        }
        if (email != null ? !email.equals(user.email) : user.email != null) {
            return false;
        }
        if (birthday != null ? !birthday.equals(user.birthday) : user.birthday != null) {
            return false;
        }
        if (images != null ? !images.equals(user.images) : user.images != null) {
            return false;
        }
        return contacts != null ? contacts.equals(user.contacts) : user.contacts == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
        result = 31 * result + (images != null ? images.hashCode() : 0);
        result = 31 * result + (contacts != null ? contacts.hashCode() : 0);
        return result;
    }

    public static class Builder extends Entity.Builder<User> {

        @Nullable String name;
        @Nullable String email;
        @Nullable Date birthday;
        @Nullable List<User> contacts;
        @Nullable List<String> images;
        @Nullable String firstName;
        @Nullable String lastName;
        @Nullable String country;

        public Builder() {
            super();
        }

        public Builder(@NonNull User user) {
            super(user);
            name(user.name());
            email(user.email());
            birthday(user.birthday());
            pictures(user.pictures());
            contacts(user.contacts());
            firstName(user.firstName());
            lastName(user.lastName());
            country(user.country());
        }

        public final @NonNull Builder country(@NonNull final String country) {
            this.country = country;
            return this;
        }

        public final @NonNull Builder firstName(@NonNull final String firstName) {
            this.firstName = firstName;
            return this;
        }

        public final @NonNull Builder lastName(@NonNull final String lastName) {
            this.lastName = lastName;
            return this;
        }

        public final @NonNull Builder birthday(@NonNull final Date birthday) {
            this.birthday = birthday;
            return this;
        }

        public final @NonNull Builder email(@NonNull final String email) {
            this.email = email;
            return this;
        }

        public final @NonNull Builder name(@NonNull final String name) {
            this.name = name;
            return this;
        }

        public final @NonNull Builder pictures(@NonNull final List<String> pictures) {
            this.images = pictures;
            return this;
        }

        public final @NonNull Builder contacts(@NonNull final List<User> contacts) {
            this.contacts = contacts;
            return this;
        }

        @Override
        public @NonNull User build() {
            if (buildable())
                return new User(this);
            else throw new IllegalStateException("Builder has an illegal state");
        }

        @Override
        public boolean buildable() {
            boolean buildable = super.buildable();
            buildable &= name != null;
            buildable &= email != null;
            buildable &= birthday != null;
            buildable &= images != null;
            buildable &= contacts != null;
            buildable &= firstName != null;
            buildable &= lastName != null;
            buildable &= country != null;
            return buildable;
        }

    }

}