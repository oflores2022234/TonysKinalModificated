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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.alejandroflores.bean.Plato;
import org.alejandroflores.bean.Servicio;
import org.alejandroflores.bean.ServicioHasPlato;
import org.alejandroflores.db.Conexion;
import org.alejandroflores.main.Principal;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.FileOutputStream;

import java.io.*;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;



public class ServicioHaPlatoController implements Initializable {
    
    private Principal escenarioPrincipal;
    private enum operaciones {GUARDAR, ELIMINAR, ACTUALIZAR, NINGUNO};
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private ObservableList<ServicioHasPlato> listaServicioHasPlato;
    private ObservableList<Plato> listaPlato;
    private ObservableList<Servicio> listaServicio;
    
    @FXML private TextField txtServicios_codigoServicio;
    @FXML private ComboBox cmbCodigoPlato;
    @FXML private ComboBox cmbCodigoServicio;
    @FXML private TableView tblServicios_has_Platos;
    @FXML private TableColumn colServicios_codigoServicio;
    @FXML private TableColumn colCodigoPlato;
    @FXML private TableColumn colCodigoServicio;
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    
    @FXML private Button btnGenerarExcel;
    
    @FXML private ImageView imgNuevo;
    @FXML private ImageView imgEliminar;
    @FXML private ImageView imgEditar;
    @FXML private ImageView imgReporte;
    

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        cmbCodigoPlato.setItems(getPlato());
        cmbCodigoServicio.setItems(getServicio());
        btnGenerarExcel.setOnAction(event -> generarExcel());
    }
    
    public void cargarDatos(){
        tblServicios_has_Platos.setItems(getServiciosHasPlatos());
        colServicios_codigoServicio.setCellValueFactory(new PropertyValueFactory<ServicioHasPlato, Integer>("Servicios_codigoServicio"));
        colCodigoPlato.setCellValueFactory(new PropertyValueFactory<ServicioHasPlato, Integer>("codigoPlato"));
        colCodigoServicio.setCellValueFactory(new PropertyValueFactory<ServicioHasPlato, Integer>("codigoServicio"));
    }
    
    public void seleccionarElemento(){
        txtServicios_codigoServicio.setText(String.valueOf(((ServicioHasPlato)tblServicios_has_Platos.getSelectionModel().getSelectedItem()).getServicios_codigoServicio()));
        cmbCodigoPlato.getSelectionModel().select(buscarPlato(((ServicioHasPlato)tblServicios_has_Platos.getSelectionModel().getSelectedItem()).getCodigoPlato()));
        cmbCodigoServicio.getSelectionModel().select(buscarServicio(((ServicioHasPlato)tblServicios_has_Platos.getSelectionModel().getSelectedItem()).getCodigoServicio()));
    }
    
    public Plato buscarPlato (int codigoPlato){
        Plato resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{Call sp_BuscarPlato(?)}");
            procedimiento.setInt(1, codigoPlato);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()){
                resultado = new Plato(registro.getByte("codigoPlato"),
                        registro.getByte("cantidad"),
                        registro.getString("nombrePlato"),
                        registro.getString("descripcionPlato"),
                        registro.getDouble("precioPlato"),
                        registro.getByte("codigoTipoPlato"));
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }
    
    public Servicio buscarServicio(int codigoServicio){
        Servicio resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{Call sp_BuscarServicio(?)}");
            procedimiento.setInt(1, codigoServicio);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()){
                resultado = new Servicio(registro.getByte("codigoServicio"),
                        registro.getDate("fechaServicio"),
                        registro.getString("tipoServicio"),
                        registro.getTime("horaServicio"),
                        registro.getString("lugarServicio"),
                        registro.getString("telefonoContacto"),
                        registro.getByte("codigoEmpresa"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }
    
    public ObservableList<ServicioHasPlato> getServiciosHasPlatos(){
        ArrayList<ServicioHasPlato> lista = new ArrayList<ServicioHasPlato>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{Call sp_ListarServicios_has_Platos()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new ServicioHasPlato(resultado.getInt("Servicios_codigoServicio"),
                        resultado.getInt("codigoPlato"),
                        resultado.getInt("codigoServicio")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaServicioHasPlato = FXCollections.observableArrayList(lista);
    }
    
    public ObservableList<Plato> getPlato(){
        ArrayList<Plato> lista = new ArrayList();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{Call sp_ListarPlatos}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Plato(resultado.getInt("codigoPlato"),
                        resultado.getInt("cantidad"),
                        resultado.getString("nombrePlato"),
                        resultado.getString("descripcionPlato"),
                        resultado.getDouble("precioPlato"),
                        resultado.getInt("codigoTipoPlato")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaPlato = FXCollections.observableArrayList(lista);
    }
    
    public ObservableList <Servicio> getServicio(){
        ArrayList <Servicio> lista = new ArrayList();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_ListarServicios");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Servicio(resultado.getInt("codigoServicio"),
                        resultado.getDate("fechaServicio"),
                        resultado.getString("tipoServicio"),
                        resultado.getTime("horaServicio"),
                        resultado.getString("lugarServicio"),
                        resultado.getString("telefonoContacto"),
                        resultado.getInt("codigoEmpresa")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return listaServicio = FXCollections.observableArrayList(lista);
    }
    
    public void nuevo(){
        switch(tipoDeOperacion){
            case NINGUNO:
                limpiarControles();
                activarControles();
                btnNuevo.setText("Guardar");
                btnEliminar.setText("Cancelar");
                imgNuevo.setImage(new Image("/org/alejandroflores/image/guardar.png"));
                imgEliminar.setImage(new Image("/org/alejandroflores/image/cancelar.png"));
                tipoDeOperacion = operaciones.GUARDAR;
                break;
            case GUARDAR:
                guardar();
                limpiarControles();
                desactivarControles();
                btnNuevo.setText("Nuevo");
                btnEliminar.setText("  ");
                imgNuevo.setImage(new Image("/org/alejandroflores/image/Nuevo.png"));
                imgEliminar.setImage(null);
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
                btnEliminar.setText("");
                imgNuevo.setImage(new Image("/org/alejandroflores/image/Nuevo.png"));
                imgEliminar.setImage(null);
                tipoDeOperacion = operaciones.NINGUNO;
                break;
        }
    }
    
    public void guardar(){
        ServicioHasPlato registro = new ServicioHasPlato();
        registro.setServicios_codigoServicio(Integer.parseInt(txtServicios_codigoServicio.getText()));
        registro.setCodigoPlato(((Plato)cmbCodigoPlato.getSelectionModel().getSelectedItem()).getCodigoPlato());
        registro.setCodigoServicio(((Servicio)cmbCodigoServicio.getSelectionModel().getSelectedItem()).getCodigoServicio());
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_AgregarServicioHasPlato(?, ?, ?)");
            procedimiento.setInt(1, registro.getServicios_codigoServicio());
            procedimiento.setInt(2, registro.getCodigoPlato());
            procedimiento.setInt(3, registro.getCodigoServicio());
            procedimiento.execute();
            listaServicioHasPlato.add(registro);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void generarExcel() {
    try {
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("TablaServiciosHasPlatos");

        // Crear encabezados de columna
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Código de Servicio");
        headerRow.createCell(1).setCellValue("Código de Plato");
        headerRow.createCell(2).setCellValue("Código de Servicio");

        int rowNum = 1;
        for (ServicioHasPlato shp : listaServicioHasPlato) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(shp.getServicios_codigoServicio());
            row.createCell(1).setCellValue(shp.getCodigoPlato());
            row.createCell(2).setCellValue(shp.getCodigoServicio());
        }

        // Autoajustar el ancho de las columnas
        for (int col = 0; col < 3; col++) {
            sheet.autoSizeColumn(col);
        }

        // Mostrar el diálogo de guardar
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar Archivo Excel");
        fileChooser.getExtensionFilters().add(new ExtensionFilter("Archivos Excel (*.xls)", "*.xls"));
        File archivo = fileChooser.showSaveDialog(null);

        if (archivo != null) {
            FileOutputStream fileOut = new FileOutputStream(archivo);
            workbook.write(fileOut);
            fileOut.close();
            System.out.println("Archivo Excel generado exitosamente.");
        } else {
            System.out.println("Generación de archivo Excel cancelada.");
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
}
    
    
    
    
    
    public void activarControles(){
        txtServicios_codigoServicio.setEditable(true);
        cmbCodigoPlato.setDisable(false);
        cmbCodigoServicio.setDisable(false);
    }
    
    public void desactivarControles(){
        txtServicios_codigoServicio.setDisable(false);
        cmbCodigoPlato.setDisable(true);
        cmbCodigoServicio.setDisable(true);
    }
    
    public void limpiarControles(){
        txtServicios_codigoServicio.clear();
        cmbCodigoPlato.setValue(null);
        cmbCodigoServicio.setValue(null);
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
