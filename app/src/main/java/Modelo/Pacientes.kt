package Modelo

data class Pacientes(
    val uuid_paciente: String,
    val nombre: String,
    val apellido: String,
    val edad: Int,
    val enfermedad: String,
    val numero_Cuarto: Int,
    val numero_Cama: Int,
    val medicamentos: String,
    val fecha_Nacimiento: String,
    val hora_Medicamentos: String
)
