package com.brianc.myapplication.clientes;

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
import com.brianc.myapplication.adapter.AdapterCliente;
import com.brianc.myapplication.models.Cliente;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ClienteListaActivity extends AppCompatActivity {

    ListView lvt_cliente;
    FloatingActionButton fab_cliente;
    AdapterCliente adapter;
    public static ArrayList<Cliente> clienteArrayList = new ArrayList<>();
    String url_mostrar = "https://webservice.qhapai.com/crudclientes/mostrar.php";
    String url_eliminar = "https://webservice.qhapai.com/crudclientes/eliminar.php";
    Cliente cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_lista);

        fab_cliente = findViewById(R.id.fab_cliente);
        lvt_cliente = findViewById(R.id.lvt_cliente);
        adapter = new AdapterCliente(this, clienteArrayList);
        lvt_cliente.setAdapter(adapter);

        lvt_cliente.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                ProgressDialog progressDialog = new ProgressDialog(view.getContext());

                CharSequence[] dialogoOpcionesItem = {"Editar registro", "Eliminar registro"};
                builder.setTitle(clienteArrayList.get(position).getDescripcion());
                builder.setItems(dialogoOpcionesItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i){
                            case 0:
                                startActivity(new Intent(getApplicationContext(), EditarClienteActivity.class)
                                        .putExtra("position", position));
                                break;
                            case 1:
                                EliminarRegistro(clienteArrayList.get(position).getId());
                                break;
                        }
                    }
                });

                builder.create().show();

            }
        });

        fab_cliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClienteListaActivity.this, AgregarClienteActivity.class);
                startActivity(intent);
            }
        });

        MostrarRegistros();
    }

    private void MostrarRegistros() {
        StringRequest peticion = new StringRequest(Request.Method.POST, url_mostrar, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                clienteArrayList.clear();

                try {

                    JSONObject jsonObject = new JSONObject(response);
                    String exito = jsonObject.getString("exito");
                    JSONArray jsonArray = jsonObject.getJSONArray("datos");

                    if(exito.equals("1")){
                        for(int i = 0; i < jsonArray.length(); i++){

                            JSONObject object = jsonArray.getJSONObject(i);

                            String id = object.getString("id");
                            String descripcion = object.getString("descripcion");
                            String ruc = object.getString("ruc");
                            String direccion = object.getString("direccion");
                            String celular = object.getString("celular");

                            cliente = new Cliente(id, descripcion, ruc, direccion, celular);
                            clienteArrayList.add(cliente);

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
                Toast.makeText(ClienteListaActivity.this, "Error en la consulta: " + error.getMessage(), Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(ClienteListaActivity.this, "Cliente eliminado", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), ClienteListaActivity.class));
                }else{
                    Toast.makeText(ClienteListaActivity.this, "No se ha podido eliminar al cliente", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ClienteListaActivity.this, "Error en la consulta: " + error.getMessage(), Toast.LENGTH_SHORT).show();
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