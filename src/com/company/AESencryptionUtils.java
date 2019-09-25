package com.company;

import java.security.SecureRandom;
import java.util.Arrays;


public class AESencryptionUtils {

    public static boolean detectECB(byte[] ciphertext)
    {
        byte[][] data = new byte[ciphertext.length / 16][16];
        int pos = 0;
        for(int j=0; j<ciphertext.length/16; j++)
        {
            for(int l=0; l<16; l++)
                data[j][l] = ciphertext[pos++];
        }
        boolean ecb = false;
        for(int i=0; i<data.length; i++) {
            if(ecb) break;
            for(int j=0; j<data.length; j++) {
                if(i == j) continue;
                if(Arrays.equals(data[i], data[j])) {
                    ecb = true;
                    break;
                }
            }
        }
        return ecb;
    }

    public static byte[] getBlock(byte[] data, int size, int block)
    {
        return getBlock(data, size, block, false);
    }

    public static byte[] getBlock(byte[] data, int size, int block, boolean pad)
    {
        byte[] ret = new byte[size];
        for(int i=0; i<size; i++)
        {
            if(block * size + i < data.length)
                ret[i] = data[block * size + i];
            else {
                ret[i] = 0;
            }
        }
        return ret;
    }

    public static void setBlock(byte[] cipher, byte[] data, int size, int block)
    {
        for(int i=0; i<data.length; i++)
        {
            cipher[i + (block * data.length)] = data[i];
        }
    }

    /**
     * Generate a cryptografically secure random IV
     */
    public static byte[] generateRandomIV()
    {
        byte[] ret = new byte[16];
        SecureRandom rng = new SecureRandom();
        rng.nextBytes(ret);
        return ret;
    }

}