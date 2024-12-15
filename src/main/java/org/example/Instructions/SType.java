package org.example.Instructions;

import org.example.Context.Context;

import java.security.InvalidParameterException;

public class SType extends Instruction {
    private final String imm, funct3;
    private static final String op = "0100011";
    private final String rs1, rs2;

    public SType(String s) {
        // replace coma and brackets
        s = s.replace(",", "").replace("(", " ").replace(")", "");

        String[] components = s.split("\\s+");
        String name = components[0];
        rs2 = components[1];
        rs1 = components[3];

        int immediate = Integer.parseInt(components[2]);
        imm = toExtendedBinary(immediate, 12, true);

        funct3 = switch(name) {
            case "sb" -> "000";
            case "sh" -> "001";
            case "sw" -> "010";
            default -> throw new InvalidParameterException("Invalid S-Type instruction.");
        };
    }

    public String compile(Context context) {
        int rs1 = context.resolveSymbol(this.rs1);
        int rs2 = context.resolveSymbol(this.rs2);

        String rs1String = toExtendedBinary(rs1, 5, false);
        String rs2String = toExtendedBinary(rs2, 5, false);

        return imm.substring(0, 7) + rs2String + rs1String + funct3 + imm.substring(7, 12) + op;
    }
}
