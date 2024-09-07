package main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GenerateInfoFiles {
	static List<String> options = Arrays.asList("Cedula", "Pasaporte", "Cedula Extranjeria");
	static Random random = new Random();
	public GenerateInfoFiles() {
		deletTxtFiles();
		createSalesBySellerFile(1);			
	}
	
	private void createFile(String fileName, List<String> content) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName + ".txt"));
            for (String line : content) {
                writer.write(line);
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(fileName + ".txt" + " has been created.");
    }
	private void deletTxtFiles() {
        String projectDirectoryPath = System.getProperty("user.dir");
        File directory = new File(projectDirectoryPath);
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.getName().endsWith(".txt")) {
                    if (file.delete()) {
                        System.out.println(file.getName() + " has been deleted.");
                    } else {
                        System.out.println("Failed to delete " + file.getName());
                    }
                }
            }
        } else {
            System.out.println(projectDirectoryPath + " is not a directory or does not exist.");
        }        
    }
	public String createSalerDocTypeDocNum() {                
        String document = options.get(random.nextInt(options.size()));
        int randomNumber = random.nextInt(1000000000) + 1000000000;
        return document + ";" + randomNumber;
    }
	public String createSaleRecord() {		
        int randomProductId = random.nextInt(10) + 1;
        int randomQuantitySold = random.nextInt(5) + 1;
        return randomProductId + ";" + randomQuantitySold;
	}
	public void createSalesBySellerFile(int Amount){
		String seller = createSalerDocTypeDocNum();
		String saleInfo = createSaleRecord();
		List<String> records = Arrays.asList(seller, saleInfo);
		createFile("Saler" + Amount, records);
	}
}
