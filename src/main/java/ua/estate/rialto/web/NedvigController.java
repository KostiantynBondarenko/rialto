package ua.estate.rialto.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import ua.estate.rialto.service.ParserService;

import java.io.File;
import java.io.IOException;

@Slf4j
@RestController
public class NedvigController {
    private static final long MAX_SIZE_FILE = 7 * 1024 * 1024;    // 7 MB more information in spring-mvc.xml

    private final ParserService parserService;

    public NedvigController(ParserService parserService) {
        this.parserService = parserService;
    }

    @PostMapping(value = "/addAds")
    public void addAds(@RequestParam("fileNedvig") MultipartFile fileNedvig) throws IOException {
        if (fileNedvig == null || fileNedvig.isEmpty()) {
            throw new IllegalArgumentException("Empty file");
        }
        String fileName = fileNedvig.getOriginalFilename();
        if (!fileName.endsWith("xls") && !fileName.endsWith("xlsx")) {
            throw new IllegalArgumentException("Bad file format");
        }
        if (fileNedvig.getSize() > MAX_SIZE_FILE) {
            throw new MaxUploadSizeExceededException(MAX_SIZE_FILE);
        }
        File convFile = File.createTempFile("tempFileNedvig", "xlsx");
        fileNedvig.transferTo(convFile);
        parserService.parser(convFile);
    }
}
