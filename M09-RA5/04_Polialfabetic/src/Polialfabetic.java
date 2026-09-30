import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Polialfabetic {
    public static final String alfabet = "aáàbcçdeéèfghiíìïjklmnñoóòpqrstuúùüvwxyz";
    public static final char[] majuscules = alfabet.toUpperCase().toCharArray();
    public static char[] alfabetP = new char[majuscules.length];
    public static final int clauSecreta = 12;
    public static Random random;  
    public static void main(String[] args) {
        String msgs[] = {"Test 01 àrbitre, coixí, Perímetre",
                        "Test 02 Taüll, DÍA, año",
                        "Test 03 Peça, Òrrius, Bòvila"};
        String msgsXifrats[] = new String[msgs.length];

        System.out.println("Xifratge:\n----------");
        for (int i = 0; i < msgs.length; i++) {
            initRandom(clauSecreta);
            msgsXifrats[i] = xifraPoliAlfa(msgs[i]);
            System.out.printf("%-34s -> %s%n", msgs[i], msgsXifrats[i]);
        }

        System.out.println("Desxifratge:\n-----------");
        for (int i = 0; i < msgs.length; i++) {
            initRandom(clauSecreta);
            String msg = desxifraPoliAlfa(msgsXifrats[i]);
            System.out.printf("%-34s -> %s%n", msgsXifrats[i], msg);
        }
    }
    public static void initRandom(int clauSecreta) {
        random = new Random(clauSecreta);
    }

    public static void permutaAlfabet() {
        ArrayList<Character> list = new ArrayList<>();

        for (char c : majuscules) {
            list.add(c);
        }

        Collections.shuffle(list,random);

        for (int i = 0; i<alfabetP.length; i++) {
            alfabetP[i] = list.get(i);
        }
    }

    public static String xifraPoliAlfa (String msg) {
        String txtF = "";
        for (int i = 0; i<msg.length(); i++) {
            permutaAlfabet();
            char c = msg.charAt(i);
            Boolean trobat = false;
            for (int x = 0; x<majuscules.length; x++) {
                if (c == majuscules[x]) {
                    txtF = txtF + alfabetP[x];
                    trobat = true;
                    break;
                }
                if (c == Character.toLowerCase(majuscules[x])) {
                    txtF = txtF + Character.toLowerCase(alfabetP[x]);
                    trobat = true;
                    break;
                }
            }
            if (!trobat) {
                txtF = txtF + c;
            }
        }
        return txtF;
    }

    public static String desxifraPoliAlfa (String msgXifrat) {
        String txtF = "";
        for (int i = 0; i<msgXifrat.length(); i++) {
            permutaAlfabet();
            char c = msgXifrat.charAt(i);
            Boolean trobat = false;
            for (int x = 0; x<alfabetP.length; x++) {
                if (c == alfabetP[x]) {
                    txtF = txtF + majuscules[x];
                    trobat = true;
                    break;
                }
                if (c == Character.toLowerCase(alfabetP[x])) {
                    txtF = txtF + Character.toLowerCase(majuscules[x]);
                    trobat = true;
                    break;
                }
            }
            if (!trobat) {
                txtF = txtF + c;
            }
        }
        return txtF;
    }
}
