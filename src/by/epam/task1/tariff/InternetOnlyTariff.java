package by.epam.task1.tariff;

import by.epam.task1.additions.PriceCategory;

public class InternetOnlyTariff implements Tariff {
	private String name;
	private double payroll;
	
	private PriceCategory sameNetPrice;
	private PriceCategory otherNetPrice;
	private PriceCategory landlinePrice;
	private PriceCategory internetPrice;
	
	private double connectionFee;
	private int internetTrafic ;
	private int favourNumber;
	private int abonentsQuantity;
	
	
	public InternetOnlyTariff (String name, double payroll,
				int internetTrafic, int abonentsQuantity) {
		
		this.name = name;
		
		payrollRestriction(payroll);
		this.payroll = payroll;
		
		internetTraficRestriction(internetTrafic);
		this.internetTrafic = internetTrafic;
		
		abonentsQuantityRestriction(abonentsQuantity);
		this.abonentsQuantity = abonentsQuantity;
		
		setPricing();
	}
////////////////////////////////////////////////////////////////////
	
	@Override
	public String getName() {
		return name;
	}
	@Override
	public void setName(String name) {
		this.name = name;	
	}
////////////////////////////////////////////////////////////////////
	
	@Override
	public double getPayroll() {
		return payroll;
	}

	@Override
	public void setPayroll(double payroll) {
		payrollRestriction(payroll);
		this.payroll = payroll;
	}
	
	private void payrollRestriction(double payroll) {
		if (payroll<=0) {
			throw new IllegalArgumentException("Payroll amount must be greater than zero");
		}
	}
////////////////////////////////////////////////////////////////////
	@Override
	public PriceCategory getSameNetPrice() {
		return sameNetPrice;
	}
	
	@Override
	public PriceCategory getOtherNetPrice() {
		return otherNetPrice;
	}
	
	@Override
	public PriceCategory getLandlinePrice() {
		return landlinePrice;
	}
	
	@Override
	public PriceCategory getInternetPrice() {
		return internetPrice;
	}

	private void setPricing () {
		otherNetPrice = PriceCategory.HIGH;
		sameNetPrice = PriceCategory.HIGH;
		landlinePrice = PriceCategory.HIGH;
		internetPrice = PriceCategory.LOW;
			
	}

////////////////////////////////////////////////////////////////////
	
	@Override
	public double getConnectionFee() {
		return connectionFee;
	}

	@Override
	public void setConnectionFee(double connectionFee) {
		throw new IllegalStateException("This tariff has Connection Fee equal to 0. You can't change this!");
	}
////////////////////////////////////////////////////////////////////
	
	@Override
	public int getInternetTrafic() {
		return internetTrafic;
	}

	@Override
	public void setInternetTrafic(int internetTrafic) {
		internetTraficRestriction(internetTrafic);
		this.internetTrafic = internetTrafic;
	}
	
	private void internetTraficRestriction(int internetTrafic) {
		if (internetTrafic<5000) {
			throw new IllegalArgumentException("Internet trafic's amount must be equal to or greater than 5000");
		}
	}
////////////////////////////////////////////////////////////////////
	
	@Override
	public int getFavourNumber() {
		return favourNumber;
	}

	@Override
	public void setFavourNumber(int favourNumber) {
		throw new IllegalStateException("This tariff has Payroll equal to 0. You can't change this!");
		
	}
////////////////////////////////////////////////////////////////////
	
	@Override
	public int getAbonentsQuantity() {
		return abonentsQuantity;
	}

	@Override
	public void setAbonentsQuantity(int abonentsQuantity) {
		abonentsQuantityRestriction(abonentsQuantity);
		this.abonentsQuantity = abonentsQuantity;
		
	}
	
	private void abonentsQuantityRestriction (int abonentsQuantity) {
		if (abonentsQuantity < 0) {
			throw new IllegalArgumentException("abonentsQuantity must be equal to or greater than 0");
		}
	}
////////////////////////////////////////////////////////////////////
	
	@Override
	public String toString() {
		
		return "name: "+ name + "; "
				+"payroll: "+ payroll + "; "
				+"sameNetPrice: "+ sameNetPrice + "; "
				+"otherNetPrice: "+ otherNetPrice + "; "
				+"landlinePrice: "+ landlinePrice + "; "
				+"internetPrice: "+ internetPrice + "; "
				+"connectionFee: "+ connectionFee + "; "
				+"internetTrafic: "+ internetTrafic + "; "
				+"favourNumber: "+ favourNumber + "; "
				+"abonentsQuantity: "+ abonentsQuantity;
	}
////////////////////////////////////////////////////////////////////
}
