package ua.estate.rialto.model;

import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.data.domain.Persistable;
import ua.estate.rialto.util.json.JsonUtil;

import javax.persistence.*;

/**
 * Do not manipulate new (transient) entries in HashSet/HashMap without overriding hashCode
 * http://stackoverflow.com/questions/5031614
 */
@MappedSuperclass

// http://stackoverflow.com/questions/594597/hibernate-annotations-which-is-better-field-or-property-access
@Access(AccessType.FIELD)
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractBaseEntity implements Persistable<Integer> {
    @Id
    @SequenceGenerator(name = "global_seq", sequenceName = "global_seq", allocationSize = 1, initialValue = 10000)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // PROPERTY access for id due to bug: https://hibernate.atlassian.net/browse/HHH-3718
    @Access(value = AccessType.PROPERTY)
    private Integer id; // ид сущности

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
        AbstractBaseEntity that = (AbstractBaseEntity) o;
        return getId() != null && getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return (getId() == null) ? 0 : getId();
    }
}
