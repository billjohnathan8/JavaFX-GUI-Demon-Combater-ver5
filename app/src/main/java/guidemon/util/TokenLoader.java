package guidemon.util;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystem;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.scene.image.Image;

public final class TokenLoader {
    private TokenLoader() {}

    /** 
     * JAR-safe loader for images under a classpath directory, e.g. "/guidemon/tokens".
     * Works in IDE ("file:" URLs) and in packaged JARs ("jar:" URLs).
     */
    public static List<Image> loadFromResources(String resourceDirAbsolute) {
        List<Image> out = new ArrayList<>();
        try {
            URL url = TokenLoader.class.getResource(resourceDirAbsolute);
            if (url == null) return out;

            String protocol = url.getProtocol();
            if ("file".equalsIgnoreCase(protocol)) {
                // Running from classes/resources directory
                Path dir = Paths.get(url.toURI());
                loadFromDirectoryStream(dir, out);

            } else if ("jar".equalsIgnoreCase(protocol)) {
                // Running from a JAR: url like jar:file:/path/app.jar!/guidemon/tokens
                URI jarUri = url.toURI(); // jar:file:/...!/guidemon/tokens
                // Split at "!/"
                String full = jarUri.toString();
                int bangIdx = full.indexOf("!/");
                if (bangIdx < 0) return out;

                URI zipUri = URI.create(full.substring(0, bangIdx)); // "jar:file:/...app.jar"
                String insidePath = full.substring(bangIdx + 1);     // "guidemon/tokens" (may begin with '/')
                if (insidePath.startsWith("/")) insidePath = insidePath.substring(1);

                FileSystem fs;
                try {
                    fs = FileSystems.getFileSystem(zipUri);

                } catch (FileSystemNotFoundException e) {
                    fs = FileSystems.newFileSystem(zipUri, Collections.emptyMap());

                }

                Path dirInJar = fs.getPath("/" + insidePath);
                loadFromDirectoryStream(dirInJar, out);

            } else {
                // Fallback: try URI then directory stream
                Path dir = Paths.get(url.toURI());
                loadFromDirectoryStream(dir, out);

            }
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
            
        }
        return out;
    }

    /**
     * Filesystem loader (useful if you keep tokens on disk, e.g. data/tokens).
     */
    public static List<Image> loadFromDir(Path dir) {
        List<Image> out = new ArrayList<>();
        if (dir == null || !Files.isDirectory(dir)) return out;
        try {
            loadFromDirectoryStream(dir, out);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return out;
    }

    // --- helpers ---

    private static void loadFromDirectoryStream(Path dir, List<Image> out) throws IOException {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            for (Path p : stream) {
                if (!Files.isRegularFile(p)) continue;
                String name = p.getFileName().toString().toLowerCase();
                if (!isImageFile(name)) continue;

                // Use URL form so JavaFX Image can load from file: or jar: seamlessly.
                String url = p.toUri().toString();
                Image img = new Image(url, 0, 0, true, true);
                if (!img.isError()) out.add(img);
            }
        }
    }

    private static boolean isImageFile(String name) {
        return name.endsWith(".png") || name.endsWith(".jpg")
            || name.endsWith(".jpeg") || name.endsWith(".gif");
    }
}