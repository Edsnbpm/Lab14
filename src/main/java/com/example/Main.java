package com.example;

/**
 * Clase principal con ejemplos de uso del servicio de registro.
 * <p>
 * Esta es una clase de utilidad con método main; no debe instanciarse.
 * </p>
 */
public final class Main {

    /** Constructor privado para evitar instanciación. */
    private Main() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Método principal de la aplicación.
     *
     * @param args argumentos de línea de comandos (no usados)
     */
    public static void main(final String[] args) {
        UserRegistrationService service = new UserRegistrationService();

        service.registerUser("juan", "123", "juan@correo");
        System.out.println(service.lastErrorMessage);

        service.registerUser(null, "12345678", "correo-sin-arroba");
        System.out.println(service.lastErrorMessage);

        service.registerUser("error", "12345678", "error@correo.com");
        System.out.println(service.lastErrorMessage);
    }
}