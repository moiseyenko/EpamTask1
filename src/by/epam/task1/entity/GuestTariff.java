package by.epam.task1.entity;

import by.epam.task1.util.PriceCategory;

public class GuestTariff extends Tariff {

	private int days;

	public GuestTariff(String name, double payroll, PriceCategory sameNetPrice, PriceCategory otherNetPrice,
			PriceCategory landlinePrice, PriceCategory internetPrice, double connectionFee, int subscribersQuantity,
			int days) {

		super(name, payroll, sameNetPrice, otherNetPrice, landlinePrice, internetPrice, connectionFee,
				subscribersQuantity);

		daysRestriction(days);
		this.days = days;
	}
	////////////////////////////////////////////////////////////////////

	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
	}

	private void daysRestriction(int days) {
		if (days <= 0) {
			throw new IllegalArgumentException("Days amount must be greater than zero");
		}
	}
	////////////////////////////////////////////////////////////////////

	@Override
	public String toString() {
		String superString = super.toString();
		String withoutLastBracketString = new String(superString.toCharArray(), 0, superString.length() - 1);
		return withoutLastBracketString + ", days=" + days + "]";
	}
	////////////////////////////////////////////////////////////////////

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + days;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof GuestTariff))
			return false;
		GuestTariff other = (GuestTariff) obj;
		if (days != other.days)
			return false;
		return true;
	}

}
