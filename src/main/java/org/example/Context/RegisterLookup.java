package org.example.Context;

import java.util.Map;

public class RegisterLookup implements Context {
    private static final Map<String, Integer> namedRegisters = Map.ofEntries(
            Map.entry("zero", 0),
            Map.entry("ra", 1),
            Map.entry("sp", 2),
            Map.entry("gp", 3),
            Map.entry("tp", 4),
            Map.entry("t0", 5),
            Map.entry("t1", 6),
            Map.entry("t2", 7),
            Map.entry("s0", 8),
            Map.entry("s1", 9),
            Map.entry("a0", 10),
            Map.entry("a1", 11),
            Map.entry("a2", 12),
            Map.entry("a3", 13),
            Map.entry("a4", 14),
            Map.entry("a5", 15),
            Map.entry("a6", 16),
            Map.entry("a7", 17),
            Map.entry("s2", 18),
            Map.entry("s3", 19),
            Map.entry("s4", 20),
            Map.entry("s5", 21),
            Map.entry("s6", 22),
            Map.entry("s7", 23),
            Map.entry("s8", 24),
            Map.entry("s9", 25),
            Map.entry("s10", 26),
            Map.entry("s11", 27),
            Map.entry("t3", 28),
            Map.entry("t4", 29),
            Map.entry("t5", 30),
            Map.entry("t6", 31)
    );

    public static boolean isRegisterAlias(String s) {
        if(namedRegisters.containsKey(s))
            return true;

        if(s.charAt(0) != 'x')
            return false;

        try {
            int registerNumber = Integer.parseInt(s.substring(1));

            return 0 <= registerNumber && registerNumber < 32;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public boolean hasSymbol(String s) {
        return isRegisterAlias(s);
    }

    @Override
    public int resolveSymbol(String s) {
        if(!isRegisterAlias(s))
            return -1;

        if(namedRegisters.containsKey(s))
            return namedRegisters.get(s);

        return Integer.parseInt(s.substring(1));
    }
}