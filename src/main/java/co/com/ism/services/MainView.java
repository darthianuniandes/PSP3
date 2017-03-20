package co.com.ism.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import co.com.ism.model.Item;
import co.com.ism.model.Parte;
import co.com.ism.model.Programa;

/**
 * @author ian
 * Esta clase es la clase que maneja los servicios. Atienede las solicitudes del controlador y 
 * administra la informacion de las entidadesEs en esta clase donde esta el core de la aplicacion. 
 * Principalmente tiene 3 operaciones: Leer los Programas que captura el controlador, Contar o 
 * calcular el tamño de cada programa e imprimir los resultados del analisis de los programas dados.
 * Tiene dos metodos 
 */
public class MainView {
	
	private static List<File> archivos;
	private static List<Programa> listaProgramas;
	private SizeView sizeView;
	
	/**
	 * @author ian
	 * El constructor de la clase inicializa los elementos 
	 */
	public MainView(){
		archivos = null;
		listaProgramas = null;
		sizeView = new SizeView();
	}
	/**
	 * @author ian
	 * Metodo que lee los programas de los direcotrios dados por el controlador.
	 * Utiliza los metodos privados para realizar la tare de obtener los archivos validos. 
	 */
	public void leerProgramas(String[] directoriosProyectos) {
		archivos = new ArrayList<File>();
		listaProgramas = new ArrayList<Programa>();
		
		try{
		if(directoriosProyectos != null && directoriosProyectos.length > 0){
			int i = 0;
			for (String iterator : directoriosProyectos){
				i++;
				Programa programaIterator = new Programa("Project " + i, iterator);
				final File folderI = new File(iterator);
				//Se llena la lista de archivos
				listarElementosDirectorio(folderI);
				List<Parte> listaPartesIterator = new ArrayList<Parte>(); 
				for (File archivo : archivos) {
					Parte nueva = filtrarArchivosJava(archivo);
					if(nueva != null)
						listaPartesIterator.add(nueva);
				}
				// Se cuentan las lineas de los archivos java
				for (Parte parteIterator : listaPartesIterator) {
					parteIterator.setListaItems(itentificarItem(parteIterator));
				}
				
				programaIterator.setListaPartes(listaPartesIterator);
				listaProgramas.add(programaIterator);
				
				archivos = new ArrayList<File>();	
			}
		} else {
			Programa programa = new Programa("Project " + 0, "./");
			final File folder = new File("./");
			//Se llena la lista de archivos
			listarElementosDirectorio(folder);			
			List<Parte> listaPartes = new ArrayList<Parte>();
			for (File archivo : archivos) {
				Parte nueva = filtrarArchivosJava(archivo);
				if(nueva != null)
					listaPartes.add(nueva);
			}
			// Se cuentan las lineas de los archivos java
			for (Parte parteIterator : listaPartes) {
				parteIterator.setListaItems(itentificarItem(parteIterator));
			}
			programa.setListaPartes(listaPartes);
			listaProgramas.add(programa);
		}
		} catch (NullPointerException npe) {
			System.out.println("Ha ingresado un directorio invalido");
		}

	}
	/**
	 * @author ian
	 * Metodo que se ejecuta despues de realizar el analisis de los archivos.
	 * Cuenta el tamaño total de cada programa 
	 */
	public void contarProgramas(){
		for(Programa iterator : listaProgramas){
			iterator.contarTamanio();
		}
	}
	/**
	 * @author ian
	 * Metodo que despues del conteo realzar la impresion de la informacion de forma ordenada 
	 */
	public void imprimirResultado(){
		StringBuilder reporte = new StringBuilder();
		reporte.append("___________________________________________________________________\n");
		reporte.append("|Project Number|   Part Name    |Class LOC/Pages|Number of Methods|\n");
		reporte.append("___________________________________________________________________\n");
		for(Programa iterator : listaProgramas){
			reporte.append("|"+ iterator.getNombre() +"     |                |               |                 |\n");
			reporte.append("___________________________________________________________________\n");
			for(Parte parte : iterator.getListaPartes()){
				reporte.append("|              |"+ parte.getNombre() +"|        "+ parte.getTamanio() +"|        "+ parte.getListaItems().size() +"|").append("\n");
				reporte.append("___________________________________________________________________\n");
			}
		}
		reporte.append("\n");
		reporte.append("_________________Rangos de tamanios__________________________\n");
		reporte.append("_____________________________________________________________\n");
		reporte.append("|           |   VS   |    S   |    M    |    L    |   VL    |\n");
		reporte.append("_____________________________________________________________\n");
		int i = 0;
		DecimalFormat four = new DecimalFormat("#0.0000");
		for(Programa iterator : listaProgramas){
			reporte.append("|Proyecto "+ i++ +" | "+ four.format(iterator.getRangos().get(0)) +" | "+ four.format(iterator.getRangos().get(1)) 
					+" | "+four.format(iterator.getRangos().get(2)) +" | "+four.format(iterator.getRangos().get(3))+" | "+four.format(iterator.getRangos().get(4))+" |").append("\n");
			reporte.append("_____________________________________________________________\n");
		}
		System.out.println(reporte.toString());
	}
	/**
	 * @author ian
	 * Metodo que recorre un direcorio dado de manera recursiva para obetener todos los archivos dentro del directorio raiz 
	 */
	private void listarElementosDirectorio(final File folder) {
		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				listarElementosDirectorio(fileEntry);
			} else {
				archivos.add(fileEntry);
			}
		}
	}
	/**
	 * @author ian
	 * Metodo que determina si un archivo dado es valido para se contado o no 
	 */
	private Parte filtrarArchivosJava(File archivo) {
		Parte parte = null;
		// String to be scanned to find the pattern.
		String pattern = "^(?=.*?\\b\\.java\\b).*$";
		String pattern2 = "^(?=.*?\\b\\.libro\\b).*$";

		// Create a Pattern object
		Pattern r = Pattern.compile(pattern);
		Pattern r2 = Pattern.compile(pattern2);

		// Now create matcher object.
		Matcher m = r.matcher(archivo.getPath());
		Matcher n = r2.matcher(archivo.getPath());
		if (m.find() || n.find()) {
//			System.out.println(archivo.getPath());
			parte = new Parte(archivo.getName(),archivo.getPath(), null);
		}
		return parte;
	}
	/**
	 * @author ian
	 * Metodo que cuenta los items validos de una parte y cuenta su tamaño 
	 */
	private List<Item> itentificarItem(Parte path){
		List<Item> listaItems = new ArrayList<Item>();
		BufferedReader in = null;
		FileInputStream fis = null;
		StringBuilder lectura;
		try {
			fis = new FileInputStream(path.getDireccion());
			in = new BufferedReader(new InputStreamReader(fis));
			boolean flag = true;
			lectura = new StringBuilder();
			String sCurrentLine;
			int numLineas = 0;
			int numItems = 0;
			String pattern = "((?:(?:public)|(?:private)|(?:static)|(?:protected)|(?:synchronized)\\s+) )*((\\w+)|(\\w+\\<\\w+\\>)) +\\w+ *\\(.*\\) *\\{";
			Pattern r = Pattern.compile(pattern);
			Item itemAux = new Item();
			while ((sCurrentLine = in.readLine()) != null) {				
				lectura.append(sCurrentLine);
				if (sCurrentLine.trim().length()>0) {										
					if (!sCurrentLine.trim().startsWith("//")) {
						if (sCurrentLine.contains("/*")) { 
							flag = false;
							if (sCurrentLine.indexOf("/*") != 0) {
								numLineas++;
							}
						} else {
							if (sCurrentLine.contains("*/")) {
								flag = true;					
							} else {
								if (flag) {
									Matcher m = r.matcher(sCurrentLine);
									if (m.find()) {
										numItems++;
										if(numItems > 1) {
											itemAux.setTamanio(numLineas);
											listaItems.add(itemAux);
											numLineas = 0;
											Item item = new Item();
											item.setNombre(sCurrentLine);
											itemAux = item;
										} else {
											Item item = new Item();
											item.setNombre(sCurrentLine);
											itemAux = item;
										}						
									}
									numLineas++;
								}
							}
						}
					}				
				} 
			}
			itemAux.setTamanio(numLineas);
			listaItems.add(itemAux);
		} catch (IOException e) {
			System.out.println("Se peresentó un fallo inesperado en la lectura de los archivos");
			e.printStackTrace();
		} finally {
			try {
				if (in != null)
					in.close();
				if (fis != null)
					fis.close();

			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return listaItems;
	}
	/**
	 * @author ian
	 * Metodo que invoca el metodo de calculo de los rangos de los tamaños de las partes de los programas 
	 */
	public void calcularRangos(){
		for(Programa programa : listaProgramas) {
			programa.setRangos(sizeView.calcularRangos(programa));
		}
		
	}
}
