package by.epam.task1.tariff;

import by.epam.task1.additions.PriceCategory;

public interface Tariff {
	
	String getName(); 
	void setName(String name); 
	
	double getPayroll() ;
	void setPayroll(double payroll); 
	
	PriceCategory getSameNetPrice() ;

	PriceCategory getOtherNetPrice() ;

	PriceCategory getLandlinePrice();

	PriceCategory getInternetPrice() ;
	
	double getConnectionFee(); 
	void setConnectionFee(double connectionFee); 
	
	int getInternetTrafic(); 
	void setInternetTrafic(int internetTrafic); 
	
	int getFavourNumber();
	void setFavourNumber(int favourNumber); 
	
	int getAbonentsQuantity();
	void setAbonentsQuantity(int abonentsQuantity);
	
}
