package ua.estate.rialto.service.crawler;

/**
 * Интерфейс для поиска новых объявлений
 *
 * @author kostia
 */
public interface СrawlerServise {
    void search();
    void parse();
    void save();
}
