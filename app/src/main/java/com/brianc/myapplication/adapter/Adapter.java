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
import com.brianc.myapplication.models.Usuario;

import java.util.List;

public class Adapter extends ArrayAdapter<Usuario> {

    Context context;
    List<Usuario> arrayusuarios;
    public Adapter(@NonNull Context context, List<Usuario> arrayusuarios) {
        super(context, R.layout.list_item_usuario, arrayusuarios);
        this.context = context;
        this.arrayusuarios = arrayusuarios;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_usuario, null, true);

        TextView tvt_id = view.findViewById(R.id.tvt_id);
        TextView tvt_nombre = view.findViewById(R.id.tvt_nombre);

        tvt_id.setText(arrayusuarios.get(position).getId());
        tvt_nombre.setText(arrayusuarios.get(position).getNombre());

        return view;

    }
}
