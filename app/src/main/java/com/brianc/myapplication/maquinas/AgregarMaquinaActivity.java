package com.brianc.myapplication.maquinas;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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
import com.brianc.myapplication.adapter.AdapterProductoMaquina;
import com.brianc.myapplication.models.Producto;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class AgregarMaquinaActivity extends AppCompatActivity implements AdapterProductoMaquina.OnCheckboxCheckedListener {

    EditText txt_maquina_desripcion;
    EditText txt_maquina_anio;
    Button btn_registro_maquina;
    ListView lvt_producto_maquina;
    AdapterProductoMaquina adapter;
    public static ArrayList<Producto> productoArrayList = new ArrayList<>();
    Producto producto;
    ArrayList<Integer> valoresSeleccionados = new ArrayList<>();

    String url_mostrar = "https://webservice.qhapai.com/crudproductos/mostrar.php";
    String url_insertar = "https://webservice.qhapai.com/crudmaquinas/insertar.php";

    int choosenYear = 2023;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_maquina);

        txt_maquina_desripcion = findViewById(R.id.txt_maquina_descripcion);
        txt_maquina_anio = findViewById(R.id.txt_maquina_anio);
        btn_registro_maquina = findViewById(R.id.btn_registro_maquina);

        lvt_producto_maquina = findViewById(R.id.lvt_producto_maquina);
        adapter = new AdapterProductoMaquina(this, productoArrayList);
        lvt_producto_maquina.setAdapter(adapter);

        // Establece esta actividad como oyente para las casillas de verificación en el adaptador
        adapter.setOnCheckboxCheckedListener(this);

        btn_registro_maquina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InsertarMaquina();
            }
        });
        txt_maquina_anio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenDialog();
            }
        });

        MostrarRegistros();
    }

    private void OpenDialog() {
       DatePickerDialog dialog =  new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                txt_maquina_anio.setText(String.valueOf(year) + "." + String.valueOf(month) + " " + String.valueOf(day));
            }
        }, 2023, 0, 15);

        dialog.show();


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

                    if (exito.equals("1")) {
                        for (int i = 0; i < jsonArray.length(); i++) {

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
                } catch (JSONException e) {
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
    public void onCheckboxChecked(int position) {
        // Este método se llama cuando se marca o desmarca una casilla de verificación en el adaptador
        boolean isChecked = productoArrayList.get(position).isChecked();
        int valor = Integer.parseInt(productoArrayList.get(position).getId());

        if (isChecked) {
            // La casilla de verificación está marcada
            if (!valoresSeleccionados.contains(valor)) {
                valoresSeleccionados.add(valor);
            }
        } else {
            // La casilla de verificación está desmarcada
            if (valoresSeleccionados.contains(valor)) {
                valoresSeleccionados.remove(Integer.valueOf(valor));
            }
        }

        // Puedes realizar acciones adicionales aquí en respuesta al cambio de estado
        // Por ejemplo, mostrar un mensaje o realizar actualizaciones en tu base de datos
        StringBuilder mensaje = new StringBuilder("Elementos seleccionados: ");
        for (int valorSeleccionado : valoresSeleccionados) {
            mensaje.append(valorSeleccionado).append(", ");
        }

        if (!valoresSeleccionados.isEmpty()) {
            mensaje.delete(mensaje.length() - 2, mensaje.length());
        }

        //Toast.makeText(this, mensaje.toString(), Toast.LENGTH_SHORT).show();
    }

    private void InsertarMaquina() {

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Registrando");
        progressDialog.setMessage("Espere porfavor...");
        progressDialog.setCancelable(false);

        String descripcion = txt_maquina_desripcion.getText().toString().trim();
        String anio = txt_maquina_anio.getText().toString().trim();


        if(descripcion.isEmpty()){
            Toast.makeText(this, "No ha ingresado la descripcion", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(anio.isEmpty()){
            Toast.makeText(this, "No ha ingresado el año", Toast.LENGTH_SHORT).show();
            return;
        }else {
            progressDialog.show();
            StringRequest peticion = new StringRequest(Request.Method.POST, url_insertar, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if(response.equalsIgnoreCase("datos insertados")){
                        Toast.makeText(AgregarMaquinaActivity.this, "Se ha registrado correctamente", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();

                        startActivity(new Intent(getApplicationContext(), MaquinaListaActivity.class));
                        finish();
                    }else{
                        progressDialog.dismiss();
                        Toast.makeText(AgregarMaquinaActivity.this, "Error en la inserción: " + response, Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(AgregarMaquinaActivity.this, "ERROR DESCONOCIDO: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }){
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    String productosSeleccionados = TextUtils.join(",",valoresSeleccionados);
                    Map<String, String> params = new HashMap<>();
                    params.put("descripcion", descripcion);
                    params.put("anio",anio);
                    params.put("productos", productosSeleccionados);

                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(AgregarMaquinaActivity.this);
            requestQueue.add(peticion);

            //Toast.makeText(this, "Peticion : " + peticion, Toast.LENGTH_LONG).show();

        }
    }


}