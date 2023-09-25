package com.brianc.myapplication.maquinas;

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
import com.brianc.myapplication.R;
import com.brianc.myapplication.adapter.AdapterMaquina;
import com.brianc.myapplication.adapter.AdapterProducto;
import com.brianc.myapplication.models.Maquina;
import com.brianc.myapplication.models.Producto;
import com.brianc.myapplication.productos.AgregarProductoActivity;
import com.brianc.myapplication.productos.EditarProductoActivity;
import com.brianc.myapplication.productos.ProductoListaActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MaquinaListaActivity extends AppCompatActivity {


    ListView lvt_maquinas;
    FloatingActionButton fab_agregar_maquina;
    AdapterMaquina adapter;
    public static ArrayList<Maquina> maquinaArrayList = new ArrayList<>();
    String url_mostrar = "https://webservice.qhapai.com/crudmaquinas/mostrar.php";
    String url_eliminar = "https://webservice.qhapai.com/crudmaquinas/eliminar.php";
    Maquina maquina;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maquina_lista);

        lvt_maquinas = findViewById(R.id.lvt_maquinas);
        fab_agregar_maquina = findViewById(R.id.fab_agregar_maquina);
        adapter = new AdapterMaquina(this, maquinaArrayList);
        lvt_maquinas.setAdapter(adapter);

        lvt_maquinas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                ProgressDialog progressDialog = new ProgressDialog(view.getContext());

                CharSequence[] dialogoOpcionesItem = {"Editar Registro", "Eliminar registro"};
                builder.setTitle(maquinaArrayList.get(position).getDescripcion());
                builder.setItems(dialogoOpcionesItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i){
                            case 0:
                                startActivity(new Intent(getApplicationContext(), EditarMaquinaActivity.class)
                                        .putExtra("position", position));
                                break;
                            case 1:
                                EliminarRegistro(maquinaArrayList.get(position).getId());
                                break;
                        }
                    }
                });

                builder.create().show();

            }
        });

        fab_agregar_maquina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MaquinaListaActivity.this, AgregarMaquinaActivity.class);
                startActivity(intent);
            }
        });

        MostrarRegistros();
    }

    private void MostrarRegistros() {
        StringRequest peticion = new StringRequest(Request.Method.POST, url_mostrar, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                maquinaArrayList.clear();

                try {

                    JSONObject jsonObject = new JSONObject(response);
                    String exito = jsonObject.getString("exito");
                    JSONArray jsonArray = jsonObject.getJSONArray("datos");

                    if(exito.equals("1")){
                        for(int i = 0; i < jsonArray.length(); i++){

                            JSONObject object = jsonArray.getJSONObject(i);

                            String id = object.getString("id");
                            String descripcion = object.getString("descripcion");
                            String anio = object.getString("anio");

                            maquina = new Maquina(id, descripcion, anio);
                            maquinaArrayList.add(maquina);

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
                Toast.makeText(MaquinaListaActivity.this, "Error en la consulta: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(peticion);

    }

    private void EliminarRegistro(String id) {
        StringRequest peticion = new StringRequest(Request.Method.POST, url_eliminar, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equalsIgnoreCase("datos eliminados")){
                    Toast.makeText(MaquinaListaActivity.this, "Usuario eliminado", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), MaquinaListaActivity.class));
                }else{
                    Toast.makeText(MaquinaListaActivity.this, "No se ha podido eliminar al usuario", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MaquinaListaActivity.this, "Error en la consulta: " + error.getMessage(), Toast.LENGTH_SHORT).show();
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