/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.makesystem.pidgey.system;

import com.makesystem.pidgey.io.file.FilesHelper;
import com.makesystem.pidgey.lang.StringHelper;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.stream.Collectors;

/**
 *
 * @author riche
 */
public class Shell implements Serializable {

    private static final long serialVersionUID = -4208239768975295244L;

    private final Runtime runtime = Runtime.getRuntime();

    final Process $(final String... commands) throws IOException {

        if (commands == null || commands.length == 0) {
            throw new IllegalArgumentException("Command can not be null or empty");
        }

        final Collection<String> allCommands = new LinkedList<>();

        if (SystemHelper.IS_OS_WINDOWS) {
            allCommands.add("cmd.exe");
            allCommands.add("/c");
        } else {
            allCommands.add("/bin/sh");
            allCommands.add("-c");
        }

        allCommands.addAll(
                Arrays.stream(commands)
                        .map(command -> String.format("%s", command.trim()))
                        .filter(command -> !StringHelper.isBlank(command))
                        .collect(Collectors.toList()));

        return runtime.exec(allCommands.stream().toArray(String[]::new));
    }

    public Result exec(final String... command) throws IOException {
        return new Result($(command));
    }

    public class Result {

        private final Process process;
        private final String result;
        private final String error;

        public Result(final Process process) throws IOException {
            this.process = process;
            this.result = FilesHelper.read(process.getInputStream());
            this.error = FilesHelper.read(process.getErrorStream());
        }

        public Process getProcess() {
            return process;
        }

        public String getResult() {
            return result;
        }

        public String getError() {
            return error;
        }

    }
}
