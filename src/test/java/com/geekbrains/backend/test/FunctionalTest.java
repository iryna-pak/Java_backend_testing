package com.geekbrains.backend.test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public abstract class FunctionalTest {


    //  зачитывает проперти файл
    //  создание экземпляра проперти
    //  пробег по строкам файла с разделением по символу "="
    //  первый аргумент это ключ, второй значение

    public static Properties readProperties() throws Exception {
        Properties properties = new Properties();   // Экземпляр проперти
        Files.readAllLines(Path.of(
                FunctionalTest.class.getResource("test.properties").toURI()))
                .forEach(str -> {
                    String[] props = str.split("=");
                    properties.setProperty(props[0].trim(), props[1].trim());
                });
        return properties;
    }

    // зачитывает ресурс и возвращает как строку
    public String getStringResource(String name) throws IOException {
        String dir = getClass().getSimpleName();
        byte[] bytes = getClass().getResourceAsStream(dir + "/" + name)
                .readAllBytes();
        return new String(bytes, StandardCharsets.UTF_8);
    }

    // зачитывает ресурс и возвращает как файл
    public File getFileResource(String name) {
        String dir = getClass().getSimpleName();
        return new File(getClass().getResource(dir + "/" + name).getFile());
    }

}
