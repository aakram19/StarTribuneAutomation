package com.startribune.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.startribune.baseclass.StarTribuneBaseClass;

public class LoginModule extends StarTribuneBaseClass{
	
	public LoginModule(WebDriver driver) {
		this.driver = driver;
	}
	
	
	public LoginModule enterUserName() {
		WebElement loginField = driver.findElement(By.id("login[username]"));
		setData(loginField, "ali-akram-st");
		return this;
	}
	
	public LoginModule enterPassword() {
		WebElement passwordField = driver.findElement(By.id("login[password]"));
		setData(passwordField, "Sadj7!");
		return this;
	}
	
	public LoginModule clickOnLoginButton() {
		WebElement loginButton = driver.findElement(By.xpath("//form[@class='c-auth__login-form']//input[@type='submit']"));
		clickOnElement(loginButton);
		return this;
	}
	
	public LoginModule validateStarTribuneHomeText() {
		WebElement logoText = driver.findElement(By.xpath("//a[@class='nav-logo-link']"));	
		Assert.assertTrue(logoText.getText().equals("Star Tribune"));
		try {
			driver.findElement(By.id("close-button-image")).click();
		} catch(Exception e) {
			System.out.println("No popup is displayed");
		}
		return this;
	}
	
	public LoginModule clickOnMenuLink(String menuName) {
		WebElement elemnet = driver.findElement(By.xpath(String.format("//a[@href= 'https://www.startribune.com/%s/']",menuName)));
		clickOnElement(elemnet);
		return this;
	}
	
	public LoginModule validateTextWithMenuLink(String expectedText) {
		WebElement elemnet = driver.findElement(By.xpath("//ul[@class='nav-section-mod col-2']//li/a"));
		System.out.println(elemnet.getText());
		Assert.assertTrue(elemnet.getText().equals(expectedText));
		return this;
	}
	
	public LoginModule validateJobsURL() {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.urlContains("https://jobs.startribune.com/"));	
		return this;
	}

}
