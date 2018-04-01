package ua.estate.rialto.service;

import java.io.File;
import java.io.IOException;

public interface SaveFromExcelService {

    void parseAndSave(File file) throws IOException;
}
