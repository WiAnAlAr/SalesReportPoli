package main;

import java.nio.file.*;
import java.io.*;
import java.util.*;
import java.util.stream.*;

public class main {
	
	//instanciacion de la clase GenerateInfoFiles para generar los archivos necesarios
	static GenerateInfoFiles createFiles = new GenerateInfoFiles();
	
	//metodo para la lectura de los archivos de las ventas realizadas por un vendedor
	public static Map<String, List<String>> readSalerFiles() throws IOException {
		Map<String, List<String>> sellers = new HashMap<>();
        try (Stream<Path> stream = Files.list(Paths.get("."))) {
            stream
                .filter(path -> path.toString().endsWith(".txt") && path.toString().contains("Saler"))
                .forEach(path -> {
                    List<String> products = new ArrayList<>();
                    String seller = null;
                    try (BufferedReader br = new BufferedReader(new FileReader(path.toFile()))) {
                        String line;
                        while ((line = br.readLine()) != null) {
                            if (seller == null) {
                            	seller = line;
                            } else {
                            	products.add(line);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (seller != null) {
                    	sellers.put(seller, products);
                    }
                });
        }
        return sellers;
    }
	
	//metodo generico para leer los archivos de vendedores y productos
	 public static List<String> genericFileReader(String fileName) throws IOException {
	        List<String> listInformation = new ArrayList<>();
	        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
	            String line;
	            while ((line = br.readLine()) != null) {
	            	listInformation.add(line);
	            }
	        }
	        return listInformation;
	    }

	public static void main(String[] args) throws IOException {	
		
		List<String> sellers = genericFileReader("Sellers.txt");
		List<String> products = genericFileReader("Products.txt");
		Map<String, List<String>> salesInfo = readSalerFiles();
		List<String> sellersReportList = new ArrayList<>();
		System.out.println("Lista de vendedores:");
		for(String sellerInfo: sellers) {
			int totalSales = 0;
			String[] sellerInfoArray = sellerInfo.split(";");
			for (Map.Entry<String, List<String>> entry : salesInfo.entrySet()) {				            
				if( entry.getKey().startsWith((sellerInfoArray[0] + ";" + sellerInfoArray[1]))) {
					for (String saledProductInfo : entry.getValue()) {
						String[] saledProductInfoArray = saledProductInfo.split(";");
						for (String product: products) {
							if(product.startsWith(saledProductInfoArray[0])) {
								String[] productArray = product.split(";");
								totalSales += Integer.parseInt(saledProductInfoArray[1]) * Integer.parseInt(productArray[2]);
							}
						}								                
		            }
				}	            
	        }	
			sellersReportList.add(sellerInfo + ";"+ Integer.toString(totalSales) + ";");			
		}
		sellersReportList.sort((s1, s2) -> {
            String[] firstElement = s1.split(";");
            String[] secondElement = s2.split(";");
            int num1 = Integer.parseInt(firstElement[firstElement.length - 1]);
            int num2 = Integer.parseInt(secondElement[secondElement.length - 1]);
            return Integer.compare(num2, num1);
        });
		for(String item: sellersReportList) {
			System.out.println(item);
		}		
	}
}
