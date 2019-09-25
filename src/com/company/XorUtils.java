package com.company;

import java.util.HashMap;

public class XorUtils {

    public static String encodeFixedXor(String input, String key){

        byte[] decodedInput = hexadecimalUtils.decodeHexString(input);
        byte[] decodedKey = hexadecimalUtils.decodeHexString(key);

        byte[] encryptedByteArray = new byte[decodedInput.length];

        for (int i = 0; i < decodedInput.length ; i++) {
            encryptedByteArray[i] = (byte) (decodedInput[i] ^ decodedKey[i]);
        }
        String result = hexadecimalUtils.bytesToHexString(encryptedByteArray);

        return result;
    }


    public static String decryptSingleByteXorString(String input, char key){

        byte[] decodedInput = hexadecimalUtils.decodeHexString(input);

        byte[] result = new byte[decodedInput.length];

        byte keyByte = (byte) key;

        for (int i = 0; i < decodedInput.length; i++) {
            result[i] = (byte) (decodedInput[i] ^ keyByte);
        }

        return new String(result);
    }


    public static byte[] fixed(byte[] b1, byte[] b2) {
        if(b1.length != b2.length) throw new RuntimeException("arrays must be same length");
        byte[] ret = new byte[b1.length];
        for(int i=0; i<b1.length; i++)
        {
            ret[i] = (byte) (b1[i] ^ b2[i]);
        }
        return ret;
    }

    public static byte[] fixed(byte[] b1, byte[] b2, int len)
    {
        if(len > b1.length || len > b2.length)
            throw new RuntimeException("Cant do fixed xor longer than buffers");
        byte[] ret = new byte[len];
        for(int i=0; i<len; i++)
        {
            ret[i] = (byte) (b1[i] ^ b2[i]);
        }
        return ret;
    }



    public static byte[] decryptSingleByteXorByteArray(byte[] input, byte key){

        byte[] result = new byte[input.length];

        byte keyByte = (byte) key;

        for (int i = 0; i < input.length; i++) {
            result[i] = (byte) (input[i] ^ keyByte);
        }

        return result;
    }


    public static byte[] generateKeys(){

        byte[] charArray = new byte[256];

        for (int i = 0; i < charArray.length ; i++) {

            char a = (char) i;
            charArray[i] = (byte) a;

        }

        return charArray;
    }

    public static String bruteForceSinlgeKeyXor(String cypherText){
        double highScore = 0;
        String highestScoringText = "";
        byte highestScoringKey= 1;

        byte[] keyset = generateKeys();
        byte[] decodedInput = hexadecimalUtils.decodeHexString(cypherText);
        byte[] result = new byte[decodedInput.length];

        for (byte key: keyset) {
            String temp = "";
            for (int i = 0; i < decodedInput.length; i++) {
                result[i] = (byte) (decodedInput[i] ^ key);
            }

            temp = new String(result);
            double score = frequencyScore(temp);
            if(score > highScore){
                highScore = score;
                highestScoringText = temp;
                highestScoringKey = key;
            }

        }
        String resultString = "The highest score is: " + highScore + " \n"
                + "The highest scoring text is: " + highestScoringText + " \n"
                +  "The highest scoring key is:  " + (char) highestScoringKey;


        return resultString;
    }

    /**
     * scores a decoded string based on the frequency of the characters
     */
    public static double frequencyScore(String text){
        double score = 0;
        text = text.toLowerCase();
        HashMap<Character, Double> scoreMap = DetectXor.generateFrequency();

        for (int i = 0; i < text.length(); i++) {

            if( scoreMap.containsKey(text.charAt(i))){
                score += scoreMap.get(text.charAt(i));
            }
        }
        return score;
    }

    /**
     * scores a decoded string based on the frequency of the characters
     */
    public static double frequencyScore(byte[] input){
        String text = new String(input);
        double score = 0;
        text = text.toLowerCase();
        HashMap<Character, Double> scoreMap = DetectXor.generateFrequency();

        for (int i = 0; i < text.length(); i++) {

            if( scoreMap.containsKey(text.charAt(i))){
                score += scoreMap.get(text.charAt(i));
            }
        }
        return score;
    }



}
