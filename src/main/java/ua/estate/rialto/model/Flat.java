package ua.estate.rialto.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import ua.estate.rialto.util.json.JsonUtil;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "flats")
@NoArgsConstructor
@Setter @Getter
public class Flat extends Ad {

    @Max(50)
    @Column(name = "count_room")
    private Integer countRoom; // количество комнат

    @Range(min = 0, max = 100)
    @Column(name = "floor")
    private Integer floor; // этаж

    @Digits(integer=9, fraction=2)
    @Column(name = "live_area", precision=7, scale=2)
    private BigDecimal liveArea; // жилая площадь

    @Digits(integer=9, fraction=2)
    @Column(name = "kitchen_area", precision=7, scale=2)
    private BigDecimal kitchenArea; // площадь кухни

    @Column(name = "balcony")
    private Boolean balcony; // балкон

    public Flat(String outsideId, AdType adType, String city, String district, String street, String address,
                Integer countRoom, Integer countFloor, Integer floor, BigDecimal area, Measures measureOfArea,
                BigDecimal liveArea, BigDecimal kitchenArea, String material, BigDecimal price, Boolean balcony,
                Currency currency, Agent agent, Boolean agentIsOwner, LocalDateTime creationDate, String description) {
        super(outsideId, adType, city, district, street, address, countFloor, area, measureOfArea, material, price,
                currency, agent, agentIsOwner, creationDate, description);
        this.countRoom = countRoom;
        this.floor = floor;
        this.liveArea = liveArea;
        this.kitchenArea = kitchenArea;
        this.balcony = balcony;
    }

    @Override
    public String toString() {
        return JsonUtil.toJson(this);
    }
}
