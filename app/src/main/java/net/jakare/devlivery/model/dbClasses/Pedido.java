package net.jakare.devlivery.model.dbClasses;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by andresvasquez on 19/9/16.
 */

public class Pedido {

    @SerializedName("key")
    private String key;

    @SerializedName("estado")
    private int estado;

    @SerializedName("productos")
    private List<Producto> productos;

    @SerializedName("usuario")
    private String usuario;

    @SerializedName("direccion")
    private String direccion;

    @SerializedName("telefono")
    private String telefono;

    @SerializedName("monto")
    private double monto;


    //Campos de AUD
    @SerializedName("usuarioAtendido")
    private String usuarioAtendido;

    @SerializedName("usuarioDelivery")
    private String usuarioDelivery;

    @SerializedName("fechaPedido")
    private String fechaPedido;

    @SerializedName("fechaDespacho")
    private String fechaDespacho;

    @SerializedName("fechaEntrega")
    private String fechaEntrega;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getUsuarioAtendido() {
        return usuarioAtendido;
    }

    public void setUsuarioAtendido(String usuarioAtendido) {
        this.usuarioAtendido = usuarioAtendido;
    }

    public String getUsuarioDelivery() {
        return usuarioDelivery;
    }

    public void setUsuarioDelivery(String usuarioDelivery) {
        this.usuarioDelivery = usuarioDelivery;
    }

    public String getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(String fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public String getFechaDespacho() {
        return fechaDespacho;
    }

    public void setFechaDespacho(String fechaDespacho) {
        this.fechaDespacho = fechaDespacho;
    }

    public String getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(String fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }
}
