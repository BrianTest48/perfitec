package com.brianc.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.brianc.myapplication.R;
import com.brianc.myapplication.models.Producto;

import java.util.List;

public class AdapterProducto extends ArrayAdapter<Producto> {
    Context context;
    List<Producto> arrayproducto;
    public AdapterProducto(@NonNull Context context, List<Producto> arrayproducto) {
        super(context, R.layout.list_item_producto, arrayproducto);
        this.context = context;
        this.arrayproducto = arrayproducto;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_producto, null, true);

        TextView tvt_id = view.findViewById(R.id.tvt_producto_id);
        TextView tvt_nombre = view.findViewById(R.id.tvt_producto_nombre);

        tvt_id.setText(arrayproducto.get(position).getId());
        tvt_nombre.setText(arrayproducto.get(position).getNombre());

        return view;

    }
}
