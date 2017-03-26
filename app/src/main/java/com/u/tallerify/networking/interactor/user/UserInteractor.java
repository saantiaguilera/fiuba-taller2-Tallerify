package com.u.tallerify.networking.interactor.user;

import android.content.Context;
import android.support.annotation.NonNull;
import com.u.tallerify.model.entity.Song;
import com.u.tallerify.model.entity.User;
import com.u.tallerify.networking.ReactiveModel;
import com.u.tallerify.networking.RestClient;
import com.u.tallerify.networking.services.songs.SongService;
import com.u.tallerify.networking.services.user.UserService;
import java.util.List;
import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.subjects.BehaviorSubject;

/**
 * Created by saguilera on 3/26/17.
 */
@SuppressWarnings("unchecked")
public final class UserInteractor {

    private static final @NonNull UserInteractor instance = new UserInteractor();

    private UserInteractor() {
    }

    public static @NonNull UserInteractor instance() {
        return instance;
    }

    public @NonNull Observable<User> user(@NonNull Context context, long userId) {
        return RestClient.with(context).create(UserService.class)
            .user(userId);
    }

    public @NonNull Observable<User> follow(@NonNull Context context, @NonNull User me, @NonNull User him) {
        return RestClient.with(context).create(UserService.class)
            .follow(me.id(), him.id());
    }

    public @NonNull Observable<User> unfollow(@NonNull Context context, @NonNull User me, final @NonNull User him) {
        return RestClient.with(context).create(UserService.class)
            .unfollow(me.id(), him.id())
            .map(new Func1<Void, User>() {
                @Override
                public User call(final Void aVoid) {
                    return him;
                }
            });
    }

}