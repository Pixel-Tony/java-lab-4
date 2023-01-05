package com.peshkov.lab4.dao;

import java.io.File;
import java.io.FileNotFoundException;

public interface FileProvider {
    File provide(String name, boolean overwrite);

    File provide(String name);

    File find(String name) throws FileNotFoundException;

    File findOrProvide(String name);
}
