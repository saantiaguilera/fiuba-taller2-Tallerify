package com.u.tallerify.view.base.cards;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.TextView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.u.tallerify.R;
import com.u.tallerify.contract.base.cards.PlayableCardContract;
import com.u.tallerify.supplier.home.card.PlayableCardSupplier;
import com.u.tallerify.utils.FrescoImageController;
import com.u.tallerify.utils.MetricsUtils;

/**
 * Created by saguilera on 3/12/17.
 */
public class PlayableCardView extends CardView
        implements PlayableCardContract.View {

    private static int bestDimen = 0;

    private @NonNull TextView nameView;
    private @NonNull SimpleDraweeView imageView;

    public PlayableCardView(final Context context) {
        this(context, null);
    }

    public PlayableCardView(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PlayableCardView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        inflate(context, R.layout.view_card_playable, this);

        RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(
            computeBestDimen(),
            computeBestDimen());
        setLayoutParams(params);

        setRadius(getResources().getDimensionPixelSize(R.dimen.view_card_song_radius));
        setCardElevation(getResources().getDimensionPixelSize(R.dimen.view_card_song_elevation));

        nameView = (TextView) findViewById(R.id.view_card_playable_name);
        imageView = (SimpleDraweeView) findViewById(R.id.view_card_playable_image);
    }

    @Override
    public void setImage(@Nullable final String url) {
        if (url != null) {
            FrescoImageController.create()
                .load(url)
                .into(imageView);
        } else {
            FrescoImageController.create()
                .load(R.drawable.placeholder_song)
                .into(imageView);
        }
    }

    @Override
    public void setName(@NonNull final String name) {
        nameView.setText(name);
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
