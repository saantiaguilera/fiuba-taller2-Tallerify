package com.u.tallerify.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import com.u.tallerify.R;

/**
 * Created by saguilera on 3/12/17.
 */
public class TextUtils {

    private TextUtils() {
    }

    public static SpannableStringBuilder createClickableSpan(String text, BaseClickableSpan... spans) {
        SpannableStringBuilder spanBuilder = new SpannableStringBuilder(text);

        int startIndex = text.indexOf("[");
        int endIndex;

        int counter = 0;

        while (startIndex != -1) {
            endIndex = text.indexOf("]", startIndex);

            spanBuilder.delete(startIndex, startIndex + 1);

            if (spanBuilder.length() == endIndex) {
                spanBuilder.append(" ");
            }

            spanBuilder.delete(endIndex - 1, endIndex);

            spanBuilder.setSpan(spans[counter], startIndex, endIndex, 0);
            startIndex = text.indexOf("[", endIndex);

            counter++;
        }

        return spanBuilder;
    }

    public abstract static class BaseClickableSpan extends ClickableSpan {

        private int color;

        public BaseClickableSpan(@NonNull Context context) {
            color = ContextCompat.getColor(context, R.color.colorPrimary);
        }

        @Override
        public void updateDrawState(TextPaint textPaint) {
            textPaint.setUnderlineText(false);
            textPaint.setColor(this.color);
        }

    }

}
