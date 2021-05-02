import java.math.BigInteger;
import java.util.ArrayList;
import java.util.function.BiFunction;

public class BinaryExponentiation {
    public static BigInteger Fpow(BigInteger a, BigInteger b){
        BigInteger res = BigInteger.valueOf(1);
        BigInteger ai = a;
        ArrayList<BigInteger> n_bin = toBin(b);

        for (int i = 0; i < (n_bin.size()); i++) {


            if ((n_bin.get(i)).compareTo(BigInteger.valueOf(1)) == 0) {
                res = (res.multiply(ai));
            }
            ai = ai.multiply(ai);
        }
        return res;
    }

    public static BigInteger FpowMod(BigInteger a, BigInteger b, BigInteger n){
        BigInteger res = BigInteger.ONE;
        BigInteger ai = a;
        ArrayList<BigInteger> n_bin = toBin(b);

        for (int i = 0; i < (n_bin.size()); i++) {

             if (n_bin.get(i).compareTo(BigInteger.valueOf(1)) == 0 ) {
                 res = (res.multiply(ai)).mod(n);

             }

             ai = (ai.multiply(ai)).mod(n);


        }
        return res;
    }

    public boolean MillerRabinTest(BigInteger n, int k){
        if ((n.compareTo(BigInteger.valueOf(2))==0) || (n.compareTo(BigInteger.valueOf(3))==0)){
            return true;
        }

        if ((n.compareTo(BigInteger.valueOf(2)) < 0) || ((n.mod(BigInteger.valueOf(2))).compareTo(BigInteger.ZERO)==0)){
            return false;
        }

        for (int i = 0; i < k; i++) {

            BigInteger a = (BigInteger.valueOf((long) Math.random()).multiply(n.divide(BigInteger.valueOf(2))).add(BigInteger.valueOf(2)));
            ArrayList<BigInteger> b = toBin(n.subtract(BigInteger.ONE));
            BigInteger t = n.subtract(BigInteger.ONE);

            int s = 0;

            while ((t.mod(BigInteger.valueOf(2))).compareTo(BigInteger.ZERO) == 0) {
                t = t.divide(BigInteger.valueOf(2));
                s += 1;
            }


            BigInteger x = FpowMod(a, t, n);



            if(((x.compareTo(BigInteger.ONE)) == 0) || ((x.compareTo(n.subtract(BigInteger.ONE))) == 0)){
                continue;
            }

            for (int j = 0; j < s-1; j++) {
                x = FpowMod(x , BigInteger.valueOf(2), n);

                if((x.compareTo(BigInteger.ONE)) == 0){
                    return false;
                }

                if ((x.compareTo(n.subtract(BigInteger.ONE))) == 0){
                    break;
                }

            }
            if ((x.compareTo(n.subtract(BigInteger.ONE))) != 0){
                return false;
            }
        }
        return true;
    }

    public static ArrayList toBin(BigInteger n){

        ArrayList<BigInteger> r1 = new ArrayList<BigInteger>();

        while (n.compareTo(BigInteger.ZERO)  > 0 ){
            r1.add(n.mod(BigInteger.valueOf(2)));
            n = n.divide(BigInteger.valueOf(2));
        }



        return r1;
    }

}
