package com.example.terceirotrabalho.cryptography;

import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.util.Base64;

import java.nio.charset.StandardCharsets;
import java.security.KeyStore;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

public class CryptographyUtils {
    private static final String ANDROID_KEYSTORE = "AndroidKeyStore";
    private static final String KEY_ALIAS = "SeuAliasChave";

    public static String encryptPassword(String password) {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, ANDROID_KEYSTORE);
            KeyGenParameterSpec.Builder builder = new KeyGenParameterSpec.Builder(KEY_ALIAS,
                    KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(false)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7);
            keyGenerator.init(builder.build());

            SecretKey secretKey = keyGenerator.generateKey();

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            byte[] encryptedBytes = cipher.doFinal(password.getBytes());
            return Base64.encodeToString(encryptedBytes, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String decryptPassword(String encryptedPassword) {
        try {
            KeyStore keyStore = KeyStore.getInstance(ANDROID_KEYSTORE);
            keyStore.load(null);
            SecretKey secretKey = (SecretKey) keyStore.getKey(KEY_ALIAS, null);

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");

            // Recupera o IV dos primeiros 16 bytes do array cifrado
            byte[] encryptedBytes = Base64.decode(encryptedPassword, Base64.DEFAULT);

            if (encryptedBytes.length < 16) {
                // Verifica se há pelo menos 16 bytes para o IV
                throw new IllegalArgumentException("Dados cifrados inválidos");
            }

            byte[] iv = Arrays.copyOfRange(encryptedBytes, 0, 16);

            // Inicializa o Cipher com o IV
            cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iv));

            // Descriptografa os bytes após o IV
            byte[] decryptedBytes = cipher.doFinal(Arrays.copyOfRange(encryptedBytes, 16, encryptedBytes.length));

            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
