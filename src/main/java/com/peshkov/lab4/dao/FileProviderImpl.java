package com.peshkov.lab4.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Arrays;

public final class FileProviderImpl implements FileProvider {

    private void provideFolders(String name) {
        var path = name.split("[\\\\/]");
        var file = new File(String.join("\\", Arrays.copyOfRange(path, 0, path.length - 1)));
        if (!file.isDirectory()) file.mkdirs();
    }

    @Override
    public File provide(String name, boolean overwrite) {
        provideFolders(name);
        var file = new File(name);
        if (file.exists()) {
            if (file.isDirectory()) {
                throw new RuntimeException("File " + file.getName() + " is directory");
            }
            if (!overwrite) {
                throw new RuntimeException("File " + file.getName() + " already exists");
            }
            // clearing contents of the file
            try {
                new FileWriter(file).close();
            } catch (Exception e) {
                throw new RuntimeException(e + " " + name);
            }
        } else try {
            if (!file.createNewFile()) throw new RuntimeException("Could not create new file");
        } catch (Exception e) {
            throw new RuntimeException(e + " " + name);
        }
        return file;
    }

    @Override
    public File provide(String name) {
        return provide(name, false);
    }

    @Override
    public File find(String name) throws FileNotFoundException {
        var file = new File(name);
        if (!file.exists() || file.isDirectory()) {
            throw new FileNotFoundException("File " + name + " not found");
        }
        return file;
    }

    @Override
    public File findOrProvide(String name) {
        var file = new File(name);
        if (file.isFile()) return file;
        try {
            provideFolders(name);
            if (!file.createNewFile()) throw new Exception("File " + name + " cannot be created");
        } catch (Exception e) {
            throw new RuntimeException(e + " " + name);
        }
        return file;
    }
}
