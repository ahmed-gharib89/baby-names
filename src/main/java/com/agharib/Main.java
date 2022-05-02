package com.agharib;

import edu.duke.FileResource;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.jetbrains.annotations.NotNull;

public class Main {
    public void totalBirths(FileResource fr) {
        int totalBirths = 0;
        int totalBoys = 0;
        int totalGirls = 0;
        int totalNames = 0;
        int numBoys = 0;
        int numGirls = 0;
        for (CSVRecord rec : fr.getCSVParser(false)) {
            totalNames++;
            int numBorn = Integer.parseInt(rec.get(2));
            totalBirths += numBorn;
            if (rec.get(1).equals("M")) {
                totalBoys += numBorn;
                numBoys++;
            }
            else {
                totalGirls += numBorn;
                numGirls++;
            }
        }
        System.out.println("Total births = " + totalBirths + " and number of names = " + totalNames + " names");
        System.out.println("Total Boys = " + totalBoys + " and number of boys = " + numBoys + " boys");
        System.out.println("Total Girls = " + totalGirls + " and number of girls = " + numGirls + " girls");
    }

    public int getRank(int year, String name, String gender) {
        int count = 0;
        String frName = "src/main/resources/us_babynames/us_babynames_test/yob" + year + "short.csv";
        FileResource fr = new FileResource(frName);
        for (CSVRecord rec : fr.getCSVParser(false)) {
            if (rec.get(1).equals(gender)) {
                count++;
                if (rec.get(0).equals(name)) {
                    return count;
                }
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        System.out.println("Starting the application..");
        FileResource fr = new FileResource("src/main/resources/us_babynames/us_babynames_test/example-small.csv");
        Main m = new Main();
//        m.totalBirths(fr);
        System.out.println("Rank of Mason and Gender M in year 2012 = " + m.getRank(2012, "Mason", "M"));
        System.out.println("Rank of Mason and Gender F in year 2012 = " + m.getRank(2012, "Mason", "F"));
    }
}
