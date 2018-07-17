package by.epam.task1.tariff;

import by.epam.task1.additions.PriceCategory;
import by.epam.task1.additions.SocialGroup;

public class SocialTariff implements Tariff{
	
	private String name;
	private double payroll;
	
	private PriceCategory sameNetPrice;
	private PriceCategory otherNetPrice;
	private PriceCategory landlinePrice;
	private PriceCategory internetPrice;
	
	private double connectionFee = 0.0;
	private int internetTrafic = Integer.MAX_VALUE;
	private int favourNumber;
	private int abonentsQuantity;

	private SocialGroup group;
	
	
	public SocialTariff(String name, SocialGroup group, double payroll, int internetTrafic, 
			int favourNumber, int abonentsQuantity) {
		
		this.name = name;
		this.group = group;
		payrollBonus(payroll);
		
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
		payrollBonus(payroll);
	}
	
	private void payrollRestriction(double payroll) {
		if (payroll<=0) {
			throw new IllegalArgumentException("Payroll amount must be greater than zero");
		}
	}
	void payrollBonus(double payroll) {
		payrollRestriction(payroll);
		switch (group) {
		case YOUTH:
			this.payroll = 0.5*payroll;
			break;
		case PENSIONER:
			this.payroll = 0.3*payroll;
			break;
		case OTHER:
			this.payroll = payroll;
			break; 
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
		otherNetPrice = PriceCategory.NORMAL;
		switch (group) {
		case YOUTH:
			sameNetPrice = PriceCategory.LOW;
			landlinePrice = PriceCategory.NORMAL;
			internetPrice = PriceCategory.LOW;
			break;
		case PENSIONER:
			sameNetPrice = PriceCategory.LOW;
			landlinePrice = PriceCategory.LOW;
			internetPrice = PriceCategory.NORMAL;
			break;
		case OTHER:
			sameNetPrice = PriceCategory.NORMAL;
			landlinePrice = PriceCategory.NORMAL;
			internetPrice = PriceCategory.NORMAL;
			break; 
		}
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
		if (group.equals(SocialGroup.YOUTH)&&internetTrafic<1000) {
			throw new IllegalArgumentException("Internet trafic amount for young people must be equal to or greater than 1000");
		}
		if (group.equals(SocialGroup.PENSIONER)&&internetTrafic>0) {
			throw new IllegalArgumentException("Internet trafic amount for rensioner must be equal to zero");
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
		if (favourNumber<3) {
			throw new IllegalArgumentException("Quantity of favourNumber must be equal to or greater than 3");
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
	
	public SocialGroup getGroup() {
		return group;
	}

	public void setGroup(SocialGroup group) {
		this.group = group;
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
				+"abonentsQuantity: "+ abonentsQuantity + "; "
				+"group: "+ group;
	}
////////////////////////////////////////////////////////////////////
	
}
