package me.akadeax.keksguilds.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

public class JsonUtil {
    public static final GsonBuilder builder = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation();

    /**
     * writes 'input' to 'file' in json
     */
    public static<T> void writeJson(File file, T input) {
        if(!isJson(file)) return;

        Gson gson = builder.create();
        try {
            FileWriter fw = new FileWriter(file);
            String json = gson.toJson(input, input.getClass());
            fw.write(json);
            fw.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * reads object of type 'clazz' from json-file 'file'
     */
    public static<T> T readJson(File file, Class<T> clazz) {
        if(!isJson(file)) return null;

        Gson gson = builder.create();
        try {
            FileReader fr = new FileReader(file);
            String json = new String(Files.readAllBytes(file.toPath()));
            return gson.fromJson(json, clazz);
        } catch(IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static boolean isJson(File f) {
        return f.getName().toLowerCase().endsWith(".json");
    }
}
