package com.mp2i.colloscope;

import com.badlogic.gdx.Gdx;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import  org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import java.io.IOException;
import java.util.Calendar;
import java.util.Date;


public class excelFileReader {

    private static final int GROUP_ROW_OFFSET = 8;
    private static final int GROUP_COLUMN_OFFSET = 1;

    private static final int DATE_ROW_OFFSET = 7;
    private static XSSFSheet sheet;




    /**
     * Init stuff related to excel file
     * */
    public static void LoadSheet() throws IOException {
            XSSFWorkbook wb = new XSSFWorkbook(Gdx.files.internal("colloscope.xlsx").read());
            //creating Workbook instance that refers to .xlsx file
            sheet = wb.getSheetAt(0);     //creating a Sheet object to retrieve object
    }

    /**
     * Get the names of the members of a group, using their group number
     * @param groupNumber the ID number of the group
     * @return array containing the names
     */
    public static String[] getGroupNames(int groupNumber) {
        assert groupNumber >= 1 && groupNumber <= 16;

        Row row = sheet.getRow(groupNumber+GROUP_ROW_OFFSET);
        return new String[]{row.getCell(GROUP_COLUMN_OFFSET).getStringCellValue(),
                row.getCell(1+GROUP_COLUMN_OFFSET).getStringCellValue(),
                row.getCell(2+GROUP_COLUMN_OFFSET).getStringCellValue()};

    }


    /**
     * function to display the content of the file.
     * Used for debugging purpose.
     */
    public static void tempTest() {
        try {
            int currentLine = 0;

            for (Row row : sheet)     //iteration over row using for each loop
            {
                System.out.print("line "+currentLine+": ");
                for (Cell cell : row)    //iteration over cell using for each loop
                {
                    switch (cell.getCellType()) {
                        case NUMERIC:   //field that represents numeric cell type
                            System.out.print(cell.getNumericCellValue() + "\t\t");
                            break;
                        case STRING:    //field that represents string cell type
                            System.out.print(cell.getStringCellValue() + "\t\t");
                            break;

                    }
                }
                System.out.println();
                currentLine++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Get the colles of a given group using their number and the current date
     * @param groupNumber the ID number of the group
     * @return the Colles object containing the colles
     */
    public static Colles getColles(int groupNumber) throws Exception {

        assert groupNumber >= 1 && groupNumber <= 16;

        Calendar calendar = Calendar.getInstance();

        int numberOfTheWeek = (calendar.get(Calendar.DAY_OF_WEEK)-1) % 7; // because of american format, Sunday is 1 (I think ?)
        int numberOfTheMonth = calendar.get(Calendar.DAY_OF_MONTH);
        int monthNumber = calendar.get(Calendar.MONTH);
        int numberOfTheFirstDayOfTheWeek = numberOfTheMonth - numberOfTheWeek + 1;
        numberOfTheFirstDayOfTheWeek = 16;

            Row dateRow = sheet.getRow(DATE_ROW_OFFSET);

            int dateColumn = 0;
            for (Cell dateCell : dateRow) {
                if (dateCell.getCellType() == CellType.NUMERIC) {// make sure we don't run into an error
                    Date dateCellValue = dateCell.getDateCellValue(); // can throw exception
                    if (dateCellValue.getMonth()  == monthNumber && dateCellValue.getDate() == numberOfTheFirstDayOfTheWeek) {
                        Row row = sheet.getRow(DATE_ROW_OFFSET + 1 + groupNumber);
                        Cell cell = row.getCell(dateColumn);
                        return new Colles (cell);
                    }
                }
                dateColumn++;
            }
        throw new Exception("week not found");
    }


    public static Colle getColleWithCode(String code) {
        assert code.length() >= 2 && code.length() <= 3;

        char subject = code.charAt(0);
        int number = Integer.parseInt(code.substring(1));

        // find offset of the cells using the code
        int offsetX;
        int offsetY;

        String matiere = "";

        switch (subject) {
            case 'M':
                offsetY = 30 + number;
                offsetX = 7;
                matiere = "Mathématiques";
                break;

            case 'I':
                offsetY = 45 + number;
                offsetX = 7;
                matiere = "Informatique";

                break;

            case 'P':
                offsetY = 30 + number;
                offsetX = 15;
                matiere = "Physique";

                break;

            case 'A':
                offsetY = 41 + number;
                offsetX = 15;
                matiere = "Anglais";

                break;

            default:
                throw new RuntimeException("invalid colle code");
        }
        Row row = sheet.getRow(offsetY);
        return new Colle(matiere,
                row.getCell(offsetX).getStringCellValue(), // nom
                row.getCell(offsetX + 2).getStringCellValue(), // creneau
                row.getCell(offsetX + 4).getStringCellValue(), // code (un peu inutile)
                row.getCell(offsetX + 5).getStringCellValue()); // salle

    }
}


