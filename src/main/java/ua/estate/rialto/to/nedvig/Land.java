package ua.estate.rialto.to.nedvig;

import ua.estate.rialto.util.excelparser.annotations.ExcelField;
import ua.estate.rialto.util.excelparser.annotations.ExcelObject;
import ua.estate.rialto.util.excelparser.annotations.ParseType;
import ua.estate.rialto.util.json.JsonUtil;

import java.time.LocalDate;

@ExcelObject(parseType = ParseType.ROW, start = 4)
public class Land {
    @ExcelField(position = 1)
    private String id; // внешний id
    @ExcelField(position = 2)
    private String region; // область
    @ExcelField(position = 3)
    private String address; // адрес
    @ExcelField(position = 4)
    private String appointment; // назначение
    @ExcelField(position = 5)
    private String allArea; // земельный участок, га
    @ExcelField(position = 6)
    private Integer gas; // газ
    @ExcelField(position = 7)
    private Integer water; // вода
    @ExcelField(position = 8)
    private Integer electricity; // электричество
    @ExcelField(position = 9)
    private String price; // цена
    @ExcelField(position = 10)
    private String phone; // телефон
    @ExcelField(position = 11)
    private String addPhone; // телефон
    @ExcelField(position = 12)
    private LocalDate creationDate; // дата создания объявления
    @ExcelField(position = 14)
    private String seller; // продавец
    @ExcelField(position = 15)
    private String description; // описание

    @Override
    public String toString() {
        return JsonUtil.toPrettyJson(this);
    }
}
