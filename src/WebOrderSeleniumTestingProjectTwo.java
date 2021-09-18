import com.github.javafaker.Faker;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.text.ParseException;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class WebOrderSeleniumTestingProjectTwo {

    @Test
    public void WebOrder() throws ParseException, InterruptedException {

        // Faker genrator and locale
        Locale locale = new Locale("en-US");
        Faker faker = new Faker(locale);

        // Browser Initialization and navigating to the link (URL).
        System.setProperty("webdriver.gecko.driver", "/Users/fmirzaev/Documents/Selenium Packages/drivers/geckodriver");
        WebDriver driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.get("http://secure.smartbearsoftware.com/samples/TestComplete12/WebOrders/Login.aspx");

        // Login using username Tester and password test.
        Thread.sleep(500);
        WebElement Textbox_username = driver.findElement(By.name("ctl00$MainContent$username"));
        Textbox_username.sendKeys("Tester");
        driver.findElement(By.name("ctl00$MainContent$password")).sendKeys("test");
        driver.findElement(By.xpath("//input[@value='Login' and @type='submit']")).click();
        // Click on Order Link (URL).
        driver.findElement(By.linkText("Order")).click();

        // Verifying if Web Title and Checking Success Login.
        String expected_title = "Web Orders";
        String actual_title = driver.getTitle();
        Assert.assertEquals(expected_title, actual_title);

        // Enter a random product quantity between 1 and 100.
        Thread.sleep(500);
        WebElement quantityTextbox = driver.findElement(By.xpath("//*[starts-with(@id, 'ctl00_MainContent_fmwOrder_txtQuantity')]"));
        int randomQuantity = faker.number().numberBetween(1,100);
        quantityTextbox.sendKeys(randomQuantity + "");
        // Click on Calculate and verify that the Total value is correct.
        driver.findElement(By.xpath("//input[@value='Calculate' and @type='submit']")).click();


        // Generate random customer fake full name and address using faker.jar file by adding the jar and importing it.
        WebElement customerNameTextbox = driver.findElement(By.xpath("//*[starts-with(@id, 'ctl00_MainContent_fmwOrder_txtName')]"));
        WebElement streetTextbox = driver.findElement(By.xpath("//*[starts-with(@id, 'ctl00_MainContent_fmwOrder_TextBox2')]"));
        WebElement cityTextbox = driver.findElement(By.xpath("//*[starts-with(@id, 'ctl00_MainContent_fmwOrder_TextBox3')]"));
        WebElement stateTextbox = driver.findElement(By.xpath("//*[starts-with(@id, 'ctl00_MainContent_fmwOrder_TextBox4')]"));
        WebElement zipTextbox = driver.findElement(By.xpath("//*[starts-with(@id, 'ctl00_MainContent_fmwOrder_TextBox5')]"));
        List<WebElement> Radiolist = driver.findElements(By.xpath("//*[starts-with(@id, 'ctl00_MainContent_fmwOrder_cardList')]")) ;
        // Radio List Random Selecto.
        java.util.Random random = new Random();
        int index = random.nextInt(Radiolist.size());
        Radiolist.get(index).click();
        WebElement ccTextbox = driver.findElement(By.xpath("//*[starts-with(@id, 'ctl00_MainContent_fmwOrder_TextBox6')]"));
        WebElement expDateTextbox = driver.findElement(By.xpath("//*[starts-with(@id, 'ctl00_MainContent_fmwOrder_TextBox1')]"));
        String exp = "11/26";
        expDateTextbox.sendKeys(exp);

        // Faker name, address, credit card generator.
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String streetAddress = faker.address().streetAddress();
        String cityName = faker.address().cityName();
        String state = faker.address().state();
        String zipCode = faker.address().zipCode();
        long ccNo = faker.number().randomNumber(16,false);

        // Sends Keys to text box (form input field)
        customerNameTextbox.sendKeys(firstName + " " + lastName);
        streetTextbox.sendKeys(streetAddress);
        cityTextbox.sendKeys(cityName);
        stateTextbox.sendKeys(state);
        zipTextbox.sendKeys(zipCode);
        ccTextbox.sendKeys(ccNo + "");

        // Process Order Submit by clicking once everything is verified and validated.
        driver.findElement(By.id("ctl00_MainContent_fmwOrder_InsertButton")).click();

        // Verify that “New order has been successfully added” message appeared on the page.
        System.out.println(driver.getPageSource().contains("New order has been successfully added."));

        // Click on View All Orders link(URL).
        Thread.sleep(500);
        driver.findElement(By.linkText("View all orders")).click();

        // After successful order process, Log out of the application.
        Thread.sleep(500);
        driver.findElement(By.id("ctl00_logout")).click();

    }

}
