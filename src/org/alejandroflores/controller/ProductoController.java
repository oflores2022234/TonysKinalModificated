
package org.alejandroflores.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.alejandroflores.bean.Producto;
import org.alejandroflores.db.Conexion;
import org.alejandroflores.main.Principal;
import javafx.animation.ScaleTransition;
import javafx.util.Duration;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.FadeTransition;
import javafx.scene.layout.Pane;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;


import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.FileOutputStream;

import java.io.*;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import org.alejandroflores.bean.Servicio;
import org.alejandroflores.bean.TipoPlato;


public class ProductoController implements Initializable {
    private enum operaciones{NUEVO, GUARDAR, ELIMINAR, ACTUALIZAR, CANCELAR, NINGUNO};
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private Principal escenarioPrincipal;
    private ObservableList<Producto> listaProducto;
    
    @FXML private TextField txtCodigoProducto;
    @FXML private TextField txtNombreProducto;
    @FXML private TextField txtCantidadProducto;
    @FXML private TableView tblProductos;
    @FXML private TableColumn colCodigoProducto;
    @FXML private TableColumn colNombreProducto;
    @FXML private TableColumn colCantidadProducto;
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    @FXML private ImageView imgNuevo;
    @FXML private ImageView imgEliminar;
    @FXML private ImageView imgEditar;
    @FXML private ImageView imgReporte;
    
    @FXML private Pane pane1;
    @FXML private Pane pane2;
    @FXML private Pane pane3;
    @FXML private Pane pane4;
    @FXML private Pane pane5;
    @FXML private Pane pane6;
    @FXML private Pane pane7;
    @FXML private Pane pane8;
    @FXML private Pane pane9;
    @FXML private Pane pane10;
    @FXML private Pane pane11;
    @FXML private Pane pane12;
    @FXML private Pane pane13;
    @FXML private Pane pane14;
    @FXML private Pane pane15;
    @FXML private Pane pane16;
    @FXML private Pane pane17;
    @FXML private Pane pane18;
        
    @FXML private Button btnGenerarExcel;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        
        addAnimation(btnNuevo);
        addAnimation(btnEliminar);
        addAnimation(btnEditar);
        addAnimation(btnReporte);
        
        aplicarAnimacionDesvanecimiento(pane1);
        aplicarAnimacionDesvanecimiento(pane2);
        aplicarAnimacionDesvanecimiento(pane3);
        aplicarAnimacionDesvanecimiento(pane4);
        aplicarAnimacionDesvanecimiento(pane5);
        aplicarAnimacionDesvanecimiento(pane6);
        aplicarAnimacionDesvanecimiento(pane7);
        aplicarAnimacionDesvanecimiento(pane8);
        aplicarAnimacionDesvanecimiento(pane9);
        aplicarAnimacionDesvanecimiento(pane10);
        aplicarAnimacionDesvanecimiento(pane11);
        aplicarAnimacionDesvanecimiento(pane12);
        aplicarAnimacionDesvanecimiento(pane13);
        aplicarAnimacionDesvanecimiento(pane14);
        aplicarAnimacionDesvanecimiento(pane15);
        aplicarAnimacionDesvanecimiento(pane16);
        aplicarAnimacionDesvanecimiento(pane17);
        aplicarAnimacionDesvanecimiento(pane18);
        
        btnGenerarExcel.setOnAction(event -> generarExcel());
        
    }
    
    private void addAnimation(Button button) {
        double originalScaleX = button.getScaleX();
        double originalScaleY = button.getScaleY();

        button.setOnMouseEntered(event -> {
            Timeline timeline = new Timeline();
            timeline.getKeyFrames().addAll(
                    new KeyFrame(Duration.ZERO,
                            new KeyValue(button.scaleXProperty(), originalScaleX),
                            new KeyValue(button.scaleYProperty(), originalScaleY)),
                    new KeyFrame(Duration.millis(200),
                            new KeyValue(button.scaleXProperty(), originalScaleX * 1.2),
                            new KeyValue(button.scaleYProperty(), originalScaleY * 1.2))
            );
            timeline.play();
        });

        button.setOnMouseExited(event -> {
            Timeline timeline = new Timeline();
            timeline.getKeyFrames().addAll(
                    new KeyFrame(Duration.ZERO,
                            new KeyValue(button.scaleXProperty(), originalScaleX * 1.2),
                            new KeyValue(button.scaleYProperty(), originalScaleY * 1.2)),
                    new KeyFrame(Duration.millis(200),
                            new KeyValue(button.scaleXProperty(), originalScaleX),
                            new KeyValue(button.scaleYProperty(), originalScaleY))
            );
            timeline.play();
        });
    }
    
    private void aplicarAnimacionDesvanecimiento(Pane pane) {
        // Configurar el Pane con una opacidad inicial
        pane.setOpacity(1);

        // Crear una transición de desvanecimiento
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(2000), pane);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0.5);
        fadeTransition.setAutoReverse(true);
        fadeTransition.setCycleCount(FadeTransition.INDEFINITE);

        // Iniciar la transición
        fadeTransition.play();
    }
    
    public void cargarDatos(){
        tblProductos.setItems(getProducto());
        colCodigoProducto.setCellValueFactory(new PropertyValueFactory<Producto, Integer>("codigoProducto"));
        colNombreProducto.setCellValueFactory(new PropertyValueFactory<Producto, String>("nombreProducto"));
        colCantidadProducto.setCellValueFactory(new PropertyValueFactory<Producto, Integer>("cantidad"));
    }
    
    public ObservableList<Producto> getProducto(){
        ArrayList<Producto> lista = new ArrayList<Producto>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_ListarProductos");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new  Producto(resultado.getInt("codigoProducto"), 
                        resultado.getString("nombreProducto"),resultado.getInt("cantidad")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaProducto = FXCollections.observableArrayList(lista);
    }
    
    
    public void nuevo(){
        switch(tipoDeOperacion){
            case NINGUNO:
                    limpiarControles();
                    activarControles();
                    btnNuevo.setText("Guardar");
                    btnEliminar.setText("Cancelar");
                    btnEditar.setDisable(true);
                    btnReporte.setDisable(true);
                    imgNuevo.setImage(new Image("/org/alejandroflores/image/guardar.png"));
                    imgEliminar.setImage(new Image("/org/alejandroflores/image/cancelar.png"));
                    tipoDeOperacion = operaciones.GUARDAR;
             break;
            case GUARDAR:
                    guardar();
                    limpiarControles();
                    desactivarControles();
                    btnNuevo.setText("Nuevo");
                    btnEliminar.setText("Eliminar");
                    btnEditar.setDisable(false);
                    btnReporte.setDisable(false);
                    imgNuevo.setImage(new Image("/org/alejandroflores/image/Nuevo.png"));
                    imgEliminar.setImage(new Image("/org/alejandroflores/image/Eliminar.png"));
                    tipoDeOperacion = operaciones.NINGUNO;
                    cargarDatos();
             break;
        }
    }
    
    public void eliminar(){
        switch(tipoDeOperacion){
            case GUARDAR:
                limpiarControles();
                desactivarControles();
                btnNuevo.setText("Nuevo");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                imgNuevo.setImage(new Image("/org/alejandroflores/image/Nuevo.png"));
                imgEliminar.setImage(new Image("/org/alejandroflores/image/Eliminar.png"));
                tipoDeOperacion = operaciones.NINGUNO;
                break;
            default:
                if(tblProductos.getSelectionModel().getSelectedItem() != null){
                    int respuesta = JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar el registro?", "Eliminar Producto", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if(respuesta == JOptionPane.YES_OPTION){
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_EliminarProducto(?)");
                            procedimiento.setInt(1,((Producto)tblProductos.getSelectionModel().getSelectedItem()).getCodigoProducto());
                            procedimiento.execute();
                            listaProducto.remove(tblProductos.getSelectionModel().getSelectedItem());
                            limpiarControles();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                
                
                }else{
                    JOptionPane.showMessageDialog(null, "Debe Seleccionar un elemento");
                }
        }
    }
    
    public void editar(){
        switch(tipoDeOperacion){
            case NINGUNO:
                if(tblProductos.getSelectionModel().getSelectedItem() != null){
                    btnNuevo.setDisable(true);
                    btnEliminar.setDisable(true);
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    imgEditar.setImage(new Image("/org/alejandroflores/image/actualizar.png"));
                    imgReporte.setImage(new Image("/org/alejandroflores/image/cancelar.png"));
                    activarControles();
                    tipoDeOperacion = operaciones.ACTUALIZAR;
                }else{
                    JOptionPane.showMessageDialog(null, "Debe Seleccionar un elemento");
                }
                break;
            case ACTUALIZAR:
                actualizar();
                limpiarControles();
                desactivarControles();
                btnNuevo.setDisable(false);
                btnEliminar.setDisable(false);
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");
                imgEditar.setImage(new Image("/org/alejandroflores/image/Editar.png"));
                imgReporte.setImage(new Image("/org/alejandroflores/image/report.png"));
                cargarDatos();
                tipoDeOperacion = operaciones.NINGUNO;
                break;
        }
    }
    
    public void reporte(){
        switch(tipoDeOperacion){
            case ACTUALIZAR:
                actualizar();
                limpiarControles();
                desactivarControles();
                btnNuevo.setDisable(false);
                btnEliminar.setDisable(false);
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");
                imgEditar.setImage(new Image("/org/alejandroflores/image/Editar.png"));
                imgReporte.setImage(new Image("/org/alejandroflores/image/report.png"));
                cargarDatos();
                tipoDeOperacion = operaciones.NINGUNO;
                break;
        }
    }
    
    public void actualizar(){
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_EditarProducto(?, ?, ?)");
            Producto registro = (Producto)tblProductos.getSelectionModel().getSelectedItem();
            registro.setCodigoProducto(Integer.parseInt(txtCodigoProducto.getText()));
            registro.setNombreProducto(txtNombreProducto.getText());
            registro.setCantidad(Integer.parseInt(txtCantidadProducto.getText()));
            procedimiento.setInt(1, registro.getCodigoProducto());
            procedimiento.setString(2, registro.getNombreProducto());
            procedimiento.setInt(3, registro.getCantidad());
            procedimiento.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    
    }

    
    public void guardar(){
        Producto registro = new Producto();
        registro.setCodigoProducto(Integer.parseInt(txtCodigoProducto.getText()));
        registro.setNombreProducto(txtNombreProducto.getText());
        registro.setCantidad(Integer.parseInt(txtCantidadProducto.getText()));
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall(" call sp_AgregarProducto (?, ?, ?)");
            procedimiento.setInt(1, registro.getCodigoProducto());
            procedimiento.setString(2, registro.getNombreProducto());
            procedimiento.setInt(3, registro.getCantidad());
            procedimiento.execute();
            listaProducto.add(registro);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    
    public void generarExcel() {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Guardar Archivo Excel");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos Excel", "*.xls"));
            File file = fileChooser.showSaveDialog(null);

            if (file != null) {
                Workbook workbook = new HSSFWorkbook();
                Sheet sheet = workbook.createSheet("TablaProducto");

                // Crear encabezados de columna
                Row headerRow = sheet.createRow(0);
                headerRow.createCell(0).setCellValue("Código Producto");
                headerRow.createCell(1).setCellValue("Nombre Proeucto");
                headerRow.createCell(2).setCellValue("Cantidad");


                int rowNum = 1;
                for (Producto producto : listaProducto) {
                    Row row = sheet.createRow(rowNum++);
                    row.createCell(0).setCellValue(producto.getCodigoProducto());
                    row.createCell(1).setCellValue(producto.getNombreProducto());
                    row.createCell(2).setCellValue(producto.getCantidad());

                }

                // Autoajustar el ancho de las columnas
                for (int col = 0; col < 8; col++) {
                    sheet.autoSizeColumn(col);
                }

                FileOutputStream fileOut = new FileOutputStream(file);
                workbook.write(fileOut);
                fileOut.close();

                System.out.println("Archivo Excel generado exitosamente.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
   
    public void seleccionarElemento(){
        txtCodigoProducto.setText(String.valueOf(((Producto)tblProductos.getSelectionModel().getSelectedItem()).getCodigoProducto()));
        txtNombreProducto.setText(((Producto)tblProductos.getSelectionModel().getSelectedItem()).getNombreProducto());
        txtCantidadProducto.setText(String.valueOf(((Producto)tblProductos.getSelectionModel().getSelectedItem()).getCantidad()));
    }
    
    
    public void desactivarControles(){
        txtCodigoProducto.setEditable(false);
        txtNombreProducto.setEditable(false);
        txtCantidadProducto.setEditable(false);
    }
    
    public void activarControles(){
       txtCodigoProducto.setEditable(true);
        txtNombreProducto.setEditable(true);
        txtCantidadProducto.setEditable(true); 
    }
    
    public void limpiarControles(){
        txtCodigoProducto.setText("");
        txtNombreProducto.setText("");
        txtCantidadProducto.setText("");
    }
    
    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    public void menuPrincipal(){
        escenarioPrincipal.menuPrincipal();
    }
    
}
