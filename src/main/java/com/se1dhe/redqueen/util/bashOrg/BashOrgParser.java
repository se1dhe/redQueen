    package com.se1dhe.redqueen.util.bashOrg;

    import org.jsoup.Jsoup;
    import org.jsoup.nodes.Document;
    import org.jsoup.nodes.Element;

    import java.io.IOException;
    import java.net.URL;

    public class BashOrgParser {


        private static Document getPage() throws IOException {
            String url = "http://bashorg.org/casual";
            return Jsoup.parse(new URL(url), 3000);
        }

     public static String getQuote() throws IOException {
         Document page = getPage();
         Element divQ = page.select("div[class=q]").first();
         assert divQ != null;
         Element quote = divQ.select("div").last();
         assert quote != null;
         return quote.toString().replaceAll("<div>","").replaceAll("</div>", "").replaceAll("<br>","");
     }
    }
