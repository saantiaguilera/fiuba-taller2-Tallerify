package com.u.tallerify.model;

import android.support.annotation.NonNull;
import com.u.tallerify.annotations.KeepName;
import javax.annotation.Nullable;

/**
 * Created by saguilera on 4/4/17.
 */
@KeepName
public final class Message {

    private long senderId;
    private @NonNull String text;

    private Message() {}

    Message(@NonNull Builder builder) {
        this.senderId = builder.senderId;
        this.text = builder.text;
    }

    public @NonNull String message() {
        return text;
    }

    public long senderId() {
        return senderId;
    }

    public static class Builder implements Preconditions<Message> {

        long senderId = NO_VALUE;
        @Nullable String text;

        public Builder() {
        }

        public Builder(@NonNull Message message) {
            this.senderId = message.senderId();
            this.text = message.message();
        }

        public @NonNull Builder senderId(long senderId) {
            this.senderId = senderId;
            return this;
        }

        public @NonNull Builder message(@NonNull String text) {
            this.text = text;
            return this;
        }

        @Override
        public boolean buildable() {
            return senderId != NO_VALUE && text != null;
        }

        @Override
        public @NonNull Message build() {
            if (!buildable()) {
                throw new IllegalStateException("Message isnt buildable! Some value was missing");
            }
            return new Message(this);
        }

    }

}
