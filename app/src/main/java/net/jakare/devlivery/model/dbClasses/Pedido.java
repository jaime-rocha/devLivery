package net.jakare.devlivery.model.dbClasses;

import com.google.gson.annotations.SerializedName;

import net.jakare.devlivery.model.appClasses.Carrito;

import java.util.List;

/**
 * Created by andresvasquez on 19/9/16.
 */

public class Pedido {

    @SerializedName("key")
    private String key;

    @SerializedName("estado")
    private int estado;

    @SerializedName("carrito")
    private List<Carrito> carrito;

    @SerializedName("usuario")
    private String usuario;

    @SerializedName("usuarioId")
    private String usuarioId;

    @SerializedName("direccion")
    private String direccion;

    @SerializedName("telefono")
    private String telefono;

    @SerializedName("observaciones")
    private String observaciones;

    @SerializedName("monto")
    private double monto;

    @SerializedName("lat")
    private double lat;

    @SerializedName("lon")
    private double lon;

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

    public List<Carrito> getCarrito() {
        return carrito;
    }

    public void setCarrito(List<Carrito> carrito) {
        this.carrito = carrito;
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

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }
}
