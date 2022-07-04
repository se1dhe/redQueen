package com.se1dhe.redqueen.util.anekdotRu;

import com.se1dhe.redqueen.RedQueenApplication;
import com.se1dhe.redqueen.bot.core.AbstractTelegramBot;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class AnekdotParser {
    private static Document getPage() throws IOException {
        String url = "https://www.anekdot.ru/random/mem/";
        return Jsoup.parse(new URL(url), 3000);
    }

    public static String getPicture() throws IOException {
       Document page = getPage();
        Element divQ = page.select("div[class=text]").first();
        assert divQ != null;
        return divQ.select("img").attr("src");

    }

    public static void download() throws IOException {
        Files.deleteIfExists(
                Paths.get("./img/picture.jpg"));
        try(InputStream in = new URL(getPicture()).openStream()){
            Files.copy(in, Paths.get("./img/picture.jpg"));
        }
    }





}
