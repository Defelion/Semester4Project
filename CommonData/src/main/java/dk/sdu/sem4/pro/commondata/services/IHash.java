package dk.sdu.sem4.pro.commondata.services;

public interface IHash {
    public String hash(String input);
    public boolean checkPassword(String input, String saved);
}
