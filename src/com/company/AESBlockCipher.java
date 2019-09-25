package com.company;

public class AESBlockCipher extends AESCipher {

    public static final int BLOCK_MODE_ECB = 0;
    public static final int BLOCK_MODE_CBC = 1;

    int blockmode;

    byte[] _IV;

    public AESBlockCipher(AESKey k) {
        super(k);
        blockmode = BLOCK_MODE_ECB;
    }

    public AESBlockCipher(AESKey k, int bm) {
        super(k);
        blockmode = bm;
    }

    public void setIV(byte[] d)
    {

        _IV = d;
    }

    public void encrypt(byte[] d)
    {
        for(int i=0; i<d.length/BLOCKSIZE; i++)
        {
            byte[] block = AESencryptionUtils.getBlock(d, BLOCKSIZE, i);

            if (blockmode == BLOCK_MODE_CBC)
            {
                if (i == 0) // us IV
                    block = XorUtils.fixed(_IV, block);
                else block = XorUtils.fixed(AESencryptionUtils.getBlock(d, 16, i - 1), block);
            }

            encryptBlock(block);
            AESencryptionUtils.setBlock(d, block, BLOCKSIZE, i);
        }
    }

    public void decrypt(byte[] d)
    {
        for(int i=d.length/BLOCKSIZE - 1; i>=0; i--)
        {
            byte[] block = AESencryptionUtils.getBlock(d, BLOCKSIZE, i);
            decryptBlock(block);
            if (blockmode == BLOCK_MODE_CBC)
            {
                if (i == 0) // us IV
                    block = XorUtils.fixed(_IV, block);
                else block = XorUtils.fixed(AESencryptionUtils.getBlock(d, 16, i - 1), block);
            }
            AESencryptionUtils.setBlock(d, block, BLOCKSIZE, i);
        }
    }
}