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

import java.util.HashMap;
import java.util.Map;

public class EditarUsuarioActivity extends AppCompatActivity {

    TextView tvt_editar_id;
    EditText txt_editar_nombre;
    EditText txt_editar_apellido;
    EditText txt_editar_celular;
    EditText txt_editar_password;
    EditText txt_editar_dni;
    Spinner spn_editar_rol;
    Spinner spn_editar_estado;
    Button btn_actualizar_usuario;
    String url_actualizar_usuario = "https://webservice.qhapai.com/crudusuario/actualizar.php";
    int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_usuario);

        Intent intent = getIntent();
        position = intent.getExtras().getInt("position");

        tvt_editar_id = findViewById(R.id.tvt_editar_id);
        txt_editar_nombre = findViewById(R.id.txt_editar_nombre);
        txt_editar_apellido = findViewById(R.id.txt_editar_apellido);
        txt_editar_celular = findViewById(R.id.txt_editar_celular);
        txt_editar_password = findViewById(R.id.txt_editar_password);
        txt_editar_dni = findViewById(R.id.txt_editar_dni);
        spn_editar_rol = findViewById(R.id.spn_editar_rol);
        spn_editar_estado = findViewById(R.id.spn_editar_estado);
        btn_actualizar_usuario = findViewById(R.id.btn_actualizar_usuario);

        tvt_editar_id.setText(UsuarioListaActivity.usuarioArrayList.get(position).getId());
        txt_editar_nombre.setText(UsuarioListaActivity.usuarioArrayList.get(position).getNombre());
        txt_editar_apellido.setText(UsuarioListaActivity.usuarioArrayList.get(position).getApellidos());
        txt_editar_celular.setText(UsuarioListaActivity.usuarioArrayList.get(position).getCelular());
        txt_editar_password.setText(UsuarioListaActivity.usuarioArrayList.get(position).getClave());
        txt_editar_dni.setText(UsuarioListaActivity.usuarioArrayList.get(position).getDni());

        btn_actualizar_usuario.setOnClickListener(new View.OnClickListener() {
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

        String id = tvt_editar_id.getText().toString().trim();
        String nombre = txt_editar_nombre.getText().toString().trim();
        String apellido = txt_editar_apellido.getText().toString().trim();
        String celular = txt_editar_celular.getText().toString().trim();
        String password = txt_editar_password.getText().toString().trim();
        String dni = txt_editar_dni.getText().toString().trim();
        String rol = spn_editar_rol.getSelectedItem().toString().trim();
        String estado = spn_editar_estado.getSelectedItem().toString().trim();

        progressDialog.show();
        StringRequest peticion = new StringRequest(Request.Method.POST, url_actualizar_usuario, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equalsIgnoreCase("datos actualizados")){
                    Toast.makeText(EditarUsuarioActivity.this, "Se ha actualizado correctamente", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();

                    startActivity(new Intent(getApplicationContext(), UsuarioListaActivity.class));
                    finish();
                }else {
                    progressDialog.dismiss();
                    Toast.makeText(EditarUsuarioActivity.this, "Error en la actualizacion: "+ response, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(EditarUsuarioActivity.this, "ERROR DESCONOCIDO: "+ error, Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", id);
                params.put("nombre", nombre);
                params.put("apellidos", apellido);
                params.put("celular", celular);
                params.put("clave", password);
                params.put("dni", dni);
                params.put("rol", rol);
                params.put("estado", estado);
                return params;
            }
        };

        //Se envia la peticion
        RequestQueue requestQueue = Volley.newRequestQueue(EditarUsuarioActivity.this);
        requestQueue.add(peticion);
    }
}