package by.epam.task1.entity;

import by.epam.task1.util.PriceType;
import by.epam.task1.util.SocialGroupType;

public class SocialTariff extends Tariff {

	private int favourNumber;
	private SocialGroupType group;

	public SocialTariff(String name, double payroll, PriceType sameNetPrice, PriceType otherNetPrice,
			PriceType landlinePrice, PriceType internetPrice, double connectionFee, int subscribersQuantity,
			int favourNumber, SocialGroupType group) {

		super(name, payroll, sameNetPrice, otherNetPrice, landlinePrice, internetPrice, connectionFee,
				subscribersQuantity);

		this.group = group;

		favourNumberRestriction(favourNumber);
		this.favourNumber = favourNumber;
	}
	////////////////////////////////////////////////////////////////////

	public SocialGroupType getGroup() {
		return group;
	}

	public void setGroup(SocialGroupType group) {
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
		if (group.equals(SocialGroupType.YOUTH) && favourNumber < 5) {
			throw new IllegalArgumentException("Quantity of favourNumber for youth must be equal to or greater than 5");
		} else if (group.equals(SocialGroupType.PENSIONER) && favourNumber < 3) {
			throw new IllegalArgumentException(
					"Quantity of favourNumber for pensioners must be equal to or greater than 3");
		} else if (favourNumber < 0) {
			throw new IllegalArgumentException("Quantity of favourNumber must be equal greater than zero");
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
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		SocialTariff other = (SocialTariff) obj;
		if (favourNumber != other.favourNumber) {
			return false;
		}
		if (group != other.group) {
			return false;
		}
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
