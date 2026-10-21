package iticbcn.xifratge;

import java.util.ArrayList;
import java.util.Collections;

public class XifradorMonoalfabetic implements Xifrador {
    public static final String alfabet = "aáàbcçdeéèfghiíìïjklmnñoóòpqrstuúùüvwxyz";
    public static final char[] majuscules = alfabet.toUpperCase().toCharArray();
    public final char[] alfabetP;

    public XifradorMonoalfabetic() {
        alfabetP = permutaAlfabet(majuscules);
    }

    @Override
    public TextXifrat xifra(String msg, String clau) throws ClauNoSuportada {
        if (clau != null) {
            throw new ClauNoSuportada("Xifratxe monoalfabètic no suporta clau != null");
        }
        return new TextXifrat(xifraMonoAlfa(msg).getBytes());
    }

    @Override
    public String desxifra(TextXifrat xifrat, String clau) throws ClauNoSuportada {
        if (clau != null) {
            throw new ClauNoSuportada("Xifratxe monoalfabètic no suporta clau != null");
        }
        return desxifraMonoAlfa(xifrat.toString());
    }

    public char[] permutaAlfabet(char[] alfabet) {
        ArrayList<Character> list = new ArrayList<>();
        for (char c : alfabet) {
            list.add(c);
        }
        Collections.shuffle(list);
        char[] perm = new char[list.size()];
        for (int i = 0; i < perm.length; i++) {
            perm[i] = list.get(i);
        }
        return perm;
    }

    public String xifraMonoAlfa(String text) {
        String txtF = "";
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            boolean trobat = false;
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
            boolean trobat = false;
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