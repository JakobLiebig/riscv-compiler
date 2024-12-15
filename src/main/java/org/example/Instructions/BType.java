package org.example.Instructions;

import org.example.Context.Context;

import java.security.InvalidParameterException;

public class BType extends Instruction {
    private static final String op = "1100011";
    private final String funct3;
    private final int lineNumber;
    private final String label, rs1, rs2;

    public BType(String s, int lineNumber) {
        s = s.replace(",", "");
        String[] components = s.split("\\s+");

        String name = components[0];
        rs1 = components[1];
        rs2 = components[2];
        label = components[3];
        this.lineNumber = lineNumber;

        funct3 = switch(name) {
            case "beq" -> "000";
            case "bne" -> "001";
            case "blt" -> "100";
            case "bge" -> "101";
            case "bltu" -> "110";
            case "bgeu" -> "111";
            default -> throw new InvalidParameterException("invalid B-Type instruction");
        };
    }

    public String compile(Context context) {
        int rs1 = context.resolveSymbol(this.rs1);
        int rs2 = context.resolveSymbol(this.rs2);
        int labelLineNumber = context.resolveSymbol(label);
        int imm = (labelLineNumber - lineNumber) * 4;

        String immString = toExtendedBinary(imm, 13, true);
        String rs1String = toExtendedBinary(rs1, 5, false);
        String rs2String = toExtendedBinary(rs2, 5, false);

        return immString.charAt(0) + immString.substring(2, 8) + rs2String + rs1String + funct3 + immString.substring(8, 12) + immString.charAt(1) + op;
    }

}
