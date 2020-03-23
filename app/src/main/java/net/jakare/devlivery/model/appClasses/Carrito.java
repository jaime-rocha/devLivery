package net.jakare.devlivery.model.appClasses;

import com.google.gson.annotations.SerializedName;

import net.jakare.devlivery.model.dbClasses.Producto;

/**
 * Created by andresvasquez on 22/9/16.
 */

public class Carrito {

    @SerializedName("id")
    private int id;

    @SerializedName("producto")
    private Producto producto;

    @SerializedName("cantidad")
    private int cantidad;

    @SerializedName("subtotal")
    private double subtotal;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }
}
