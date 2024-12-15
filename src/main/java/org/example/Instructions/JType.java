package org.example.Instructions;

import org.example.Context.Context;

public class JType extends Instruction {
    private static String op = "1101111";
    private String rd, label;
    private int lineNumber;

    public JType(String s, int lineNumber) {
        s = s.replace(",", "");
        String[] components = s.split("\\s+");

        rd = components[1];
        label = components[2];
        this.lineNumber = lineNumber;
    }

    public String compile(Context context) {
        int rd = context.resolveSymbol(this.rd);
        int labelLineNumber = context.resolveSymbol(label);
        int imm = (labelLineNumber - lineNumber) * 4;

        String immString = toExtendedBinary(imm, 21, true);
        String rdString = toExtendedBinary(rd, 5, false);


        return immString.charAt(0) + immString.substring(10, 20) + immString.charAt(9) + immString.substring(1, 9) + rdString + op;
    }
}
