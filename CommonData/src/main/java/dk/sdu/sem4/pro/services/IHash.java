package dk.sdu.sem4.pro.services;

public interface IHash {
    public String hash(String input);
    public boolean checkPassword(String input, String saved);
}
