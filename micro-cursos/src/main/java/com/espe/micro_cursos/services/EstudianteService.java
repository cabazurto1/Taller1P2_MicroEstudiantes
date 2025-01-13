package com.espe.micro_cursos.services;

import com.espe.micro_cursos.model.entity.Estudiante;

import java.util.Date;
import java.util.List;

public interface EstudianteService {
    // Métodos CRUD
    List<Estudiante> listarTodos();
    Estudiante guardarEstudiante(Estudiante estudiante);
    Estudiante obtenerPorId(int id);
    void eliminarPorId(int id);

    // Métodos de búsqueda personalizados
    Estudiante buscarPorMatricula(String matricula);
    List<Estudiante> buscarPorEdad(int edad);
    List<Estudiante> buscarPorEdadMayorQue(int edad);
    List<Estudiante> buscarPorFechaNacimientoPosterior(Date fecha);
    List<Estudiante> buscarPorNombreContiene(String texto);
}
