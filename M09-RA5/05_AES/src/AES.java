import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Arrays;
import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AES {
    public static final String ALGORISME_XIFRAT = "AES";
    public static final String ALGORISME_HASH = "SHA-256";
    public static final String FORMAT_AES = "AES/CBC/PKCS5Padding";

    private static final int MIDA_IV = 16;
    private static byte[] iv = new byte[MIDA_IV];
    private static final String CLAU = "LaClauSecretaQueVulgis";
    public static void main(String[] args) {
        String msgs[] = {"Lorem ipsum dicet",
                        "Hola Andrés cómo está tu cuñado",
                        "Àgora ïlla Ôtto"};

        for (int i = 0; i < msgs.length; i++) {
            String msg = msgs[i];

            byte[] bXifrats = null;
            String desxifrat = "";
            try {
                bXifrats = xifraAES(msg, CLAU);
                desxifrat = desxifraAES(bXifrats, CLAU);
            } catch (Exception e) {
                System.err.println("Error de xifrat: "
                        + e.getLocalizedMessage());
            }
            System.out.println("--------------------");
            System.out.println("Msg: " + msg);
            System.out.println("Enc: " + new String(bXifrats));
            System.out.println("DEC: " + desxifrat);
        }
    }

public static byte[] xifraAES(String msg, String clau) throws Exception {
        // Obtenir els bytes de l'String
        byte[] bytes = msg.getBytes(StandardCharsets.UTF_8);
        // Genera IvParameterSpec
        iv = generaIv();
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        // Genera hash
        SecretKeySpec secretKey = generaHash(clau);
        // Encrypt.
        Cipher cipher = Cipher.getInstance(FORMAT_AES);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);
        byte[] msgXifrat = cipher.doFinal(bytes);
        // Combinar IV i part xifrada.
        byte[] resultat = new byte[MIDA_IV + msgXifrat.length];
        System.arraycopy(iv, 0, resultat, 0, MIDA_IV);
        System.arraycopy(msgXifrat, 0, resultat, MIDA_IV, msgXifrat.length);
        // return iv+msgxifrat
        return resultat;
    }

    public static String desxifraAES(byte[] bIvIMsgXifrat, String clau) throws Exception {
        // Extreure l'IV.
        iv = extreureIv(bIvIMsgXifrat);
        // Extreure la part xifrada.
        byte[] msgXifrat = getBytesXifrats(bIvIMsgXifrat);
        // Fer hash de la clau
        SecretKeySpec secretKey = generaHash(clau);
        // Desxifrar.
        Cipher cipher = Cipher.getInstance(FORMAT_AES);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
        byte[] bytesDesxifrats = cipher.doFinal(msgXifrat);
        String txtF = new String(bytesDesxifrats, StandardCharsets.UTF_8);
        // return String desxifrat
        return txtF;
    }

    private static byte[] generaIv() {
        new SecureRandom().nextBytes(iv);
        return iv;
    }

    private static SecretKeySpec generaHash(String clau) throws Exception {
        MessageDigest digest = MessageDigest.getInstance(ALGORISME_HASH);
        byte[] hash = digest.digest(clau.getBytes(StandardCharsets.UTF_8));
        return new SecretKeySpec(hash, ALGORISME_XIFRAT);
    }

    private static byte[] extreureIv(byte[] bIvIMsgXifrat) {
        return Arrays.copyOfRange(bIvIMsgXifrat, 0, MIDA_IV);
    }

    private static byte[] getBytesXifrats(byte[] bIvIMsgXifrat) {
        return Arrays.copyOfRange(bIvIMsgXifrat, MIDA_IV, bIvIMsgXifrat.length);
    }

}
