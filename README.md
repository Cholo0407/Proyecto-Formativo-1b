////////////////////////////////////
///// Script de base de datos //////
////////////////////////////////////

-- Usuarios
CREATE TABLE Usuarios (
    id_usuario NUMBER PRIMARY KEY,
    nombre NVARCHAR2(30) NOT NULL,
    usuario NVARCHAR2(20) NOT NULL,
    contrasena NVARCHAR2(20) NOT NULL
);

CREATE SEQUENCE seq_usuarios_id
START WITH 1
INCREMENT BY 1;

CREATE OR REPLACE TRIGGER trg_usuarios_id
BEFORE INSERT ON Usuarios
FOR EACH ROW
BEGIN
    SELECT seq_usuarios_id.NEXTVAL INTO :NEW.id_usuario FROM dual;
END;
/

-- Pacientes
CREATE TABLE Pacientes(
id_paciente NUMBER PRIMARY KEY,
nombre NVARCHAR2(30) NOT NULL,
apellido NVARCHAR2(30) NOT NULL,
edad INT NOT NULL, 
enfermedad NVARCHAR2(50) NOT NULL,
numero_Cuarto INT NOT NULL,
numero_Cama INT NOT NULL,
medicamentos NVARCHAR2(100) NOT NULL,
fecha_nacimiento NVARCHAR2(10) NOT NULL,
hora_medicamentos NVARCHAR2(10) NOT NULL
);

CREATE SEQUENCE seq_pacientes_id
START WITH 1
INCREMENT BY 1;

CREATE OR REPLACE TRIGGER trg_pacientes_id
BEFORE INSERT ON Pacientes
FOR EACH ROW
BEGIN
    SELECT seq_pacientes_id.NEXTVAL INTO :NEW.id_paciente FROM dual;
END;
/

-- comandos "DROP"
drop table usuarios;
drop table pacientes;
