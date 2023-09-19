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
import com.brianc.myapplication.models.Maquina;

import java.util.List;

public class AdapterMaquina extends ArrayAdapter<Maquina> {

    Context context;
    List<Maquina> arraymaquina;
    public AdapterMaquina(@NonNull Context context, List<Maquina> arraymaquinas) {
        super(context, R.layout.list_item_maquina, arraymaquinas);
        this.context = context;
        this.arraymaquina = arraymaquinas;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_maquina, null, true);

        TextView tvt_id = view.findViewById(R.id.tvt_maquina_id);
        TextView tvt_nombre = view.findViewById(R.id.tvt_maquina_nombre);

        tvt_id.setText(arraymaquina.get(position).getId());
        tvt_nombre.setText(arraymaquina.get(position).getDescripcion());

        return view;

    }
}
