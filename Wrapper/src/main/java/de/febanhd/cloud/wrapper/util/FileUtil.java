package de.febanhd.cloud.wrapper.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.Comparator;

public class FileUtil {

    public static void copyDirToDir(File dir1, File dir2) throws IOException {
        File[] files = dir1.listFiles();
        for(File file : files) {
            Files.copy(file.toPath(), new File(dir2, file.getName()).toPath());
        }
    }

    public static void deleteDirectoryAndContents(Path path) throws IOException {
        Files.walk(path)
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                    .forEach(File::delete);
    }
}
