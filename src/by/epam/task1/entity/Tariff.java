package by.epam.task1.entity;

import by.epam.task1.util.PriceType;

public class Tariff {

	private String name;
	private double payroll;
	private double connectionFee;
	private int subscribersQuantity;

	private PriceType sameNetPrice;
	private PriceType otherNetPrice;
	private PriceType landlinePrice;
	private PriceType internetPrice;

	public Tariff() {

	}

	public Tariff(String name, double payroll, PriceType sameNetPrice, PriceType otherNetPrice, PriceType landlinePrice,
			PriceType internetPrice, double connectionFee, int subscribersQuantity) {

		this.name = name;

		payrollRestriction(payroll);
		this.payroll = payroll;

		this.sameNetPrice = sameNetPrice;
		this.otherNetPrice = otherNetPrice;
		this.landlinePrice = landlinePrice;
		this.internetPrice = internetPrice;

		connectionFeeRestriction(connectionFee);
		this.connectionFee = connectionFee;

		subscribersQuantityRestriction(subscribersQuantity);
		this.subscribersQuantity = subscribersQuantity;

	}
	////////////////////////////////////////////////////////////////////

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	////////////////////////////////////////////////////////////////////

	public double getPayroll() {
		return payroll;
	}

	public void setPayroll(double payroll) {
		payrollRestriction(payroll);
		this.payroll = payroll;
	}

	private void payrollRestriction(double payroll) {
		if (payroll <= 0) {
			throw new IllegalArgumentException("Payroll amount must be greater than zero");
		}
	}
	////////////////////////////////////////////////////////////////////

	public PriceType getSameNetPrice() {
		return sameNetPrice;
	}

	public void setSameNetPrice(PriceType sameNetPrice) {
		this.sameNetPrice = sameNetPrice;
	}
	////////////////////////////////////////////////////////////////////

	public PriceType getOtherNetPrice() {
		return otherNetPrice;
	}

	public void setOtherNetPrice(PriceType otherNetPrice) {
		this.otherNetPrice = otherNetPrice;
	}
	////////////////////////////////////////////////////////////////////

	public PriceType getLandlinePrice() {
		return landlinePrice;
	}

	public void setLandlinePrice(PriceType landlinePrice) {
		this.landlinePrice = landlinePrice;
	}
	////////////////////////////////////////////////////////////////////

	public PriceType getInternetPrice() {
		return internetPrice;
	}

	public void setInternetPrice(PriceType internetPrice) {
		this.internetPrice = internetPrice;
	}
	////////////////////////////////////////////////////////////////////

	public double getConnectionFee() {
		return connectionFee;
	}

	public void setConnectionFee(double connectionFee) {
		connectionFeeRestriction(connectionFee);
		this.connectionFee = connectionFee;
	}

	private void connectionFeeRestriction(double connectionFee) {
		if (connectionFee <= 0) {
			throw new IllegalArgumentException("Connection fee must be greater than zero");
		}
	}
	////////////////////////////////////////////////////////////////////

	public int getSubscribersQuantity() {
		return subscribersQuantity;
	}

	public void setSubscribersQuantity(int subscribersQuantity) {
		subscribersQuantityRestriction(subscribersQuantity);
		this.subscribersQuantity = subscribersQuantity;
	}

	private void subscribersQuantityRestriction(int subscribersQuantity) {
		if (subscribersQuantity < 0) {
			throw new IllegalArgumentException("Subscribers quantity must be equal to or greater than 0");
		}
	}
	////////////////////////////////////////////////////////////////////

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(connectionFee);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((internetPrice == null) ? 0 : internetPrice.hashCode());
		result = prime * result + ((landlinePrice == null) ? 0 : landlinePrice.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((otherNetPrice == null) ? 0 : otherNetPrice.hashCode());
		temp = Double.doubleToLongBits(payroll);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((sameNetPrice == null) ? 0 : sameNetPrice.hashCode());
		result = prime * result + subscribersQuantity;
		return result;
	}
	////////////////////////////////////////////////////////////////////

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
		if (Double.doubleToLongBits(connectionFee) != Double.doubleToLongBits(other.connectionFee)) {
			return false;
		}
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
	////////////////////////////////////////////////////////////////////

	@Override
	public String toString() {
		return getClass().getSimpleName() + " [name=" + name + ", payroll=" + payroll + ", sameNetPrice=" + sameNetPrice
				+ ", otherNetPrice=" + otherNetPrice + ", landlinePrice=" + landlinePrice + ", internetPrice="
				+ internetPrice + ", connectionFee=" + connectionFee + ", subscribersQuantity=" + subscribersQuantity
				+ "]";
	}
	////////////////////////////////////////////////////////////////////

}
