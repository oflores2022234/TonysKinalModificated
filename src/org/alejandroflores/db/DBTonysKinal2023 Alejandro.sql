/*
Nombre: Oscar Alejandro Flores Yllescas
Código Técnico: IN5BV
Carné: 2022234
Fecha de Creación: 30-03-2023 16:55
Fecha de Modificación 1:04/04/2023 10:30 AM
Fecha de Modificación 2:10/04/2023
Fecha de Modificación 3:15/04/2023
Fecha de Modificación 4:19/04/2023
*/
Drop database if exists DBTonysKinal2023;
Create database DBTonysKinal2023;

Use DBTonysKinal2023;

Create table Empresas(
	codigoEmpresa int auto_increment not null,
    nombreEmpresa varchar(150) not null,
    direccion varchar(150) not null,
    telefono varchar(10) not null,
    primary key PK_codigoEmpresa(codigoEmpresa) 
);

Create table TipoEmpleado(
	codigoTipoEmpleado int not null auto_increment,
    descripcion varchar(50) not null,
    primary key PK_codigoTipoEmpleado (codigoTipoEmpleado)
);
	 
Create table Empleados(
	codigoEmpleado int auto_increment not null,
    numeroEmpleado int not null,
    apellidosEmpleado varchar(150) not null,
    nombresEmpleado varchar(150) not null,
    direccionEmpleado varchar(150) not null,
    telefonoContacto varchar(10),
    gradoCocinero varchar(50) not null,
    codigoTipoEmpleado int not null,
    primary key PK_codigoEmpleado (codigoEmpleado),
    constraint FK_Empleados_TipoEmpleado foreign key
		(codigoTipoEmpleado) references TipoEmpleado(codigoTipoEmpleado) ON DELETE CASCADE
);

Create table TipoPlato(
	codigoTipoPlato int auto_increment not null,
    descripcionTipo varchar(100) not null,
    primary key PK_codigoTipoPlato (codigoTipoPlato)
);

Create table Productos(
	codigoProducto int not null,
    nombreProducto varchar(150) not null,
    cantidad int not null,
    primary key PK_codigoProducto (codigoProducto)
);

Create table Servicios(
	codigoServicio int auto_increment not null,
    fechaServicio date not null,
    tipoServicio varchar(150) not null,
    horaServicio time not null,
    lugarServicio varchar(150) not null,
    telefonoContacto varchar(10) not null,
    codigoEmpresa int not null,
    primary key PK_codigoServicio (codigoServicio),
    constraint FK_Servicios_Empresas foreign key (codigoEmpresa)
		references Empresas (codigoEmpresa)ON DELETE CASCADE
);

Create table Presupuestos(
	codigoPresupuesto int auto_increment not null,
    fechaSolicitud date not null,
    cantidadPresupuesto decimal(10,2) not null,
    codigoEmpresa int not null,
    primary key PK_codigoPresupuesto (codigoPresupuesto),
    constraint FK_Presupuestos_Empresas foreign key (codigoEmpresa) 
		references Empresas (codigoEmpresa) ON DELETE CASCADE
);

Create table Platos(
	codigoPlato int auto_increment not null,
    cantidad int not null,
    nombrePlato varchar(50) not null,
    descripcionPlato varchar(150) not null,
    precioPlato decimal(10,2) not null,
    codigoTipoPlato int not null,
    -- tipoPlato_codigoTipoPlato int not null,
    primary key PK_codigoPlato (codigoPlato),
    constraint FK_Platos_TipoPlato1 foreign key (codigoTipoPlato)
		references TipoPlato(codigoTipoPlato)ON DELETE CASCADE
);

Create table Productos_has_Platos(
	Productos_codigoProducto int not null,
    codigoPlato int not null,
    codigoProducto int not null,
    primary key PK_Productos_codigoProducto (Productos_codigoProducto),
    constraint FK_Productos_has_Platos_Productos1 foreign key (codigoProducto)
		references Productos(codigoProducto) ON DELETE CASCADE,
    constraint FK_Productos_has_Platos_Platos1 foreign key (codigoPlato)
		references Platos(codigoPlato) ON DELETE CASCADE
);

Create table Servicios_has_Platos(
	Servicios_codigoServicio int not null,
    codigoPlato int not null,
    codigoServicio int not null,
    primary key PK_Servicios_codigoServicio (Servicios_codigoServicio),
    constraint FK_Servicios_has_Platos_Servicios1 foreign key (codigoServicio)
		references Servicios(codigoServicio) ON DELETE CASCADE,
	constraint FK_Servicios_has_Platos_Platos1 foreign key (codigoPlato)
		references Platos(codigoPlato) ON DELETE CASCADE
);

Create table Servicios_has_Empleados(
	Servicios_codigoServicio int not null,
    codigoServicio int not null,
    codigoEmpleado int not null,
    fechaEvento date not null,
    horaEvento time not null,
    lugarEvento varchar(150) not null,
    primary key PK_Servicios_codigoServicio (Servicios_codigoServicio),
    constraint FK_Servicios_has_Empleados_Servicios1 foreign key (codigoServicio)
		references Servicios(codigoServicio) ON DELETE CASCADE,
	constraint FK_Servicios_has_Empleados_Empleados1 foreign key (codigoEmpleado)
		references Empleados(codigoEmpleado) ON DELETE CASCADE
);

-- Use DBRecuperacion ;

Create table Usuario(
	codigoUsuario int not null auto_increment,
    nombreUsuario varchar(100) not null,
    apellidoUsuario varchar(100) not null,
    usuarioLogin varchar(50) not null,
    contrasena varchar(50) not null,
    primary key Pk_codigoUsuario (codigoUsuario)
);

Create table Login(
	usuarioMaster varchar(50) not null,
    passwordLogin varchar(50) not null,
    primary key PK_usuarioMaster (usuarioMaster)
)

-- Use DBRecuperacion ;

-- --------- Procedimintos Almacenados Entidad Usuario -------------

Delimiter $$
	Create procedure sp_AgregarUsuario(in nombreUsuario varchar(100), in apellidoUsuario varchar(100),
		in usuarioLogin varchar(50), in contrasena varchar(50))
	Begin
		Insert into Usuario(nombreUsuario, apellidoUsuario, usuarioLogin, contrasena)
			values(nombreUsuario, apellidoUsuario, usuarioLogin,contrasena);
    End$$
Delimiter ;

Delimiter $$
	Create procedure sp_ListarUsuarios()
		Begin
			Select
            U.codigoUsuario,
            U.nombreUsuario,
            U.apellidoUsuario,
            U.usuarioLogin,
            U.contrasena
            from Usuario U;
        End$$
Delimiter ;


call sp_AgregarUsuario('Alejandro', 'Flores', 'oflores','1236');
call sp_ListarUsuarios();




-- ---------- Procedimientos Almacenados Entidad Empresas -----

-- Agregar Empresa ---

Delimiter $$
	Create procedure sp_AgregarEmpresa (in nombreEmpresa varchar(150),in direccion varchar(150), in telefono varchar(10))
    Begin
		insert into Empresas(nombreEmpresa, direccion, telefono)
			values (nombreEmpresa, direccion, telefono);
    End$$
Delimiter ;

Call sp_AgregarEmpresa ('CAMPERO','Guatemala Ciudad','12345678');
Call sp_AgregarEmpresa ('Pizza Hut','Mixco','1734');
Call sp_AgregarEmpresa ('La Estancia','Antigua Guatemala','11223344');
Call sp_AgregarEmpresa ('Lai Lai','San Lucas Sacatepéquez','1122');
Call sp_AgregarEmpresa ('Kabuki','Antigua Guatemala','1010');

-- ----------- Listar Empresa -----------------
describe Empresas;
Delimiter $$
	Create procedure sp_ListarEmpresas ()
    Begin
		Select E.codigoEmpresa,
			E.nombreEmpresa,
            E.direccion,
            E.telefono
            from Empresas E;
    End$$
Delimiter ;

Call sp_ListarEmpresas();

-- ----------- Buscar Empresa ------------

Delimiter $$
	Create procedure sp_BuscarEmpresa(in codEmpre int)
    Begin
		Select E.codigoEmpresa,
			E.nombreEmpresa,
            E.direccion,
            E.telefono
            from Empresas E where E.codigoEmpresa = codEmpre;
    End$$
Delimiter ;

call sp_BuscarEmpresa(1);

-- --------------- Elimitar Empresa ------------

Delimiter $$
	Create procedure sp_EliminarEmpresa(in codEmpre int)
    Begin
		Delete from Empresas
			where codigoEmpresa = codEmpre;
    End$$
Delimiter ;



-- ----------------- Editar Empresa ------------------------

Delimiter $$
	Create procedure sp_EditarEmpresa(in codEmpresa int, in nomEmpresa varchar(150), in dire varchar(150), in tel varchar(10))
    Begin
		Update Empresas E
			Set E.nombreEmpresa = nomEmpresa,
            E.direccion = dire,
            E.telefono = tel
            where E.codigoEmpresa = codEmpresa;
    End$$
Delimiter ;

call sp_EditarEmpresa (1,'Campero','Ciudad De Guatemala','1777');


-- ------------------ Procedimientos Almacenados Entidad Tipo Empleado ------------------------------------

-- ----------- Agregar TipoEmpleado -----------

Delimiter $$
	Create procedure sp_AgregarTipoEmpleado(in descripcion varchar(50))
		Begin 
			Insert into TipoEmpleado(descripcion)
				values(descripcion);
        End$$
Delimiter ;

Call sp_AgregarTipoEmpleado('Chef');
Call sp_AgregarTipoEmpleado('Chef Ejecutivo');
Call sp_AgregarTipoEmpleado('Chef de Grill');
Call sp_AgregarTipoEmpleado('Chef deComida Internacional');
Call sp_AgregarTipoEmpleado('Chef de Cocina Rápida');


-- ----------- Listar Tipo Empleados ------------

Delimiter $$
	Create procedure sp_ListarTipoEmpleados()
		Begin
			Select
            Te.codigoTipoEmpleado,
            Te.descripcion
            from TipoEmpleado Te;
        End$$
Delimiter ;

Call sp_ListarTipoEmpleados();
-- ----------- Buscar Tipo Empleado ----------------

Delimiter $$
	Create procedure sp_BuscarTipoEmpleado(in idTipoEmpleado int)
		Begin
			Select
            Te.codigoTipoEmpleado,
            Te.descripcion
            from TipoEmpleado Te where Te.codigoTipoEmpleado = idTipoEmpleado;
        End$$
Delimiter ;

Call sp_BuscarTipoEmpleado(2)

-- -------------- Eliminar Tipo Empleado -----------------

Delimiter $$
	Create procedure sp_EliminarTipoEmpleado(in idTipoEmpleado int)
		Begin
			Delete from TipoEmpleado
				where codigoTipoEmpleado = idTipoEmpleado;
        End$$
Delimiter ;

-- ---------- Editar Tipo Empleado ------------

Delimiter $$
	Create procedure sp_EditarTipoEmpleado(in idTipoEmpleado int, in descrip varchar(50))
		Begin
			Update TipoEmpleado Te
				Set Te.descripcion = descrip
                where Te.codigoTipoEmpleado = idTipoEmpleado;
        End$$
Delimiter ;

Call sp_EditarTipoEmpleado(1,'Jefe De Cocina')

-- ------------ Procedimientos Almacenados Entidad Empleados -------------

-- -----Agregar Empleado -----------

Delimiter $$
	Create procedure sp_AgregarEmpleado(in numeroEmpleado int, in apellidosEmpleado varchar(150), in nombresEmpleado varchar(150),
										in direccionEmpleado varchar(150), in telefonoContacto varchar(10), in gradoCocinero varchar(50), in codigoTipoEmpleado int)
		Begin
			Insert into Empleados(numeroEmpleado, apellidosEmpleado, nombresEmpleado, direccionEmpleado, telefonoContacto, gradoCocinero, codigoTipoEmpleado)
            values (numeroEmpleado, apellidosEmpleado,nombresEmpleado,direccionEmpleado, telefonoContacto, gradoCocinero,codigoTipoEmpleado);
        End$$
Delimiter ;

Call sp_AgregarEmpleado(1,'Flores Yllescas','Oscar Alejandro','Antigua Guatemala','42363512','Superior',1);
Call sp_AgregarEmpleado(2,'Veliz Mazariegos','Ariana Maritza','Antigua Guatemala','12345678','Medio',2);
Call sp_AgregarEmpleado(3,'Gonzalez','José Plablo','Antigua Guatemala','12345678','Medio',3);
Call sp_AgregarEmpleado(4,'Chanquín Mac','José Rodrigo',' Ciudad de Guatemala, Zona 18','14151614','Principiante',4);
Call sp_AgregarEmpleado(5,'Ambrocio Alvarado','Josue Sebastian',' Ciudad San Cristobal','14151614','Principiante',5);

-- ----------- Listar Empleados ------

Delimiter $$
	Create procedure sp_ListarEmpleados()
		Begin
			Select Em.codigoEmpleado,
            Em.numeroEmpleado,
            Em.apellidosEmpleado,
            Em.nombresEmpleado,
            Em.direccionEmpleado,
            Em.telefonoContacto,
            Em.gradoCocinero,
            Em.codigoTipoEmpleado
            from Empleados Em;
        End$$
Delimiter ;

Call sp_ListarEmpleados();

-- ------- Buscar Empleado -----------

Delimiter $$
	Create procedure sp_BuscarEmpleado(in idEmpleado int)
		Begin
			Select Em.codigoEmpleado,
            Em.numeroEmpleado,
            Em.apellidosEmpleado,
            Em.nombresEmpleado,
            Em.direccionEmpleado,
            Em.telefonoContacto,
            Em.gradoCocinero,
            Em.codigoTipoEmpleado
            from Empleados Em where Em.codigoEmpleado = idEmpleado;
        End$$
Delimiter ;

Call sp_BuscarEmpleado(1);

-- ---- Eliminar Empleado -------

Delimiter $$
	Create procedure sp_EliminarEmpleado(in idEmpleado int)
    Begin
		Delete from Empleados 
			where codigoEmpleado = idEmpleado;
    End$$
Delimiter ;

-- ------------- Editar Empleado --------------

Delimiter $$
	Create procedure sp_EditarEmpleado(in idEmpleado int, in numEmpleado int, in apeEmpleado varchar(150), in nomEmpleado varchar(150), in direEmpleado varchar(150),
										in telContacto varchar(10),in graCocinero varchar(50), in idTipoEmpleado int)
		Begin
			Update Empleados Em
				Set 
                Em.numeroEmpleado = numEmpleado,
                Em.apellidosEmpleado = apeEmpleado,
                Em.nombresEmpleado = nomEmpleado,
				Em.direccionEmpleado = direEmpleado,
                Em.telefonoContacto = telContacto,
                Em.gradoCocinero = graCocinero,
                Em.codigoTipoEmpleado = idTipoEmpleado
                where Em.codigoEmpleado = idEmpleado;
        End$$
Delimiter ;

	
Call sp_ListarEmpleados();


-- --------- Procedimientos Almacenados Entidad Tipo Plato ----------------

-- -----  Agregar TipoPlato --------

Delimiter $$
	Create procedure sp_AgregarTipoPlato(in descripcionTipo varchar(100))
    Begin
		Insert into TipoPlato(descripcionTipo)
        values(descripcionTipo);
    End$$
Delimiter ;

Call sp_AgregarTipoPlato('Espan	ola');
Call sp_AgregarTipoPlato('Italiana');
Call sp_AgregarTipoPlato('China');
Call sp_AgregarTipoPlato('Comida Rápida');
Call sp_AgregarTipoPlato('Parrillada');

-- ------------ Listar Tipos Platos --------

Delimiter $$
	Create procedure sp_ListarTipoPlatos()
	Begin
		Select Pt.codigoTipoPlato,
        Pt.descripcionTipo
        from TipoPlato Pt;
    End$$
Delimiter ;

Call sp_ListarTipoPlatos();

-- ------ Buscar Tipo Plato ------------

Delimiter $$
	Create procedure sp_BuscarTipoPlato(in idTipoPlato int)
    Begin
		Select Pt.codigoTipoPlato,
        Pt.descripcionTipo
        from TipoPlato Pt where Pt.codigoTipoPlato = idTipoPlato;
    End$$
Delimiter ;

Call sp_BuscarTipoPlato(2);

-- -------- Eliminar Tipo Plato ----------------

Delimiter $$
	Create procedure sp_EliminarTipoPlato(in idTipoPlato int)
	Begin
		Delete from TipoPlato 
			where codigoTipoPlato = idTipoPlato;
    End$$
Delimiter ;

-- ----- Editar Tipo Plato ----------

Delimiter $$
	Create procedure sp_EditarTipoPlato(in idTipoPlato int, in descripTipo varchar(100))
    Begin
		Update TipoPlato Pt
        Set
			Pt.descripcionTipo = descripTipo
            where Pt.codigoTipoPlato = idTipoPlato;
    End$$	
Delimiter ;

Call sp_EditarTipoPlato(3,'China');
Call sp_ListarTipoPlatos();

-- -------- Procedimientos Almacenados Entidad Productos -------

-- ----Agregar Producto --------

Delimiter $$
	Create procedure sp_AgregarProducto(in codigoProducto int, in nombreProducto varchar(150), in cantidad int)
    Begin
		Insert into Productos(codigoProducto,nombreProducto,cantidad)
        values (codigoProducto,nombreProducto,cantidad);
    End$$
Delimiter ;

Call sp_AgregarProducto(1,'RibEye',50);
Call sp_AgregarProducto(2,'Arroz',100);
Call sp_AgregarProducto(3,'Tomates',500);
Call sp_AgregarProducto(4,'Quezo Mozzarella',75);
Call sp_AgregarProducto(5,'Zanahoria',500);

-- ------ Listar Productos ---------

Delimiter $$
	Create procedure sp_ListarProductos()
    Begin
		Select P.codigoProducto,
        P.nombreProducto,
        P.cantidad
        from Productos P;
    End$$
Delimiter ;

Call sp_ListarProductos();

-- ------- Buscar Producto -------

Delimiter $$
	Create procedure sp_BuscarProducto(in idProducto int)
    Begin
		Select P.codigoProducto,
        P.nombreProducto,
        P.cantidad
        from Productos P where P.codigoProducto = idProducto;
    End$$
Delimiter ;

Call sp_BuscarProducto(2);

-- ------ Eliminar Producto --------

Delimiter $$
	Create procedure sp_EliminarProducto(in idProducto int)
    Begin
		Delete from Productos
			where codigoProducto = idProducto;
    End$$
Delimiter ;

-- ---------- Editar Producto --------

Delimiter $$
	Create procedure sp_EditarProducto(in idProducto int, in nomProducto varchar(150), in cant int)
    Begin
		Update Productos P 
        Set
			P.nombreProducto = nomProducto,
            P.cantidad = cant
            where P.codigoProducto = idProducto;
    End$$
Delimiter ;


Call sp_ListarProductos();


-- --------- Procedimientos Almacenados Entidad Servicios -----------

-- ------ Agregar Servicio --------

Delimiter $$
	Create procedure sp_AgregarServicio(in fechaServicio date, in tipoServicio varchar(150), in horaServicio time, in lugarServicio varchar(150),
										in telefonoContacto varchar(10), in codigoEmpresa int)
		Begin
			Insert into Servicios(fechaServicio,tipoServicio,horaServicio,lugarServicio,telefonoContacto,codigoEmpresa)
            values (fechaServicio,tipoServicio,horaServicio,lugarServicio,telefonoContacto,codigoEmpresa);
        End$$
Delimiter ;

Call sp_AgregarServicio(now(),'Servicio A La Carta',now(),' San Lucas','14253623',1);
Call sp_AgregarServicio(now(),'Servicio de banquete',now(),'Antigua Guatemala','11113623',2);
Call sp_AgregarServicio(now(),'Delivery',now(),'Ciudad de Guatemala','11113623',3);
Call sp_AgregarServicio(now(),'Servicio de barra',now(),'Mixco','11113623',4);
Call sp_AgregarServicio(now(),'Servicio de catering',now(),'Antigua Guatemala','11113623',5);

-- ---- Listar Servicios --------

Delimiter $$
	Create procedure sp_ListarServicios()
    Begin
		Select S.codigoServicio,
        S.fechaServicio,
        S.tipoServicio,
        S.horaServicio,
        S.lugarServicio,
        S.telefonoContacto,
        S.codigoEmpresa
        from Servicios S;
    End$$
Delimiter ;

Call sp_ListarServicios();

-- ---------- Buscar Servicio --------

Delimiter $$
	Create procedure sp_BuscarServicio(in idServicio int)
    Begin
		Select S.codigoServicio,
        S.fechaServicio,
        S.tipoServicio,
        S.horaServicio,
        S.lugarServicio,
        S.telefonoContacto,
        S.codigoEmpresa
        from Servicios S where S.codigoServicio = idServicio;
    End$$
Delimiter ;

	;
-- ----- Eliminar Servicio ---------

Delimiter $$
	Create procedure sp_EliminarServicio(in idServicio int)
    Begin
		Delete from Servicios
			where codigoServicio = idServicio;
    End$$
Delimiter ;

-- --------- Editar Servicio -----------

Delimiter $$
	Create procedure sp_EditarServicio(in idServicio int, in fServicio date, in tServicio varchar(150), in hServicio time, in lServicio varchar(150),
										in telContacto varchar(10), in idEmpresa int)
		Begin
			Update Servicios S 
            Set
				S.fechaServicio = fServicio,
                S.tipoServicio = tServicio,
                S.horaServicio = hServicio,
                S.lugarServicio = lServicio,
                S.telefonoContacto = telContacto,
                S.codigoEmpresa = idEmpresa
                where S.codigoServicio = idServicio;
        End$$
Delimiter ;

call sp_EditarServicio(6, "2023-12-12", "Reunion","20:00:00","Costa Rica","11117777",1);
call sp_EditarServicio(5, "2023-12-12", "Meeting","22:30:00","Antigua Guatemala","00003333",2);


-- ------- Procedimientos Almacenados Entidad Presupuestos -----------

-- ---- Agregar Presupuesto --

Delimiter $$
	Create procedure sp_AgregarPresupuesto(in fechaSolicitud date, in cantidadPresupuesto decimal(10,2), in codigoEmpresa int)
    Begin
		Insert into Presupuestos(fechaSolicitud, cantidadPresupuesto, codigoEmpresa)
        values (fechaSolicitud, cantidadPresupuesto, codigoEmpresa);
    End$$
Delimiter ;

Call sp_AgregarPresupuesto('2023-04-09', 12500.14, 1);
Call sp_AgregarPresupuesto('2023-04-09', 12500.00, 2);
Call sp_AgregarPresupuesto('2023-04-09', 100000.00, 3);
Call sp_AgregarPresupuesto('2022-05-09', 300000.00, 4);
Call sp_AgregarPresupuesto('2021-09-09', 500000.00, 5);

-- ------ Listar Presupuestos -----

Delimiter $$
	Create procedure sp_ListarPresupuestos()
    Begin
		Select Pre.codigoPresupuesto,
        Pre.fechaSolicitud,
        Pre.cantidadPresupuesto,
        Pre.codigoEmpresa
        from Presupuestos Pre;
    End$$
Delimiter ;

call sp_ListarPresupuestos();

-- ------- Buscar Presupuestos ------------

Delimiter $$
	Create procedure sp_BuscarPresupuesto(in idPresupuesto int)
    Begin
		Select Pre.codigoPresupuesto,
        Pre.fechaSolicitud,
        Pre.cantidadPresupuesto,
        Pre.codigoEmpresa
        from Presupuestos Pre where Pre.codigoPresupuesto = idPresupuesto;
    End$$
Delimiter ;

Call sp_BuscarPresupuesto(2);

-- ------- Eliminar Presupuesto -------

Delimiter $$
	Create procedure sp_EliminarPresupuesto(in idPresupuesto int)
    Begin
		Delete from Presupuestos
			where codigoPresupuesto = idPresupuesto;
    End$$
Delimiter ;

-- ------- Editar Presupuesto ------------

Delimiter $$
	Create procedure sp_EditarPresupuesto(in idPresupuesto int, in fSolicitud date, in cantPresupuesto decimal(10,2), in idEmpresa int)
    Begin
		Update Presupuestos Pre
        set
			Pre.fechaSolicitud = fSolicitud,
			Pre.cantidadPresupuesto = cantPresupuesto,
			Pre.codigoEmpresa = idEmpresa
            where Pre.codigoPresupuesto = idPresupuesto;
    End$$
Delimiter ;


 -- ---------Procedimientos Almacenados Entidad Platos ----------
 
 -- ---- Agregar Plato -----
 
 Delimiter $$
	Create procedure sp_AgregarPlato(in cantidad int, in nombrePlato varchar(50), in descripcionPlato varchar(150), in precioPlato decimal(10,2), in codigoTipoPlato int)
    Begin
		Insert into Platos(cantidad, nombrePlato, descripcionPlato, precioPlato, codigoTipoPlato)
        values (cantidad, nombrePlato, descripcionPlato, precioPlato, codigoTipoPlato);
    End$$
 Delimiter ;
 
 Call sp_AgregarPlato(1, 'Paella','Camarones Extra',80.00, 1);
 Call sp_AgregarPlato(2, 'Arroz Chino','De lomito, pollo y camarón',50.00, 2);
 Call sp_AgregarPlato(3, 'Parrillada','Con frijoles, guacamole, salse',100.00, 3);
 Call sp_AgregarPlato(10, 'Pollo Frito','Pollo frito crispi y a la parrilla',110.00, 4);
 Call sp_AgregarPlato(2, 'Pizza De Peperoni','Con Orilla de Quezo y doble peperoni',125.00, 5);

 
 -- --- Listar Platos---
 
 Delimiter $$
	Create procedure sp_ListarPlatos()
    Begin
		Select Pla.codigoPlato,
        Pla.cantidad,
        Pla.nombrePlato,
        Pla.descripcionPlato,
        Pla.precioPlato,
        Pla.codigoTipoPlato
        from Platos Pla;
    End$$
 Delimiter ;
 
 Call sp_ListarPlatos();

-- ------ Buscar Plato -----

Delimiter $$
	Create procedure sp_BuscarPlato(in idPlato int)
    Begin
		Select Pla.codigoPlato,
        Pla.cantidad,
        Pla.nombrePlato,
        Pla.descripcionPlato,
        Pla.precioPlato,
        Pla.codigoTipoPlato
        from Platos Pla where Pla.codigoPlato = idPlato;
    End$$
Delimiter ;

Call sp_BuscarPlato(1);

-- ------- Eliminar Plato -----

Delimiter $$
	Create procedure sp_EliminarPlato(in idPlato int)
    Begin
		Delete from Platos 
			where codigoPlato = idPlato;
    End$$
Delimiter ;

-- ----- Editar Plato -----

Delimiter $$
	Create procedure sp_EditarPlato(in idPlato int, in cant int, in nomPlato varchar(50), in descriPlato varchar(150), in precioPla decimal(10,2), in idTipoPlato int)
    Begin
		Update Platos Pla
        set
		Pla.cantidad = cant,
        Pla.nombrePlato = nomPlato,
        Pla.descripcionPlato = descriPlato,
        Pla.precioPlato = precioPla,
        Pla.codigoTipoPlato = idTipoPlato
        where Pla.codigoPlato = idTipoPlato;
    End$$
Delimiter ;


 
-- ------- Procedimientos Almacenados Entidad Productos_has_Platos -------------------------

-- --- Agregar Productos_has_platos

Delimiter $$
	Create procedure sp_AgregarProductosHasPlatos(in Productos_codigoProducto int, in codigoPlato int, in codigoProducto int)
		Begin
			Insert into Productos_has_Platos(Productos_codigoProducto, codigoPlato, codigoProducto)
            values(Productos_codigoProducto, codigoPlato,codigoProducto);
        End$$
Delimiter ;

call sp_AgregarProductosHasPlatos(1,1,1);
call sp_AgregarProductosHasPlatos(2,2,2);
call sp_AgregarProductosHasPlatos(3,3,3);
call sp_AgregarProductosHasPlatos(4,4,4);
call sp_AgregarProductosHasPlatos(5,5,5);

-- --- Listar Productos_has_Platos

Delimiter $$
	Create procedure sp_ListarProductos_has_Platos()
		Begin
			Select 
				PRHP.Productos_codigoProducto,
                PRHP.codigoPlato,
                PRHP.codigoProducto
                from Productos_has_Platos PRHP;
        End$$
Delimiter ;

call sp_ListarProductos_has_Platos();

-- ---------- Buscar Productos_has_Platos ----------

Delimiter $$
	Create procedure sp_BuscarProductos_has_Platos(in idProducto_has int)
		Begin
			Select
				PRHP.Productos_codigoProducto,
                PRHP.codigoPlato,
                PRHP.codigoProducto
                from Productos_has_Platos PRHP where PRHP.Productos_codigoProducto = idProducto_has;
        End$$
Delimiter ;

-- ----------- Eliminar Productos_has_Platos ------------

Delimiter $$
	Create procedure sp_EliminarProductos_has_Platos(in idProducto_has int)
		Begin
			Delete from Productos_has_Platos
				where Productos_codigoProducto = idProducto_has;
        End$$
Delimiter ;



-- ------- Editar Productos_has_Platos ---------

Delimiter $$
	Create procedure sp_EditarProductos_has_Platos(in pro_codiProducto int, in codiPlato int, in codiProducto int)
    Begin
		Update Productos_has_Platos PRHP
        SET
        PRHP.codigoPlato = codiPlato,
        PRHP.codigoProducto = codiProducto
        where PRHP.Productos_codigoProducto = pro_codiProducto;
    End$$
Delimiter ;


-- ---------------- Procedimientos Almacenados Entidad Servicios_has_Platos -----------

-- --- Agregar Servicios_has_Platos

Delimiter $$
	Create procedure sp_AgregarServicioHasPlato(in Servicios_codigoServicio int, in codigoPlato int, in codigoServicio int)
    Begin
		Insert into Servicios_has_Platos(Servicios_codigoServicio, codigoPlato, codigoServicio)
        values(Servicios_codigoServicio, codigoPlato, codigoServicio);
    End$$
Delimiter ;

Call sp_AgregarServicioHasPlato(1,1,1);
Call sp_AgregarServicioHasPlato(2,2,2);
Call sp_AgregarServicioHasPlato(3,3,3);
Call sp_AgregarServicioHasPlato(4,4,4);
Call sp_AgregarServicioHasPlato(5,5,5);

-- ----- Listar Servicios_has_Platos -------

Delimiter $$
	Create procedure sp_ListarServicios_has_Platos()
		Begin
			Select
            SEHP.Servicios_codigoServicio,
            SEHP.codigoPlato,
            SEHP.codigoServicio
            from Servicios_has_Platos SEHP;
        End$$
Delimiter ;

Call sp_ListarServicios_has_Platos();

-- --------- Buscar Servicios_has_Platos ----------
Delimiter $$
	Create procedure sp_BuscarServicios_has_Platos(in ser_codServi int)
		Begin
			Select
            SEHP.Servicios_codigoServicio,
            SEHP.codigoPlato,
            SEHP.codigoServicio
            from Servicios_has_Platos SEHP where SEHP.Servicios_codigoServicio = ser_codServi;
        End$$
Delimiter ;

-- ------- Eliminar Servicios_has_Platos

Delimiter $$
	Create procedure sp_EliminarServicios_has_Platos(in ser_codServi int)
		Begin
			Delete from Servicios_has_Platos
            where Servicios_codigoServicio = ser_codServi;
        End$$
Delimiter ;

-- -------- Editar Servicios_has_Platos -----------

Delimiter $$
	Create procedure sp_EditarServicios_has_Platos(in ser_codServi int, in codiPlat int, in codiServi int)
		Begin
			Update Servicios_has_Platos SEHP
            Set
            SEHP.codigoPlato = codiPlat,
            SEHP.codigoServicio = codiServi
            where SEHP.Servicio_codigoServicio = ser_codServi;
        End$$
Delimiter ;

-- ------- Procedimientos Almacenados de la entidad Servicios_has_Empleados

-- ------- Agregar Servicios_has_Empleados ------------

Delimiter $$
	Create procedure sp_AgregarServicioHasEmpleado(in Servicios_codigoServicio int, in codigoServicio int, in codigoEmpleado int, 
														in fechaEvento date, in horaEvento time, in lugarEvento varchar(150))
		Begin
			Insert into Servicios_has_Empleados(Servicios_codigoServicio, codigoServicio, codigoEmpleado, fechaEvento, horaEvento, lugarEvento)
            values(Servicios_codigoServicio, codigoServicio, codigoEmpleado, fechaEvento, horaEvento, lugarEvento);
        End$$
Delimiter ;

call sp_AgregarServicioHasEmpleado(1, 1, 1, '2022-09-09', '21:20:18', 'Antigua Guatemala');
call sp_AgregarServicioHasEmpleado(2, 2, 2, '2022-09-09', '21:20:18', 'Antigua Guatemala');
call sp_AgregarServicioHasEmpleado(3, 3, 3, '2022-09-09', '21:20:18', 'Antigua Guatemala');
call sp_AgregarServicioHasEmpleado(4, 4, 4, '2022-09-09', '21:20:18', 'Antigua Guatemala');
call sp_AgregarServicioHasEmpleado(5, 5, 5, '2022-09-09', '21:20:18', 'Antigua Guatemala');


-- ---- Listar Servicios_has_Empleados -----

Delimiter $$
	Create procedure sp_ListarServicios_has_Empleados()
		Begin
			Select
            SHE.Servicios_codigoServicio,
            SHE.codigoServicio,
            SHE.codigoEmpleado,
            SHE.fechaEvento,
            SHE.horaEvento,
            SHE.lugarEvento
            from Servicios_has_Empleados SHE;
        End$$
Delimiter ;

Call sp_ListarServicios_has_Empleados();

-- ------ Buscar Servicios_has_Empleados -----

Delimiter $$
	Create procedure sp_BuscarServicios_has_Empleados(in ser_idServi int) 
		Begin
			Select
            SHE.Servicios_codigoServicio,
            SHE.codigoServicio,
            SHE.codigoEmpleado,
            SHE.fechaEvento,
            SHE.horaEvento,
            SHE.lugarEvento
            from Servicios_has_Empleados SHE where SHE.Servicios_codigoServicio = ser_idServi;
        End$$
Delimiter ;

-- ----- Eliminar Servicios_has_Empleados---

Delimiter $$
	Create procedure sp_EliminarServicios_has_Empleados(in ser_idServi int)
		Begin
			Delete from Servicios_has_Empleados
            where Servicios_codigoServicio = ser_idServi;
        End$$
Delimiter ;


-- ---- Editar Servicios_has_Empleados

Delimiter $$
    Create procedure sp_EditarServicios_has_Empleados(in ser_idServi int, in codServicio int, in codEmpleado int, in feEvento date, in hrEvento time, lugaEvento varchar(150))
        Begin
            Update Servicios_has_Empleados
                set SHE.codigoServicio = codServicio,
                    SHE.codigoEmpleado = codEmpleado,
                    SHE.fechaEvento = feEvento,
                    SHE.horaEvento = hrEvento,
                    SHE.lugarEvento = lugaEvento
                    where SHE.Servicios_codigoServicio = ser_idServi;
        End$$
Delimiter ;


ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY 'admin';
flush privileges;


-- Reporte Profesor Pedro Armas--

Delimiter $$
	Create procedure sp_Reporte(in idEmpresa int)
	Begin
		Select 
        Empre.nombreEmpresa as "Nombre Empresa", Empre.direccion as "Direccion Empresa", Empre.telefono as "Telefono Empresa",
        Pre.fechaSolicitud as "Fecha De Solicitud", Pre.cantidadPresupuesto as "Presupuesto",
		Serv.tipoServicio as "Tipo De Servicio",Serv.fechaServicio as "Fecha Servicio", Serv.horaServicio as "Hora de Servicio", Serv.lugarServicio as "Lugar Del Servicio",
		concat(Empl.nombresEmpleado, ' ',Empl.apellidosEmpleado) as "Nombre Empleado", Empl.gradoCocinero as "Grado de Cocinero",
        Te.descripcion as "Descripcion Empleado",
        Pla.cantidad as "Cantidad", Pla.nombrePlato as "Nombre Plato", Pla.descripcionPlato as "Descripcion Plato", Pla.precioPlato as "Precio Plato",
        Tp.descripcionTipo as "Descripcion Tipo Plato",
        Pro.nombreProducto as "Nombre Producto", Pro.cantidad as "Cantidad Producto"
        from Empresas as Empre
        INNER JOIN Presupuestos as Pre on Empre.codigoEmpresa = Pre.codigoEmpresa
        INNER JOIN Servicios as Serv on Empre.codigoEmpresa = Serv.codigoEmpresa
        INNER JOIN Servicios_has_Empleados as SHE on Serv.codigoServicio = SHE.codigoServicio
        INNER JOIN Empleados as Empl on SHE.codigoEmpleado = Empl.codigoEmpleado
        INNER JOIN TipoEmpleado as Te on Empl.codigoTipoEmpleado = Te.codigoTipoEmpleado
        INNER JOIN Servicios_has_Platos as SHP ON Serv.codigoServicio = SHP.codigoServicio
        INNER JOIN Platos as Pla on SHP.codigoPlato = Pla.codigoPlato
        INNER JOIN TipoPlato as Tp on Pla.codigoTipoPlato = Tp.codigoTipoPlato
        INNER JOIN Productos_has_Platos as PHP on Pla.codigoPlato = PHP.codigoPlato
        INNER JOIN Productos as Pro on PHP.codigoProducto = Pro.codigoProducto
        where Empre.codigoEmpresa = idEmpresa;
        
         
    End$$

Delimiter ;

call sp_Reporte(3);


