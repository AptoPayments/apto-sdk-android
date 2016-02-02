package us.ledge.link.sdk.sdk.presenters;

import android.view.View;
import us.ledge.link.sdk.sdk.models.Model;

/**
 * Presenter in the MVP pattern.
 *
 * @param <V> {@link View} type.
 * @param <M> {@link Model} type.
 *
 * @author Wijnand
 */
public interface Presenter<M extends Model, V extends View> {

    /**
     * @return A new {@link Model} instance.
     */
    M createModel();

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
