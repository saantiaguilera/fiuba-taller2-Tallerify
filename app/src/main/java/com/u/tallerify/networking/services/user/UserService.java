package com.u.tallerify.networking.services.user;

import com.u.tallerify.model.entity.Song;
import com.u.tallerify.model.entity.User;
import java.util.List;
import java.util.Map;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by saguilera on 3/12/17.
 */
public interface UserService {

    @GET("users/me")
    Observable<User> currentUser();

    @GET("users/{id}")
    Observable<User> user(@Path("id") long id);

    @Multipart
    @POST("users/")
    Observable<User> create(@PartMap Map<String, RequestBody> params);

    @GET("users/{id}/activity")
    Observable<List<Song>> activity(@Path("id") long id);

    @POST("users/{id}/follow")
    Observable<User> follow(@Path("id") long hisId);

    @DELETE("users/{id}/follow")
    Observable<Void> unfollow(@Path("id") long hisId);

    @GET("users/search")
    Observable<List<User>> queryUsers(@Query("query") String query);

}
