import java.math.BigInteger;
import java.util.ArrayList;

public class Pow {
// добавить еще отрицательную степень
//    public BigInteger go(BigInteger bigint, BigInteger m, Integer pow) {
//        System.out.println("Задание 4. Пункт 1");
//        if (pow == 0) {
//            BigInteger c = bigint.mod(m);
//            System.out.println(c);
//        } else {
//            BigInteger multi = new BigInteger(String.valueOf(1));
//            for (int i = 1; i <= pow; i++) {
//                multi = bigint.multiply(multi);
//            }
//            System.out.println("Большое число " + bigint + " в степени " + pow + " == " + multi);
//            BigInteger modpow = multi.mod(m);
//            System.out.println("Возведение числа " + bigint + " в степень " + pow + " по модулю " + m + " == " + modpow);
//        }
        //перевод степени в двоичную систему
//    }

    public void FpowMod(BigInteger a, BigInteger b, BigInteger n){
        BigInteger res = BigInteger.ONE;
        BigInteger ai = a;
        ArrayList<BigInteger> n_bin = toBin(b);

        for (int i = 0; i < (n_bin.size()); i++) {

            if (n_bin.get(i).compareTo(BigInteger.valueOf(1)) == 0 ) {
                res = (res.multiply(ai)).mod(n);

            }

            ai = (ai.multiply(ai)).mod(n);


        }
        System.out.println(res);
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