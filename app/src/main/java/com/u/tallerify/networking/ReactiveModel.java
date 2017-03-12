package com.u.tallerify.networking;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by saguilera on 3/12/17.
 */
public class ReactiveModel<Model> {

    public static final int NO_ACTION = -1;

    private Throwable error;
    private Model model;

    private int action;

    ReactiveModel(Throwable error, Model model, int action) {
        this.error = error;
        this.model = model;
        this.action = action;
    }

    public boolean hasError() {
        return error != null;
    }

    public @Nullable Throwable error() {
        return error;
    }

    public @Nullable Model model() {
        return model;
    }

    public int action() {
        return action;
    }

    @SuppressWarnings("unchecked")
    public @NonNull Builder newBuilder() {
        return new Builder<Model>()
            .action(action)
            .error(error)
            .model(model);
    }

    public static class Builder<Model> {

        private Throwable error;
        private Model model;
        private int action;

        public Builder() {
            this.action = NO_ACTION;
            this.error = null;
            this.model = null;
        }

        public @NonNull Builder action(int action) {
            this.action = action;
            return this;
        }

        public @NonNull Builder model(@NonNull Model model) {
            this.model = model;
            return this;
        }

        public @NonNull Builder error(@NonNull Throwable error) {
            this.error = error;
            return this;
        }

        public @NonNull ReactiveModel<Model> build() {
            return new ReactiveModel<>(error, model, action);
        }

    }

}
