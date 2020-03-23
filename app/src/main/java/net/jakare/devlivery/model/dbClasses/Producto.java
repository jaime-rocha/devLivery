package net.jakare.devlivery.model.dbClasses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by andresvasquez on 19/9/16.
 */

public class Producto {
    @SerializedName("key")
    private String key;

    @SerializedName("categoria")
    private String categoria;

    @SerializedName("nombre")
    private String nombre;

    @SerializedName("precio")
    private double precio;

    @SerializedName("descripcion")
    private String descripcion;

    @SerializedName("foto")
    private String foto;

    @SerializedName("estado")
    private int estado;

    @SerializedName("usuarioCreador")
    private String usuarioCreador;

    @SerializedName("fechaCreacion")
    private String fechaCreacion;

    @SerializedName("stats")
    private String stats;

    @SerializedName("ingredientes")
    private String[] ingredientes;

    @SerializedName("nombreEn")
    private String nombreEn;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getUsuarioCreador() {
        return usuarioCreador;
    }

    public void setUsuarioCreador(String usuarioCreador) {
        this.usuarioCreador = usuarioCreador;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getStats() {
        return stats;
    }

    public void setStats(String stats) {
        this.stats = stats;
    }

    public String getNombreEn() {
        return nombreEn;
    }

    public void setNombreEn(String nombreEn) {
        this.nombreEn = nombreEn;
    }

    public String[] getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(String[] ingredientes) {
        this.ingredientes = ingredientes;
    }
}
