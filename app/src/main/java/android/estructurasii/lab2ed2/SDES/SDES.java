package android.estructurasii.lab2ed2.SDES;

public class SDES {
    /*
     * Constantes
     */
    private final int[] P10 = new int[] {5,4,2,8,9,7,1,0,3,6};
    private final int[] P8 = new int[] {0,9,5,6,2,7,3,4};
    private final int[] P4 = new int[]{3,1,0,2};
    private final int[] IP = new int[] {2,4,5,7,1,6,3,0};
    private  final String[][] S0 = new String[][] {{"01","00","11","10"},{"11","10","01","00"},{"00","10","01","11"},
            {"11","01","11","10"}};
    private final String[][] S1 = new String[][] {{"00","01","10","11"},{"10","00","01","11"},{"11","00","01","00"},
            {"10","01","00","11"}};
    //variables
    private String[] keys;
    /*
     Metodos
     */
    public SDES() {
        keys  = new String[2];
    }

    String permute10(String init) {
        StringBuilder str = new StringBuilder();
        for(int i = 0; i<P10.length;i++) {
            str.append(init.charAt(P10[i]));
        }

        return str.toString();
    }
    String leftSingleShift(String in_) {
        String lfs = in_ + "0";
        StringBuilder str = new StringBuilder(lfs.substring(1, lfs.length()-1));
        str.append(lfs.charAt(0));
        return str.toString();
    }
    String permute8(String enter) {
        enter = permute10(enter);
        StringBuilder str = new StringBuilder();
        for(int i = 0; i<P8.length;i++) {
            str.append(enter.charAt(P8[i]));
        }
        return str.toString();
    }
    String leftDoubleShift(String in_) {
        in_ = in_ + "00";
        StringBuilder str = new StringBuilder();
        str.append(in_.substring(2, in_.length()-2));
        str.append(in_.charAt(0));
        str.append(in_.charAt(1));
        return str.toString();
    }
    String XoR(String c1, String c2) {
        StringBuilder str = new StringBuilder();
        for(int i = 0; i < c1.length(); i++) {
            if(c1.charAt(i) == c2.charAt(i)) {
                str.append("0");
            }else {
                str.append("1");
            }
        }
        return str.toString();
    }
    String Initialpermute(String word) {
        StringBuilder str = new StringBuilder();
        for(int i = 0; i< IP.length; i++) {
            str.append(word.charAt(IP[i]));
        }
        return str.toString();
    }
    String Expand_Permute(String p2) {
        int[] p1 = new int[] {0,2,3,1};
        int[] p_2 = new int[] {3,1,0,2};
        StringBuilder str = new StringBuilder();
        for (int i = 0; i<p1.length; i++) {
            str.append(p2.charAt(p1[i]));
        }
        for(int i = 0; i< p_2.length;i++) {
            str.append(p2.charAt(p_2[i]));
        }
        return str.toString();
    }
    String permute4(String toP) {
        StringBuilder str = new StringBuilder();
        for(int i = 0; i<P4.length; i++) {
            str.append(toP.charAt(P4[i]));
        }
        return str.toString();
    }
    String Switch(String word) {
        StringBuilder str = new StringBuilder();
        str.append(word.substring(4,word.length()));
        str.append(word.substring(0,4));
        return str.toString();
    }
    String S0result (String s) {
        String r = String.valueOf(new char[] {s.charAt(0),s.charAt(s.length()-1)});
        int row = Integer.valueOf(r, 2);
        String c = String.valueOf(new char[] {s.charAt(1), s.charAt(s.length()-2)});
        int column = Integer.valueOf(c,2);
        return S0[row][column];
    }
    String S1result (String s) {
        String r = String.valueOf(new char[] {s.charAt(0),s.charAt(s.length()-1)});
        int row = Integer.valueOf(r, 2);
        String c = String.valueOf(new char[] {s.charAt(1), s.charAt(s.length()-2)});
        int column = Integer.valueOf(c,2);
        return S1[row][column];
    }
    String Inversepermute(String current) {
        char[] resultado = new char[current.length()];
        for(int i = 0; i<IP.length; i++) {
            resultado[IP[i]] = current.charAt(i);
        }
        return String.valueOf(resultado);
    }

    /*
     * M�todos Publicos
     */

    public void GenerateKeys(String masterkey) {
        masterkey = permute10(masterkey);
        String ls1 = masterkey.substring(0, 5);
        String ls2 = masterkey.substring(5, masterkey.length());
        StringBuilder str = new StringBuilder();
        ls1 = leftSingleShift(ls1);
        str.append(ls1);
        ls2 = leftSingleShift(ls2);
        str.append(ls2);
        keys[0] = permute8(str.toString());
        str.setLength(0);
        ls1 = leftDoubleShift(ls1);
        str.append(ls1);
        ls2 = leftDoubleShift(ls2);
        str.append(ls2);
        keys[1] = permute8(str.toString());
    }
    public char cipher(char c) {
        int character =  (int)c;
        String binarychar = Integer.toBinaryString(character);
        if(binarychar.length() <8) {
            int diference = 8 - binarychar.length();
            StringBuilder str = new StringBuilder();
            for(int i = 0; i<diference; i++) {
                str.append("0");
            }
            str.append(binarychar);
            binarychar = str.toString();
        }
        binarychar = Initialpermute(binarychar);
        String part1 = binarychar.substring(0,4);
        String part2 = binarychar.substring(4, binarychar.length());
        String partTowork = part2;
        //Trabajando primer mitad del algoritmo
        partTowork = Expand_Permute(partTowork);
        partTowork = XoR(partTowork, keys[0]);
        String subpart1 = partTowork.substring(0,4);
        String subpart2 = partTowork.substring(4,partTowork.length());
        StringBuilder str = new StringBuilder();
        str.append(S0result(subpart1));
        str.append(S1result(subpart2));
        partTowork = str.toString();
        partTowork = permute4(partTowork);
        partTowork = XoR(part1, partTowork);
        str.setLength(0);
        binarychar = partTowork.concat(part2);
        //Comienza segunda mitad algoritmo
        binarychar = Switch(binarychar);
        part1 = binarychar.substring(0,4);
        part2 = binarychar.substring(4, binarychar.length());
        partTowork = part2;
        partTowork = Expand_Permute(partTowork);
        partTowork = XoR(partTowork, keys[1]);
        subpart1 = partTowork.substring(0,4);
        subpart2 = partTowork.substring(4,partTowork.length());
        str.append(S0result(subpart1));
        str.append(S1result(subpart2));
        partTowork = str.toString();
        str.setLength(0);
        partTowork = permute4(partTowork);
        partTowork = XoR(part1, partTowork);
        binarychar = partTowork.concat(part2);
        binarychar = Inversepermute(binarychar);
        //resultado
        int r = Integer.valueOf(binarychar, 2);

        return (char)r;
    }

    public char descipher(char c) {
        int character = (int) c;
        String binarychar = Integer.toBinaryString(character);
        if(binarychar.length() <8) {
            int diference = 8 - binarychar.length();
            StringBuilder str = new StringBuilder();
            for(int i = 0; i<diference; i++) {
                str.append("0");
            }
            str.append(binarychar);
            binarychar = str.toString();
        }
        binarychar = Initialpermute(binarychar);
        String part1 = binarychar.substring(0,4);
        String part2 = binarychar.substring(4, binarychar.length());
        String partTowork = part2;
        //Trabajando primer mitad del algoritmo
        partTowork = Expand_Permute(partTowork);
        partTowork = XoR(partTowork, keys[1]);
        String subpart1 = partTowork.substring(0,4);
        String subpart2 = partTowork.substring(4,partTowork.length());
        StringBuilder str = new StringBuilder();
        str.append(S0result(subpart1));
        str.append(S1result(subpart2));
        partTowork = str.toString();
        partTowork = permute4(partTowork);
        partTowork = XoR(part1, partTowork);
        str.setLength(0);
        binarychar = partTowork.concat(part2);
        //Comienza segunda mitad algoritmo
        binarychar = Switch(binarychar);
        part1 = binarychar.substring(0,4);
        part2 = binarychar.substring(4, binarychar.length());
        partTowork = part2;
        partTowork = Expand_Permute(partTowork);
        partTowork = XoR(partTowork, keys[0]);
        subpart1 = partTowork.substring(0,4);
        subpart2 = partTowork.substring(4,partTowork.length());
        str.append(S0result(subpart1));
        str.append(S1result(subpart2));
        partTowork = str.toString();
        str.setLength(0);
        partTowork = permute4(partTowork);
        partTowork = XoR(part1, partTowork);
        binarychar = partTowork.concat(part2);
        binarychar = Inversepermute(binarychar);
        //resultado
        int r = Integer.valueOf(binarychar, 2);

        return (char)r;
    }
}
