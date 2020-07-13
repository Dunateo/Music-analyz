package spectrogram;

import static java.lang.Math.*;
import static java.lang.Math.sin;

public class FastFourierTransform {

    public FastFourierTransform(){}

    //Permutation d'inversion de bits
    public static int bitReverse(int n, int bits) {
        int reversedN = n;
        int count = bits - 1;

        n >>= 1;
        while (n > 0) {
            reversedN = (reversedN << 1) | (n & 1);
            count--;
            n >>= 1;
        }

        return ((reversedN << count) & ((1 << bits) - 1));
    }

    //La Transformation de Fourier rapide
    public Complex[] fft(Complex[] buffer) {
        Complex even = null;
        Complex odd = null;
        Complex exp;
        int evenIndex = 0;
        int oddIndex = 0;
        // vérifie la validité des indices
        boolean b = (evenIndex > 0 && evenIndex < buffer.length) && (oddIndex > 0 && oddIndex < buffer.length);


        int bits = (int) (log(buffer.length) / log(2));
        for (int j = 1; j < buffer.length / 2; j++) {

            int swapPos = bitReverse(j, bits);
            Complex temp = buffer[j];
            buffer[j] = buffer[swapPos];
            buffer[swapPos] = temp;
        }

        for (int N = 2; N <= buffer.length; N <<= 1) {
            for (int i = 0; i < buffer.length; i += N) {
                for (int k = 0; k < N / 2; k++) {

                    evenIndex = i + k;
                    oddIndex = i + k + (N / 2);

                    if (b){
                        //Evite => Exception in thread "main" java.lang.ArrayIndexOutOfBoundsException:
                        even = buffer[evenIndex];
                        odd = buffer[oddIndex];
                    }


                    double term = (-2 * PI * k) / (double) N;
                    if (odd != null) {
                        //Evite => Exception in thread "main" java.lang.NullPointerException
                        exp = (new Complex(cos(term), sin(term)).mult(odd));
                        if (b) {
                            buffer[evenIndex] = even.add(exp);
                            buffer[oddIndex] = even.sub(exp);
                        }
                    }
                }
            }
        }
        return buffer;
    }
}
