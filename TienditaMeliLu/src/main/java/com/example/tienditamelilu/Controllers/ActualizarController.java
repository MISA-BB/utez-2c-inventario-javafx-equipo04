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

    //representan los cuadritos de texto y botones que el usuario ve en la ventana
    @FXML private TextField txtId, txtNombre, txtStock, txtPrecio;
    @FXML private ComboBox<String> txtCategoria;

    // Instancias de ayuda: uno para guardar en el archivo y otro para revisar que no haya errores
    private final ActualizarService service = new ActualizarService();
    private final ValidadorProducto validador = new ValidadorProducto();

    /**
     * initialize(): Se ejecuta automáticamente al abrir la ventana.
     * Sirve para preparar los elementos visuales, como llenar las opciones del menú desplegable.
     */
    @FXML
    public void initialize() {
        txtCategoria.setItems(FXCollections.observableArrayList("Electronica", "Accesorios", "Mobiliario", "Otros"));
    }

    /**
     * cargarDatos(): Este método "rellena" el formulario con la info de un producto.
     * Se usa cuando seleccionas algo en una tabla y le das a "Editar".
     */
    public void cargarDatos(Producto p) {
        txtId.setText(String.valueOf(p.getId()));
        txtId.setEditable(false);
        txtNombre.setText(p.getNombre());
        txtStock.setText(String.valueOf(p.getStock()));
        txtPrecio.setText(String.valueOf(p.getPrecio()));
        txtCategoria.setValue(p.getCategoria());
    }

    /**
     * Actualizar(): Es el método principal. Se activa al presionar el botón "Actualizar".
     */
    @FXML
    public void Actualizar() {
        // 1. Recolectamos todos los datos como String
        String idStr = txtId.getText();
        String nombre = txtNombre.getText();
        String stockStr = txtStock.getText();
        String precioStr = txtPrecio.getText();
        String categoria = txtCategoria.getValue();

        // Mandamos al validador por si por si estan vacios o asi
        String mensajeError = validador.validar(idStr, nombre, stockStr, precioStr, categoria, true);

        if (mensajeError != null) {
            mostrarAlerta("Error de validación", mensajeError);
            return;
        }

        // Aqui nos ayuda con atrapar los errores y asi para que no se rompa
        try {
            // Convertimos los textos a numero reales, para crear el objeto producto
            Producto p = new Producto(
                    Integer.parseInt(idStr),
                    nombre,
                    Integer.parseInt(stockStr),
                    Double.parseDouble(precioStr),
                    categoria
            );

            //Le pedimos al Service que busque este producto en el CSV y lo reemplace
            if (service.actualizar(p)) {
                mostrarAlerta("Éxito", "Producto actualizado correctamente.");
                Cerrar();
            } else {
                mostrarAlerta("Error", "No se pudo encontrar el producto para actualizar.");
            }
        } catch (NumberFormatException e) {
            // Si el usuario puso letras en el precio o stock, esta parte "atrapa" el error
            mostrarAlerta("Error de formato", "Verifica que los datos numéricos sean correctos.");
        }
    }

    // crea y muestra una ventana de alerta
    private void mostrarAlerta(String titulo, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    //cierra el formulario
    @FXML
    public void Cerrar() {
        Stage stage = (Stage) txtId.getScene().getWindow();
        stage.close();
    }
}