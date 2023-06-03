package com.mirea.opagg;

import com.mirea.opagg.model.Place;
import com.github.demidko.aot.MorphologyTag;
import org.apache.commons.lang3.ArrayUtils;
import org.jsoup.Jsoup;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.*;
import java.util.stream.Collectors;

import static com.github.demidko.aot.WordformMeaning.lookupForMeanings;

public class PlaceCreator {


    public static PlaceStub createPlaceFirstStep(String request) throws ParserConfigurationException, IOException, SAXException {
        RestTemplate restTemplate = new RestTemplate();
//
//		String url = "https://yandex.ru/search/xml?user=lamfoid&key=03.318996266:113e9b8baeaab9cf70e436a8a156754f&query="+request.replaceAll("  *", "+")+"&l10n=ru&sortby=rlv&filter=none&groupby=attr";
//		ResponseEntity<String> response
//				= restTemplate.getForEntity(url, String.class);
//		System.out.println(response.getBody());
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
//		Document document =  builder.parse(new InputSource(new StringReader(response.getBody())));

        Document document = builder.parse(new File("C:/Users/mitya/Desktop/opagg/src/main/resources/test.xml"));

        String gisurl = document.getElementsByTagName("url").item(0).getTextContent();
//        url = "https://yandex.ru/search/xml?user=lamfoid&key=03.318996266:113e9b8baeaab9cf70e436a8a156754f&query="+request.replaceAll("  *", "+")+"+tripadvisor&l10n=ru&sortby=rlv&filter=none&groupby=attr";
//        response = restTemplate.getForEntity(url, String.class);
//        document =  builder.parse(new InputSource(new StringReader(response.getBody())));
        document = builder.parse(new File("C:/Users/mitya/Desktop/opagg/src/main/resources/test2.xml"));
        String tripurl = document.getElementsByTagName("url").item(0).getTextContent();
//        url = "https://yandex.ru/search/xml?user=lamfoid&key=03.318996266:113e9b8baeaab9cf70e436a8a156754f&query="+request.replaceAll("  *", "+")+"+zoon&l10n=ru&sortby=rlv&filter=none&groupby=attr";
//        response = restTemplate.getForEntity(url, String.class);
//        document =  builder.parse(new InputSource(new StringReader(response.getBody())));
        document = builder.parse(new File("C:/Users/mitya/Desktop/opagg/src/main/resources/test3.xml"));
        String zoonurl = document.getElementsByTagName("url").item(0).getTextContent();

        org.jsoup.nodes.Document gispage = Jsoup.connect(gisurl + "/tab/reviews").get();
        org.jsoup.nodes.Document gisparse = Jsoup.parse(gispage.html());

        org.jsoup.nodes.Document trippage = Jsoup.connect(tripurl).get();
        org.jsoup.nodes.Document tripparse = Jsoup.parse(trippage.html());

        org.jsoup.nodes.Document zoonpage = Jsoup.connect(zoonurl).get();
        org.jsoup.nodes.Document zoonparse = Jsoup.parse(zoonpage.html());


        String name = tripparse.select("h1.HjBfq").text();
        PlaceStub placestub = new PlaceStub(name, tripparse, zoonparse, gisparse);
        return placestub;
    }

        public static Place createPlaceSecondStep(PlaceStub stub) throws ParserConfigurationException, IOException, SAXException {
            org.jsoup.nodes.Document gisparse = stub.getGisparse();
            org.jsoup.nodes.Document tripparse = stub.getTripparse();
            org.jsoup.nodes.Document zoonparse = stub.getZoonparse();
            String[] gis = ArrayUtils.addAll(gisparse.select("a._ayej9u3").text().split(" "), gisparse.select("a._1it5ivp").text().split(" "));
            String[] trip = tripparse.select("p.partial_entry").text().split(" ");
            String[] zoon = ArrayUtils.addAll(zoonparse.select("span.js-comment-content").text().split(" "), zoonparse.select("span.js-comment-appended-text").text().split(" "));

            String priceandtype = tripparse.select("a.dlMOJ").text();

            System.out.println(priceandtype);


            String rating = gisparse.select("div._y10azs").text();

            String contacts = zoonparse.select("a.tel-phone").attr("href");
            System.out.println(rating);

            System.out.println(contacts);

            String adress = tripparse.select("a.YnKZo").select("span.yEWoV").text();
            adress = adress.substring(0, adress.length() - 5);

            System.out.println(adress);

            RestTemplate restTemplate = new RestTemplate();
            String url = "https://geocode-maps.yandex.ru/1.x/?apikey=3a27af8d-1f32-43af-baac-1ffdd8a26629&geocode="+adress.replaceAll("  *", "+");
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document adressdocument = builder.parse(new InputSource(new StringReader(response.getBody())));
            String point = adressdocument.getElementsByTagName("pos").item(0).getTextContent();

            String[] before = ArrayUtils.addAll(gis, trip);
            before = ArrayUtils.addAll(before, zoon);

            List<String> exceptions = Arrays.asList("место", "еда", "блюдо", "минута", "час", "гость", "заказ");
            List<String> results = new ArrayList<>();

            for (int i = 0; i < before.length; i++) {

                String cur = before[i].replaceAll("[^\\p{L}\\p{N}]+", "").toLowerCase();
                var meanings = lookupForMeanings(cur);
                if (!meanings.isEmpty())
                {
                    if (meanings.get(0).getMorphology().contains(MorphologyTag.Noun) && !exceptions.contains(meanings.get(0).getLemma().toString()))
                    {
                        results.add(meanings.get(0).getLemma().toString());
                    }
                }
            }

            HashMap<String, Integer> map = new HashMap<String, Integer>();
            String tempStr;
            for (int i = 0; i < results.size(); i++)
            {
                tempStr = results.get(i);
                if(map.containsKey(tempStr))
                {
                    map.put(tempStr, map.get(tempStr) + 1);
                }
                else
                {
                    map.put(tempStr,1);
                }
            }

            ValueComparator bvc =  new ValueComparator(map);
            TreeMap<String,Integer> sorted_map = new TreeMap<String,Integer>(bvc);
            sorted_map.putAll(map);

            List<String> tags = sorted_map.entrySet().stream()
                    .map(Map.Entry::getKey)
                    .limit(5)
                    .collect(Collectors.toList());
            System.out.println(tags.toString());
            Place resultPlace = new Place(stub.getName(), priceandtype, rating, adress, point, contacts, String.join(", ", tags));

            return resultPlace;
     }
}
