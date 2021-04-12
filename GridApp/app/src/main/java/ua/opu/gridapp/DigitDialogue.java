package ua.opu.gridapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class DigitDialogue extends DialogFragment {

    // Слушатель и интерфейс для передачи данных в Activity
    private MyDialogListener mListener;
    public static interface MyDialogListener {
        void onMyDialogResult(String choice);
    }

    // Статический метод для передачи данных в диалог
    public static DigitDialogue newInstance(String param1) {
        DigitDialogue fragment = new DigitDialogue();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    private static final String ARG_PARAM1 = "param1";
    private String mParam1;

    // Получаем данные из Bundle
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParam1 = getArguments() == null ? "null" : getArguments().getString(ARG_PARAM1);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Number");
        builder.setMessage("You choose number: " + mParam1);
        builder.setPositiveButton("OK", ((dialog, which) -> {
            dismiss();
        }));

        return builder.create();
    }

    // Получаем объект слушателя
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try { mListener = (MyDialogListener) context;
        } catch (ClassCastException ex) {
            throw new ClassCastException(context.toString() + " must implement MyDialog.MyDialogListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
