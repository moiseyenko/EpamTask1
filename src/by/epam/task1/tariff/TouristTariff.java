package by.epam.task1.tariff;

import by.epam.task1.additions.PriceCategory;

public class TouristTariff implements Tariff{
	
	private String name;
	private double payroll = 0.0;
	
	private PriceCategory sameNetPrice;
	private PriceCategory otherNetPrice;
	private PriceCategory landlinePrice;
	private PriceCategory internetPrice;
	
	private double connectionFee;
	private int internetTrafic;
	private int favourNumber;
	private int abonentsQuantity;
	
	public TouristTariff(String name, double connectionFee, int internetTrafic,
			int favourNumber, int abonentsQuantity) {
		
		this.name = name;
		
		ConnectionFeeRestriction(connectionFee);
		this.connectionFee = connectionFee;
		
		
		internetTraficRestriction(internetTrafic);
		this.internetTrafic = internetTrafic;
		
		favourNumberRestriction (favourNumber);
		this.favourNumber = favourNumber;
		
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
		throw new IllegalStateException("This tariff has Payroll equal to  0. You can't change this!");
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
		otherNetPrice = PriceCategory.NORMAL;
		sameNetPrice = PriceCategory.NORMAL;
		landlinePrice = PriceCategory.NORMAL;
		internetPrice = PriceCategory.NORMAL;
			
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
		if (connectionFee <=0) {
			throw new IllegalArgumentException("ConnectionFee must be greater than zero");
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
		favourNumberRestriction(favourNumber);
		this.favourNumber = favourNumber;
		
	}
	
	private void favourNumberRestriction (int favourNumber) {
		if (favourNumber<1) {
			throw new IllegalArgumentException("Quantity of favourNumber must be equal to or greater than 1");
		}
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
