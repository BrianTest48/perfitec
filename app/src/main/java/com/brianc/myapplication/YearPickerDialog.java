package com.brianc.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.LinearLayout;
import android.widget.NumberPicker;

import java.util.Calendar;

public class YearPickerDialog {
    private final int MIN_YEAR = 1900; // Año mínimo
    private final int MAX_YEAR = 2100; // Año máximo

    private Activity activity;
    private AlertDialog dialog;
    private NumberPicker yearPicker;

    public YearPickerDialog(Activity activity) {
        this.activity = activity;
    }

    public void showYearPickerDialog(final YearPickerDialog.OnYearSelectedListener listener) {
        // Crear un layout para el NumberPicker
        LinearLayout linearLayout = new LinearLayout(activity);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        // Crear el NumberPicker
        yearPicker = new NumberPicker(activity);
        yearPicker.setMinValue(MIN_YEAR);
        yearPicker.setMaxValue(MAX_YEAR);
        yearPicker.setValue(Calendar.getInstance().get(Calendar.YEAR)); // Año actual como valor predeterminado

        // Agregar el NumberPicker al layout
        linearLayout.addView(yearPicker, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT)
        );

        // Crear el cuadro de diálogo
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setView(linearLayout);
        builder.setTitle("Selecciona un año");
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int selectedYear = yearPicker.getValue();
                if (listener != null) {
                    listener.onYearSelected(selectedYear);
                }
            }
        });
        builder.setNegativeButton("Cancelar", null);

        // Mostrar el cuadro de diálogo
        dialog = builder.create();
        dialog.show();
    }

    public interface OnYearSelectedListener {
        void onYearSelected(int year);
    }

    public void dismissYearPickerDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
