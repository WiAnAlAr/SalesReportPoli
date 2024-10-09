package main;

import java.nio.file.*;
import java.io.*;
import java.util.*;
import java.util.stream.*;

public class main {
	
	//instanciacion de la clase GenerateInfoFiles para generar los archivos necesarios
	static GenerateInfoFiles generateInfoFiles = new GenerateInfoFiles();
	
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
		Map<String, String> productReportMap = new HashMap<>();
		List<String> productReportList =  new ArrayList<>();
		
		for(String product: products) {
			String[] productArray = product.split(";");
			productReportMap.put(productArray[0], "0");
		}
		
		for(String sellerInfo: sellers) {
			int totalSales = 0;
			String[] sellerInfoArray = sellerInfo.split(";");
			for (Map.Entry<String, List<String>> entry : salesInfo.entrySet()) {				            
				if( entry.getKey().startsWith((sellerInfoArray[0] + ";" + sellerInfoArray[1]))) {
					for (String saledProductInfo : entry.getValue()) {
						String[] saledProductInfoArray = saledProductInfo.split(";");
						for (String product: products) {
							if(product.startsWith(saledProductInfoArray[0] + ";")) {
								String[] productArray = product.split(";");
								totalSales += Integer.parseInt(saledProductInfoArray[1]) * Integer.parseInt(productArray[2]);
								int count = Integer.parseInt(productReportMap.get(saledProductInfoArray[0])) + Integer.parseInt(saledProductInfoArray[1]);
								productReportMap.put(saledProductInfoArray[0], Integer.toString(count));
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
		System.out.println("Reporte de ventas por vendedor:");
		for(String item: sellersReportList) {
			System.out.println(item);
		}
		
		Map<String, String> sortedProductReportMap = productReportMap.entrySet().stream()
	            .sorted((e1, e2) -> Integer.compare(Integer.parseInt(e2.getValue()), Integer.parseInt(e1.getValue())))
	            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
		
		for (Map.Entry<String, String> entry : sortedProductReportMap.entrySet()) {
			for(String product : products) {				
				String[] productArray = product.split(";");				
				if(entry.getKey().equals(productArray[0])) {
					productReportList.add(productArray[1]+";"+productArray[2]+";");
				}
			}
		}		
		System.out.println("Reporte productos vendidos:");
		System.out.println(productReportMap);
		System.out.println(productReportList);
		generateInfoFiles.createFile("SalesReport", sellersReportList);
		generateInfoFiles.createFile("ProductReport", productReportList);
				
	}
}
