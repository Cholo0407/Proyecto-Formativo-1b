package Modelo

data class Pacientes(
    val uuid_paciente: String,
    var nombre: String,
    var apellido: String,
    var edad: Int,
    var enfermedad: String,
    var numero_Cuarto: Int,
    var numero_Cama: Int,
    var medicamentos: String,
    var fecha_Nacimiento: String,
    var hora_Medicamentos: String
)
