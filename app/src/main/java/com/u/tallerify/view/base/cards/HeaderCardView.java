package com.u.tallerify.view.base.cards;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import com.u.tallerify.R;
import com.u.tallerify.contract.base.cards.HeaderCardContract;

/**
 * Created by saguilera on 3/12/17.
 */
public class HeaderCardView extends AppCompatTextView
        implements HeaderCardContract.View {

    public HeaderCardView(final Context context) {
        this(context, null);
    }

    public HeaderCardView(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HeaderCardView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        // Dont care about the setted values, override this ones
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT));
        setGravity(Gravity.CENTER_VERTICAL);
        setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.transparent, null));
        setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(R.dimen.view_card_header_text_size));
        setTextColor(ResourcesCompat.getColor(getResources(), R.color.black, null));
    }

    @Override
    public void setTitle(@NonNull final CharSequence charSequence) {
        setText(charSequence);
    }

}
