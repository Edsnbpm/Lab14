package com.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Servicio de registro de usuarios mejorado, sin modelo User.
 * Compatible con el Main original que solo prueba con cadenas.
 */
public class UserRegistrationService {

    private static final Logger LOGGER = Logger.getLogger(UserRegistrationService.class.getName());

    private static final int MIN_PASSWORD_LENGTH = 8;
    private static final String EMAIL_REGEX = "^[^@]+@[^@]+\\.[^@]+$"; // válido básico

    // Lista tipada solo con nombres de usuario (como en el código original)
    private final List<String> registeredUsernames = new ArrayList<>();

    // Mensaje de error público (mantenido por compatibilidad con Main)
    public String lastErrorMessage = "";

    /**
     * Intenta registrar un usuario con los datos proporcionados.
     *
     * @param username nombre de usuario
     * @param password contraseña
     * @param email    correo electrónico
     * @return true si se registró correctamente
     */
    public boolean registerUser(String username, String password, String email) {
        // 1. Validación de username
        if (!isValidUsername(username)) {
            return false;
        }

        // 2. Validación de contraseña
        if (!isValidPassword(password)) {
            return false;
        }

        // 3. Validación de email
        if (!isValidEmail(email)) {
            return false;
        }

        // 4. Evitar usuarios duplicados
        if (registeredUsernames.contains(username)) {
            lastErrorMessage = "El nombre de usuario ya está registrado.";
            LOGGER.warning("Intento de registro duplicado: " + username);
            return false;
        }

        // 5. Guardar usuario
        try {
            saveUser(username);
            LOGGER.info("Usuario registrado correctamente: " + username);
            return true;
        } catch (IllegalArgumentException e) {
            lastErrorMessage = e.getMessage();
            LOGGER.warning("Error de validación al guardar: " + e.getMessage());
            return false;
        } catch (Exception e) {
            lastErrorMessage = "Error interno del servidor al registrar el usuario.";
            LOGGER.log(Level.SEVERE, "Excepción inesperada al guardar usuario: " + username, e);
            return false;
        }
    }

    private boolean isValidUsername(String username) {
        if (username == null) {
            lastErrorMessage = "El nombre de usuario no puede ser nulo.";
            return false;
        }
        if (username.trim().isEmpty()) {
            lastErrorMessage = "El nombre de usuario no puede estar vacío.";
            return false;
        }
        return true;
    }

    private boolean isValidPassword(String password) {
        if (password == null) {
            lastErrorMessage = "La contraseña no puede ser nula.";
            return false;
        }
        if (password.length() < MIN_PASSWORD_LENGTH) {
            lastErrorMessage = "La contraseña debe tener al menos " + MIN_PASSWORD_LENGTH + " caracteres.";
            return false;
        }
        return true;
    }

    private boolean isValidEmail(String email) {
        if (email == null || !email.matches(EMAIL_REGEX)) {
            lastErrorMessage = "El correo electrónico no tiene un formato válido.";
            return false;
        }
        return true;
    }

    /**
     * Simula el guardado del usuario (solo guarda el username como en el original).
     */
    private void saveUser(String username) throws Exception {
        Objects.requireNonNull(username, "Username no puede ser nulo al guardar");

        if ("error".equalsIgnoreCase(username)) {
            throw new IllegalArgumentException("Nombre de usuario no permitido.");
        }

        registeredUsernames.add(username);
    }

    /**
     * Método mejorado que reemplaza al confuso x(String)
     */
    public int getStringLength(String s) {
        return s == null ? -1 : s.length();
    }

    // Getter opcional para inspeccionar usuarios registrados (útil en pruebas)
    public List<String> getRegisteredUsernames() {
        return List.copyOf(registeredUsernames);
    }
}