package net.jakare.devlivery.ui.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.jakare.devlivery.R;
import net.jakare.devlivery.controller.data.SharedPreferencesData;
import net.jakare.devlivery.controller.server.FirebasePedidosController;
import net.jakare.devlivery.model.appClasses.Carrito;
import net.jakare.devlivery.model.dbClasses.Pedido;
import net.jakare.devlivery.model.dbClasses.User;
import net.jakare.devlivery.utils.constants.AppConstants;
import net.jakare.devlivery.utils.global.GlobalFunctions;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ProcesarPedidoActivity extends AppCompatActivity implements View.OnClickListener{

    private Context context;

    private EditText txtNombreCompleto;
    private EditText txtDireccion;
    private EditText txtTelefono;
    private EditText txtObservaciones;

    private Button btnEnviar;
    private List<Carrito> lstCarrito;

    private double lat=0;
    private double lon=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_procesar_pedido);

        context=this;
        initViews();

        // Handle Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.datos_pedido));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        btnEnviar.setOnClickListener(this);

        Intent intent=getIntent();
        if(intent.hasExtra(AppConstants.TAG_ITEM_PEDIDO)){
            Type listType = new TypeToken<ArrayList<Carrito>>(){}.getType();

            lstCarrito = new Gson().fromJson(intent.getStringExtra(AppConstants.TAG_ITEM_PEDIDO), listType);
        }
        else {
            finish();
        }
    }

    private void initViews(){
        txtNombreCompleto=(EditText)findViewById(R.id.txtNombreCompleto);
        txtDireccion=(EditText)findViewById(R.id.txtDireccion);
        txtTelefono=(EditText)findViewById(R.id.txtTelefono);
        txtObservaciones=(EditText)findViewById(R.id.txtObservaciones);

        btnEnviar=(Button)findViewById(R.id.btnEnviar);
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.btnEnviar){
            String nombreCompleto=txtNombreCompleto.getText().toString().trim();
            String direccion=txtDireccion.getText().toString().trim();
            String telefono=txtTelefono.getText().toString().trim();
            String observaciones=txtObservaciones.getText().toString().trim();

            if(nombreCompleto.isEmpty()){
                txtNombreCompleto.setError(getResources().getString(R.string.error_nombre));
                return;
            }

            if(direccion.isEmpty()){
                txtDireccion.setError(getResources().getString(R.string.error_direccion));
                return;
            }

            if(telefono.isEmpty()){
                txtTelefono.setError(getResources().getString(R.string.error_telefono));
                return;
            }


            SharedPreferencesData prefs=new SharedPreferencesData(context);
            User user=prefs.getUserData();

            Pedido pedido=new Pedido();
            pedido.setUsuarioId(user.getIdUser());
            pedido.setUsuario(nombreCompleto);
            pedido.setDireccion(direccion);
            pedido.setCarrito(lstCarrito);
            pedido.setFechaPedido(GlobalFunctions.getDate());
            pedido.setObservaciones(observaciones);
            pedido.setTelefono(telefono);
            pedido.setLat(lat);
            pedido.setLon(lon);

            double monto=0;
            for (Carrito carrito : lstCarrito)
                monto+=carrito.getSubtotal();

            pedido.setMonto(monto);

            showProgressDialog();
            FirebasePedidosController pedidosController=new FirebasePedidosController(this,
                    new FirebasePedidosController.ResultadoGestion() {
                @Override
                public void onResponse(int codigoResultado, String mensaje) {
                    hideProgressDialog();

                    Toast.makeText(context,mensaje, Toast.LENGTH_SHORT).show();

                    if(codigoResultado==AppConstants.RESULTADO_CORRECTO){
                        Intent main=new Intent(context,MainActivity.class);
                        main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                        ProcesarPedidoActivity.this.finish();
                        startActivity(main);
                    }
                }
            });
            pedidosController.CrearPedido(pedido);
        }
    }

    private ProgressDialog progressDialog;

    protected void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage(getString(R.string.progress_dialog_loading));
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setIndeterminate(true);
            progressDialog.setMax(100);
        }
        progressDialog.show();
    }

    protected void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
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
            ProcesarPedidoActivity.this.finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
