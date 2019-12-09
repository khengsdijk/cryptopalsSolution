package com.company;

import java.security.SecureRandom;

public class AESKey {

    protected byte[] keyData;
    protected byte[] expandedKey;
    protected int keySize;
    protected int expandedKeySize;

    /**
     * create the key
     */
    public AESKey(byte[] data) {
        keySize = data.length;
        keyData = data;
        expandKey();
    }

    /**
     * Getter for bytes
     */
    public byte[] keyBytes()
    {
        return keyData;
    }

    /**
     * Get the round key so it can be used for encryption
     */
    protected byte[] getRoundKey(int r)
    {
        byte[] ret = new byte[16];
        for(int i=0; i<16; i++)
            ret[i] = expandedKey[(r * 16) + i];
        return ret;
    }

    /**
     * Rotate bites by 4
     */
    private byte[] rotate(byte[] d)
    {
        byte c = d[0];
        for (int i = 0; i < 3; i++)
            d[i] = d[i+1];
        d[3] = c;
        return d;
    }

    /**
     * Expansion function
     */
    private void expandCore(byte[] word, int iter)
    {
        word = rotate(word);
        for(int i=0; i<4; i++)
        {
            word[i] = (byte)AESLookup.forwardBox(word[i]);
        }
        word[0] = (byte)(word[0] ^ AESLookup.rcon(iter));
    }

    /**
     * expand the key to a round key so it can be used
     */
    private void expandKey()
    {
        int currentSize = keySize;
        int rconIter = 1;
        byte[] tmp = new byte[4];
        switch(keySize)
        {
            case 16: expandedKeySize = 176; break;
            case 24: expandedKeySize = 208; break;
            case 32: expandedKeySize = 240; break;
        }
        expandedKey = new byte[expandedKeySize];

        for(int i=0; i<keySize; i++)
            expandedKey[i] = keyData[i];

        while(currentSize < expandedKeySize)
        {
            // grab the most recent 4 bytes
            for(int i=0; i<4; i++)
                tmp[i] = expandedKey[currentSize - 4 + i];
            if(currentSize % keySize == 0) // every keySize bytes do the core
                expandCore(tmp, rconIter++);

            // for large keys we need the extra s-box step
            if(keySize == 32 && (currentSize % keySize == 16))
            {
                for(int i=0; i<4; i++)
                    tmp[i] = AESLookup.forwardBox(tmp[i]);
            }

            for(int i=0;i<4; i++)
            {
                expandedKey[currentSize] = (byte)(expandedKey[currentSize - keySize] ^ tmp[i]);
                currentSize++;
            }
        }
    }

    /**
     * Get the number of rounds for the keysize
     */
    protected int numRounds()
    {
        switch(keySize)
        {
            case 16: return 10;
            case 24: return 12;
            case 32: return 14;
        }
        return -1;
    }

    /**
     * Generates a random key using crypto secure random
     */
    public static AESKey getRandomKey()
    {
        SecureRandom r = new SecureRandom();
        byte[] keyBytes = new byte[16];
        r.nextBytes(keyBytes);
        AESKey k = new AESKey(keyBytes);
        return k;
    }

}
