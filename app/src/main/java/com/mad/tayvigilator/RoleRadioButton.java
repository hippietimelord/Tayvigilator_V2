package com.mad.tayvigilator;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

public class RoleRadioButton extends DialogFragment {

    private int mSelectedItem;
    private String role;
    public interface RoleDialogListener {
        void onFinishDialogRole(String role);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.pick_role)
                .setSingleChoiceItems(R.array.role_array, 0,
                        new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mSelectedItem = which;
                            }
                        })
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        RoleDialogListener activity = (RoleDialogListener) getActivity();
                        role = getResources().getStringArray(R.array.role_array)[mSelectedItem];
                        activity.onFinishDialogRole(role);
                        dismiss();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(getActivity(), "You clicked Cancel. No Item was selected.", Toast.LENGTH_SHORT).show();
                    }
                });

        return builder.create();
    }
}
