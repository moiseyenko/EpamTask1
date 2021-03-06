package by.epam.task1.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.task1.entity.GuestTariff;
import by.epam.task1.entity.SocialTariff;
import by.epam.task1.entity.Tariff;
import by.epam.task1.exception.QuantityException;
import by.epam.task1.util.enums.ParameterType;
import by.epam.task1.util.enums.PriceType;
import by.epam.task1.util.enums.SocialGroupType;
import by.epam.task1.util.enums.TariffType;
import by.epam.task1.entity.InternetTariff;

public class TariffHelper {

	private final static Logger LOG = LogManager.getLogger(TariffHelper.class);

	// count total subscribers amount
	public static int countTotalSubscribers(List<Tariff> tariffList) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("Start countTotalSubscribers");
		}
		int result = 0;
		for (Tariff tariff : tariffList) {
			result += tariff.getSubscribersQuantity();
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("End countTotalSubscribers with result: " + result);
		}
		return result;
	}

	// sort tariffs by payroll
	public static void sortByPayroll(List<Tariff> tariffList) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("Start sortByPayroll with tariffList hashcode: " + tariffList.hashCode());
		}
		tariffList.sort(Comparator.comparingDouble(Tariff::getPayroll));
		if (LOG.isDebugEnabled()) {
			LOG.debug("End sortByPayroll with tariffList hashcode: " + tariffList.hashCode());
		}
	}

	// return tariffs' list after reading the file
	public static List<Tariff> readTariffsFile(String path) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("Start readTariffsFile with path: " + path);
		}
		List<Tariff> tariffs = new ArrayList<>();

		try (BufferedReader br = new BufferedReader(new FileReader(new File(path)))) {
			String line;
			while ((line = br.readLine()) != null) {
				StringTokenizer t = new StringTokenizer(line, ":");
				// create appropriate Tariff object according to the beginning of each line
				Tariff tariff = initializeTariff(t);
				if (LOG.isDebugEnabled()) {
					LOG.debug("initializeTariff Return: " + tariff.getName());
				}
				// add Tariff to list
				tariffs.add(tariff);
			}
		} catch (FileNotFoundException e) {
			if (LOG.isErrorEnabled()) {
				LOG.error("Tariffs' file not found", e);
			}
		} catch (QuantityException | IOException e1) {
			if (LOG.isErrorEnabled()) {
				LOG.error("Tariff's error", e1);
			}
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("End readTariffsFile with tariffs' list size: " + tariffs.size());
		}
		return tariffs;
	}

	// initialize tariff
	private static Tariff initializeTariff(StringTokenizer t) throws QuantityException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("Start initializeTariff with StringTokenizer.count: " + t.countTokens());
		}
		TariffType tariffType;
		try {
			tariffType = TariffType.valueOf(t.nextToken());
		} catch (Exception e) {
			if (LOG.isErrorEnabled()) {
				LOG.error("Invalid tariff's type", e);
			}
			throw new IllegalArgumentException();
		}
		String name = t.nextToken();
		double payroll = Double.parseDouble(t.nextToken());
		payrollRestriction(payroll);
		PriceType sameNetPrice = PriceType.valueOf(t.nextToken());
		PriceType otherNetPrice = PriceType.valueOf(t.nextToken());
		PriceType landlinePrice = PriceType.valueOf(t.nextToken());
		PriceType internetPrice = PriceType.valueOf(t.nextToken());
		int subscribersQuantity = Integer.parseInt(t.nextToken());
		subscribersQuantityRestriction(subscribersQuantity);

		switch (tariffType) {
		case SOCIAL_TARIFF:
			int favourNumber = Integer.parseInt(t.nextToken());
			SocialGroupType group = SocialGroupType.valueOf(t.nextToken().toUpperCase());
			favourNumberRestriction(favourNumber, group);
			payroll = socialTariffBonus(group, payroll);
			if (LOG.isDebugEnabled()) {
				LOG.debug("Return socialTariffBonus payroll:" + payroll);
			}
			SocialTariff socialTariff = new SocialTariff();
			socialTariff.setName(name);
			socialTariff.setPayroll(payroll);
			socialTariff.setSameNetPrice(sameNetPrice);
			socialTariff.setOtherNetPrice(otherNetPrice);
			socialTariff.setLandlinePrice(landlinePrice);
			socialTariff.setInternetPrice(internetPrice);
			socialTariff.setSubscribersQuantity(subscribersQuantity);
			socialTariff.setGroup(group);
			socialTariff.setFavourNumber(favourNumber);
			return socialTariff;

		case GUEST_TARIFF:
			int days = Integer.parseInt(t.nextToken());
			daysRestriction(days);
			payroll = getPayrolRateForGuestTariff(days, payroll);
			GuestTariff guestTariff = new GuestTariff();
			guestTariff.setName(name);
			guestTariff.setPayroll(payroll);
			guestTariff.setSameNetPrice(sameNetPrice);
			guestTariff.setOtherNetPrice(otherNetPrice);
			guestTariff.setLandlinePrice(landlinePrice);
			guestTariff.setInternetPrice(internetPrice);
			guestTariff.setSubscribersQuantity(subscribersQuantity);
			guestTariff.setDays(days);
			return guestTariff;

		case INTERNET_TARIFF:
			boolean unlim = Boolean.parseBoolean(t.nextToken());
			internetPrice = internetTariffBonus(internetPrice, unlim);
			if (LOG.isDebugEnabled()) {
				LOG.debug("Return internetTariff:" + internetPrice);
			}
			InternetTariff internetTariff = new InternetTariff();
			internetTariff.setName(name);
			internetTariff.setPayroll(payroll);
			internetTariff.setSameNetPrice(sameNetPrice);
			internetTariff.setOtherNetPrice(otherNetPrice);
			internetTariff.setLandlinePrice(landlinePrice);
			internetTariff.setInternetPrice(internetPrice);
			internetTariff.setSubscribersQuantity(subscribersQuantity);
			internetTariff.setUnlim(unlim);
			return internetTariff;

		default:
			throw new IllegalArgumentException("Invalid tariff's type");
		}

	}

	// set payroll restrictions
	public static void payrollRestriction(double payroll) throws QuantityException {
		if (payroll <= 0) {
			throw new QuantityException("Payroll amount must be greater than zero");
		}
	}

	// set restrictions to subscribers' quantity
	public static void subscribersQuantityRestriction(int subscribersQuantity) throws QuantityException {
		if (subscribersQuantity < 0) {
			throw new QuantityException("Subscribers quantity must be equal to or greater than 0");
		}
	}

	// set favourNumber restrictions
	public static void favourNumberRestriction(int favourNumber, SocialGroupType group) throws QuantityException {
		if (favourNumber < 0) {
			throw new QuantityException("Quantity of favourNumber must be equal greater than zero");
		} else if (group.equals(SocialGroupType.YOUTH) && favourNumber < 5) {
			throw new QuantityException("Quantity of favourNumber for youth must be equal to or greater than 5");
		} else if (group.equals(SocialGroupType.PENSIONER) && favourNumber < 3) {
			throw new QuantityException("Quantity of favourNumber for pensioners must be equal to or greater than 3");
		}
	}

	// set days' restrictions
	public static void daysRestriction(int days) throws QuantityException {
		if (days <= 0) {
			throw new QuantityException("Days amount must be greater than zero");
		}
	}

	// set internetTariff's bonus depending on unlim parameter
	public static PriceType internetTariffBonus(PriceType internetPrice, boolean unlim) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("Start internetTariffBonus with unlim:" + unlim);
		}
		if (unlim) {
			return PriceType.ZERO;
		} else {
			return internetPrice;
		}
	}

	// set socialTariff's bonus depending on subscriber's social group
	public static double socialTariffBonus(SocialGroupType group, double payroll) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("Start socialTariffBonus with group:" + group + " and payroll: " + payroll);
		}
		if (group.equals(SocialGroupType.YOUTH)) {
			return payroll * TariffConstants.YOUTH_BONUS_RATE;
		} else if (group.equals(SocialGroupType.PENSIONER)) {
			return payroll * TariffConstants.PENSIONER_BONUS_RATE;
		} else {
			return payroll;
		}
	}

	// set payroll rate for guestTariff depending on days' number
	public static double getPayrolRateForGuestTariff(int days, double payroll) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("Start getPayrolRateForGuestTariff with days: " + days + " and payroll: " + payroll);
		}
		int dayOfMonth = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH);
		payroll *= (double) days / dayOfMonth;
		if (LOG.isDebugEnabled()) {
			LOG.debug("End getPayrolRateForGuestTariff with payroll: " + payroll);
		}
		return payroll;
	}

	// return parameters' map after reading the file
	public static Map<ParameterType, Range> readParametersRange(String path) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("Start readParametersRange with path: " + path);
		}
		Map<ParameterType, Range> inputParameters = new HashMap<>();

		try (BufferedReader br = new BufferedReader(new FileReader(new File(path)))) {
			String line;
			while ((line = br.readLine()) != null) {
				StringTokenizer t = new StringTokenizer(line, ":");

				ParameterType key;
				String from;
				String to;

				if (t.countTokens() < 2) {
					continue;
				} else if (t.countTokens() < 3
						&& (line.startsWith("NAME") | line.startsWith("GROUP") | line.startsWith("UNLIM"))) {
					try {
						key = ParameterType.valueOf(t.nextToken());
					} catch (Exception e) {
						if (LOG.isErrorEnabled()) {
							LOG.error("Invalid parameter's type", e);
						}
						throw new IllegalArgumentException();
					}
					from = to = t.nextToken();
				} else if (t.countTokens() < 3) {
					continue;
				} else {
					try {
						key = ParameterType.valueOf(t.nextToken());
					} catch (Exception e) {
						if (LOG.isErrorEnabled()) {
							LOG.error("Invalid parameter's type", e);
						}
						throw new IllegalArgumentException();
					}
					from = t.nextToken();
					to = t.nextToken();
				}
				inputParameters.put(key, new Range(from, to));
			}
		} catch (FileNotFoundException e) {
			if (LOG.isErrorEnabled()) {
				LOG.error("Parameters' file not found", e);
			}
		} catch (IOException e1) {
			if (LOG.isErrorEnabled()) {
				LOG.error("Input exception", e1);
			}
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("End readParametersRange with Map size: " + inputParameters.size());
		}
		return inputParameters;
	}

	// form list with suit tariffs
	public static List<String> searchTariff(List<Tariff> tariffs, Map<ParameterType, Range> inputParameters) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("Start searchTariff with tariffs list size: " + tariffs.size() + " and parameters map size: "
					+ inputParameters.size());
		}
		List<String> list = new ArrayList<>();

		for (Tariff tariff : tariffs) {
			boolean isSuit = checkParameters(tariff, inputParameters);
			if (LOG.isDebugEnabled()) {
				LOG.debug("checkParameters Return :" + isSuit);
			}
			if (isSuit) {
				list.add(tariff.getName());
			}
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("End searchTariff with result list size: " + list.size());
		}
		return list;
	}

	// check what parameters have been written in the file
	private static boolean checkParameters(Tariff tariff, Map<ParameterType, Range> inputParameters) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("Start checkParameters with tariff: " + tariff.getName() + " and parameters map size: "
					+ inputParameters.size());
		}
		for (Map.Entry<ParameterType, Range> entry : inputParameters.entrySet()) {
			String from = entry.getValue().getFrom();
			String to = entry.getValue().getTo();
			switch (entry.getKey()) {
			case NAME:
				if (tariff.getName().toLowerCase().contains(from.toLowerCase())) {
					continue;
				}
				return false;

			case PAYROLL:
				if (tariff.getPayroll() >= Double.parseDouble(from) && tariff.getPayroll() <= Double.parseDouble(to)) {
					continue;
				}
				return false;

			case SAME_NET_PRICE:
				PriceType sameNetPriceType = tariff.getSameNetPrice();
				if (sameNetPriceType.getPrice() >= Double.parseDouble(from)
						&& sameNetPriceType.getPrice() <= Double.parseDouble(to)) {
					continue;
				}
				return false;

			case OTHER_NET_PRICE:
				PriceType otherNetPriceType = tariff.getOtherNetPrice();
				if (otherNetPriceType.getPrice() >= Double.parseDouble(from)
						&& otherNetPriceType.getPrice() <= Double.parseDouble(to)) {
					continue;
				}
				return false;

			case LANDLINE_PRICE:
				PriceType landlinePriceType = tariff.getLandlinePrice();
				if (landlinePriceType.getPrice() >= Double.parseDouble(from)
						&& landlinePriceType.getPrice() <= Double.parseDouble(to)) {
					continue;
				}
				return false;

			case INTERNET_PRICE:
				PriceType internetPriceType = tariff.getInternetPrice();
				if (internetPriceType.getPrice() >= Double.parseDouble(from)
						&& internetPriceType.getPrice() <= Double.parseDouble(to)) {
					continue;
				}
				return false;

			case FAVOUR_NUMBER:
				if (tariff instanceof SocialTariff) {
					SocialTariff socialTariff = (SocialTariff) tariff;
					if (socialTariff.getFavourNumber() >= Integer.parseInt(from)
							&& socialTariff.getFavourNumber() <= Integer.parseInt(to)) {
						continue;
					}
				}
				return false;

			case GROUP:
				if (tariff instanceof SocialTariff) {
					SocialTariff socialTariff = (SocialTariff) tariff;
					SocialGroupType currentGroup = socialTariff.getGroup();
					if (currentGroup.name().toLowerCase().contains(from.toLowerCase())) {
						continue;
					}
				}
				return false;

			case UNLIM:
				if (tariff instanceof InternetTariff
						&& ((InternetTariff) tariff).isUnlim() == Boolean.parseBoolean(from)) {
					continue;
				}
				return false;

			case DAYS:
				if (tariff instanceof GuestTariff) {
					GuestTariff guestTariff = (GuestTariff) tariff;
					if (guestTariff.getDays() >= Integer.parseInt(from)
							&& guestTariff.getDays() <= Integer.parseInt(to)) {
						continue;
					}
				}
				return false;

			}
		}
		return true;
	}

}
