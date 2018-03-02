package ua.estate.rialto.service;

import org.springframework.context.support.GenericXmlApplicationContext;

import java.io.IOException;

public class TestMain {
    public static void main(String[] args) throws IOException {
        try (GenericXmlApplicationContext appCtx = new GenericXmlApplicationContext()) {
            appCtx.load("spring/spring-app.xml");
            appCtx.refresh();

            ParserNedvigService parserNedvigService = appCtx.getBean(ParserNedvigService.class);
//            parserNedvigService.parser("D:\\Загрузки\\nedvig2102.xls");

        }
    }
}
