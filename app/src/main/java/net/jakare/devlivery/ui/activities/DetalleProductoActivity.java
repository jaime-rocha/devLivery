package net.jakare.devlivery.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import net.jakare.devlivery.R;
import net.jakare.devlivery.controller.data.SharedPreferencesData;
import net.jakare.devlivery.model.dbClasses.Producto;
import net.jakare.devlivery.model.dbClasses.User;
import net.jakare.devlivery.utils.constants.AppConstants;

public class DetalleProductoActivity extends AppCompatActivity {

    final static String LOG = DetalleProductoActivity.class.getName();
    private Context context;

    private FloatingActionButton fab;
    private ImageView header;
    private TextView lblNombreProducto;
    private TextView lblDescripcion;
    private TextView lblPrecio;

    private Producto item;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_producto);

        context=this;
        initViews();

        SharedPreferencesData prefs=new SharedPreferencesData(context);
        user=prefs.getUserData();

        // Handle Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        Intent intent=getIntent();
        try {
            String strBid=intent.getStringExtra(AppConstants.TAG_ITEM_PRODUCTO);
            item=new Gson().fromJson(strBid,Producto.class);

            getSupportActionBar().setTitle(item.getNombre());

            lblNombreProducto.setText(""+item.getNombre());
            lblDescripcion.setText(""+item.getDescripcion());
            lblPrecio.setText(""+item.getPrecio());


            Glide.with(context)
                    .load(item.getFoto())
                    .centerCrop()
                    .placeholder(R.drawable.img_no_disponible)
                    .crossFade()
                    .into(header);

            //Return selected product
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intentResultado=new Intent();
                    intentResultado.putExtra(AppConstants.TAG_PRODUCTO,new Gson().toJson(item));
                    setResult(RESULT_OK,intentResultado);
                    finish();
                }
            });
        }
        catch (Exception ex)
        {
            Log.e(LOG+".onCreate", ""+ex.getMessage());
        }
    }

    private void initViews() {
        fab=(FloatingActionButton)findViewById(R.id.fab);
        header=(ImageView) findViewById(R.id.header);
        lblNombreProducto=(TextView)findViewById(R.id.lblNombreProducto);
        lblDescripcion=(TextView)findViewById(R.id.lblDescripcion);
        lblPrecio=(TextView)findViewById(R.id.lblPrecio);
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
}
