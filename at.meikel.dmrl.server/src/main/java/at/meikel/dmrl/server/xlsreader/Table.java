package at.meikel.dmrl.server.xlsreader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

public class Table {

    private Vector<Row> rows = new Vector<Row>();
    private int minColumnIndex = 0;
    private int maxColumnIndex = 0;
    private Hashtable<Integer, Integer> virtual2physicalMap = null;
    private Hashtable<Integer, String> columnNames = null;

    private static final Logger LOGGER = Logger.getLogger(Table.class);

    private Table() {
        // intentionally left empty
    }

    public static Table read(String filename, String sheetName) {
        try {
            return read(new FileInputStream(filename), sheetName);
        } catch (FileNotFoundException e) {
            LOGGER.warn("Unable to read file.", e);
            return null;
        }
    }

    public static Table read(InputStream is, String sheetName) {
        Table result = new Table();
        try {
            HSSFWorkbook wb = new HSSFWorkbook(is);
            // HSSFSheet sheet = wb.getSheetAt(0);
            HSSFSheet sheet = wb.getSheet(sheetName);
            for (int rowIndex = sheet.getFirstRowNum(); rowIndex <= sheet
                    .getLastRowNum(); rowIndex++) {
                HSSFRow row = sheet.getRow(rowIndex);

                if (row == null) {
                    continue;
                }

                Row xrrow = result.createRow();

                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    Object value = null;
                    switch (cell.getCellType()) {
                        case Cell.CELL_TYPE_BLANK:
                            value = "CELL_TYPE_BLANK";
                            break;

                        case Cell.CELL_TYPE_BOOLEAN:
                            value = cell.getBooleanCellValue();
                            break;

                        case Cell.CELL_TYPE_ERROR:
                            value = cell.getErrorCellValue();
                            break;

                        case Cell.CELL_TYPE_FORMULA:
                            value = cell.getCellFormula();
                            break;

                        case Cell.CELL_TYPE_NUMERIC:
                            value = cell.getNumericCellValue();
                            break;

                        case Cell.CELL_TYPE_STRING:
                            value = cell.getStringCellValue();
                            break;

                        default:
                            value = "NA";
                            break;
                    }
                    if (value != null) {
                        xrrow.setColumnValue(cell.getColumnIndex(), value);
                    }
                }
            }
        } catch (IOException e) {
            LOGGER.warn("Unable to read file.", e);
            return null;
        }

        result.computeColumnVirtualIndicesAndHeaders();
        return result;
    }

    public boolean hasColumns() {
        return columnNames != null;
    }

    public String getColumnName(int columnIndex) {
        return columnNames.get(new Integer(columnIndex));
    }

    public int getMinRowIndex() {
        return 1;
    }

    public int getMaxRowIndex() {
        return rows.size();
    }

    public Row getRow(int rowIndex) {
        return rows.get(new Integer(rowIndex - 1));
    }

    public void print(PrintStream ps) {
        for (int columnIndex = minColumnIndex; columnIndex <= maxColumnIndex; columnIndex++) {
            if (columnIndex > minColumnIndex) {
                ps.print(" | ");
            }
            ps.print(getColumnName(columnIndex));
        }

        ps.println();
        ps.println();

        for (Row row : rows) {
            for (int columnIndex = minColumnIndex; columnIndex <= maxColumnIndex; columnIndex++) {
                if (columnIndex > minColumnIndex) {
                    ps.print(" | ");
                }
                ps.print(row.getColumnValue(virtual2physicalMap
                        .get(new Integer(columnIndex).intValue())));
            }

            ps.println();
            ps.println();
        }
    }

    private Row createRow() {
        Row result = new Row();
        rows.add(result);
        return result;
    }

    private void computeColumnVirtualIndicesAndHeaders() {
        String[] skipValues = new String[]{"Platz", "Pass", "Name", "Kat.",
                "Kader", "Verein"
                // , "LV", "Wert", "+/- Platz", "+/- Wert", "Anzahl",
                // "Strei-cher", "Wer-tung", "Max."
        };
        SortedSet<Integer> physicalIndices = new TreeSet<Integer>();
        Iterator<Row> rowIterator = rows.iterator();
        boolean skip = true;
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            if (skip) {
                skip = false;
                for (int i = 0; i < skipValues.length; i++) {
                    if (!skipValues[i].equals(row.getNthColumnValue(i))) {
                        skip = true;
                        break;
                    }
                }

                if (skip) {
                    rowIterator.remove();
                    continue;
                }

                physicalIndices.addAll(row.getColomnIndices());
            }
        }

        if (physicalIndices.isEmpty()) {
            return;
        }

        virtual2physicalMap = new Hashtable<Integer, Integer>();
        int virtualIndex = 0;
        for (Integer physicalIndex : physicalIndices) {
            virtualIndex++;
            virtual2physicalMap.put(new Integer(virtualIndex), physicalIndex);
        }
        minColumnIndex = 1;
        maxColumnIndex = virtualIndex;

        if (rows.isEmpty()) {
            return;
        }

        columnNames = new Hashtable<Integer, String>();
        Row headerRow = rows.remove(0);
        for (int columnIndex = minColumnIndex; columnIndex <= maxColumnIndex; columnIndex++) {
            headerRow.getColumnValue(columnIndex);
            Object value = headerRow.getColumnValue(virtual2physicalMap
                    .get(new Integer(columnIndex).intValue()));
            columnNames.put(new Integer(columnIndex), value == null ? "NA"
                    : value.toString());
        }
    }
}
