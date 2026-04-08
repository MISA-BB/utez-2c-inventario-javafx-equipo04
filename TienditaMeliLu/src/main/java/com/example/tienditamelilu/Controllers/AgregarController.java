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
                "Lácteos y Huevos",
                "Salchichonería",
                "Panadería",
                "Abarrotes",
                "Bebidas y Refrescos",
                "Botanas",
                "Frutas y Verduras",
                "Limpieza del Hogar",
                "Cuidado Personal",
                "Cervecería y Vinos",
                "Dulcería",
                "Otros"
        );

        txtCategoria.setItems(categorias);

        // Accede al modelo de selección del ComboBox y marca el primer elemento (índice 0) como seleccionado
        txtCategoria.getSelectionModel().selectFirst();

    }

    @FXML
    public void Agregar() {
        String idStr = txtId.getText();
        String nombre = txtNombre.getText();
        String stockStr = txtStock.getText();
        String precioStr = txtPrecio.getText();
        String categoria = txtCategoria.getSelectionModel().getSelectedItem();

        String mensajeError = validador.validar(idStr, nombre, stockStr, precioStr, categoria, false);

        if (mensajeError != null) {
            mostrarAlerta("Error de validación", mensajeError, Alert.AlertType.WARNING);
            return;
        }

        try {
            int id = Integer.parseInt(idStr);
            int stock = Integer.parseInt(stockStr);
            double precio = Double.parseDouble(precioStr);

            // --- CORRECCIÓN: Validamos ANTES de guardar ---
            if (stock <= 0) {
                mostrarAlerta("Error de Inventario", "El stock inicial debe ser mayor a cero.", Alert.AlertType.WARNING);
                return; // Se sale sin guardar
            }

            if (precio <= 0) {
                mostrarAlerta("Error de Precio", "El precio debe ser mayor a cero.", Alert.AlertType.WARNING);
                return; // Se sale sin guardar
            }

            // Ahora que estamos seguros de que los datos son correctos, guardamos
            Producto nuevo = new Producto(id, nombre, stock, precio, categoria);
            boolean exito = service.guardar(nuevo);

            if (exito) {
                mostrarAlerta("Éxito", "Producto guardado correctamente.", Alert.AlertType.INFORMATION);
                Cerrar();
            } else {
                mostrarAlerta("Error", "No se pudo guardar en el CSV.", Alert.AlertType.ERROR);
            }

        } catch (NumberFormatException e) {
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
