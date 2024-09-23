package main;

import java.nio.file.*;
import java.io.*;
import java.util.*;
import java.util.stream.*;

public class main {
	
	static GenerateInfoFiles createFiles = new GenerateInfoFiles();
	
	public static Map<String, List<String>> readSalerFiles() throws IOException {
		Map<String, List<String>> vendedores = new HashMap<>();
        try (Stream<Path> stream = Files.list(Paths.get("."))) {
            stream
                .filter(path -> path.toString().endsWith(".txt") && path.toString().contains("Saler"))
                .forEach(path -> {
                    List<String> productos = new ArrayList<>();
                    String vendedor = null;
                    try (BufferedReader br = new BufferedReader(new FileReader(path.toFile()))) {
                        String line;
                        while ((line = br.readLine()) != null) {
                            if (vendedor == null) {
                                vendedor = line;
                            } else {
                                productos.add(line);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (vendedor != null) {
                        vendedores.put(vendedor, productos);
                    }
                });
        }
        return vendedores;
    }
	
	 public static List<String> genericFileReader(String fileName) throws IOException {
	        List<String> sellers = new ArrayList<>();
	        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
	            String line;
	            while ((line = br.readLine()) != null) {
	                sellers.add(line);
	            }
	        }
	        return sellers;
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
