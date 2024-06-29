package med.voll.api.medico;

public record datosListadoMedico(
        Long id,
        String nombre,
        String especialidad,
        String documento,
        String email
) {

    public datosListadoMedico(Medico medico){
        this(
                medico.getId(),
                medico.getNombre(),
                medico.getEspecialidad().toString(),
                medico.getDocumento(),
                medico.getEmail()
        );
    }
}
