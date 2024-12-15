package org.example;

import org.example.Context.Source;
import org.example.Context.SourceFile;
import org.example.Instructions.BaseLineModule;
import org.example.Instructions.Instruction;
import org.example.Instructions.Module;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Compiler {
    private final Source source;
    private final Module module;
    private final String destinationFilename;

    public Compiler(String sourceFilename, String destinationFilename) throws IOException {
       this.source = new SourceFile(sourceFilename);
       this.module = new BaseLineModule();
       this.destinationFilename = destinationFilename;
    }

    private void writeToFile(String filename, String content) throws IOException {
        Files.write(Paths.get(filename), content.getBytes());
    }

    public void run() throws IOException {
        StringBuilder builder = new StringBuilder();

        int lineCount = 0;

       for(String command: this.source) {
           try {
               Instruction current = module.classify(command, lineCount++);
               builder.append(current.compile(this.source) + "\n");
           } catch (Exception e) {
               System.out.println("Error in instruction number " + lineCount + ": '" + command + "': ");
               System.out.println(e.getMessage());
               return;
           }
       }

       writeToFile(destinationFilename, builder.toString());
    }
}
