package org.alejandroflores.controller;

import eu.schudt.javafx.controls.calendar.DatePicker;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
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
import javafx.scene.layout.GridPane;
import javax.swing.JOptionPane;
import org.alejandroflores.bean.Empresa;
import org.alejandroflores.bean.Servicio;
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


public class ServicioController implements Initializable {
    
    private enum operaciones {NUEVO, GUARDAR, ELIMINAR, ACTUALIZAR, NINGUNO};
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private Principal escenarioPrincipal;
    private DatePicker fecha;
    private ObservableList <Servicio> listaServicio;
    private ObservableList <Empresa> listaEmpresa;
    private ObservableList <String> listaHora;
    private ObservableList <String> listaMinuto;
    @FXML private TextField txtCodigoServicio;
    @FXML private TextField txtTipoServicio;
    @FXML private TextField txtLugarServicio;
    @FXML private TextField txtTelefonoContacto;
    @FXML private TableView tblServicios;
    @FXML private TableColumn colCodigoServicio;
    @FXML private TableColumn colFechaServicio;
    @FXML private TableColumn colTipoServicio;
    @FXML private TableColumn colHoraServicio;
    @FXML private TableColumn colLugarServicio;
    @FXML private TableColumn colTelefonoContactoServicio;
    @FXML private TableColumn colCodigoEmpresaServicio;
    @FXML private ComboBox cmbCodigoEmpresa;
    @FXML private GridPane grpFecha;
    @FXML private ComboBox cmbHoras;
    @FXML private ComboBox cmbMinutos;
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
    
    @FXML private Button btnGenerarExcel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        fecha = new DatePicker(Locale.ENGLISH);
        fecha.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        fecha.getCalendarView().todayButtonTextProperty().set("Today");
        fecha.getCalendarView().setShowWeeks(false);
        grpFecha.add(fecha, 3, 0);
        fecha.getStylesheets().add("/org/alejandroflores/resource/TonysKinal.css");
        cmbCodigoEmpresa.setItems(getEmpresa());
        cmbHoras.setItems(getHora());
        cmbMinutos.setItems(getMinuto());
        desactivarControles();
        
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
        tblServicios.setItems(getServicio());
        colCodigoServicio.setCellValueFactory(new PropertyValueFactory<Servicio, Integer>("codigoServicio"));
        colFechaServicio.setCellValueFactory(new PropertyValueFactory<Servicio, java.sql.Date>("fechaServicio"));
        colTipoServicio.setCellValueFactory(new PropertyValueFactory<Servicio, String>("tipoServicio"));
        colHoraServicio.setCellValueFactory(new PropertyValueFactory<Servicio, java.sql.Time>("horaServicio"));
        colLugarServicio.setCellValueFactory(new PropertyValueFactory<Servicio, String>("lugarServicio"));
        colTelefonoContactoServicio.setCellValueFactory(new PropertyValueFactory<Servicio, String>("telefonoContacto"));
        colCodigoEmpresaServicio.setCellValueFactory(new PropertyValueFactory<Servicio, Integer>("codigoEmpresa"));
    }
    
    public void seleccionarElemento(){
        txtCodigoServicio.setText(String.valueOf(((Servicio)tblServicios.getSelectionModel().getSelectedItem()).getCodigoServicio()));
        fecha.setSelectedDate(((Servicio)tblServicios.getSelectionModel().getSelectedItem()).getFechaServicio());
        txtTipoServicio.setText(String.valueOf((((Servicio)tblServicios.getSelectionModel().getSelectedItem()).getTipoServicio())));
        cmbHoras.setValue(((Servicio)tblServicios.getSelectionModel().getSelectedItem()).getHora());
        cmbMinutos.setValue(((Servicio)tblServicios.getSelectionModel().getSelectedItem()).getMinuto());
        txtLugarServicio.setText(((Servicio)tblServicios.getSelectionModel().getSelectedItem()).getLugarServicio());
        txtTelefonoContacto.setText(((Servicio)tblServicios.getSelectionModel().getSelectedItem()).getTelefonoContacto());
        cmbCodigoEmpresa.getSelectionModel().select(buscarEmpresa(((Servicio)tblServicios.getSelectionModel().getSelectedItem()).getCodigoEmpresa()));
    }
    
    public Empresa buscarEmpresa(int codigoEmpresa){
        Empresa resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_BuscarEmpresa(?)");
            procedimiento.setInt(1, codigoEmpresa);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()){
                resultado = new Empresa(registro.getByte("codigoEmpresa"),
                                    registro.getString("nombreEmpresa"),
                                    registro.getString("direccion"),
                                    registro.getString("telefono"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return resultado;
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
    
    public ObservableList<Empresa> getEmpresa(){
        ArrayList<Empresa> lista = new ArrayList<Empresa>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_ListarEmpresas");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Empresa(resultado.getInt("codigoEmpresa"),   
                        resultado.getString("nombreEmpresa"),
                        resultado.getString("direccion"),
                        resultado.getString("telefono")));
                
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return listaEmpresa = FXCollections.observableArrayList(lista);
        
    }
    
    public ObservableList <String> getHora(){
        ArrayList <String> lista = new ArrayList<>();
        for(int a= 0; a<=23; a++){
            if(a<10){
                lista.add("0"+String.valueOf(a));
            }else{
                lista.add(String.valueOf(a));
            }
        }
        return listaHora = FXCollections.observableArrayList(lista);
    }
    
    public ObservableList <String> getMinuto(){
        ArrayList <String> lista = new ArrayList<>();
        for(int a = 0; a<59; a++){
            if(a<10){
                lista.add("0"+String.valueOf(a));
            }else{
                lista.add(String.valueOf(a));
            }
        }
        return listaMinuto = FXCollections.observableArrayList(lista);
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
                if(tblServicios.getSelectionModel().getSelectedItem() !=null){
                    int respuesta = JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar el registro?" + "¿Desea eliminar la llave foranea?", "Eliminar Servicio", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                    if(respuesta == JOptionPane.YES_OPTION){
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_EliminarServicio (?)");
                            procedimiento.setInt(1, ((Servicio)tblServicios.getSelectionModel().getSelectedItem()).getCodigoServicio());
                            procedimiento.execute();
                            listaServicio.remove(tblServicios.getSelectionModel().getSelectedItem());
                            limpiarControles();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Debe Seleccionar Un Elemento");
                }
        }
    }
    
    public void editar(){
        switch(tipoDeOperacion){
            case NINGUNO:
                if(tblServicios.getSelectionModel().getSelectedItem() != null){
                    btnNuevo.setDisable(true);
                    btnEliminar.setDisable(true);
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    imgEditar.setImage(new Image("/org/alejandroflores/image/actualizar.png"));
                    imgReporte.setImage(new Image("/org/alejandroflores/image/cancelar.png"));
                    activarControles();
                    tipoDeOperacion = operaciones.ACTUALIZAR;
                }else{
                    JOptionPane.showMessageDialog(null, "Debe Seleccionar Un Elemento");
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
    
    public void guardar(){
        Servicio registro = new Servicio();
        registro.setFechaServicio(fecha.getSelectedDate());
        registro.setTipoServicio(txtTipoServicio.getText());
        String hora = String.valueOf(cmbHoras.getSelectionModel().getSelectedItem());
        String minuto = String.valueOf(cmbMinutos.getSelectionModel().getSelectedItem());
        registro.setHoraServicio(new java.sql.Time(Integer.parseInt(hora), Integer.parseInt(minuto),0));
        registro.setLugarServicio(txtLugarServicio.getText());
        registro.setTelefonoContacto(txtTelefonoContacto.getText());
        registro.setCodigoEmpresa(((Empresa)cmbCodigoEmpresa.getSelectionModel().getSelectedItem()).getCodigoEmpresa());
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("Call sp_AgregarServicio(?, ?, ?, ?, ?, ?)");
            procedimiento.setDate(1, new java.sql.Date(registro.getFechaServicio().getTime()));
            procedimiento.setString(2, registro.getTipoServicio());
            procedimiento.setTime(3, registro.getHoraServicio());
            procedimiento.setString(4, registro.getLugarServicio());
            procedimiento.setString(5, registro.getTelefonoContacto());
            procedimiento.setInt(6, registro.getCodigoEmpresa());
            procedimiento.execute();
            listaServicio.add(registro);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void actualizar(){
        Servicio registro = new Servicio();
        registro.setCodigoServicio(Integer.parseInt(txtCodigoServicio.getText()));
        registro.setFechaServicio(new java.sql.Date(fecha.getSelectedDate().getTime()));
        registro.setTipoServicio(txtTipoServicio.getText());
        String hora = String.valueOf(cmbHoras.getSelectionModel().getSelectedItem());
        String minuto = String.valueOf(cmbMinutos.getSelectionModel().getSelectedItem());
        registro.setHoraServicio(new java.sql.Time(Integer.parseInt(hora), Integer.parseInt(minuto),0));
        registro.setLugarServicio(txtLugarServicio.getText());
        registro.setTelefonoContacto(txtTelefonoContacto.getText());
        registro.setCodigoEmpresa(((Empresa)cmbCodigoEmpresa.getSelectionModel().getSelectedItem()).getCodigoEmpresa());
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_EditarServicio(?,?,?,?,?,?,?)");
            procedimiento.setInt(1, registro.getCodigoServicio());
            procedimiento.setDate(2, new java.sql.Date(registro.getFechaServicio().getTime()));
            procedimiento.setString(3, registro.getTipoServicio());
            procedimiento.setTime(4, registro.getHoraServicio());
            procedimiento.setString(5, registro.getLugarServicio());
            procedimiento.setString(6, registro.getTelefonoContacto());
            procedimiento.setInt(7, registro.getCodigoEmpresa());
            procedimiento.execute();
        } catch (Exception e) {
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
                Sheet sheet = workbook.createSheet("TablaPlato");

                // Crear encabezados de columna
                Row headerRow = sheet.createRow(0);
                headerRow.createCell(0).setCellValue("Código Servicio");
                headerRow.createCell(1).setCellValue("Fecha Servicio");
                headerRow.createCell(2).setCellValue("Tipo Servicio");
                headerRow.createCell(3).setCellValue("Hora Servicio");
                headerRow.createCell(4).setCellValue("Lugar Servicio");
                headerRow.createCell(5).setCellValue("Teléfono Contacto");
                headerRow.createCell(6).setCellValue("Código Empresa");


                int rowNum = 1;
                for (Servicio servicio : listaServicio) {
                    Row row = sheet.createRow(rowNum++);
                    row.createCell(0).setCellValue(servicio.getCodigoServicio());
                    row.createCell(1).setCellValue(servicio.getFechaServicio());
                    row.createCell(2).setCellValue(servicio.getTipoServicio());
                    row.createCell(3).setCellValue(servicio.getHoraServicio());
                    row.createCell(4).setCellValue(servicio.getLugarServicio());
                    row.createCell(5).setCellValue(servicio.getTelefonoContacto());
                    row.createCell(6).setCellValue(servicio.getCodigoEmpresa());

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
    
    
    
    public void desactivarControles(){
        txtCodigoServicio.setEditable(false);
        fecha.setDisable(true);
        txtTipoServicio.setEditable(false);
        cmbHoras.setDisable(true);
        cmbMinutos.setDisable(true);
        txtLugarServicio.setEditable(false);
        txtTelefonoContacto.setEditable(false);
        cmbCodigoEmpresa.setDisable(true);
    }
    
    public void activarControles(){
        fecha.setDisable(false);
        txtTipoServicio.setEditable(true);
        cmbHoras.setDisable(false);
        cmbMinutos.setDisable(false);
        txtLugarServicio.setEditable(true);
        txtTelefonoContacto.setEditable(true);
        cmbCodigoEmpresa.setDisable(false);
    }
    
    public void limpiarControles(){
        txtCodigoServicio.clear();
        fecha.selectedDateProperty().setValue(null);
        txtTipoServicio.clear();
        cmbHoras.valueProperty().set(null);
        cmbMinutos.valueProperty().set(null);
        txtLugarServicio.clear();
        txtTelefonoContacto.clear();
        cmbCodigoEmpresa.valueProperty().set(null);
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
