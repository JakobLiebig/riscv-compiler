package org.example.Instructions;

public class BaseLineModule implements Module {
    @Override
    public Instruction classify(String s, int lineNumber) {
        String name = s.split("\\s+")[0];

        return switch (name) {
            case "jalr", "lb", "lh", "lw", "lbu", "lhu", "addi", "slti", "sltiu", "xori", "ori", "andi", "slli", "srli", "srai" -> new IType(s);
            case "slt", "sltu", "add", "sub", "and", "or", "xor", "sll", "srl", "sra" -> new RType(s);
            case "sb", "sh", "sw" -> new SType(s);
            case "beq", "bne", "blt", "bge", "bltu", "bgeu" -> new BType(s, lineNumber);
            case "lui", "auipc" -> new UType(s);
            case "jal" -> new JType(s, lineNumber);
            default -> throw new IllegalArgumentException("Unknown RISC-V operation: " + s);
        };
    }
}
