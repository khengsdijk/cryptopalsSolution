package com.company;

import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        challenge1();
        challenge2();
        challenge3();
        //System.out.println(XorUtils.bruteForceSinlgeKeyXor("1b37373331363f78151b7f2b783431333d78397828372d363c78373e783a393b3736"));
        challenge4();
        challenge5();
        challenge6();
        challenge7();
        challenge8();
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
        System.out.println("challenge 4");
        System.out.println("------------------------------------------------------");
        System.out.println(DetectXor.detectXor());
        System.out.println("------------------------------------------------------");
    }

    public static void challenge5(){
        String input = "Burning 'em, if you ain't quick and nimble\n" +
                "I go crazy when I hear a cymbal";
        String key = "ICE";
        String expectedResult = "0b3637272a2b2e63622c2e69692a23693a2a3c6324202d623d63343c2a26226324272765272a282b2f20430a652e2c652a3124333a653e2b2027630c692b20283165286326302e27282f";
        String result = RepeatingXor.repeatedXorEncryption(input, key);

        if(expectedResult.equals(result) ){
            System.out.println("Challenge 5 correct!!!!");
        }
    }

    public static void challenge6(){
        BreakXor.calculateKey();
        System.out.println("----------------------------------------------------------------");
    }

    public static void challenge7(){
        System.out.println("----------------------------Challenge 7------------------------------------");

        byte[] keyBytes = "YELLOW SUBMARINE".getBytes();
        AESKey k = new AESKey(keyBytes);
        byte[] data = FileStuff.readBase64("/home/koen/Documents/ICT projects/assignment2/resources/assignment7.txt");

        AESBlockCipher cipher = new AESBlockCipher(k);
        cipher.decrypt(data);
        System.out.println(new String(data));

    }

    public static void challenge8(){
        System.out.println(" ------------------------------challenge8-------------------------------------");
        List<String> cipherTexts = DetectXor.loadFile("/home/koen/Documents/ICT projects/assignment2/resources/data8.txt");
        int line = 1;
        for (String cText : cipherTexts)
        {
            byte[] cipher = hexadecimalUtils.decodeHexString(cText);

            // we know that every block is 16 bytes
            byte[][] data = new byte[cipher.length / 16][16];
            int pos = 0;

            // fill up the array
            for (int j = 0; j < cipher.length / 16; j++)
            {
                for (int l = 0; l < 16; l++)
                    data[j][l] = cipher[pos++];
            }
            boolean breakout = false;
            for (int i = 0; i < data.length; i++)
            {
                if (breakout)
                    break;
                for (int j = 0; j < data.length; j++)
                {
                    if (i == j)
                        continue;
                    if (Arrays.equals(data[i], data[j]))
                    {
                        System.out.println("Line " + line + " is probably encrypted by ECB");
                        breakout = true;
                        break;
                    }
                }
            }
            line++;
        }
    }


}
