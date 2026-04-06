package com.example.tienditamelilu.Services;

import com.example.tienditamelilu.Models.Producto;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ActualizarService {

    private static final File RUTA_CSV = new File("data/productos.csv");

    public boolean actualizar(Producto pModificado) {
        List<Producto> lista = obtenerTodos();
        boolean encontrado = false;

        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId() == pModificado.getId()) {
                lista.set(i, pModificado);
                encontrado = true;
                break;
            }
        }

        return encontrado ? reescribirCSV(lista) : false;
    }

    public List<Producto> obtenerTodos() {
        List<Producto> productos = new ArrayList<>();
        if (!RUTA_CSV.exists()) return productos;

        try (BufferedReader br = new BufferedReader(new FileReader(RUTA_CSV))) {
            br.readLine();
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] d = linea.split(",");
                if (d.length == 5) {
                    productos.add(new Producto(
                            Integer.parseInt(d[0].trim()),
                            d[1].trim(),
                            Integer.parseInt(d[2].trim()),
                            Double.parseDouble(d[3].trim()),
                            d[4].trim()
                    ));
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el CSV: " + e.getMessage());
        }
        return productos;
    }

    private boolean reescribirCSV(List<Producto> lista) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(RUTA_CSV))) {
            bw.write("id,nombre,stock,precio,categoria");

            for (Producto p : lista) {
                bw.newLine();
                bw.write(p.getId() + "," +
                        p.getNombre() + "," +
                        p.getStock() + "," +
                        p.getPrecio() + "," +
                        p.getCategoria());
            }
            return true;
        } catch (IOException e) {
            System.err.println("Error al escribir el CSV: " + e.getMessage());
            return false;
        }
    }
}