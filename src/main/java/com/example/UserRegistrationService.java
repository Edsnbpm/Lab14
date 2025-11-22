package com.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Servicio de registro de usuarios con validaciones robustas.
 * <p>
 * Esta clase no está diseñada para ser extendida.
 * </p>
 */
public final class UserRegistrationService {

    private static final Logger LOGGER = Logger.getLogger(
            UserRegistrationService.class.getName());

    /** Longitud mínima requerida para la contraseña. */
    private static final int MIN_PASSWORD_LENGTH = 8;

    /** Expresión regular básica para validar formato de email. */
    private static final String EMAIL_REGEX = "^[^@]+@[^@]+\\.[^@]+$";

    /** Lista de nombres de usuario registrados. */
    private final List<String> registeredUsernames = new ArrayList<>();

    /** Último mensaje de error producido. Mantenido público por compatibilidad. */
    public String lastErrorMessage = "";

    /**
     * Intenta registrar un nuevo usuario.
     *
     * @param username nombre de usuario (no nulo, no vacío)
     * @param password contraseña (no nula, mínimo 8 caracteres)
     * @param email    correo electrónico con formato válido
     * @return {@code true} si el registro fue exitoso, {@code false} en caso contrario
     */
    public boolean registerUser(final String username,
                                final String password,
                                final String email) {

        if (!isValidUsername(username)) {
            return false;
        }
        if (!isValidPassword(password)) {
            return false;
        }
        if (!isValidEmail(email)) {
            return false;

        }
        if (registeredUsernames.contains(username)) {
            lastErrorMessage = "El nombre de usuario ya está registrado.";
            LOGGER.warning("Intento de registro duplicado: " + username);
            return false;
        }

        try {
            saveUser(username);
            LOGGER.info("Usuario registrado correctamente: " + username);
            return true;
        } catch (IllegalArgumentException e) {
            lastErrorMessage = e.getMessage();
            LOGGER.warning("Error al guardar usuario: " + e.getMessage());
            return false;
        } catch (Exception e) {
            lastErrorMessage = "Error interno del servidor al registrar el usuario.";
            LOGGER.log(Level.SEVERE, "Error inesperado al guardar usuario", e);
            return false;
        }
    }

    private boolean isValidUsername(final String username) {
        if (username == null || username.trim().isEmpty()) {
            lastErrorMessage = "El nombre de usuario no puede ser nulo ni estar vacío.";
            return false;
        }
        return true;
    }

    private boolean isValidPassword(final String password) {
        if (password == null) {
            lastErrorMessage = "La contraseña no puede ser nula.";
            return false;
        }
        if (password.length() < MIN_PASSWORD_LENGTH) {
            lastErrorMessage = "La contraseña debe tener al menos "
                    + MIN_PASSWORD_LENGTH + " caracteres.";
            return false;
        }
        return true;
    }

    private boolean isValidEmail(final String email) {
        if (email == null || !email.matches(EMAIL_REGEX)) {
            lastErrorMessage = "El correo electrónico no tiene un formato válido.";
            return false;
        }
        return true;
    }

    private void saveUser(final String username) throws Exception {
        Objects.requireNonNull(username, "Username no puede ser nulo al guardar");

        if ("error".equalsIgnoreCase(username)) {
            throw new IllegalArgumentException("Nombre de usuario no permitido.");
        }
        registeredUsernames.add(username);
    }

    /**
     * Devuelve la longitud de una cadena.
     *
     * @param s cadena de entrada
     * @return longitud de la cadena o -1 si es {@code null}
     */
    public int getStringLength(final String s) {
        return s == null ? -1 : s.length();
    }

    /**
     * Devuelve una copia inmutable de los nombres de usuario registrados.
     *
     * @return lista de nombres de usuario registrados
     */
    public List<String> getRegisteredUsernames() {
        return List.copyOf(registeredUsernames);
    }
}