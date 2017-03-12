package com.u.tallerify.view.home.cards;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;
import com.u.tallerify.R;
import com.u.tallerify.contract.home.cards.HeaderCardContract;

/**
 * Created by saguilera on 3/12/17.
 */
public class HeaderCardView extends TextView
        implements HeaderCardContract.View {

    public HeaderCardView(final Context context) {
        super(context);

        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT));
        setGravity(Gravity.CENTER_VERTICAL);
        setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.transparent, null));
        setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(R.dimen.view_card_header_text_size));
        setTextColor(ResourcesCompat.getColor(getResources(), R.color.black, null));
    }

    public HeaderCardView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        throw new IllegalStateException("This view cant be used from XML");
    }

    public HeaderCardView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        throw new IllegalStateException("This view cant be used from XML");
    }

    public HeaderCardView(final Context context, final AttributeSet attrs, final int defStyleAttr,
        final int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        throw new IllegalStateException("This view cant be used from XML");
    }

    @Override
    public void setTitle(@NonNull final CharSequence charSequence) {
        setText(charSequence);
    }

}
