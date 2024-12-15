package org.example.Instructions;

import org.example.Context.Context;

import java.security.InvalidParameterException;

public class UType extends Instruction{
    private final String imm, op;
    private final String rd;

    public UType(String s) {
        s = s.replace(",", "");
        String[] components = s.split("\\s+");
        String name = components[0];
        rd = components[1];

        op = switch(name) {
            case "auipc" -> "0010111";
            case "lui" -> "0110111";
            default -> throw new InvalidParameterException("invalid U-Type instruction");
        };

        int upimm = Integer.parseInt(components[2]);
        imm = toExtendedBinary(upimm, 20, true);
    }

    public String compile(Context context) {
        int rd = context.resolveSymbol(this.rd);
        String rdString = toExtendedBinary(rd, 5, false);

        return imm + rdString + op;
    }
}
