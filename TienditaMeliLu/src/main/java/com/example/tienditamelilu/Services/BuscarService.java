package com.example.tienditamelilu.Services;

import com.example.tienditamelilu.Models.Producto;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class BuscarService {
    private static final File RUTA_CSV = new File("data/productos.csv");

    public List<Producto> buscar(String idBusqueda, String nombreBusqueda) {
        List<Producto> resultados = new ArrayList<>();
        List<Producto> todos = obtenerTodos();

        String idB = idBusqueda.trim().toLowerCase();//todos los casos a minusculas
        String nomB = nombreBusqueda.trim().toLowerCase();

        for (Producto p : todos) {
            if (idB.isEmpty() && nomB.isEmpty()) {
                resultados.add(p);
                continue;
            }

            boolean coincideId = !idB.isEmpty() && String.valueOf(p.getId()).contains(idB);
            boolean coincideNombre = !nomB.isEmpty() && p.getNombre().toLowerCase().contains(nomB);

            if (!idB.isEmpty() && !nomB.isEmpty()) {
                if (coincideId && coincideNombre) resultados.add(p);
            } else if (coincideId || coincideNombre) {
                resultados.add(p);
            }
        }
        return resultados;
    }

    private List<Producto> obtenerTodos() {
        List<Producto> lista = new ArrayList<>();
        if (!RUTA_CSV.exists()) return lista;

        try (BufferedReader br = new BufferedReader(new FileReader(RUTA_CSV))) {
            br.readLine();
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] d = linea.split(",");
                if (d.length == 5) {
                    lista.add(new Producto(
                            Integer.parseInt(d[0].trim()), d[1].trim(),
                            Integer.parseInt(d[2].trim()), Double.parseDouble(d[3].trim()), d[4].trim()
                    ));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
}
