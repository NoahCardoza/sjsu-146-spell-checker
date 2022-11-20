import java.io.*;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class CacheableZip extends Cacheable {

    /**
     * Constructs a Cacheable instance.
     *
     * @param file the location to cache it to on the file system
     * @param uri  the uri to load it from if it's not cached
     */
    public CacheableZip(File file, URI uri) {
        super(file, uri);

        if (!uri.toString().endsWith(".zip")) {
            throw new RuntimeException("file is not a .zip file");
        }
    }

    /**
     * Ensures the resource exists on the file system otherwise it
     * downloads and unzips it to the specified location.
     *
     * @throws IOException if an error occurs making the http request
     * @throws InterruptedException if the http request is interrupted
     */
    @Override
    public void sync() throws IOException, InterruptedException {
        if (getFile().exists()) return;

        byte[] buffer = new byte[8 * 1024];

        URL resourceUrl = new URL(getUri().toString());
        URLConnection conn = resourceUrl.openConnection();
        InputStream initialStream = conn.getInputStream();

        File rootDirectory = getFile();

        if(!rootDirectory.exists()) {
            rootDirectory.mkdirs();
        }

        ZipInputStream zipInputStream = new ZipInputStream(conn.getInputStream());
        ZipEntry nextEntry = zipInputStream.getNextEntry();

        String rootDirectoryName = null;
        if (nextEntry != null && nextEntry.isDirectory()) {
            rootDirectoryName = nextEntry.getName();
            zipInputStream.closeEntry();
            nextEntry = zipInputStream.getNextEntry();
        }

        while (nextEntry != null) {
            String fileName = nextEntry.getName();
            if (rootDirectoryName != null) {
                fileName = fileName.replace(rootDirectoryName, "");
            }
            File newFile = new File(rootDirectory.getAbsolutePath() + File.separator + fileName);
            if (nextEntry.isDirectory()) {
                newFile.mkdir();
            } else {
                new File(newFile.getParent()).mkdirs();
                FileOutputStream fileOutputStream = new FileOutputStream(newFile);
                int len;
                while ((len = zipInputStream.read(buffer)) > 0) {
                    fileOutputStream.write(buffer, 0, len);
                }
                fileOutputStream.close();
            }
            zipInputStream.closeEntry();
            nextEntry = zipInputStream.getNextEntry();
        }

        zipInputStream.closeEntry();
        zipInputStream.close();
        initialStream.close();
    }
}
