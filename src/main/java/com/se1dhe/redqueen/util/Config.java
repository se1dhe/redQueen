package com.se1dhe.redqueen.util;

import lombok.extern.log4j.Log4j2;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;


@Log4j2
public class Config {


    public static final String CONFIGURATION_BOT_FILE = "config/bot.properties";
    public static final String CONFIGURATION_DB_FILE = "config/database.properties";

    public static String BOT_NAME;
    public static String BOT_TOKEN;
    public static String BOT_LANGUAGE;
    public static String DB_URL;
    public static String DB_USER;
    public static String DB_PWD;


    public static void load() {

        Properties botSettings = new Properties();
        Properties botDBSettings = new Properties();

        try (InputStream is = new FileInputStream(CONFIGURATION_BOT_FILE)) {
            botSettings.load(is);
            is.close();
            log.info("Конфиг Бота загружен успешно!");
        } catch (Exception e) {
            log.error("Ошибка загрузки конфига Бота");
        }

        try (InputStream is = new FileInputStream(CONFIGURATION_DB_FILE)) {
            botDBSettings.load(is);
            is.close();
            log.info("Конфиг Базы Данных загружен успешно!");
        } catch (Exception e) {
            log.error("Ошибка загрузки конфига Базы Данных");
        }


        BOT_NAME = botSettings.getProperty("bot.name", "cryptootest_bot:");
        BOT_TOKEN = botSettings.getProperty("bot.token", "644918045:AAEmu88-rJ4iSGBNstThz_bx1VNR8qqjELE");
        BOT_LANGUAGE = botSettings.getProperty("bot.language", "ru");

        DB_URL = botDBSettings.getProperty("bot.url", "jdbc:mysql://127.0.0.1:3306/redQueen?useUnicode=true&character_set_server=utf8mb4&autoReconnect=true&interactiveClient=true&serverTimezone=Europe/Kiev&useSSL=false");
        DB_USER = botDBSettings.getProperty("bot.username", "root");
        DB_PWD = botDBSettings.getProperty("bot.password", "1234");


    }

}
