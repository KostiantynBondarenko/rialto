package ua.estate.rialto.service;

import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;

public interface SaveFromExcelService {

    void parseAndSave(File file, Consumer<Exception> errorHandler) throws IOException;
}
