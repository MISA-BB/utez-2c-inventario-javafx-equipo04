package com.example.tienditamelilu.Controllers;

import com.example.tienditamelilu.Models.Producto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

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

    private ObservableList<Producto> listaProductos = FXCollections.observableArrayList();

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

    public void Agregar(){}
    public void Cerrar(){}

}
