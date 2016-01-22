package us.ledge.line.sdk.sdk.presenters;

import android.view.View;
import us.ledge.line.sdk.sdk.models.Model;

/**
 * Presenter in the MVP pattern.
 * @author Wijnand
 */
public interface Presenter<V extends View, M extends Model> {

    /**
     * Attaches a new {@link View} to this {@link Presenter}.
     * @param view The {@link View} to attach.
     */
    void attachView(V view);

    /**
     * Detaches the current {@link View} from this {@link Presenter}.
     */
    void detachView();
}
