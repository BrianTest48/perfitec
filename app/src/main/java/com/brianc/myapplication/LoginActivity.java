package com.brianc.myapplication;

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

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText txt_username;
    EditText txt_password;
    Button btn_login;
    String url_ingresar = "https://webservice.qhapai.com/ingresar.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txt_username = findViewById(R.id.txt_username);
        txt_password = findViewById(R.id.txt_password);
        btn_login    = findViewById(R.id.btn_login);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IniciarSesion();
            }
        });
    }

    private void IniciarSesion() {

        //Mostramos un ProgressDialog
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Autenticando");
        progressDialog.setMessage("Espere porfavor ......");
        progressDialog.setCancelable(false);

        String username = txt_username.getText().toString().trim();
        String password = txt_password.getText().toString().trim();

        if(username.isEmpty()){
            Toast.makeText(this, "No ha ingresado su usuario", Toast.LENGTH_SHORT).show();
            return;
        }else if( password.isEmpty()){
            Toast.makeText(this, "No ha ingresado su contraseña", Toast.LENGTH_SHORT).show();
            return;
        }else {
            progressDialog.show();
            StringRequest peticion = new StringRequest(Request.Method.POST, url_ingresar, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if(response.equalsIgnoreCase("1")){
                        Toast.makeText(LoginActivity.this, "Bienvenido", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();

                        startActivity(new Intent(getApplicationContext(), PrincipalActivity.class));
                        finish();
                    }else{
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this, "Credenciales Incorrectas", Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "ERROR DESCONOCIDO: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }){
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("dni", username);
                    params.put("clave", password);
                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
            requestQueue.add(peticion);
        }
    }
}