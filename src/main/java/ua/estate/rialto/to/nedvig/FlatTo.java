package ua.estate.rialto.to.nedvig;

import lombok.Getter;
import ua.estate.rialto.util.excelparser.annotations.ExcelField;
import ua.estate.rialto.util.excelparser.annotations.ExcelObject;
import ua.estate.rialto.util.excelparser.annotations.ParseType;
import ua.estate.rialto.util.json.JsonUtil;

import java.time.LocalDate;

@Getter
@ExcelObject(parseType = ParseType.ROW, start = 4)
public class FlatTo {
    @ExcelField(position = 1)
    private String id; // внешний id
    @ExcelField(position = 2)
    private String district; // область
    @ExcelField(position = 3)
    private String address; // адрес
    @ExcelField(position = 4)
    private Integer countRoom; // количество комнат
    @ExcelField(position = 5)
    private Integer izol;
    @ExcelField(position = 6)
    private Integer floor; // этаж
    @ExcelField(position = 7)
    private Integer countFloor; // количество этажей
    @ExcelField(position = 8)
    private String material; // материал
    @ExcelField(position = 9)
    private String allArea; // площадь
    @ExcelField(position = 10)
    private String liveArea; // жилая площадь
    @ExcelField(position = 11)
    private String kitchenArea; // площадь кухни
    @ExcelField(position = 12)
    private Integer balcony; // балкон
    @ExcelField(position = 13)
    private Integer hasPhone; // телефон
    @ExcelField(position = 14)
    private String price; // цена
    @ExcelField(position = 15)
    private String phone; // телефон
    @ExcelField(position = 16)
    private String addPhone; // телефон
    @ExcelField(position = 17)
    private LocalDate creationDate; // дата создания объявления
    @ExcelField(position = 19)
    private String seller; // продавец
    @ExcelField(position = 20)
    private String description; // описание

    @Override
    public String toString() {
        return JsonUtil.toJson(this);
    }
}
