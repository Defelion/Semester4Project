package dk.sdu.sem4.pro.common.data;

import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Operations {
    public final Map<Integer, Unit> unitMap = new ConcurrentHashMap<>();
    public Map<Integer, Integer> batchQueue = new ConcurrentHashMap<>();
    public LinkedList<String> taskQueue = new LinkedList<>();
}
