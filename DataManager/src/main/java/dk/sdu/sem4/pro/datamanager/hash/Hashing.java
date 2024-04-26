package dk.sdu.sem4.pro.datamanager.hash;

import com.amdelamar.jhash.Hash;
import com.amdelamar.jhash.exception.InvalidHashException;
import dk.sdu.sem4.pro.commondata.services.IHash;

public class Hashing implements IHash {
    public String hash(String input) {
        String hashed = "";
        char[] inputCharArray = input.toCharArray();
        return Hash.password(inputCharArray).create();
    }

    public boolean checkPassword(String input, String saved) {
        boolean check = false;
        try {
            if(Hash.password(input.toCharArray()).verify(hash(saved))) { check = true; }
        } catch (InvalidHashException e) {
            throw new RuntimeException(e);
        }
        return check;
    }
}
