package ua.opu.announcement;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class AnnouncementDialog extends DialogFragment {

    private static final String ARG_PARAM1 = "param1";
    private String mParam1;
    private static final String ARG_PARAM2 = "param2";
    private String mParam2;

    public static AnnouncementDialog newInstance(String param1, String param2) {
        AnnouncementDialog fragment = new AnnouncementDialog();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParam1 = getArguments() == null ? "null" : getArguments().getString(ARG_PARAM1);
        mParam2 = getArguments() == null ? "null" : getArguments().getString(ARG_PARAM2);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(mParam1);
        builder.setMessage(mParam2);
        builder.setPositiveButton("OK", ((dialog, which) -> {
            dismiss();
        }));

        return builder.create();
    }
}
