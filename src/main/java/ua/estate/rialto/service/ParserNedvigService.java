package ua.estate.rialto.service;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Service;
import ua.estate.rialto.to.nedvig.*;
import ua.estate.rialto.util.excelparser.SheetParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

@Service
public class ParserNedvigService implements ParserService {
    private SheetParser sheetParser;

    public ParserNedvigService(SheetParser sheetParser) {
        this.sheetParser = sheetParser;
    }

    @Override
    public void parser (File file) throws IOException {
        FileInputStream excelFile = new FileInputStream(file);//xlsx, xls
//        FileInputStream excelFile = new FileInputStream(new File(path));//xlsx, xls

        Iterator<Sheet> sheetIterator = new HSSFWorkbook(excelFile).sheetIterator();
        while (sheetIterator.hasNext()) {
            Sheet sheet = sheetIterator.next();
            switch (sheet.getSheetName()) {
                case "Flat":
                    List<Flat> flats = sheetParser.createEntity(sheet, Flat.class, (r) -> System.out.println(r.getMessage()));
                    flats.stream().forEach(System.out::println);
                    break;
                case "Estate":
                    List<Estate> entityList2 = sheetParser.createEntity(sheet, Estate.class, (r) -> System.out.println(r.getMessage()));
                    entityList2.stream().forEach(System.out::println);
                    break;
                case "Land":
                    List<Land> entityList3 = sheetParser.createEntity(sheet, Land.class, (r) -> System.out.println(r.getMessage()));
                    entityList3.stream().forEach(System.out::println);
                    break;
                case "RentaFLT":
                    List<RentaFlat> entityList4 = sheetParser.createEntity(sheet, RentaFlat.class, (r) -> System.out.println(r.getMessage()));
                    entityList4.stream().forEach(System.out::println);
                    break;
                case "RentaEST":
                    List<RentaEstate> entityList5 = sheetParser.createEntity(sheet, RentaEstate.class, (r) -> System.out.println(r.getMessage()));
                    entityList5.stream().forEach(System.out::println);
                    break;
            }
        }
        excelFile.close();
        file.delete();
    }
}
