package com.company;

public class RepeatingXor {

    public static String repeatedXorEncryption(String input, String key){

        byte[] inputBytes = input.getBytes();
        byte[] keyBytes = key.getBytes();

        byte[] encryptedByteArray = new byte[inputBytes.length];

        for (int i = 0; i < inputBytes.length; i++) {
            encryptedByteArray[i] = (byte) (inputBytes[i] ^ keyBytes[(i % (key.length()))] );

        }

        return hexadecimalUtils.bytesToHexString(encryptedByteArray);
    }

    public static byte[] repeatedXorEncryptionByteArray(byte[] input, byte[] key){

        byte[] encryptedByteArray = new byte[input.length];

        for (int i = 0; i < input.length; i++) {
            encryptedByteArray[i] = (byte) (input[i] ^ key[(i % (key.length))] );
        }

        return encryptedByteArray;
    }


}
