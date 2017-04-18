package com.u.tallerify.networking.interactor.user;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.u.tallerify.model.entity.Song;
import com.u.tallerify.model.entity.User;
import com.u.tallerify.networking.RestClient;
import com.u.tallerify.networking.services.user.UserService;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observable;
import rx.functions.Func1;

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

    public @NonNull Observable<User> create(@NonNull Context context, @NonNull User user, @NonNull String password) {
        final String MULTIPART_FORM_DATA = "multipart/form-data";

        Map<String, RequestBody> params = new HashMap<>();
        params.put("userName", RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), user.name()));
        params.put("password", RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), password));
        params.put("firstName", RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), user.firstName()));
        params.put("lastName", RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), user.lastName()));
        params.put("country", RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), user.country()));
        params.put("email", RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), user.email()));

        DateFormat format = SimpleDateFormat.getDateInstance(); // TODO if theres a custom date format, supply here
        String date = format.format(user.birthday());
        params.put("birthday", RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), date));

        for (int pos = 0; pos < user.pictures().size(); pos++) {
            if (!TextUtils.isEmpty(user.pictures().get(pos))) {
                RequestBody requestBody = RequestBody.create(
                    MediaType.parse(MULTIPART_FORM_DATA), new File(user.pictures().get(pos)));
                String key = String.format("%1$s\"; filename=\"%1$s", "photo_" + String.valueOf(pos + 1));
                params.put(key, requestBody);
            }
        }

        return RestClient.with(context)
            .noAuth()
            .create(UserService.class)
            .create(params);
    }

    public @NonNull Observable<List<Song>> activity(@NonNull Context context, long userId) {
        return RestClient.with(context)
            .create(UserService.class)
            .activity(userId);
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