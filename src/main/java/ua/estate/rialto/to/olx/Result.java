package ua.estate.rialto.to.olx;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class Result {
    private List<Ads> ads; // объявления

    @JsonIgnore
    private Number ads_on_page; // количество объявлений на странице

    @JsonIgnore
    private String category_id; //номер категории

    @JsonIgnore
    private String next_page_url; // урл следующей страницы

    @JsonIgnore
    private Number page; // номер страницы

    private Params params; // параметры поиска

    @JsonIgnore
    private TopHeaderLabels top_header_labels; // область поиска (default - Вся Украина)

    @JsonIgnore
    private Integer total_ads; // общее количество объявлений

    @JsonIgnore
    private Integer total_pages; // общее количество страниц

    @JsonIgnore
    private String view;
}
