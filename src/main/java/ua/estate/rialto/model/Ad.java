package ua.estate.rialto.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ua.estate.rialto.util.json.JsonUtil;

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * Pojo для хранения объявления
 *
 * @author kostia
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Ad extends AbstractBaseEntity {
    private AdType type; // тип объявления
    private AddressHolder address; // адрес объекта
    private Integer countRoom; // количество комнат
    private Integer floor; // этаж
    private String area; // площадь
    private String material; // материал
    private BigDecimal price; // цена
    private Currency currency; // валюта
    private Realtor realtor; // владелец
    private LocalDate creationDate; // дата создания объявления
    private LocalDateTime changeDate = LocalDateTime.now(); // дата изменения
    private String description; // описание
    private boolean active = true; // активно
    private URL urlAdvertisement; // ссылка на объявление
    private Set<URL> urlPhoto; // ссылка на фото

    @Override
    public String toString() {
        return JsonUtil.toPrettyJson(this);
    }
}
