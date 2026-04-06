package com.example.tienditamelilu.Controllers;

import com.example.tienditamelilu.Services.EliminarService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EliminarController {

    @FXML private TextField txtId;
    @FXML private TextField txtNombre;

    private final EliminarService service = new EliminarService();

    @FXML
    public void eliminar() {
        String idInput = txtId.getText();
        String nombreInput = txtNombre.getText();

        if (idInput.isEmpty() && nombreInput.isEmpty()) {
            mostrarAlerta("Campos vacíos", "Debes llenar al menos el ID o el Nombre para eliminar.", Alert.AlertType.WARNING);
            return;
        }

        boolean exito = service.eliminarPorIdONombre(idInput, nombreInput);

        if (exito) {
            mostrarAlerta("Éxito", "Producto eliminado correctamente.", Alert.AlertType.INFORMATION);
            Cerrar();
        } else {
            mostrarAlerta("No encontrado", "No se encontró ningún producto que coincida.", Alert.AlertType.ERROR);
        }
    }

    // El botón cancelar o cerrar
    @FXML
    public void Cerrar() {
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