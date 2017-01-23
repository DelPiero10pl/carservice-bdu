package com.cars.ui.dialog;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import com.cars.ui.R;

public class DialogPartEdit {
    private final Activity context;
    private View dialogView;
    public DialogPartEdit(Activity context) {
        this.context = context;
        LayoutInflater inflater = context.getLayoutInflater();
        dialogView = inflater.inflate(R.layout.dialog_part_form, null);
    }

    public View getDialogView() {
        return dialogView;
    }

    public AlertDialog.Builder build(DialogInterface.OnClickListener clickListener) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setView(dialogView);
        alertDialog.setPositiveButton("Zapisz", clickListener)
                .setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
        return alertDialog;
    }
}
