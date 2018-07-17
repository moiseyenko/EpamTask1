package by.epam.task1.algorithms;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;

import by.epam.task1.additions.Parameter;
import by.epam.task1.additions.Range;
import by.epam.task1.additions.SocialGroup;
import by.epam.task1.tariff.AllInTariff;
import by.epam.task1.tariff.InternetOnlyTariff;
import by.epam.task1.tariff.SocialTariff;
import by.epam.task1.tariff.Tariff;
import by.epam.task1.tariff.TouristTariff;

public class Tariffs {
	
	public static int countTotalAbonents(List<Tariff> tariffList) {
		int result = 0;
		for(Tariff tariff:tariffList) {
			result += tariff.getAbonentsQuantity();
		}
		return result;
	}
/////////////////////////////////////////////////////////////////////////////

	public static void sortByPayroll (List<Tariff> tariffList) {
		tariffList.sort(Comparator.comparingDouble(Tariff::getPayroll));
	}
/////////////////////////////////////////////////////////////////////////////
	
	//return tariffs' list after reading the file
	public static List<Tariff> readTariffsFile (String path){
		
		List<Tariff> tariffs = new ArrayList<>();
		
		try (Scanner sc = new Scanner(Paths.get(path))){
			String line;
			while(sc.hasNextLine()) {
				line = sc.nextLine();
				StringTokenizer t = new StringTokenizer(line, ":");
				String kindTariff = t.nextToken();
				
				//create appropriate Tariff object according to the beginning of each line
				switch (kindTariff) {
					case "SocialTariff":
						tariffs.add(new SocialTariff(t.nextToken(), SocialGroup.valueOf(t.nextToken()), Double.parseDouble(t.nextToken()), 
								Integer.parseInt(t.nextToken()), Integer.parseInt(t.nextToken()),Integer.parseInt(t.nextToken())));
						break;
					case "TouristTariff":
						tariffs.add(new TouristTariff(t.nextToken(), Double.parseDouble(t.nextToken()), 
								Integer.parseInt(t.nextToken()), Integer.parseInt(t.nextToken()),Integer.parseInt(t.nextToken())));
						break;
					case "AllInTariff":
						tariffs.add(new AllInTariff(t.nextToken(), Double.parseDouble(t.nextToken()), 
								Double.parseDouble(t.nextToken()), Integer.parseInt(t.nextToken()),Integer.parseInt(t.nextToken())));
						break;
					case "InternetOnlyTariff":
						tariffs.add(new InternetOnlyTariff(t.nextToken(), Double.parseDouble(t.nextToken()), 
								Integer.parseInt(t.nextToken()), Integer.parseInt(t.nextToken())));
						break;
					default: throw new IllegalArgumentException("Invalid tariff type");
				}
	
			}
			
		}catch (IOException e) {
			e.printStackTrace();
		}
		
		return tariffs;
	}
/////////////////////////////////////////////////////////////////////////////
	
	//return parameters' map after reading the file
	public static Map<Parameter, Range> readParametersRange (String path) {
		
		Map<Parameter, Range> inputParameters = new HashMap<>();
		
		try (Scanner sc = new Scanner(Paths.get(path))){
			String line;
			while(sc.hasNextLine()) {
				line = sc.nextLine();
				StringTokenizer t = new StringTokenizer(line, ":");
						
				Parameter key;
				String from;
				String to;
				
				if(t.countTokens()<2) {
					continue;
				}else if (t.countTokens()<3 && (line.startsWith("NAME")|line.startsWith("GROUP"))) {
					key = Parameter.valueOf(t.nextToken());
					from = to = t.nextToken();
				}else if(t.countTokens()<3) {
					continue;
				}else {
					key = Parameter.valueOf(t.nextToken());
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
	
	//form list with suit tariffs 
	public static List<String> formSuitTariffs (List<Tariff> tariffs, Map<Parameter, Range>inputParameters){
		
		List<String> list = new ArrayList<>();
		
		for(Tariff tariff:tariffs) {
			if (checkParameters(tariff, inputParameters)) {
				list.add(tariff.getName());
			}
		}
		return list;
	}
/////////////////////////////////////////////////////////////////////////////
	
	//check what parameters have been written in the file
	private static boolean checkParameters (Tariff tariff, Map<Parameter,Range> inputParameters) {
		
		Parameter key;
		String from;
		String to;
		
		for (Map.Entry<Parameter, Range>entry:inputParameters.entrySet()) {
			
			key = entry.getKey();
			from = entry.getValue().getFrom();
			to = entry.getValue().getTo();
			
			switch (key) {
				case NAME:
					if(tariff.getName().toLowerCase().contains(from.toLowerCase())) {
						continue;
					}
					return false;
					
				case PAYROLL:
					if(tariff.getPayroll()>=Double.parseDouble(from)
								&&tariff.getPayroll()<=Double.parseDouble(to)) {
						continue;
					}
					return false;
					
				case SAMENETPRICE:
					if(tariff.getSameNetPrice().getPrice()>=Double.parseDouble(from)
							&&tariff.getSameNetPrice().getPrice()<=Double.parseDouble(to)) {
						continue;
					}
					return false;
					
				case OTHERNETPRICE:
					if(tariff.getOtherNetPrice().getPrice()>=Double.parseDouble(from)
							&&tariff.getOtherNetPrice().getPrice()<=Double.parseDouble(to)) {
						continue;
					}
					return false;
					
				case LANDLINEPRICE:
					if(tariff.getLandlinePrice().getPrice()>=Double.parseDouble(from)
							&&tariff.getLandlinePrice().getPrice()<=Double.parseDouble(to)) {
						continue;
					}
					return false;

				case INTERNETPRICE:
					if(tariff.getInternetPrice().getPrice()>=Double.parseDouble(from)
							&&tariff.getInternetPrice().getPrice()<=Double.parseDouble(to)) {
						continue;
					}
					return false;

				case CONNECTIONFEE:
					if(tariff.getConnectionFee()>=Double.parseDouble(from)
							&&tariff.getConnectionFee()<=Double.parseDouble(to)) {
						continue;
					}
					return false;

				case INTERNETTRAFIC:
					if(tariff.getConnectionFee()>=Integer.parseInt(from)
							&&tariff.getConnectionFee()<=Integer.parseInt(to)) {
						continue;
					}
					return false;
					
				case FAVOURNUMBER:
					if(tariff.getFavourNumber()>=Integer.parseInt(from)
							&&tariff.getFavourNumber()<=Integer.parseInt(to)) {
						continue;
					}
					return false;
					
				case GROUP:
					if(tariff instanceof SocialTariff
							&& ((SocialTariff)tariff).getGroup().name().toLowerCase().contains(from.toLowerCase())) {
							continue;
						}
					return false;
			}
		}
		return true;
	}
/////////////////////////////////////////////////////////////////////////////
	
}
