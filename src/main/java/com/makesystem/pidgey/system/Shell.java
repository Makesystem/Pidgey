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
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 *
 * @author riche
 */
public class Shell implements Serializable {

    private static final long serialVersionUID = -4208239768975395244L;

    private static final String DEF__FORMAT = "%s";

    public static final Collection<String> CMD_WINDOWS = Arrays.asList("cmd.exe", "/c");
    public static final Collection<String> CMD_LINUX = Arrays.asList("/bin/sh", "-c");

    private final Runtime runtime = Runtime.getRuntime();

    final Process $(final String... commands) throws IOException {

        if (commands == null || commands.length == 0) {
            throw new IllegalArgumentException("Command can not be null or empty");
        }

        final Collection<String> allCommands = new LinkedList<>();

        if (SystemHelper.IS_OS_WINDOWS) {
            allCommands.addAll(CMD_WINDOWS);
        } else if (SystemHelper.IS_OS_LINUX) {
            allCommands.addAll(CMD_LINUX);
        } else {
            throw new RuntimeException("SO is not supported!");
        }

        allCommands.addAll(
                Arrays.stream(commands)
                        .map(command -> String.format(DEF__FORMAT, command.trim()))
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

        public int getExitValue() {
            return process.exitValue();
        }

        public int waitFor() throws InterruptedException {
            return process.waitFor();
        }

        public boolean waitFor(
                final long timeout,
                final TimeUnit unit) throws InterruptedException {
            return process.waitFor(timeout, unit);
        }
    }
}
