/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.util;

import edu.wpi.first.util.RuntimeDetector;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;
import java.util.Scanner;

public final class RuntimeLoader<T> {
    private static String defaultExtractionRoot;
    private final String m_libraryName;
    private final Class<T> m_loadClass;
    private final String m_extractionRoot;

    public static synchronized String getDefaultExtractionRoot() {
        if (defaultExtractionRoot != null) {
            return defaultExtractionRoot;
        }
        String home = System.getProperty("user.home");
        defaultExtractionRoot = Paths.get(home, ".wpilib", "nativecache").toString();
        return defaultExtractionRoot;
    }

    public RuntimeLoader(String libraryName, String extractionRoot, Class<T> cls) {
        this.m_libraryName = libraryName;
        this.m_loadClass = cls;
        this.m_extractionRoot = extractionRoot;
    }

    private String getLoadErrorMessage(UnsatisfiedLinkError ule) {
        StringBuilder msg = new StringBuilder(512);
        msg.append(this.m_libraryName).append(" could not be loaded from path or an embedded resource.\n\tattempted to load for platform ").append(RuntimeDetector.getPlatformPath()).append("\nLast Load Error: \n").append(ule.getMessage()).append('\n');
        if (RuntimeDetector.isWindows()) {
            msg.append("A common cause of this error is missing the C++ runtime.\nDownload the latest at https://support.microsoft.com/en-us/help/2977003/the-latest-supported-visual-c-downloads\n");
        }
        return msg.toString();
    }

    public void loadLibrary() throws IOException {
        try {
            System.loadLibrary(this.m_libraryName);
        }
        catch (UnsatisfiedLinkError ule) {
            String hashName = RuntimeDetector.getHashLibraryResource(this.m_libraryName);
            String resname = RuntimeDetector.getLibraryResource(this.m_libraryName);
            try (InputStream hashIs = this.m_loadClass.getResourceAsStream(hashName);){
                if (hashIs == null) {
                    throw new IOException(this.getLoadErrorMessage(ule));
                }
                try (Scanner scanner = new Scanner(hashIs, StandardCharsets.UTF_8.name());){
                    String hash = scanner.nextLine();
                    File jniLibrary = new File(this.m_extractionRoot, resname + "." + hash);
                    try {
                        System.load(jniLibrary.getAbsolutePath());
                    }
                    catch (UnsatisfiedLinkError ule2) {
                        try (InputStream resIs = this.m_loadClass.getResourceAsStream(resname);){
                            if (resIs == null) {
                                throw new IOException(this.getLoadErrorMessage(ule));
                            }
                            File parentFile = jniLibrary.getParentFile();
                            if (parentFile == null) {
                                throw new IOException("JNI library has no parent file");
                            }
                            parentFile.mkdirs();
                            try (OutputStream os = Files.newOutputStream(jniLibrary.toPath(), new OpenOption[0]);){
                                int readBytes;
                                byte[] buffer = new byte[65535];
                                while ((readBytes = resIs.read(buffer)) != -1) {
                                    os.write(buffer, 0, readBytes);
                                }
                            }
                            System.load(jniLibrary.getAbsolutePath());
                        }
                    }
                }
            }
        }
    }

    public void loadLibraryHashed() throws IOException {
        try {
            System.loadLibrary(this.m_libraryName);
        }
        catch (UnsatisfiedLinkError ule) {
            String hash;
            String resname = RuntimeDetector.getLibraryResource(this.m_libraryName);
            try (InputStream is = this.m_loadClass.getResourceAsStream(resname);){
                MessageDigest md;
                if (is == null) {
                    throw new IOException(this.getLoadErrorMessage(ule));
                }
                try {
                    md = MessageDigest.getInstance("MD5");
                }
                catch (NoSuchAlgorithmException nsae) {
                    throw new RuntimeException("Weird Hash Algorithm?");
                }
                try (DigestInputStream dis = new DigestInputStream(is, md);){
                    byte[] buffer = new byte[65535];
                    while (dis.read(buffer) > -1) {
                    }
                    MessageDigest digest = dis.getMessageDigest();
                    byte[] digestOutput = digest.digest();
                    StringBuilder builder = new StringBuilder();
                    for (byte b : digestOutput) {
                        builder.append(String.format("%02X", b));
                    }
                    hash = builder.toString().toLowerCase(Locale.ENGLISH);
                }
            }
            if (hash == null) {
                throw new IOException("Weird Hash?");
            }
            File jniLibrary = new File(this.m_extractionRoot, resname + "." + hash);
            try {
                System.load(jniLibrary.getAbsolutePath());
            }
            catch (UnsatisfiedLinkError ule2) {
                try (InputStream resIs = this.m_loadClass.getResourceAsStream(resname);){
                    if (resIs == null) {
                        throw new IOException(this.getLoadErrorMessage(ule));
                    }
                    File parentFile = jniLibrary.getParentFile();
                    if (parentFile == null) {
                        throw new IOException("JNI library has no parent file");
                    }
                    parentFile.mkdirs();
                    try (OutputStream os = Files.newOutputStream(jniLibrary.toPath(), new OpenOption[0]);){
                        int readBytes;
                        byte[] buffer = new byte[65535];
                        while ((readBytes = resIs.read(buffer)) != -1) {
                            os.write(buffer, 0, readBytes);
                        }
                    }
                    System.load(jniLibrary.getAbsolutePath());
                }
            }
        }
    }
}

