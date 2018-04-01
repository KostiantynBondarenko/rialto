package ua.estate.rialto.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ua.estate.rialto.util.json.JsonUtil;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "estates")
@NoArgsConstructor
@Setter @Getter
public class Estate extends Ad {
    @Digits(integer=9, fraction=2)
    @Column(name = "build_area", precision=7, scale=2)
    private BigDecimal buildArea; // площадь зданий

    @Column(name = "measure_of_build_area")
    @Enumerated(EnumType.STRING)
    private Measures measureOfBuildArea; // единицы измерения площади

    @Column(name = "gas")
    private Boolean gas; // газ

    @Column(name = "water")
    private Boolean water; // вода

    public Estate(String outsideId, AdType adType, String city, String district, String street, String address,
                  String appointment, Integer countFloor, BigDecimal area, Measures measureOfArea, BigDecimal buildArea, Measures measureOfBuildArea,
                  String material, Boolean gas, Boolean water, BigDecimal price,
                  Currency currency, Agent agent, Boolean agentIsOwner, LocalDateTime creationDate, String description) {
        super(outsideId, adType, city, district, street, address, appointment, countFloor, null, area, measureOfArea, material, price,
                currency, agent, agentIsOwner, creationDate, description);
        this.buildArea = buildArea;
        this.measureOfBuildArea = measureOfBuildArea;
        this.gas = gas;
        this.water = water;
    }

    public Estate(String outsideId, AdType adType, String city, String district, String street, String address,
                  String appointment, Integer floor, BigDecimal area, Measures measureOfArea, BigDecimal price,
                  Currency currency, Agent agent, Boolean agentIsOwner, LocalDateTime creationDate, String description) {
        super(outsideId, adType, city, district, street, address, appointment, null, floor, area, measureOfArea, null, price,
                currency, agent, agentIsOwner, creationDate, description);
    }

    @Override
    public String toString() {
        return JsonUtil.toJson(this);
    }
}
