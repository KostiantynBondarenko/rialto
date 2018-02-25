package ua.estate.rialto.util.excelparser.exception;

import java.util.ArrayList;
import java.util.List;

public class ExcelInvalidCellValuesException extends  ExcelParsingException {
    List<ExcelInvalidCell> invalidCells;
    public ExcelInvalidCellValuesException(String message) {
        super(message);
        invalidCells = new ArrayList<>();
    }

    public ExcelInvalidCellValuesException(String message, Exception exception) {
        super(message, exception);
        invalidCells = new ArrayList<>();
    }

    public List<ExcelInvalidCell> getInvalidCells() {
        return invalidCells;
    }

    public void setInvalidCells(List<ExcelInvalidCell> invalidCells) {
        this.invalidCells = invalidCells;
    }

    public void addInvalidCell(ExcelInvalidCell excelInvalidCell)
    {
        invalidCells.add(excelInvalidCell);
    }
}