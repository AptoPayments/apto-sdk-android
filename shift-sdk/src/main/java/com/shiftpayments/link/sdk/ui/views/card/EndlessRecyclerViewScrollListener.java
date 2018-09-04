package com.shiftpayments.link.sdk.ui.views.card;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

/**
 * Source: https://gist.github.com/nesquena/d09dc68ff07e845cc622
 */

public abstract class EndlessRecyclerViewScrollListener extends RecyclerView.OnScrollListener {
    // The minimum amount of items to have below your current scroll position
    // before loading more.
    private static final int VISIBLE_THRESHOLD = 3;
    // The current offset index of data you have loaded
    private int currentPage = 0;
    // The total number of items in the dataset after the last load
    private int previousTotalItemCount = 1;
    // True if we are still waiting for the last set of data to load.
    private boolean loading = true;
    // Sets the starting page index
    private int startingPageIndex = 0;

    private RecyclerView.LayoutManager mLayoutManager;

    public EndlessRecyclerViewScrollListener(LinearLayoutManager layoutManager) {
        this.mLayoutManager = layoutManager;
    }

    public int getLastVisibleItem(int[] lastVisibleItemPositions) {
        int maxSize = 0;
        for (int i = 0; i < lastVisibleItemPositions.length; i++) {
            if (i == 0) {
                maxSize = lastVisibleItemPositions[i];
            }
            else if (lastVisibleItemPositions[i] > maxSize) {
                maxSize = lastVisibleItemPositions[i];
            }
        }
        return maxSize;
    }

    // This happens many times a second during a scroll, so be wary of the code you place here.
    // We are given a few useful parameters to help us work out if we need to load some more data,
    // but first we check if we are waiting for the previous load to finish.
    @Override
    public void onScrolled(RecyclerView view, int dx, int dy) {
        int lastVisibleItemPosition = ((LinearLayoutManager) mLayoutManager).findLastVisibleItemPosition();
        int totalItemCount = mLayoutManager.getItemCount()-2;
        Log.d("ADRIAN", "onScrolled: lastVisibleItemPosition " + lastVisibleItemPosition);
        Log.d("ADRIAN", "onScrolled: totalItemCount " + totalItemCount);
        Log.d("ADRIAN", "onScrolled: currentPage " + this.currentPage);
        Log.d("ADRIAN", "onScrolled: startingPageIndex " + this.startingPageIndex);
        Log.d("ADRIAN", "onScrolled: previousTotalItemCount " + previousTotalItemCount);
        // If the total item count is zero and the previous isn't, assume the
        // list is invalidated and should be reset back to initial state
        if (totalItemCount < previousTotalItemCount) {
            this.currentPage = this.startingPageIndex;
            this.previousTotalItemCount = totalItemCount;
            if (totalItemCount == 0) {
                this.loading = true;
                Log.d("ADRIAN", "onScrolled: loading = true");
            }
        }
        // If it’s still loading, we check to see if the dataset count has
        // changed, if so we conclude it has finished loading and update the current page
        // number and total item count.
        if (this.loading && (totalItemCount > previousTotalItemCount)) {
            Log.d("ADRIAN", "onScrolled: loading = false");
            this.loading = false;
            previousTotalItemCount = totalItemCount;
        }

        // If it isn’t currently loading, we check to see if we have breached
        // the VISIBLE_THRESHOLD and need to reload more data.
        // If we do need to reload some more data, we execute onLoadMore to fetch the data.
        // threshold should reflect how many total columns there are too
        if (!this.loading && (lastVisibleItemPosition + VISIBLE_THRESHOLD) > totalItemCount) {
            Log.d("ADRIAN", "onScrolled: onLoadMore");
            currentPage++;
            this.loading = true;
            onLoadMore(currentPage, totalItemCount, view);
        }
    }

    // Call this method whenever performing new searches
    public void resetState() {
        this.currentPage = this.startingPageIndex;
        this.previousTotalItemCount = 0;
        this.loading = true;
    }

    // Defines the process for actually loading more data based on page
    public abstract void onLoadMore(int page, int totalItemsCount, RecyclerView view);

}