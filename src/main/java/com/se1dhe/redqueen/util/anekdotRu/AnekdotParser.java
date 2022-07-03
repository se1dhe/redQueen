package com.se1dhe.redqueen.util.anekdotRu;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.net.URL;

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


}
