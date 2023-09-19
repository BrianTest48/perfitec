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
import com.brianc.myapplication.models.Cliente;

import java.util.List;

public class AdapterCliente extends ArrayAdapter<Cliente> {

    Context context;
    List<Cliente> arraycliente;
    public AdapterCliente(@NonNull Context context, List<Cliente> arrayusuarios) {
        super(context, R.layout.list_item_cliente, arrayusuarios);
        this.context = context;
        this.arraycliente = arrayusuarios;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_cliente, null, true);

        TextView tvt_id = view.findViewById(R.id.tvt_cliente_id);
        TextView tvt_nombre = view.findViewById(R.id.tvt_cliente_nombre);

        tvt_id.setText(arraycliente.get(position).getId());
        tvt_nombre.setText(arraycliente.get(position).getDescripcion());

        return view;

    }
}
