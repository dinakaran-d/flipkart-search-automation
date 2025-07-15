package demo;

import java.util.logging.Level;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import demo.wrappers.Wrappers;

public class TestCases {
    ChromeDriver driver;
    Wrappers wrappers;

    /*
     * TODO: Write your tests here with testng @Test annotation. 
     * Follow `testCase01` `testCase02`... format or what is provided in instructions
     */

     
    /*
     * Do not change the provided methods unless necessary, they will help in automation and assessment
     */
    @BeforeTest
    public void startBrowser()
    {
        System.setProperty("java.util.logging.config.file", "logging.properties");

        // NOT NEEDED FOR SELENIUM MANAGER
        // WebDriverManager.chromedriver().timeout(30).setup();

        ChromeOptions options = new ChromeOptions();
        LoggingPreferences logs = new LoggingPreferences();

        logs.enable(LogType.BROWSER, Level.ALL);
        logs.enable(LogType.DRIVER, Level.ALL);
        options.setCapability("goog:loggingPrefs", logs);
        options.addArguments("--remote-allow-origins=*");

        System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "build/chromedriver.log"); 
        driver = new ChromeDriver(options);

        driver.manage().window().maximize();
        wrappers = new Wrappers(driver);
    }
    

    @Test
    public void testCase01() throws InterruptedException {
        System.out.println("Start Test case: testCase01");
        driver.get("https://www.flipkart.com/");
        Thread.sleep(3000);
        wrappers.searchBox("Washing Machine");
        Thread.sleep(3000);
        wrappers.popularitySearch();
        Thread.sleep(3000);
        wrappers.countRatingsBelowOrEqual(4);
        System.out.println("End Test case: testCase01");
    }

    @Test
    public void testCase02() throws InterruptedException {
        System.out.println("Start Test case: testCase02");
        driver.get("https://www.flipkart.com/");
        Thread.sleep(3000);
        wrappers.searchBox("iPhone");
        Thread.sleep(3000);
        wrappers.productTitlesWithDiscountMore(17);
        System.out.println("End Test case: testCase02");
    }

    @Test
    public void testCase03() throws InterruptedException {
        System.out.println("Start Test case: testCase03");
        driver.get("https://www.flipkart.com/");
        Thread.sleep(3000);
        wrappers.searchBox("Coffee Mug");
        Thread.sleep(3000);
        wrappers.fourStarAndAbove();
        Thread.sleep(3000);
        wrappers.printTopFiveCofeeMugsByReview();
        System.out.println("End Test case: testCase03");
    }



    @AfterTest
    public void endTest()
    {
        driver.close();
        driver.quit();

    }
}