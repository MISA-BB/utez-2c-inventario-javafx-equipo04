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

    // Representan los cuadritos de texto y botones que el usuario ve en la ventana
    @FXML private TextField txtId, txtNombre, txtStock, txtPrecio;
    @FXML private ComboBox<String> txtCategoria;

    private final ActualizarService service = new ActualizarService();
    private final ValidadorProducto validador = new ValidadorProducto();

    @FXML
    public void initialize() {
        txtCategoria.setItems(FXCollections.observableArrayList("Electronica", "Accesorios", "Mobiliario", "Otros"));
    }

    /**
     * cargarDatos(): Este método actúa como un "rellenador" automático del formulario.
     * Recibe un objeto de tipo Producto (p) que contiene la información actual.
     */
    public void cargarDatos(Producto p) {
        // Toma el ID del producto (que es un número) y lo convierte a Texto para ponerlo en el campo txtId
        txtId.setText(String.valueOf(p.getId()));
        // Bloquea el campo del ID para que el usuario NO pueda borrarlo ni cambiarlo.
        // Esto es importante porque el ID es la llave única para identificar el producto en el CSV.
        txtId.setEditable(false);
        // Pone el nombre actual del producto en el cuadro de texto correspondiente
        txtNombre.setText(p.getNombre());
        // Convierte el número de existencias (stock) a texto y lo muestra en su campo
        txtStock.setText(String.valueOf(p.getStock()));
        // Convierte el precio (double) a texto para mostrarlo en el campo de precio
        txtPrecio.setText(String.valueOf(p.getPrecio()));
        // Selecciona automáticamente en el ComboBox la categoría que ya tiene asignada el producto
        txtCategoria.setValue(p.getCategoria());
    }

    /**
     * Actualizar(): Método principal para guardar los cambios.
     */
    @FXML
    public void Actualizar() {
        // Recolectamos todos los datos como String
        String idStr = txtId.getText();
        String nombre = txtNombre.getText();
        String stockStr = txtStock.getText();
        String precioStr = txtPrecio.getText();
        String categoria = txtCategoria.getValue();

        // Validación básica (campos vacíos, etc.)
        String mensajeError = validador.validar(idStr, nombre, stockStr, precioStr, categoria, true);

        if (mensajeError != null) {
            mostrarAlerta("Error de validación", mensajeError);
            return;
        }

        // cacha errores
        try {
            // Convertimos los textos a números
            int stock = Integer.parseInt(stockStr);
            double precio = Double.parseDouble(precioStr);

            // precio debe ser mayor a cero
            if (precio <= 0) {
                mostrarAlerta("Error de Precio", "El precio debe ser mayor a cero.");
                return; // Detenemos el proceso
            }

            // Si el precio es válido, creamos el objeto Producto
            Producto p = new Producto(
                    Integer.parseInt(idStr),
                    nombre,
                    stock,
                    precio,
                    categoria
            );

            // se guarda el archivo CSV
            if (service.actualizar(p)) {
                mostrarAlerta("Éxito", "Producto actualizado correctamente.");
                Cerrar();
            } else {
                mostrarAlerta("Error", "No se pudo encontrar el producto para actualizar.");
            }

        } catch (NumberFormatException e) {
            // Si el usuario puso letras en precio o stock, atrapamos el error aquí
            mostrarAlerta("Error de formato", "Verifica que los datos numéricos sean correctos.");
        }
    }

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