package by.epam.task1.entity;

import by.epam.task1.util.PriceCategory;

public class UnlimInternetTariff extends Tariff {

	private int modemMode;

	public UnlimInternetTariff(String name, double payroll, PriceCategory sameNetPrice, PriceCategory otherNetPrice,
			PriceCategory landlinePrice, PriceCategory internetPrice, double connectionFee, int subscribersQuantity,
			int modemMode) {

		super(name, payroll, sameNetPrice, otherNetPrice, landlinePrice, internetPrice, connectionFee,
				subscribersQuantity);

		modemModeRestriction(modemMode);
		this.modemMode = modemMode;
		
		internetPriceRestriction(internetPrice);
		super.setInternetPrice(internetPrice);
	}
	////////////////////////////////////////////////////////////////////

	public int getModemMode() {
		return modemMode;
	}

	public void setModemMode(int modemMode) {
		this.modemMode = modemMode;
	}

	private void modemModeRestriction(int modemMode) {
		if (modemMode <= 0) {
			throw new IllegalArgumentException("ModemMode amount must be greater than zero");
		}
	}
	////////////////////////////////////////////////////////////////////
	
	@Override
	public void setInternetPrice(PriceCategory internetPrice) {
		internetPriceRestriction(internetPrice);
		super.setInternetPrice(internetPrice);
		
	}
	
	private void internetPriceRestriction(PriceCategory internetPrice) {
		if(!internetPrice.equals(PriceCategory.ZERO)) {
			throw new IllegalArgumentException("InternetPrice amount for UmlimInternet tariffs must be equal to zero");
			
		}
			
	}
	////////////////////////////////////////////////////////////////////

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + modemMode;
		return result;
	}
	////////////////////////////////////////////////////////////////////

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof UnlimInternetTariff))
			return false;
		UnlimInternetTariff other = (UnlimInternetTariff) obj;
		if (modemMode != other.modemMode)
			return false;
		return true;
	}
	////////////////////////////////////////////////////////////////////

	@Override
	public String toString() {
		String superString = super.toString();
		String withoutLastBracketString = new String(superString.toCharArray(), 0, superString.length() - 1);
		return withoutLastBracketString + ", modemMode=" + modemMode + "]";
	}
	////////////////////////////////////////////////////////////////////

}
