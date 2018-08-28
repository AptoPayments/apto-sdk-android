package com.shiftpayments.link.sdk.ui.storages;

import android.content.res.ColorStateList;
import android.graphics.Color;

import com.shiftpayments.link.sdk.api.exceptions.ApiException;
import com.shiftpayments.link.sdk.api.vos.requests.config.GetProjectConfigRequestVo;
import com.shiftpayments.link.sdk.api.vos.responses.config.ConfigResponseVo;
import com.shiftpayments.link.sdk.api.vos.responses.config.ContextConfigResponseVo;
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
    private Integer mTertiaryColor;
    private Integer mErrorColor;
    private Integer mSuccessColor;
    private Integer mTextPrimaryColor;
    private Integer mTextSecondaryColor;
    private Integer mTextTertiaryColor;
    private Integer mTextTopbarColor;
    private Integer mIconPrimaryColor;
    private Integer mIconSecondaryColor;
    private Integer mIconTertiaryColor;
    private ContextConfigResponseVo mConfig;
    private ShiftSdkOptions mSdkOptions;

    // Define default colors here instead of in colors.xml to avoid having to pass in context
    private final static String DEFAULT_PRIMARY_COLOR = "F90D00";
    private final static String DEFAULT_SECONDARY_COLOR = "FF9500";
    private final static String DEFAULT_TERTIARY_COLOR = "FFCC00";
    private final static String DEFAULT_SUCCESS_COLOR = "DB1D0E";
    private final static String DEFAULT_ERROR_COLOR = "FFDC4337";
    private final static String DEFAULT_PRIMARY_TEXT_COLOR = "FF2B2D35";
    private final static String DEFAULT_SECONDARY_TEXT_COLOR = "FF54565F";
    private final static String DEFAULT_TERTIARY_TEXT_COLOR = "FFBBBDBD";
    private final static String DEFAULT_TOPBAR_TEXT_COLOR = "FFFFFF";
    private final static String DEFAULT_ICON_PRIMARY_COLOR = "000000";
    private final static String DEFAULT_ICON_SECONDARY_COLOR = "000000";
    private final static String DEFAULT_ICON_TERTIARY_COLOR = "FFFFFF";
    private static final Map<String, Integer> mIconMap = createIconMap();

    private static UIStorage mInstance;

    /**
     * Creates a new {@link UIStorage} instance.
     */
    private UIStorage() {
        getResultFromFuture(getConfigFuture());
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

    private CompletableFuture<ContextConfigResponseVo> getConfigFuture() {
        CompletableFuture<ContextConfigResponseVo> future = CompletableFuture.supplyAsync(() -> {
            try {
                return getApiWrapper().getUserConfig(new GetProjectConfigRequestVo());
            } catch (ApiException e) {
                throw new CompletionException(e);
            }
        });
        future.thenApply(config -> {
            setConfig(config);
            return config;
        });
        return future;
    }

    public synchronized Integer getPrimaryColor() {
        if(mPrimaryColor == null) {
            mPrimaryColor = convertHexToInt(DEFAULT_PRIMARY_COLOR);
        }
        return mPrimaryColor;
    }

    public synchronized Integer getSecondaryColor() {
        if(mSecondaryColor == null) {
            mSecondaryColor = convertHexToInt(DEFAULT_SECONDARY_COLOR);
        }
        return mSecondaryColor;
    }

    public synchronized Integer getTertiaryColor() {
        if(mTertiaryColor == null) {
            mTertiaryColor = convertHexToInt(DEFAULT_TERTIARY_COLOR);
        }
        return mTertiaryColor;
    }

    public synchronized Integer getSuccessColor() {
        if(mSuccessColor == null) {
            mSuccessColor = convertHexToInt(DEFAULT_SUCCESS_COLOR);
        }
        return mSuccessColor;
    }

    public synchronized Integer getErrorColor() {
        if(mErrorColor == null) {
            mErrorColor = convertHexToInt(DEFAULT_ERROR_COLOR);
        }
        return mErrorColor;
    }

    public synchronized Integer getTextPrimaryColor() {
        if(mTextPrimaryColor == null) {
            mTextPrimaryColor = convertHexToInt(DEFAULT_PRIMARY_TEXT_COLOR);
        }
        return mTextPrimaryColor;
    }

    public synchronized Integer getTextSecondaryColor() {
        if(mTextSecondaryColor == null) {
            mTextSecondaryColor = convertHexToInt(DEFAULT_SECONDARY_TEXT_COLOR);
        }
        return mTextSecondaryColor;
    }

    public synchronized Integer getTextTertiaryColor() {
        if(mTextTertiaryColor == null) {
            mTextTertiaryColor = convertHexToInt(DEFAULT_TERTIARY_TEXT_COLOR);
        }
        return mTextTertiaryColor;
    }

    public synchronized Integer getTextTopbarColor() {
        if(mTextTopbarColor == null) {
            mTextTopbarColor = convertHexToInt(DEFAULT_TOPBAR_TEXT_COLOR);
        }
        return mTextTopbarColor;
    }

    public synchronized Integer getIconPrimaryColor() {
        if(mIconPrimaryColor == null) {
            mIconPrimaryColor = convertHexToInt(DEFAULT_ICON_PRIMARY_COLOR);
        }
        return mIconPrimaryColor;
    }

    public synchronized Integer getIconSecondaryColor() {
        if(mIconSecondaryColor == null) {
            mIconSecondaryColor = convertHexToInt(DEFAULT_ICON_SECONDARY_COLOR);
        }
        return mIconSecondaryColor;
    }

    public synchronized Integer getIconTertiaryColor() {
        if(mIconTertiaryColor == null) {
            mIconTertiaryColor = convertHexToInt(DEFAULT_ICON_TERTIARY_COLOR);
        }
        return mIconTertiaryColor;
    }

    public int getStatusBarColor() {
        return getStatusBarColor(getPrimaryColor());
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
        if(mConfig.projectConfiguration.primaryColor != null) {
            mPrimaryColor = convertHexToInt(mConfig.projectConfiguration.primaryColor);
        }
        if(mConfig.projectConfiguration.secondaryColor != null) {
            mSecondaryColor = convertHexToInt(mConfig.projectConfiguration.secondaryColor);
        }
        if(mConfig.projectConfiguration.tertiaryColor != null) {
            mTertiaryColor = convertHexToInt(mConfig.projectConfiguration.tertiaryColor);
        }
        if(mConfig.projectConfiguration.errorColor != null) {
            mErrorColor = convertHexToInt(mConfig.projectConfiguration.errorColor);
        }
        if(mConfig.projectConfiguration.successColor != null) {
            mSuccessColor = convertHexToInt(mConfig.projectConfiguration.successColor);
        }
        if(mConfig.projectConfiguration.textPrimaryColor != null) {
            mTextPrimaryColor = convertHexToInt(mConfig.projectConfiguration.textPrimaryColor);
        }
        if(mConfig.projectConfiguration.textSecondaryColor != null) {
            mTextSecondaryColor = convertHexToInt(mConfig.projectConfiguration.textSecondaryColor);
        }
        if(mConfig.projectConfiguration.textTertiaryColor != null) {
            mTextTertiaryColor = convertHexToInt(mConfig.projectConfiguration.textTertiaryColor);
        }
        if(mConfig.projectConfiguration.textTopbarColor != null) {
            mTextTopbarColor = convertHexToInt(mConfig.projectConfiguration.textTopbarColor);
        }
        if(mConfig.projectConfiguration.iconPrimaryColor != null) {
            mIconPrimaryColor = convertHexToInt(mConfig.projectConfiguration.iconPrimaryColor);
        }
        if(mConfig.projectConfiguration.iconSecondaryColor != null) {
            mIconSecondaryColor = convertHexToInt(mConfig.projectConfiguration.iconSecondaryColor);
        }
        if(mConfig.projectConfiguration.iconTertiaryColor != null) {
            mIconTertiaryColor = convertHexToInt(mConfig.projectConfiguration.iconTertiaryColor);
        }
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
        int grayBackground = Color.parseColor("#40221F1F");
        return new ColorStateList(
                new int[][]{
                        new int[]{-android.R.attr.state_checked},
                        new int[]{android.R.attr.state_checked}
                },
                new int[] {
                        grayBackground, mPrimaryColor
                }
        );
    }

    public ColorStateList getSwitchForegroundColors() {
        int grayBackground = Color.parseColor("#FFF1F1F1");
        return new ColorStateList(
                new int[][]{
                        new int[]{-android.R.attr.state_checked},
                        new int[]{android.R.attr.state_checked}
                },
                new int[] {
                        grayBackground, mPrimaryColor
                }
        );
    }

    public ColorStateList getSwitchBackgroundColors() {
        int grayBackground = Color.parseColor("#40221F1F");
        int statusBarColor = getStatusBarColor(mPrimaryColor);
        return new ColorStateList(
                new int[][]{
                        new int[]{-android.R.attr.state_checked},
                        new int[]{android.R.attr.state_checked}
                },
                new int[] {
                        grayBackground, statusBarColor
                }
        );
    }

    public Integer getIcon(String merchantCategory) {
        return mIconMap.get(merchantCategory);
    }

    private static Map<String, Integer> createIconMap()
    {
        Map<String, Integer> iconMap = new HashMap<>();
        iconMap.put("plane", R.drawable.ic_flights);
        iconMap.put("car", R.drawable.ic_car);
        iconMap.put("glass", R.drawable.ic_alcohol);
        iconMap.put("finance", R.drawable.ic_withdraw);
        iconMap.put("food", R.drawable.ic_food);
        iconMap.put("gas", R.drawable.ic_fuel);
        iconMap.put("bed", R.drawable.ic_hotel);
        iconMap.put("medical", R.drawable.ic_medicine);
        iconMap.put("camera", R.drawable.ic_other);
        iconMap.put("card", R.drawable.ic_bank_card);
        iconMap.put("cart", R.drawable.ic_purchases);
        iconMap.put("road", R.drawable.ic_toll_road);
        return iconMap;
    }

    public void setSdkOptions(ShiftSdkOptions sdkOptions) {
        mSdkOptions = sdkOptions;
    }

    public boolean showAddFundingSourceButton() {
        return mSdkOptions.features.get(ShiftSdkOptions.OptionKeys.showAddFundingSourceButton);
    }

    public boolean showActivateCardButton() {
        return mSdkOptions.features.get(ShiftSdkOptions.OptionKeys.showActivateCardButton);
    }
}
