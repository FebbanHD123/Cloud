package de.febanhd.cloud.wrapper.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Objects;

public class FileDownloader {

    private URL URL;
    private String FILE_NAME;

    public FileDownloader(String link, String fileName) {
        try {
            this.FILE_NAME = fileName;
            this.URL = new URL(link);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public FileDownloader(URL url, String fileName) {
        this.FILE_NAME = fileName;
        this.URL = Objects.requireNonNull(url);
    }

    public File downloadAndPaste(File pasteDir) {
        ReadableByteChannel readableByteChannel = null;
        FileOutputStream fileOutputStream = null;
        File file = null;
        try {
            readableByteChannel = Channels.newChannel(this.URL.openStream());
            fileOutputStream = new FileOutputStream(pasteDir.getAbsoluteFile() + "/" + FILE_NAME);
            fileOutputStream.getChannel().transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
            file = new File(pasteDir.getAbsoluteFile(), FILE_NAME);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(fileOutputStream != null)
                    fileOutputStream.close();
                if(readableByteChannel != null)
                    readableByteChannel.close();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }
}
