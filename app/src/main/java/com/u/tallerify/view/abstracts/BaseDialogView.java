package com.u.tallerify.view.abstracts;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jakewharton.rxbinding.view.RxView;
import com.u.tallerify.R;
import com.u.tallerify.contract.abstracts.BaseDialogContract;
import com.u.tallerify.utils.FrescoImageController;
import java.io.File;
import rx.Observable;

/**
 * Created by saguilera on 3/12/17.
 */

public class BaseDialogView extends LinearLayout implements BaseDialogContract.View {

    private @NonNull SimpleDraweeView severityImage;
    private @NonNull TextView severityTitle;
    private @NonNull ViewGroup container;

    public BaseDialogView(final Context context) {
        super(context);

        inflate(context, R.layout.view_base_dialog, this);

        severityImage = (SimpleDraweeView) findViewById(R.id.view_dialog_severity_image);
        severityTitle = (TextView) findViewById(R.id.view_dialog_severity_title);
        container = (ViewGroup) findViewById(R.id.view_dialog_content);

        setGravity(Gravity.CENTER_HORIZONTAL);
        setOrientation(VERTICAL);
        setBackgroundColor(ContextCompat.getColor(context, R.color.transparent));

        int padding = getResources().getDimensionPixelSize(R.dimen.dialog_padding);
        setPadding(padding, padding, padding, padding);
    }

    public BaseDialogView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        throw new IllegalStateException("This view doesnt support xml injection. Use is programatical only");
    }

    public BaseDialogView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        throw new IllegalStateException("This view doesnt support xml injection. Use is programatical only");
    }

    public BaseDialogView(final Context context, final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        throw new IllegalStateException("This view doesnt support xml injection. Use is programatical only");
    }

    @Override
    public void setContentView(@NonNull View view) {
        container.removeAllViews();
        container.addView(view);
    }

    @Override
    public void setSeverityImage(@DrawableRes int resId) {
        FrescoImageController.create()
            .load(resId)
            .into(severityImage);
    }

    @Override
    public void setSeverityImageUrl(@NonNull final String url) {
        File asLocalFile = null;
        try {
            asLocalFile = new File(url);
            if (!asLocalFile.exists()) {
                asLocalFile = null;
            }
        } catch (Exception e) { /* silent */ }

        if (asLocalFile == null) {
            FrescoImageController.create()
                .load(url)
                .into(severityImage);
        } else {
            FrescoImageController.create()
                .load(asLocalFile)
                .into(severityImage);
        }
    }

    @NonNull
    @Override
    public Observable<Void> observeImageClicks() {
        return RxView.clicks(severityImage);
    }

    @Override
    public void setSeverityTitle(@NonNull String title) {
        severityTitle.setText(title);
    }

}
