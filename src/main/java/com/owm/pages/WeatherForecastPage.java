package com.owm.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.owm.base.TestBase;

public class WeatherForecastPage extends TestBase{

	//Page Factory - Object Repository:
	@FindBy(xpath="//div[@id='forecast_list_ul']/table/tbody/tr/td/following-sibling::td/b/a") // Link of city searched for weather forecast
	WebElement cityLink;
	
	@FindBy(xpath="//span[@id='imperial' and contains(text(), '°F')]") // °C to °F slide bar at the right top of the page
	WebElement farenheitButton;
	
	@FindBy(xpath="//h3[@class='weather-widget__temperature']") // Temperature display widget of searched city
	WebElement farenheit;
	
	public WeatherForecastPage() {
		PageFactory.initElements(driver, this);
	}
	
	public void clickOnSearchedCity() {
		click(driver, cityLink, 30);
	}
	
	public void clickOnTempUnitSlideBar() {
		click(driver, farenheitButton, 30);
	}
	
	public String checkfarenheit() {
		return farenheit.getText();
	}
}
