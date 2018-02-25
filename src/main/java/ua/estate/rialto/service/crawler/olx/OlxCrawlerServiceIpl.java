package ua.estate.rialto.service.crawler.olx;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlImage;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import lombok.extern.slf4j.Slf4j;
import ua.estate.rialto.to.olx.Result;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class OlxCrawlerServiceIpl {
    public static final String ROOMS_FROM="3";
    public static final String ROOMS_TO="4";
    public static String MAX_PAGE="30";
    private static final String OLX_URL = "https://www.olx.ua/i2/nedvizhimost/?json=1&search[order]=created_at:desc";
//            String.format("https://www.olx.ua/i2/nedvizhimost/prodazha-kvartir/kiev/?json=1&search[filter_float_number_of_rooms:from]=%1$s&search[filter_float_number_of_rooms:to]=%2$s&search[district_id]=9&search[order]=created_at:desc&search[filter_float_price:to]=%3$s",
//            ROOMS_FROM, ROOMS_TO, PRICE_TO);
// &search[filter_float_number_of_rooms:from]=%1$s - параметр количество комнат от
// &search[filter_float_number_of_rooms:to]=%2$s - параметр количество комнат до
// &search[district_id]=9 - район
// &search[filter_float_price:to]=%3$s - цена до

    public static void main(String[] args) {
        // Отключаем отображение ошибок
//        java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(Level.OFF);
        // Create a new RestTemplate instance
        RestTemplate restTemplate = new RestTemplate();

        // Используем движок Firefox (он совместим с большинством js-библиотек)
        WebClient webClient = new WebClient(BrowserVersion.FIREFOX_38);
        // Ждем, пока отработают ajax-запросы, выполняемые при загрузке страницы
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());

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
                            HtmlPage page = webClient.getPage(ad.getUrl());
                            List<HtmlImage> htmlImages = (List<HtmlImage>)page.getByXPath("//div[@class='photo-glow'] //img ");
                            List<String> images = htmlImages.stream().map(img -> img.getSrcAttribute()).collect(Collectors.toList());
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
        webClient.close();
    }

//    public static void getPhone(Ads ad) {

//        try {
            // Запрашиваем и рендерим веб-страницу
//            HtmlPage page = webClient.getPage(ad.getUrl());
            // Выводим исходный код страницы в консоль
//            List<HtmlImage> htmlImages = (List<HtmlImage>)page.getByXPath("//div[@class='photo-glow'] //img ");
//            List<String> images = htmlImages.stream().map(img -> img.getSrcAttribute()).collect(Collectors.toList());

//            ad.setPhotos();

//            System.out.println(page.asXml());
            // Закрываем headless-браузер, освобождаем память
//            webClient.close();
//            WebClient client = new WebClient();
//            client.getOptions().setCssEnabled(false);
//            client.getOptions().setJavaScriptEnabled(true);
//            HtmlPage page = client.getPage(url);
//            System.out.println(page);
//            System.out.println("refsdfre");
//            ((HtmlAnchor)page.getAnchorByText().getByXPath("//div[@class='link-phone']").get(0)).getFirstByXPath();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
}
