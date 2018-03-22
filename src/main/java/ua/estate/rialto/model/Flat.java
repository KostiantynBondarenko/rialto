package ua.estate.rialto.model;


import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import ua.estate.rialto.util.json.JsonUtil;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Entity
@Table(name = "flats")
@NoArgsConstructor
public class Flat extends Ad {

    public Flat(String outsideId, AdType adType, String city, String district, String street, String address,
                Integer countRoom, Integer floor, BigDecimal area, String measureOfArea, String material, BigDecimal price,
                Currency currency, Agent agent, Boolean agentIsOwner, String description) {
        super(outsideId, adType, city, district, street, address, countRoom, floor, area, measureOfArea, material, price,
                currency, agent, agentIsOwner, description);
    }

    @Override
    public String toString() {
        return JsonUtil.toJson(this);
    }
}
