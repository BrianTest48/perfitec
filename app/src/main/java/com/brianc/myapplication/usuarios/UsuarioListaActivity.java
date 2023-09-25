package com.brianc.myapplication.usuarios;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.brianc.myapplication.adapter.Adapter;
import com.brianc.myapplication.R;
import com.brianc.myapplication.models.Usuario;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UsuarioListaActivity extends AppCompatActivity {

    ListView lvt_usuarios;
    FloatingActionButton fab_agregar;
    Adapter adapter;

    public static ArrayList<Usuario> usuarioArrayList = new ArrayList<>();
    String url_mostrar_usuario = "https://webservice.qhapai.com/crudusuario/mostrar.php";
    String url_eliminar_usuario = "https://webservice.qhapai.com/crudusuario/eliminar.php";
    Usuario usuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_lista);

        fab_agregar = findViewById(R.id.fab_agregar);
        lvt_usuarios = findViewById(R.id.lvt_usuarios);
        adapter = new Adapter(this, usuarioArrayList);
        lvt_usuarios.setAdapter(adapter);

        lvt_usuarios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                ProgressDialog progressDialog = new ProgressDialog(view.getContext());

                CharSequence[] dialogoOpcionesItem = {"Editar Usuario", "Eliminar Usuario"};
                builder.setTitle(usuarioArrayList.get(position).getNombre());
                builder.setItems(dialogoOpcionesItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i){
                            case 0:
                                startActivity(new Intent(getApplicationContext(), EditarUsuarioActivity.class)
                                        .putExtra("position", position));
                                break;
                            case 1:
                                EliminarRegistro(usuarioArrayList.get(position).getId());
                                break;
                        }
                    }
                });

                builder.create().show();

            }
        });

        fab_agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UsuarioListaActivity.this, AgregarUsuarioActivity.class);
                startActivity(intent);
            }
        });

        MostrarRegistros();
    }

    private void MostrarRegistros() {
        StringRequest peticion = new StringRequest(Request.Method.POST, url_mostrar_usuario, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                usuarioArrayList.clear();

                try {

                    JSONObject jsonObject = new JSONObject(response);
                    String exito = jsonObject.getString("exito");
                    JSONArray jsonArray = jsonObject.getJSONArray("datos");

                    if(exito.equals("1")){
                        for(int i = 0; i < jsonArray.length(); i++){

                            JSONObject object = jsonArray.getJSONObject(i);

                            String id = object.getString("id");
                            String nombre = object.getString("nombre");
                            String apellidos = object.getString("apellidos");
                            String celular = object.getString("celular");
                            String clave = object.getString("clave");
                            String dni = object.getString("dni");
                            String rol = object.getString("rol");

                            usuario = new Usuario(id, nombre, apellidos, celular, clave, dni, rol);
                            usuarioArrayList.add(usuario);

                            adapter.notifyDataSetChanged();

                        }
                    }

                }catch (JSONException e){
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(UsuarioListaActivity.this, "Error en la consulta: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(peticion);

    }
    private void EliminarRegistro(String id) {
        StringRequest peticion = new StringRequest(Request.Method.POST, url_eliminar_usuario, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equalsIgnoreCase("datos eliminados")){
                    Toast.makeText(UsuarioListaActivity.this, "Usuario eliminado", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), UsuarioListaActivity.class));
                }else{
                    Toast.makeText(UsuarioListaActivity.this, "No se ha podido eliminar al usuario", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(UsuarioListaActivity.this, "Error en la consulta: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", id);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(peticion);

    }
}