package ua.estate.rialto.service.crawler.olx;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import ua.estate.rialto.to.olx.Result;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
public class OlxCrawlerServiceImpl2 {
    private static final String OLX_URL = "https://www.olx.ua/i2/nedvizhimost/?json=1&search[order]=created_at:desc";
    private static final String GOOGLE_API_KEY = "AIzaSyBUnvqI-Y7CPp2UNrrxLMvkrXBiXVHpfTU";
    private static final Pattern patternClear = Pattern.compile("[\n\r]"); // грязь в строке

    public static void main(String[] args) {
        // Create a new RestTemplate instance
        RestTemplate restTemplate = new RestTemplate();

        // Make the HTTP GET request, marshaling the response to a String
        try {
            for (int i = 1; i <= 10; i++) {
                log.info("Getting page {}", i);
                String pagedUrl = OLX_URL + "&page=" + i;
                Result result = restTemplate.getForObject(pagedUrl, Result.class, "");

                if (result.getAds() != null) {
                    log.info("Got result! Ads size: {}", result.getAds().size());
                    result.getAds().forEach(ad -> {
                        try {
                            //удалить лишнее
                            ad.setDescription(patternClear.matcher(ad.getDescription()).replaceAll(""));

//                            https://developers.google.com/maps/documentation/geocoding/start?hl=en
                            final String baseUrl = "http://maps.googleapis.com/maps/api/geocode/json?language=ru&sensor=false&latlng="
                                    + ad.getMapLat() + "," + ad.getMapLon();// +"&key=" + GOOGLE_API_KEY;
                            final Map<String, String> params = new HashMap<>();
//                            params.put("language", "ru");// язык данных, на котором мы хотим получить
//                            params.put("sensor", "false");
//                            params.put("latlng", "55.735893,37.527420");
//                            final String url = baseUrl + '?' + encodeParams(params);

//                            https://habrahabr.ru/post/148986/
//                            JSONObject  response = restTemplate.getForObject(baseUrl, JSONObject.class, "");
                            System.out.println(restTemplate.getForObject(baseUrl, String.class, ""));
//                            final JSONObject location = response.getJSONArray("results").getJSONObject(0);
//                            final String formattedAddress = location.getString("formatted_address");
//                            System.out.println(formattedAddress);

                            //выставить картинки
                            Document doc = Jsoup.connect(ad.getUrl())
                                    .validateTLSCertificates(false)
                                    .followRedirects(true)
                                    .ignoreHttpErrors(true)
                                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.133 Safari/537.36")
                                    .timeout(20000)
                                    .get();
                            Elements elements = doc.select("div.photo-glow > img");
                            List<String> images = elements.stream()
                                    .map(el -> el.attr("src"))
                                    .collect(Collectors.toList());

                            ad.setImages(images);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        System.out.println(ad);
                        log.info("Find interesting ad: {}", ad);
//                        App.openUrl(ads.getUrl());
                    });
                }

            }
            log.debug("Exiting");
        } catch (RestClientException e) {
            log.error("Can't get result", e);
        } catch (Exception e){
            log.error("Can't get result", e);
        } catch (Throwable e){
            log.error("Can't get result", e);
        }
    }
}
