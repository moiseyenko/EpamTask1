package by.epam.task1.util;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;

import by.epam.task1.entity.GuestTariff;
import by.epam.task1.entity.SocialTariff;
import by.epam.task1.entity.Tariff;
import by.epam.task1.entity.InternetTariff;

public class TariffHelper {

	// count total subscribers amount
	public static int countTotalSubscribers(List<Tariff> tariffList) {
		int result = 0;
		for (Tariff tariff : tariffList) {
			result += tariff.getSubscribersQuantity();
		}
		return result;
	}

	// sort tariffs by payroll
	public static void sortByPayroll(List<Tariff> tariffList) {
		tariffList.sort(Comparator.comparingDouble(Tariff::getPayroll));
	}

	// return tariffs' list after reading the file
	public static List<Tariff> readTariffsFile(String path) {

		List<Tariff> tariffs = new ArrayList<>();

		try (Scanner sc = new Scanner(Paths.get(path))) {
			String line;
			while (sc.hasNextLine()) {
				line = sc.nextLine();
				StringTokenizer t = new StringTokenizer(line, ":");
				// create appropriate Tariff object according to the beginning of each line
				tariffs.add(initializeTariff(t));

			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return tariffs;
	}

	// initialize tariff
	private static Tariff initializeTariff(StringTokenizer t) {

		TariffType tariffType = TariffType.valueOf(t.nextToken());
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
			return new SocialTariff(name, payroll, sameNetPrice, otherNetPrice, landlinePrice, internetPrice,
					subscribersQuantity, favourNumber, group);

		case GUEST_TARIFF:
			int days = Integer.parseInt(t.nextToken());
			payroll = getPayrolRateForGuestTariff(days, payroll);
			return new GuestTariff(name, payroll, sameNetPrice, otherNetPrice, landlinePrice, internetPrice,
					subscribersQuantity, days);

		case INTERNET_TARIFF:
			boolean unlim = Boolean.parseBoolean(t.nextToken());
			internetPrice = internetTariffBonus(internetPrice, unlim);
			return new InternetTariff(name, payroll, sameNetPrice, otherNetPrice, landlinePrice, internetPrice,
					subscribersQuantity, unlim);

		default:
			throw new IllegalArgumentException("Invalid tariff's type");
		}
	}

	// set payroll restrictions
	public static void payrollRestriction(double payroll) {
		if (payroll <= 0) {
			throw new IllegalArgumentException("Payroll amount must be greater than zero");
		}
	}

	// set restrictions to subscribers' quantity
	public static void subscribersQuantityRestriction(int subscribersQuantity) {
		if (subscribersQuantity < 0) {
			throw new IllegalArgumentException("Subscribers quantity must be equal to or greater than 0");
		}
	}

	// set favourNumber restrictions
	public static void favourNumberRestriction(int favourNumber, SocialGroupType group) {
		if (favourNumber < 0) {
			throw new IllegalArgumentException("Quantity of favourNumber must be equal greater than zero");
		} else if (group.equals(SocialGroupType.YOUTH) && favourNumber < 5) {
			throw new IllegalArgumentException("Quantity of favourNumber for youth must be equal to or greater than 5");
		} else if (group.equals(SocialGroupType.PENSIONER) && favourNumber < 3) {
			throw new IllegalArgumentException(
					"Quantity of favourNumber for pensioners must be equal to or greater than 3");
		}
	}

	// set days' restrictions
	public static void daysRestriction(int days) {
		if (days <= 0) {
			throw new IllegalArgumentException("Days amount must be greater than zero");
		}
	}

	// set internetTariff's bonus depending on unlim parameter
	public static PriceType internetTariffBonus(PriceType internetPrice, boolean unlim) {
		if (unlim) {
			return PriceType.ZERO;
		} else {
			return internetPrice;
		}
	}

	// set socialTariff's bonus depending on subscriber's social group
	public static double socialTariffBonus(SocialGroupType group, double payroll) {
		if (group.equals(SocialGroupType.YOUTH)) {
			return payroll *= TariffConstants.YOUTH_BONUS_RATE;
		} else if (group.equals(SocialGroupType.PENSIONER)) {
			return payroll *= TariffConstants.PENSIONER_BONUS_RATE;
		} else {
			return payroll;
		}
	}

	// set payroll rate for guestTariff depending on days' number
	public static double getPayrolRateForGuestTariff(int days, double payroll) {
		int dayOfMonth = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH);
		return payroll *= (double) days / dayOfMonth;
	}

	// return parameters' map after reading the file
	public static Map<ParameterType, Range> readParametersRange(String path) {

		Map<ParameterType, Range> inputParameters = new HashMap<>();

		try (Scanner sc = new Scanner(Paths.get(path))) {
			String line;
			while (sc.hasNextLine()) {
				line = sc.nextLine();
				StringTokenizer t = new StringTokenizer(line, ":");

				ParameterType key;
				String from;
				String to;

				if (t.countTokens() < 2) {
					continue;
				} else if (t.countTokens() < 3
						&& (line.startsWith("NAME") | line.startsWith("GROUP") | line.startsWith("UNLIM"))) {
					key = ParameterType.valueOf(t.nextToken());
					from = to = t.nextToken();
				} else if (t.countTokens() < 3) {
					continue;
				} else {
					key = ParameterType.valueOf(t.nextToken());
					from = t.nextToken();
					to = t.nextToken();
				}

				inputParameters.put(key, new Range(from, to));

			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return inputParameters;
	}

	// form list with suit tariffs
	public static List<String> SearchTariff(List<Tariff> tariffs, Map<ParameterType, Range> inputParameters) {

		List<String> list = new ArrayList<>();

		for (Tariff tariff : tariffs) {
			if (checkParameters(tariff, inputParameters)) {
				list.add(tariff.getName());
			}
		}
		return list;
	}

	// check what parameters have been written in the file
	private static boolean checkParameters(Tariff tariff, Map<ParameterType, Range> inputParameters) {

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
				if (tariff.getSameNetPrice().getPrice() >= Double.parseDouble(from)
						&& tariff.getSameNetPrice().getPrice() <= Double.parseDouble(to)) {
					continue;
				}
				return false;

			case OTHER_NET_PRICE:
				if (tariff.getOtherNetPrice().getPrice() >= Double.parseDouble(from)
						&& tariff.getOtherNetPrice().getPrice() <= Double.parseDouble(to)) {
					continue;
				}
				return false;

			case LANDLINE_PRICE:
				if (tariff.getLandlinePrice().getPrice() >= Double.parseDouble(from)
						&& tariff.getLandlinePrice().getPrice() <= Double.parseDouble(to)) {
					continue;
				}
				return false;

			case INTERNET_PRICE:
				if (tariff.getInternetPrice().getPrice() >= Double.parseDouble(from)
						&& tariff.getInternetPrice().getPrice() <= Double.parseDouble(to)) {
					continue;
				}
				return false;

			case FAVOUR_NUMBER:

				if (tariff instanceof SocialTariff
						&& ((SocialTariff) tariff).getFavourNumber() >= Integer.parseInt(from)
						&& ((SocialTariff) tariff).getFavourNumber() <= Integer.parseInt(to)) {
					continue;
				}
				return false;

			case GROUP:
				if (tariff instanceof SocialTariff
						&& ((SocialTariff) tariff).getGroup().name().toLowerCase().contains(from.toLowerCase())) {
					continue;
				}
				return false;

			case UNLIM:
				if (tariff instanceof InternetTariff
						&& ((InternetTariff) tariff).isUnlim() == Boolean.parseBoolean(from)) {
					continue;
				}
				return false;

			case DAYS:
				if (tariff instanceof GuestTariff && ((GuestTariff) tariff).getDays() >= Integer.parseInt(from)
						&& ((GuestTariff) tariff).getDays() <= Integer.parseInt(to)) {
					continue;
				}
				return false;

			default:
				throw new IllegalArgumentException("Invalid parameter's type");
			}
		}
		return true;
	}

}
