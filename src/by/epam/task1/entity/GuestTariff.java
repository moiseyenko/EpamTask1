package by.epam.task1.entity;

public class GuestTariff extends Tariff {

	private int days;

	public GuestTariff() {
	}

	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + days;
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
		GuestTariff other = (GuestTariff) obj;
		if (days != other.days) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return super.toString() + ", days=" + days;
	}
}
