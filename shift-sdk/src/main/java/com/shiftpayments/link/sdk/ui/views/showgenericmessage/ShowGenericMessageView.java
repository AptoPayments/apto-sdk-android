package com.shiftpayments.link.sdk.ui.views.showgenericmessage;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.ShiftPlatform;
import com.shiftpayments.link.sdk.ui.views.ViewWithToolbar;
import com.shiftpayments.link.sdk.ui.views.userdata.NextButtonListener;
import com.shiftpayments.link.sdk.ui.views.userdata.UserDataView;
import com.shiftpayments.link.sdk.ui.widgets.steppers.StepperListener;

import br.tiagohm.markdownview.MarkdownView;

/**
 * Displays the show generic message screen.
 * @author Adrian
 */
public class ShowGenericMessageView extends UserDataView<ShowGenericMessageView.ViewListener>
        implements ViewWithToolbar {

    /**
     * Callbacks this {@link View} will invoke.
     */
    public interface ViewListener extends StepperListener, NextButtonListener {}

    private MarkdownView mMarkdownView;
    private TextView mNextButton;
    private ImageView mImageView;

    /**
     * @see UserDataView#UserDataView
     * @param context See {@link UserDataView#UserDataView}.
     */
    public ShowGenericMessageView(Context context) {
        super(context);
    }

    /**
     * @see UserDataView#UserDataView
     * @param context See {@link UserDataView#UserDataView}.
     * @param attrs See {@link UserDataView#UserDataView}.
     */
    public ShowGenericMessageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /** {@inheritDoc} */
    @Override
    protected void findAllViews() {
        super.findAllViews();

        mMarkdownView = (MarkdownView) findViewById(R.id.md_content);
        mNextButton = (TextView) findViewById(R.id.tv_next_bttn);
        mImageView = (ImageView) findViewById(R.id.iv_image);
    }

    public void setMarkdown(String markdown) {
        mMarkdownView.setBackgroundColor(Color.TRANSPARENT);
        mMarkdownView.loadMarkdown(markdown);
    }

    public void setCallToAction(String title) {
        mNextButton.setVisibility(VISIBLE);
        mNextButton.setText(title);
    }

    public void setImage(String imageUrl) {
        mImageView.setVisibility(VISIBLE);
        ShiftPlatform.getImageLoader().load(imageUrl, mImageView);
    }
}
