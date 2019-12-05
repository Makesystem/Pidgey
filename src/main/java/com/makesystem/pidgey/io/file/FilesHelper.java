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
import java.io.InputStream;
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
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.function.Function;

/**
 *
 * @author Richeli.vargas
 */
public class FilesHelper implements Serializable {

    private static final long serialVersionUID = -5106513613191381392L;

    static final Charset DEFAULT_CHARSET = Charset.UTF_8;

    public final static Charset getCharset(final String file) throws IOException {
        return getCharset(Paths.get(file));
    }

    public final static Charset getCharset(final Path file) throws IOException {
        for (final Charset charset : Charset.values()) {
            try {
                Files.lines(file, charset.toNative()).limit(1).count();
                return charset;
            } catch (UncheckedIOException ignore) {
                // Ignore for a moment
            }
        }
        throw new IOException("Charset can not be identified");
    }

    public final static Charset getCharset(final byte[] buffer) throws IOException {
        for (final Charset charset : Charset.values()) {

            final CharsetDecoder decoder = charset.toNative().newDecoder();
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

    final static String resolveName(final String file) {
        return (file.startsWith("/") ? "" : "/") + file;
    }

    public final static URL getURL(final String file) {
        return FilesHelper.class.getResource(resolveName(file));
    }

    public final static URI getURI(final String file) throws URISyntaxException {
        final URL url = getURL(file);
        if (url == null) {
            throw new IllegalArgumentException("Invalid path: " + file);
        }
        return url.toURI();
    }

    public final static InputStream getInputStream(final String file) throws FileNotFoundException, IOException {
        final URL url = getURL(file);
        return url == null ? new FileInputStream(file) : url.openStream();
    }

    public final static void replaceLines(
            final String file,
            final Charset charset,
            final Function<String, String> mapper) throws IOException {

        final String original = file + ".tmp";
        final File originalFile = new File(original);
        final File newFile = new File(file);
        newFile.renameTo(originalFile);

        try {
            replaceLines(original, file, charset, mapper);
            originalFile.delete();
        } catch (IOException throwable) {
            originalFile.renameTo(newFile);
            throw throwable;
        }
    }

    final static void replaceLines(
            final String fileSource,
            final String fileTarget,
            final Charset charset,
            final Function<String, String> mapper) throws IOException {

        try (final BufferedWriter writer = Files.newBufferedWriter(new File(fileTarget).toPath(), charset.toNative())) {
            readLines(fileSource, charset, line -> {
                if (line != null && !line.isEmpty()) {
                    try {
                        writer.write(mapper.apply(line));
                        writer.newLine();
                    } catch (IOException cause) {
                        throw new RuntimeException(cause);
                    }
                }
            });
        }
    }

    public final static void readLines(final String file, final Consumer<String> consumer) throws IOException {
        readLines(file, DEFAULT_CHARSET, consumer);
    }

    public final static void readLines(final String file,
            final Charset charset, final Consumer<String> consumer) throws IOException {

        try (
                final InputStream inputStream = getInputStream(file);
                final Scanner scanner = new Scanner(inputStream, charset.getName())) {
            while (scanner.hasNextLine()) {
                consumer.accept(scanner.nextLine());
            }

            if (scanner.ioException() != null) {
                throw scanner.ioException();
            }
        }
    }

    public final static void delete(final String path) throws IOException {
        delete(new File(path));
    }

    public final static void delete(final File file) {

        if (!file.exists()) {
            return;
        }

        // Delete internal files and directories, 
        // because folder must be empty to delete
        if (!file.isFile()) {
            Arrays.stream(file.listFiles()).forEach(child -> delete(child));
        }

        file.delete();
    }

    /**
     * Removes all files from the directory. Subfolders are not affected.
     *
     * @param directory
     * @throws IOException
     */
    public final static void deleteFiles(final String directory) throws IOException {
        deleteFiles(directory, null);
    }

    /**
     * Removes all files from the directory. Subfolders are not affected.
     *
     * @param directory
     * @param predicate
     * @throws IOException
     */
    public final static void deleteFiles(final String directory, final Predicate<File> predicate) throws IOException {
        final File dir = new File(directory);
        if (dir.exists() && dir.isDirectory()) {

            final Stream<File> stream = Arrays.stream(dir.listFiles())
                    .filter(file -> file.isFile());

            if (predicate == null) {
                stream.forEach(file -> file.delete());
            } else {
                stream.filter(predicate).forEach(file -> file.delete());
            }

        }
    }

    public final static void clear(final String file) throws IOException {

        final Path path = Paths.get(file);

        if (!path.toFile().exists()) {
            return;
        }

        try (final BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.TRUNCATE_EXISTING)) {
        }
    }

    public final static void append(final String file, final String data) throws IOException {
        final Path path = Paths.get(file);
        mkfile(path);
        try (final BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
            writer.write(data);
        }
    }

    public final static void append(final String file, final byte[] data) throws IOException {
        final Path path = Paths.get(file);
        mkfile(path);
        Files.write(path, data, StandardOpenOption.APPEND);
    }

    public final static void newLine(final String file) throws IOException {
        final Path path = Paths.get(file);
        mkfile(path);
        try (final BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
            writer.newLine();
        }
    }

    public final static void write(final String file, final String data) throws IOException {
        final Path path = Paths.get(file);
        mkfile(path);
        try (final BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.TRUNCATE_EXISTING)) {
            writer.write(data);
        }
    }

    public final static void write(final String filePath, final byte[] data) throws IOException {
        final Path path = Paths.get(filePath);
        mkfile(path);
        Files.write(path, data, StandardOpenOption.TRUNCATE_EXISTING);
    }

    public final static String read(final String file) throws IOException {
        return read(file, DEFAULT_CHARSET);
    }

    public final static String read(final String file, final Charset charset) throws IOException {
        if (charset == null) {
            return new String(readBytes(file));
        } else {
            return new String(readBytes(file), charset.toNative());
        }
    }

    public final static byte[] readBytes(final String file) throws IOException {

        /**
         * First attempt
         */
        try {
            return readURL(file);
        } catch (@SuppressWarnings("UseSpecificCatch") Throwable ignore) {
        }

        /**
         * Second attempt
         */
        try {
            return readBytesOnWebService(file);
        } catch (@SuppressWarnings("UseSpecificCatch") Throwable ignore) {
        }

        /**
         * Third attempt
         */
        try {
            return readBytesOnDesktop(file);
        } catch (@SuppressWarnings("UseSpecificCatch") Throwable ignore) {
        }

        /**
         * Last attempt
         */
        return readBytesInnerProject(file);
    }

    final static byte[] readBytesInnerProject(final String file) throws IOException {
        return Files.readAllBytes(new File(file).toPath());
    }

    final static byte[] readBytesOnDesktop(final String file) throws IOException {
        try {
            final URL url = FilesHelper.class.getClassLoader().getResource(file);
            final Path path = Paths.get(url.toURI());
            return Files.readAllBytes(path);
        } catch (URISyntaxException ex) {
            throw new IOException(ex);
        }
    }

    final static byte[] readBytesOnWebService(final String file) throws IOException {
        try {
            final Path path = Paths.get(FilesHelper.class.getResource(file).toURI());
            return Files.readAllBytes(path);
        } catch (URISyntaxException ex) {
            throw new IOException(ex);
        }
    }

    final static byte[] readURL(final String file) throws IOException {
        try (final InputStream inputStream = getInputStream(file)) {
            return readInputStream(inputStream);
        }
    }

    public final static String read(final InputStream inputStream) throws IOException {
        return read(inputStream, DEFAULT_CHARSET);
    }

    public final static String read(final InputStream inputStream, final Charset charset) throws IOException {
        return new String(readInputStream(inputStream), charset.toNative());
    }

    final static byte[] readInputStream(final InputStream inputStream) throws IOException {
        final byte[] array = new byte[inputStream.available()];
        inputStream.read(array);
        return array;
    }

    public final static boolean mkfile(final Path path) throws IOException {
        return mkfile(path.toFile());
    }

    public final static boolean mkfile(final String path) throws IOException {
        return mkfile(new File(path));
    }

    public final static boolean mkfile(final File file) throws IOException {
        final File parent = file.getParentFile();
        if (!parent.exists()) {
            parent.mkdirs();
        }
        return file.exists() ? false : file.createNewFile();
    }

    public static boolean mkdir(final Path path) {
        return mkdir(path.toFile());
    }

    public static boolean mkdir(final String path) {
        return mkdir(new File(path));
    }

    public static boolean mkdir(final File file) {
        return file.exists() ? false : file.mkdirs();
    }
}
