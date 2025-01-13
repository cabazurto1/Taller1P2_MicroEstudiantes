package com.espe.micro_cursos.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.Date;

/**
 * Entidad que representa a un Estudiante en el sistema.
 */
@Entity
@Table(name = "estudiante") // Asegura que la tabla en la base de datos se llame "estudiante"
public class Estudiante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 50, message = "El nombre no puede exceder 50 caracteres")
    @Column(nullable = false, length = 50)
    private String nombre;

    @NotBlank(message = "La matrícula es obligatoria")
    @Size(max = 20, message = "La matrícula no puede exceder 20 caracteres")
    @Column(name = "matricula", unique = true, nullable = false, length = 20)
    private String matricula;

    @Min(value = 16, message = "La edad mínima es 16 años")
    @Max(value = 100, message = "La edad máxima es 100 años")
    @Column(nullable = false)
    private int edad;

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @Past(message = "La fecha de nacimiento debe estar en el pasado")
    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_nacimiento", nullable = false)
    private Date fechaNacimiento;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creado_en", nullable = false, updatable = false)
    private Date creadoEn;

    /**
     * Constructor vacío requerido por JPA.
     */
    public Estudiante() {
    }

    /**
     * Constructor con todos los campos excepto el ID y creadoEn.
     *
     * @param nombre          Nombre del estudiante.
     * @param matricula       Matrícula única del estudiante.
     * @param edad            Edad del estudiante.
     * @param fechaNacimiento Fecha de nacimiento del estudiante.
     */
    public Estudiante(String nombre, String matricula, int edad, Date fechaNacimiento) {
        this.nombre = nombre;
        this.matricula = matricula;
        this.edad = edad;
        this.fechaNacimiento = fechaNacimiento;
    }

    /**
     * Método que se ejecuta antes de persistir la entidad para inicializar creadoEn.
     */
    @PrePersist
    protected void prePersist() {
        this.creadoEn = new Date();
    }

    // Getters y Setters

    public int getId() {
        return id;
    }

    // No se proporciona setter para el ID ya que se genera automáticamente
    // public void setId(int id) {
    //     this.id = id;
    // }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Date getCreadoEn() {
        return creadoEn;
    }

    // No se proporciona setter para creadoEn ya que se gestiona automáticamente
    // public void setCreadoEn(Date creadoEn) {
    //     this.creadoEn = creadoEn;
    // }

    // Opcional: Override de métodos equals() y hashCode() si es necesario

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Estudiante that = (Estudiante) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    // Opcional: Override de toString() para facilitar la depuración

    @Override
    public String toString() {
        return "Estudiante{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", matricula='" + matricula + '\'' +
                ", edad=" + edad +
                ", fechaNacimiento=" + fechaNacimiento +
                ", creadoEn=" + creadoEn +
                '}';
    }
}
