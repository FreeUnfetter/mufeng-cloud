package com.mufeng.mufengGenerator.util;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtils {

    public static void zipFolder(String sourceFolder, OutputStream out) throws IOException {
        try (ZipOutputStream zos = new ZipOutputStream(out)) {
            Path sourcePath = Paths.get(sourceFolder);

            if (!Files.exists(sourcePath)) {
                throw new IOException("源文件夹不存在: " + sourceFolder);
            }

            Files.walk(sourcePath)
                    .filter(path -> !Files.isDirectory(path))
                    .forEach(path -> {
                        try {
                            String relativePath = sourcePath.relativize(path).toString();
                            ZipEntry zipEntry = new ZipEntry(relativePath);
                            zos.putNextEntry(zipEntry);
                            Files.copy(path, zos);
                            zos.closeEntry();
                        } catch (IOException e) {
                            throw new RuntimeException("压缩文件失败: " + path, e);
                        }
                    });
        }
    }

}
