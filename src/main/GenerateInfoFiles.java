package main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GenerateInfoFiles {
	static List<String> docTypes = Arrays.asList("Cedula", "Pasaporte", "Cedula Extranjeria");
	static List<String> names = Arrays.asList("Pepe", "Pedro", "Juan", "Luis", "Carlos", "José", "Miguel", "Andrés", "Diego", "Antonio", "Manuel", "Javier", "Fernando", "Ricardo", "Alejandro", "Hugo", "Martín", "Daniel", "Santiago", "Pablo");
	static List<String> lastNames = Arrays.asList("García", "Martínez", "Rodríguez", "López", "Hernández", "Pérez", "Sánchez", "Ramírez", "Torres", "Flores", "González", "Fernández", "Castro", "Vargas", "Morales", "Reyes", "Mendoza", "Romero", "Díaz", "Cruz");
	static List<String> products = Arrays.asList("Televisor", "Teléfono móvil", "Laptop", "Cámara fotográfica", "Auriculares", "Reloj inteligente", "Tablet", "Teclado mecánico", "Consola de videojuegos", "Impresora"
);
	static List<String> documents = new ArrayList<>();
	static Random random = new Random();
	public GenerateInfoFiles() {
		deletTxtFiles();
		for(int i = 0; i < random.nextInt(10) + 1; i++) {
			createSalesBySellerFile(i+1);
		}					
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
        String document = docTypes.get(random.nextInt(docTypes.size()));
        
        int randomNumber = random.nextInt(1000000000) + 1000000000;
        String documentInfo =  document + ";" + randomNumber;
        documents.add(documentInfo);
        return documentInfo;
    }
	
	public String createSaleRecord() {		
        int randomProductId = random.nextInt(10) + 1;
        int randomQuantitySold = random.nextInt(5) + 1;
        return randomProductId + ";" + randomQuantitySold;
	}
	
	public void createSalesBySellerFile(int Amount){
		String seller = createSalerDocTypeDocNum();
		List<String> records =  new ArrayList<>();
		records.add(seller);
		for(int i = 0; i < random.nextInt(10) + 1;i++) {
			String saleInfo = createSaleRecord();
			records.add(saleInfo);
		}				
		createFile("Saler" + Amount, records);
	}
}
