package med.voll.api.consulta;

import med.voll.api.infra.errores.validacionDeIntegridad;
import med.voll.api.medico.Medico;
import med.voll.api.medico.MedicoRepository;
import med.voll.api.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgendaDeConsultaService {

    @Autowired
    private PacienteRepository pacienteRepository;
    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private ConsultaRepository consultaRepository;

    public void agendar(DatosAgendarConsulta datos) {

        if (pacienteRepository.findById(datos.idPaciente()).isPresent()){
            throw new validacionDeIntegridad("ESTE ID PARA EL PACIENTE NO FUE ENCONTRADO");
        }

        if (datos.idMedico()!=null && medicoRepository.existsById(datos.idMedico())){
            throw new validacionDeIntegridad("ESTE ID PARA EL MEDICO NO FUE ENCONTRADO");
        }

        var paciente = pacienteRepository.findById(datos.idPaciente()).get();
        var medico = seleccionarMedico(datos);
        var consulta = new Consulta(null, medico, paciente, datos.fecha());

        consultaRepository.save(consulta);
    }

    private Medico seleccionarMedico(DatosAgendarConsulta datos) {

        if (datos.idMedico()!=null){
            return medicoRepository.getReferenceById(datos.idMedico());
        }
        if (datos.especialidad()==null){
            throw new validacionDeIntegridad("DEBE SELECCIONARSE UNA ESPECIALIDAD PARA EL MEDICO");
        }

        return medicoRepository.seleccionarMedicoConEspecialidadEnFecha(datos.especialidad(),datos.fecha());
    }
}
