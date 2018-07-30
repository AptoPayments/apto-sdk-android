package com.shiftpayments.link.sdk.ui.storages;

import android.content.res.ColorStateList;
import android.graphics.Color;

import com.shiftpayments.link.sdk.api.exceptions.ApiException;
import com.shiftpayments.link.sdk.api.vos.requests.config.GetProjectConfigRequestVo;
import com.shiftpayments.link.sdk.api.vos.responses.config.ConfigResponseVo;
import com.shiftpayments.link.sdk.api.vos.responses.config.ContextConfigResponseVo;
import com.shiftpayments.link.sdk.ui.R;

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
    private ContextConfigResponseVo mConfig;
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
                return R.color.colorPrimary;
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
            if (getResultFromFuture(future) == null) {
                return R.color.colorPrimaryDark;
            } else {
                return (Integer) getResultFromFuture(future);
            }
        }
    }

    public synchronized Integer getTertiaryColor() {
        if(mTertiaryColor != null) {
            return mTertiaryColor;
        }
        else {
            CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
                try {
                    setConfig(getApiWrapper().getUserConfig(new GetProjectConfigRequestVo()));
                    return mTertiaryColor;
                } catch (ApiException e) {
                    throw new CompletionException(e);
                }
            });
            if (getResultFromFuture(future) == null) {
                return R.color.colorAccent;
            } else {
                return (Integer) getResultFromFuture(future);
            }
        }
    }

    public synchronized Integer getSuccessColor() {
        if(mSuccessColor != null) {
            return mSuccessColor;
        }
        else {
            CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
                try {
                    setConfig(getApiWrapper().getUserConfig(new GetProjectConfigRequestVo()));
                    return mSuccessColor;
                } catch (ApiException e) {
                    throw new CompletionException(e);
                }
            });
            if (getResultFromFuture(future) == null) {
                return R.color.llsdk_success_color;
            } else {
                return (Integer) getResultFromFuture(future);
            }
        }
    }

    public synchronized Integer getErrorColor() {
        if(mErrorColor != null) {
            return mErrorColor;
        }
        else {
            CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
                try {
                    setConfig(getApiWrapper().getUserConfig(new GetProjectConfigRequestVo()));
                    return mErrorColor;
                } catch (ApiException e) {
                    throw new CompletionException(e);
                }
            });
            if (getResultFromFuture(future) == null) {
                return R.color.llsdk_error_color;
            } else {
                return (Integer) getResultFromFuture(future);
            }
        }
    }

    public synchronized Integer getTextPrimaryColor() {
        if(mTextPrimaryColor != null) {
            return mTextPrimaryColor;
        }
        else {
            CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
                try {
                    setConfig(getApiWrapper().getUserConfig(new GetProjectConfigRequestVo()));
                    return mTextPrimaryColor;
                } catch (ApiException e) {
                    throw new CompletionException(e);
                }
            });
            if (getResultFromFuture(future) == null) {
                return R.color.llsdk_primary_text_color;
            } else {
                return (Integer) getResultFromFuture(future);
            }
        }
    }

    public synchronized Integer getTextSecondaryColor() {
        if(mTextSecondaryColor != null) {
            return mTextSecondaryColor;
        }
        else {
            CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
                try {
                    setConfig(getApiWrapper().getUserConfig(new GetProjectConfigRequestVo()));
                    return mTextSecondaryColor;
                } catch (ApiException e) {
                    throw new CompletionException(e);
                }
            });
            if (getResultFromFuture(future) == null) {
                return R.color.llsdk_secondary_text_color;
            } else {
                return (Integer) getResultFromFuture(future);
            }
        }
    }

    public synchronized Integer getTextTertiaryColor() {
        if(mTextTertiaryColor != null) {
            return mTextTertiaryColor;
        }
        else {
            CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
                try {
                    setConfig(getApiWrapper().getUserConfig(new GetProjectConfigRequestVo()));
                    return mTextTertiaryColor;
                } catch (ApiException e) {
                    throw new CompletionException(e);
                }
            });
            if (getResultFromFuture(future) == null) {
                return R.color.llsdk_tertiary_text_color;
            } else {
                return (Integer) getResultFromFuture(future);
            }
        }
    }

    public synchronized Integer getTextTopbarColor() {
        if(mTextTopbarColor != null) {
            return mTextTopbarColor;
        }
        else {
            CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
                try {
                    setConfig(getApiWrapper().getUserConfig(new GetProjectConfigRequestVo()));
                    return mTextTopbarColor;
                } catch (ApiException e) {
                    throw new CompletionException(e);
                }
            });
            if (getResultFromFuture(future) == null) {
                return R.color.llsdk_topbar_text_color;
            } else {
                return (Integer) getResultFromFuture(future);
            }
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
}
