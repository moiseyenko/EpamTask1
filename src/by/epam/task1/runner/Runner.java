package by.epam.task1.runner;

import java.util.List;
import java.util.Map;
import by.epam.task1.entity.Tariff;
import by.epam.task1.util.Message;
import by.epam.task1.util.ParameterType;
import by.epam.task1.util.Range;
import by.epam.task1.util.TariffConstants;
import by.epam.task1.util.TariffHelper;
public class Runner {

	public static void main(String[] args) {
		
		List<Tariff> tariffs = TariffHelper.readTariffsFile(TariffConstants.TARIFFS_PATH);

		int totalAbonents = TariffHelper.countTotalSubscribers(tariffs);

		Message.showMsg("Total amount of abonents equals to ", totalAbonents);

		Message.showMsg("before sorting: ", tariffs);
		TariffHelper.sortByPayroll(tariffs);
		Message.showMsg("after sorting: ", tariffs);

		Map<ParameterType, Range> inputParameters = TariffHelper.readParametersRange(TariffConstants.PARAMETERS_PATH);
		List<String> resultList = TariffHelper.searchTariff(tariffs, inputParameters);
		Message.showMsg("Suit tariffs: ", resultList);

	}

}
