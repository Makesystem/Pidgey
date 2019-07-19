/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.makesystem.pidgey.io.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.CharsetDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author Richeli.vargas
 */
public class FilesHelper {

    static final Charset DEFAULT_CHARSET = Charset.UTF_8;

    public final static Charset getCharset(final String file) throws IOException {
        return getCharset(Paths.get(file));
    }

    public final static Charset getCharset(final Path file) throws IOException {
        for (final Charset charset : Charset.values()) {
            try {
                Files.lines(file, toNative(charset)).limit(1).count();
                return charset;
            } catch (UncheckedIOException ignore) {
                // Ignore for a moment
            }
        }
        throw new IOException("Charset can not be identified");
    }

    public final static Charset getCharset(final byte[] buffer) throws IOException {
        for (final Charset charset : Charset.values()) {

            final CharsetDecoder decoder = toNative(charset).newDecoder();
            decoder.reset();

            final byte[] array = Arrays.copyOfRange(buffer, 0, Math.min(buffer.length, 512));

            try {
                decoder.decode(ByteBuffer.wrap(array));
                return charset;
            } catch (CharacterCodingException ignore) {
                // Ignore for a moment
            }

        }
        throw new IOException("Charset can not be identified");
    }

    public final static URL getURL(final String file) {
        try {
            return FilesHelper.class.getClassLoader().getResource(file);
        } catch (Throwable ignore) {
            return FilesHelper.class.getResource((file.startsWith("/") ? "" : "/") + file);
        }
    }

    public final static URI getURI(final String file) throws URISyntaxException {
        return getURL(file).toURI();
    }

    public final static void replaceLines(
            final String file,
            final Charset charset,
            final LineReplacement lineReplacement) throws IOException {

        final String original = file + ".tmp";
        final File originalFile = new File(original);
        final File newFile = new File(file);
        newFile.renameTo(originalFile);

        try {
            final Path originalFilePath = originalFile.toPath();
            final Path newFilePath = newFile.toPath();
            replaceLines(originalFilePath, newFilePath, charset, lineReplacement);
        } catch (IOException throwable) {
            originalFile.renameTo(newFile);
            throw throwable;
        }
    }

    final static void replaceLines(
            final Path fileSource,
            final Path fileTarget,
            final Charset charset,
            final LineReplacement lineReplacement) throws IOException {

        FileInputStream inputStream = null;
        Scanner sc = null;
        BufferedWriter writer = null;

        try {
            inputStream = new FileInputStream(fileSource.toFile());
            sc = new Scanner(inputStream, charset.getName());
            writer = Files.newBufferedWriter(fileTarget, toNative(charset));
            int number = 0;
            while (sc.hasNextLine()) {
                final String line = lineReplacement.replace(number++, sc.nextLine());
                if (line != null && !line.isEmpty()) {
                    writer.write(line);
                    writer.newLine();
                }
            }
            if (sc.ioException() != null) {
                throw sc.ioException();
            }
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (sc != null) {
                sc.close();
            }
            if (writer != null) {
                writer.flush();
                writer.close();
            }
        }
    }

    public final static String read(final String file) throws IOException {
        return read(file, DEFAULT_CHARSET);
    }

    public final static String read(final String file, final Charset charset) throws IOException {
        try {
            //return readFileOnDesktopOLD(file);
            return readOnDesktop(file, charset == null ? DEFAULT_CHARSET : charset);
        } catch (@SuppressWarnings("UseSpecificCatch") Throwable ignore) {
            return readOnWebService(file, charset == null ? DEFAULT_CHARSET : charset);
        }
    }

    final static String readOnDesktop(final String file, final Charset charset) throws URISyntaxException, IOException {

        final Path path = Paths.get(FilesHelper.class.getClassLoader()
                .getResource(file).toURI());

        final String data;
        try (Stream<String> lines = Files.lines(path, java.nio.charset.Charset.forName(charset.getName()))) {
            data = lines.collect(Collectors.joining("\n"));
        }

        return data;
    }

    final static String readOnWebService(final String file, final Charset charset) {

        if (file == null) {
            return null;
        }

        String content = "";
        final Scanner sc = new Scanner(FilesHelper.class.getResourceAsStream((file.startsWith("/") ? "" : "/") + file), charset.getName());
        while (sc.hasNextLine()) {
            final String line = sc.nextLine();
            content += line + "\n";
        }
        return content;
    }

    public final static byte[] readBytes(final String file) throws URISyntaxException, IOException {
        try {
            return readBytesOnWebService(file);
        } catch (@SuppressWarnings("UseSpecificCatch") Throwable ignore) {
            try {
                return readBytesOnDesktop(file);
            } catch (@SuppressWarnings("UseSpecificCatch") Throwable ignoreTwo) {
                return readBytesInnerProject(file);
            }
        }
    }

    final static byte[] readBytesInnerProject(final String file) throws URISyntaxException, IOException {
        return Files.readAllBytes(new File(file).toPath());
    }

    final static byte[] readBytesOnDesktop(final String file) throws URISyntaxException, IOException {
        final URL url = FilesHelper.class.getClassLoader().getResource(file);
        final Path path = Paths.get(url.toURI());
        return Files.readAllBytes(path);
    }

    final static byte[] readBytesOnWebService(final String file) throws URISyntaxException, IOException {
        final Path path = Paths.get(FilesHelper.class.getResource(file).toURI());
        return Files.readAllBytes(path);
    }

    final static java.nio.charset.Charset toNative(final Charset charset) {
        return java.nio.charset.Charset.forName(charset.getName());
    }

    public static interface LineReplacement {

        public String replace(final int lineNumber, final String originalLine);
    }
}
