package com.u.tallerify.model.entity;

import android.support.annotation.NonNull;
import com.u.tallerify.model.Preconditions;
import java.io.Serializable;

/**
 * Base model of an entity.
 *
 * Entity must have essence and be differentiated from sames. This means, for easiness, that they must
 * have an id to determine if they are the same or not. Something like a Location cant be an entity for example.
 *
 * Models MUST use the Builder pattern. For more information read about it in google :)
 * Models MUST be immutable. They cant have setters. Dont use getX(), use x().
 * Models MUST have an empty constructor AND a (Builder b) constructor.
 * IF the model can be instantiated. It MUST have a builder->model / model->builder method.
 * - This will be build() on Builder class
 * - This will be newBuilder() on Model class
 * Builder CAN override buildable() method to satisfy new preconditions before building.
 * - Builder MUST validate with buildable() in build() method
 * - Builder MUST call super buildables() if overriding it.
 *
 * Created by saguilera on 3/12/17.
 */
@SuppressWarnings("unused")
public abstract class Entity implements Serializable {

    private long id;

    protected Entity() {
    }

    protected Entity(Builder builder) {
        this.id = builder.id;
    }

    public long id() {
        return id;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Entity)) {
            return false;
        }

        final Entity entity = (Entity) o;

        return id == entity.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    public static class Builder<EntityType extends Entity> implements Preconditions<EntityType> {

        long id = NO_VALUE;

        public Builder() {
        }

        public Builder(@NonNull EntityType entityType) {
            id(entityType.id());
        }

        public final @NonNull Builder<EntityType> id(final long id) {
            this.id = id;
            return this;
        }

        @Override
        public boolean buildable() {
            return id != NO_VALUE;
        }

        @Override
        public @NonNull
        EntityType build() {
            throw new IllegalStateException("Entity cant be instantiated via Build pattern");
        }

    }

}
