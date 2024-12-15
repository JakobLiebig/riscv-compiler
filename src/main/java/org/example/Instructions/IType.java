package org.example.Instructions;

import org.example.Context.Context;

import java.security.InvalidParameterException;

public class IType extends Instruction{
    String imm, funct3, op;
    String rs1, rd;

    public IType(String s) {
        // remove comma, and brackets
        s = s.replace(",", "").replace("(", " ").replace(")", "");

        // get components
        String[] components = s.split("\\s+");
        String name = components[0];
        rd = components[1];

        // get immediate value and rs1
        int immediate;
        switch(name) {
            case "lb", "lh", "lw", "lbu", "lhu":
                immediate = Integer.parseInt(components[2]);
                rs1 = components[3];
                break;
            default:
                immediate = Integer.parseInt(components[3]);
                rs1 = components[2];
                break;
        }

        // encode immediate
        imm = switch (name) {
            case "slli", "srli" -> imm = "0000000" + toExtendedBinary(immediate, 5, false);
            case "srai" -> imm = "0100000" + toExtendedBinary(immediate, 5, false);
            default -> imm = toExtendedBinary(immediate, 12, true);
        };

        // get op-code
        op = switch (name) {
            case "lb", "lh", "lw", "lbu", "lhu" -> "0000011";
            case "addi", "slli", "slti", "sltiu", "xori", "srli", "srai", "ori", "andi" -> "0010011";
            case "jalr" -> "1100111";
            default -> throw new InvalidParameterException("invalid I-Type instruction");
        };

        // get funct3
        funct3 = switch(name) {
            case "jalr", "lb", "addi" -> "000";
            case "lh", "slli" -> "001";
            case "lw", "slti" -> "010";
            case "sltiu" -> "011";
            case "lbu", "xori" -> "100";
            case "lhu", "srli", "srai" -> "101";
            case "ori" -> "110";
            case "andi" -> "111";
            default -> throw new InvalidParameterException("invalid I-Type instruction");
        };
    }

    @Override
    public String compile(Context context) {
        int rs1 = context.resolveSymbol(this.rs1);
        int rd = context.resolveSymbol(this.rd);

        String rs1String = toExtendedBinary(rs1, 5, false);
        String rdString = toExtendedBinary(rd, 5, false);

        return imm + rs1String + funct3 + rdString + op;
    }

}
