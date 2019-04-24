package com.example.security.models.entity;

import org.mongodb.morphia.annotations.Entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;


/**
 * The Class Usuario.
 */
@Entity
public class Usuario extends BaseEntity implements Serializable {

    /**
     * The Enum UserRole.
     */
    public enum UserRole {

        /**
         * The role user.
         */
        ROLE_USER("ROLE_USER"),
        /**
         * The role supervisor.
         */
        ROLE_SUPERVISOR("ROLE_SUPERVISOR"),
        /**
         * The role gestor.
         */
        ROLE_GESTOR("ROLE_GESTOR"),
        /**
         * The role gestor limitado.
         */
        ROLE_OTRO_GESTOR("ROLE_OTRO_GESTOR"),
        /**
         * The role admin.
         */
        ROLE_ADMIN("ROLE_ADMIN");

        /**
         * The val.
         */
        private final String val;

        /**
         * Instantiates a new user role.
         *
         * @param val the val
         */
        UserRole(String val) {
            this.val = val;
        }

        /**
         * Gets the value.
         *
         * @return the value
         */
        public String getValue() {
            return val;
        }
    }

    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The nombre completo.
     */
    private String nombreCompleto;

    /**
     * The dni.
     */
    private String dni;

    /**
     * The email.
     */
    private String email;

    /**
     * The foto.
     */
    private String foto;

    /**
     * The roles.
     */
    private Set<UserRole> roles;

    /**
     * The last connection.
     */
    private Date lastConnection;

    /**
     * The primera conexion.
     */
    private Boolean primeraConexion = true;

    /**
     * Instantiates a new usuario.
     */
    public Usuario() { // tiene que estar vacío

    }

    /**
     * Instantiates a new usuario.
     *
     * @param nombreCompleto the nombre completo
     * @param dni            the dni
     * @param email          the email
     */
    public Usuario(String nombreCompleto, String dni, String email) {
        super();
        this.nombreCompleto = nombreCompleto;
        this.dni = dni;
        this.email = email;
    }

    /**
     * Gets the roles.
     *
     * @return the roles
     */
    public Set<UserRole> getRoles() {
        return roles;
    }

    /**
     * Sets the roles.
     *
     * @param roles the new roles
     */
    public void setRoles(Set<UserRole> roles) {
        this.roles = roles;
    }

    /**
     * Gets the nombre completo.
     *
     * @return the nombre completo
     */
    public String getNombreCompleto() {
        return nombreCompleto;
    }

    /**
     * Sets the nombre completo.
     *
     * @param nombreCompleto the new nombre completo
     */
    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    /**
     * Gets the dni.
     *
     * @return the dni
     */
    public String getDNI() {
        return dni;
    }

    /**
     * Sets the dni.
     *
     * @param dni the new dni
     */
    public void setDNI(String dni) {
        this.dni = dni;
    }

    /**
     * Gets the email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email.
     *
     * @param email the new email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((dni == null) ? 0 : dni.hashCode());
        return result;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Usuario other = (Usuario) obj;
        if (dni == null) {
            if (other.dni != null) {
                return false;
            }
        } else if (!dni.equals(other.dni)) {
            return false;
        }
        return true;
    }

    /**
     * Gets the foto.
     *
     * @return the foto
     */
    public String getFoto() {
        return foto;
    }

    /**
     * Sets the foto.
     *
     * @param foto the new foto
     */
    public void setFoto(String foto) {
        this.foto = foto;
    }

    /**
     * Gets the last connection.
     *
     * @return the last connection
     */
    public Date getLastConnection() {
        return lastConnection;
    }

    /**
     * Sets the last connection.
     *
     * @param lastConnection the new last connection
     */
    public void setLastConnection(Date lastConnection) {
        this.lastConnection = lastConnection;
    }

    /**
     * Checks if is primera conexion.
     *
     * @return true, if is primera conexion
     */
    public boolean isPrimeraConexion() {
        return primeraConexion;
    }

    /**
     * Sets the primera conexion.
     *
     * @param primeraConexion the new primera conexion
     */
    public void setPrimeraConexion(boolean primeraConexion) {
        this.primeraConexion = primeraConexion;
    }

    /**
     * Gets the areas.
     *
     * @return the areas
     */

}
