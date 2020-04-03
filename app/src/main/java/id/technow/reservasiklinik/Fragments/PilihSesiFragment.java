package id.technow.reservasiklinik.Fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import id.technow.reservasiklinik.R;

public class PilihSesiFragment extends BottomSheetDialogFragment implements View.OnClickListener {

    public PilihSesiFragment() {
        // Required empty public constructor
    }

    private BottomSheetBehavior mBottomSheetBehavior;
    Context mCtx;

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        //handleUserExit();
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);

        View fragmentView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_pilih_sesi, null);
        dialog.setContentView(fragmentView);

        mCtx = getActivity();

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) fragmentView.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();

        if (behavior != null && behavior instanceof BottomSheetBehavior) {
            mBottomSheetBehavior = (BottomSheetBehavior) behavior;
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
                @Override
                public void onStateChanged(@NonNull View bottomSheet, int newState) {
                    if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                        dismiss();
                    }
                }

                @Override
                public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                }
            });
        }
    }

    @Override
    public void onClick(View v) {

    }
}
