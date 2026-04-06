package com.example.tienditamelilu.Controllers;

import com.example.tienditamelilu.Models.Producto;
import com.example.tienditamelilu.Models.ValidadorProducto;
import com.example.tienditamelilu.Services.AgregarService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AgregarController {

    @FXML
    TextField txtId;
    @FXML
    TextField txtNombre;
    @FXML
    TextField txtStock;
    @FXML
    TextField txtPrecio;
    @FXML
    private ComboBox<String> txtCategoria;

    private final AgregarService service = new AgregarService();

    private final ValidadorProducto validador = new ValidadorProducto();

    public void initialize(){

        ObservableList<String> categorias = FXCollections.observableArrayList(
                "Electrónica",
                "Línea Blanca",
                "Accesorios",
                "Mobiliario",
                "Papelería",
                "Otros"
        );

        txtCategoria.setItems(categorias);

        txtCategoria.getSelectionModel().selectFirst();

    }

    @FXML
    public void Agregar() {
        // 1. Recolectamos todo como String (incluso los números)
        String idStr = txtId.getText();
        String nombre = txtNombre.getText();
        String stockStr = txtStock.getText();
        String precioStr = txtPrecio.getText();
        String categoria = txtCategoria.getSelectionModel().getSelectedItem();

        // 2. Llamamos al validador de Models
        // Pasamos 'false' porque es un producto nuevo (queremos que revise si el ID ya existe)
        String mensajeError = validador.validar(idStr, nombre, stockStr, precioStr, categoria, false);

        if (mensajeError != null) {
            // Si hay un error de validación (vacío, símbolos, repetido, etc.), mostramos la alerta y paramos
            mostrarAlerta("Error de validación", mensajeError, Alert.AlertType.WARNING);
            return;
        }

        // 3. Si pasamos la validación, ahora sí es seguro convertir y guardar
        try {
            int id = Integer.parseInt(idStr);
            int stock = Integer.parseInt(stockStr);
            double precio = Double.parseDouble(precioStr);

            Producto nuevo = new Producto(id, nombre, stock, precio, categoria);

            boolean exito = service.guardar(nuevo);

            if (exito) {
                mostrarAlerta("Éxito", "Producto guardado correctamente.", Alert.AlertType.INFORMATION);
                Cerrar(); // Cierra la ventana y refresca la tabla automáticamente
            } else {
                mostrarAlerta("Error", "No se pudo guardar en el CSV.", Alert.AlertType.ERROR);
            }

        } catch (NumberFormatException e) {
            // Este catch queda como respaldo, aunque el validador debería filtrar esto antes
            mostrarAlerta("Error de formato", "Revisa que ID, Stock y Precio sean números válidos.", Alert.AlertType.ERROR);
        }
    }

    public void Cerrar(){
        Stage stage = (Stage) txtId.getScene().getWindow();
        stage.close();
    }


    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
