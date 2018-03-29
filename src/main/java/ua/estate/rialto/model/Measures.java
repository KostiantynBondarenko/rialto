package ua.estate.rialto.model;


import lombok.Getter;

/**
 * Единицы измерения
 *
 * @author kostia
 */
@Getter
public enum Measures {
    HECTARE("га"),
    SQ_M("кв.м");

    private final String name;
    Measures(String name) {
        this.name = name;
    }
}
