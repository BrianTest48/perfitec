package com.brianc.myapplication.maquinas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.brianc.myapplication.R;
import com.brianc.myapplication.adapter.AdapterProducto;
import com.brianc.myapplication.adapter.AdapterProductoMaquina;
import com.brianc.myapplication.models.Producto;
import com.brianc.myapplication.productos.ProductoListaActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AgregarMaquinaActivity extends AppCompatActivity implements  AdapterProductoMaquina.CheckboxCheckedListener {

    ListView lvt_producto_maquina;
    AdapterProductoMaquina adapter;
    public static ArrayList<Producto> productoArrayList = new ArrayList<>();
    String url_mostrar = "https://webservice.qhapai.com/crudproductos/mostrar.php";
    String url_insertar = "https://webservice.qhapai.com/crudmaquinas/insertar.php";
    Producto producto;

    ArrayList<Integer> valoresSeleccionados = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_maquina);

        lvt_producto_maquina = findViewById(R.id.lvt_producto_maquina);
        adapter = new AdapterProductoMaquina(this, productoArrayList);
        lvt_producto_maquina.setAdapter(adapter);
        adapter.setCheckedListener(this);

        MostrarRegistros();
    }


    private void MostrarRegistros() {
        StringRequest peticion = new StringRequest(Request.Method.POST, url_mostrar, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                productoArrayList.clear();

                try {

                    JSONObject jsonObject = new JSONObject(response);
                    String exito = jsonObject.getString("exito");
                    JSONArray jsonArray = jsonObject.getJSONArray("datos");

                    if(exito.equals("1")){
                        for(int i = 0; i < jsonArray.length(); i++){

                            JSONObject object = jsonArray.getJSONObject(i);

                            String id = object.getString("id");
                            String nombre = object.getString("nombre");
                            String tipo = object.getString("tipo");

                            producto = new Producto(id, nombre, tipo);
                            //producto = new Producto(id, nombre, tipo, true);
                            productoArrayList.add(producto);

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
                Toast.makeText(AgregarMaquinaActivity.this, "Error en la consulta: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(peticion);

    }

    @Override
    public void getCheckBoxCheckedListener(int position) {

        boolean isChecked = true;
        if (isChecked) {
            int valor = Integer.parseInt(productoArrayList.get(position).getId());
            if (!valoresSeleccionados.contains(valor)) {
                valoresSeleccionados.add(valor);
            }else {
                valoresSeleccionados.remove(Integer.valueOf(valor));
            }
        } else {
            int valor = Integer.parseInt(productoArrayList.get(position).getId());
            if (valoresSeleccionados.contains(valor)) {
                valoresSeleccionados.remove(Integer.valueOf(valor));
            }
        }
        // Construye un mensaje que enumera todos los elementos seleccionados
        StringBuilder mensaje = new StringBuilder("Elementos seleccionados: ");
        for (int valor : valoresSeleccionados) {
            mensaje.append(valor).append(", ");
        }
        // Elimina la coma y el espacio final
        if (!valoresSeleccionados.isEmpty()) {
            mensaje.delete(mensaje.length() - 2, mensaje.length());
        }
        // Muestra un Toast con la lista de elementos seleccionados
        Toast.makeText(this, mensaje.toString(), Toast.LENGTH_SHORT).show();

    }

}