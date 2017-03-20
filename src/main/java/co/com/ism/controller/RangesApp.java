package co.com.ism.controller;

import co.com.ism.services.MainView;

/**
 * Hello world!
 *
 */
public class RangesApp {
	
	private static MainView mainView;
	
    public static void main( String[] args ) {
    	mainView = new MainView();
		
		mainView.leerProgramas(args);
		
		mainView.contarProgramas();
		
		mainView.calcularRangos();
		
		mainView.imprimirResultado();
    }
}
