package com.brianc.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.brianc.myapplication.R;
import com.brianc.myapplication.models.Producto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class AdapterProductoMaquina extends ArrayAdapter<Producto> {

    Context context;
    List<Producto> arrayproducto;

    private OnCheckboxCheckedListener checkedListener; // Nombre de la interfaz

    public AdapterProductoMaquina(@NonNull Context context, List<Producto> arrayproducto) {
        super(context, R.layout.list_item_maquina_ckd, arrayproducto);
        this.context = context;
        this.arrayproducto = arrayproducto;
    }

    @Override
    public int getCount() {
        return arrayproducto.size();
    }

    @Override
    public Producto getItem(int position) {
        return arrayproducto.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_maquina_ckd, null, true);
        }

        TextView tvt_id = convertView.findViewById(R.id.tvt_chk_id_producto);
        CheckBox chk_producto_id = convertView.findViewById(R.id.chk_producto_id);
        TextView tvt_nombre = convertView.findViewById(R.id.tvt_descripcion_producto_maquina);

        final Producto producto = arrayproducto.get(position);
        tvt_id.setText(producto.getId());
        tvt_nombre.setText(producto.getNombre());

        chk_producto_id.setChecked(producto.isChecked());

        chk_producto_id.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                producto.setChecked(isChecked);

                if (checkedListener != null) {
                    checkedListener.onCheckboxChecked(position); // Cambié el nombre del método
                }
            }
        });

        return convertView;
    }

    // Definición de la interfaz OnCheckboxCheckedListener
    public interface OnCheckboxCheckedListener {
        void onCheckboxChecked(int position);
    }

    // Método para establecer el oyente
    public void setOnCheckboxCheckedListener(OnCheckboxCheckedListener listener) {
        this.checkedListener = listener;
    }
}


