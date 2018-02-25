package ua.estate.rialto.to.olx;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TopHeaderLabels {
    private String category_icon; // ссылка на иконку категории
    private String location_label; // область (default - Вся Украина)
    private int parameters_count; // количество параметров
}
