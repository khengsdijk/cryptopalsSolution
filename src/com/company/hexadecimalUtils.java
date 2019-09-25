package com.company;

import java.util.Base64;

public class hexadecimalUtils {

    private static String hexKey = "0123456789abcdef";

    public static String hexToBase64(String hexString){
        byte[] test = decodeHexString(hexString);
        String result = byteToBase64(test);

        return result;
    }


    public static String byteToBase64(byte[] byteArray){
        String baseString = Base64.getEncoder().encodeToString(byteArray);
        return baseString;
    }

    public static String bytesToHexString(byte[] input){
        StringBuilder result = new StringBuilder();

        for (byte b : input) {
            result.append(byteToHex(b));
        }
        return result.toString();
    }

    public static byte[] decodeHexString(String hexString) {
        if (hexString.length() % 2 == 1) {
            throw new IllegalArgumentException(
                    "Error the hexadecimal String has to be even");
        }

        byte[] bytes = new byte[hexString.length() / 2];
        for (int i = 0; i < hexString.length(); i += 2) {
            bytes[i / 2] = hexToByte(hexString.substring(i, i + 2));
        }
        return bytes;
    }

    private static String byteToHex(byte num) {
        char[] hexDigits = new char[2];
        hexDigits[0] = Character.forDigit((num >> 4) & 0xF, 16);
        hexDigits[1] = Character.forDigit((num & 0xF), 16);
        return new String(hexDigits);
    }

    public static byte hexToByte(String hexString) {
        int firstDigit = toDigit(hexString.charAt(0));
        int secondDigit = toDigit(hexString.charAt(1));
        return (byte) ((firstDigit << 4) + secondDigit);
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

    public static String toPrettyHexStr(byte[] arr)
    {
        String s = "";
        int i = 0;
        for (byte b : arr)
        {
            if (i % 16 == 0 && i != 0)
                s += "\n";
            s += hexKey.charAt((b & 0xF0) >> 4);
            s += hexKey.charAt(b & 0x0F);
            i++;
        }
        return s;
    }


    private static int toDigit(char hexChar) {
        int digit = Character.digit(hexChar, 16);
        if(digit == -1) {
            throw new IllegalArgumentException(
                    "Invalid Hexadecimal Character: "+ hexChar);
        }
        return digit;
    }

}
