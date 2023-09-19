package com.brianc.myapplication.clientes;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.brianc.myapplication.R;
import com.brianc.myapplication.usuarios.EditarUsuarioActivity;
import com.brianc.myapplication.usuarios.UsuarioListaActivity;

import java.util.HashMap;
import java.util.Map;

public class EditarClienteActivity extends AppCompatActivity {

    TextView tvt_cliente_editar_id;
    EditText txt_editar_descripcion, txt_editar_ruc, txt_editar_direccion, txt_editar_celular;
    Button btn_actualizar_cliente;
    Spinner spn_actualizar_cliente;
    String url_actualizar_cliente = "https://webservice.qhapai.com/crudclientes/actualizar.php";
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_cliente);

        Intent intent = getIntent();
        position = intent.getExtras().getInt("position");

        tvt_cliente_editar_id = findViewById(R.id.tvt_cliente_editar_id);
        txt_editar_descripcion = findViewById(R.id.txt_editar_descripcion);
        txt_editar_ruc = findViewById(R.id.txt_editar_ruc);
        txt_editar_direccion = findViewById(R.id.txt_editar_direccion);
        txt_editar_celular = findViewById(R.id.txt_editar_cliente_celular);
        spn_actualizar_cliente = findViewById(R.id.spn_actualizar_cliente);
        btn_actualizar_cliente = findViewById(R.id.btn_actualizar_cliente);

        tvt_cliente_editar_id.setText(ClienteListaActivity.clienteArrayList.get(position).getId());
        txt_editar_descripcion.setText(ClienteListaActivity.clienteArrayList.get(position).getDescripcion());
        txt_editar_ruc.setText(ClienteListaActivity.clienteArrayList.get(position).getRuc());
        txt_editar_direccion.setText(ClienteListaActivity.clienteArrayList.get(position).getDireccion());
        txt_editar_celular.setText(ClienteListaActivity.clienteArrayList.get(position).getCelular());



        btn_actualizar_cliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Actualizar();
            }
        });
    }

    private void Actualizar() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Actualizando");
        progressDialog.setMessage("Espere por favor...");
        progressDialog.setCancelable(false);

        String id = tvt_cliente_editar_id.getText().toString().trim();
        String descripcion = txt_editar_descripcion.getText().toString().trim();
        String ruc = txt_editar_ruc.getText().toString().trim();
        String celular = txt_editar_celular.getText().toString().trim();
        String direccion = txt_editar_direccion.getText().toString().trim();
        String estado = spn_actualizar_cliente.getSelectedItem().toString().trim();

        progressDialog.show();
        StringRequest peticion = new StringRequest(Request.Method.POST, url_actualizar_cliente, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equalsIgnoreCase("datos actualizados")){
                    Toast.makeText(EditarClienteActivity.this, "Se ha actualizado correctamente", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();

                    startActivity(new Intent(getApplicationContext(), ClienteListaActivity.class));
                    finish();
                }else {
                    progressDialog.dismiss();
                    Toast.makeText(EditarClienteActivity.this, "Error en la actualizacion: "+ response, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(EditarClienteActivity.this, "ERROR DESCONOCIDO: "+ error, Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", id);
                params.put("descripcion", descripcion);
                params.put("ruc", ruc);
                params.put("direccion", direccion);
                params.put("celular", celular);
                params.put("estado", estado);
                return params;
            }
        };

        //Se envia la peticion
        RequestQueue requestQueue = Volley.newRequestQueue(EditarClienteActivity.this);
        requestQueue.add(peticion);
    }
}