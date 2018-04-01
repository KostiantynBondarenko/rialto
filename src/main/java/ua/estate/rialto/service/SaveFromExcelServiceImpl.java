package ua.estate.rialto.service;

import javaslang.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import ua.estate.rialto.model.*;
import ua.estate.rialto.repository.ad.AdRepository;
import ua.estate.rialto.repository.agent.AgentRepository;
import ua.estate.rialto.to.nedvig.*;
import ua.estate.rialto.util.excelparser.SheetParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.*;
import static ua.estate.rialto.util.Utils.containsDigital;

@Slf4j
@Service
public class SaveFromExcelServiceImpl implements SaveFromExcelService {
    private static final String CITY_DNEPR = "Днепр";
    private SheetParser sheetParser;
    private AgentRepository agentRepository;
    private AdRepository adRepository;

    public SaveFromExcelServiceImpl(SheetParser sheetParser, AgentRepository agentRepository, AdRepository adRepository) {
        this.sheetParser = sheetParser;
        this.agentRepository = agentRepository;
        this.adRepository = adRepository;
    }

    @Override
    public void parseAndSave (File file) throws IOException {
        FileInputStream excelFile = null; //xlsx, xls

        try {
            excelFile = new FileInputStream(file);
            Iterator<Sheet> sheetIterator = new HSSFWorkbook(excelFile).sheetIterator();
            while (sheetIterator.hasNext()) {
                Sheet sheet = sheetIterator.next();
                switch (sheet.getSheetName()) {
                    case "Flat":
                        saveFlats(sheet, FlatTo.class);
                        break;
                    case "RentaFLT":
                        saveFlats(sheet, RentaFlatTo.class);
                        break;
                    case "Estate":
                        saveEstate(sheet, EstateTo.class);
                        break;
                    case "RentaEST":
                        saveEstate(sheet, RentaEstateTo.class);
                        break;
                    case "Land2"://убрать 2 (LandTo.class)
                        break;
                }
            }
        } finally {
            if (nonNull(excelFile)) {
                excelFile.close();
                file.delete();
            }
        }
    }

    /*
     * Save entities Flat
     **/
    private <T> void saveFlats(Sheet sheet, Class<T> clazz) {
        log.info("***saveFlats sheet={}, class={}.", sheet.getSheetName(), clazz.getSimpleName());
        sheetParser
            .createEntity(sheet, clazz,
                    (ex) -> log.error("Error create entity Flat.", ex)) //parse

            .stream()
            .map(flatTO -> Try
                    .of(() -> (clazz == FlatTo.class ? convert((FlatTo) flatTO) : convert((RentaFlatTo) flatTO)))
                    .onFailure((ex) -> log.error("Error convert entity Flat. flatTO={}", flatTO, ex))
                    .getOrElse((Flat) null)) //convert

            .filter(Objects::nonNull)
            .forEach(flat -> Try
                    .of(() -> adRepository.save(flat))
                    .onFailure(ex -> log.error("Error save entity Flat. flat={}", flat, ex)));  //save
    }

    /*
     * Convert FlatTo to Flat
     **/
    private Flat convert(FlatTo flatTo) {
        String[] fullAddressArr = StringUtils.split(flatTo.getAddress(), ",");
        String address = getAddtess(fullAddressArr);

        Flat flat = new Flat(
                trimToNull(flatTo.getId()),
                AdType.SELLING,
                CITY_DNEPR,
                trimToNull(flatTo.getDistrict()),
                getStreet(flatTo.getAddress(), fullAddressArr, address),
                address,
                flatTo.getCountRoom(),
                flatTo.getCountFloor(),
                flatTo.getFloor(),
                strToBigDecimal(flatTo.getAllArea()),
                Measures.SQ_M,
                strToBigDecimal(flatTo.getLiveArea()),
                strToBigDecimal(flatTo.getKitchenArea()),
                trimToNull(flatTo.getMaterial()),
                strToBigDecimalWithOrder(flatTo.getPrice(), 2),
                convertIntToBool(flatTo.getBalcony()),
                Currency.USD,
                saveAndGetAgent(flatTo.getPhone(), flatTo.getAddPhone()),
                isSeller(flatTo.getSeller()),
                flatTo.getCreationDate().atStartOfDay(),
                trimToNull(flatTo.getDescription()));

        log.trace("Convert flatTo={} to flat={}", flatTo, flat);
        return flat;
    }

    /*
     * Convert RentaFlatTo to Flat
     **/
    private Flat convert(RentaFlatTo rentaFlatTo) {
        String[] fullAddressArr = StringUtils.split(rentaFlatTo.getAddress(), ",");
        String address = getAddtess(fullAddressArr);

        Flat flat = new Flat(
                trimToNull(rentaFlatTo.getId()),
                "Квартира-суточн".equalsIgnoreCase(trimToNull(rentaFlatTo.getType())) ? AdType.HAND_OVER_BY_DAY : AdType.HAND_OVER,
                CITY_DNEPR,
                trimToNull(rentaFlatTo.getDistrict()),
                getStreet(rentaFlatTo.getAddress(), fullAddressArr, address),
                address,
                trimToNull(rentaFlatTo.getType()),
                rentaFlatTo.getCountRoom(),
                rentaFlatTo.getCountFloor(),
                rentaFlatTo.getFloor(),
                convertIntToBool(rentaFlatTo.getFurniture()),
                convertIntToBool(rentaFlatTo.getAppliances()),
                strToBigDecimal(rentaFlatTo.getPrice()),
                Currency.USD,
                saveAndGetAgent(rentaFlatTo.getPhone(), rentaFlatTo.getAddPhone()),
                isSeller(rentaFlatTo.getSeller()),
                rentaFlatTo.getCreationDate().atStartOfDay(),
                trimToNull(rentaFlatTo.getDescription()));

        log.trace("Convert rentaFlatTo={} to flat={}", rentaFlatTo, flat);
        return flat;
    }

    /*
     * Save entities Estate
     **/
    private <T> void saveEstate(Sheet sheet, Class<T> clazz) {
        log.info("***saveEstate sheet={}, class={}.", sheet.getSheetName(), clazz.getSimpleName());
        sheetParser
            .createEntity(sheet, clazz, (ex) -> log.error("Error create entity Estate.", ex)) //parse

            .stream()
            .map(estateTO -> Try
                    .of(() -> (clazz == EstateTo.class ? convert((EstateTo) estateTO) : convert((RentaEstateTo) estateTO)))
                    .onFailure((ex) -> log.error("Error convert entity Estate. estateTO={}", estateTO, ex))
                    .getOrElse((Estate) null)) //convert

            .filter(Objects::nonNull)
            .forEach(estate -> Try
                    .of(() -> adRepository.save(estate))
                    .onFailure(ex -> log.error("Error save entity Estate. estate={}", estate, ex)));  //save
    }

    /*
     * Convert EstateTo to Estate
     **/
    private Estate convert(EstateTo estateTo) {
        String[] fullAddressArr = StringUtils.split(estateTo.getAddress(), ",");
        String address = getAddtess(fullAddressArr);

        Estate estate = new Estate(
                trimToNull(estateTo.getId()),
                AdType.SELLING,
                CITY_DNEPR,
                trimToNull(estateTo.getDistrict()),
                getStreet(estateTo.getAddress(), fullAddressArr, address),
                address,
                estateTo.getAppointment(),
                estateTo.getCountFloor(),
                strToBigDecimal(estateTo.getAllArea()),
                Measures.HECTARE,
                strToBigDecimal(estateTo.getBuildArea()),
                Measures.SQ_M,
                trimToNull(estateTo.getMaterial()),
                convertIntToBool(estateTo.getGas()),
                convertIntToBool(estateTo.getWater()),
                strToBigDecimalWithOrder(estateTo.getPrice(), 2),
                Currency.USD,
                saveAndGetAgent(estateTo.getPhone(), estateTo.getAddPhone()),
                isSeller(estateTo.getSeller()),
                estateTo.getCreationDate().atStartOfDay(),
                trimToNull(estateTo.getDescription()));

        log.trace("Convert estateTo={} to estate={}", estateTo, estate);
        return estate;
    }

    /*
     * Convert RentaEstateTo to Estate
     **/
    private Estate convert(RentaEstateTo rentaEstateTo) {
        String[] fullAddressArr = StringUtils.split(rentaEstateTo.getAddress(), ",");
        String address = getAddtess(fullAddressArr);

        BigDecimal area = strToBigDecimal(rentaEstateTo.getAllArea());
        BigDecimal price = strToBigDecimal(rentaEstateTo.getPrice());
        if (nonNull(area) && nonNull(price)) {
            price = area.multiply(price);
        }

        Estate estate =  new Estate(
                trimToNull(rentaEstateTo.getId()),
                AdType.HAND_OVER,
                CITY_DNEPR,
                trimToNull(rentaEstateTo.getDistrict()),
                getStreet(rentaEstateTo.getAddress(), fullAddressArr, address),
                address,
                rentaEstateTo.getType(),
                rentaEstateTo.getFloor(),
                area,
                Measures.SQ_M,
                price,
                Currency.UAN,
                saveAndGetAgent(rentaEstateTo.getPhone(), rentaEstateTo.getAddPhone()),
                isSeller(rentaEstateTo.getSeller()),
                rentaEstateTo.getCreationDate().atStartOfDay(),
                trimToNull(rentaEstateTo.getDescription()));

        log.trace("Convert rentaEstateTo={} to estate={}", rentaEstateTo, estate);
        return estate;
    }

    /*
     * get the address without the street
     *
     * if Address contains a comma, then save the string after the last comma like the address
     **/
    private String getAddtess(String[] fullAddressArr) {
        return nonNull(fullAddressArr) && fullAddressArr.length > 1 && containsDigital(fullAddressArr[fullAddressArr.length - 1]) ?
                trimToNull(fullAddressArr[fullAddressArr.length - 1]) : null;
    }

    /*
     * get the street without the address
     **/
    private String getStreet(String allAddress, String[] fullAddressArr, String address) {
        return nonNull(address) ? trimToNull(removeEnd(allAddress, "," + fullAddressArr[fullAddressArr.length - 1])) : trimToNull(allAddress);
    }

    /*
     * convert String seller to Boolean seller
     **/
    private Boolean isSeller(String seller) {
        seller = trimToNull(seller);
        if (equalsIgnoreCase("Владелец", seller)) {
            return true;
        } else if (equalsIgnoreCase("Посредник-свое", seller)) {
            return false;
        } else {
            return null;
        }
    }

    /*
     * convert 1 / 0  to true / false
     **/
    private Boolean convertIntToBool(Integer value) {
        return Objects.isNull(value) ? null : value.equals(1);
    }

    /*
     * strToBigDecimalWithOrder("3,2", 2) = 3200
     **/
    private BigDecimal strToBigDecimalWithOrder(String price, int order) {
        return isBlank(price) ? null : new BigDecimal(price.trim().replace(",", "") + repeat("0", order));
    }

    /*
     * strToBigDecimalWithOrder("70,1") = 70.1
     **/
    private BigDecimal strToBigDecimal(String area) {
        return isBlank(area) ? null : new BigDecimal(area.trim().replace(",", "."));
    }

    /*
     * SaveAndGet Agent by phone
     **/
    private Agent saveAndGetAgent(String phone, String addPhone) {
        Agent agent = new Agent(null, trimToNull(phone), trimToNull(addPhone));
        List<Agent> agents = nonNull(agent.getAddPhone()) ?
                agentRepository.findByPhoneOrAddPhone(agent.getPhone(), agent.getAddPhone()) :
                agentRepository.findByPhone(agent.getPhone());

        if (CollectionUtils.isEmpty(agents)) {
            agentRepository.save(agent); // if the phone does not exist, save the new agent
        } else {
            agent = agents.get(0);// if the phone already exists, then return the first agent with this phone
        }
        return agent;
    }
}
