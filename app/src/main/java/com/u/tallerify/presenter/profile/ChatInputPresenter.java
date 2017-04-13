package com.u.tallerify.presenter.profile;

import android.support.annotation.NonNull;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.u.tallerify.contract.profile.ChatInputContract;
import com.u.tallerify.controller.profile.ChatController;
import com.u.tallerify.model.Message;
import com.u.tallerify.model.entity.User;
import com.u.tallerify.presenter.Presenter;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by saguilera on 4/5/17.
 */
public class ChatInputPresenter extends Presenter<ChatInputContract.View>
        implements ChatInputContract.Presenter {

    @NonNull User him;
    @NonNull User me;

    public ChatInputPresenter(@NonNull User me, @NonNull User him) {
        this.me = me;
        this.him = him;
    }

    @Override
    protected void onAttach(@NonNull final ChatInputContract.View view) {
        view.observeUserMessages()
            .observeOn(Schedulers.computation())
            .subscribeOn(Schedulers.computation())
            .compose(this.<String>bindToLifecycle())
            .subscribe(new Action1<String>() {
                @Override
                public void call(final String message) {
                    if (!message.trim().isEmpty()) {
                        Message sendableObject = new Message.Builder()
                            .message(message)
                            .senderId(me.id())
                            .build();

                        FirebaseDatabase.getInstance().getReference()
                            .child(ChatController.composeSerialKey(me, him))
                            .push()
                            .setValue(new Gson().toJson(sendableObject));
                    }
                }
            });
    }

    @Override
    protected void onRender(@NonNull final ChatInputContract.View view) {
        view.focus();
    }

}
