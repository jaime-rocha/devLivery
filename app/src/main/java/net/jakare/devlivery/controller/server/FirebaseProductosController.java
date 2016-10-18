package net.jakare.devlivery.controller.server;

import android.app.Activity;
import android.net.Uri;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import net.jakare.devlivery.model.dbClasses.Producto;
import net.jakare.devlivery.utils.constants.AppConstants;

import java.util.List;

/**
 * Created by andresvasquez on 24/9/16.
 */

public class FirebaseProductosController {

    private Activity activity;
    private ResultadoGestion callBackGestion;
    private ResultadoLista callBackLista;

    public FirebaseProductosController(Activity activity, ResultadoGestion callBackGestion) {
        this.activity = activity;
        this.callBackGestion = callBackGestion;
    }

    public FirebaseProductosController(Activity activity, ResultadoLista callBackLista) {
        this.activity = activity;
        this.callBackLista = callBackLista;
    }

    public void AdjuntarFoto(Uri uriFoto){
        //TODO adjuntar foto

        /*callBackGestion.onResponse(AppConstants.RESULTADO_CORRECTO,
                taskSnapshot.getMetadata().getDownloadUrl().toString());*/
    }

    public void CrearProducto(Producto producto){
        //TODO Agregar pedido

        /*if(databaseError!=null){
            callBackGestion.onResponse(AppConstants.RESULTADO_INCORRECTO, databaseError.getMessage());
        } else {
            callBackGestion.onResponse(AppConstants.RESULTADO_CORRECTO,activity.getResources().getString(R.string.registro_correcto));
        }*/
    }

    public void ListasProductos(String categoria){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        Query ref;
        if(!categoria.isEmpty()){
            ref=databaseReference.child(AppConstants.TAG_PRODUCTO)
                    .orderByChild(AppConstants.TAG_CATEGORIA).equalTo(categoria);
        } else {
            ref=databaseReference.child(AppConstants.TAG_PRODUCTO);
        }

        //TODO Listar categoria

        /*List<Producto> lstProductos=new ArrayList<Producto>();
        for(DataSnapshot dataProducto : dataSnapshot.getChildren()){
            Producto producto=dataProducto.getValue(Producto.class);
            producto.setKey(dataProducto.getKey());
            lstProductos.add(producto);
        }

        Log.e("Categoria",new Gson().toJson(lstProductos));

        callBackLista.onResponse(lstProductos);*/
    }


    public interface ResultadoGestion{
        public void onResponse(int codigoResultado, String mensaje);
    }

    public interface ResultadoLista{
        public void onResponse(List<Producto> lstProductos);
    }
}
