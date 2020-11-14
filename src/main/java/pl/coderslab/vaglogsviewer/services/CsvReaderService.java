package pl.coderslab.vaglogsviewer.services;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class CsvReaderService {
    public List<String[]> readAllDataAtOnceWithHeaders(File file) {
        List<String[]> allData = new ArrayList<>();
        try {
            // Create an object of file reader
            // class with CSV file as a parameter.
            FileReader filereader = new FileReader(String.valueOf(file));

            // create csvReader object and skip first Line
            CSVReader csvReader = new CSVReaderBuilder(filereader)
                    .withSkipLines(4)
                    .build();
            allData = csvReader.readAll();

//            for (String[] row : allData) {
//                for (String cell : row) {
//                    System.out.print(cell + "\t");
//                }
//                System.out.println();
//            }

//            for (String[] row : allData) {
//                finalData.add(row);
//            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return allData;
    }

    public List<String> readFirstHeader(List<String[]> list) {
        List<String> firstHeader = new ArrayList<>();
        for (String cell : list.get(0)) {
            firstHeader.add(cell);

        }
        return firstHeader;
    }

    public List<String> readSecondHeader(List<String[]> list) {
        List<String> secondHeader = new ArrayList<>();
        for (String cell : list.get(1)) {
            secondHeader.add(cell);

        }
        return secondHeader;
    }
    public List<String> readThirdHeader(List<String[]> list) {
        List<String> thirdHeader = new ArrayList<>();
        for (String cell : list.get(2)) {
            thirdHeader.add(cell);

        }
        return thirdHeader;
    }
    public List<String[]> readAllDataAtOnceWithoutHeaders(File file) {
        List<String[]> allData = new ArrayList<>();
        try {
            // Create an object of file reader
            // class with CSV file as a parameter.
            FileReader filereader = new FileReader(String.valueOf(file));

            // create csvReader object and skip first Line
            CSVReader csvReader = new CSVReaderBuilder(filereader)
                    .withSkipLines(7)
                    .build();
            allData = csvReader.readAll();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return allData;
    }

}