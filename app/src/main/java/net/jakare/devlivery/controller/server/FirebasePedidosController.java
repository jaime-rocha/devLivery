package net.jakare.devlivery.controller.server;

import android.app.Activity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import net.jakare.devlivery.R;
import net.jakare.devlivery.model.dbClasses.Pedido;
import net.jakare.devlivery.utils.constants.AppConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andresvasquez on 24/9/16.
 */

public class FirebasePedidosController {
    private Activity activity;
    private ResultadoGestion callBackGestion;
    private ResultadoLista callBackLista;

    public FirebasePedidosController(Activity activity, ResultadoGestion callBackGestion) {
        this.activity = activity;
        this.callBackGestion = callBackGestion;
    }

    public FirebasePedidosController(Activity activity, ResultadoLista callBackLista) {
        this.activity = activity;
        this.callBackLista = callBackLista;
    }

    public void CrearPedido(Pedido pedido){
        //Firebase upload object
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child(AppConstants.TAG_PEDIDOS).push().setValue(pedido, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if(databaseError!=null){
                    callBackGestion.onResponse(AppConstants.RESULTADO_INCORRECTO,
                            databaseError.getMessage());
                } else {
                    callBackGestion.onResponse(AppConstants.RESULTADO_CORRECTO,
                            activity.getResources().getString(R.string.registro_correcto));
                }
            }
        });
    }

    public void ListasPedidos(int estado){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child(AppConstants.TAG_PEDIDOS).orderByChild(AppConstants.TAG_ESTADO)
                .equalTo(estado).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Pedido> lstPedidos=new ArrayList<Pedido>();
                for(DataSnapshot dataPedido : dataSnapshot.getChildren()){
                    Pedido pedido=dataPedido.getValue(Pedido.class);
                    pedido.setKey(dataPedido.getKey());
                    lstPedidos.add(pedido);
                }
                callBackLista.onResponse(lstPedidos);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public interface ResultadoGestion{
        public void onResponse(int codigoResultado, String mensaje);
    }

    public interface ResultadoLista{
        public void onResponse(List<Pedido> lstPedidos);
    }
}
