package net.jakare.devlivery.utils.constants;

/**
 * Created by andresvasquez on 19/9/16.
 */

public class AppConstants {
    public static final String TAG_PRODUCTO = "maindish_producto";
    public static final String DIRECTORY_NAME = "maindish";

    //Firebase
    public static final String TAG_STORAGE = "gs://devlivery-a8edd.appspot.com";
    public static final String TAG_IMAGENES_PRODUCTOS = "maindish_productos";
    public static final String TAG_PRODUCTOS = "maindish_productos";
    public static final String TAG_PEDIDOS = "maindish_pedidos";
    public static final String TAG_ESTADO = "estado";
    public static final String TAG_CATEGORIA = "categoria";

    public static final String TAG_ITEM_PRODUCTO = "productoItem";
    public static final String TAG_ITEM_PEDIDO = "pedidoItem";

    //Estados del pedido
    public static final int ESTADO_PREPARACION = 1;
    public static final int ESTADO_DELIVERY = 2;
    public static final int ESTADO_FINALIZADO = 3;

    public static final String CATEGORIA_BEBIDA = "Bebidas";

    //Resultados acciones
    public static final int RESULTADO_CORRECTO = 1;
    public static final int RESULTADO_INCORRECTO = 2;
}
