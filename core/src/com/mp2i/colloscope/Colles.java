package com.mp2i.colloscope;

import org.apache.poi.ss.usermodel.Cell;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


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

        // Add french colles if needed
        if (cellCodes.equals("M2-P1")) {
            this.colles.add(new Colle(
                    "Français",
                    "Mme Gleize",
                    "Mercredi 14h30",
                    "F1",
                    "E 205"
            ));
            this.amount++;

        } else if (cellCodes.equals("M9-A3")) {
            this.colles.add(new Colle(
                    "Français",
                    "Mme Gleize",
                    "Mercredi 16h00",
                    "F2",
                    "E 205"
            ));
            this.amount++;

        }

        Collections.sort(colles, new Comparator<Colle>() {
            @Override
            public int compare(Colle c1, Colle c2) {
                return Integer.compare(c2.ordre, c1.ordre);
            }

        });

    }


}
