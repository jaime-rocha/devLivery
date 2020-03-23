package net.jakare.devlivery.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import net.jakare.devlivery.R;
import net.jakare.devlivery.controller.server.FirebasePedidosController;
import net.jakare.devlivery.model.appClasses.Carrito;
import net.jakare.devlivery.model.dbClasses.Pedido;
import net.jakare.devlivery.ui.adapters.CarritoAdapter;
import net.jakare.devlivery.utils.constants.AppConstants;
import net.jakare.devlivery.utils.global.GlobalFunctions;

import java.util.ArrayList;
import java.util.List;

public class DetallePedido extends AppCompatActivity implements View.OnClickListener {

    final static String LOG = DetallePedido.class.getName();

    private Context context;
    private Pedido pedido;

    private CarritoAdapter adapter;
    private List<Carrito> items = new ArrayList<Carrito>();

    private RecyclerView rvLista;
    private TextView lblTotal;

    private TextView lblNombreCompleto;
    private TextView lblDireccion;
    private TextView lblTelefono;
    private TextView lblObservaciones;
    private Button btnEnviar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_pedido);
        context = this;
        initViews();


        // Handle Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.detalle_pedido));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        rvLista.setHasFixedSize(true);
        RecyclerView.LayoutManager cardLayoutManager = new LinearLayoutManager(context);
        rvLista.setLayoutManager(cardLayoutManager);


        adapter = new CarritoAdapter(context, false);
        rvLista.setAdapter(adapter);


        Intent intent = getIntent();
        if (intent.hasExtra(AppConstants.TAG_ITEM_PEDIDO)) {
            try {
                pedido = new Gson().fromJson(intent.getStringExtra(AppConstants.TAG_ITEM_PEDIDO),
                        Pedido.class);

                List<Carrito> lstCarrito = pedido.getCarrito();
                actualizarCarrito(lstCarrito);

                lblNombreCompleto.setText(pedido.getUsuario());
                lblDireccion.setText(pedido.getDireccion());
                lblTelefono.setText(pedido.getTelefono());
                lblObservaciones.setText(pedido.getObservaciones());

                btnEnviar.setOnClickListener(this);

            } catch (Exception ex) {
                Log.e(LOG + ".onCreate", "" + ex.getMessage());
            }
        }
    }

    private void initViews() {
        rvLista = (RecyclerView) findViewById(R.id.rvLista);
        lblTotal = (TextView) findViewById(R.id.lblTotal);

        lblNombreCompleto = (TextView) findViewById(R.id.lblNombreCompleto);
        lblDireccion = (TextView) findViewById(R.id.lblDireccion);
        lblTelefono = (TextView) findViewById(R.id.lblTelefono);
        lblObservaciones = (TextView) findViewById(R.id.lblObservaciones);
        btnEnviar = (Button) findViewById(R.id.btnEnviar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void actualizarCarrito(List<Carrito> lstCarrito) {
        double total = 0;

        items.clear();
        items.addAll(lstCarrito);
        adapter.swapCursor(items);

        for (Carrito carrito : items) {
            total += carrito.getSubtotal();
        }
        lblTotal.setText(GlobalFunctions.format2Digits(total));
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnEnviar) {
            Toast.makeText(context, "Proximamente", Toast.LENGTH_SHORT).show();
        }
    }
}
