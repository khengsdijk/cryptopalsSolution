package com.company;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;


public class DetectXor {


    public static HashMap<Character, Double> generateFrequency(){

        HashMap<Character, Double> map = new HashMap<>();

        map.put('a', 11.682);
        map.put('b', 4.434 );
        map.put('c', 5.238 );
        map.put('d', 3.174 );
        map.put('e', 2.799);
        map.put('f', 4.027);
        map.put('g', 1.642);
        map.put('h', 4.200);
        map.put('i', 7.294);
        map.put('j', 0.511 );
        map.put('k', 0.456);
        map.put('l', 2.415);
        map.put('m', 3.826);
        map.put('n', 2.284);
        map.put('o', 7.631);
        map.put('p', 4.319);
        map.put('q', 0.222);
        map.put('r', 2.826);
        map.put('s', 6.686);
        map.put('t', 15.978);
        map.put('u', 1.183);
        map.put('v', 0.824);
        map.put('w', 5.497);
        map.put('x', 0.045);
        map.put('y', 0.763);
        map.put('z', 0.045);

        return map;
    }




    public static List<String> loadFile(String fileName){

        List<String> fileContent = null;

        try {
            Path path = Paths.get(fileName);
            fileContent = Files.readAllLines(path, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileContent;
    }

    public static String detectXor(){
        List<String> input = DetectXor.loadFile("/home/koen/Documents/ICT projects/assignment2/resources/file.txt");

        for (String a: input
        ) {

            //System.out.println(a);
            //System.out.println(XorUtils.decryptSingleByteXorString(a, 'X'));

            System.out.println(new String(hexadecimalUtils.decodeHexString(a)));
        }
        return "A";
    }





}
