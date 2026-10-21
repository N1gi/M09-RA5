package iticbcn.xifratge;

public class XifradorRotX implements Xifrador {
    public static final String alfabet = "aáàbcçdeéèfghiíìïjklmnñoóòpqrstuúùüvwxyz";
    public static final char[] minuscules = alfabet.toCharArray();
    public static final char[] majuscules = alfabet.toUpperCase().toCharArray();

    @Override
    public TextXifrat xifra(String msg, String clau) throws ClauNoSuportada {
        int index;
        try {
            index = Integer.parseInt(clau);
        } catch (NumberFormatException e) {
            throw new ClauNoSuportada("Clau de RotX ha de ser un sencer de 0 a 40");
        }
        if (index < 0 || index > 40) {
            throw new ClauNoSuportada("Clau de RotX ha de ser un sencer de 0 a 40");
        }
        return new TextXifrat(xifraRotX(msg, index).getBytes());
    }

    @Override
    public String desxifra(TextXifrat xifrat, String clau) throws ClauNoSuportada {
        int index;
        try {
            index = Integer.parseInt(clau);
        } catch (NumberFormatException e) {
            throw new ClauNoSuportada("Clau de RotX ha de ser un sencer de 0 a 40");
        }
        if (index < 0 || index > 40) {
            throw new ClauNoSuportada("Clau de RotX ha de ser un sencer de 0 a 40");
        }
        return desxifraRotX(xifrat.toString(), index);
    }

    public String xifraRotX(String msg, int index) {
        return desplaca(msg, index);
    }

    public String desxifraRotX(String msg, int index) {
        return desplaca(msg, -index);
    }

    // Desplaça cada lletra "desp" posicions (admet valors negatius i grans)
    private String desplaca(String msg, int desp) {
        int n = minuscules.length;
        String txtF = "";
        for (int i = 0; i < msg.length(); i++) {
            char c = msg.charAt(i);
            boolean trobat = false;
            for (int x = 0; x < n; x++) {
                if (c == minuscules[x]) {
                    txtF = txtF + minuscules[Math.floorMod(x + desp, n)];
                    trobat = true;
                    break;
                }
                if (c == majuscules[x]) {
                    txtF = txtF + majuscules[Math.floorMod(x + desp, n)];
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

    public void forcaBrutaRotX(String msg) {
        for (int index = 0; index < alfabet.length(); index++) {
            System.out.printf("(%d)->%s%n", index, desxifraRotX(msg, index));
        }
    }
}