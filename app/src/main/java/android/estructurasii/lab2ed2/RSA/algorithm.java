package android.estructurasii.lab2ed2.RSA;

import java.math.BigInteger;

public class algorithm {
    BigInteger[] keys;

    public algorithm() {
        keys = new BigInteger[2];
    }
    public void generate_key (int p, int q, boolean inout ) {
        BigInteger n = new BigInteger(String.valueOf( p * q));
        keys[0] = n;
        int phi = (p-1) *(q-1);
        int e = chooseE(phi);
        if(inout) {
            keys[1] = new BigInteger(String.valueOf(e));
        } else {
            BigInteger x = new BigInteger(String.valueOf(e));
            BigInteger d = x.modInverse(new BigInteger(String.valueOf(phi)));
            keys[1] = d;
        }

    }
    public int cipher (int c) {
        int result = 0;
        BigInteger partial = new BigInteger(String.valueOf(c));
        result = partial.modPow(keys[1], keys[0]).intValue();
        return result;
    }
    public int decipher(int s) {
        int result = 0;
        BigInteger partial = new BigInteger(String.valueOf(s));
        result = partial.modPow(keys[1], keys[0]).intValue();
        return result;
    }
    boolean coprimos(int a, int b) {
        boolean verify = false;
        if(mcd(a,b) == 1) {
            verify = true;
        }
        return verify;
    }
    int chooseE(int phi) {
        int e = 0;
        boolean found = false;
        int counter = 2;
        while(!found && counter <phi ) {
            boolean isIt = coprimos(counter, phi);
            if(isIt) {
                boolean verify = isPrime(counter);
                if(verify && counter>10) {
                    e = counter;
                    found = true;
                }
            }
            counter++;

        }
        return e;
    }

   public boolean isPrime(int n) {
        int counter = 0;
        for(int i = 1; i <=n; i++) {
            if(n%i == 0) {
                counter++;
            }
        }
        if(counter==2) {
            return true;
        } else {
            return false;
        }
    }

    int mcd(int a, int b) {
        if(b == 0) {
            return a;
        }else {
            return mcd(b, a%b);
        }


    }
}
