package com.example.tienditamelilu.Controllers;
import com.example.tienditamelilu.Models.Producto;

import com.example.tienditamelilu.Services.BuscarService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class InicioController {

    @FXML
    TextField txtId;
    @FXML
    TextField txtNombre;
    @FXML
    TableView tablaProductos;

    private ObservableList<Producto> listaProductos = FXCollections.observableArrayList();

    private final BuscarService buscarService = new BuscarService();

    private final BuscarService service = new BuscarService();

    @FXML
    public void initialize() {
        tablaProductos.getColumns().clear();

        TableColumn<Producto, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Producto, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        TableColumn<Producto, Integer> colStock = new TableColumn<>("Stock");
        colStock.setCellValueFactory(new PropertyValueFactory<>("stock"));

        TableColumn<Producto, Double> colPrecio = new TableColumn<>("Precio");
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));

        TableColumn<Producto, String> colCategoria = new TableColumn<>("Categoría");
        colCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));

        tablaProductos.getColumns().addAll(colId, colNombre, colStock, colPrecio, colCategoria);

        tablaProductos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        tablaProductos.setItems(listaProductos);

        cargarDatosCSV();
    }

    private void cargarDatosCSV() {
        String ruta = "data/productos.csv";
        String linea;

        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            br.readLine();

            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length == 5) {
                    Producto p = new Producto(
                            Integer.parseInt(datos[0]),
                            datos[1],
                            Integer.parseInt(datos[2]),
                            Double.parseDouble(datos[3]),
                            datos[4]
                    );
                    listaProductos.add(p);
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el CSV: " + e.getMessage());
        }
    }
    @FXML
    public void Agregar() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/tienditamelilu/Views/agregar-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Agregar Producto");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);

        stage.showAndWait();
        //wait (un esperaaa,ella no te ama, como yo te amo xdxd)

        refrescarTabla();
    }

    @FXML
    public void Eliminar() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/tienditamelilu/Views/eliminar-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Eliminar Producto");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);

        stage.showAndWait();

        refrescarTabla();
    }

    @FXML
    public void Actualizar() throws IOException {
        Producto seleccionado = (Producto) tablaProductos.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setContentText("Por favor, selecciona un producto de la tabla.");
            alerta.show();
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/tienditamelilu/Views/actualizar-view.fxml"));
        Scene scene = new Scene(loader.load());

        ActualizarController controller = loader.getController();
        controller.cargarDatos(seleccionado);

        Stage stage = new Stage();
        stage.setTitle("Actualizar Producto");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();

        listaProductos.clear();
        cargarDatosCSV();
    }

    @FXML
    public void Buscar() {
        String id = txtId.getText();
        String nombre = txtNombre.getText();

        List<Producto> resultados = service.buscar(id, nombre);

        listaProductos.clear();
        listaProductos.addAll(resultados);
        tablaProductos.setItems(listaProductos);
    }

    private void refrescarTabla() {
        listaProductos.clear();
        listaProductos.addAll(cargarDatosDesdeCSV());
        tablaProductos.setItems(listaProductos);
    }

    private List<Producto> cargarDatosDesdeCSV() {
        return buscarService.buscar("", "");
    }
}