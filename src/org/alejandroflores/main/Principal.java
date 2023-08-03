/*
Nombre: Oscar Alejandro Flores Yllescas
Código Técnico: IN5BV
Carné: 2022234
Fecha de Creación: 14-04-2023 13:50
Fecha de Modificación 1: 14-04-2023
Fecha de Modificación 2: 20-04-2023
Fecha de Modificación 3: 21-04-2023
Fecha de Modificación 4: 25-04-2023
Fecha de Modificación 5: 26-04-2023
Fecha de Modificación 6: 27-04-2023
Fecha de Modificación 7: 01-05-2023
Fecha de Modificación 8: 02-05-2023
Fecha de Modificación 8: 10-05-2023
Fecha de Modificación 8: 11-05-2023
Fecha de Modificación 8: 12-05-2023
Fecha de Modificación 8: 13-05-2023
Fecha de Modificación 8: 20-05-2023
Fecha de Modificación 8: 23-05-2023
Fecha de Modificación 8: 28-05-2023
Fecha de Modificación 8: 30-05-2023
Fecha de Modificación 8: 31-05-2023
Fecha de Modificación 8: 04-06-2023
Fecha de Modificación 8: 05-06-2023
Fecha de Modificación 8: 06-06-2023
Fecha de Modificación 8: 07-06-2023
Fecha de Modificación 8: 08-06-2023
Fecha de Modificación 8: 09-06-2023


 */ 
package org.alejandroflores.main;

import java.io.IOException;
import java.io.InputStream;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;

import javafx.stage.Stage;
import org.alejandroflores.controller.EmpleadoController;
import org.alejandroflores.controller.EmpresaController;
import org.alejandroflores.controller.LoginController;
import org.alejandroflores.controller.MenuPrincipalController;
import org.alejandroflores.controller.PlatoController;
import org.alejandroflores.controller.PresupuestoController;
import org.alejandroflores.controller.ProductoController;
import org.alejandroflores.controller.ProductoHasPlatoController;
import org.alejandroflores.controller.ProgramadorController;
import org.alejandroflores.controller.ServicioController;
import org.alejandroflores.controller.ServicioHaPlatoController;
import org.alejandroflores.controller.ServicioHasEmpleadoController;
import org.alejandroflores.controller.TipoEmpleadoController;
import org.alejandroflores.controller.TipoPlatoController;
import org.alejandroflores.controller.UsuarioController;

/**
 *
 * @author informatica
 */
public class Principal extends Application {
    private final String PAQUETE_VISTA = "/org/alejandroflores/view/";
    private Stage escenarioPrincipal;
    private Scene escena;
    
    @Override
    public void start(Stage escenarioPrincipal) throws Exception {
        this.escenarioPrincipal = escenarioPrincipal;
        this.escenarioPrincipal.setTitle("Tony's Kinal 2023");
        escenarioPrincipal.getIcons().add(new Image("/org/alejandroflores/image/logo2.jpg"));
////        Parent root = FXMLLoader.load(getClass().getResource("/org/alejandroflores/view/MenuPrincipalView.fxml"));
////        Parent root = FXMLLoader.load(getClass().getResource("/org/alejandroflores/view/ProgramadorView.fxml"));
////        Parent root = FXMLLoader.load(getClass().getResource("/org/alejandroflores/view/EmpresaView.fxml"));
////        Scene escena = new Scene (root);
////        escenarioPrincipal.setScene(escena);
        login();
        //menuPrincipal();
        escenarioPrincipal.show();
    }
    
    public void menuPrincipal(){
        
        try{
            MenuPrincipalController menu = (MenuPrincipalController)cambiarEscena("MenuPrincipalView.fxml",650,650);
            menu.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    
    }
    
    public void ventanaProgramador(){
        try{
            ProgramadorController programador = (ProgramadorController)cambiarEscena("ProgramadorView.fxml",1094,600);
            programador.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaEmpresa(){
        try{
            EmpresaController empresa = (EmpresaController)cambiarEscena("EmpresaView.fxml",1094,600);
            empresa.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaEmpleado(){
        try{
            EmpleadoController empleado = (EmpleadoController)cambiarEscena("EmpleadoView.fxml",1379,700);
            empleado.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaTipoEmpleado(){
        try{
            TipoEmpleadoController tipoEmpleado = (TipoEmpleadoController)cambiarEscena("TipoEmpleadoView.fxml",1094,600);
            tipoEmpleado.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaTipoPlato(){
        try{
            TipoPlatoController tipoPlato = (TipoPlatoController)cambiarEscena("TipoPlatoView.fxml",1094,600);
            tipoPlato.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaProducto(){
        try{
            ProductoController producto = (ProductoController)cambiarEscena("ProductoView.fxml",1094,600);
            producto.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaPresupuesto(){
        try {
            PresupuestoController presupuestoController = (PresupuestoController)cambiarEscena("PresupuestoView.fxml",1094,600);
            presupuestoController.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void ventanaPlato(){
        try {
            PlatoController platoController = (PlatoController)cambiarEscena("PlatoView.fxml",1094,600);
            platoController.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void ventanaProHasPla(){
        try {
            ProductoHasPlatoController proHasPla = (ProductoHasPlatoController)cambiarEscena("ProductoHasPlatoView.fxml",1094,600);
            proHasPla.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void ventanaSerHasPla(){
        try {
            ServicioHaPlatoController serHasPla = (ServicioHaPlatoController)cambiarEscena("ServicioHasPlatoView.fxml",1094,600);
            serHasPla.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void ventanaSerHasEmp(){
        try {
            ServicioHasEmpleadoController serHasEmp = (ServicioHasEmpleadoController)cambiarEscena("ServicioHasEmpleadoView.fxml",1094,600);
            serHasEmp.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void ventanaServicio(){
        try {
            ServicioController servController = (ServicioController) cambiarEscena("ServicioView.fxml",1094,600);
            servController.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void login(){
        try {
            LoginController login = (LoginController) cambiarEscena("LoginView.fxml",800,500);
            login.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void ventanaUsuario(){
        try {
            UsuarioController usuario = (UsuarioController) cambiarEscena("UsuarioView.fxml",1094 ,600);
            usuario.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    } 
    
    public Initializable cambiarEscena(String fxml, int ancho, int alto) throws Exception{
        Initializable resultado = null;
        FXMLLoader cargadorFXML = new FXMLLoader();
        InputStream archivo = Principal.class.getResourceAsStream(PAQUETE_VISTA+fxml);
        cargadorFXML.setBuilderFactory(new JavaFXBuilderFactory());
        cargadorFXML.setLocation(Principal.class.getResource(PAQUETE_VISTA+fxml));
        escena = new Scene((AnchorPane)cargadorFXML.load(archivo),ancho,alto);
        escenarioPrincipal.setScene(escena);
        escenarioPrincipal.sizeToScene();
        resultado = (Initializable)cargadorFXML.getController();
        return resultado;
    }
    
    
}
