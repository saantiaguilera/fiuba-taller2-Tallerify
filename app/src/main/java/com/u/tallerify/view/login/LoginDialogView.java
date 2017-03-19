package com.u.tallerify.view.login;

import android.animation.Animator;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.method.LinkMovementMethod;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.jakewharton.rxbinding.view.RxView;
import com.u.tallerify.R;
import com.u.tallerify.contract.login.LoginContract;
import com.u.tallerify.utils.TextUtils;
import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Created by saguilera on 3/12/17.
 */
public class LoginDialogView extends LinearLayout implements LoginContract.View {

    public static final float ALPHA_START = 1f;
    public static final float ALPHA_END = 0f;

    private @NonNull TextView readTCView;
    private @NonNull ImageView facebookButton;
    @NonNull TextView errorView;

    @Nullable PublishSubject<Void> termsAndConditionsListener;

    public LoginDialogView(final Context context) {
        super(context);

        inflate(context, R.layout.view_dialog_login, this);

        setOrientation(VERTICAL);
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        readTCView = (TextView) findViewById(R.id.view_dialog_login_terms_and_conditions);
        facebookButton = (ImageView) findViewById(R.id.view_dialog_login_facebook);
        errorView = (TextView) findViewById(R.id.view_dialog_login_error);

        readTCView.setMovementMethod(LinkMovementMethod.getInstance());
        readTCView.setHighlightColor(Color.TRANSPARENT);
        readTCView.setText(TextUtils.createClickableSpan(getResources().getString(R.string.view_dialog_login_register_terms_and_conditions),
            new TextUtils.BaseClickableSpan(getContext()) {
                @Override
                public void onClick(final View widget) {
                    if (termsAndConditionsListener != null) {
                        termsAndConditionsListener.onNext(null);
                    }
                }
            }), TextView.BufferType.SPANNABLE);
    }

    public LoginDialogView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        throw new IllegalStateException("This view doesnt support xml injection. Use is programatical only");
    }

    public LoginDialogView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        throw new IllegalStateException("This view doesnt support xml injection. Use is programatical only");
    }

    @NonNull
    @Override
    public Observable<Void> observeTermsAndConditionsClicks() {
        if (termsAndConditionsListener == null) {
            termsAndConditionsListener = PublishSubject.create();
        }

        return termsAndConditionsListener;
    }

    @NonNull
    @Override
    public Observable<Void> observeFacebookLoginClicks() {
        return RxView.clicks(facebookButton);
    }

    @Override
    public void showError() {
        errorView.setVisibility(View.VISIBLE);
        errorView.animate()
            .alpha(ALPHA_END)
            .setDuration(getResources().getInteger(R.integer.view_dialog_login_error_animation_duration))
            .setStartDelay(getResources().getInteger(R.integer.view_dialog_login_error_visibility_duration))
            .setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(final Animator animation) {}

                @Override
                public void onAnimationEnd(final Animator animation) {
                    errorView.setVisibility(View.GONE);
                    errorView.setAlpha(ALPHA_START);
                }

                @Override
                public void onAnimationCancel(final Animator animation) {}

                @Override
                public void onAnimationRepeat(final Animator animation) {}
            }).start();
    }

}
