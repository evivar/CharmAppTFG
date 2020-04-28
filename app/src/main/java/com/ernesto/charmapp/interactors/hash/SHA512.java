package com.ernesto.charmapp.interactors.hash;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public abstract class SHA512 {

    private static SHA512 instance;

    public static SHA512 getInstance() {
        return instance;
    }

    /**
     * Método que hashea la contraseña del usuario mediante el algoritmo SHA512
     *
     * @param password Contraseña introducida por el usuario
     * @return Contraseña hasheada
     * @throws NoSuchAlgorithmException Se lanza si el algoritmo no estuvera disponible
     */
    public static String hashPassword(String password) throws NoSuchAlgorithmException {
        String hashedPassword = "";

        MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");

        messageDigest.update(password.getBytes(StandardCharsets.UTF_8));
        byte[] digest = messageDigest.digest();
        hashedPassword = String.format("%064x", new BigInteger(1, digest));
        return hashedPassword;
    }

}
