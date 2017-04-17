package com.u.tallerify.presenter.abstracts;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import com.u.tallerify.R;
import com.u.tallerify.contract.abstracts.BaseDialogContract;
import com.u.tallerify.presenter.Presenter;
import rx.functions.Action1;
import rx.subjects.PublishSubject;

/**
 * Created by saguilera on 3/12/17.
 */
public class BaseDialogPresenter extends Presenter<BaseDialogContract.View> implements BaseDialogContract.Presenter {

    private @NonNull Severity severity;
    private @NonNull String title;

    private @Nullable View content;
    @Nullable PublishSubject<Void> imageClicksSubject;
    private @Nullable String imageUrl;

    BaseDialogPresenter(@NonNull Builder builder) {
        super();
        this.severity = builder.severity;
        this.title = builder.title;
        this.content = builder.content;
        this.imageUrl = builder.imageUrl;
        this.imageClicksSubject = builder.imageClicksSubject;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onRender(@NonNull final BaseDialogContract.View view) {
        view.setSeverityTitle(title);

        if (content != null) {
            view.setContentView(content);
        }

        if (imageClicksSubject != null) {
            view.observeImageClicks()
                .compose(this.<Void>bindToLifecycle())
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(final Void aVoid) {
                        imageClicksSubject.onNext(aVoid);
                    }
                });
        }

        switch (severity) {
            case ERROR:
                view.setSeverityImage(R.drawable.ic_cancel_black_36dp);
                break;
            case WARNING:
                view.setSeverityImage(R.drawable.ic_warning_black_36dp);
                break;
            case INFO:
                view.setSeverityImage(R.drawable.ic_info_outline_black_36dp);
                break;
            case CUSTOM:
                if (imageUrl != null) {
                    view.setSeverityImageUrl(imageUrl);
                }
        }
    }

    public enum Severity {
        ERROR,
        WARNING,
        INFO,
        CUSTOM
    }

    public static class Builder {

        @Nullable Severity severity;
        @Nullable String title;
        @Nullable View content;
        @Nullable String imageUrl;
        @Nullable PublishSubject<Void> imageClicksSubject;

        public Builder() {}

        public @NonNull Builder severity(@NonNull Severity severity) {
            this.severity = severity;
            return this;
        }

        public @NonNull Builder title(@NonNull String title) {
            this.title = title;
            return this;
        }

        public @NonNull Builder content(@NonNull View content) {
            this.content = content;
            return this;
        }

        public @NonNull Builder imageUrl(@Nullable String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public @NonNull Builder imageClicksSubject(@Nullable PublishSubject<Void> subject) {
            this.imageClicksSubject = subject;
            return this;
        }

        public @NonNull BaseDialogPresenter build() {
            if (severity == null || title == null) {
                throw new IllegalStateException("Missing params");
            }

            return new BaseDialogPresenter(this);
        }

    }

}