/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.makesystem.pidgey.io.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
public class FileHelper {

    private static final Charset DEFAULT_CHARSET = Charset.UTF_8;

    protected final static java.nio.charset.Charset toNative(final Charset charset) {
        return java.nio.charset.Charset.forName(charset.getName());
    }

    public final static Charset getCharset(final Path filePath) throws IOException {
        for (final Charset charset : Charset.values()) {
            try {
                Files.lines(filePath, toNative(charset)).limit(1).count();
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

    public final static URL getFileURL(final String file) {
        try {
            return FileHelper.class.getClassLoader().getResource(file);
        } catch (Throwable ignore) {
            return FileHelper.class.getResource((file.startsWith("/") ? "" : "/") + file);
        }
    }

    public final static URI getFileURI(final String file) throws URISyntaxException {
        return getFileURL(file).toURI();
    }

    public final static String readFile(final String file) throws IOException {
        return readFile(file, DEFAULT_CHARSET);
    }

    public final static String readFile(final String file, final Charset charset) throws IOException {
        try {
            //return readFileOnDesktopOLD(file);
            return readFileOnDesktop(file, charset == null ? DEFAULT_CHARSET : charset);
        } catch (@SuppressWarnings("UseSpecificCatch") Throwable ignore) {
            return readFileOnWebService(file, charset == null ? DEFAULT_CHARSET : charset);
        }
    }

    protected final static String readFileOnDesktop(final String file, final Charset charset) throws URISyntaxException, IOException {

        final Path path = Paths.get(FileHelper.class.getClassLoader()
                .getResource(file).toURI());

        final String data;
        try (Stream<String> lines = Files.lines(path, java.nio.charset.Charset.forName(charset.getName()))) {
            data = lines.collect(Collectors.joining("\n"));
        }

        return data;
    }

    protected final static String readFileOnWebService(final String file, final Charset charset) {

        if (file == null) {
            return null;
        }

        String content = "";
        final Scanner sc = new Scanner(FileHelper.class.getResourceAsStream((file.startsWith("/") ? "" : "/") + file), charset.getName());
        while (sc.hasNextLine()) {
            final String line = sc.nextLine();
            content += line + "\n";
        }
        return content;
    }

    public final static byte[] readFileBytes(final String file) throws URISyntaxException, IOException {
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

    protected final static byte[] readBytesInnerProject(final String file) throws URISyntaxException, IOException {
        return Files.readAllBytes(new File(file).toPath());
    }

    protected final static byte[] readBytesOnDesktop(final String file) throws URISyntaxException, IOException {
        final URL url = FileHelper.class.getClassLoader().getResource(file);
        final Path path = Paths.get(url.toURI());
        return Files.readAllBytes(path);
    }

    protected final static byte[] readBytesOnWebService(final String file) throws URISyntaxException, IOException {
        final Path path = Paths.get(FileHelper.class.getResource(file).toURI());
        return Files.readAllBytes(path);
    }

}
