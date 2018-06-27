package Kanika;

import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

public class NewTest {

  
  WebDriver driver;
	
	@BeforeClass
	public void launchBrowser()
	{
		//ChromeDriverManager.getInstance().setup();
		System.setProperty("webdriver.chrome.driver", "/home/qainfotech/Desktop/chromedriver");
		this.driver = new ChromeDriver();
		this.driver.get("http://10.0.1.86/tatoc");
	}
	
	@AfterClass
	public void closeBrowser()
	{
		this.driver.quit();
	}
	
		@Test
	public void basicCourse()
	{
		driver.findElement(By.linkText("Basic Course")).click();
		Assert.assertEquals("http://10.0.1.86/tatoc/basic/grid/gate", this.driver.getCurrentUrl());
	}
	
	@Test(dependsOnMethods = {"basicCourse"})
	public void greenBox()
	{
		driver.findElement(By.className("greenbox")).click();
		Assert.assertEquals("http://10.0.1.86/tatoc/basic/frame/dungeon", this.driver.getCurrentUrl());
	}
	
	//3rd page- "Repaint Box-2" in Frame Dungeon
	@Test(dependsOnMethods = {"greenBox"})
	public void frameDungeon()
	{
		driver.switchTo().frame(0);
		WebElement box1 = driver.findElement(By.id("answer"));
		String colorBox1 = box1.getAttribute("class");
		String colorBox2 = "";
	    while(!colorBox1.equals(colorBox2)){
		    driver.switchTo().defaultContent();
		    driver.switchTo().frame(0);
		    driver.findElement(By.cssSelector("a")).click();
		    driver.switchTo().frame(0);
		    colorBox2 = driver.findElement(By.id("answer")).getAttribute("class");
		     }
	   driver.switchTo().defaultContent();
	   driver.switchTo().frame(0).findElement(By.linkText("Proceed")).click();   
	   Assert.assertEquals("http://10.0.1.86/tatoc/basic/drag", this.driver.getCurrentUrl());
	}

	@Test(dependsOnMethods = {"frameDungeon"})
	public void drag()
	{
		JavascriptExecutor js = (JavascriptExecutor) driver;
		WebElement dragbox = driver.findElement(By.id("dragbox"));
		js.executeScript("arguments[0].setAttribute('style','position: relative; left: 30px; top: -71px;')",
				   dragbox);	   
		this.driver.findElement(By.linkText("Proceed")).click();
		Assert.assertEquals("http://10.0.1.86/tatoc/basic/windows", this.driver.getCurrentUrl());
	}
	
	@Test(dependsOnMethods = {"drag"})
	public void popUpWindows()
	{
		driver.findElement(By.linkText("Launch Popup Window")).click();
      String parentwindow = driver.getWindowHandle();
	    String subWindow = null;
	    Set<String> windows = driver.getWindowHandles();
	    Iterator itr = (Iterator) windows.iterator();
	    while(((java.util.Iterator<String>) itr).hasNext()){
	    	subWindow = (String) itr.next();
	    }
	    driver.switchTo().window(subWindow);
	    driver.findElement(By.id("name")).sendKeys("Kanika");
	    driver.findElement(By.id("submit")).click();
	    driver.switchTo().window(parentwindow);
	    driver.findElement(By.linkText("Proceed")).click();
	    Assert.assertEquals("http://10.0.1.86/tatoc/basic/cookie",this.driver.getCurrentUrl());
	}
	
	@Test(dependsOnMethods = {"popUpWindows"})
	public void cookie()
	{
		driver.findElement(By.linkText("Generate Token")).click();
	    String token_text = driver.findElement(By.id("token")).getText();
	    String token = token_text.substring(token_text.indexOf(":")+2);
	    Cookie cookie = new Cookie("Token",token);
	    driver.manage().addCookie(cookie);
	    driver.findElement(By.linkText("Proceed")).click();
	    Assert.assertEquals("Cookie Handling" , this.driver.getTitle());
	}
		
  
  

//  @Test
//  public void f() {
//	  System.out.println("I am Working fine ");
//  }
//  
	
	
	
}
