package com.shiftpayments.link.sdk.ui.storages;

import android.content.res.ColorStateList;
import android.graphics.Color;

import com.shiftpayments.link.sdk.api.exceptions.ApiException;
import com.shiftpayments.link.sdk.api.vos.requests.config.GetProjectConfigRequestVo;
import com.shiftpayments.link.sdk.api.vos.responses.config.ConfigResponseVo;
import com.shiftpayments.link.sdk.api.vos.responses.config.ContextConfigResponseVo;
import com.shiftpayments.link.sdk.api.vos.responses.config.TeamConfigResponseVo;
import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.vos.ShiftSdkOptions;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import java8.util.concurrent.CompletableFuture;
import java8.util.concurrent.CompletionException;

import static com.shiftpayments.link.sdk.sdk.ShiftLinkSdk.getApiWrapper;

/**
 * Stores UI related data.
 * @author Adrian
 */
public class UIStorage {

    private Integer mPrimaryColor;
    private Integer mSecondaryColor;
    private ContextConfigResponseVo mConfig;
    private ShiftSdkOptions mSdkOptions;
    private final static String DEFAULT_PRIMARY_COLOR = "9ADE83";
    private final static String DEFAULT_SECONDARY_COLOR = "F2F2F2";
    private static final Map<String, Integer> mIconMap = createIconMap();

    private static UIStorage mInstance;

    /**
     * Creates a new {@link UIStorage} instance.
     */
    private UIStorage() {
    }

    /**
     * @return The single instance of this class.
     */
    public static synchronized UIStorage getInstance() {
        if (mInstance == null) {
            mInstance = new UIStorage();
        }

        return mInstance;
    }

    public void setConfig(ContextConfigResponseVo config) {
        mConfig = config;
        setColors();
    }

    public synchronized Integer getPrimaryColor() {
        if(mPrimaryColor != null) {
            return mPrimaryColor;
        }
        else {
            CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {

                try {
                    setConfig(getApiWrapper().getUserConfig(new GetProjectConfigRequestVo()));
                    return mPrimaryColor;
                } catch (ApiException e) {
                    throw new CompletionException(e);
                }

            });
            if (getResultFromFuture(future) == null) {
                return 0;
            } else {
                return (Integer) getResultFromFuture(future);
            }
        }
    }

    public synchronized Integer getSecondaryColor() {
        if(mSecondaryColor != null) {
            return mSecondaryColor;
        }
        else {
            CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {

                try {
                    setConfig(getApiWrapper().getUserConfig(new GetProjectConfigRequestVo()));
                    return mSecondaryColor;
                } catch (ApiException e) {
                    throw new CompletionException(e);
                }

            });
            return (Integer) getResultFromFuture(future);
        }
    }

    public int getStatusBarColor(int actionBarColor) {
        float[] hsv = new float[3];
        Color.colorToHSV(actionBarColor, hsv);
        hsv[2] *= 0.8f;
        return Color.HSVToColor(hsv);
    }

    public synchronized ConfigResponseVo getContextConfig() {
        if (mConfig != null) {
            return mConfig.projectConfiguration;
        }
        else {
            CompletableFuture<ConfigResponseVo> future = CompletableFuture.supplyAsync(() -> {
                try {
                    setConfig(getApiWrapper().getUserConfig(new GetProjectConfigRequestVo()));
                    return mConfig.projectConfiguration;
                } catch (ApiException e) {
                    throw new CompletionException(e);
                }
            });
            return future.join();
        }
    }

    public synchronized TeamConfigResponseVo getTeamConfig() {
        if(mConfig != null) {
            return mConfig.teamConfiguration;
        }
        else {
            CompletableFuture<TeamConfigResponseVo> future = CompletableFuture.supplyAsync(() -> {
                try {
                    setConfig(getApiWrapper().getUserConfig(new GetProjectConfigRequestVo()));
                    return mConfig.teamConfiguration;
                } catch (ApiException e) {
                    throw new CompletionException(e);
                }
            });
            return future.join();
        }
    }

    public synchronized int getPrimaryContrastColor() {
        if(mPrimaryColor==null) {
            return 0;
        }
        int red = Color.red(mPrimaryColor);
        int green = Color.green(mPrimaryColor);
        int blue = Color.blue(mPrimaryColor);
        return getBrightness(red, green, blue) > 200 ? Color.BLACK : Color.WHITE;
    }

    private int getBrightness(int red, int green, int blue) {
        // As per http://stackoverflow.com/a/2241471
        return (int)Math.sqrt(red * red * .299 +
                            green * green * .587 +
                            blue * blue * .114);
    }

    private void setColors() {
        String hexColor = mConfig.projectConfiguration.primaryColor;
        if(hexColor == null) {
            hexColor = DEFAULT_PRIMARY_COLOR;
        }
        mPrimaryColor = convertHexToInt(hexColor);
        hexColor = mConfig.projectConfiguration.secondaryColor;
        if(hexColor == null) {
            hexColor = DEFAULT_SECONDARY_COLOR;
        }
        mSecondaryColor = convertHexToInt(hexColor);
    }

    private int convertHexToInt(String hexColor) {
        return Color.parseColor("#"+hexColor);
    }

    private Object getResultFromFuture(CompletableFuture future) {
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            future.completeExceptionally(e);
            return null;
        }
    }

    public ColorStateList getRadioButtonColors() {
        return new ColorStateList(
                new int[][]{
                        new int[]{-android.R.attr.state_checked},
                        new int[]{android.R.attr.state_checked}
                },
                new int[] {
                        Color.GRAY, mPrimaryColor
                }
        );
    }

    public ColorStateList getSwitchBackgroundColors() {
        int materialGrayColor = Color.parseColor("#bdbdbd");
        int statusBarColor = Color.parseColor("#FFF9F9F9");
        statusBarColor = statusBarColor==mPrimaryColor ? materialGrayColor : statusBarColor;
        return new ColorStateList(
                new int[][]{
                        new int[]{-android.R.attr.state_checked},
                        new int[]{android.R.attr.state_checked}
                },
                new int[] {
                        materialGrayColor, statusBarColor
                }
        );
    }

    public Integer getIcon(String merchantCategory) {
        return mIconMap.get(merchantCategory);
    }

    private static Map<String, Integer> createIconMap()
    {
        Map<String, Integer> iconMap = new HashMap<>();
        iconMap.put("plane", R.drawable.flights);
        iconMap.put("car", R.drawable.car);
        iconMap.put("glass", R.drawable.alcohol);
        iconMap.put("finance", R.drawable.withdraw);
        iconMap.put("food", R.drawable.food);
        iconMap.put("gas", R.drawable.fuel);
        iconMap.put("bed", R.drawable.hotel);
        iconMap.put("medical", R.drawable.medicine);
        iconMap.put("camera", R.drawable.other);
        iconMap.put("card", R.drawable.bank_card);
        iconMap.put("cart", R.drawable.purchases);
        iconMap.put("road", R.drawable.toll_road);
        return iconMap;
    }

    public void setSdkOptions(ShiftSdkOptions sdkOptions) {
        mSdkOptions = sdkOptions;
    }

    public boolean showAddFundingSourceButton() {
        return mSdkOptions.showAddFundingSourceButton;
    }

    public boolean showActivateCardButton() {
        return mSdkOptions.showActivateCardButton;
    }
}
