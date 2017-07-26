package me.ledge.link.sdk.ui.views.userdata;

import android.content.Context;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.views.LoadingView;
import me.ledge.link.sdk.ui.views.ViewWithIndeterminateLoading;
import me.ledge.link.sdk.ui.views.ViewWithToolbar;
import me.ledge.link.sdk.ui.widgets.steppers.StepperListener;
import us.feras.mdv.MarkdownView;

/**
 * Displays the terms and conditions screen.
 * @author Wijnand
 */
public class TermsView extends UserDataView<TermsView.ViewListener>
        implements ViewWithToolbar, ViewWithIndeterminateLoading {

    /**
     * Callbacks this {@link View} will invoke.
     */
    public interface ViewListener extends StepperListener, NextButtonListener {}

    private TextView mTermsField;
    private LoadingView mLoadingView;
    private MarkdownView mMarkdownView;

    /**
     * @see UserDataView#UserDataView
     * @param context See {@link UserDataView#UserDataView}.
     */
    public TermsView(Context context) {
        super(context);
    }

    /**
     * @see UserDataView#UserDataView
     * @param context See {@link UserDataView#UserDataView}.
     * @param attrs See {@link UserDataView#UserDataView}.
     */
    public TermsView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /** {@inheritDoc} */
    @Override
    protected void findAllViews() {
        super.findAllViews();

        mMarkdownView = (MarkdownView) findViewById(R.id.md_terms);
        mTermsField = (TextView) findViewById(R.id.tv_terms);
        mTermsField.setMovementMethod(LinkMovementMethod.getInstance());

        mLoadingView = (LoadingView) findViewById(R.id.rl_loading_overlay);
    }

    @Override
    public LoadingView getLoadingView() {
        return mLoadingView;
    }

    public void setTerms(String disclaimer) {
        mTermsField.setText(Html.fromHtml(disclaimer));
    }

    public void setMarkDownTerms(String disclaimer) {
        mMarkdownView.loadMarkdown(disclaimer);
    }
}
