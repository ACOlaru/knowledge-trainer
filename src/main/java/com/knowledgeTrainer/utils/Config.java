package com.knowledgeTrainer.utils;

import java.io.InputStream;
import java.util.Properties;

public class Config {

    private final Properties properties = new Properties();

    public Config() {
        try (InputStream is = getClass().getClassLoader()
                .getResourceAsStream("application.properties")) {
            if (is == null) {
                throw new RuntimeException("application.properties not found");
            }
            properties.load(is);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load config", e);
        }
    }

    public String getApiKey() {
        return properties.getProperty("openaiKey");
    }

    public String getExportPath() {
        return properties.getProperty("export.path");
    }

    public String getModel() {
        return properties.getProperty("model");
    }
}

