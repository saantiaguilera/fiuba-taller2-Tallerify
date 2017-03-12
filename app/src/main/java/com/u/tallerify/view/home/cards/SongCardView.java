package com.u.tallerify.view.home.cards;

import android.content.Context;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import com.u.tallerify.R;
import com.u.tallerify.contract.home.cards.SongCardContract;
import com.u.tallerify.list.home.card.supplier.SongCardSupplier;

/**
 * Created by saguilera on 3/12/17.
 */
public class SongCardView extends CardView
        implements SongCardContract.View {

    private static int bestDimen = 0;

    private @NonNull TextView nameView;
    private @NonNull ImageView imageView;

    public SongCardView(final Context context) {
        this(context, null);
    }

    public SongCardView(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SongCardView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        inflate(context, R.layout.view_card_song, this);

        RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(
            computeBestDimen(),
            computeBestDimen());
        setLayoutParams(params);

        setRadius(getResources().getDimensionPixelSize(R.dimen.view_card_song_radius));
        setCardElevation(getResources().getDimensionPixelSize(R.dimen.view_card_song_elevation));

        nameView = (TextView) findViewById(R.id.view_card_song_name);
        imageView = (ImageView) findViewById(R.id.view_card_song_image);
    }

    @Override
    public void setImage(@NonNull final String url) {
        // TODO
        imageView.setImageResource(R.mipmap.ic_launcher);
    }

    @Override
    public void setName(@NonNull final String name) {
        nameView.setText(name);
    }

    private int computeBestDimen() {
        if (bestDimen != 0) return bestDimen;

        WindowManager wm = (WindowManager) getContext().getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int screenWidth = size.x;
        int columns = SongCardSupplier.SONGS_PER_ROW;

        screenWidth -= (2 * getResources().getDimensionPixelSize(R.dimen.home_item_paddings));
        screenWidth -= ((columns + 1) * getResources().getDimensionPixelSize(R.dimen.home_item_paddings));

        return bestDimen = (screenWidth / columns);
    }

}
