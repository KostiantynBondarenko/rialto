package ua.estate.rialto.to.nedvig;

import ua.estate.rialto.util.excelparser.annotations.ExcelField;
import ua.estate.rialto.util.excelparser.annotations.ExcelObject;
import ua.estate.rialto.util.excelparser.annotations.ParseType;
import ua.estate.rialto.util.json.JsonUtil;

import java.time.LocalDate;

@ExcelObject(parseType = ParseType.ROW, start = 4)
public class RentaEstateTo {
    @ExcelField(position = 1)
    private String id; // внешний id
    @ExcelField(position = 2)
    private String district; // область
    @ExcelField(position = 3)
    private String address; // адрес
    @ExcelField(position = 4)
    private String type; // тип
    @ExcelField(position = 5)
    private String allArea; // площадь
    @ExcelField(position = 6)
    private Integer floor; // этаж
    @ExcelField(position = 7)
    private Integer hasPhone; // телефон
    @ExcelField(position = 8)
    private String price; // цена
    @ExcelField(position = 9)
    private String phone; // телефон
    @ExcelField(position = 10)
    private String addPhone; // телефон
    @ExcelField(position = 11)
    private LocalDate creationDate; // дата создания объявления
    @ExcelField(position = 12)
    private String seller; // продавец
    @ExcelField(position = 13)
    private String description; // описание

    @Override
    public String toString() {
        return JsonUtil.toPrettyJson(this);
    }
}
