package com.example.tienditamelilu.Services;

import com.example.tienditamelilu.Models.Producto;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class AgregarService {

    private final String RUTA_CSV = "data/productos.csv";

    public boolean guardar(Producto producto) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(RUTA_CSV, true))) {
            String nuevaLinea = String.format("%d,%s,%d,%.2f,%s",
                    producto.getId(),
                    producto.getNombre(),
                    producto.getStock(),
                    producto.getPrecio(),
                    producto.getCategoria());

            bw.newLine();
            bw.write(nuevaLinea);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
