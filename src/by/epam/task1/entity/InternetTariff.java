package by.epam.task1.entity;

import by.epam.task1.util.PriceType;

public class InternetTariff extends Tariff {

	private boolean unlim;

	public InternetTariff(String name, double payroll, PriceType sameNetPrice, PriceType otherNetPrice,
			PriceType landlinePrice, PriceType internetPrice, int subscribersQuantity, boolean unlim) {

		super(name, payroll, sameNetPrice, otherNetPrice, landlinePrice, internetPrice, subscribersQuantity);

		this.unlim = unlim;
	}

	public boolean isUnlim() {
		return unlim;
	}

	public void setUnlim(boolean unlim) {
		this.unlim = unlim;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (unlim ? 1231 : 1237);
		return result;
	}

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
		InternetTariff other = (InternetTariff) obj;
		if (unlim != other.unlim) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		String superString = super.toString();
		String withoutLastBracketString = new String(superString.toCharArray(), 0, superString.length() - 1);
		return withoutLastBracketString + ", unlim =" + unlim + "]";
	}
}
