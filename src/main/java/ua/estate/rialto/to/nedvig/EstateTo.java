package ua.estate.rialto.to.nedvig;

import lombok.Getter;
import ua.estate.rialto.util.excelparser.annotations.ExcelField;
import ua.estate.rialto.util.excelparser.annotations.ExcelObject;
import ua.estate.rialto.util.excelparser.annotations.ParseType;
import ua.estate.rialto.util.json.JsonUtil;

import java.time.LocalDate;

@Getter
@ExcelObject(parseType = ParseType.ROW, start = 4)
public class EstateTo {
    @ExcelField(position = 1)
    private String id; // внешний id
    @ExcelField(position = 2)
    private String district; // область
    @ExcelField(position = 3)
    private String address; // адрес
    @ExcelField(position = 4)
    private String appointment; // назначение
    @ExcelField(position = 5)
    private String allArea; // земельный участок, га
    @ExcelField(position = 6)
    private String buildArea; // здания, кв.м
    @ExcelField(position = 7)
    private Integer countFloor; // количество этажей
    @ExcelField(position = 8)
    private String material; // материал
    @ExcelField(position = 9)
    private Integer gas; // газ
    @ExcelField(position = 10)
    private Integer water; // вода
    @ExcelField(position = 11)
    private Integer hasPhone; // телефон
    @ExcelField(position = 12)
    private String price; // цена
    @ExcelField(position = 13)
    private String phone; // телефон
    @ExcelField(position = 14)
    private String addPhone; // телефон
    @ExcelField(position = 15)
    private LocalDate creationDate; // дата создания объявления
    @ExcelField(position = 17)
    private String seller; // продавец
    @ExcelField(position = 18)
    private String description; // описание

    @Override
    public String toString() {
        return JsonUtil.toPrettyJson(this);
    }
}
