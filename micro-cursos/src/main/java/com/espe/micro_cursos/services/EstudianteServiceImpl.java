package com.espe.micro_cursos.services;

import com.espe.micro_cursos.model.entity.Estudiante;
import com.espe.micro_cursos.repositories.EstudianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class EstudianteServiceImpl implements EstudianteService {

    @Autowired
    private EstudianteRepository estudianteRepository;

    @Override
    public List<Estudiante> listarTodos() {
        return estudianteRepository.findAll();
    }

    @Override
    public Estudiante guardarEstudiante(Estudiante estudiante) {
        return estudianteRepository.save(estudiante);
    }

    @Override
    public Estudiante obtenerPorId(int id) {
        return estudianteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado con ID: " + id));
    }

    @Override
    public void eliminarPorId(int id) {
        if (!estudianteRepository.existsById(id)) {
            throw new RuntimeException("Estudiante no encontrado con ID: " + id);
        }
        estudianteRepository.deleteById(id);
    }

    @Override
    public Estudiante buscarPorMatricula(String matricula) {
        Estudiante estudiante = estudianteRepository.findByMatricula(matricula);
        if (estudiante == null) {
            throw new RuntimeException("Estudiante no encontrado con matrícula: " + matricula);
        }
        return estudiante;
    }

    @Override
    public List<Estudiante> buscarPorEdad(int edad) {
        return estudianteRepository.findByEdad(edad);
    }

    @Override
    public List<Estudiante> buscarPorEdadMayorQue(int edad) {
        return estudianteRepository.findByEdadGreaterThan(edad);
    }

    @Override
    public List<Estudiante> buscarPorFechaNacimientoPosterior(Date fecha) {
        return estudianteRepository.findByFechaNacimientoAfter(fecha);
    }

    @Override
    public List<Estudiante> buscarPorNombreContiene(String texto) {
        return estudianteRepository.findByNombreContaining(texto);
    }
}
