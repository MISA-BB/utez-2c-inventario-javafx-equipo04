package com.example.tienditamelilu.Services;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class EliminarService {
    private static final File RUTA_CSV = new File("data/productos.csv");

    public boolean eliminarPorIdONombre(String idBusqueda, String nombreBusqueda) {

        List<String> lineasRestantes = new ArrayList<>();
        boolean encontrado = false;

        // Limpiamos los datos para comparar mejor
        String idB = idBusqueda.trim().toLowerCase();
        String nomB = nombreBusqueda.trim().toLowerCase();

        try (BufferedReader br = new BufferedReader(new FileReader(RUTA_CSV))) {
            String encabezado = br.readLine();
            if (encabezado != null) lineasRestantes.add(encabezado);

            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length < 2) continue;

                String idActual = datos[0].trim().toLowerCase();
                String nombreActual = datos[1].trim().toLowerCase();

                // Lógica: Si coincide el ID (si no está vacío) O coincide el nombre (si no está vacío)
                boolean coincideId = !idB.isEmpty() && idActual.equals(idB);
                boolean coincideNombre = !nomB.isEmpty() && nombreActual.equals(nomB);

                if (coincideId || coincideNombre) {
                    encontrado = true; // Lo saltamos (eliminamos)
                } else {
                    lineasRestantes.add(linea);
                }
            }
        } catch (IOException e) {
            return false;
        }

        if (!encontrado) return false;

        // Guardar cambios
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(RUTA_CSV))) {
            for (int i = 0; i < lineasRestantes.size(); i++) {
                bw.write(lineasRestantes.get(i));
                if (i < lineasRestantes.size() - 1) bw.newLine();
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
