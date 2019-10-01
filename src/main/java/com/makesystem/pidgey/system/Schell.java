/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.makesystem.pidgey.system;

import com.makesystem.pidgey.io.file.FilesHelper;
import java.io.IOException;

/**
 *
 * @author riche
 */
public class Schell {

    private final Runtime runtime = Runtime.getRuntime();

    final Process $(final String command) throws IOException {

        if (command == null) {
            throw new IllegalArgumentException("Command can not be null");
        }

        if (SystemHelper.IS_OS_WINDOWS) {
            return runtime.exec(String.format("cmd.exe /c %s", command));
        } else {
            return runtime.exec(String.format("sh -c %s", command));
        }
    }

    public Result exec(final String command) throws IOException {
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
