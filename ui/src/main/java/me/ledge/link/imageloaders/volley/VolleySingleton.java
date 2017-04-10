package me.ledge.link.imageloaders.volley;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Singleton that encapsulates Volley functionality.
 * @see <a href="https://developer.android.com/training/volley/requestqueue.html#singleton">RequestQueue docs</a>
 * @author wijnand
 */
public class VolleySingleton {

    private static VolleySingleton mInstance;
    private static Context mContext;

    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    /**
     * Creates a new {@link VolleySingleton} instance.
     * @param context The {@link Context} to use to create a new cache and request queue.
     */
    private VolleySingleton(Context context) {
        mContext = context;
        init();
    }

    /**
     * Initializes this class.
     */
    private void init() {
        mRequestQueue = getRequestQueue();
        mImageLoader = new ImageLoader(mRequestQueue, new LruBitmapCache(mContext));
    }

    /**
     * @param context The {@link Context} to use to create a new cache and request queue.
     * @return The single instance of this class.
     */
    public static synchronized VolleySingleton getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new VolleySingleton(context);
        }
        return mInstance;
    }

    /**
     * @return The {@link RequestQueue}.
     */
    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the Activity or BroadcastReceiver if
            // someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }

        return mRequestQueue;
    }

    /**
     * Adds a {@link Request} to the {@link RequestQueue}.
     * @param request The {@link Request} to add.
     * @param <T> Expected response type.
     */
    public <T> void addToRequestQueue(Request<T> request) {
        getRequestQueue().add(request);
    }

    /**
     * @return The {@link ImageLoader}.
     */
    public ImageLoader getImageLoader() {
        return mImageLoader;
    }

}
