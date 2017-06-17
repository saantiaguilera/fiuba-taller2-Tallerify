package com.u.tallerify.networking.interactor.user;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.u.tallerify.model.entity.Song;
import com.u.tallerify.model.entity.User;
import com.u.tallerify.networking.ReactiveModel;
import com.u.tallerify.networking.RestClient;
import com.u.tallerify.networking.services.user.UserService;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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

    public static final int ACTION_LOADING = 0;
    public static final int ACTION_EMPTY_SEARCH = 1;

    private static final @NonNull UserInteractor instance = new UserInteractor();

    @NonNull BehaviorSubject<ReactiveModel<List<User>>> searchSubject;

    private UserInteractor() {
        searchSubject = BehaviorSubject.create();
    }

    public static @NonNull UserInteractor instance() {
        return instance;
    }

    public @NonNull Observable<ReactiveModel<List<User>>> observeSearches() {
        return searchSubject;
    }

    public @NonNull Observable<User> user(@NonNull Context context, long userId) {
        return RestClient.with(context).create(UserService.class)
            .user(userId);
    }

    public @NonNull Observable<User> create(@NonNull Context context, @NonNull User user, @NonNull String password) {
        final String MULTIPART_FORM_DATA = "multipart/form-data";

        Map<String, RequestBody> params = new HashMap<>();
        params.put("userName", RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), user.name()));
        params.put("password", RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), password));
        params.put("firstName", RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), user.firstName()));
        params.put("lastName", RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), user.lastName()));
        params.put("country", RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), user.country()));
        params.put("email", RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), user.email()));

        params.put("birthdate", RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), user.birthday()));

        for (int pos = 0; pos < user.pictures().size(); pos++) {
            if (!TextUtils.isEmpty(user.pictures().get(pos))) {
                RequestBody requestBody = RequestBody.create(
                    MediaType.parse(MULTIPART_FORM_DATA), new File(user.pictures().get(pos)));
                String key = String.format("%1$s\"; filename=\"%1$s", "avatar");
                params.put(key, requestBody);
            }
        }

        return RestClient.with(context)
            .noAuth()
            .create(UserService.class)
            .create(params);
    }

    public @NonNull Observable<User> follow(@NonNull Context context, @NonNull User him) {
        return RestClient.with(context).create(UserService.class)
            .follow(him.id());
    }

    public @NonNull Observable<User> unfollow(@NonNull Context context, final @NonNull User him) {
        return RestClient.with(context).create(UserService.class)
            .unfollow(him.id())
            .map(new Func1<Void, User>() {
                @Override
                public User call(final Void aVoid) {
                    return him;
                }
            });
    }

    public @NonNull Observable<List<User>> search(@NonNull Context context, @NonNull String query) {
        if (query.isEmpty()) {
            return Observable.just(null)
                .map(new Func1<Object, List<User>>() {
                    @Override
                    public List<User> call(final Object o) {
                        return null;
                    }
                })
                .doOnNext(new Action1<List<User>>() {
                    @Override
                    public void call(final List<User> o) {
                        searchSubject.onNext(new ReactiveModel.Builder<List<User>>()
                            .action(ACTION_EMPTY_SEARCH)
                            .build());
                    }
                });
        } else {
            return RestClient.with(context).create(UserService.class)
                .queryUsers(query)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        searchSubject.onNext(new ReactiveModel.Builder<List<User>>()
                            .action(ACTION_LOADING)
                            .build());
                    }
                }).doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(final Throwable throwable) {
                        searchSubject.onNext(new ReactiveModel.Builder<List<User>>()
                            .error(throwable)
                            .build());
                    }
                }).doOnNext(new Action1<List<User>>() {
                    @Override
                    public void call(final List<User> users) {
                        searchSubject.onNext(new ReactiveModel.Builder<List<User>>()
                            .model(users)
                            .build());
                    }
                });
        }
    }

}