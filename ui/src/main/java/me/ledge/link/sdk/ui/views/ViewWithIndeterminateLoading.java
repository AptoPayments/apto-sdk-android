package me.ledge.link.sdk.ui.views;

/**
 * Any View with an indeterminate loading overlay.
 * @author Wijnand
 */
public interface ViewWithIndeterminateLoading {

    /**
     * Changes the loading overlay visibility.
     * @param show Whether the loading overlay should be shown.
     */
    void showLoading(boolean show);

}
