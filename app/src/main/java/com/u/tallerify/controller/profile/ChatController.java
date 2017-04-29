package com.u.tallerify.controller.profile;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.squareup.coordinators.Coordinator;
import com.squareup.coordinators.CoordinatorProvider;
import com.u.tallerify.R;
import com.u.tallerify.controller.FlowController;
import com.u.tallerify.model.entity.User;
import com.u.tallerify.networking.ReactiveModel;
import com.u.tallerify.networking.interactor.me.MeInteractor;
import com.u.tallerify.presenter.AbstractPresenterGraph;
import com.u.tallerify.presenter.Presenter;
import com.u.tallerify.presenter.profile.ChatInputPresenter;
import com.u.tallerify.presenter.profile.ChatListPresenter;
import com.u.tallerify.utils.CoordinatorsInstaller;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by saguilera on 4/4/17.
 */
public class ChatController extends FlowController {

    private static final int KEY_LIST = 0;
    private static final int KEY_INPUT = 1;

    @Nullable User him;
    @Nullable User me;

    @Nullable Graph graph;

    public ChatController() {
        me = MeInteractor.instance().userSnapshot();
    }

    public @NonNull ChatController with(@NonNull User him) {
        this.him = him;
        createGraph();
        return this;
    }

    @NonNull
    @Override
    protected View onCreateView(@NonNull final LayoutInflater inflater, @NonNull final ViewGroup container) {
        View view = inflater.inflate(R.layout.controller_chat, container, false);

        view.findViewById(R.id.controller_chat_list).setTag(KEY_LIST);
        view.findViewById(R.id.controller_chat_input).setTag(KEY_INPUT);

        return view;
    }

    @Override
    protected void onAttach(@NonNull final View view) {
        super.onAttach(view);

        createGraph(); // In case we had detached.

        if (graph != null) {
            CoordinatorsInstaller.installBinder((ViewGroup) view, new CoordinatorProvider() {
                @Nullable
                @Override
                public Coordinator provideCoordinator(final View view) {
                    return graph.present(view);
                }
            });
        }
    }

    @Override
    protected void onDetach(@NonNull final View view) {
        super.onDetach(view);
        graph = null;
    }

    void createGraph() {
        if (me != null && him != null && graph == null) {
            graph = new Graph(me, him);
        }
    }

    @Override
    protected boolean hasScrollingActionBar() {
        return false;
    }

    @Nullable
    @Override
    protected String title() {
        if (him == null) {
            throw new IllegalStateException("No user for chat controller. Forgot calling with(User)?");
        }
        return him.name();
    }

    private static class Graph extends AbstractPresenterGraph {

        Graph(@NonNull User me, @NonNull User him) {
            add(KEY_LIST, new ChatListPresenter(me, him));
            add(KEY_INPUT, new ChatInputPresenter(me, him));
        }

        @Nullable
        @Override
        public Presenter<?> present(@NonNull final View view) {
            return view.getTag() == null ? null : get((Integer) view.getTag());
        }

        @Override
        public int size() {
            return 2;
        }

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
