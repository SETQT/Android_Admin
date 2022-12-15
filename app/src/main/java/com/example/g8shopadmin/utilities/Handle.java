package com.example.g8shopadmin.utilities;

public class Handle {
    static public String kFortmatter(String number) {
        String result = "";

        result = number.substring(0, number.length() - 3) + "k";

        return result;
    }
}
