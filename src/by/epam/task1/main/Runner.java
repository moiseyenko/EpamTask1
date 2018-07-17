package by.epam.task1.main;

import java.util.List;
import java.util.Map;

import by.epam.task1.additions.Parameter;
import by.epam.task1.additions.Range;
import by.epam.task1.algorithms.Tariffs;
import by.epam.task1.message.Message;
import by.epam.task1.tariff.Tariff;


public class Runner {

	public static void main(String[] args){
		
		
		String path = "inputTariffs.txt";
		
		List<Tariff> tariffs = Tariffs.readTariffsFile(path);
		
		int totalAbonents = Tariffs.countTotalAbonents(tariffs);
		
		Message.showMsg("Total amount of abonents equals to ", totalAbonents);
		
		Message.showMsg("before sorting: ", tariffs);
		Tariffs.sortByPayroll(tariffs);
		Message.showMsg("after sorting: ", tariffs);
		
		path = "inputParameters.txt";
		
		Map<Parameter, Range> inputParameters = Tariffs.readParametersRange(path);
		List<String> resultList = Tariffs.formSuitTariffs(tariffs, inputParameters);
		Message.showMsg("Suit tariffs: ", resultList);
		
	}
	
}
