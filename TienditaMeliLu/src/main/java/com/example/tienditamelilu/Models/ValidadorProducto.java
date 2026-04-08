package com.example.tienditamelilu.Models;

import com.example.tienditamelilu.Services.BuscarService;
import java.util.List;

public class ValidadorProducto {

    // Crea una copia privada e inalterable de la herramienta de búsqueda para usarla en esta clase
    private final BuscarService buscarService = new BuscarService();

    public String validar(String id, String nombre, String stock, String precio, String categoria, boolean esActualizacion) {

        if (estaVacio(id) || estaVacio(nombre) || estaVacio(stock) || estaVacio(precio) || categoria == null) {
            return "Todos los campos son obligatorios.";
        }

        if (!id.matches("^[a-zA-Z0-9]+$")) {
            return "El ID debe ser alfanumérico (sin espacios ni símbolos).";
        }

        if (contieneEspeciales(id) || contieneEspeciales(nombre)) {
            return "No se permiten caracteres especiales como @, !, $, #, \", etc.";
        }

        if (!nombre.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$")) {
            return "El nombre solo debe contener letras.";
        }
        if (nombre.trim().length() < 3) {
            return "El nombre debe tener al menos 3 caracteres.";
        }

        if (!stock.matches("^\\d+$")) {
            return "El stock debe ser un número entero positivo.";
        }

        if (!precio.matches("^\\d+(\\.\\d+)?$")) {
            return "El precio debe ser un número válido (ej: 10.50).";
        }

        if (!esActualizacion) {
            if (idExiste(id)) {
                return "El ID ya existe. Por favor usa uno diferente.";
            }
        }

        return null;
    }

    private boolean estaVacio(String s) {
        return s == null || s.trim().isEmpty();
    }

    private boolean contieneEspeciales(String s) {
        return s.matches(".*[@!$#\"'%;&/()=?¿¡*].*");
    }

    private boolean idExiste(String id) {
        List<Producto> productos = buscarService.buscar("", "");
        return productos.stream().anyMatch(p -> String.valueOf(p.getId()).equalsIgnoreCase(id));
    }
}