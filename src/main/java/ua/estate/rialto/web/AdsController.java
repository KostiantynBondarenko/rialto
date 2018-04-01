package ua.estate.rialto.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import ua.estate.rialto.service.SaveFromExcelService;

import java.io.File;
import java.io.IOException;

@Slf4j
@RestController
public class AdsController {
    private static final long MAX_SIZE_FILE = 7 * 1024 * 1024;    // 7 MB more information in spring-mvc.xml

    private final SaveFromExcelService saveFromExcelService;

    public AdsController(SaveFromExcelService saveFromExcelService) {
        this.saveFromExcelService = saveFromExcelService;
    }

    @PostMapping(value = "/uploudFile")
    public void addAds(@RequestParam("file") MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Empty file");
        }
        String fileName = file.getOriginalFilename();
        if (!fileName.endsWith("xls") && !fileName.endsWith("xlsx")) {
            throw new IllegalArgumentException("Bad file format");
        }
        if (file.getSize() > MAX_SIZE_FILE) {
            throw new MaxUploadSizeExceededException(MAX_SIZE_FILE);
        }
        File convFile = File.createTempFile("tempFile", "xlsx");
        file.transferTo(convFile);
        saveFromExcelService.parseAndSave(convFile);
    }
}
