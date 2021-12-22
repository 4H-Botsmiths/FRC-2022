/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.wpi.first.util.RuntimeDetector;
import edu.wpi.first.util.RuntimeLoader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class CombinedRuntimeLoader {
    private static String extractionDirectory;

    private CombinedRuntimeLoader() {
    }

    public static synchronized String getExtractionDirectory() {
        return extractionDirectory;
    }

    private static synchronized void setExtractionDirectory(String directory) {
        extractionDirectory = directory;
    }

    public static native String setDllDirectory(String var0);

    private static String getLoadErrorMessage(String libraryName, UnsatisfiedLinkError ule) {
        StringBuilder msg = new StringBuilder(512);
        msg.append(libraryName).append(" could not be loaded from path\n\tattempted to load for platform ").append(RuntimeDetector.getPlatformPath()).append("\nLast Load Error: \n").append(ule.getMessage()).append('\n');
        if (RuntimeDetector.isWindows()) {
            msg.append("A common cause of this error is missing the C++ runtime.\nDownload the latest at https://support.microsoft.com/en-us/help/2977003/the-latest-supported-visual-c-downloads\n");
        }
        return msg.toString();
    }

    public static <T> List<String> extractLibraries(Class<T> clazz, String resourceName) throws IOException {
        Map map;
        TypeReference<HashMap<String, Object>> typeRef = new TypeReference<HashMap<String, Object>>(){};
        ObjectMapper mapper = new ObjectMapper();
        try (InputStream stream = clazz.getResourceAsStream(resourceName);){
            map = mapper.readValue(stream, typeRef);
        }
        Path platformPath = Paths.get(RuntimeDetector.getPlatformPath(), new String[0]);
        String platform = platformPath.getName(0).toString();
        String arch = platformPath.getName(1).toString();
        Map platformMap = (Map)map.get(platform);
        List fileList = (List)platformMap.get(arch);
        String extractionPathString = CombinedRuntimeLoader.getExtractionDirectory();
        if (extractionPathString == null) {
            String hash = (String)map.get("hash");
            String defaultExtractionRoot = RuntimeLoader.getDefaultExtractionRoot();
            Path extractionPath = Paths.get(defaultExtractionRoot, platform, arch, hash);
            extractionPathString = extractionPath.toString();
            CombinedRuntimeLoader.setExtractionDirectory(extractionPathString);
        }
        ArrayList<String> extractedFiles = new ArrayList<String>();
        byte[] buffer = new byte[65536];
        for (String file : fileList) {
            InputStream stream = clazz.getResourceAsStream(file);
            try {
                Objects.requireNonNull(stream);
                Path outputFile = Paths.get(extractionPathString, new File(file).getName());
                extractedFiles.add(outputFile.toString());
                if (outputFile.toFile().exists()) continue;
                Path parent = outputFile.getParent();
                if (parent == null) {
                    throw new IOException("Output file has no parent");
                }
                parent.toFile().mkdirs();
                OutputStream os = Files.newOutputStream(outputFile, new OpenOption[0]);
                try {
                    int readBytes;
                    while ((readBytes = stream.read(buffer)) != -1) {
                        os.write(buffer, 0, readBytes);
                    }
                }
                finally {
                    if (os == null) continue;
                    os.close();
                }
            }
            finally {
                if (stream == null) continue;
                stream.close();
            }
        }
        return extractedFiles;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void loadLibrary(String libraryName, List<String> extractedFiles) throws IOException {
        String oldDllDirectory;
        block8: {
            String currentPath = null;
            oldDllDirectory = null;
            try {
                if (RuntimeDetector.isWindows()) {
                    String extractionPathString = CombinedRuntimeLoader.getExtractionDirectory();
                    oldDllDirectory = CombinedRuntimeLoader.setDllDirectory(extractionPathString);
                }
                for (String extractedFile : extractedFiles) {
                    if (!extractedFile.contains(libraryName)) continue;
                    currentPath = extractedFile;
                    System.load(extractedFile);
                    if (oldDllDirectory == null) return;
                    break block8;
                }
                throw new IOException("Could not find library " + libraryName);
            }
            catch (UnsatisfiedLinkError ule) {
                throw new IOException(CombinedRuntimeLoader.getLoadErrorMessage(currentPath, ule));
            }
            catch (Throwable throwable) {
                if (oldDllDirectory == null) throw throwable;
                CombinedRuntimeLoader.setDllDirectory(oldDllDirectory);
                throw throwable;
            }
        }
        CombinedRuntimeLoader.setDllDirectory(oldDllDirectory);
    }

    public static <T> void loadLibraries(Class<T> clazz, String ... librariesToLoad) throws IOException {
        List<String> extractedFiles = CombinedRuntimeLoader.extractLibraries(clazz, "/ResourceInformation.json");
        String currentPath = "";
        try {
            if (RuntimeDetector.isWindows()) {
                String extractionPathString = CombinedRuntimeLoader.getExtractionDirectory();
                currentPath = Paths.get(extractionPathString, "WindowsLoaderHelper.dll").toString();
                System.load(currentPath);
            }
        }
        catch (UnsatisfiedLinkError ule) {
            throw new IOException(CombinedRuntimeLoader.getLoadErrorMessage(currentPath, ule));
        }
        for (String library : librariesToLoad) {
            CombinedRuntimeLoader.loadLibrary(library, extractedFiles);
        }
    }
}

