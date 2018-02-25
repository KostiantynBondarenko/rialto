package ua.estate.rialto.model;

import lombok.*;
import ua.estate.rialto.util.json.JsonUtil;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;

@MappedSuperclass
@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractNamedEntity extends AbstractBaseEntity {
    @NotBlank
    @Column(name = "name", nullable = false)
    protected String name; // имя
    protected String surname; // фамилмя
    protected String patronymic; // отчество

    protected AbstractNamedEntity(Integer id, String name) {
        super(id);
        this.name = name;
    }

    protected AbstractNamedEntity(Integer id, String name, String surname, String patronymic) {
        super(id);
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
    }

    @Override
    public String toString() {
        return JsonUtil.toPrettyJson(this);
    }
}
