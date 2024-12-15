package org.example.Instructions;

import org.example.Context.Context;

public abstract class Instruction {
    protected String toExtendedBinary(int value, int numberCharacters, boolean signExtend) {
        String extedCharacter = "0";
        if(value < 0 && signExtend)
            extedCharacter = "1";

        String s = Integer.toBinaryString(value);
        int repeat = numberCharacters - s.length();
        if(repeat >= 0)
            return extedCharacter.repeat(repeat) + s;
        else
            return s.substring(-repeat);
    }
    abstract public String compile(Context context);
}
