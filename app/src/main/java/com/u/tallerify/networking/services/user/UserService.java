package com.u.tallerify.networking.services.user;

import com.u.tallerify.model.entity.Artist;
import com.u.tallerify.model.entity.Playlist;
import com.u.tallerify.model.entity.Song;
import com.u.tallerify.model.entity.User;
import java.util.Date;
import java.util.List;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by saguilera on 3/12/17.
 */
public interface UserService {

    @GET("users/me")
    Observable<User> currentUser();

    @GET("users/{id}")
    Observable<User> user(@Path("id") long id);

    @POST("users/")
    Observable<User> create(@Body User user, @Field("password") String password);

    @POST("users/{id}/follow")
    Observable<User> follow(@Path("id") long myId, @Field("userId") long hisId);

    @DELETE("users/{id}/follow")
    Observable<Void> unfollow(@Path("id") long myId, @Field("userId") long hisId);

}
