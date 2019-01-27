import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import sun.rmi.runtime.Log;

import java.security.Key;
import java.util.Iterator;
import java.util.Set;

public class Test1 {
    public static final String url="https://dou.ua/";
    public WebDriver driver;
        @BeforeTest
        public void setUp() throws Exception{
            System.setProperty("webdriver.chrome.driver", "C:\\Users\\x-jap\\IdeaProjects\\automation_1\\src\\main\\resources\\chromedriver.exe");
            driver= new ChromeDriver();
            driver.manage().window().maximize();
            }

        // Verify that user is redirected to search page after pressing the "Enter" button
        @Test
        public void  NewPageAfterSearching() {


            driver.get(url);
            driver.findElement(By.xpath("//*[@id=\"txtGlobalSearch\"]")).sendKeys("EPAM");
            driver.findElement(By.xpath("//*[@id=\"txtGlobalSearch\"]")).sendKeys(Keys.ENTER);
            String URLafterSearch =driver.getCurrentUrl();
            Assert.assertEquals(URLafterSearch, "https://dou.ua/search/?q=EPAM");
            }

    // Verify login modal window is displayed after clicking on the login button
        @Test
        public  void LoginModalWindow() throws InterruptedException {
            driver.get(url);
            driver.findElement(By.xpath("//*[@id=\"login-link\"]")).click();
            Thread.sleep(500);
            driver.findElement(By.xpath("//*[@id=\"_loginDialog\"]")).isDisplayed();
            }

        // Verify that checkbox is checked and unchecked after clicking on it in the login modal window
        @Test
        public void CheckAndUncheckCheckbox() throws InterruptedException {
            LoginModalWindow();
            WebElement agree_checkbox=driver.findElement(By.xpath("//*[@id=\"agree-checkbox\"]"));
            Assert.assertTrue(agree_checkbox.isSelected());
            agree_checkbox.click();
            Assert.assertFalse(agree_checkbox.isSelected());
            agree_checkbox.click();
            Assert.assertTrue(agree_checkbox.isSelected());
               }

        //Verify that login modal window is closed after clicking on the cross icon button
        @Test
        public void CloseLoginModalWindow() throws InterruptedException {
            LoginModalWindow();
            driver.findElement(By.xpath("//*[@class=\"close cancel\"]")).click();
            Assert.assertFalse(driver.findElement(By.xpath("//*[@id=\"_loginDialog\"]")).isDisplayed());
        }

        //Verify that google login page is opened in new window after clicking on the "registration via google" button
        @Test
        public void RegistrationViaGogole() throws InterruptedException {
            LoginModalWindow();
            String mwh=driver.getWindowHandle();
            driver.findElement(By.xpath("//*[@class=\"login-button btnGoogle\"]")).click();

            Set s=driver.getWindowHandles(); //this method will give us the handles of all opened windows
            Iterator ite=s.iterator();

            while(ite.hasNext())
            {
                String popupHandle=ite.next().toString();
                if(!popupHandle.contains(mwh))
                {
                    driver.switchTo().window(popupHandle);
                    driver.switchTo().window(mwh);
                }
            }
            driver.close();
        }
}


