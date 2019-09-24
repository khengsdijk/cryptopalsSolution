package com.company;

import java.util.List;

public class BreakXor {

    private final static int MIN_KEYSIZE = 2;
    private final static int MAX_KEYSIZE = 41;


    /**
     * WARNING ONLY EQUAL LENGTH STRINGS
     * @param input1 first string
     * @param input2 second string
     * @return the hamming distance
     */
    public static int calculateDistance(String input1, String input2){

        int hammingDistance = 0;
        System.out.println(input1);
        System.out.println(input2);

        for (int i = 0; i < input1.length(); i++) {
            int a = input1.charAt(i) ^ input2.charAt(i);
            hammingDistance += Integer.bitCount(a);
        }
        return hammingDistance;

    }

    /**
     * WARNING ONLY EQUAL LENGTH STRINGS
     * @param input1 first string
     * @param input2 second string
     * @return the hamming distance
     */
    public static int calculateDistance(byte[] input1, byte[] input2){

        int hammingDistance = 0;

        for (int i = 0; i < input1.length; i++) {
            int a = input1[i] ^ input2[i];
            hammingDistance += Integer.bitCount(a);
        }
        return hammingDistance;
    }



    public static void calculateKey(){

        int[] keys = possibleKeys();

        List<String> encryptedStrings = DetectXor.loadFile("/home/koen/Documents/ICT projects/assignment2/resources/challenge6.txt");

        // 2 lines to ensure enough bytes
        String encryptedString = encryptedStrings.get(1) + encryptedStrings.get(2);
        byte[] bytes = encryptedString.getBytes();

        for (int key : keys) {

            byte[] a = new byte[key];
            byte[] b = new byte[key];

            for (int i = 0; i < key; i++) {
                a[i] = bytes[i];
            }

            for (int i = key; i < (key * 2); i++) {
                b[i - key] = bytes[i];
            }

            int distance = calculateDistance(new String(a), new String(b));

            double result = distance / (double) key;

            System.out.println("The distance with key: " + key + " is: " + result);

        }
    }

    public static void breakEncryption(){
        List<String> encryptedStrings = DetectXor.loadFile("/home/koen/Documents/ICT projects/assignment2/resources/challenge6.txt");

        int count = 0;

        for (String a: encryptedStrings) {
            count += a.length();
        }
        System.out.println(count);
    }


    public static int[] possibleKeys(){
        
        int[] keyArray = new int[MAX_KEYSIZE - MIN_KEYSIZE];

        for (int i = 0; i <  (MAX_KEYSIZE - MIN_KEYSIZE); i++) {

            keyArray[i] = MIN_KEYSIZE + i;
        }

        return keyArray;
    }

}
