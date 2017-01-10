package com.cars.data.model;

import java.util.HashMap;

public class FuelTypUtil {
    private static final int ON = 1;
    private static final int PB = 2;
    private static final int LPG = 3;
    private static final int OTHER = 4;

    private static HashMap<Integer, String> fuel = new HashMap<>();
    private static HashMap<String, Integer> fuel2 = new HashMap<>();

    static {
        fuel.put(1, "ON");
        fuel.put(2,"PB");
        fuel.put(3, "LPG");
        fuel.put(4, "OTHER");
        fuel2.put("ON", 1);
        fuel2.put("PB", 2);
        fuel2.put("LPG",3);
        fuel2.put("OTHER",4);
    }

    public static String getFuelName(int i) {
        return fuel.get(i);
    }

    public static Integer getFuelKey(String name) {
        return  fuel2.get(name);
    }

}
