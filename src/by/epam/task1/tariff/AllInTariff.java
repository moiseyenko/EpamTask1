package by.epam.task1.tariff;

import by.epam.task1.additions.PriceCategory;

public class AllInTariff implements Tariff{
	
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
	
	
	public AllInTariff (String name, double payroll, double connectionFee,
				int internetTrafic, int abonentsQuantity) {
		
		this.name = name;
		
		payrollRestriction(payroll);
		this.payroll = payroll;
		
		ConnectionFeeRestriction(connectionFee);
		this.connectionFee = connectionFee;
		
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
		if (payroll<200) {
			throw new IllegalArgumentException("Payroll amount must be equal to or greater than 200");
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
		otherNetPrice = PriceCategory.ZERO;
		sameNetPrice = PriceCategory.ZERO;
		landlinePrice = PriceCategory.ZERO;
		internetPrice = PriceCategory.LOW;
			
	}

////////////////////////////////////////////////////////////////////
	
	@Override
	public double getConnectionFee() {
		return connectionFee;
	}

	@Override
	public void setConnectionFee(double connectionFee) {
		ConnectionFeeRestriction(connectionFee);
		this.connectionFee = connectionFee;
	}
	
	private void ConnectionFeeRestriction (double connectionFee) {
		if (connectionFee <=100) {
			throw new IllegalArgumentException("ConnectionFee must be equal to or greater than 100");
		}
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
		if (internetTrafic<1000) {
			throw new IllegalArgumentException("Internet trafic amount must be equal to or greater than 1000");
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
