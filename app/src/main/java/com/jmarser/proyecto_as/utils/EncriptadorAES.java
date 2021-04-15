package com.jmarser.proyecto_as.utils;

import android.os.Build;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class EncriptadorAES {

    private static final String UNICODE = "UTF-8";
    private static final String HASH_ALGORITM = "SHA-256";
    private static final String ESQUEMA_CIFRADO = "AES";

    /**
     * Crea la clave de encriptación que usará internamente nuestra aplicación
     *
     * @param clave clave que usaremos para generar la clave de
     * encriptación/desencriptación
     * @return clave de encriptación/desencriptación
     */
    private SecretKeySpec crearClave(String clave){

        try{
            byte[] cadena = clave.getBytes(UNICODE);
            MessageDigest md = MessageDigest.getInstance(HASH_ALGORITM);
            md.update(cadena, 0, cadena.length);
            return new SecretKeySpec(md.digest(), ESQUEMA_CIFRADO);

        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Realiza la encriptación AES sobre el texto que deseemos usando la clave
     * indicada
     *
     * @param datos, cadena que deseamos encriptar.
     * @param claveEncriptacion, clave con la que deseamos encriptar el texto.
     * @return cadena encriptada
     */
    public String encriptar(String datos, String claveEncriptacion){

        try{
            SecretKeySpec sks = crearClave(claveEncriptacion);
            Cipher cipher = Cipher.getInstance(ESQUEMA_CIFRADO);
            cipher.init(Cipher.ENCRYPT_MODE, sks);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                return Base64.getEncoder().encodeToString(cipher.doFinal(datos.getBytes(UNICODE)));
            }else{
                return new String(cipher.doFinal(datos.getBytes(UNICODE)));
            }
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | BadPaddingException | UnsupportedEncodingException | IllegalBlockSizeException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Realiza la desencriptación del texto que le pasamos encriptado con AES
     * usando la misma clave que se utilizó para realizar la encriptación.
     *
     * @param datos, cadena encriptada en AES que se desea desencriptar.
     * @param claveEncriptacion, clave que usaremos para desencriptar, debe ser la misma que
     * se uso para encriptar.
     * @return texto desencriptado.
     */
    public String desencriptar(String datos, String claveEncriptacion){

        try{
            SecretKeySpec sks = crearClave(claveEncriptacion);
            Cipher cipher = Cipher.getInstance(ESQUEMA_CIFRADO);
            cipher.init(Cipher.DECRYPT_MODE, sks);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                return new String(cipher.doFinal(Base64.getDecoder().decode(datos)), UNICODE);
            }else{
                return new String(cipher.doFinal(datos.getBytes(UNICODE)));
            }
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | BadPaddingException | UnsupportedEncodingException | IllegalBlockSizeException e) {
            e.printStackTrace();
            return null;
        }
    }
}
