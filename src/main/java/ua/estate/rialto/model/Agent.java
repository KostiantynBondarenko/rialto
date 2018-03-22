package ua.estate.rialto.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.domain.Persistable;
import ua.estate.rialto.util.json.JsonUtil;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Pojo для хранения инфорформации о представителе объявления
 *
 * @author kostia
 */

// http://stackoverflow.com/questions/594597/hibernate-annotations-which-is-better-field-or-property-access
@Access(AccessType.FIELD)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Entity
@Table(name = "agents", uniqueConstraints = {@UniqueConstraint(columnNames = "phone", name = "agents_unique_phone_idx")})
@NoArgsConstructor
@Setter @Getter
public class Agent implements Persistable<Integer>{
    @Id
    @SequenceGenerator(name = "agent_seq", sequenceName = "agent_seq", allocationSize = 1, initialValue = 100)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "agent_seq")
    // PROPERTY access for id due to bug: https://hibernate.atlassian.net/browse/HHH-3718
    @Access(value = AccessType.PROPERTY)
    private Integer id;

    @Column(name = "name")
    protected String name;

    @Column(name = "surname")
    protected String surname; // фамилмя

    @Column(name = "patronymic")
    protected String patronymic; // отчество

    @Column(name = "registered", nullable = false, columnDefinition = "timestamp default now()")
    @NotNull
    private Date registered = new Date(); // дата регистрации

    @Column(name = "cheater", nullable = false, columnDefinition = "bool default false")
    private boolean cheater = false; // является мошенником

    @Column(name = "phone")
    private String phone; // телефон

    @Column(name = "add_phone")
    private String addPhone; // дополнительный телефон

    public Agent(String name, String phone, String addPhone) {
        this(null, name, null, null, new Date(), false, phone, addPhone);
    }

    public Agent(Integer id, String name, String surname, String patronymic, Date registered, boolean cheater, String phone, String addPhone) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.registered = registered;
        this.cheater  = cheater;
        this.phone  = phone;
        this.addPhone  = addPhone;
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
        Agent that = (Agent) o;
        return getId() != null && getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return (getId() == null) ? 0 : getId();
    }
}
