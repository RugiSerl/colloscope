package com.mp2i.colloscope;

import org.apache.poi.ss.usermodel.Cell;

import java.util.ArrayList;


public class Colles {

    public int amount = 0;
    public final ArrayList<Colle> colles ;


    public Colles(Cell cell) {
        assert cell != null;

        // have to replace "+" to "-" because when the string is split after it doesn't like "+" as separator
        String cellCodes = cell.getStringCellValue().replace(" ", "").replace("+", "-");

        colles = new ArrayList<>();


        for (String code : cellCodes.split("-")) {

            this.colles.add(excelFileReader.getColleWithCode(code.replace("-", "")));
            this.amount++;

        }




    }
}
