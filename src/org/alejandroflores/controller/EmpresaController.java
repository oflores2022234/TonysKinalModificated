
package org.alejandroflores.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
import org.alejandroflores.bean.Empresa;
import org.alejandroflores.db.Conexion;
import org.alejandroflores.main.Principal;
import org.alejandroflores.report.GenerarReporte;
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
import org.alejandroflores.bean.Empleado;


public class EmpresaController implements Initializable{
    private enum operaciones{NUEVO, GUARDAR, ELIMINAR, ACTUALIZAR, CANCELAR, NINGUNO};
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private Principal escenarioPrincipal;
    private ObservableList<Empresa> listaEmpresa;
    private final String img1 = "/src/org/alejandroflores/image/Empresa.png";
    
    
    @FXML private TextField txtCodigoEmpresa;
    @FXML private TextField txtNombreEmpresa;
    @FXML private TextField txtDireccionEmpresa;
    @FXML private TextField txtTelefonoEmpresa;
    @FXML private TableView tblEmpresas;
    @FXML private TableColumn colCodigoEmpresa;
    @FXML private TableColumn colNombreEmpresa;
    @FXML private TableColumn colDireccionEmpresa;
    @FXML private TableColumn colTelefonoEmpresa;
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    @FXML private Button btnReporte1;
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
      addAnimation(btnReporte1);
        
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
       tblEmpresas.setItems(getEmpresa());
       colCodigoEmpresa.setCellValueFactory(new PropertyValueFactory<Empresa, Integer>("codigoEmpresa"));
       colNombreEmpresa.setCellValueFactory(new PropertyValueFactory<Empresa, String>("nombreEmpresa"));
       colDireccionEmpresa.setCellValueFactory(new PropertyValueFactory<Empresa, String>("direccion"));
       colTelefonoEmpresa.setCellValueFactory(new PropertyValueFactory<Empresa, String>("telefono"));
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
                    imgNuevo.setImage(new Image("org/alejandroflores/image/Nuevo.png"));
                    imgEliminar.setImage(new Image("org/alejandroflores/image/Eliminar.png"));
                    tipoDeOperacion = operaciones.NINGUNO;
                
                break;
                
            default:
                if(tblEmpresas.getSelectionModel().getSelectedItem() != null){
                    int respuesta = JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar el registro?" + "Desea eliminar la llave foranea","Eliminar Empresa",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                    if(respuesta == JOptionPane.YES_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_EliminarEmpresa (?)");
                            procedimiento.setInt(1, ((Empresa)tblEmpresas.getSelectionModel().getSelectedItem()).getCodigoEmpresa());
                            procedimiento.execute();
                            listaEmpresa.remove(tblEmpresas.getSelectionModel().getSelectedItem());
                            limpiarControles();
                        }catch(Exception e){
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
                    if(tblEmpresas.getSelectionModel().getSelectedItem() != null){
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
    
    public void actualizar(){
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_EditarEmpresa (?, ?, ?, ?)");
            Empresa registro = (Empresa)tblEmpresas.getSelectionModel().getSelectedItem();
            registro.setNombreEmpresa(txtNombreEmpresa.getText());
            registro.setDireccion(txtDireccionEmpresa.getText());
            registro.setTelefono(txtTelefonoEmpresa.getText());
            procedimiento.setInt(1, registro.getCodigoEmpresa());
            procedimiento.setString(2, registro.getNombreEmpresa());
            procedimiento.setString(3, registro.getDireccion());
            procedimiento.setString(4, registro.getTelefono());
            procedimiento.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void guardar(){
        Empresa registro = new Empresa();
//        registro.setCodigoEmpresa(Integer.parseInt(txtCodigoEmpresa.getText()));
        registro.setNombreEmpresa(txtNombreEmpresa.getText());
        registro.setDireccion(txtDireccionEmpresa.getText());
        registro.setTelefono(txtTelefonoEmpresa.getText());
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_AgregarEmpresa (?,?,?)");
            procedimiento.setString(1, registro.getNombreEmpresa());
            procedimiento.setString(2, registro.getDireccion());
            procedimiento.setString(3, registro.getTelefono());
            procedimiento.execute();
            listaEmpresa.add(registro);
        
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Éxito");
            alerta.setHeaderText(null);
            alerta.setContentText("Guardado exitoso");


            ImageView imageView = new ImageView(new Image("/org/alejandroflores/image/Empresa.png"));
            imageView.setFitWidth(100); // Establecer el ancho deseado para la imagen
            imageView.setFitHeight(100); // Establecer el alto deseado para la imagen


            alerta.setGraphic(imageView);


            DialogPane dialogPane = alerta.getDialogPane();
            dialogPane.getStylesheets().add(getClass().getResource("/org/alejandroflores/resource/TonysKinal.css").toExternalForm());
            dialogPane.getStyleClass().add("centered-dialog");

        alerta.showAndWait();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void seleccionarElemento(){
        txtCodigoEmpresa.setText(String.valueOf(((Empresa)tblEmpresas.getSelectionModel().getSelectedItem()).getCodigoEmpresa()));
        txtNombreEmpresa.setText(((Empresa)tblEmpresas.getSelectionModel().getSelectedItem()).getNombreEmpresa());
        txtDireccionEmpresa.setText(((Empresa)tblEmpresas.getSelectionModel().getSelectedItem()).getDireccion());
        txtTelefonoEmpresa.setText(((Empresa)tblEmpresas.getSelectionModel().getSelectedItem()).getTelefono());
    }
    
    public void generarReporte(){
        switch(tipoDeOperacion){
            case NINGUNO:
                imprimirReporte();
                break;
            case ACTUALIZAR:
                reporte();
                break;
        }
    }
    
    public void imprimirReporte(){
        Map parametros = new HashMap();
        parametros.put("codigoEmpresa", null);
        parametros.put("img1", this.getClass().getResourceAsStream(img1));
        GenerarReporte.mostrarReporte("ReporteEmpresas.jasper", "Reporte de Empresas", parametros);
    }
    
    
    
    
    public void imprimirReportePedro() {
        if (tblEmpresas.getSelectionModel().isEmpty()) {
           
            return;
        }

        Empresa empresaSeleccionada = (Empresa)tblEmpresas.getSelectionModel().getSelectedItem();
        int codEmpresa = empresaSeleccionada.getCodigoEmpresa();

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("codEmpresa", codEmpresa);
        parametros.put("img1", this.getClass().getResourceAsStream(img1));
        GenerarReporte.mostrarReporte("ReporteArmas.jasper", "Reporte de Pedro Armas", parametros);
    }
    
    
    public void generarExcel() {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Guardar Archivo Excel");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos Excel", "*.xls"));
            File file = fileChooser.showSaveDialog(null);

            if (file != null) {
                Workbook workbook = new HSSFWorkbook();
                Sheet sheet = workbook.createSheet("TablaEmpresa");

                // Crear encabezados de columna
                Row headerRow = sheet.createRow(0);
                headerRow.createCell(0).setCellValue("Código Empresa");
                headerRow.createCell(1).setCellValue("Nombre Empresa");
                headerRow.createCell(2).setCellValue("Dirección");
                headerRow.createCell(3).setCellValue("Teléfono");


                int rowNum = 1;
                for (Empresa empresa : listaEmpresa) {
                    Row row = sheet.createRow(rowNum++);
                    row.createCell(0).setCellValue(empresa.getCodigoEmpresa());
                    row.createCell(1).setCellValue(empresa.getNombreEmpresa());
                    row.createCell(2).setCellValue(empresa.getDireccion());
                    row.createCell(3).setCellValue(empresa.getTelefono());

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
        txtCodigoEmpresa.setEditable(false);
        txtNombreEmpresa.setEditable(false);
        txtDireccionEmpresa.setEditable(false);
        txtTelefonoEmpresa.setEditable(false);
    }
    
    public void activarControles(){
        txtCodigoEmpresa.setEditable(false);
        txtNombreEmpresa.setEditable(true);
        txtDireccionEmpresa.setEditable(true);
        txtTelefonoEmpresa.setEditable(true);
    }
    
    public void limpiarControles(){
        txtCodigoEmpresa.setText("");
        txtNombreEmpresa.setText("");
        txtDireccionEmpresa.setText("");
        txtTelefonoEmpresa.setText("");  
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
    
    public void ventanaPresupuesto(){
        escenarioPrincipal.ventanaPresupuesto();
    }
    
    
}
