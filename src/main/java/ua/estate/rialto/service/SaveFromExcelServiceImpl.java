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
import java.util.function.Consumer;
import java.util.stream.Collectors;

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
    public void parseAndSave (File file, Consumer<Exception> errorHandler) throws IOException {
        FileInputStream excelFile = null; //xlsx, xls

        try {
            excelFile = new FileInputStream(file);

            Iterator<Sheet> sheetIterator = new HSSFWorkbook(excelFile).sheetIterator();
            while (sheetIterator.hasNext()) {
                Class classObj = null;
                Sheet sheet = sheetIterator.next();
                switch (sheet.getSheetName()) {
                    case "Flat":
                        saveFlats(sheet, errorHandler);
                        break;
                    case "Estate":
                        saveEstate(sheet, errorHandler);
                        break;
                    case "Land2"://убрать 2
                        classObj = LandTo.class;
                        break;
                    case "RentaFLT2":
                        classObj = RentaFlatTo.class;
                        break;
                    case "RentaEST2":
                        classObj = RentaEstateTo.class;
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
    private void saveFlats(Sheet sheet, Consumer<Exception> errorHandler) {
        List<FlatTo> flatTos = sheetParser.createEntity(sheet, FlatTo.class,
                (ex) -> errorHandler.accept(new RuntimeException("Error create entity FlatTo.", ex))); //parse

        List<Flat> flats = flatTos.stream()
                .map(flatTo -> Try
                        .of(() -> convert(flatTo))
                        .onFailure((ex) -> errorHandler.accept(new RuntimeException("Error convert entity FlatTo." + flatTo, ex)))
                        .getOrElse((Flat) null))
                .filter(Objects::nonNull)
                .collect(Collectors.toList()); //convert

        flats.forEach(flat -> Try
                .of(() -> adRepository.save(flat))
                .onFailure(ex -> errorHandler.accept(new RuntimeException("Error save entity Flat." + flat, ex))));  //save
    }

    /*
     * Convert FlatTo to Flat
     **/
    private Flat convert(FlatTo flatTo) {
        String[] fullAddressArr = StringUtils.split(flatTo.getAddress(), ",");
        String address = getAddtess(fullAddressArr);

        return new Flat(
                trimToNull(flatTo.getId()),
                AdType.SELLING,
                CITY_DNEPR,
                trimToNull(flatTo.getDistrict()),
                getStreet(flatTo.getAddress(), fullAddressArr, address),
                address,
                flatTo.getCountRoom(),
                flatTo.getCountFloor(),
                flatTo.getFloor(),
                convertArea(flatTo.getAllArea()),
                Measures.SQ_M,
                convertArea(flatTo.getLiveArea()),
                convertArea(flatTo.getKitchenArea()),
                trimToNull(flatTo.getMaterial()),
                convertPrice(flatTo.getPrice()),
                convertIntToBool(flatTo.getBalcony()),
                Currency.USD,
                saveAndGetAgent(flatTo.getPhone(), flatTo.getAddPhone()),
                isSeller(flatTo.getSeller()),
                flatTo.getCreationDate().atStartOfDay(),
                trimToNull(flatTo.getDescription()));
    }

    /*
     * Save entities Estate
     **/
    private void saveEstate(Sheet sheet, Consumer<Exception> errorHandler) {
        List<EstateTo> estateTos = sheetParser.createEntity(sheet, EstateTo.class,
                (ex) -> errorHandler.accept(new RuntimeException("Error create entity EstateTo.", ex))); //parse

        List<Estate> estates = estateTos.stream()
                .map(estateTo -> Try
                        .of(() -> convert(estateTo))
                        .onFailure((ex) -> errorHandler.accept(new RuntimeException("Error convert entity EstateTo.", ex)))
                        .getOrElse((Estate) null))
                .filter(Objects::nonNull)
                .collect(Collectors.toList()); //convert

        estates.forEach(estate -> Try
                .of(() -> adRepository.save(estate))
                .onFailure(ex -> errorHandler.accept(new RuntimeException("Error save entity Estate.", ex))));  //save
    }

    /*
     * Convert EstateTo to Estate
     **/
    private Estate convert(EstateTo estateTo) {
        String[] fullAddressArr = StringUtils.split(estateTo.getAddress(), ",");
        String address = getAddtess(fullAddressArr);

        return new Estate(
                    trimToNull(estateTo.getId()),
                    AdType.SELLING,
                    CITY_DNEPR,
                    trimToNull(estateTo.getDistrict()),
                    getStreet(estateTo.getAddress(), fullAddressArr, address),
                    address,
                    estateTo.getAppointment(),
                    estateTo.getCountFloor(),
                    convertArea(estateTo.getAllArea()),
                    Measures.HECTARE,
                    convertArea(estateTo.getBuildArea()),
                    Measures.SQ_M,
                    trimToNull(estateTo.getMaterial()),
                    convertIntToBool(estateTo.getGas()),
                    convertIntToBool(estateTo.getWater()),
                    convertPrice(estateTo.getPrice()),
                    Currency.USD,
                    saveAndGetAgent(estateTo.getPhone(), estateTo.getAddPhone()),
                    isSeller(estateTo.getSeller()),
                    estateTo.getCreationDate().atStartOfDay(),
                    trimToNull(estateTo.getDescription()));
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
     * convert 3,2 to 3200
     **/
    private BigDecimal convertPrice(String price) {
        return isBlank(price) ? null : new BigDecimal(price.trim().replace(",", "") + "00");
    }

    /*
     * convert 70,1 to 70.1
     **/
    private BigDecimal convertArea(String area) {
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
