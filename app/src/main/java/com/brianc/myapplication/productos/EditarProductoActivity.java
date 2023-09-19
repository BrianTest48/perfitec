package com.brianc.myapplication.productos;

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

public class EditarProductoActivity extends AppCompatActivity {

    EditText  txt_editar_nombre, txt_editar_tipo;
    TextView tvt_producto_editar_id;
    Button btn_actualizar_producto;
    Spinner spn_actualizar_producto;
    int position;
    String url_editar = "https://webservice.qhapai.com/crudproductos/actualizar.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_producto);

        Intent intent = getIntent();
        position = intent.getExtras().getInt("position");

        tvt_producto_editar_id = findViewById(R.id.tvt_producto_editar_id);
        txt_editar_nombre = findViewById(R.id.txt_editar_nombre);
        txt_editar_tipo = findViewById(R.id.txt_editar_tipo);
        btn_actualizar_producto = findViewById(R.id.btn_actualizar_producto);

        tvt_producto_editar_id.setText(ProductoListaActivity.productoArrayList.get(position).getId());
        txt_editar_nombre.setText(ProductoListaActivity.productoArrayList.get(position).getNombre());
        txt_editar_tipo.setText(ProductoListaActivity.productoArrayList.get(position).getTipo());

        btn_actualizar_producto.setOnClickListener(new View.OnClickListener() {
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

        String id = tvt_producto_editar_id.getText().toString().trim();
        String descripcion = txt_editar_nombre.getText().toString().trim();
        String tipo = txt_editar_tipo.getText().toString().trim();

        progressDialog.show();
        StringRequest peticion = new StringRequest(Request.Method.POST, url_editar, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equalsIgnoreCase("datos actualizados")){
                    Toast.makeText(EditarProductoActivity.this, "Se ha actualizado correctamente", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();

                    startActivity(new Intent(getApplicationContext(), ProductoListaActivity.class));
                    finish();
                }else {
                    progressDialog.dismiss();
                    Toast.makeText(EditarProductoActivity.this, "Error en la actualizacion: "+ response, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(EditarProductoActivity.this, "ERROR DESCONOCIDO: "+ error, Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", id);
                params.put("nombre", descripcion);
                params.put("tipo", tipo);
                return params;
            }
        };

        //Se envia la peticion
        RequestQueue requestQueue = Volley.newRequestQueue(EditarProductoActivity.this);
        requestQueue.add(peticion);
    }
}