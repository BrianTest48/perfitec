package com.brianc.myapplication.clientes;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.brianc.myapplication.R;

import java.util.HashMap;
import java.util.Map;

public class AgregarClienteActivity extends AppCompatActivity {


    EditText txt_cliente_descripcion, txt_cliente_ruc, txt_cliente_direccion, txt_cliente_celular;
    Button btn_registrar;

    String url_insertar = "https://webservice.qhapai.com/crudclientes/insertar.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_cliente);

        txt_cliente_descripcion = findViewById(R.id.txt_cliente_descripcion);
        txt_cliente_ruc = findViewById(R.id.txt_cliente_ruc);
        txt_cliente_direccion = findViewById(R.id.txt_cliente_direccion);
        txt_cliente_celular = findViewById(R.id.txt_cliente_celular);
        btn_registrar = findViewById(R.id.btn_registro_cliente);

        btn_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                crearRegistro();
            }
        });
    }

    private void crearRegistro() {

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Registrando");
        progressDialog.setMessage("Espere porfavor...");
        progressDialog.setCancelable(false);

        String descripcion = txt_cliente_descripcion.getText().toString().trim();
        String direccion = txt_cliente_direccion.getText().toString().trim();
        String ruc = txt_cliente_ruc.getText().toString().trim();
        String celular = txt_cliente_celular.getText().toString().trim();

        if(descripcion.isEmpty()){
            Toast.makeText(this, "No ha ingresado la descripcion", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(ruc.isEmpty()){
            Toast.makeText(this, "No ha ingresado su direccion", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(direccion.isEmpty()){
            Toast.makeText(this, "No ha ingresado su ruc", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(celular.isEmpty()){
            Toast.makeText(this, "No ha ingresado su celular", Toast.LENGTH_SHORT).show();
            return;
        }else{
            progressDialog.show();
            StringRequest peticion = new StringRequest(Request.Method.POST, url_insertar, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if(response.equalsIgnoreCase("datos insertados")){
                        Toast.makeText(AgregarClienteActivity.this, "Se ha registrado correctamente", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();

                        startActivity(new Intent(getApplicationContext(), ClienteListaActivity.class));
                        finish();
                    }else{
                        progressDialog.dismiss();
                        Toast.makeText(AgregarClienteActivity.this, "Error en la inserción: " + response, Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(AgregarClienteActivity.this, "ERROR DESCONOCIDO: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }){
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("descripcion", descripcion);
                    params.put("ruc", ruc);
                    params.put("direccion", direccion);
                    params.put("celular", celular);

                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(AgregarClienteActivity.this);
            requestQueue.add(peticion);

        }

    }
}