package com.shiftpayments.link.sdk.ui.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.DatePicker;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

/**
 * Workaround for this bug: https://code.google.com/p/android/issues/detail?id=222208
 * In Android 7.0 Nougat, spinner mode for the TimePicker in TimePickerDialog is
 * incorrectly displayed as clock, even when the theme specifies otherwise, such as:
 *
 *  <resources>
 *      <style name="Theme.MyApp" parent="Theme.AppCompat.Light.NoActionBar">
 *          <item name="android:timePickerStyle">@style/Widget.MyApp.TimePicker</item>
 *      </style>
 *
 *      <style name="Widget.MyApp.TimePicker" parent="android:Widget.Material.TimePicker">
 *          <item name="android:timePickerMode">spinner</item>
 *      </style>
 *  </resources>
 *
 * May also pass TimePickerDialog.THEME_HOLO_LIGHT as an argument to the constructor,
 * as this theme has the TimePickerMode set to spinner.
 */
public class DatePickerDialogFixedNougatSpinner extends DatePickerDialog {

    public DatePickerDialogFixedNougatSpinner(Context context, DatePickerDialog.OnDateSetListener listener, int year, int month, int day) {
        super(context, listener, year, month, day);
        fixSpinner(context, year, month, day);
    }

    public DatePickerDialogFixedNougatSpinner(Context context, int themeResId, DatePickerDialog.OnDateSetListener listener, int year, int month, int day) {
        super(context, themeResId, listener, year, month, day);
        fixSpinner(context, year, month, day);
    }

    private void fixSpinner(Context context, int year, int month, int dayOfMonth) {
        // The spinner vs not distinction probably started in lollipop but applying this
        // for versions < nougat leads to a crash trying to get DatePickerSpinnerDelegate
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.N) {
            try {
                // Get the theme's android:datePickerMode
                final int MODE_SPINNER = 2;
                Class<?> styleableClass = Class.forName("com.android.internal.R$styleable");
                Field datePickerStyleableField = styleableClass.getField("DatePicker");
                int[] datePickerStyleable = (int[]) datePickerStyleableField.get(null);
                final TypedArray a = context.obtainStyledAttributes(null, datePickerStyleable,
                        android.R.attr.datePickerStyle, 0);
                Field datePickerModeStyleableField = styleableClass.getField("DatePicker_datePickerMode");
                int datePickerModeStyleable = datePickerModeStyleableField.getInt(null);
                final int mode = a.getInt(datePickerModeStyleable, MODE_SPINNER);
                a.recycle();

                if (mode == MODE_SPINNER) {
                    DatePicker datePicker = (DatePicker) findField(DatePickerDialog.class,
                            DatePicker.class, "mDatePicker").get(this);
                    Class<?> delegateClass = Class.forName("android.widget.DatePicker$DatePickerDelegate");
                    Field delegateField = findField(DatePicker.class, delegateClass, "mDelegate");
                    Object delegate = delegateField.get(datePicker);

                    Class<?> spinnerDelegateClass = Class.forName("android.widget.DatePickerSpinnerDelegate");

                    // In 7.0 Nougat for some reason the datePickerMode is ignored and the
                    // delegate is DatePickerCalendarDelegate
                    if (delegate.getClass() != spinnerDelegateClass) {
                        delegateField.set(datePicker, null); // throw out the DatePickerCalendarDelegate!
                        datePicker.removeAllViews(); // remove the DatePickerCalendarDelegate views
                        Constructor spinnerDelegateConstructor = spinnerDelegateClass
                                .getDeclaredConstructor(DatePicker.class, Context.class,
                                        AttributeSet.class, int.class, int.class);
                        spinnerDelegateConstructor.setAccessible(true);

                        // Instantiate a DatePickerSpinnerDelegate
                        delegate = spinnerDelegateConstructor.newInstance(datePicker, context,
                                null, android.R.attr.datePickerStyle, 0);

                        // set the DatePicker.mDelegate to the spinner delegate
                        delegateField.set(datePicker, delegate);

                        datePicker.setCalendarViewShown(false);
                        // Set up the DatePicker again, with the DatePickerSpinnerDelegate
                        datePicker.updateDate(year, month, dayOfMonth);
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private Field findField(Class objectClass, Class fieldClass, String expectedName) {
        try {
            Field field = objectClass.getDeclaredField(expectedName);
            field.setAccessible(true);
            return field;
        } catch (NoSuchFieldException e) {
            // ignore
        }

        // search for it if it wasn't found under the expected ivar name
        for (Field searchField : objectClass.getDeclaredFields()) {
            if (searchField.getType() == fieldClass) {
                searchField.setAccessible(true);
                return searchField;
            }
        }
        return null;
    }
}