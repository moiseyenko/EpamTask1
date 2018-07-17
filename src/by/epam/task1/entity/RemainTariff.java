package by.epam.task1.entity;

import by.epam.task1.util.PriceType;

public class RemainTariff extends Tariff {

	private double remainingRate;

	public RemainTariff(String name, double payroll, PriceType sameNetPrice, PriceType otherNetPrice,
			PriceType landlinePrice, PriceType internetPrice, double connectionFee, int subscribersQuantity,
			double remainingRate) {

		super(name, payroll, sameNetPrice, otherNetPrice, landlinePrice, internetPrice, connectionFee,
				subscribersQuantity);

		remainingRateRestriction(remainingRate);
		this.remainingRate = remainingRate;
	}
	////////////////////////////////////////////////////////////////////

	public double getRemainingRate() {
		return remainingRate;
	}

	public void setRemainingRate(double remainingRate) {
		this.remainingRate = remainingRate;
	}

	private void remainingRateRestriction(double remainingRate) {
		if (remainingRate <= 0) {
			throw new IllegalArgumentException("Remaining rate amount must be greater than zero");
		}
	}
	////////////////////////////////////////////////////////////////////

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		long temp;
		temp = Double.doubleToLongBits(remainingRate);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		RemainTariff other = (RemainTariff) obj;
		if (Double.doubleToLongBits(remainingRate) != Double.doubleToLongBits(other.remainingRate)) {
			return false;
		}
		return true;
	}
	////////////////////////////////////////////////////////////////////

	@Override
	public String toString() {
		String superString = super.toString();
		String withoutLastBracketString = new String(superString.toCharArray(), 0, superString.length() - 1);
		return withoutLastBracketString + ", remainingRate=" + remainingRate + "]";
	}
	////////////////////////////////////////////////////////////////////

}
