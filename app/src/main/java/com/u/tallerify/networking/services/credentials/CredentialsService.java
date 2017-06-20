package com.u.tallerify.networking.services.credentials;

import android.support.annotation.NonNull;
import java.io.Serializable;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Api Service for the login endpoints
 *
 * Created by saguilera on 3/12/17.
 */
@SuppressWarnings("unused")
public interface CredentialsService {

    String PATH_URL = "tokens";

    String GRANT_TYPE_CREATE = "assertion";
    String GRANT_TYPE_REFRESH = "refresh_token";
    String GRANT_TYPE_LOGIN = "assertion";

    /**
     * Post to create an accessToken.
     *
     * #NO AUTH
     *
     * @return Application Access Token.
     */
    @POST(PATH_URL)
    Observable<String> withProvider(@Body CreateCredentialForm body);

    /**
     * Post to create an accessToken
     *
     * #NO AUTH
     *
     * @return Application Access Token
     */
    @POST(PATH_URL)
    Observable<String> withNative(@Body CreateNativeForm body);

    /**
     * Post to refresh an access token
     *
     * #NO AUTH
     *
     * @return Application refreshed Access Token
     */
    @POST(PATH_URL)
    Observable<String> refresh(@Body RefreshCredentialForm body);

    /**
     * Class for the withProvider with request body
     */
    class CreateCredentialForm implements Serializable {

        private @NonNull String userId;
        private @NonNull String authToken;

        public CreateCredentialForm(@NonNull String accessToken,
            @NonNull String userId) {
            this.authToken = accessToken;
            this.userId = userId;
        }

    }

    class CreateNativeForm implements Serializable {

        private @NonNull String userName;
        private @NonNull String password;

        public CreateNativeForm(@NonNull String userName,
            @NonNull String password) {
            this.userName = userName;
            this.password = password;
        }

    }

    /**
     * Class for the refresh request body
     */
    class RefreshCredentialForm implements Serializable {

        private @NonNull String grantType = GRANT_TYPE_REFRESH;
        private @NonNull String refreshToken;

        public RefreshCredentialForm(@NonNull String refreshToken) {
            this.refreshToken = refreshToken;
        }

    }

}
