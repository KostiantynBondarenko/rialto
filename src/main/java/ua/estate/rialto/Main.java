package ua.estate.rialto;


import com.fasterxml.jackson.core.type.TypeReference;
import ua.estate.rialto.model.Realtor;
import ua.estate.rialto.model.Role;
import ua.estate.rialto.model.User;
import ua.estate.rialto.util.json.JsonUtil;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        Set<String> phone = new HashSet<>();
        phone.add("0938140172");
        phone.add("+380938140172");
        EnumSet.of(Role.ROLE_ADMIN); //?
        Realtor owner = new Realtor(2, "kostia", "Bondarenko", "Evgenievich", false, null);
//        Arrays.asList(null);
//        owner.setPhones(null);
        System.out.println(owner);
//        System.out.println(owner);
//        System.out.println(JsonUtil.toJson(owner));
//        System.out.println(JsonUtil.toJsonArray(owner, owner));
//        System.out.println(JsonUtil.toPrettyJsonArray(new Realtor[] {owner, owner}, owner));
        String json = "[{\n" +
                "  \"id\" : 2,\n" +
                "  \"name\" : \"kostia\",\n" +
                "  \"surname\" : \"Bondarenko\",\n" +
                "  \"patronymic\" : \"Evgenievich\",\n" +
                "  \"phone\" : [ \"+380938140172\", \"0938140172\" ],\n" +
                "  \"confidant\" : false,\n" +
                "  \"blackList\" : false\n" +
                "}]";
//        Realtor owner1 =  JsonUtil.fromJson(json, Realtor.class);
//        System.out.println(owner1);

        String json2 = "[{\"id\":2,\"name\":\"kostia\",\"surname\":\"Bondarenko\",\"patronymic\":\"Evgenievich\",\"phone\":[\"+380938140172\",\"0938140172\"],\"confidant\":false,\"blackList\":false}]";

        List<Realtor> owner2 = JsonUtil.fromJson(json, new TypeReference<List<Realtor>>(){});
        System.out.println(owner2);
        User user = new User();
        System.out.println(user);

        String bbb = "{\n" +
                "  \"enabled\" : true,\n  \"registered\" : \"2018-02-13T22:39:31.202+0200\"\n" +
                "}";
        User user1 = JsonUtil.fromJson(bbb, new TypeReference<User>(){});

        System.out.println(user1);
    }
}
/*
Тип объявления
Город
Район
Адрес
Тип
Этаж
Площадь
Материал
Цена
Владелец
Фио владельца
Телефон
Дата создания
Дата изменения
Инфо
Урл
Фото


*
* */