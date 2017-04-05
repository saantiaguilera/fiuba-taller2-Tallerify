package com.u.tallerify.controller.profile;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.squareup.coordinators.Coordinator;
import com.squareup.coordinators.CoordinatorProvider;
import com.squareup.coordinators.Coordinators;
import com.u.tallerify.R;
import com.u.tallerify.controller.FlowController;
import com.u.tallerify.model.entity.User;
import com.u.tallerify.networking.ReactiveModel;
import com.u.tallerify.networking.interactor.me.MeInteractor;
import com.u.tallerify.presenter.profile.ChatInputPresenter;
import com.u.tallerify.presenter.profile.ChatListPresenter;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by saguilera on 4/4/17.
 */

public class ChatController extends FlowController {

    @Nullable User him;
    @Nullable User me;

    public ChatController() {
        MeInteractor.instance().observeUser()
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .compose(this.<ReactiveModel<User>>bindToLifecycle())
            .subscribe(new Action1<ReactiveModel<User>>() {
                @Override
                public void call(final ReactiveModel<User> rxModel) {
                    if (rxModel.model() != null && !rxModel.hasError()) {
                        me = rxModel.model();
                    }
                }
            });
    }

    public @NonNull ChatController with(@NonNull User him) {
        this.him = him;
        return this;
    }

    @NonNull
    @Override
    protected View onCreateView(@NonNull final LayoutInflater inflater, @NonNull final ViewGroup container) {
        return inflater.inflate(R.layout.controller_chat, container, false);
    }

    @Override
    protected void onAttach(@NonNull final View view) {
        super.onAttach(view);

        Coordinators.bind(view.findViewById(R.id.controller_chat_list), new CoordinatorProvider() {
            @Nullable
            @Override
            public Coordinator provideCoordinator(final View view) {
                if (me == null || him == null) {
                    return null;
                }
                return new ChatListPresenter(me, him);
            }
        });

        Coordinators.bind(view.findViewById(R.id.controller_chat_input), new CoordinatorProvider() {
            @Nullable
            @Override
            public Coordinator provideCoordinator(final View view) {
                if (me == null || him == null) {
                    return null;
                }
                return new ChatInputPresenter(me, him);
            }
        });
    }

    @Nullable
    @Override
    protected String title() {
        if (him == null) {
            throw new IllegalStateException("No user for chat controller. Forgot calling with(User)?");
        }
        return him.name();
    }

    public static @NonNull String composeSerialKey(@NonNull User me, @NonNull User him) {
        // Creates a unique firebase identifier for two users to chat.
        // We can call it "it creates a channel for both users to chat"
        // Precedence for the lower one
        long lower = me.id() < him.id() ? me.id() : him.id();
        long higher = lower == me.id() ? him.id() : me.id();
        return lower + "_" + higher;
    }

}
