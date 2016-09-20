package net.jakare.devlivery.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.gson.Gson;

import net.jakare.devlivery.R;
import net.jakare.devlivery.model.dbClasses.Producto;
import net.jakare.devlivery.utils.constants.AppConstants;

public class AgregarFotoActivity extends AppCompatActivity implements View.OnClickListener{

    private Context context;
    private SharedPreferences preferences;

    private Producto producto;

    private Button btnAtras;
    private Button btnContinuar;
    private Button btnAdjuntar;

    private ImageView imgAdjuntar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_foto);

        context=this;
        preferences= PreferenceManager.getDefaultSharedPreferences(context);

        Intent intent=getIntent();
        if(intent.hasExtra(AppConstants.TAG_PRODUCTO)){
            producto=new Gson().fromJson(intent.getStringExtra(AppConstants.TAG_PRODUCTO),Producto.class);

            initViews();

            btnAtras.setOnClickListener(this);
            btnContinuar.setOnClickListener(this);
            btnAdjuntar.setOnClickListener(this);
            imgAdjuntar.setOnClickListener(this);
        }
        else {
            finish();
        }
    }

    private void initViews() {
        btnAtras=(Button)findViewById(R.id.btnAtras);
        btnContinuar=(Button)findViewById(R.id.btnContinuar);
        btnAdjuntar=(Button)findViewById(R.id.btnAdjuntar);

        imgAdjuntar=(ImageView)findViewById(R.id.imgAdjuntar);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnContinuar:
                break;
            case R.id.btnAtras:
                finish();
                break;
            case R.id.btnAdjuntar:
                break;
            case R.id.imgAdjuntar:
                break;
            default:
                break;
        }
    }
}
