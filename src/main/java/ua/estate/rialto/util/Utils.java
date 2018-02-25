package ua.estate.rialto.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class Utils {
    /**
     * Конвертирует лист массивов в строковую таблицу
     *
     * @param rows лист массивов любого типа
     * @param firstIsHeader true - первый элемент листа заголовки колонок, false - обычная строка
     * @param defaultValue если элемент листа пустой, null или его длина меньше количества столбцов в таблице,
     *                     то отображается эта строка, как значение его ячеек
     * @return строковую таблицу
     */
    public static <T> String listToTable(List<T[]> rows, boolean firstIsHeader, String defaultValue) {
        if (rows == null || rows.isEmpty()) return "";

        String nullStr = "null"; // отображается вместо null значений
        Pattern patternEnter = Pattern.compile("[\n]"); // грязь в строке
        Pattern patternNull = Pattern.compile("null"); // замена null на nullStr в deepToString()
        if (defaultValue == null) defaultValue = nullStr;
        StringBuilder sb = new StringBuilder(rows.size());
        List<Integer> maxLenCols = new ArrayList<Integer>();
        List<String[]> rowsStr = new ArrayList<String[]>(rows.size());

        // конвертация массива объектов в массив строк.
        for (int i = 0; i < rows.size(); i++) {
            T[] row = rows.get(i);
            String[] tmpArr = row == null ? new String[0] : new String[row.length];
            for (int j = 0; j < tmpArr.length; j++) {
                if (row[j] == null) {
                    tmpArr[j] = nullStr;
                } else {
                    Class<?> eClass = row[j].getClass();
                    if (eClass.isArray()) {
                        if (eClass == byte[].class) tmpArr[j] = Arrays.toString((byte[]) row[j]);
                        else if (eClass == short[].class) tmpArr[j] = Arrays.toString((short[]) row[j]);
                        else if (eClass == int[].class) tmpArr[j] = Arrays.toString((int[]) row[j]);
                        else if (eClass == long[].class) tmpArr[j] = Arrays.toString((long[]) row[j]);
                        else if (eClass == char[].class) tmpArr[j] = Arrays.toString((char[]) row[j]);
                        else if (eClass == float[].class) tmpArr[j] = Arrays.toString((float[]) row[j]);
                        else if (eClass == double[].class) tmpArr[j] = Arrays.toString((double[]) row[j]);
                        else if (eClass == boolean[].class) tmpArr[j] = Arrays.toString((boolean[]) row[j]);
                        else tmpArr[j] = patternNull.matcher(Arrays.deepToString((Object[]) row[j])).replaceAll(nullStr);
                    } else {  // элемент non-null и не массив
                        tmpArr[j] = row[j].toString().trim();
                    };
                    tmpArr[j] = patternEnter.matcher(tmpArr[j]).replaceAll("");
                }
            }
            rowsStr.add(tmpArr);

            // сохранение количества и длины колонок.
            int strLength;
            for (int j = 0; j < Integer.max(tmpArr.length, maxLenCols.size()); j++) {
                strLength = j < tmpArr.length ? tmpArr[j].length() : defaultValue.length();
                if (j >= maxLenCols.size()) {
                    maxLenCols.add(strLength < defaultValue.length() && i != 0 ? defaultValue.length() : strLength);
                } else if (maxLenCols.get(j) < strLength) {
                    maxLenCols.set(j, strLength);
                }
            }
        }

        // расчет длины верхнего и нижнего отделителя таблицы
        int sepSize = 0;
        if (maxLenCols.isEmpty()) maxLenCols.add(defaultValue.length());
        for (Integer integer : maxLenCols) {
            sepSize += integer;
        }
        sepSize = (sepSize + (maxLenCols.size() * 3)) - 1;

        // формирование таблицы
        sb.append("\n ").append(repeatStr("_", sepSize)).append("\n");
        for (int i = 0; i < rowsStr.size(); i++) {
            sb.append("| ");
            for (int j = 0; j < maxLenCols.size(); j++) {
                // если массив пустой или его длина меньше количества столбцов в таблице - отображаем defaultValue
                String str = j >= rowsStr.get(i).length ? defaultValue : rowsStr.get(i)[j];
                int spaceSize = maxLenCols.get(j) - str.length();
                if (firstIsHeader && i == 0) {
                    sb.append(repeatStr(" ", spaceSize / 2));
                    spaceSize -= spaceSize / 2;
                }
                sb.append(str).append(repeatStr(" ", spaceSize)).append(" | ");
            }
            if (i != rowsStr.size() - 1) {
                sb.append("\n|");
                for (int k = 0; k < maxLenCols.size(); k++) {
                    sb.append(repeatStr(firstIsHeader && i == 0 ? "_" : "-", maxLenCols.get(k) + 2));
                    if (k != maxLenCols.size() - 1) sb.append(firstIsHeader && i == 0 ? "|" : "+");
                }
                sb.append("|\n");
            }
        }
        sb.append("\n ").append(repeatStr("\u00AF", sepSize)).append(" \n");

        return sb.toString();
    }

    public static String repeatStr(String strBit, int count) {
        if (count <= 0) return "";

        StringBuilder sb = new StringBuilder(count);
        for (int i = 0; i < count; i++) {
            sb.append(strBit);
        }

        return sb.toString();
    }
}
