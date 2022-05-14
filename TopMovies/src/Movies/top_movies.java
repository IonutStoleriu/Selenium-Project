
package Movies;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class top_movies {
	WebDriver driver;
	@BeforeMethod
	public void setUP() throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", "D:\\Selenium\\chromedriver.exe");
		driver=new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://betterqa.ro/top-movies/");
		driver.findElement(By.id("pwbox-4212")).sendKeys("do_not_share!1");
		driver.findElement(By.name("Submit")).click();
		Thread.sleep(3000);
	}
	@Test(priority=1)
	public void check_movie_date() {
		String release_date="1994-09-22";
		
		driver.findElement(By.cssSelector("#root > div > div > div:nth-child(1) > div.jss92 > button > span.jss95")).click();
		String date = driver.findElement(By.cssSelector("body > div.jss87.jss80 > div.jss10.jss36.jss11.jss81.jss83 > div.jss122 > div:nth-child(3) > div > input")).getAttribute("value");
		
		Assert.assertEquals(release_date, date);
	}
	@Test(priority=2)
	public void check_movies_list()
	{
		List<WebElement> movies=driver.findElements(By.className("movie-card"));
		for(int j=0;j<movies.size();j++)
		{
			String v=movies.get(j).getText();
			System.out.println(v);
			System.out.println();
		}
		System.out.print("Test Success: ");
		System.out.print(movies.size());
		System.out.print(" movies.");
		
		Assert.assertNotEquals(movies.size(), 0);
	}
	@Test(priority=3)
	public void check_search_movie() throws InterruptedException {
		driver.findElement(By.cssSelector("#root > div > header > div > form > div > input")).sendKeys("Star Trek");
		driver.findElement(By.cssSelector("#root > div > header > div > form > div > input")).sendKeys(Keys.ENTER);
		Thread.sleep(5000);
		List<WebElement> movies=driver.findElements(By.className("jss44"));
		
		String displayed_movie="Star Trek: First Contact";
		String undisplayed_movie="The Shawshank Redemption";
		
		System.out.println("Nr. movies:");
		System.out.println(movies.size());
		List <String> all_movies=new ArrayList<String>();
		for(int i=0;i<movies.size();i++)
		{
			String mv=movies.get(i).getText();
			all_movies.add(mv);
		}
		Assert.assertEquals(all_movies.contains(displayed_movie) && !all_movies.contains(undisplayed_movie), true);
	}
	@Test(priority=4)
	public void check_movie_info() throws InterruptedException
	{
		driver.findElement(By.cssSelector("#root > div > header > div > form > div > input")).sendKeys("Tomb Raider");
		driver.findElement(By.cssSelector("#root > div > header > div > form > div > input")).sendKeys(Keys.ENTER);
		Thread.sleep(5000);
		
		driver.findElement(By.cssSelector("#root > div > div > div:nth-child(1) > div.jss92 > button > span.jss95")).click();
		
		String expected_release_date="2018-03-02";
		String expected_popularity="1.357";
		String expected_vote_average="6.3";
		String expected_vote_count="210458";
		List <String> expected_info= new ArrayList<String>();
		expected_info.add(expected_release_date);
		expected_info.add(expected_popularity);
		expected_info.add(expected_vote_average);
		expected_info.add(expected_vote_count);
		
		List<WebElement> movies_info=driver.findElements(By.className("jss73"));
		List <String> info=new ArrayList<String>();
		for(int i=0;i<movies_info.size();i++)
		{
			String mv_info=movies_info.get(i).getAttribute("value");
			info.add(mv_info);
		}
		
		for(int i=0;i<info.size();i++)
		{
			Assert.assertEquals(info.get(i),expected_info.get(i));
		}
	}
	@AfterMethod
	public void close() {
		driver.quit();
	}
	
}
