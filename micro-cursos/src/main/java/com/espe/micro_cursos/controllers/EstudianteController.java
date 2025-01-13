package com.espe.micro_cursos.controllers;

import com.espe.micro_cursos.model.entity.Estudiante;
import com.espe.micro_cursos.services.EstudianteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/estudiantes")
public class EstudianteController {

    @Autowired
    private EstudianteService estudianteService;

    /**
     * Endpoint para listar todos los estudiantes.
     *
     * @return ResponseEntity con la lista de estudiantes y mensaje
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> listarEstudiantes() {
        List<Estudiante> estudiantes = estudianteService.listarTodos();
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Estudiantes obtenidos exitosamente.");
        response.put("data", estudiantes);
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint para obtener un estudiante por su ID.
     *
     * @param id ID del estudiante
     * @return ResponseEntity con el estudiante encontrado o error 404 si no existe
     */
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> obtenerEstudiante(@PathVariable int id) {
        Estudiante estudiante = estudianteService.obtenerPorId(id);
        if (estudiante == null) {
            Map<String, Object> responseNotFound = new HashMap<>();
            responseNotFound.put("message", "Estudiante no encontrado.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseNotFound);
        }
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Estudiante encontrado exitosamente.");
        response.put("data", estudiante);
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint para crear un nuevo estudiante.
     *
     * @param estudiante Objeto Estudiante enviado en el cuerpo de la solicitud
     * @param result Resultado de la validación
     * @return ResponseEntity con el estudiante creado y mensaje, o errores de validación
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> crearEstudiante(@Valid @RequestBody Estudiante estudiante, BindingResult result) {
        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()) {
            List<String> errores = obtenerErroresValidacion(result);
            response.put("message", "Errores de validación en la solicitud.");
            response.put("errors", errores);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        try {
            Estudiante estudianteCreado = estudianteService.guardarEstudiante(estudiante);
            response.put("message", "Estudiante creado exitosamente.");
            response.put("data", estudianteCreado);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            response.put("message", "Error al crear el estudiante.");
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Endpoint para actualizar un estudiante existente.
     *
     * @param id ID del estudiante a actualizar
     * @param estudiante Objeto Estudiante con los datos actualizados
     * @param result Resultado de la validación
     * @return ResponseEntity con el estudiante actualizado o errores
     */
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> actualizarEstudiante(@PathVariable int id, @Valid @RequestBody Estudiante estudiante, BindingResult result) {
        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()) {
            List<String> errores = obtenerErroresValidacion(result);
            response.put("message", "Errores de validación en la solicitud.");
            response.put("errors", errores);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        Estudiante estudianteExistente = estudianteService.obtenerPorId(id);
        if (estudianteExistente == null) {
            response.put("message", "Estudiante no encontrado.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        // Actualizar campos relevantes
        estudianteExistente.setNombre(estudiante.getNombre());
        estudianteExistente.setMatricula(estudiante.getMatricula());
        estudianteExistente.setEdad(estudiante.getEdad());
        estudianteExistente.setFechaNacimiento(estudiante.getFechaNacimiento());
        // No se actualiza creadoEn ya que es solo para creación

        try {
            Estudiante estudianteActualizado = estudianteService.guardarEstudiante(estudianteExistente);
            response.put("message", "Estudiante actualizado exitosamente.");
            response.put("data", estudianteActualizado);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", "Error al actualizar el estudiante.");
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Endpoint para eliminar un estudiante por su ID.
     *
     * @param id ID del estudiante a eliminar
     * @return ResponseEntity con mensaje de eliminación o error 404 si no existe
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> eliminarEstudiante(@PathVariable int id) {
        Estudiante estudiante = estudianteService.obtenerPorId(id);
        if (estudiante == null) {
            Map<String, Object> responseNotFound = new HashMap<>();
            responseNotFound.put("message", "Estudiante no encontrado.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseNotFound);
        }

        try {
            estudianteService.eliminarPorId(id);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Estudiante eliminado exitosamente.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> responseError = new HashMap<>();
            responseError.put("message", "Error al eliminar el estudiante.");
            responseError.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseError);
        }
    }

    /**
     * Método auxiliar para extraer mensajes de errores de validación.
     *
     * @param result BindingResult con los errores de validación
     * @return Lista de mensajes de error
     */
    private List<String> obtenerErroresValidacion(BindingResult result) {
        List<String> errores = new ArrayList<>();
        result.getFieldErrors().forEach(error ->
                errores.add(error.getField() + ": " + error.getDefaultMessage())
        );
        return errores;
    }
}
