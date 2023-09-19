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


    private CheckboxCheckedListener checkedListener;

    public AdapterProductoMaquina(@NonNull Context context, List<Producto> arrayproducto) {
        super(context, R.layout.list_item_maquina_ckd, arrayproducto);
        this.context = context;
        this.arrayproducto = arrayproducto;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_maquina_ckd, null, true);

        TextView tvt_id = view.findViewById(R.id.tvt_chk_id_producto);
        CheckBox chk_producto_id = view.findViewById(R.id.chk_producto_id);
        TextView tvt_nombre = view.findViewById(R.id.tvt_descripcion_producto_maquina);

        tvt_id.setText(arrayproducto.get(position).getId());
        tvt_nombre.setText(arrayproducto.get(position).getNombre());
        chk_producto_id.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (checkedListener != null){
                    checkedListener.getCheckBoxCheckedListener(position);
                }
            }
        });

        return view;

    }




    public interface CheckboxCheckedListener{
        void getCheckBoxCheckedListener(int position);
    }

    public void setCheckedListener(CheckboxCheckedListener checkedListener){
        this.checkedListener = checkedListener;
    }
}


