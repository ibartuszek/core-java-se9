package tutorial.ch01.sec01;

import java.math.BigDecimal;
import java.math.BigInteger;

public class BigNumberExample {

    public static void main(String[] args) {
        BigInteger n = BigInteger.valueOf(876543210123456789L);
        BigInteger k = new BigInteger("876543210123456789");
        BigInteger r = BigInteger.valueOf(5L).multiply(n.add(k));
        System.out.println(r);
        System.out.println(BigDecimal.valueOf(1234567890123456789L, 2));
    }

}
