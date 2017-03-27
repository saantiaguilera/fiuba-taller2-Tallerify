package com.u.tallerify.view.home.cards;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.ViewGroup;
import com.jakewharton.rxbinding.view.RxView;
import com.u.tallerify.R;
import com.u.tallerify.contract.abstracts.cards.NoAccountCardContract;
import rx.Observable;

/**
 * Created by saguilera on 3/12/17.
 */
public class NoAccountCardView extends CardView
    implements NoAccountCardContract.View {

    public NoAccountCardView(final Context context) {
        this(context, null);
    }

    public NoAccountCardView(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NoAccountCardView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        inflate(context, R.layout.view_card_no_account, this);

        RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            getResources().getDimensionPixelSize(R.dimen.view_card_no_account_height));
        setLayoutParams(params);

        setRadius(getResources().getDimensionPixelSize(R.dimen.view_card_no_account_radius));
        setCardElevation(getResources().getDimensionPixelSize(R.dimen.view_card_no_account_elevation));
    }

    @Override
    public Observable<Void> observeCreateAccountsClicks() {
        return RxView.clicks(this);
    }

}
