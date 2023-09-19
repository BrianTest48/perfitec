package com.brianc.myapplication.usuarios;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

public class AgregarUsuarioActivity extends AppCompatActivity {

    EditText txt_nombre;
    EditText txt_apellido;
    EditText txt_celular;
    EditText txt_password;
    EditText txt_dni;
    Spinner spn_rol_usuario;
    Button btn_registro;
    String url_insertar_usuario = "https://webservice.qhapai.com/crudusuario/insertar.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_usuario);

        txt_nombre = findViewById(R.id.txt_nombre);
        txt_apellido = findViewById(R.id.txt_apellido);
        txt_celular = findViewById(R.id.txt_celular);
        txt_password = findViewById(R.id.txt_password);
        txt_dni = findViewById(R.id.txt_dni);
        spn_rol_usuario = findViewById(R.id.spn_rol_usuario);
        btn_registro = findViewById(R.id.btn_registro);

        btn_registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearRegistro();
            }
        });
    }

    private void crearRegistro() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Registrando");
        progressDialog.setMessage("Espere porfavor...");
        progressDialog.setCancelable(false);

        String nombre = txt_nombre.getText().toString().trim();
        String apellido = txt_apellido.getText().toString().trim();
        String celular = txt_celular.getText().toString().trim();
        String password = txt_password.getText().toString().trim();
        String dni = txt_dni.getText().toString().trim();
        String rol= spn_rol_usuario.getSelectedItem().toString().trim();

        if(nombre.isEmpty()){
            Toast.makeText(this, "No ha ingresado su nombres", Toast.LENGTH_SHORT).show();
            return;
        }else if (apellido.isEmpty()){
            Toast.makeText(this, "No ha ingresado su apellido", Toast.LENGTH_SHORT).show();
            return;
        }else if (celular.isEmpty()){
            Toast.makeText(this, "No ha ingresado su celular", Toast.LENGTH_SHORT).show();
            return;
        }else if(password.isEmpty()){
            Toast.makeText(this, "No ha ingresado su contraseña", Toast.LENGTH_SHORT).show();
            return;
        }else if(dni.isEmpty()){
            Toast.makeText(this, "No ha ingresado su DNI", Toast.LENGTH_SHORT).show();
            return;
        }else {
            progressDialog.show();
            StringRequest peticion = new StringRequest(Request.Method.POST, url_insertar_usuario, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if(response.equalsIgnoreCase("datos insertados")){
                        Toast.makeText(AgregarUsuarioActivity.this, "Se ha registrado correctamente", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();

                        startActivity(new Intent(getApplicationContext(), UsuarioListaActivity.class));
                        finish();
                    }else{
                        progressDialog.dismiss();
                        Toast.makeText(AgregarUsuarioActivity.this, "Error en la inserción: " + response, Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(AgregarUsuarioActivity.this, "ERROR DESCONOCIDO: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }){
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("nombre", nombre);
                    params.put("apellidos", apellido);
                    params.put("celular", celular);
                    params.put("clave", password);
                    params.put("dni", dni);
                    params.put("rol", rol);
                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(AgregarUsuarioActivity.this);
            requestQueue.add(peticion);
        }
    }
}