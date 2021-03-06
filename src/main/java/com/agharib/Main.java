package com.agharib;

import edu.duke.DirectoryResource;
import edu.duke.FileResource;
import org.apache.commons.csv.CSVRecord;

import java.io.File;

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
        String frName = "src/main/resources/us_babynames/us_babynames_by_year/yob" + year + ".csv";
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

    public String getName(int year, int rank, String gender) {
        int count = 0;
        String name = "NO NAME";
        String frName = "src/main/resources/us_babynames/us_babynames_by_year/yob" + year + ".csv";
        FileResource fr = new FileResource(frName);
        for (CSVRecord rec : fr.getCSVParser(false)) {
            if (rec.get(1).equals(gender)) {
                count++;
            }
            if (count == rank) {
                return rec.get(0);
            }
        }
        return name;
    }

    public void whatIsNameInYear (String name, int year, int newYear, String gender) {
        int rank = getRank(year, name, gender);
        String newName = getName(newYear, rank, gender);
        String sheOrHe = "";
        if(gender.equals("F")){
            sheOrHe = "she";
        } else {
            sheOrHe = "he";
        }
        System.out.println(name + " born in "+ year + " would be " + newName + " if " + sheOrHe +" was born in " + newYear + ".");
    }

    public int yearOfHighestRank(String name, String gender) {
        int year = -1;
        int rank = Integer.MAX_VALUE;
        DirectoryResource dr = new DirectoryResource();
        for (File f: dr.selectedFiles()) {
            int currYear = Integer.parseInt(f.getName().substring(3, 7));
            int currRank = getRank(currYear, name, gender);
            if (currRank != -1 && currRank < rank) {
                rank = currRank;
                year = currYear;
            }
        }
        return year;
    }

    public double getAverageRank(String name, String gender) {
        double totalRank = 0;
        double count = 0;
        DirectoryResource dr = new DirectoryResource();
        for (File f: dr.selectedFiles()) {
            int currYear = Integer.parseInt(f.getName().substring(3, 7));
            int currRank = getRank(currYear, name, gender);
            count ++;
            totalRank += currRank;
        }
        return totalRank / count;
    }

    public int getTotalBirthsRankedHigher (int year, String name, String gender) {
        int totalBirths = 0;
        String frName = "src/main/resources/us_babynames/us_babynames_by_year/yob" + year + ".csv";
        FileResource fr = new FileResource(frName);
        for (CSVRecord rec : fr.getCSVParser(false)) {
            if (rec.get(1).equals(gender)) {
                if (rec.get(0).equals(name)) {
                    break;
                }
                totalBirths += Integer.parseInt(rec.get(2));
            }
        }
        return totalBirths;
    }

    public static void main(String[] args) {
        System.out.println("Starting the application..");
        FileResource fr = new FileResource("src/main/resources/us_babynames/us_babynames_by_year/yob1905.csv");
        Main m = new Main();
//        m.totalBirths(fr);
//        System.out.println("Rank of Frank and Gender M in year 1971 = " + m.getRank(1971, "Frank", "M"));
//        System.out.println("Rank of Emily and Gender F in year 1960 = " + m.getRank(1960, "Emily", "F"));
//        System.out.println("The name of ranked 450 male in 1982 = " + m.getName(1982, 450, "M"));
//        System.out.println("The name of ranked 350 female in 1980 = " + m.getName(1980, 350, "F"));
//        System.out.println("The name of ranked 100 male in 2012 = " + m.getName(2012, 100, "M"));
//        m.whatIsNameInYear("Owen", 1974, 2014, "M");
//        m.whatIsNameInYear("Mason", 2012, 2014, "F");
//        System.out.println(m.yearOfHighestRank("Mich", "M"));
//        System.out.println(m.getAverageRank("Susan", "F"));
//        System.out.println(m.getAverageRank("Robert", "M"));
        System.out.println(m.getTotalBirthsRankedHigher(1990, "Emily", "F"));
        System.out.println(m.getTotalBirthsRankedHigher(1990, "Drew", "M"));

    }
}
