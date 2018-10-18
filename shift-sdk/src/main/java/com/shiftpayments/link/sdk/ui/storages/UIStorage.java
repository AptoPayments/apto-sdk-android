package com.shiftpayments.link.sdk.ui.storages;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;
import android.widget.EditText;
import android.widget.TextView;

import com.shiftpayments.link.sdk.api.exceptions.ApiException;
import com.shiftpayments.link.sdk.api.vos.requests.config.GetProjectConfigRequestVo;
import com.shiftpayments.link.sdk.api.vos.responses.config.ConfigResponseVo;
import com.shiftpayments.link.sdk.api.vos.responses.config.ContextConfigResponseVo;
import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.vos.ShiftSdkOptions;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import java8.util.concurrent.CompletableFuture;
import java8.util.concurrent.CompletionException;

import static com.shiftpayments.link.sdk.sdk.ShiftSdk.getApiWrapper;

/**
 * Stores UI related data.
 * @author Adrian
 */
public class UIStorage {

    private Integer mUiPrimaryColor;
    private Integer mUiSecondaryColor;
    private Integer mUiTertiaryColor;
    private Integer mUiErrorColor;
    private Integer mUiSuccessColor;
    private Integer mTextPrimaryColor;
    private Integer mTextSecondaryColor;
    private Integer mTextTertiaryColor;
    private Integer mTextTopbarColor;
    private Integer mIconPrimaryColor;
    private Integer mIconSecondaryColor;
    private Integer mIconTertiaryColor;
    private Integer mCardBackgroundColor;
    private ContextConfigResponseVo mConfig;
    private ShiftSdkOptions mSdkOptions;

    // Define default colors here instead of in colors.xml to avoid having to pass in context
    private final static String DEFAULT_UI_PRIMARY_COLOR = "F90D00";
    private final static String DEFAULT_UI_SECONDARY_COLOR = "FF9500";
    private final static String DEFAULT_UI_TERTIARY_COLOR = "FFCC00";
    private final static String DEFAULT_UI_SUCCESS_COLOR = "DB1D0E";
    private final static String DEFAULT_UI_ERROR_COLOR = "FFDC4337";
    private final static String DEFAULT_PRIMARY_TEXT_COLOR = "FF2B2D35";
    private final static String DEFAULT_SECONDARY_TEXT_COLOR = "FF54565F";
    private final static String DEFAULT_TERTIARY_TEXT_COLOR = "FFBBBDBD";
    private final static String DEFAULT_TOPBAR_TEXT_COLOR = "FFFFFF";
    private final static String DEFAULT_ICON_PRIMARY_COLOR = "000000";
    private final static String DEFAULT_ICON_SECONDARY_COLOR = "000000";
    private final static String DEFAULT_ICON_TERTIARY_COLOR = "FFFFFF";
    private final static String DEFAULT_CARD_BACKGROUND_COLOR = "F90D00";
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

    public synchronized Integer getUiPrimaryColor() {
        if(mUiPrimaryColor == null) {
            mUiPrimaryColor = convertHexToInt(DEFAULT_UI_PRIMARY_COLOR);
        }
        return mUiPrimaryColor;
    }

    public synchronized Integer getUiSecondaryColor() {
        if(mUiSecondaryColor == null) {
            mUiSecondaryColor = convertHexToInt(DEFAULT_UI_SECONDARY_COLOR);
        }
        return mUiSecondaryColor;
    }

    public synchronized Integer getUiTertiaryColor() {
        if(mUiTertiaryColor == null) {
            mUiTertiaryColor = convertHexToInt(DEFAULT_UI_TERTIARY_COLOR);
        }
        return mUiTertiaryColor;
    }

    public synchronized Integer getUiSuccessColor() {
        if(mUiSuccessColor == null) {
            mUiSuccessColor = convertHexToInt(DEFAULT_UI_SUCCESS_COLOR);
        }
        return mUiSuccessColor;
    }

    public synchronized Integer getUiErrorColor() {
        if(mUiErrorColor == null) {
            mUiErrorColor = convertHexToInt(DEFAULT_UI_ERROR_COLOR);
        }
        return mUiErrorColor;
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

    public synchronized Integer getCardBackgroundColor() {
        if(mCardBackgroundColor == null) {
            mCardBackgroundColor = convertHexToInt(DEFAULT_CARD_BACKGROUND_COLOR);
        }
        return mCardBackgroundColor;
    }

    public int getStatusBarColor() {
        return getStatusBarColor(getUiPrimaryColor());
    }

    public int getStatusBarColor(int actionBarColor) {
        return adjustColorValue(actionBarColor, 0.8f);
    }

    public int getDisabledTextTopbarColor() {
        return adjustColorValue(getTextTopbarColor(), 0.8f);
    }

    public int adjustColorValue(@ColorInt int color, float value) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[2] *= value;
        return Color.HSVToColor(hsv);
    }

    public static int adjustColorAlpha(@ColorInt int color, float factor) {
        int alpha = Math.round(Color.alpha(color) * factor);
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        return Color.argb(alpha, red, green, blue);
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
        if(mUiPrimaryColor ==null) {
            return 0;
        }
        int red = Color.red(mUiPrimaryColor);
        int green = Color.green(mUiPrimaryColor);
        int blue = Color.blue(mUiPrimaryColor);
        return getBrightness(red, green, blue) > 200 ? Color.BLACK : Color.WHITE;
    }

    private int getBrightness(int red, int green, int blue) {
        // As per http://stackoverflow.com/a/2241471
        return (int)Math.sqrt(red * red * .299 +
                            green * green * .587 +
                            blue * blue * .114);
    }

    private void setColors() {
        if(mConfig.projectConfiguration.projectBranding.uiPrimaryColor != null) {
            mUiPrimaryColor = convertHexToInt(mConfig.projectConfiguration.projectBranding.uiPrimaryColor);
        }
        if(mConfig.projectConfiguration.projectBranding.uiSecondaryColor != null) {
            mUiSecondaryColor = convertHexToInt(mConfig.projectConfiguration.projectBranding.uiSecondaryColor);
        }
        if(mConfig.projectConfiguration.projectBranding.uiTertiaryColor != null) {
            mUiTertiaryColor = convertHexToInt(mConfig.projectConfiguration.projectBranding.uiTertiaryColor);
        }
        if(mConfig.projectConfiguration.projectBranding.uiErrorColor != null) {
            mUiErrorColor = convertHexToInt(mConfig.projectConfiguration.projectBranding.uiErrorColor);
        }
        if(mConfig.projectConfiguration.projectBranding.uiSuccessColor != null) {
            mUiSuccessColor = convertHexToInt(mConfig.projectConfiguration.projectBranding.uiSuccessColor);
        }
        if(mConfig.projectConfiguration.projectBranding.textPrimaryColor != null) {
            mTextPrimaryColor = convertHexToInt(mConfig.projectConfiguration.projectBranding.textPrimaryColor);
        }
        if(mConfig.projectConfiguration.projectBranding.textSecondaryColor != null) {
            mTextSecondaryColor = convertHexToInt(mConfig.projectConfiguration.projectBranding.textSecondaryColor);
        }
        if(mConfig.projectConfiguration.projectBranding.textTertiaryColor != null) {
            mTextTertiaryColor = convertHexToInt(mConfig.projectConfiguration.projectBranding.textTertiaryColor);
        }
        if(mConfig.projectConfiguration.projectBranding.textTopbarColor != null) {
            mTextTopbarColor = convertHexToInt(mConfig.projectConfiguration.projectBranding.textTopbarColor);
        }
        if(mConfig.projectConfiguration.projectBranding.iconPrimaryColor != null) {
            mIconPrimaryColor = convertHexToInt(mConfig.projectConfiguration.projectBranding.iconPrimaryColor);
        }
        if(mConfig.projectConfiguration.projectBranding.iconSecondaryColor != null) {
            mIconSecondaryColor = convertHexToInt(mConfig.projectConfiguration.projectBranding.iconSecondaryColor);
        }
        if(mConfig.projectConfiguration.projectBranding.iconTertiaryColor != null) {
            mIconTertiaryColor = convertHexToInt(mConfig.projectConfiguration.projectBranding.iconTertiaryColor);
        }
        if(mConfig.projectConfiguration.projectBranding.cardBackgroundColor != null) {
            mCardBackgroundColor = convertHexToInt(mConfig.projectConfiguration.projectBranding.cardBackgroundColor);
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
                        grayBackground, mUiPrimaryColor
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
                        grayBackground, mUiPrimaryColor
                }
        );
    }

    public ColorStateList getSwitchBackgroundColors() {
        int grayBackground = Color.parseColor("#40221F1F");
        int statusBarColor = getStatusBarColor(mUiPrimaryColor);
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

    public boolean isEmbeddedMode() {
        return mSdkOptions.features.get(ShiftSdkOptions.OptionKeys.useEmbeddedMode);
    }

    public void setCursorColor(EditText view) {
        try {
            // Get the cursor resource id
            Field field = TextView.class.getDeclaredField("mCursorDrawableRes");
            field.setAccessible(true);
            int drawableResId = field.getInt(view);

            // Get the editor
            field = TextView.class.getDeclaredField("mEditor");
            field.setAccessible(true);
            Object editor = field.get(view);

            // Get the drawable and set a color filter
            Drawable drawable = ContextCompat.getDrawable(view.getContext(), drawableResId);
            drawable.setColorFilter(this.getUiPrimaryColor(), PorterDuff.Mode.SRC_IN);
            Drawable[] drawables = {drawable, drawable};

            // Set the drawables
            field = editor.getClass().getDeclaredField("mCursorDrawable");
            field.setAccessible(true);
            field.set(editor, drawables);
        } catch (Exception ignored) {
        }
    }
}
