package co.com.ism.services;

import java.util.LinkedList;
import java.util.List;

import co.com.ism.model.Parte;
import co.com.ism.model.Programa;
/**
 * @author ian
 * Esta clase es la responsable de realizar todos los calculos necesarios para calcular 
 * los rangos de los tamaños de las partes de un programa
 */
public class SizeView {
	
	private Programa programa;
	private List<Double> locMethod;
	private List<Double> lnXi;
	private Double avg;
	private Double var;
	private Double sigma;
	private List<Double> ranges;
	
	public SizeView() {
		
	}
	/**
	 * @author ian
	 * Metodo que inicializa los atributos y calcula los rangos 
	 */
	public List<Double> calcularRangos(Programa programa) {
		locMethod = new LinkedList<Double>();
		lnXi = new LinkedList<Double>();
		avg = 0.0;
		var = 0.0;
		sigma = 0.0;
		ranges = new LinkedList<Double>();
		this.programa = programa;
		calcularLogaritmos();
		calcularPromedio();
		calcularVarianza();
		calcularDesviacio();
		calcularRangosLogaritmicos();
		return ranges;
	}
	/**
	 * @author ian
	 * Metodo que calcula los logaritmos de los tamaños de las partes 
	 */
	private void calcularLogaritmos() {
		for(Parte parte : programa.getListaPartes()){
			Double locMethodIter = (double) (parte.getTamanio() / parte.getListaItems().size());
			locMethod.add(locMethodIter);			
		}
		Double sum = 0.0;
		for(Double iterator : locMethod){
			Double ln = Math.log(iterator);
			sum = sum + ln;
			lnXi.add(ln);
		}
	}
	/**
	 * @author ian
	 * Metodo que calcula el promedio de los logaritmos 
	 */
	private void calcularPromedio() {
		Double sum = 0.0;
		for(Double iterator : lnXi){
			sum = sum + iterator;
		}
		avg = sum / lnXi.size();
	}
	/**
	 * @author ian
	 * Metodo que calcula la varianza 
	 */
	private void calcularVarianza() {
		Double sum = 0.0;
		for(Double iterator : lnXi){
			sum = sum + Math.pow((iterator - avg), 2);
		}
		var = sum / (lnXi.size() - 1);
	}
	/**
	 * @author ian
	 * Metodo que calcula la desviacion estandar 
	 */
	private void calcularDesviacio() {
		sigma = Math.sqrt(var);
	}
	/**
	 * @author ian
	 * Metodo que calcula los rangos logaritmicos 
	 */
	private void calcularRangosLogaritmicos() {
		Double lnVS = avg - (2 * sigma);
		Double lnS = avg - sigma;
		Double lnM = avg;
		Double lnL = avg + sigma;
		Double lnVL = avg + (2 * sigma);
		ranges.add(Math.pow(Math.E, lnVS));
		ranges.add(Math.pow(Math.E, lnS));
		ranges.add(Math.pow(Math.E, lnM));
		ranges.add(Math.pow(Math.E, lnL));
		ranges.add(Math.pow(Math.E, lnVL));
	}

	public List<Double> getLocMethod() {
		return locMethod;
	}

	public void setLocMethod(List<Double> locMethod) {
		this.locMethod = locMethod;
	}

	public List<Double> getLnXi() {
		return lnXi;
	}

	public void setLnXi(List<Double> lnXi) {
		this.lnXi = lnXi;
	}

	public Double getAvg() {
		return avg;
	}

	public void setAvg(Double avg) {
		this.avg = avg;
	}

	public Double getVar() {
		return var;
	}

	public void setVar(Double var) {
		this.var = var;
	}

	public Double getSigma() {
		return sigma;
	}

	public void setSigma(Double sigma) {
		this.sigma = sigma;
	}	
}