package org.example.Context;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SourceFile implements Source {
    private final LabelLookup labels;
    private final RegisterLookup registers;
    private final List<String> instructions;

    private void recursiveReadFile(BufferedReader file) throws IOException {
        String line = file.readLine();
        if (line == null) return;

        // remove all comments and cleanup text
        String cleanLine = line.replaceAll("#.*", "").trim().replaceAll("\\s+", " ").replaceAll("\t+", " ");

        if (!cleanLine.isEmpty()) {
            if (cleanLine.charAt(cleanLine.length() - 1) == ':') {
                // line is a label
                String label = cleanLine.substring(0, cleanLine.length() - 1);
                int lineNumber = instructions.size();
                labels.add(label, lineNumber);
            } else {
                // line is a instruction
                instructions.add(cleanLine);
            }
        }

        recursiveReadFile(file);
    }

    public SourceFile(String filename) throws IOException {
        this.labels = new LabelLookup();
        this.registers = new RegisterLookup();
        this.instructions = new ArrayList<>();

        BufferedReader file = new BufferedReader(new FileReader(filename));
        recursiveReadFile(file);
    }

    @Override
    public boolean hasSymbol(String s) {
        return labels.hasSymbol(s) || registers.hasSymbol(s);
    }

    @Override
    public int resolveSymbol(String s) {
        if(labels.hasSymbol(s))
            return labels.resolveSymbol(s);
        else if(registers.hasSymbol(s))
            return registers.resolveSymbol(s);

        throw new InvalidParameterException("Invalid Symbole: " + s);
    }

    @Override
    public Iterator<String> iterator() {
        return instructions.iterator();
    }
}
