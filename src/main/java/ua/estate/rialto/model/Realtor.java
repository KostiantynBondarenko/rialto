package ua.estate.rialto.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ua.estate.rialto.util.json.JsonUtil;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Pojo для хранения инфорформации о риэлторе
 *
 * @author kostia
 */
@AllArgsConstructor
public class Realtor extends AbstractNamedEntity {
    @Getter private Set<String> phone; // телефон
    @Setter @Getter private Boolean owner; // является собственником объекта
    @Setter @Getter private boolean cheater ; // является мошенником

    public Realtor(Integer id, String name, String surname, String patronymic, boolean cheater, String... phone) {
        this(id, name, surname, patronymic, null, cheater,
                Objects.isNull(phone) ? new HashSet<>() : new HashSet<>(Arrays.asList(phone)));
    }

    public Realtor(Integer id, String name, String surname, String patronymic, Boolean owner, boolean cheater, Set<String> phones) {
        super(id, name, surname, patronymic);
        this.owner = owner;
        this.cheater  = cheater;
        setPhone(phones);
    }

    public void setPhone(Set<String> phone) {
        this.phone = Objects.isNull(phone) ? new HashSet<>() : phone;
    }

    @Override
    public String toString() {
        return JsonUtil.toPrettyJson(this);
    }
}
