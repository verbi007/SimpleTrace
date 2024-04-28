package org.jumbo.simpletrace.configFile;

import org.jumbo.simpletrace.constants.Constants;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ConfigFile {
    private File file;
    private String path;
    private String theme;
    private String number;
    private String token;
    private String repeats;

    public ConfigFile() {
        this.path = "src/main/resources/config.txt";
        this.file = new File(this.path);
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
            byte[] themeBuffer = (theme + "\n").getBytes();
            byte[] numberBuffer = (number + "\n").getBytes();
            byte[] tokenBuffer = (token + "\n").getBytes();
            byte[] repeatsBuffer = (repeats).getBytes();


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
        String data = "";
        try {
            FileInputStream inputStream = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                data = new String(buffer, 0, bytesRead);
            }
            inputStream.close();
            String[] dt = data.split("\n");
            theme = dt[0].trim();
            number = dt[1].trim();
            token = dt[2].trim();
            repeats = dt[3].trim();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void defaultConfiguration() {
        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            byte[] themeBuffer = (Constants.DARK + "\n").getBytes();
            byte[] numberBuffer = (Constants.NUMBER + "\n").getBytes();
            byte[] tokenBuffer = (Constants.TOKEN + "\n").getBytes();
            byte[] repeatsBuffer = (Constants.REPEATS + "\n").getBytes();

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
