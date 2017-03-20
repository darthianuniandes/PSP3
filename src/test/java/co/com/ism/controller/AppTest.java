package co.com.ism.controller;

import java.util.LinkedList;
import java.util.List;

import co.com.ism.model.Item;
import co.com.ism.model.Parte;
import co.com.ism.model.Programa;
import co.com.ism.services.SizeView;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {
	
	private SizeView sizeView;
	private List<Item> listaItems;
	private List<Parte> listaPartes;
	private Programa prog;
	
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName ) {
        super( testName );
        sizeView = new SizeView();
        
        listaItems = new LinkedList<Item>();
        Item item = new Item();
        item.setNombre("nombreItem");
        item.setTamanio(0);
        listaItems.add(item);
        
        listaPartes = new LinkedList<Parte>();
        
        for (int i = 0; i < 16; i++) {
			Parte parte = new Parte("nombre"+i, "direccion"+i, listaItems);
			listaPartes.add(parte);
		}
        
        listaPartes.get(0).setTamanio(8);
        listaPartes.get(1).setTamanio(8);
        listaPartes.get(2).setTamanio(20);
        listaPartes.get(3).setTamanio(14);
        listaPartes.get(4).setTamanio(18);
        listaPartes.get(5).setTamanio(12);
        listaPartes.get(6).setTamanio(12);
        listaPartes.get(7).setTamanio(10);
        listaPartes.get(8).setTamanio(12);
        listaPartes.get(9).setTamanio(10);
        listaPartes.get(10).setTamanio(12);
        listaPartes.get(11).setTamanio(12);
        listaPartes.get(12).setTamanio(12);
        listaPartes.get(13).setTamanio(12);
        listaPartes.get(14).setTamanio(8);
        listaPartes.get(15).setTamanio(7);        
    	
    	prog = new Programa("nombre", "direccion");
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {        
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testCalcularRangos() {
    	prog.setListaPartes(listaPartes);
    	List<Double> test = sizeView.calcularRangos(prog); 
        assertEquals(test.get(0), 6.3375, 4);
        assertEquals(test.get(1), 8.4393, 4);
        assertEquals(test.get(2), 11.2381, 4);
        assertEquals(test.get(3), 14.9650, 4);
        assertEquals(test.get(4), 19.9280, 4);
    }
    
    /**
     * Rigourous Test :-)
     */
    public void testCalcularLogaritmos() {
        prog.setListaPartes(listaPartes);
        sizeView.calcularRangos(prog);
                
        assertEquals(sizeView.getLnXi().get(0), 1.3862, 4);
        assertEquals(sizeView.getLnXi().get(1), 1.3862, 4);
        assertEquals(sizeView.getLnXi().get(2), 2.3025, 4);
        assertEquals(sizeView.getLnXi().get(3), 1.9459, 4);
        assertEquals(sizeView.getLnXi().get(4), 2.1972, 4);
        assertEquals(sizeView.getLnXi().get(5), 1.7917, 4);
        assertEquals(sizeView.getLnXi().get(6), 1.7917, 4);
        assertEquals(sizeView.getLnXi().get(7), 1.6094, 4);
        assertEquals(sizeView.getLnXi().get(8), 1.7917, 4);
        assertEquals(sizeView.getLnXi().get(9), 1.6094, 4);
        assertEquals(sizeView.getLnXi().get(10), 1.7917, 4);
        assertEquals(sizeView.getLnXi().get(11), 1.7917, 4);
        assertEquals(sizeView.getLnXi().get(12), 1.7917, 4);
        assertEquals(sizeView.getLnXi().get(13), 1.7917, 4);
        assertEquals(sizeView.getLnXi().get(14), 1.3862, 4);
        assertEquals(sizeView.getLnXi().get(15), 1.0986, 4);
        
    }
    
    /**
     * Rigourous Test :-)
     */
    public void testCalcularPromedio() {
    	prog.setListaPartes(listaPartes);
    	sizeView.calcularRangos(prog);
        assertEquals(sizeView.getAvg(), 2.4193, 4);
    }
    
    /**
     * Rigourous Test :-)
     */
    public void testCalcularVarianza() {
    	prog.setListaPartes(listaPartes);
    	sizeView.calcularRangos(prog);
    	assertEquals(sizeView.getVar(), 0.0820, 4);
    }
    
    /**
     * Rigourous Test :-)
     */
    public void testCalcularDesviacion() {
    	prog.setListaPartes(listaPartes);
    	sizeView.calcularRangos(prog);
        assertEquals(sizeView.getVar(), 0.2863, 4);
    }
}
