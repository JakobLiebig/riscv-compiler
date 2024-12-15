package org.example;

import org.example.Instructions.*;

import java.io.IOException;
import java.security.InvalidParameterException;

public class Main {
    public void testInstructions() {
        IType i = new IType("lb t0, 10(t1)");
        UType u = new UType("auipc t0, 10");
        SType s = new SType("sb t1, 10(t2)");
        RType r = new RType("add t0, t1, t2");
        BType b = new BType("beq t0, t1, hello", 10);
        JType j = new JType("jal t0, hello", 10);

        System.out.println(0);
    }
    public static void main(String[] args) {
        try {
            Compiler c = new Compiler("source.riscv", "destination.hex");
            c.run();
        } catch (IOException e) {
            System.out.println("IOException while compiling: " + e.getMessage());
        }
    }
}