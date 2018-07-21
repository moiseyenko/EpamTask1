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
import by.epam.task1.entity.InternetTariff;

public class TariffHelper {

	private final static Logger log = LogManager.getLogger(TariffHelper.class);

	// count total subscribers amount
	public static int countTotalSubscribers(List<Tariff> tariffList) {
		log.debug("Start countTotalSubscribers");
		int result = 0;
		for (Tariff tariff : tariffList) {
			result += tariff.getSubscribersQuantity();
		}
		log.debug("End countTotalSubscribers with result: " + result);
		return result;
	}

	// sort tariffs by payroll
	public static void sortByPayroll(List<Tariff> tariffList) {
		log.debug("Start sortByPayroll with tariffList hashcode: " + tariffList.hashCode());
		tariffList.sort(Comparator.comparingDouble(Tariff::getPayroll));
		log.debug("End sortByPayroll with tariffList hashcode: " + tariffList.hashCode());
	}

	// return tariffs' list after reading the file
	public static List<Tariff> readTariffsFile(String path) {
		log.debug("Start readTariffsFile with path: " + path);
		List<Tariff> tariffs = new ArrayList<>();

		try (BufferedReader br = new BufferedReader(new FileReader(new File(path)))) {
			String line;
			while ((line = br.readLine()) != null) {
				StringTokenizer t = new StringTokenizer(line, ":");
				// create appropriate Tariff object according to the beginning of each line
				Tariff tariff = initializeTariff(t);
				log.debug("initializeTariff Return: " + tariff.getName());
				// add Tariff to list
				tariffs.add(tariff);

			}

		} catch (FileNotFoundException e) {
			log.error(e);
		} catch (QuantityException e1) {
			log.error(e1);
		} catch (IOException e2) {
			log.error(e2);
		}
		log.debug("End readTariffsFile with tariffs' list size: " + tariffs.size());
		return tariffs;
	}

	// initialize tariff
	private static Tariff initializeTariff(StringTokenizer t) throws QuantityException {
		log.debug("Start initializeTariff with StringTokenizer.count: " + t.countTokens());
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

		Tariff tariff;
		switch (tariffType) {
		case SOCIAL_TARIFF:
			int favourNumber = Integer.parseInt(t.nextToken());
			SocialGroupType group = SocialGroupType.valueOf(t.nextToken().toUpperCase());
			favourNumberRestriction(favourNumber, group);
			payroll = socialTariffBonus(group, payroll);
			log.debug("Return socialTariffBonus payroll:" + payroll);

			tariff = new SocialTariff();
			tariff.setName(name);
			tariff.setPayroll(payroll);
			tariff.setSameNetPrice(sameNetPrice);
			tariff.setOtherNetPrice(otherNetPrice);
			tariff.setLandlinePrice(landlinePrice);
			tariff.setInternetPrice(internetPrice);
			tariff.setSubscribersQuantity(subscribersQuantity);
			((SocialTariff) tariff).setGroup(group);
			((SocialTariff) tariff).setFavourNumber(favourNumber);
			return tariff;

		case GUEST_TARIFF:
			int days = Integer.parseInt(t.nextToken());
			daysRestriction(days);
			payroll = getPayrolRateForGuestTariff(days, payroll);

			tariff = new GuestTariff();
			tariff.setName(name);
			tariff.setPayroll(payroll);
			tariff.setSameNetPrice(sameNetPrice);
			tariff.setOtherNetPrice(otherNetPrice);
			tariff.setLandlinePrice(landlinePrice);
			tariff.setInternetPrice(internetPrice);
			tariff.setSubscribersQuantity(subscribersQuantity);
			((GuestTariff) tariff).setDays(days);
			return tariff;

		case INTERNET_TARIFF:
			boolean unlim = Boolean.parseBoolean(t.nextToken());
			internetPrice = internetTariffBonus(internetPrice, unlim);
			log.debug("Return internetTariff:" + internetPrice);

			tariff = new InternetTariff();
			tariff.setName(name);
			tariff.setPayroll(payroll);
			tariff.setSameNetPrice(sameNetPrice);
			tariff.setOtherNetPrice(otherNetPrice);
			tariff.setLandlinePrice(landlinePrice);
			tariff.setInternetPrice(internetPrice);
			tariff.setSubscribersQuantity(subscribersQuantity);
			((InternetTariff) tariff).setUnlim(unlim);
			return tariff;

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
		log.debug("Start internetTariffBonus with unlim:" + unlim);
		if (unlim) {
			return PriceType.ZERO;
		} else {
			return internetPrice;
		}
	}

	// set socialTariff's bonus depending on subscriber's social group
	public static double socialTariffBonus(SocialGroupType group, double payroll) {
		log.debug("Start socialTariffBonus with group:" + group + " and payroll: " + payroll);
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
		log.debug("Start getPayrolRateForGuestTariff with days: "+days+" and payroll: "+payroll);
		int dayOfMonth = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH);
		payroll *= (double) days / dayOfMonth;
		log.debug("End getPayrolRateForGuestTariff with payroll: "+payroll);
		return payroll;
	}

	// return parameters' map after reading the file
	public static Map<ParameterType, Range> readParametersRange(String path) {
		log.debug("Start readParametersRange with path: "+path);
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
		} catch (FileNotFoundException e) {
			log.error(e);
		} catch (IOException e1) {
			log.error(e1);
		}
		log.debug("End readParametersRange with Map size: "+inputParameters.size());
		return inputParameters;
	}

	// form list with suit tariffs
	public static List<String> searchTariff(List<Tariff> tariffs, Map<ParameterType, Range> inputParameters) {
		log.debug("Start searchTariff with tariffs list size: "+tariffs.size()+" and parameters map size: "+ inputParameters.size());
		List<String> list = new ArrayList<>();

		for (Tariff tariff : tariffs) {
			boolean isSuit = checkParameters(tariff, inputParameters);
			log.debug("checkParameters Return :"+ isSuit);
			if (isSuit) {
				list.add(tariff.getName());
			}
		}
		log.debug("End searchTariff with result list size: "+list.size());
		return list;
	}

	// check what parameters have been written in the file
	private static boolean checkParameters(Tariff tariff, Map<ParameterType, Range> inputParameters) {
		log.debug("Start checkParameters with tariff: "+tariff.getName()+" and parameters map size: "+ inputParameters.size());
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

			}
		}
		return true;
	}

}
