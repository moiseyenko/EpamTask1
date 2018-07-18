package by.epam.task1.entity;

import by.epam.task1.util.PriceType;

public class Tariff {

	private String name;
	private double payroll;
	private int subscribersQuantity;

	// one minute's price in the same network
	private PriceType sameNetPrice;
	// one minute's price to other networks
	private PriceType otherNetPrice;
	// one minute's price to landline number
	private PriceType landlinePrice;
	// one megabyte's price
	private PriceType internetPrice;

	public Tariff() {

	}

	public Tariff(String name, double payroll, PriceType sameNetPrice, PriceType otherNetPrice, PriceType landlinePrice,
			PriceType internetPrice, int subscribersQuantity) {

		this.name = name;
		this.payroll = payroll;

		this.sameNetPrice = sameNetPrice;
		this.otherNetPrice = otherNetPrice;
		this.landlinePrice = landlinePrice;
		this.internetPrice = internetPrice;

		this.subscribersQuantity = subscribersQuantity;

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPayroll() {
		return payroll;
	}

	public void setPayroll(double payroll) {
		this.payroll = payroll;
	}

	public PriceType getSameNetPrice() {
		return sameNetPrice;
	}

	public void setSameNetPrice(PriceType sameNetPrice) {
		this.sameNetPrice = sameNetPrice;
	}

	public PriceType getOtherNetPrice() {
		return otherNetPrice;
	}

	public void setOtherNetPrice(PriceType otherNetPrice) {
		this.otherNetPrice = otherNetPrice;
	}

	public PriceType getLandlinePrice() {
		return landlinePrice;
	}

	public void setLandlinePrice(PriceType landlinePrice) {
		this.landlinePrice = landlinePrice;
	}

	public PriceType getInternetPrice() {
		return internetPrice;
	}

	public void setInternetPrice(PriceType internetPrice) {
		this.internetPrice = internetPrice;
	}

	public int getSubscribersQuantity() {
		return subscribersQuantity;
	}

	public void setSubscribersQuantity(int subscribersQuantity) {

		this.subscribersQuantity = subscribersQuantity;
	}

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(payroll);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((internetPrice == null) ? 0 : internetPrice.hashCode());
		result = prime * result + ((landlinePrice == null) ? 0 : landlinePrice.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((otherNetPrice == null) ? 0 : otherNetPrice.hashCode());
		result = prime * result + ((sameNetPrice == null) ? 0 : sameNetPrice.hashCode());
		result = prime * result + subscribersQuantity;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Tariff other = (Tariff) obj;
		if (internetPrice != other.internetPrice) {
			return false;
		}
		if (landlinePrice != other.landlinePrice) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (otherNetPrice != other.otherNetPrice) {
			return false;
		}
		if (Double.doubleToLongBits(payroll) != Double.doubleToLongBits(other.payroll)) {
			return false;
		}
		if (sameNetPrice != other.sameNetPrice) {
			return false;
		}
		if (subscribersQuantity != other.subscribersQuantity) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {

		return getClass().getSimpleName() + " [name=" + name + ", payroll="
				+ String.format("%.1f", payroll) + ", sameNetPrice=" + sameNetPrice + ", otherNetPrice="
				+ otherNetPrice + ", landlinePrice=" + landlinePrice + ", internetPrice=" + internetPrice
				+ ", subscribersQuantity=" + subscribersQuantity + "]";

	}

}
