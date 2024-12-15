package org.example.Instructions;

import org.example.Context.*;


public class RType extends Instruction {
    private final String funct7, funct3;
    private final static String op = "0110011";
    private final String rs2, rs1, rd;

    public RType(String s) {
        // remove coma
        s = s.replace(",", "");

        String[] components = s.split("\\s+");
        String name = components[0];
        rd = components[1];

        rs1 = components[2];
        rs2 = components[3];

        switch(name) {
            case "sub":
            case "sra":
                funct7 = "0100000";
                break;
            default:
                funct7 = "0000000";
                break;
        }
        switch(name) {
            case "sll":
                funct3 = "001";
                break;
            case "slt":
                funct3 = "010";
                break;
            case "sltu":
                funct3 = "011";
                break;
            case "xor":
                funct3 = "100";
                break;
            case "srl":
            case "sra":
                funct3 = "101";
                break;
            case "or":
                funct3 = "110";
                break;
            case "and":
                funct3 = "111";
                break;
            default:
                funct3 = "000";
                break;
        }

    }

    @Override
    public String compile(Context context) {
        int rs1 = context.resolveSymbol(this.rs1);
        int rs2 = context.resolveSymbol(this.rs2);
        int rd = context.resolveSymbol(this.rd);

        String rdString = toExtendedBinary(rd, 5, false);
        String rs1String = toExtendedBinary(rs1, 5, false);
        String rs2String = toExtendedBinary(rs2, 5, false);

        return funct7 + rs2String + rs1String + funct3 + rdString + op;
    }
}
