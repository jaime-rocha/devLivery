package net.jakare.devlivery.utils.constants;

/**
 * Created by andresvasquez on 19/9/16.
 */

public class AppConstants {
    public static final String TAG_PRODUCTO="producto";
    public static final String DIRECTORY_NAME = "devLivery";

    //Firebase
    public static final String TAG_STORAGE="gs://devlivery-a8edd.appspot.com";
    public static final String TAG_IMAGENES_PRODUCTOS = "productos";
    public static final String TAG_PRODUCTOS = "productos";
    public static final String TAG_PEDIDOS = "pedidos";


    public static final String TAG_ITEM_PRODUCTO = "productoItem";
    public static final String TAG_ITEM_PEDIDO = "pedidoItem";

    //Estados del pedido
    public static final int ESTADO_PREPARACION = 1;
    public static final int ESTADO_DELIVERY = 2;
    public static final int ESTADO_FINALIZADO = 3;

}
