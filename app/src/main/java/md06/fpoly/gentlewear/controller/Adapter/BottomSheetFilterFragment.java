package md06.fpoly.gentlewear.controller.Adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import md06.fpoly.gentlewear.R;

public class BottomSheetFilterFragment extends BottomSheetDialogFragment {
  
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.bottom_sheet_dialog, container, false);

        // Find and initialize views, set up any necessary adapters or listeners

        return rootView;
    }
}
