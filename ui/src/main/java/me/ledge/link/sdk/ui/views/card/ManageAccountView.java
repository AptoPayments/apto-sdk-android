package me.ledge.link.sdk.ui.views.card;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import me.ledge.common.adapters.recyclerview.PagedListRecyclerAdapter;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.models.card.FundingSourceModel;

/**
 * Displays the manage account screen.
 * @author Adrian
 */
public class ManageAccountView
        extends CoordinatorLayout {

    private ViewListener mListener;
    private RecyclerView mFundingSourcesListView;

    /*private ProgressBar mSpinner;*/

    public ManageAccountView(Context context) {
        this(context, null);
    }

    public ManageAccountView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Callbacks this {@link View} will invoke.
     */
    public interface ViewListener {

    }
    public void setViewListener(ViewListener viewListener) {
        mListener = viewListener;
    }

    /** {@inheritDoc} */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        findAllViews();
        setUpListeners();
        setupRecyclerView();
    }

    protected void findAllViews() {
        //mSpinner = (ProgressBar) findViewById(R.id.pb_progress);
        mFundingSourcesListView = (RecyclerView) findViewById(R.id.rv_funding_sources_list);
    }

    private void setUpListeners() {

    }

    /**
     * Sets up the {@link RecyclerView}.
     */
    private void setupRecyclerView() {
        mFundingSourcesListView.setHasFixedSize(true);
        mFundingSourcesListView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    /**
     * Stores a new {@link PagedListRecyclerAdapter} for the {@link RecyclerView} to use.
     * @param adapter The adapter to use.
     */
    public void setAdapter(PagedListRecyclerAdapter<FundingSourceModel, FundingSourceView> adapter) {
        mFundingSourcesListView.setAdapter(adapter);
    }

    public void showLoading(boolean show) {
        /*if(show) {
            mSpinner.setVisibility(View.VISIBLE);
        }
        else {
            mSpinner.setVisibility(View.GONE);
        }*/
    }
}
