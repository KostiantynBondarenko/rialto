package ua.estate.rialto.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
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
 * базовое pojo хранения объявления
 *
 * @author kostia
 */
// http://stackoverflow.com/questions/594597/hibernate-annotations-which-is-better-field-or-property-access
@Access(AccessType.FIELD)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
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

    @Size(max = 20)
    @Column(name = "outside_id")
    private String outsideId; // внешний идентификатор объявления

    @NotNull
    @Column(name = "ad_type")
    @Enumerated(EnumType.STRING)
    private AdType adType; // тип объявления

    @Column(name = "active", nullable = false, columnDefinition = "bool default true")
    private boolean active = true; // активно

    @Size(max = 20)
    @Column(name = "city")
    private String city; // город

    @Size(max = 50)
    @Column(name = "district")
    private String district; // район

    @Size(max = 50)
    @Column(name = "street")
    private String street; // улица

    @Size(max = 20)
    @Column(name = "address")
    private String address; // адрес

    @Size(max = 20)
    @Column(name = "appointment")
    private String appointment;  // назначение

    @Range(min = 0, max = 100)
    @Column(name = "count_floor")
    private Integer countFloor; // количество этажей

    @Range(min = 0, max = 100)
    @Column(name = "floor")
    private Integer floor; // этаж

    @Digits(integer=9, fraction=2)
    @Column(name = "area", precision=7, scale=2)
    private BigDecimal area; // площадь

    @Column(name = "measure_of_area")
    @Enumerated(EnumType.STRING)
    private Measures measureOfArea; // единицы измерения площади

    @Size(max = 50)
    @Column(name = "material")
    private String material; // материал

    @Digits(integer=9, fraction=2)
    @Column(name = "price", precision=7, scale=2)
    private BigDecimal price; // цена

    @Column(name = "currency")
    @Enumerated(EnumType.STRING)
    private Currency currency; // валюта

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agent_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Agent agent; // представитель

    @Column(name = "agent_is_owner")
    private Boolean agentIsOwner; // является собственником объекта

    @NotNull
    @Column(name = "creation_dt", nullable = false, columnDefinition = "timestamp default now()")
    private LocalDateTime creationDate = LocalDateTime.now(); // дата создания объявления

    @NotNull
    @Column(name = "change_dt", nullable = false, columnDefinition = "timestamp default now()")
    private LocalDateTime changeDate = LocalDateTime.now(); // дата изменения

    @Size(max = 500)
    @Column(name = "description")
    private String description; // описание
//    private HashSet<URL> urlAdvertisement; // ссылки на объявления
//    private Set<URL> urlPhoto; // ссылка на фото


    public Ad(String outsideId, AdType adType, String city, String district, String street, String address, String appointment,
              Integer countFloor, Integer floor, BigDecimal area, Measures measureOfArea, String material, BigDecimal price, Currency currency,
              Agent agent, Boolean agentIsOwner, LocalDateTime creationDate, String description) {
        this.outsideId = outsideId;
        this.adType = adType;
        this.city = city;
        this.district = district;
        this.street = street;
        this.address = address;
        this.appointment = appointment;
        this.countFloor = countFloor;
        this.floor = floor;
        this.area = area;
        this.measureOfArea = measureOfArea;
        this.material = material;
        this.price = price;
        this.currency = currency;
        this.agent = agent;
        this.agentIsOwner = agentIsOwner;
        this.creationDate = creationDate;
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
