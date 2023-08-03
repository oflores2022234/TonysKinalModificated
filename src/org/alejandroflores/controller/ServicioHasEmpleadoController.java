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
import javafx.scene.DepthTest;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import org.alejandroflores.bean.Empleado;
import org.alejandroflores.bean.Servicio;
import org.alejandroflores.bean.ServicioHasEmpleado;
import org.alejandroflores.db.Conexion;
import org.alejandroflores.main.Principal;

public class ServicioHasEmpleadoController implements Initializable{

    private enum operaciones {NUEVO, GUARDAR, ELIMINAR, ACTUALIZAR, NINGUNO};
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private Principal escenarioPrincipal;
    private DatePicker fecha;
    private ObservableList <ServicioHasEmpleado> listaServicioHasEmpleado;
    private ObservableList <Servicio> listaServicio;
    private ObservableList <Empleado> listaEmpleado;
    private ObservableList <String> listaHora;
    private ObservableList <String> listaMinuto;
    @FXML private TextField txtServicios_codigoServicio;
    @FXML private TextField txtLugarEvento;
    @FXML private TableView tblServicios_has_Empleados;
    @FXML private TableColumn colServicios_codigoServicio;
    @FXML private TableColumn colCodigoServicio;
    @FXML private TableColumn colCodigoEmpleado;
    @FXML private TableColumn colFechaEvento;
    @FXML private TableColumn colHoraEvento;
    @FXML private TableColumn colLugarEvento;
    @FXML private ComboBox cmbCodigoServicio;
    @FXML private ComboBox cmbCodigoEmpleado;
    @FXML private GridPane grpFecha;
    @FXML private ComboBox cmbHoras;
    @FXML private ComboBox cmbMinutos;
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private ImageView imgNuevo;
    @FXML private ImageView imgEliminar;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        fecha = new DatePicker(Locale.ENGLISH);
        fecha.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        fecha.getCalendarView().todayButtonTextProperty().set("Today");
        fecha.getCalendarView().setShowWeeks(false);
        grpFecha.add(fecha, 3, 0);
        fecha.getStylesheets().add("/org/alejandroflores/resource/TonysKinal.css");
        cmbCodigoServicio.setItems(getServicio());
        cmbCodigoEmpleado.setItems(getEmpleado());
        cmbHoras.setItems(getHora());
        cmbMinutos.setItems(getMinuto());
        desactivarControles();
    }
    
    public void cargarDatos(){
        tblServicios_has_Empleados.setItems(getServicioHasEmpleado());
        colServicios_codigoServicio.setCellValueFactory(new PropertyValueFactory<ServicioHasEmpleado, Integer>("Servicios_codigoServicio"));
        colCodigoServicio.setCellValueFactory(new PropertyValueFactory<ServicioHasEmpleado, Integer>("codigoServicio"));
        colCodigoEmpleado.setCellValueFactory(new PropertyValueFactory<ServicioHasEmpleado, Integer>("codigoEmpleado"));
        colFechaEvento.setCellValueFactory(new PropertyValueFactory<ServicioHasEmpleado, java.sql.Date>("fechaEvento"));
        colHoraEvento.setCellValueFactory(new PropertyValueFactory<ServicioHasEmpleado, java.sql.Time>("horaEvento"));
        colLugarEvento.setCellValueFactory(new PropertyValueFactory<ServicioHasEmpleado, String>("lugarEvento"));
    }
    
    public void seleccionarElemento(){
        txtServicios_codigoServicio.setText(String.valueOf(((ServicioHasEmpleado)tblServicios_has_Empleados.getSelectionModel().getSelectedItem()).getServicios_codigoServicio()));
        cmbCodigoServicio.getSelectionModel().select(buscarServicio(((ServicioHasEmpleado)tblServicios_has_Empleados.getSelectionModel().getSelectedItem()).getCodigoServicio()));
        cmbCodigoEmpleado.getSelectionModel().select(buscarEmpleado(((ServicioHasEmpleado)tblServicios_has_Empleados.getSelectionModel().getSelectedItem()).getCodigoEmpleado()));
        fecha.setSelectedDate(((ServicioHasEmpleado)tblServicios_has_Empleados.getSelectionModel().getSelectedItem()).getFechaEvento());
        cmbHoras.setValue(((ServicioHasEmpleado)tblServicios_has_Empleados.getSelectionModel().getSelectedItem()).getHora());
        cmbMinutos.setValue(((Servicio)tblServicios_has_Empleados.getSelectionModel().getSelectedItem()).getMinuto());
        txtLugarEvento.setText(String.valueOf(((ServicioHasEmpleado)tblServicios_has_Empleados.getSelectionModel().getSelectedItem()).getLugarEvento()));
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
    
    public Empleado buscarEmpleado(int codigoEmpleado){
        Empleado resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{Call sp_BuscarEmpleado(?)}");
            procedimiento.setInt(1, codigoEmpleado);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()){
                resultado = new Empleado(registro.getByte("codigoEmpleado"),
                        registro.getByte("numeroEmpleado"),
                        registro.getString("apellidosEmpleado"),
                        registro.getString("nombresEmpleado"),
                        registro.getString("direccionEmpleado"),
                        registro.getString("telefonoContacto"),
                        registro.getString("gradoCocinero"),
                        registro.getByte("codigoTipoEmpleado"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }
    
    public ObservableList <ServicioHasEmpleado> getServicioHasEmpleado(){
        ArrayList <ServicioHasEmpleado> lista = new ArrayList();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{Call sp_ListarServicios_has_Empleados()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new ServicioHasEmpleado(resultado.getInt("Servicios_codigoServicio"),
                        resultado.getInt("codigoServicio"),
                        resultado.getInt("codigoEmpleado"),
                        resultado.getDate("fechaEvento"),
                        resultado.getTime("horaEvento"),
                        resultado.getString("lugarEvento")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaServicioHasEmpleado = FXCollections.observableArrayList(lista);
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
    
    public ObservableList<Empleado> getEmpleado(){
        ArrayList<Empleado> lista = new ArrayList<Empleado>();
            try {
                PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_ListarEmpleados");
                ResultSet resultado = procedimiento.executeQuery();
                while(resultado.next()){
                    lista.add(new Empleado(resultado.getInt("codigoEmpleado"),
                                resultado.getInt("numeroEmpleado"),
                                resultado.getString("apellidosEmpleado"),
                                resultado.getString("nombresEmpleado"),
                                resultado.getString("direccionEmpleado"),
                                resultado.getString("telefonoContacto"),
                                resultado.getString("gradoCocinero"),
                                resultado.getInt("codigoTipoEmpleado")));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return listaEmpleado = FXCollections.observableArrayList(lista);
    }
    
    public ObservableList <String> getHora(){
        ArrayList <String> lista = new ArrayList<>();
        for(int a= 0; a<23; a++){
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
                    imgNuevo.setImage(new Image("/org/alejandroflores/image/guardar.png"));
                    imgEliminar.setImage(new Image("/org/alejandroflores/image/cancelar.png"));
                    tipoDeOperacion = operaciones.GUARDAR;
                break;
            case GUARDAR:
                    guardar();
                    limpiarControles();
                    desactivarControles();
                    btnNuevo.setText("Nuevo");
                    btnEliminar.setText(" ");
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
                    btnEliminar.setText(" ");
                    imgNuevo.setImage(new Image("/org/alejandroflores/image/Nuevo.png"));
                    imgEliminar.setImage(null);
                    tipoDeOperacion = operaciones.NINGUNO;
                break;
        }
    }
    
    public void guardar(){
        ServicioHasEmpleado registro = new ServicioHasEmpleado();
        registro.setServicios_codigoServicio(Integer.parseInt(txtServicios_codigoServicio.getText()));
        registro.setCodigoServicio(((Servicio)cmbCodigoServicio.getSelectionModel().getSelectedItem()).getCodigoServicio());
        registro.setCodigoEmpleado(((Empleado)cmbCodigoEmpleado.getSelectionModel().getSelectedItem()).getCodigoEmpleado());
        registro.setFechaEvento(fecha.getSelectedDate());
        String hora = String.valueOf(cmbHoras.getSelectionModel().getSelectedItem());
        String minuto = String.valueOf(cmbMinutos.getSelectionModel().getSelectedItem());
        registro.setHoraEvento(new java.sql.Time(Integer.parseInt(hora), Integer.parseInt(minuto),0));
        registro.setLugarEvento(txtLugarEvento.getText());
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarServicioHasEmpleado(? , ?, ?, ?, ?, ?)}");
            procedimiento.setInt(1, registro.getServicios_codigoServicio());
            procedimiento.setInt(2, registro.getCodigoServicio());
            procedimiento.setInt(3, registro.getCodigoEmpleado());
            procedimiento.setDate(4, new java.sql.Date(registro.getFechaEvento().getTime()));
            procedimiento.setTime(5, registro.getHoraEvento());
            procedimiento.setString(6, registro.getLugarEvento());
            procedimiento.execute();
            listaServicioHasEmpleado.add(registro);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void desactivarControles(){
        txtServicios_codigoServicio.setEditable(false);
        cmbCodigoServicio.setDisable(true);
        cmbCodigoEmpleado.setDisable(true);
        fecha.setDisable(true);
        cmbHoras.setDisable(true);
        cmbMinutos.setDisable(true);
        txtLugarEvento.setEditable(false);
    }
    public void activarControles(){
        txtServicios_codigoServicio.setEditable(true);
        cmbCodigoServicio.setDisable(false);
        cmbCodigoEmpleado.setDisable(false);
        fecha.setDisable(false);
        cmbHoras.setDisable(false);
        cmbMinutos.setDisable(false);
        txtLugarEvento.setEditable(true);
        
    }
    public void limpiarControles(){
        txtServicios_codigoServicio.clear();
        cmbCodigoServicio.setValue(null);
        cmbCodigoEmpleado.setValue(null);
        fecha.selectedDateProperty().setValue(null);
        cmbHoras.setValue(null);
        cmbMinutos.setValue(null);
        txtLugarEvento.clear();
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
