package com.u.tallerify.view.base.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.widget.EditText;
import android.widget.LinearLayout;
import com.u.tallerify.R;

import static java.lang.Integer.MAX_VALUE;

public final class InputTextView extends LinearLayout {

    /**
     * Views
     */
    TextInputLayout container;
    private TextInputEditText input;

    /**
     * Attributes
     */
    private int maxLines;
    private int maxCharacters;
    private boolean charactersCountVisible;
    private String hint;
    private boolean enabled = true;

    /**
     * TextField constructor for code usage
     *
     * @param context the context
     */
    public InputTextView(final Context context) {
        super(context);
        init(context, null, 0);
    }

    /**
     * TextField constructor for XML usage
     *
     * @param context the context
     * @param attrs   the attributes
     */
    public InputTextView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    /**
     * TextField constructor for XML usage
     *
     * @param context      the context
     * @param attrs        the attributes
     * @param defStyleAttr the default attributes
     */
    public InputTextView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    /**
     * TextField constructor for XML usage
     *
     * @param context      the context
     * @param attrs        the attributes
     * @param defStyleAttr the default attributes
     * @param defStyleRes  the default style resource
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public InputTextView(final Context context, final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr);
    }

    /**
     * Inits all component variables
     * @param context       The android context
     * @param attrs         The attributes setted by XML
     * @param defStyleAttr  The default style
     */
    private void init(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        inflate(context, R.layout.view_input_text, this);
        setOrientation(VERTICAL);

        container = (TextInputLayout) findViewById(R.id.view_input_text_container);
        input = (TextInputEditText) findViewById(R.id.view_input_text_input);

        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.InputTextView, defStyleAttr, 0);
        hint = a.getString(R.styleable.InputTextView_inputTextView_Hint);
        maxLines = a.getInt(R.styleable.InputTextView_inputTextView_MaxLines, MAX_VALUE);
        maxCharacters = a.getInt(R.styleable.InputTextView_inputTextView_MaxCharacters, MAX_VALUE);
        charactersCountVisible = a.getBoolean(R.styleable.InputTextView_inputTextView_CharactersCountVisible, false);
        enabled = a.getBoolean(R.styleable.InputTextView_inputTextView_Enabled, true);

        final Drawable passwordDrawable = a.getDrawable(R.styleable.InputTextView_inputTextView_PasswordToggleDrawable);
        if (passwordDrawable == null) {
            setPasswordVisibilityToggleEnabled(a.getBoolean(R.styleable.InputTextView_inputTextView_PasswordToggleEnabled, false));
        } else {
            setPasswordVisibilityToggleDrawable(passwordDrawable);
            setPasswordVisibilityToggleTint(a.getColorStateList(R.styleable.InputTextView_inputTextView_PasswordToggleTint));
            setPasswordVisibilityToggleEnabled(a.getBoolean(R.styleable.InputTextView_inputTextView_PasswordToggleEnabled, true));
        }

        final Drawable left = a.getDrawable(R.styleable.InputTextView_inputTextView_DrawableLeft);
        final Drawable top = a.getDrawable(R.styleable.InputTextView_inputTextView_DrawableTop);
        final Drawable right = a.getDrawable(R.styleable.InputTextView_inputTextView_DrawableRight);
        final Drawable bottom = a.getDrawable(R.styleable.InputTextView_inputTextView_DrawableBottom);

        setCompoundDrawables(left, top, right, bottom);
        setEllipsize(a.getInt(R.styleable.InputTextView_inputTextView_Ellipsize, 0));
        setInputType(a.getInt(R.styleable.InputTextView_inputTextView_InputType, InputType.TYPE_CLASS_TEXT));

        a.recycle();

        init();
    }

    /**
     * With all the variables initialized, init the component by setting them
     */
    private void init() {
        //Set the different components
        setHint(hint);

        setMaxLines(maxLines);
        setMaxCharacters(maxCharacters);
        setEnabled(enabled);
        setCharactersCountVisible(charactersCountVisible);

        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(final CharSequence s, final int start, final int count, final int after) {
                // Do nothing
            }

            @Override
            public void onTextChanged(final CharSequence s, final int start, final int before, final int count) {
                //Do nothing
            }

            @Override
            public void afterTextChanged(final Editable s) {
                setError(null);
            }
        });
    }

    /**
     * Setter for the hint animation
     *
     * @param isEnabled is the hint animation enabled or not
     */
    public void setHintAnimationEnabled(final boolean isEnabled) {
        container.setHintEnabled(isEnabled);
        container.setHintAnimationEnabled(isEnabled);
        // We have to re set the hint to the corresponding view
        setHint(hint);
    }

    /**
     * Getter for the hint animation
     *
     * @return the hint animation
     */
    public boolean isHintAnimationEnabled() {
        return container.isHintEnabled() && container.isHintAnimationEnabled();
    }

    /**
     * Setter for the hint
     *
     * @param hint the hint to set
     */
    public void setHint(final String hint) {
        if (container.isHintEnabled()) {
            container.setHint(hint);
        } else {
            input.setHint(hint);
        }

        container.setHintTextAppearance(R.style.InputTextViewHint);
    }

    /**
     * Setters for the hint
     *
     * @param hint the hint to set
     */
    public void setHint(@StringRes final int hint) {
        setHint(getResources().getString(hint));
    }

    /**
     * Getter for the hint
     *
     * @return the hint
     */
    public String getHint() {
        return TextUtils.isEmpty(container.getHint()) ? "" : container.getHint().toString();
    }

    /**
     * Setter for the error
     *
     * @param error the error
     */
    public void setError(final String error) {
        if (isEnabled()) {
            try {
                container.setErrorEnabled(!TextUtils.isEmpty(error));
                container.setError(error);
            } catch (UnsupportedOperationException ex) {
                // Silent
            }
        }
    }

    /**
     * Setter for the error
     *
     * @param error the error
     */
    public void setError(@StringRes final int error) {
        setError(getResources().getString(error));
    }

    /**
     * Getter for the error
     *
     * @return the error
     */
    public String getError() {
        return TextUtils.isEmpty(container.getError()) ? "" : container.getError().toString();
    }

    /**
     * Setter for the enabled state
     *
     * @param isEnabled the enabled state
     */
    public void setEnabled(final boolean isEnabled) {
        this.enabled = isEnabled;
        if (enabled) {
            container.setFocusableInTouchMode(true);
            container.setEnabled(true);
            setMaxCharacters(maxCharacters);
        } else {
            container.setFocusableInTouchMode(false);
            container.setEnabled(false);
            setCharactersCountVisible(false);
            setError(null);
        }
    }

    /**
     * Getter for the enabled state
     *
     * @return the enabled state
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Setter for the text
     *
     * @param text the text
     */
    public void setText(final String text) {
        input.setText(text);
    }

    /**
     * Setter for the text
     *
     * @param text the text
     */
    public void setText(@StringRes final int text) {
        setText(getResources().getString(text));
    }

    /**
     * Getter for the text
     *
     * @return the text
     */
    public String getText() {
        return input.getText().toString();
    }

    /**
     * Sets the maximum number of lines.
     *
     * @param maxLines the maximum number of lines.
     */
    public void setMaxLines(final int maxLines) {
        if (maxLines == 0) {
            this.maxLines = MAX_VALUE;
        } else {
            this.maxLines = maxLines;
        }
        input.setMaxLines(this.maxLines);
    }

    @Override
    public void setFocusableInTouchMode(final boolean focusableInTouchMode) {
        input.setFocusableInTouchMode(focusableInTouchMode);
    }

    /**
     * Sets the maximum number of characters.
     *
     * @param maxChars the maximum number of characters.
     */
    public void setMaxCharacters(final int maxChars) {
        maxCharacters = maxChars;
        if (maxCharacters == MAX_VALUE) {
            setCharactersCountVisible(false);
        } else {
            final InputFilter[] filterArray = new InputFilter[1];
            filterArray[0] = new InputFilter.LengthFilter(maxCharacters);
            input.setFilters(filterArray);
            setCharactersCountVisible(true);
            container.setCounterMaxLength(maxChars);
        }
    }

    /**
     * Sets the visibility of the character counter
     *
     * @param isVisible the new visibility
     */
    public void setCharactersCountVisible(final boolean isVisible) {
        this.charactersCountVisible = isVisible;
        container.setCounterEnabled(charactersCountVisible);
    }

    @Override
    public boolean requestFocus(final int direction, final Rect previouslyFocusedRect) {
        return input.requestFocus();
    }

    /**
     * Set the ellipsize for the input
     *
     * @param ellipsize the new ellipsize
     */
    public void setEllipsize(final TextUtils.TruncateAt ellipsize) {
        input.setEllipsize(ellipsize);
    }

    /**
     * Set the input type.
     *
     * @param type the new input type
     */
    public void setInputType(final int type) {
        input.setInputType(type);
    }

    /**
     * Check if the view is focused
     * @return true if the view is focused, false otherwise
     */
    public boolean isFocused() {
        return super.isFocused() || input.isFocused() || container.isFocused();
    }

    /**
     * Adds a TextWatcher to the list of those whose methods are called
     * whenever this TextView's text changes.
     *
     * @param watcher the text watcher to add
     */
    public void addTextChangedListener(final TextWatcher watcher) {
        input.addTextChangedListener(watcher);
    }

    /**
     * Get the EditText component.
     *
     * @return the edit text
     */
    public EditText getEditText() {
        return input;
    }

    /**
     * Set drawables to the sides of the edit text
     *
     * @param left   left drawable or 0 to show nothing
     * @param top    top drawable or 0 to show nothing
     * @param right  right drawable or 0 to show nothing
     * @param bottom bottom drawable or 0 to show nothing
     */
    public void setCompoundDrawables(@DrawableRes final int left,
                                     @DrawableRes final int top,
                                     @DrawableRes final int right,
                                     @DrawableRes final int bottom) {
        input.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
    }

    /**
     * Set drawables to the sides of the edit text
     *
     * @param left   left drawable or null to show nothing
     * @param top    top drawable or null to show nothing
     * @param right  right drawable or null to show nothing
     * @param bottom bottom drawable or null to show nothing
     */
    public void setCompoundDrawables(@Nullable final Drawable left,
                                     @Nullable final Drawable top,
                                     @Nullable final Drawable right,
                                     @Nullable final Drawable bottom) {
        input.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
    }

    /**
     * Set a drawable for the password visibility toggle
     *
     * @param toggleDrawable The drawable to set
     */
    public void setPasswordVisibilityToggleDrawable(final Drawable toggleDrawable) {
        container.setPasswordVisibilityToggleDrawable(toggleDrawable);
    }

    /**
     * Set a drawable for the password visibility toggle
     *
     * @param toggleDrawableRes The drawable to set
     */
    public void setPasswordVisibilityToggleDrawable(@DrawableRes final int toggleDrawableRes) {
        container.setPasswordVisibilityToggleDrawable(toggleDrawableRes);
    }

    /**
     * Set a tint for the toggle drawable
     *
     * @param toggleTint the color list to use with the drawable
     */
    public void setPasswordVisibilityToggleTint(final ColorStateList toggleTint) {
        container.setPasswordVisibilityToggleTintList(toggleTint);
    }

    /**
     * Set a tint for the toggle drawable
     *
     * @param toggleTint the color list to use with the drawable
     */
    public void setPasswordVisibilityToggleTint(@ColorRes final int toggleTint) {
        setPasswordVisibilityToggleTint(ContextCompat.getColorStateList(getContext(), toggleTint));
    }

    /**
     * Set if the password toggle is enabled or not
     *
     * @param enabled is enabled or not
     */
    public void setPasswordVisibilityToggleEnabled(final boolean enabled) {
        container.setPasswordVisibilityToggleEnabled(enabled);
    }

    private void setEllipsize(final int ellipsize) {
        switch (ellipsize) {
            case 1:
                setEllipsize(TextUtils.TruncateAt.START);
                break;
            case 2:
                setEllipsize(TextUtils.TruncateAt.MIDDLE);
                break;
            case 3:
                setEllipsize(TextUtils.TruncateAt.END);
                break;
            case 4:
                setEllipsize(TextUtils.TruncateAt.MARQUEE);
                break;
        }
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        final Parcelable superState = super.onSaveInstanceState();
        return new SavedState(superState, getText(), getError(), maxLines, maxCharacters, charactersCountVisible, getHint(), enabled);
    }

    @Override
    protected void onRestoreInstanceState(final Parcelable state) {
        final SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());

        maxLines = savedState.linesNumber;
        maxCharacters = savedState.charactersNumber;
        charactersCountVisible = savedState.charactersVisible;
        hint = savedState.hint;
        enabled = savedState.enabled;
        init();
        setText(savedState.inputText);
        if (!TextUtils.isEmpty(savedState.errorText) && isEnabled()) {
            setError(savedState.errorText);
        }
    }

    @Override
    protected void dispatchSaveInstanceState(@NonNull final SparseArray<Parcelable> container) {
        super.dispatchFreezeSelfOnly(container);
    }

    @Override
    protected void dispatchRestoreInstanceState(@NonNull final SparseArray<Parcelable> container) {
        super.dispatchThawSelfOnly(container);
    }

    protected static class SavedState extends BaseSavedState {

        public static final Parcelable.Creator<SavedState> CREATOR = new Creator<SavedState>() {

            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }

        };

        final boolean enabled;
        final String inputText;
        final String hint;
        final String errorText;
        final int linesNumber;
        final int charactersNumber;
        final boolean charactersVisible;

        SavedState(final Parcelable superState, final String inputText, final String errorText,
            final
            int linesNumber, final int charactersNumber, final boolean charactersVisible, final String hint,
            final boolean enabled) {
            super(superState);
            this.inputText = inputText;
            this.errorText = errorText;
            this.linesNumber = linesNumber;
            this.charactersNumber = charactersNumber;
            this.charactersVisible = charactersVisible;
            this.hint = hint;
            this.enabled = enabled;
        }

        public SavedState(final Parcel source) {
            super(source);
            this.inputText = source.readString();
            this.errorText = source.readString();
            this.linesNumber = source.readInt();
            this.charactersNumber = source.readInt();
            this.charactersVisible = source.readInt() == 1;
            this.hint = source.readString();
            this.enabled = source.readInt() == 1;
        }

        @Override
        public void writeToParcel(@NonNull final Parcel destination, final int flags) {
            super.writeToParcel(destination, flags);
            destination.writeString(inputText);
            destination.writeString(errorText);
            destination.writeInt(linesNumber);
            destination.writeInt(charactersNumber);
            destination.writeInt(charactersVisible ? 1 : 0);
            destination.writeString(hint);
            destination.writeInt(enabled ? 1 : 0);
        }

    }

}