package com.shiftpayments.link.sdk.ui.vos;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class AmountVo {

    private static final Map<String, String> mCurrencySymbols = createCurrencySymbolsMap();
    private double mAmount;
    private String mCurrency;
    private String mCurrencySymbol;

    public AmountVo(double amount, String currency) {
        mAmount = amount;
        mCurrency = currency;
        mCurrencySymbol = getCurrencySymbol(mCurrency);
    }

    public AmountVo(int amount, String currency) {
        this((double)amount, currency);
    }

    @Override
    public String toString() {
        if(mCurrency==null) {
            return String.valueOf(mAmount);
        }

        if(mCurrencySymbols.containsKey(mCurrency)) {
            return String.format("%s %.6f", mCurrencySymbol, mAmount);
        }
        else {
            NumberFormat format = NumberFormat.getCurrencyInstance(Locale.getDefault());
            format.setCurrency(Currency.getInstance(mCurrency));
            return format.format(mAmount);
        }
    }

    public double getAmount() {
        return mAmount;
    }

    public String getCurrency() {
        return mCurrency;
    }

    private String getCurrencySymbol(String currency) {
        String currencySymbol = currency;
        if(currency!=null && !currency.isEmpty() && mCurrencySymbols.get(currency)!=null) {
            currencySymbol = mCurrencySymbols.get(currency);
        }
        return currencySymbol;
    }

    private static Map<String, String> createCurrencySymbolsMap()
    {
        Map<String, String> currencyMap = new HashMap<>();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            currencyMap.put("BTC", "\u20BF");
            currencyMap.put("ETH", "\u039E");
        }
        return currencyMap;
    }
}
