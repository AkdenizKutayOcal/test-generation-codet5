package org.jsoup.integration;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.Assert.*;

/**

 Integration test: parses from real-world example HTML.

 @author Jonathan Hedley, jonathan@hedley.net */
public class ParseTest {

@Test
public void testGoogleSearchIpod7() throws IOException { 
     File in = getFile("/htmltests/google-ipod.html"); 
     Document doc = Jsoup.parse(in, "UTF-8", "http://www.google.com/search?hl=en&q=ipod&aq=f&oq=&aqi=g10"); 
     assertEquals("ipod - Google Search", doc.title()); 
     Elements results = doc.select("h3.r > a"); 
     assertEquals(12, results.size()); 
     assertEquals("http://news.google.com/news?hl=en&q=ipod&um=1&ie=UTF-8&ei=uYlKS4SbBoGg6gPf-5XXCw&sa=X&oi=news_group&ct=title&resnum=1&ved=0CCIQsQQwAA", results.get(0).attr("href")); 
     assertEquals("http://www.apple.com/itunes/", results.get(1).attr("href")); 
 }
@Test
public void testNewsHomepage9() throws IOException { 
     File in = getFile("/htmltests/news-com-au-home.html"); 
     Document doc = Jsoup.parse(in, "UTF-8", "http://www.news.com.au/"); 
     assertEquals("News.com.au | News from Australia and around the world online | NewsComAu", doc.title()); 
     assertEquals("Brace yourself for Metro meltdown", doc.select(".id1225817868581 h4").text().trim()); 
     Element a = doc.select("a[href=/entertainment/horoscopes]").first(); 
     assertEquals("/entertainment/horoscopes", a.attr("href")); 
     assertEquals("http://www.news.com.au/entertainment/horoscopes", a.attr("abs:href")); 
     Element hs = doc.select("a[href*=naughty-corners-are-a-bad-idea]").first(); 
     assertEquals("http://www.heraldsun.com.au/news/naughty-corners-are-a-bad-idea-for-kids/story-e6frf7jo-1225817899003", hs.attr("href")); 
     assertEquals(hs.attr("href"), hs.attr("abs:href")); 
 }
@Test
public void testBinary26() throws IOException { 
     File in = getFile("/htmltests/thumb.jpg"); 
     Document doc = Jsoup.parse(in, "UTF-8"); 
     assertTrue(doc.text().contains("gd-jpeg")); 
 }
     
    @Test public void testNewsHomepage() throws IOException {
        File in = getFile("/htmltests/news-com-au-home.html");
        Document doc = Jsoup.parse(in, "UTF-8", "http://www.news.com.au/");
        assertEquals("News.com.au | News from Australia and around the world online | NewsComAu", doc.title());
        assertEquals("Brace yourself for Metro meltdown", doc.select(".id1225817868581 h4").text().trim());
        
        Element a = doc.select("a[href=/entertainment/horoscopes]").first();
        assertEquals("/entertainment/horoscopes", a.attr("href"));
        assertEquals("http://www.news.com.au/entertainment/horoscopes", a.attr("abs:href"));
        
        Element hs = doc.select("a[href*=naughty-corners-are-a-bad-idea]").first();
        assertEquals("http://www.heraldsun.com.au/news/naughty-corners-are-a-bad-idea-for-kids/story-e6frf7jo-1225817899003", hs.attr("href"));
        assertEquals(hs.attr("href"), hs.attr("abs:href"));
    }
    
    @Test public void testGoogleSearchIpod() throws IOException {
        File in = getFile("/htmltests/google-ipod.html");
        Document doc = Jsoup.parse(in, "UTF-8", "http://www.google.com/search?hl=en&q=ipod&aq=f&oq=&aqi=g10");
        assertEquals("ipod - Google Search", doc.title());
        Elements results = doc.select("h3.r > a");
        assertEquals(12, results.size());
        assertEquals("http://news.google.com/news?hl=en&q=ipod&um=1&ie=UTF-8&ei=uYlKS4SbBoGg6gPf-5XXCw&sa=X&oi=news_group&ct=title&resnum=1&ved=0CCIQsQQwAA", 
                results.get(0).attr("href"));
        assertEquals("http://www.apple.com/itunes/",
                results.get(1).attr("href"));
    }
    
    @Test public void testBinary() throws IOException {
        File in = getFile("/htmltests/thumb.jpg");
        Document doc = Jsoup.parse(in, "UTF-8");
        // nothing useful, but did not blow up
        assertTrue(doc.text().contains("gd-jpeg"));
    }
    
    @Test public void testYahooJp() throws IOException {
        File in = getFile("/htmltests/yahoo-jp.html");
        Document doc = Jsoup.parse(in, "UTF-8", "http://www.yahoo.co.jp/index.html"); // http charset is utf-8.
        assertEquals("Yahoo! JAPAN", doc.title());
        Element a = doc.select("a[href=t/2322m2]").first();
        assertEquals("http://www.yahoo.co.jp/_ylh=X3oDMTB0NWxnaGxsBF9TAzIwNzcyOTYyNjUEdGlkAzEyBHRtcGwDZ2Ex/t/2322m2", 
                a.attr("abs:href")); // session put into <base>
        assertEquals("全国、人気の駅ランキング", a.text());
    }
    
    File getFile(String resourceName) {
        try {
            File file = new File(ParseTest.class.getResource(resourceName).toURI());
            return file;
        }
        catch (URISyntaxException e) {
            throw new IllegalStateException(e);
        }
    }
}
