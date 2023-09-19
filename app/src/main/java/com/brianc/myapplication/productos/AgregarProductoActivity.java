package com.brianc.myapplication.productos;

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

public class AgregarProductoActivity extends AppCompatActivity {

    EditText txt_producto_nombre, txt_producto_tipo;
    Button btn_registrar;

    String url_insertar = "https://webservice.qhapai.com/crudproductos/insertar.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_producto);

        txt_producto_nombre = findViewById(R.id.txt_producto_nombre);
        txt_producto_tipo = findViewById(R.id.txt_producto_tipo);
        btn_registrar = findViewById(R.id.btn_registro_producto);

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

        String nombre = txt_producto_nombre.getText().toString().trim();
        String tipo = txt_producto_tipo.getText().toString().trim();

        if(nombre.isEmpty()){
            Toast.makeText(this, "No ha ingresado el nombre", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(tipo.isEmpty()){
            Toast.makeText(this, "No ha ingresado su tipo", Toast.LENGTH_SHORT).show();
            return;
        }else{
            progressDialog.show();
            StringRequest peticion = new StringRequest(Request.Method.POST, url_insertar, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if(response.equalsIgnoreCase("datos insertados")){
                        Toast.makeText(AgregarProductoActivity.this, "Se ha registrado correctamente", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();

                        startActivity(new Intent(getApplicationContext(), ProductoListaActivity.class));
                        finish();
                    }else{
                        progressDialog.dismiss();
                        Toast.makeText(AgregarProductoActivity.this, "Error en la inserción: " + response, Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(AgregarProductoActivity.this, "ERROR DESCONOCIDO: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }){
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("nombre", nombre);
                    params.put("tipo", tipo);

                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(AgregarProductoActivity.this);
            requestQueue.add(peticion);

        }

    }
}