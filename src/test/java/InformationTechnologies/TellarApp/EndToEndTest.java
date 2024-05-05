package InformationTechnologies.TellarApp;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class EndToEndTest {

	public static void main(String[] args) {
		
		String productName="ZARA COAT 3";
		
		WebDriver driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		
		driver.get("https://rahulshettyacademy.com/client/");
		driver.findElement(By.id("userEmail")).sendKeys("nasinaramana@gmail.com");
		driver.findElement(By.id("userPassword")).sendKeys("Nvdarling@123");
		driver.findElement(By.xpath("//*[@id='login']")).click();
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(5));
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb-3")));
		List<WebElement> products=driver.findElements(By.cssSelector(".mb-3"));
		WebElement actualProd=products.stream().filter(product->
		product.findElement(By.cssSelector(".card-body b")).getText().equals(productName)).findFirst().orElse(null);
		actualProd.findElement(By.cssSelector(".card-body button:last-of-type")).click();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".ng-amnimating")));
		driver.findElement(By.cssSelector("*[routerlink*='cart']")).click();
		
		List<WebElement> finalProduct=driver.findElements(By.xpath("//*[@class='cartSection']/h3"));
		boolean match=finalProduct.stream().anyMatch(cartProduct->cartProduct.getText().equalsIgnoreCase(productName));
		Assert.assertTrue(match);
		driver.findElement(By.cssSelector(".totalRow button")).click();
		driver.findElement(By.xpath("//*[@class='field small']/input[@class='input txt']")).sendKeys("999");
		driver.findElement(By.xpath("//*[@placeholder='Select Country']")).sendKeys("ind");
		driver.findElement(By.xpath("//*[@class='field'] //*[@class='input txt']")).sendKeys("venkat");
		List<WebElement> countrys=driver.findElements(By.cssSelector("[class='ta-results list-group ng-star-inserted'] button"));
		for(WebElement country:countrys)
		{
			if(country.getText().equalsIgnoreCase("India"))
			{
				country.click();
				break;
			}
		}
		
		driver.findElement(By.cssSelector(".action__submit")).click();
		String confirmationMsg=driver.findElement(By.cssSelector(".hero-primary")).getText();
		confirmationMsg.equalsIgnoreCase("THANKYOU FOR THE ORDER.");

	}

}
