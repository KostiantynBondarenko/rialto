package ua.estate.rialto.to.olx;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Params {
    private Number category; //категория
    private Number city; // город
    private ParamsSearch params; // дополнительные параметры
    private Number region; // регион
    private Number shopId;
    private Number subregion; // подрегион
}
