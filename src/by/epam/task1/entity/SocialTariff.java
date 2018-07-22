package by.epam.task1.entity;

import by.epam.task1.util.enums.SocialGroupType;

public class SocialTariff extends Tariff {

	private int favourNumber;
	private SocialGroupType group;

	public SocialTariff() {
	}

	public SocialGroupType getGroup() {
		return group;
	}

	public void setGroup(SocialGroupType group) {
		this.group = group;
	}

	public int getFavourNumber() {
		return favourNumber;
	}

	public void setFavourNumber(int favourNumber) {
		this.favourNumber = favourNumber;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + favourNumber;
		result = prime * result + ((group == null) ? 0 : group.hashCode());
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
		SocialTariff other = (SocialTariff) obj;
		if (favourNumber != other.favourNumber) {
			return false;
		}
		if (group != other.group) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		String superString = super.toString();
		String withoutLastBracketString = new String(superString.toCharArray(), 0, superString.length() - 1);
		return withoutLastBracketString + ", favourNumber=" + favourNumber + ", group=" + group + "]";
	}

}
