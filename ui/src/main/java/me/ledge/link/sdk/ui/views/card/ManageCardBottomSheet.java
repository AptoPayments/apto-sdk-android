package me.ledge.link.sdk.ui.views.card;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.SwitchCompat;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import me.ledge.link.sdk.ui.R;


/**
 * Created by adrian on 30/11/2017.
 */

public class ManageCardBottomSheet extends BottomSheetDialogFragment
        implements CompoundButton.OnCheckedChangeListener, CompoundButton.OnTouchListener, View.OnClickListener {

    private ViewListener mListener;
    private SwitchCompat mEnableCardSwitch;
    private SwitchCompat mShowCardInfoSwitch;
    private static boolean mIsEnableCardSwitchTouched;
    private static boolean mIsShowCardInfoSwitchTouched;
    public boolean isCardEnabled;
    public boolean showCardInfo;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback = new BottomSheetBehavior.BottomSheetCallback() {

        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                dismiss();
            }
        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
        }
    };

    @NonNull
    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        final BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);

        final View view = View.inflate(this.getContext(), R.layout.fragment_manage_card_bottom_sheet, null);
        mEnableCardSwitch = (SwitchCompat) view.findViewById(R.id.sw_card_enabled);
        mEnableCardSwitch.setOnCheckedChangeListener(this);
        mEnableCardSwitch.setOnTouchListener(this);
        setEnableCardSwitch(isCardEnabled);
        mShowCardInfoSwitch = (SwitchCompat) view.findViewById(R.id.sw_show_card_info);
        mShowCardInfoSwitch.setOnCheckedChangeListener(this);
        mShowCardInfoSwitch.setOnTouchListener(this);
        setShowCardInfoSwitch(showCardInfo);
        LinearLayout mChangePin = (LinearLayout) view.findViewById(R.id.ll_change_pin);
        mChangePin.setOnClickListener(this);
        dialog.setContentView(view);

        BottomSheetBehavior mBottomSheetBehavior = BottomSheetBehavior.from((View) view.getParent());
        mBottomSheetBehavior.setBottomSheetCallback(mBottomSheetBehaviorCallback);

        return dialog;
    }

    public void setEnableCardSwitch(boolean enable) {
        mEnableCardSwitch.setChecked(enable);
    }

    public void setShowCardInfoSwitch(boolean enable) {
        mShowCardInfoSwitch.setChecked(enable);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        if (mListener == null) {
            return;
        }
        int id = compoundButton.getId();

        if (id == R.id.sw_card_enabled) {
            if (mIsEnableCardSwitchTouched) {
                mIsEnableCardSwitchTouched = false;
                mListener.enableCardClickHandler(isChecked);
            }
        }
        else if(id == R.id.sw_show_card_info) {
            if (mIsShowCardInfoSwitchTouched) {
                mIsShowCardInfoSwitchTouched = false;
                mListener.showCardInfoClickHandler(isChecked);
            }
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if(view.getId() == R.id.sw_card_enabled) {
            mIsEnableCardSwitchTouched = true;
        }
        else if(view.getId() == R.id.sw_show_card_info) {
            mIsShowCardInfoSwitchTouched = true;
        }
        return false;
    }

    @Override
    public void onClick(View view) {
        if (mListener == null) {
            return;
        }

        if (view.getId() == R.id.ll_change_pin) {
            mListener.changePinClickHandler();
        }
    }

    /**
     * Callbacks this {@link View} will invoke.
     */
    public interface ViewListener {
        void enableCardClickHandler(boolean enable);
        void showCardInfoClickHandler(boolean show);
        void changePinClickHandler();
    }

    public void setViewListener(ViewListener viewListener) {
        mListener = viewListener;
    }
}