package com.example.tienditamelilu.Controllers;

import com.example.tienditamelilu.Models.Producto;
import com.example.tienditamelilu.Models.ValidadorProducto;
import com.example.tienditamelilu.Services.ActualizarService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ActualizarController {

    @FXML private TextField txtId, txtNombre, txtStock, txtPrecio;
    @FXML private ComboBox<String> txtCategoria;

    private final ActualizarService service = new ActualizarService();
    private final ValidadorProducto validador = new ValidadorProducto();

    @FXML
    public void initialize() {
        txtCategoria.setItems(FXCollections.observableArrayList("Electronica", "Accesorios", "Mobiliario", "Otros"));
    }

    public void cargarDatos(Producto p) {
        txtId.setText(String.valueOf(p.getId()));
        txtId.setEditable(false);
        txtNombre.setText(p.getNombre());
        txtStock.setText(String.valueOf(p.getStock()));
        txtPrecio.setText(String.valueOf(p.getPrecio()));
        txtCategoria.setValue(p.getCategoria());
    }

    @FXML
    public void Actualizar() {
        // 1. Recolectamos todos los datos como String
        String idStr = txtId.getText();
        String nombre = txtNombre.getText();
        String stockStr = txtStock.getText();
        String precioStr = txtPrecio.getText();
        String categoria = txtCategoria.getValue();

        // 2. Llamamos al validador (true porque es actualización)
        String mensajeError = validador.validar(idStr, nombre, stockStr, precioStr, categoria, true);

        if (mensajeError != null) {
            // CORREGIDO: Ahora solo enviamos 2 argumentos
            mostrarAlerta("Error de validación", mensajeError);
            return;
        }

        // 3. Conversión y guardado
        try {
            Producto p = new Producto(
                    Integer.parseInt(idStr),
                    nombre,
                    Integer.parseInt(stockStr),
                    Double.parseDouble(precioStr),
                    categoria
            );

            if (service.actualizar(p)) {
                // CORREGIDO: Ahora solo enviamos 2 argumentos
                mostrarAlerta("Éxito", "Producto actualizado correctamente.");
                Cerrar();
            } else {
                // CORREGIDO: Ahora solo enviamos 2 argumentos
                mostrarAlerta("Error", "No se pudo encontrar el producto para actualizar.");
            }
        } catch (NumberFormatException e) {
            // CORREGIDO: Ahora solo enviamos 2 argumentos
            mostrarAlerta("Error de formato", "Verifica que los datos numéricos sean correctos.");
        }
    }

    // Tu método de 2 argumentos (déjalo así)
    private void mostrarAlerta(String titulo, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    @FXML
    public void Cerrar() {
        Stage stage = (Stage) txtId.getScene().getWindow();
        stage.close();
    }
}