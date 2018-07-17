package by.epam.task1.util;

public enum PriceCategory {

	ZERO(0.0), LOW(1.0), NORMAL(2.0), HIGH(3.0);

	double price;

	PriceCategory(double price) {
		this.price = price;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

}
