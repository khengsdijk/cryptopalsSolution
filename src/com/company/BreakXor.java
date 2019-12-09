package com.company;

import java.util.*;
import java.util.stream.Collectors;

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

        byte[] bytes = FileStuff.readBase64("/home/koen/Documents/ICT projects/assignment2/resources/challenge6.txt");
        System.out.println(bytes.length);
        Map<Integer, Integer> keysAndDistance = new HashMap<>();

        for (int key : keys) {

            byte[] data1 = Arrays.copyOfRange(bytes, 0 * key, 1 * key);
            byte[] data2 = Arrays.copyOfRange(bytes, 1 * key, 2 * key);
            byte[] data3 = Arrays.copyOfRange(bytes, 2 * key, 3 * key);
            byte[] data4 = Arrays.copyOfRange(bytes, 3 * key, 4 * key);

            int totaldist = calculateDistance(data1, data2);
            totaldist += calculateDistance(data1, data3);
            totaldist += calculateDistance(data1, data4);
            totaldist += calculateDistance(data2, data3);
            totaldist += calculateDistance(data2, data4);
            totaldist += calculateDistance(data3, data4);

            keysAndDistance.put(key, totaldist/key);
        }


        Map<Integer, Integer> result = keysAndDistance.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        result.forEach((mapKey, value) -> System.out.println(mapKey + ":" + value));

        // you can change this to try the top keysizes
        for (int i = 0; i < 1; i++)
        {
            int keysize = 29;
            System.out.println("trying keysize " + keysize);
            byte[] key = new byte[keysize];

            // split the data into chunks
            byte[][] data = new byte[bytes.length / keysize][keysize];
            int pos = 0;
            for (int j = 0; j < bytes.length / keysize; j++)
            {
                for (int l = 0; l < keysize; l++)
                    data[j][l] = bytes[pos++];
            }

            // transpose the array
            byte[][] transpose = new byte[keysize][bytes.length / keysize];
            for (int r = 0; r < data.length; r++)
            {
                for (int c = 0; c < data[0].length; c++)
                {
                    transpose[c][r] = data[r][c];
                }
            }
            // time to bruteforce the key
            for (int k = 0; k < keysize; k++)
            {
                for (int j = 0; j < 255; j++)
                {
                    byte[] decoded = single(transpose[k], (byte) j);
                    double score = stringMetric(decoded);
                    if (score > 0.85)
                    {
                        key[k] = (byte) j;
                    }
                }
            }

            System.out.println("Guessed Key: " + toNormalStr(key));
            System.out.println(toNormalStr(RepeatingXor.repeatedXorEncryptionByteArray(bytes, key)));
        }
    }

    public static byte[] repeating(byte[] arr, byte[] key)
    {
        byte[] ret = new byte[arr.length];
        for(int i=0; i<arr.length; i++)
        {
            ret[i] = (byte)(arr[i] ^ key[i % key.length]);
        }
        return ret;
    }

    public static double stringMetric(byte[] arr)
    {
        int count = 0;
        for (byte b : arr)
        {
            //  my other frequency didnt work but this does??  Its ranked by hand
            if ((b >= 'a' && b <= 'z') || b == ' ')
                count += 4;
            if ((b >= 'A' && b <= 'Z') || b == '\'' || b == '.' || b == '!' || b == '?')
                count += 2;
            if ((b >= '0' && b <= '9') || b == '\n' || b == '\t' || b == '\r')
                count++;
        }
        return (double)count / (arr.length * 4);
    }

    public static String toNormalStr(byte[] arr)
    {
        try
        {
            return new String(arr, "UTF-8");
        } catch (Exception e)
        {
            return "";
        }
    }

    public static byte[] single(byte[] arr, byte key)
    {
        byte[] ret = new byte[arr.length];
        for(int i=0; i<arr.length; i++)
        {
            ret[i] = (byte) (arr[i] ^ key);
        }
        return ret;
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
