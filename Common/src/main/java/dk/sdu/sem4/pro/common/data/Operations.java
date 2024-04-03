package dk.sdu.sem4.pro.common.data;

import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Operations {
    private final Map<Integer, Unit> unitMap = new ConcurrentHashMap<>();
    private Map<Integer, Integer> batchQueue = new ConcurrentHashMap<>();
    private LinkedList<String> taskQueue = new LinkedList<>();
}
