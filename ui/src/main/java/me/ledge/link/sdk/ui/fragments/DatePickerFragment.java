package me.ledge.link.sdk.ui.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.NonNull;

import java.util.Calendar;
import java.util.Date;

/**
 * Displays a {@link DatePickerDialog}.
 * @author Wijnand
 */
public class DatePickerFragment extends DialogFragment {

    public static final String TAG = "DatePicker";
    private static final String THEME_ID_KEY = "me.ledge.link.sdk.ui.fragments.themeId";

    private Date mDate;
    private DatePickerDialog.OnDateSetListener mListener;
    private int mThemeId;

    /**
     * Creates a new {@link DatePickerFragment} instance.
     */
    public DatePickerFragment() {
        init();
    }

    /**
     * Initializes this class.
     */
    private void init() {
        mDate = null;
        mListener = null;
        mThemeId = 0;
    }

    /** {@inheritDoc} */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mThemeId = savedInstanceState.getInt(THEME_ID_KEY, 0);
        }

        final Calendar calendar = Calendar.getInstance();
        if (mDate != null) {
            calendar.setTime(mDate);
        }

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog;

        if (mThemeId == 0) {
            dialog = new DatePickerDialog(getActivity(), mListener, year, month, day);
        } else {
            dialog = new DatePickerDialog(getActivity(), mThemeId, mListener, year, month, day);
        }

        return dialog;
    }

    /** {@inheritDoc} */
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(THEME_ID_KEY, mThemeId);
        super.onSaveInstanceState(outState);
    }

    /**
     * Stores a new theme resource ID to use for the {@link DatePickerDialog}.
     * @param themeId Theme resource ID.
     */
    public void setThemeId(int themeId) {
        mThemeId = themeId;
    }

    /**
     * Stores a new {@link Date} to use as starting point.
     * @param date Date.
     */
    public void setDate(Date date) {
        mDate = date;
    }

    /**
     * Stores a new callback listener.
     * @param listener The new listener.
     */
    public void setListener(DatePickerDialog.OnDateSetListener listener) {
        mListener = listener;
    }
}
