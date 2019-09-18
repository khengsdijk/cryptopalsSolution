package com.company;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        challenge1();
        challenge2();
        challenge3();
        System.out.println(XorUtils.bruteForceSinlgeKeyXor("1b37373331363f78151b7f2b783431333d78397828372d363c78373e783a393b3736"));
        //challenge4();

    }

    public static void challenge1(){
        String input = "49276d206b696c6c696e6720796f757220627261696e206c696b65206120706f69736f6e6f7573206d757368726f6f6d";
        String expectedResult = "SSdtIGtpbGxpbmcgeW91ciBicmFpbiBsaWtlIGEgcG9pc29ub3VzIG11c2hyb29t";
        String result = hexadecimalUtils.hexToBase64(input);

        if (result.equals(expectedResult)){
            System.out.println("Challenge 1 correct!!!!");
        }
    }

    public static void challenge2(){
        String input = "1c0111001f010100061a024b53535009181c";
        String key = "686974207468652062756c6c277320657965";
        String expectedResult = "746865206b696420646f6e277420706c6179";
        String result = XorUtils.encodeFixedXor(input, key);

        if (result.equals(expectedResult)){
            System.out.println("Challenge 2 correct!!!!");
        }
    }

    public static void challenge3(){

        // i basically brute forced the encryption key until x gave a relatively good result and
        // then i figured out X was the right key

        System.out.println(XorUtils.decryptSingleByteXorString("1b37373331363f78151b7f2b783431333d78397828372d363c78373e783a393b3736", 'X'));
    }

    public static void challenge4(){

        DetectXor.detectXor();

    }


}
