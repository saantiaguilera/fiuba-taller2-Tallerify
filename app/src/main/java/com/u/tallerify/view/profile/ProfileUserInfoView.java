package com.u.tallerify.view.profile;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.u.tallerify.R;
import com.u.tallerify.contract.profile.ProfileUserInfoContract;
import com.u.tallerify.utils.FrescoImageController;

/**
 * Created by saguilera on 3/30/17.
 */

public class ProfileUserInfoView extends RelativeLayout
        implements ProfileUserInfoContract.View {

    private @NonNull TextView userNameView;
    private @NonNull TextView userLocationView;
    private @NonNull SimpleDraweeView userImageView;

    public ProfileUserInfoView(final Context context) {
        this(context, null);
    }

    public ProfileUserInfoView(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProfileUserInfoView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        inflate(context, R.layout.view_user_info, this);

        userNameView = (TextView) findViewById(R.id.view_user_info_name);
        userLocationView = (TextView) findViewById(R.id.view_user_info_location);
        userImageView = (SimpleDraweeView) findViewById(R.id.view_user_info_image);
    }

    @Override
    public void setUserName(@NonNull final String name) {
        userNameView.setText(name);
    }

    @Override
    public void setUserLastLocation(@NonNull final String location) {
        userLocationView.setText(location);
    }

    @Override
    public void setUserImage(@NonNull final String url) {
        FrescoImageController.create()
            .load(url)
            .into(userImageView);
    }

}
