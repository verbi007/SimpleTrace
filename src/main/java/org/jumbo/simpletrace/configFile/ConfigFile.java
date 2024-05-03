package org.jumbo.simpletrace.configFile;

import org.jumbo.simpletrace.BaseController;
import org.jumbo.simpletrace.constants.Constants;

import java.io.*;

import java.util.Properties;

public class ConfigFile {
    Properties mainProperties;
    private File file;
    private String path;
    private String theme;
    private String number;
    private String token;
    private String repeats;

    public ConfigFile() {
        this.path = "./config.properties";
        mainProperties = new Properties();
        this.file = new File(path);
        readApplicationSet();
    }

    public File getFile() {
        return file;
    }

    public ConfigFile setFile(File file) {
        this.file = file;
        return this;
    }

    public String getPath() {
        return path;
    }

    public ConfigFile setPath(String path) {
        this.path = path;
        return this;
    }

    public String getTheme() {
        return theme;
    }

    public ConfigFile setTheme(String theme) {
        this.theme = theme;
        return this;
    }

    public String getNumber() {
        return number;
    }

    public ConfigFile setNumber(String number) {
        this.number = number;
        return this;
    }

    public String getToken() {
        return token;
    }

    public ConfigFile setToken(String token) {
        this.token = token;
        return this;
    }

    public String getRepeats() {
        return repeats;
    }

    public ConfigFile setRepeats(String repeats) {
        this.repeats = repeats;
        return this;
    }

    public void writeApplicationSet() {
        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            byte[] themeBuffer = ("theme=" + theme + "\n").getBytes();
            byte[] numberBuffer = ("number=" +number + "\n").getBytes();
            byte[] tokenBuffer = ("token=" + token + "\n").getBytes();
            byte[] repeatsBuffer = ("repeats=" + repeats).getBytes();


            outputStream.write(themeBuffer);
            outputStream.write(numberBuffer);
            outputStream.write(tokenBuffer);
            outputStream.write(repeatsBuffer);

            outputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void readApplicationSet() {
        try {
            FileInputStream inputStream = new FileInputStream(file);
            mainProperties.load(inputStream);
            inputStream.close();

            theme = mainProperties.getProperty("theme");
            number = mainProperties.getProperty("number");
            token = mainProperties.getProperty("token");
            repeats = mainProperties.getProperty("repeats");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void defaultConfiguration() {
        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            theme = Constants.DARK;
            number = Constants.NUMBER_4;
            token = Constants.TOKEN_4;
            repeats = Constants.REPEATS.toString();


            byte[] themeBuffer = ("theme=" + theme + "\n").getBytes();
            byte[] numberBuffer = ("number=" +number + "\n").getBytes();
            byte[] tokenBuffer = ("token=" + token + "\n").getBytes();
            byte[] repeatsBuffer = ("repeats=" + repeats).getBytes();

            outputStream.write(themeBuffer);
            outputStream.write(numberBuffer);
            outputStream.write(tokenBuffer);
            outputStream.write(repeatsBuffer);

            outputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
