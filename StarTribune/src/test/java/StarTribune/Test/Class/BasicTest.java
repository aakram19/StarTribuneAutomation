package StarTribune.Test.Class;

import org.testng.annotations.Test;

import com.startribune.baseclass.StarTribuneBaseClass;
import com.startribune.pageobjects.LoginModule;

public class BasicTest extends StarTribuneBaseClass{
	
	@Test
	public void validateStarTribuneLogo() {
		LoginModule login = new LoginModule(driver);
		login.validateStarTribuneHomeText().close();
	}
	
	@Test
	public void verifySportsMenuLink() {
		LoginModule login = new LoginModule(driver);
		login.validateStarTribuneHomeText().clickOnMenuLink("sports").validateTextWithMenuLink("SPORTS").close();
		
	}
	
	@Test
	public void verifyBusinessMenuLink() {
		LoginModule login = new LoginModule(driver);
		login.validateStarTribuneHomeText().clickOnMenuLink("business").validateTextWithMenuLink("BUSINESS").close();
		
	}
	
	@Test
	public void verifyOpinionMenuLink() {
		LoginModule login = new LoginModule(driver);
		login.validateStarTribuneHomeText().clickOnMenuLink("opinion").validateTextWithMenuLink("OPINION").close();
	}
	
	@Test
	public void verifyVarietyMenuLink() {
		LoginModule login = new LoginModule(driver);
		login.validateStarTribuneHomeText().clickOnMenuLink("variety").validateTextWithMenuLink("VARIETY").close();
		
	}
	
	
	@Test
	public void verifyObituariesMenuLink() {
		LoginModule login = new LoginModule(driver);
		login.validateStarTribuneHomeText().clickOnMenuLink("obituaries").validateTextWithMenuLink("OBITUARIES").close();
		
	}
	
	@Test
	public void verifyClassifiedsMenuLink() {
		LoginModule login = new LoginModule(driver);
		login.validateStarTribuneHomeText().clickOnMenuLink("classifieds").validateTextWithMenuLink("CLASSIFIEDS").close();
		
	}
	
	@Test
	public void verifyAutosMenuLink() {
		LoginModule login = new LoginModule(driver);
		login.validateStarTribuneHomeText().clickOnMenuLink("autos").validateTextWithMenuLink("AUTOS").close();
		
	}
	
	@Test
	public void verifyHousingMenuLink() {
		LoginModule login = new LoginModule(driver);
		login.validateStarTribuneHomeText().clickOnMenuLink("housing").validateTextWithMenuLink("HOUSING").close();
		
	}
	
	@Test
	public void verifyJobsMenuLink() {
		LoginModule login = new LoginModule(driver);
		login.validateStarTribuneHomeText().clickOnMenuLink("jobs").validateJobsURL().close();
	} 
}
