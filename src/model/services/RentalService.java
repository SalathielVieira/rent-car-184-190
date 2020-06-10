package model.services;

import model.entities.CarRental;
import model.entities.Invoice;

public class RentalService {

	private Double pricePerDay;
	private Double pricePerHour;
	
	private BrazilTaxService taxService;

	public RentalService(Double pricePerDay, Double pricePerHour, BrazilTaxService taxService) {
		super();
		this.pricePerDay = pricePerDay;
		this.pricePerHour = pricePerHour;
		this.taxService = taxService;
	}
	
	public void processInvoice(CarRental carRental) {
		long t1 = carRental.getStart().getTime();					// Transformando a data em milisegundos (getTime)
		long t2 = carRental.getFinish().getTime();					// Transformando a data em milisegundos (getTime)
		double hours = (double)(t2 - t1) / 1000 / 60 / 60;			// A vari�vel recebe a diferen�a em horas. O casting garante que o resultado sera em double
		
		double basicPayment;										// Declara��o da vari�vel
		if (hours <= 12.0) {										// Calculando o pre�o por horas
			basicPayment = Math.ceil(hours) * pricePerHour;			// Fun��o ceil para arredondar para cima
		} else {													// Calculando o pre�o por dias
			basicPayment = Math.ceil(hours / 24) * pricePerDay;		// / 24 para calcular por dia
		}
		
		double tax = taxService.tax(basicPayment);					// Para calcular o imposto
		
		carRental.setInvoice(new Invoice(basicPayment, tax));		// Instanciando o novo objeto de Invoice
		
	}
	
}
