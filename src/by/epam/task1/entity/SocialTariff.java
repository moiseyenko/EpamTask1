package by.epam.task1.entity;

import by.epam.task1.util.PriceCategory;
import by.epam.task1.util.SocialGroup;

public class SocialTariff extends Tariff {

	private int favourNumber;
	private SocialGroup group;

	public SocialTariff(String name, double payroll, PriceCategory sameNetPrice, PriceCategory otherNetPrice,
			PriceCategory landlinePrice, PriceCategory internetPrice, double connectionFee, int subscribersQuantity,
			int favourNumber, SocialGroup group) {

		super(name, payroll, sameNetPrice, otherNetPrice, landlinePrice, internetPrice, connectionFee,
				subscribersQuantity);

		this.group = group;

		favourNumberRestriction(favourNumber);
		this.favourNumber = favourNumber;

		super.setPayroll(discountPayroll(payroll));
	}
	////////////////////////////////////////////////////////////////////

	public SocialGroup getGroup() {
		return group;
	}

	public void setGroup(SocialGroup group) {
		this.group = group;
	}
	////////////////////////////////////////////////////////////////////

	public int getFavourNumber() {
		return favourNumber;
	}

	public void setFavourNumber(int favourNumber) {
		favourNumberRestriction(favourNumber);
		this.favourNumber = favourNumber;
	}

	private void favourNumberRestriction(int favourNumber) {
		if (group.equals(SocialGroup.YOUTH) && favourNumber < 5) {
			throw new IllegalArgumentException("Quantity of favourNumber for youth must be equal to or greater than 5");
		} else if (group.equals(SocialGroup.PENSIONER) && favourNumber < 3) {
			throw new IllegalArgumentException(
					"Quantity of favourNumber for pensioners must be equal to or greater than 3");
		} else if (favourNumber < 0) {
			throw new IllegalArgumentException("Quantity of favourNumber must be equal greater than zero");
		}
	}
	////////////////////////////////////////////////////////////////////

	@Override
	public void setPayroll(double payroll) {
		super.setPayroll(discountPayroll(payroll));
	}

	private double discountPayroll(double payroll) {
		switch (group) {
		case YOUTH:
			return 0.5 * payroll;
		case PENSIONER:
			return 0.3 * payroll;
		case OTHER:
			return payroll;
		default:
			throw new IllegalArgumentException("Invalid social group");
		}
	}
	////////////////////////////////////////////////////////////////////

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + favourNumber;
		result = prime * result + ((group == null) ? 0 : group.hashCode());
		return result;
	}
	////////////////////////////////////////////////////////////////////

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof SocialTariff))
			return false;
		SocialTariff other = (SocialTariff) obj;
		if (favourNumber != other.favourNumber)
			return false;
		if (group != other.group)
			return false;
		return true;
	}
	////////////////////////////////////////////////////////////////////

	@Override
	public String toString() {
		String superString = super.toString();
		String withoutLastBracketString = new String(superString.toCharArray(), 0, superString.length() - 1);
		return withoutLastBracketString + ", favourNumber=" + favourNumber + ", group=" + group + "]";
	}
	////////////////////////////////////////////////////////////////////

}
