package com.u.tallerify.view.base.cards;

import android.content.Context;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.u.tallerify.R;
import com.u.tallerify.contract.base.cards.ContactCardContract;
import com.u.tallerify.supplier.card.PlayableCardSupplier;
import com.u.tallerify.utils.FrescoImageController;
import com.u.tallerify.utils.MetricsUtils;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Created by saguilera on 4/26/17.
 */
public class ContactCardView extends CardView
        implements ContactCardContract.View {

    public static final int ACTION_ADD = 0;
    public static final int ACTION_DELETE = 1;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({ACTION_ADD, ACTION_DELETE})
    public @interface Action {}

    private static int bestDimen = 0;

    private @NonNull SimpleDraweeView imageView;
    private @NonNull ImageView actionView;

    @Action int action;

    @Nullable PublishSubject<Void> addSubject;
    @Nullable PublishSubject<Void> removeSubject;

    public ContactCardView(final Context context) {
        this(context, null);
    }

    public ContactCardView(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ContactCardView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        inflate(context, R.layout.view_card_contact, this);

        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
            computeBestDimen(),
            computeBestDimen());
        setLayoutParams(params);

        setRadius(computeBestDimen() / 2);
        setCardElevation(getResources().getDimensionPixelSize(R.dimen.view_card_contact_elevation));

        actionView = (ImageView) findViewById(R.id.view_card_contact_action);
        imageView = (SimpleDraweeView) findViewById(R.id.view_card_contact_user_image);

        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                switch (action) {
                    case ACTION_ADD:
                        if (addSubject != null) {
                            addSubject.onNext(null);
                        }
                        setAction(ACTION_DELETE);
                        break;
                    case ACTION_DELETE:
                        if (removeSubject != null) {
                            removeSubject.onNext(null);
                        }
                        setAction(ACTION_ADD);
                }

            }
        });

        setAction(ACTION_ADD);
    }

    @Override
    public void setImage(@NonNull final String url) {
        FrescoImageController.create()
            .load(url)
            .into(imageView);
    }

    @Override
    public void setAction(@Action final int action) {
        this.action = action;
        actionView.setImageResource(action == ACTION_ADD ?
            R.drawable.ic_add_circle_outline_black_36dp :
            R.drawable.ic_remove_circle_outline_black_36dp);
    }

    @NonNull
    @Override
    public Observable<Void> observeAddClicks() {
        if (addSubject == null) {
            addSubject = PublishSubject.create();
        }
        return addSubject;
    }

    @NonNull
    @Override
    public Observable<Void> observeRemoveClicks() {
        if (removeSubject == null) {
            removeSubject = PublishSubject.create();
        }
        return removeSubject;
    }

    private int computeBestDimen() {
        if (bestDimen != 0) return bestDimen;

        int screenWidth = MetricsUtils.getScreenPixelBounds(getContext()).x;
        int columns = PlayableCardSupplier.SONGS_PER_ROW;

        screenWidth -= (2 * getResources().getDimensionPixelSize(R.dimen.home_item_paddings));
        screenWidth -= ((columns + 1) * getResources().getDimensionPixelSize(R.dimen.home_item_paddings));

        return bestDimen = (screenWidth / columns);
    }

}