package com.github.classyex.pdftool;

import java.io.File;

public class FileUtils {

    public static File[] getSplitFiles(String path) {
        File directory = new File(path);
        File[] files = directory.listFiles();
        if (files == null) {
            return null;
        }
        return files;
    }

    public static boolean isPdfFile(String path) {
        File file = new File(path);
        return file.getName().endsWith(".pdf");
    }

    public static void initTempDir(String path) {
        File directory = new File(path);
        if (directory.exists()) {
            directory.delete();
        }
        directory.mkdirs();
    }

    public static void clearFiles(String path) {
        File file = new File(path);
        if (file.exists()) {
            deleteFile(file);
        }
    }

    public static void deleteFile(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                deleteFile(files[i]);
            }
        }
        file.delete();
    }

}
