package net.jakare.devlivery.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.gson.Gson;

import net.jakare.devlivery.R;
import net.jakare.devlivery.controller.data.SharedPreferencesData;
import net.jakare.devlivery.model.dbClasses.Producto;
import net.jakare.devlivery.model.dbClasses.User;
import net.jakare.devlivery.utils.constants.AppConstants;

public class AgregarProductoActivity extends AppCompatActivity implements View.OnClickListener{

    private Context context;
    private SharedPreferences preferences;

    private String categoria;
    private Spinner cmbCategoria;
    private EditText txtNombreProducto;
    private EditText txtPrecio;
    private EditText txtDescripcion;

    private Button btnCrear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_producto);
        context=this;
        preferences= PreferenceManager.getDefaultSharedPreferences(context);

        // Handle Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        initViews();
        btnCrear.setOnClickListener(this);
        cmbCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                categoria=adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
    }

    private void initViews() {
        cmbCategoria=(Spinner)findViewById(R.id.cmbCategoria);
        txtNombreProducto=(EditText) findViewById(R.id.txtNombreProducto);
        txtPrecio=(EditText)findViewById(R.id.txtPrecio);
        txtDescripcion=(EditText)findViewById(R.id.txtDescripcion);
        btnCrear=(Button)findViewById(R.id.btnCrear);
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.btnCrear){
            String nombreProducto=txtNombreProducto.getText().toString().trim();
            String precio=txtPrecio.getText().toString().trim();
            String descripcion=txtDescripcion.getText().toString().trim();

            if(nombreProducto.isEmpty()){
                txtNombreProducto.setError(getResources().getString(R.string.error_nombre));
                return;
            }

            if(precio.isEmpty()){
                txtPrecio.setError(getResources().getString(R.string.error_precio));
                return;
            }

            if(descripcion.isEmpty()){
                txtDescripcion.setError(getResources().getString(R.string.error_descripcion));
                return;
            }

            double precioProducto=0;
            try
            {
                precioProducto = Double.parseDouble(precio);
                if(precioProducto<=0){
                    txtPrecio.setError(getResources().getString(R.string.error_precio_valido));
                }
            }
            catch (NumberFormatException ex){
                txtPrecio.setError(getResources().getString(R.string.error_precio_valido));
                return;
            }

            SharedPreferencesData prefs=new SharedPreferencesData(context);
            User user=prefs.getUserData();

            Producto producto=new Producto();
            producto.setNombre(nombreProducto);
            producto.setPrecio(precioProducto);
            producto.setDescripcion(descripcion);
            producto.setUsuarioCreador(user.getIdUser());
            producto.setCategoria(categoria);

            Intent agregarFotos=new Intent(context,AgregarFotoActivity.class);
            agregarFotos.putExtra(AppConstants.TAG_PRODUCTO,new Gson().toJson(producto));
            startActivity(agregarFotos);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            AgregarProductoActivity.this.finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
