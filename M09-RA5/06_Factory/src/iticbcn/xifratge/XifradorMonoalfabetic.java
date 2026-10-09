package iticbcn.xifratge;

import java.util.ArrayList;
import java.util.Collections;

public class XifradorMonoalfabetic implements Xifrador {
    public static final String alfabet = "aáàbcçdeéèfghiíìïjklmnñoóòpqrstuúùüvwxyz";
    public static final char[] majuscules = alfabet.toUpperCase().toCharArray();
    public final char[] alfabetP = permutaAlfabet(majuscules);

    public static void main(String[] args) {
        XifradorMonoalfabetic xifrador = new XifradorMonoalfabetic();

        String tests[] = {"Test 01 àrbitre, coixí, Perímetre", "Test 02 Taüll, DÍA, año", "Test 03 Peça, Òrrius, Bòvila"};
        String testX[] = new String[tests.length];

        xifrador.mostraAlfabet(majuscules);
        xifrador.mostraAlfabet(xifrador.alfabetP);

        System.out.println("Xifratge:");
        for (int i = 0; i < tests.length; i++) {
            testX[i] = xifrador.xifraMonoAlfa(tests[i]);
            System.out.printf("%-23s -> %s%n", tests[i], testX[i]);
        }

        System.out.println("Desxifratge:");
        for (String test : testX) {
            System.out.printf("%-23s -> %s%n", test, xifrador.desxifraMonoAlfa(test));
        }
    }

    public char[] permutaAlfabet(char[] alfabet) {
        ArrayList<Character> list = new ArrayList<>();

        for (char c : alfabet) {
            list.add(c);
        }

        Collections.shuffle(list);

        char[] alfabetP = new char[list.size()];

        for (int i = 0; i < alfabetP.length; i++) {
            alfabetP[i] = list.get(i);
        }

        return alfabetP;
    }

    public void mostraAlfabet(char[] alfabet) {
        for (int i = 0; i < alfabet.length; i++) {
            char c = alfabet[i];

            if (i == alfabet.length - 1) {
                System.out.printf("%c%n", c);
            } else {
                System.out.printf("%c ", c);
            }
        }
    }

    public String xifraMonoAlfa(String text) {
        String txtF = "";

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            Boolean trobat = false;

            for (int x = 0; x < majuscules.length; x++) {
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

    public String desxifraMonoAlfa(String text) {
        String txtF = "";

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            Boolean trobat = false;

            for (int x = 0; x < alfabetP.length; x++) {
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