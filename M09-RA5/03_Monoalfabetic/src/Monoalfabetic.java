import java.util.ArrayList;
import java.util.Collections;

public class Monoalfabetic {
    public static final String alfabet = "aáàbcçdeéèfghiíìïjklmnñoóòpqrstuúùüvwxyz";
    public static final char[] majuscules = alfabet.toUpperCase().toCharArray();
    public static final char[] abcP = permutaAlfabet(majuscules);
    public static void main(String[] args) {
        String tests[] = {"Test 01 àrbitre, coixí, Perímetre", "Test 02 Taüll, DÍA, año", "Test 03 Peça, Òrrius, Bòvila"};
        String testX[] = new String[tests.length];
        mostraAlfabet(majuscules);
        mostraAlfabet(abcP);

        System.out.println("Xifratge:");
        for (int i = 0; i<tests.length; i++) {
            testX[i] = xifraMonoAlfa(tests[i]);
            System.out.printf("%-23s -> %s%n",tests[i], testX[i]);
        }

        System.out.println("Desxifratge:");
        for (String test: testX) {
            System.out.printf("%-23s -> %s%n",test, desxifraMonoAlfa(test));
        }
    }

    public static char[] permutaAlfabet(char[] abc) {
        ArrayList<Character> list = new ArrayList<>();

        for (char c : abc) {
            list.add(c);
        }

        Collections.shuffle(list);

        char[] abcP = new char[list.size()];
        
        for (int i = 0; i<abcP.length; i++) {
            abcP[i] = list.get(i);
        }
        return abcP;
    }

    public static void mostraAlfabet(char[] abc) {
        for (int i = 0; i<abc.length; i++) {
            char c = abc[i];
            if (i == abc.length - 1) {
                System.out.printf("%c%n",c);
            }
            else {
                System.out.printf("%c ",c);
            }
        }
    }

    public static String xifraMonoAlfa (String text) {
        String txtF = "";
        for (int i = 0; i<text.length(); i++) {
            char c = text.charAt(i);
            Boolean trobat = false;
            for (int x = 0; x<majuscules.length; x++) {
                if (c == majuscules[x]) {
                    txtF = txtF + abcP[x];
                    trobat = true;
                    break;
                }
                if (c == Character.toLowerCase(majuscules[x])) {
                    txtF = txtF + Character.toLowerCase(abcP[x]);
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

    public static String desxifraMonoAlfa (String text) {
        String txtF = "";
        for (int i = 0; i<text.length(); i++) {
            char c = text.charAt(i);
            Boolean trobat = false;
            for (int x = 0; x<abcP.length; x++) {
                if (c == abcP[x]) {
                    txtF = txtF + majuscules[x];
                    trobat = true;
                    break;
                }
                if (c == Character.toLowerCase(abcP[x])) {
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
