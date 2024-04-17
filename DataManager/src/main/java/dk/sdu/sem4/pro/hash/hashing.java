package dk.sdu.sem4.pro.hash;

import com.amdelamar.jhash.Hash;
import com.amdelamar.jhash.exception.InvalidHashException;

public class hashing {
    public static String hash(String input) {
        String hashed = "";
        char[] inputCharArray = input.toCharArray();
        return Hash.password(inputCharArray).create();
    }

    public static boolean checkPassword(String input, String saved) throws InvalidHashException {
        boolean check = false;
        if(Hash.password(input.toCharArray()).verify(hash(saved))) { check = true; }
        return check;
    }
}
