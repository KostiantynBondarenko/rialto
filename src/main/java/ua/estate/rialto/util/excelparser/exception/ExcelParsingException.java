package ua.estate.rialto.util.excelparser.exception;

import ua.estate.rialto.util.excelparser.helper.Locator;

import java.text.MessageFormat;

public class ExcelParsingException extends RuntimeException {
    public ExcelParsingException(String message) {
        super(message);
    }

    public ExcelParsingException(String message, Exception exception) {
        super(message, exception);
    }

    public ExcelParsingException(String message, Locator locator) {
        super(MessageFormat.format(message + " in sheet {0} at row {1}, column {2}",
                locator.getSheetName(), locator.getRow(), locator.getCol()));
    }
}

