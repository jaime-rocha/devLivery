package net.jakare.devlivery.controller.server;

import android.app.Activity;

import net.jakare.devlivery.model.dbClasses.Pedido;

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
        //TODO Enviar pedido
        /*if(databaseError!=null){
            callBackGestion.onResponse(AppConstants.RESULTADO_INCORRECTO,
                    databaseError.getMessage());
        } else {
            callBackGestion.onResponse(AppConstants.RESULTADO_CORRECTO,
                    activity.getResources().getString(R.string.registro_correcto));
        }*/
    }

    public void ListasPedidos(int estado){
        //TODO Listar pedidos

        /*List<Pedido> lstPedidos=new ArrayList<Pedido>();
        for(DataSnapshot dataPedido : dataSnapshot.getChildren()){
            Pedido pedido=dataPedido.getValue(Pedido.class);
            pedido.setKey(dataPedido.getKey());
            lstPedidos.add(pedido);
        }
        callBackLista.onResponse(lstPedidos);*/
    }

    public interface ResultadoGestion{
        public void onResponse(int codigoResultado, String mensaje);
    }

    public interface ResultadoLista{
        public void onResponse(List<Pedido> lstPedidos);
    }
}
