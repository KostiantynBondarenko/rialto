package ua.estate.rialto.to.olx;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import ua.estate.rialto.util.json.JsonUtil;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Setter
@Getter
public class Ads {
    private String created; // дата создания

    private String status; // статус (active)

    private boolean urgent; // срочное

    private boolean business; // бизнес

    @JsonProperty("category_id")
    private int categoryId; // номер категории

    private boolean topAd; // топовое

    private String header; // заголовок ("Топ-объявления")

    @JsonProperty("header_type")
    private String headerType; // тип ("promoted")

    private String title; // заголовок

    @JsonIgnore
    private List<String> subtitle; // подзаголовок

    private String id; // ид объявления

    private List<String[]> params; // параметры объявления (Объявление от, Тип аренды, Количество комнат, Жилая площадь, Общая площадь, Этаж, Этажность дома, Тип)

    private String description; // описание объекта

    @JsonProperty("is_price_negotiable")
    private Boolean isPriceNegotiable; // договорная цена

    @JsonProperty("list_label")
    private String listLabel; // цена

    @JsonProperty("list_label_ad")
    private String listLabelAd; // дополнительная цена

    @JsonProperty("price_numeric")
    private Integer priceNumeric; // цена в числовом виде

    @JsonIgnore
    @JsonProperty("price_type")
    private String priceType; // расположенность к цене

    @JsonProperty("list_label_small")
    private String listLabelSmall; // описание цены

    @JsonIgnore
    private List<Photos> photos; // фото ??? странно хранятся

    private List<String> images; // фото ??? странно хранятся

    private String person; // владелец

    @JsonIgnore
    @JsonProperty("user_id")
    private String userId; // ид пользователя

    @JsonIgnore
    @JsonProperty("user_label")
    private String userLabel; // имя пользователя

    @JsonIgnore
    @JsonProperty("has_email")
    private Number hasEmail; // пользователь с почновым адресом

    @JsonIgnore
    @JsonProperty("has_phone")
    private Number hasPhone; // пользователь с номером телефона

    @JsonIgnore
    @JsonProperty("hide_user_ads_button")
    private boolean hideUserAdsButton; // скрыть кнопку "объявления пользователя"

    @JsonIgnore
    private boolean highlighted; // выделить пользователя

    @JsonIgnore
    @JsonProperty("user_ads_id")
    private String userAdsId; // ид объявлений пользователя

    @JsonIgnore
    @JsonProperty("user_ads_url")
    private String userAdsUrl; // ссылка на объявления пользователей

    @JsonIgnore
    @JsonProperty("preview_url")
    private String previewUrl; // url with json info about AD

    private String url; // ссылка на объявление

    @JsonProperty("city_id")
    private int cityId; // номер города

    @JsonProperty("city_label")
    private String cityLabel; // название города

    @JsonProperty("district_id")
    private int districtId; // номер области

    @JsonProperty("region_id")
    private Integer regionId; // номер региона

    @JsonProperty("map_lat")
    private String mapLat; // координаты на карте

    @JsonProperty("map_lon")
    private String mapLon; // координаты на карте

//    @JsonIgnore
    @JsonProperty("map_location")
    private String mapLocation; // адрес (закодирован)

    @JsonIgnore
    @JsonProperty("map_radius")
    private Number mapRadius; // радиус объекта

    @JsonIgnore
    @JsonProperty("map_show_detailed")
    private boolean mapShowDetailed; // показывать детали

    @JsonIgnore
    @JsonProperty("map_zoom")
    private Integer mapZoom; // приближение на карте

    @Override
    public String toString() {
        return JsonUtil.toPrettyJson(this);
    }
}
