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
	//se las variables necesarios para la creacion de los archivos
	static List<String> docTypes = Arrays.asList("Cedula", "Pasaporte", "Cedula Extranjeria");//lista de tipos de documento
	static List<String> names = Arrays.asList("Pepe", "Pedro", "Juan", "Luis", "Carlos", "José", "Miguel", "Andrés", "Diego", "Antonio", "Manuel", "Javier", "Fernando", "Ricardo", "Alejandro", "Hugo", "Martín", "Daniel", "Santiago", "Pablo");//lista de nombres
	static List<String> lastNames = Arrays.asList("García", "Martínez", "Rodríguez", "López", "Hernández", "Pérez", "Sánchez", "Ramírez", "Torres", "Flores", "González", "Fernández", "Castro", "Vargas", "Morales", "Reyes", "Mendoza", "Romero", "Díaz", "Cruz"); //lista de apellidos
	static List<String> products = Arrays.asList("Televisor", "Teléfono móvil", "Laptop", "Cámara fotográfica", "Auriculares", "Reloj inteligente", "Tablet", "Teclado mecánico", "Consola de videojuegos", "Impresora");//lista de productos
	static List<String> documents = new ArrayList<>();//lista para almacenar los documentos de los vendedores
	static Random random = new Random();// clase para seleccionar numeros aleatorios
	//clase constuctora en la que se llamaran los metodos para la eliminancion y la creacion de los archivos necesarios
	public GenerateInfoFiles() {
		deletTxtFiles();
		for(int i = 0; i < random.nextInt(10) + 1; i++) {
			createSalesBySellerFile(i+1);
		}
		createSellersFile();
		createProductsFile();
	}
	//metodo para la creacion de archivos genericos
	public void createFile(String fileName, List<String> content) {
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
	//metodo para la eliminancion de archivos .txt
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
	//metodo para la creacion de registros de numero de documento y tipo de documento
	private String createSalerDocTypeDocNum() {                
        String document = docTypes.get(random.nextInt(docTypes.size()));
        
        int randomNumber = random.nextInt(1000000000) + 1000000000;
        String documentInfo =  document + ";" + randomNumber;
        documents.add(documentInfo);
        return documentInfo;
    }
	//metodo para crear los registros de productos vendidos por un vendedor
	private String createSaleRecord() {		
        int randomProductId = random.nextInt(10) + 1;
        int randomQuantitySold = random.nextInt(5) + 1;
        return randomProductId + ";" + randomQuantitySold;
	}
	//metodo para la creacion del archivos de las ventas realizadas por un vendedor
	private void createSalesBySellerFile(int Amount){
		String seller = createSalerDocTypeDocNum();
		List<String> records =  new ArrayList<>();
		records.add(seller);
		for(int i = 0; i < random.nextInt(10) + 1;i++) {
			String saleInfo = createSaleRecord();
			records.add(saleInfo);
		}				
		createFile("Saler" + Amount, records);
	}
	//metodo para la generacion de nombres y apellidos
	private String createSellerNamesAndLastNames() {		
        int indexName1 = random.nextInt(names.size());
        int indexName2 = random.nextInt(names.size());        
        while (indexName1 == indexName2) {
            indexName2 = random.nextInt(names.size());
        }        
        int indexLastName1 = random.nextInt(lastNames.size());
        int indexLastName2 = random.nextInt(lastNames.size());        
        while (indexLastName1 == indexLastName2) {
            indexLastName2 = random.nextInt(names.size());
        }
        return String.join(" ", names.get(indexName1), names.get(indexName2)+";", lastNames.get(indexLastName1), lastNames.get(indexLastName2));
	}
	//metodo para la creacion del archivo que contiene la informacion de vendedores
	private void createSellersFile() {
		List<String> records =  new ArrayList<>();
		for(String Item: documents)
		{
			records.add(Item + ";" + createSellerNamesAndLastNames());			
		}
		createFile("Sellers", records);
	}
	//metodo para la creacion del archivo que contiene la informacion de los productos
	private void createProductsFile() {
		List<String> records =  new ArrayList<>();
		int i = 1;
		for(String Item: products) {
			int price = random.nextInt(1000001) + 1000000;
			records.add(i+";"+Item+";"+price);
			i++;
		}
		createFile("Products", records);
	}
}
