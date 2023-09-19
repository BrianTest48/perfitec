package com.brianc.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.brianc.myapplication.clientes.ClienteListaActivity;
import com.brianc.myapplication.maquinas.MaquinaListaActivity;
import com.brianc.myapplication.productos.ProductoListaActivity;
import com.brianc.myapplication.usuarios.UsuarioListaActivity;

public class PrincipalActivity extends AppCompatActivity {

    LinearLayout btn_usuarios;
    LinearLayout btn_clientes;
    LinearLayout btn_productos;
    LinearLayout btn_maquinas;
    Button btn_cerrar_sesion;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        btn_usuarios = findViewById(R.id.btn_usuarios);
        btn_clientes = findViewById(R.id.btn_clientes);
        btn_productos = findViewById(R.id.btn_productos);
        btn_maquinas = findViewById(R.id.btn_maquinas);
        btn_cerrar_sesion = findViewById(R.id.btn_cerrar_sesion);



        btn_usuarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PrincipalActivity.this, UsuarioListaActivity.class);
                startActivity(intent);
            }
        });

        btn_maquinas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PrincipalActivity.this, MaquinaListaActivity.class);
                startActivity(intent);
            }
        });

        btn_clientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PrincipalActivity.this, ClienteListaActivity.class);
                startActivity(intent);
            }
        });

        btn_productos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PrincipalActivity.this, ProductoListaActivity.class);
                startActivity(intent);
            }
        });

        btn_cerrar_sesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PrincipalActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}