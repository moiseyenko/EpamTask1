package by.epam.task1.util;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;

import by.epam.task1.entity.GuestTariff;
import by.epam.task1.entity.Range;
import by.epam.task1.entity.RemainTariff;
import by.epam.task1.entity.SocialTariff;
import by.epam.task1.entity.Tariff;
import by.epam.task1.entity.UnlimInternetTariff;

public class TariffHelper {

	public static int countTotalAbonents(List<Tariff> tariffList) {
		int result = 0;
		for (Tariff tariff : tariffList) {
			result += tariff.getSubscribersQuantity();
		}
		return result;
	}
	/////////////////////////////////////////////////////////////////////////////

	public static void sortByPayroll(List<Tariff> tariffList) {
		tariffList.sort(Comparator.comparingDouble(Tariff::getPayroll));
	}
	/////////////////////////////////////////////////////////////////////////////

	// return tariffs' list after reading the file
	public static List<Tariff> readTariffsFile(String path) {

		List<Tariff> tariffs = new ArrayList<>();

		try (Scanner sc = new Scanner(Paths.get(path))) {
			String line;
			while (sc.hasNextLine()) {
				line = sc.nextLine();
				StringTokenizer t = new StringTokenizer(line, ":");
				// create appropriate Tariff object according to the beginning of each line
				tariffs.add(createApproriateTariff(t));

			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return tariffs;
	}
	/////////////////////////////////////////////////////////////////////////////

	private static SocialTariff SocialBonus(StringTokenizer t) {

		String name = t.nextToken();
		double payroll = Double.parseDouble(t.nextToken());
		PriceType sameNetPrice = PriceType.valueOf(t.nextToken());
		PriceType otherNetPrice = PriceType.valueOf(t.nextToken());
		PriceType landlinePrice = PriceType.valueOf(t.nextToken());
		PriceType internetPrice = PriceType.valueOf(t.nextToken());
		double connectionFee = Double.parseDouble(t.nextToken());
		int subscribersQuantity = Integer.parseInt(t.nextToken());
		int favourNumber = Integer.parseInt(t.nextToken());
		SocialGroupType group = SocialGroupType.valueOf(t.nextToken().toUpperCase());

		if (group.equals(SocialGroupType.YOUTH)) {
			payroll *= 0.5;
		} else if (group.equals(SocialGroupType.PENSIONER)) {
			payroll *= 0.3;
		}
		return new SocialTariff(name, payroll, sameNetPrice, otherNetPrice, landlinePrice, internetPrice, connectionFee,
				subscribersQuantity, favourNumber, group);

	}

	/////////////////////////////////////////////////////////////////////////////
	// create appropriate Tariff object according to the beginning of each line
	private static Tariff createApproriateTariff(StringTokenizer t) {

		TariffType kindTariff = TariffType.valueOf(t.nextToken());
		switch (kindTariff) {
		case SOCIAL_TARIFF:
			return SocialBonus(t);

		case GUEST_TARIFF:
			return new GuestTariff(t.nextToken(), Double.parseDouble(t.nextToken()), PriceType.valueOf(t.nextToken()),
					PriceType.valueOf(t.nextToken()), PriceType.valueOf(t.nextToken()),
					PriceType.valueOf(t.nextToken()), Double.parseDouble(t.nextToken()),
					Integer.parseInt(t.nextToken()), Integer.parseInt(t.nextToken()));

		case REMAIN_TARIFF:
			return new RemainTariff(t.nextToken(), Double.parseDouble(t.nextToken()), PriceType.valueOf(t.nextToken()),
					PriceType.valueOf(t.nextToken()), PriceType.valueOf(t.nextToken()),
					PriceType.valueOf(t.nextToken()), Double.parseDouble(t.nextToken()),
					Integer.parseInt(t.nextToken()), Double.parseDouble(t.nextToken()));

		case UNLIM_INTERNET_TARIFF:
			return new UnlimInternetTariff(t.nextToken(), Double.parseDouble(t.nextToken()),
					PriceType.valueOf(t.nextToken()), PriceType.valueOf(t.nextToken()),
					PriceType.valueOf(t.nextToken()), PriceType.valueOf(t.nextToken()),
					Double.parseDouble(t.nextToken()), Integer.parseInt(t.nextToken()),
					Integer.parseInt(t.nextToken()));

		default:
			throw new IllegalArgumentException("Invalid tariff's type");
		}
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
				} else if (t.countTokens() < 3 && (line.startsWith("NAME") | line.startsWith("GROUP"))) {
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
	/////////////////////////////////////////////////////////////////////////////

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
	/////////////////////////////////////////////////////////////////////////////

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

			case CONNECTION_FEE:
				if (tariff.getConnectionFee() >= Double.parseDouble(from)
						&& tariff.getConnectionFee() <= Double.parseDouble(to)) {
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

			case DAYS:
				if (tariff instanceof GuestTariff && ((GuestTariff) tariff).getDays() >= Integer.parseInt(from)
						&& ((GuestTariff) tariff).getDays() <= Integer.parseInt(to)) {
					continue;
				}
				return false;

			case MODEM_MODE:
				if (tariff instanceof UnlimInternetTariff
						&& ((UnlimInternetTariff) tariff).getModemMode() >= Integer.parseInt(from)
						&& ((UnlimInternetTariff) tariff).getModemMode() <= Integer.parseInt(to)) {
					continue;
				}
				return false;

			case REMAINING_RATE:
				if (tariff instanceof RemainTariff
						&& ((RemainTariff) tariff).getRemainingRate() >= Double.parseDouble(from)
						&& ((RemainTariff) tariff).getRemainingRate() <= Double.parseDouble(to)) {
					continue;
				}
				return false;

			default:
				throw new IllegalArgumentException("Invalid parameter's type");
			}
		}
		return true;
	}
	/////////////////////////////////////////////////////////////////////////////

}
