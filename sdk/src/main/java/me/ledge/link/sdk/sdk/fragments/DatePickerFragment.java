package me.ledge.link.sdk.sdk.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

import java.util.Calendar;
import java.util.Date;

/**
 * Displays a {@link DatePickerDialog}.
 * @author Wijnand
 */
public class DatePickerFragment extends DialogFragment {

    public static final String TAG = "DatePicker";

    private Date mDate;
    private DatePickerDialog.OnDateSetListener mListener;

    public DatePickerFragment() { }

    /** {@inheritDoc} */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();
        if (mDate != null) {
            calendar.setTime(mDate);
        }

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), mListener, year, month, day);
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
