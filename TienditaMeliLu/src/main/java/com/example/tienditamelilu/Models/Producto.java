package com.example.tienditamelilu.Models;

public class Producto {
    // Atributos privados
    private int id;
    private String nombre;
    private int stock;
    private double precio;
    private String categoria;

    // Constructor vacío (buena práctica)
    public Producto() {
    }

    // Constructor con parámetros para crear productos rápidamente
    public Producto(int id, String nombre, int stock, double precio, String categoria) {
        this.id = id;
        this.nombre = nombre;
        this.stock = stock;
        this.precio = precio;
        this.categoria = categoria;
    }

    // --- MÉTODOS GETTER (Indispensables para la TableView) ---
    // Si no pones estos métodos, la tabla aparecerá vacía aunque le pases datos.

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getPrecio() {
        return precio;
    }



    public String getCategoria() {
        return categoria;
    }


    // Método opcional para imprimir el objeto (útil para pruebas en consola)
    @Override
    public String toString() {
        return "Producto{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", stock=" + stock +
                ", precio=" + precio +
                ", categoria='" + categoria + '\'' +
                '}';
    }
}