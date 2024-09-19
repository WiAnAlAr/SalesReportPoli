package main;

import java.nio.file.*;
import java.io.*;
import java.util.*;
import java.util.stream.*;

public class main {
	
	static GenerateInfoFiles createFiles = new GenerateInfoFiles();
	
	public static Map<String, List<String>> reedSalerFiles() throws IOException {
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

	public static void main(String[] args) throws IOException {	
		Map<String, List<String>> vendedores = reedSalerFiles();
		for (Map.Entry<String, List<String>> entry : vendedores.entrySet()) {
            System.out.println("Vendedor: " + entry.getKey());
            System.out.println("Productos: ");
            for (String producto : entry.getValue()) {
                System.out.println("\t" + producto);
            }
        }
	}

}
