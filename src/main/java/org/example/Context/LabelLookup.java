package org.example.Context;

import java.util.HashMap;
import java.util.Map;

public class LabelLookup implements Context {
    private final Map<String, Integer> labels;

    public LabelLookup() {
        labels = new HashMap<>();
    }

    public void add(String name, Integer line) {
        this.labels.put(name, line);
    }

    @Override
    public boolean hasSymbol(String s) {
        return labels.containsKey(s);
    }

    @Override
    public int resolveSymbol(String s) {
        if(!hasSymbol(s))
            return -1;

        return labels.get(s);
    }
}
