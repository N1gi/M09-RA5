package iticbcn.xifratge;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Arrays;
import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class XifradorAES implements Xifrador {
    public static final String ALGORISME_XIFRAT = "AES";
    public static final String ALGORISME_HASH = "SHA-256";
    public static final String FORMAT_AES = "AES/CBC/PKCS5Padding";
    private static final int MIDA_IV = 16;
    private byte[] iv = new byte[MIDA_IV];

    @Override
    public TextXifrat xifra(String msg, String clau) throws ClauNoSuportada {
        try {
            return new TextXifrat(xifraAES(msg, clau));
        } catch (Exception e) {
            System.err.println("Error de xifrat: " + e.getLocalizedMessage());
            System.exit(1);
            return null;
        }
    }

    @Override
    public String desxifra(TextXifrat xifrat, String clau) throws ClauNoSuportada {
        try {
            return desxifraAES(xifrat.getBytes(), clau);
        } catch (Exception e) {
            System.err.println("Error de xifrat: " + e.getLocalizedMessage());
            System.exit(1);
            return null;
        }
    }

    public byte[] xifraAES(String msg, String clau) throws Exception {
        byte[] bytes = msg.getBytes(StandardCharsets.UTF_8);
        iv = generaIv();
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        SecretKeySpec secretKey = generaHash(clau);
        Cipher cipher = Cipher.getInstance(FORMAT_AES);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);
        byte[] msgXifrat = cipher.doFinal(bytes);
        byte[] resultat = new byte[MIDA_IV + msgXifrat.length];
        System.arraycopy(iv, 0, resultat, 0, MIDA_IV);
        System.arraycopy(msgXifrat, 0, resultat, MIDA_IV, msgXifrat.length);
        return resultat;
    }

    public String desxifraAES(byte[] bIvIMsgXifrat, String clau) throws Exception {
        iv = extreureIv(bIvIMsgXifrat);
        byte[] msgXifrat = getBytesXifrats(bIvIMsgXifrat);
        SecretKeySpec secretKey = generaHash(clau);
        Cipher cipher = Cipher.getInstance(FORMAT_AES);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
        byte[] bytesDesxifrats = cipher.doFinal(msgXifrat);
        return new String(bytesDesxifrats, StandardCharsets.UTF_8);
    }

    public byte[] generaIv() {
        new SecureRandom().nextBytes(iv);
        return iv;
    }

    public SecretKeySpec generaHash(String clau) throws Exception {
        MessageDigest digest = MessageDigest.getInstance(ALGORISME_HASH);
        byte[] hash = digest.digest(clau.getBytes(StandardCharsets.UTF_8));
        return new SecretKeySpec(hash, ALGORISME_XIFRAT);
    }

    public byte[] extreureIv(byte[] bIvIMsgXifrat) {
        return Arrays.copyOfRange(bIvIMsgXifrat, 0, MIDA_IV);
    }

    public byte[] getBytesXifrats(byte[] bIvIMsgXifrat) {
        return Arrays.copyOfRange(bIvIMsgXifrat, MIDA_IV, bIvIMsgXifrat.length);
    }
}