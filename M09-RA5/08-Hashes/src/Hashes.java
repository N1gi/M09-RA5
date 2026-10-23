
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HexFormat;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class Hashes {

    private int npass = 0;

    public String getSHA512AmbSalt(String pw, String salt) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        md.update(salt.getBytes(StandardCharsets.UTF_8));

        byte[] bytes = md.digest(pw.getBytes(StandardCharsets.UTF_8));

        HexFormat hex = HexFormat.of();
        String hash = hex.formatHex(bytes);

        npass++;
        return hash;
    }

    public String getPBKDF2AmbSalt(String pw, String salt) throws Exception {
        char[] password = pw.toCharArray();
        byte[] saltBytes = salt.getBytes(StandardCharsets.UTF_8);

        int iterations = 65536;
        int keyLength = 512;

        PBEKeySpec spec =
                new PBEKeySpec(password, saltBytes, iterations, keyLength);

        try {
            SecretKeyFactory skf =
                    SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");

            byte[] bytes = skf.generateSecret(spec).getEncoded();

            HexFormat hex = HexFormat.of();
            String hash = hex.formatHex(bytes);

            npass++;
            return hash;
        } finally {
            spec.clearPassword();
        }
    }

    public String forcaBruta(String alg, String hash, String salt) throws Exception {
        npass = 0;

        if (!alg.equals("SHA-512") && !alg.equals("PBKDF2")) {
            return "Algoritme no implementat";
        }

        char[] charSet = "abcdefABCDEF1234567890!".toCharArray();
        char[] password = new char[6];

        for (int i = 0; i < charSet.length; i++) {
            password[0] = charSet[i];

            if (provar(password, 1, alg, hash, salt)) {
                return new String(password, 0, 1);
            }

            for (int j = 0; j < charSet.length; j++) {
                password[1] = charSet[j];

                if (provar(password, 2, alg, hash, salt)) {
                    return new String(password, 0, 2);
                }

                for (int k = 0; k < charSet.length; k++) {
                    password[2] = charSet[k];

                    if (provar(password, 3, alg, hash, salt)) {
                        return new String(password, 0, 3);
                    }

                    for (int m = 0; m < charSet.length; m++) {
                        password[3] = charSet[m];

                        if (provar(password, 4, alg, hash, salt)) {
                            return new String(password, 0, 4);
                        }

                        for (int n = 0; n < charSet.length; n++) {
                            password[4] = charSet[n];

                            if (provar(password, 5, alg, hash, salt)) {
                                return new String(password, 0, 5);
                            }

                            for (int o = 0; o < charSet.length; o++) {
                                password[5] = charSet[o];

                                if (provar(password, 6, alg, hash, salt)) {
                                    return new String(password);
                                }
                            }
                        }
                    }
                }
            }
        }
        return "Password no trobat";
    }

    public boolean provar(char[] password, int longitud, String alg, String hash, String salt) throws Exception {
        String pw = new String(password, 0, longitud);
        String resultat;

        if (alg.equals("SHA-512")) {
            resultat = getSHA512AmbSalt(pw, salt);
        } else {
            resultat = getPBKDF2AmbSalt(pw, salt);
        }

        return resultat.equals(hash);
    }

    public String getInterval(long t1, long t2) {
        long temps = t2 - t1;

        long dies = temps / 86400000;
        temps %= 86400000;

        long hores = temps / 3600000;
        temps %= 3600000;

        long minuts = temps / 60000;
        temps %= 60000;

        long segons = temps / 1000;
        long millis = temps % 1000;

        return dies + " dies / " + hores + " hores / "
                + minuts + " minuts / " + segons
                + " segons / " + millis + " millis";
    }

    public static void main(String[] args) throws Exception {
        String salt = "qpoweiruañslkdfjz";
        String pw = "aaabF!";

        Hashes h = new Hashes();

        String[] aHashes = {
            h.getSHA512AmbSalt(pw, salt),
            h.getPBKDF2AmbSalt(pw, salt)
        };

        String pwTrobat = null;
        String[] algorismes = {"SHA-512", "PBKDF2"};

        for (int i = 0; i < aHashes.length; i++) {
            System.out.printf("==============================\n");
            System.out.printf("Algorisme: %s\n", algorismes[i]);
            System.out.printf("Hash: %s\n", aHashes[i]);
            System.out.printf("------------------------------\n");
            System.out.printf("-- Inici de força bruta ---\n");

            long t1 = System.currentTimeMillis();
            pwTrobat = h.forcaBruta(algorismes[i], aHashes[i], salt);
            long t2 = System.currentTimeMillis();

            System.out.printf("Pass : %s\n", pwTrobat);
            System.out.printf("Provats: %d\n", h.npass);
            System.out.printf("Temps : %s\n", h.getInterval(t1, t2));
            System.out.printf("------------------------------\n\n");
        }
    }
}
