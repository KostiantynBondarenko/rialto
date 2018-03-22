package ua.estate.rialto.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.domain.Persistable;
import ua.estate.rialto.util.json.JsonUtil;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Pojo для хранения объявления
 *
 * @author kostia
 */
// http://stackoverflow.com/questions/594597/hibernate-annotations-which-is-better-field-or-property-access
@Access(AccessType.FIELD)
@MappedSuperclass
@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
public abstract class Ad implements Persistable<Integer>{
    @Id
    @SequenceGenerator(name = "ad_seq", sequenceName = "ad_seq", allocationSize = 1, initialValue = 100)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ad_seq")
    // PROPERTY access for id due to bug: https://hibernate.atlassian.net/browse/HHH-3718
    @Access(value = AccessType.PROPERTY)
    public Integer id; // идентификатор объявления

    @Column(name = "outside_id")
    private String outsideId; // внешний идентификатор объявления

    @Column(name = "ad_type")
    @NotNull
    private AdType adType; // тип объявления

    @Column(name = "active", nullable = false, columnDefinition = "bool default true")
    private boolean active = true; // активно

    @Column(name = "city")
    @Size(max = 20)
    private String city; // город

    @Column(name = "district")
    @Size(max = 50)
    private String district; // район

    @Column(name = "street")
    @Size(max = 50)
    private String street; // улица

    @Column(name = "address")
    @Size(max = 20)
    private String address; // адрес

    @Column(name = "count_room")
    @Max(50)
    private Integer countRoom; // количество комнат

    @Column(name = "floor")
    @Range(min = 0, max = 100)
    private Integer floor; // этаж

    @Column(name = "area", precision=7, scale=2)
    @Digits(integer=9, fraction=2)
    private BigDecimal area; // площадь

    @Column(name = "measure_of_area")
    @Size(max = 25)
    private String measureOfArea; // единицы измерения площади

    @Column(name = "material")
    @Size(max = 50)
    private String material; // материал

    @Column(name = "price", precision=7, scale=2)
    @Digits(integer=9, fraction=2)
    private BigDecimal price; // цена

    @Column(name = "currency")
    private Currency currency; // валюта

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agent_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private Agent agent; // представитель

    @Column(name = "agent_is_owner")
    private Boolean agentIsOwner; // является собственником объекта

    @Column(name = "creation_dt", nullable = false, columnDefinition = "timestamp default now()")
    @NotNull
    private LocalDateTime creationDate = LocalDateTime.now(); // дата создания объявления

    @Column(name = "change_dt", nullable = false, columnDefinition = "timestamp default now()")
    @NotNull
    private LocalDateTime changeDate = LocalDateTime.now(); // дата изменения

    @Column(name = "description")
    @Size(max = 500)
    private String description; // описание
//    private HashSet<URL> urlAdvertisement; // ссылки на объявления
//    private Set<URL> urlPhoto; // ссылка на фото


    public Ad(String outsideId, AdType adType, String city, String district, String street, String address, Integer countRoom,
              Integer floor, BigDecimal area, String measureOfArea, String material, BigDecimal price, Currency currency,
              Agent agent, Boolean agentIsOwner, String description) {
        this.outsideId = outsideId;
        this.adType = adType;
        this.city = city;
        this.district = district;
        this.street = street;
        this.address = address;
        this.countRoom = countRoom;
        this.floor = floor;
        this.area = area;
        this.measureOfArea = measureOfArea;
        this.material = material;
        this.price = price;
        this.currency = currency;
        this.agent = agent;
        this.agentIsOwner = agentIsOwner;
        this.description = description;
    }

    @Override
    public boolean isNew() {
        return this.id == null;
    }

    @Override
    public String toString() {
        return JsonUtil.toPrettyJson(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || !getClass().equals(Hibernate.getClass(o))) {
            return false;
        }
        Ad that = (Ad) o;
        return getId() != null && getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return (getId() == null) ? 0 : getId();
    }
}
