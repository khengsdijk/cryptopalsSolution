package com.company;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileStuff {


    public static String readFull(String file)
    {
        String ret = "";
        String line;
        try
        {
            BufferedReader input = new BufferedReader(new FileReader(new File(file)));
            while ((line = input.readLine()) != null)
            {
                ret += line;
            }
            input.close();
        } catch (IOException e) {}
        return ret;
    }

    public static byte[] readBase64(String file)
    {
        String filetext = "";
        String line;
        try
        {
            BufferedReader input = new BufferedReader(new FileReader(new File(file)));
            while ((line = input.readLine()) != null)
            {
                filetext += line;
            }
            input.close();
        } catch (IOException e)
        {}
        return Base64toBytes(filetext);
    }


    public static byte[] Base64toBytes(String str) {
        byte [] ret = new byte[(int)(str.length() * 0.75)];
        int pos = 0;
        for(int i=0; i<str.length(); i+=4){
            int j = (mapFrom(str.charAt(i)) << 18) |
                    (mapFrom(str.charAt(i + 1)) << 12) |
                    (mapFrom(str.charAt(i + 2)) << 6) |
                    (mapFrom(str.charAt(i + 3)));
            ret[pos++] = (byte)((j >> 16) & 0xFF);
            ret[pos++] = (byte)((j >> 8) & 0xFF);
            ret[pos++] = (byte)(j & 0xFF);
        }

        return ret;
    }

    private static int mapFrom(char c)
    {
        if(c == '=') return 0;
        return indexTable.indexOf(c);
    }

    private static String indexTable = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";


}
