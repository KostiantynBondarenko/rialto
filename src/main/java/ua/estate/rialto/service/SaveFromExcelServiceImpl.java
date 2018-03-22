package ua.estate.rialto.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import ua.estate.rialto.model.AdType;
import ua.estate.rialto.model.Agent;
import ua.estate.rialto.model.Currency;
import ua.estate.rialto.model.Flat;
import ua.estate.rialto.repository.agent.AgentRepository;
import ua.estate.rialto.repository.flat.FlatRepository;
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

@Service
@Slf4j
public class SaveFromExcelServiceImpl implements SaveFromExcelService {
    private SheetParser sheetParser;
    private AgentRepository agentRepository;
    private FlatRepository flatRepository;

    public SaveFromExcelServiceImpl(SheetParser sheetParser, AgentRepository agentRepository, FlatRepository flatRepository) {
        this.sheetParser = sheetParser;
        this.agentRepository = agentRepository;
        this.flatRepository = flatRepository;
    }

    @Override
    public void save (File file, Consumer<Exception> errorHandler) throws IOException {
        FileInputStream excelFile = null; //xlsx, xls

        try {
            excelFile = new FileInputStream(file);

            Iterator<Sheet> sheetIterator = new HSSFWorkbook(excelFile).sheetIterator();
            while (sheetIterator.hasNext()) {
                Class classObj = null;
                Sheet sheet = sheetIterator.next();
                switch (sheet.getSheetName()) {
                    case "Flat":
                        List<FlatTo> flatTos = sheetParser.createEntity(sheet, FlatTo.class,
                                (ex) -> errorHandler.accept(new RuntimeException("Error creating entity FlatTo.", ex))); //parse
                        List<Flat> flats = convert(flatTos,
                                (ex) -> errorHandler.accept(new RuntimeException("Error convert entity FlatTo.", ex))); //convert
                        flats.stream().forEach(i -> {
                            try {
                                flatRepository.save(i);
                            } catch (Exception ex) {
                                errorHandler.accept(new RuntimeException("Error save entity FlatTo.", ex));
                            }
                        }); //save
                        break;
                    case "Estate2"://убрать 2
                        classObj = EstateTo.class;
                        break;
                    case "Land2":
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
     * Convert entity
     **/
    private List<Flat> convert(List<FlatTo> flatTos, Consumer<Exception> errorHandler) {
        return flatTos.stream()
                .filter(Objects::nonNull)
                .map(flatTo -> convert(flatTo, errorHandler))
                .collect(Collectors.toList());
    }

    /*
     * Convert FlatTo to Flat
     **/
    private Flat convert(FlatTo flatTo, Consumer<Exception> errorHandler) {
        Flat flat = null;
        try {
            String[] fullAddressArr = StringUtils.split(flatTo.getAddress(), ",");

            // if Address contains a comma, then save the string after the last comma like the address
            String address = nonNull(fullAddressArr) && fullAddressArr.length > 1 && containsDigital(fullAddressArr[fullAddressArr.length - 1]) ?
                    trimToNull(fullAddressArr[fullAddressArr.length - 1]) : null;

            // save the street without the address
            String street = nonNull(address) ? removeEnd(flatTo.getAddress(), "," + fullAddressArr[fullAddressArr.length - 1]) : flatTo.getAddress();
            flat = new Flat(
                    trimToNull(flatTo.getId()),
                    AdType.SELLING,
                    "Днепр",
                    trimToNull(flatTo.getDistrict()),
                    trimToNull(street),
                    trimToNull(address),
                    flatTo.getCountRoom(),
                    flatTo.getFloor(),
                    isBlank(flatTo.getAllArea()) ? null : new BigDecimal(flatTo.getAllArea().trim().replace(",", ".")), //convert 70,1 to 70.1
                    "кв.м",
                    trimToNull(flatTo.getMaterial()),
                    isBlank(flatTo.getPrice()) ? null : new BigDecimal(flatTo.getPrice().trim().replace(",", "") + "00"), //convert 3,2 to 3200
                    Currency.USD,
                    saveAndGet(flatTo.getPhone(), flatTo.getAddPhone()),
                    isBlank(flatTo.getSeller()) ? null : equalsIgnoreCase("Владелец", trimToNull(flatTo.getSeller())),
                    trimToNull(flatTo.getDescription()));
        } catch (Exception ex) {
            errorHandler.accept(new RuntimeException("Error convert FlatTo to Flat. FlatTo=" + flatTo, ex));
        }

        return flat;
    }

    /*
     * SaveAndGet Agent by phone
     **/
    private Agent saveAndGet(String phone, String addPhone) {
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
