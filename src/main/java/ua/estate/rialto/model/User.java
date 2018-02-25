package ua.estate.rialto.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.util.CollectionUtils;
import ua.estate.rialto.util.json.JsonUtil;

import java.util.*;

/**
 * Pojo для зарегистрированного пользователя
 *
 * @author kostia
 */
@NoArgsConstructor
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class User extends AbstractNamedEntity {
    @Setter @Getter private String email; // логин
    @Setter @Getter private String password; // пароль
    @Setter @Getter private boolean enabled = true; // активность пользователя
    @Setter @Getter private Date registered = new Date(); // дата регистрации
    @Getter private Set<Role> roles; // роли пользователя

    public User(Integer id, String name, String email, String password, Role role, Role... roles) {
        this(id, name, email, password, true, new Date(), EnumSet.of(role, roles));
    }

    public User(Integer id, String name, String email, String password, boolean enabled, Date registered, Collection<Role> roles) {
        super(id, name);
        this.email = email;
        this.password = password;
        this.enabled = enabled;
        this.registered = registered;
        setRoles(roles);
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = CollectionUtils.isEmpty(roles) ? Collections.emptySet() : EnumSet.copyOf(roles);
    }

    @Override
    public String toString() {
        return JsonUtil.toPrettyJson(this);
    }
}
