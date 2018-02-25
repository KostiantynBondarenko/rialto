package ua.estate.rialto.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ua.estate.rialto.util.json.JsonUtil;

/**
 * Pojo для хранения адреса
 *
 * @author kostia
 */

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddressHolder {
    String city; // город
    String district; // район
    String address; //адрес

    @Override
    public String toString() {
        return JsonUtil.toPrettyJson(this);
    }
}