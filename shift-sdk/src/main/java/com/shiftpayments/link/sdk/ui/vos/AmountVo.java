package com.shiftpayments.link.sdk.ui.vos;

import java.util.HashMap;
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
        int intAmount = (int) mAmount;
        String amount = mAmount == intAmount ? String.valueOf(intAmount)
                : String.format("%.2f", mAmount);
        return mCurrencySymbol + amount;
    }

    private String getCurrencySymbol(String currency) {
        String currencySymbol = "";
        if(currency!=null && !currency.isEmpty()) {
            currencySymbol = mCurrencySymbols.get(currency);
        }
        return currencySymbol;
    }

    private static Map<String, String> createCurrencySymbolsMap()
    {
        Map<String, String> currencyMap = new HashMap<>();
        currencyMap.put("USD", "$");
        currencyMap.put("EUR", "€");
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            currencyMap.put("BTC", "\u20BF");
        }
        else {
            currencyMap.put("BTC", "BTC ");
        }
        return currencyMap;
    }
}