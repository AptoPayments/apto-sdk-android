package me.ledge.link.sdk.ui.presenters.link;

import android.app.Activity;

import me.ledge.link.sdk.ui.Command;
import me.ledge.link.sdk.ui.LedgeBaseModule;
import me.ledge.link.sdk.ui.activities.link.LoanAmountActivity;

/**
 * Created by adrian on 29/12/2016.
 */

public class LoanInfoModule extends LedgeBaseModule implements LoanDataDelegate {

    private static LoanInfoModule mInstance;
    public Command onFinish;
    public Command onBack;


    public static synchronized  LoanInfoModule getInstance(Activity activity) {
        if (mInstance == null) {
            mInstance = new LoanInfoModule(activity);
        }
        return mInstance;
    }

    private LoanInfoModule(Activity activity) {
        super(activity);
    }

    @Override
    public void initialModuleSetup() {
        startActivity(LoanAmountActivity.class);
    }

    @Override
    public void loanDataPresented() {
        onFinish.execute();
    }

    @Override
    public void loanDataOnBackPressed() {
        onBack.execute();
    }
}
