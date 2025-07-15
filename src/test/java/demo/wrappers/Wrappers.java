package demo.wrappers;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Wrappers {
    /*
     * Write your selenium wrappers here
     */

    private WebDriver driver;

    public Wrappers(WebDriver driver) {
        this.driver = driver;
    }

    public void searchBox(String searchText) {
        try {
            WebElement searchbox = driver.findElement(By.xpath("//input[@type='text']"));
            searchbox.sendKeys(searchText, Keys.ENTER);
            System.out.println("Test step: Search text entered successfully");
        } catch (Exception e) {
            System.out.println("Error occured " + e.getMessage());
        }
    }

    public void popularitySearch() {
        try {
            WebElement popularityOption = driver.findElement(By.xpath("//div[contains(text(), 'Popularity')]"));
            popularityOption.click();
            System.out.println("Test step: popularity option selected successfully");
        } catch (Exception e) {
            System.out.println("Error occured " + e.getMessage());
        }
    }

    public void countRatingsBelowOrEqual(int ratings) {
        List<WebElement> ratingElements = driver.findElements(By.xpath("//span[contains(@id, 'productRating')]"));
        int count = 0;
        for (WebElement ratingElement : ratingElements) {
            String text = ratingElement.getText().trim();
            if (!text.isEmpty()) {
                double rating = Double.parseDouble(text);
                if (rating <= ratings) {
                    count++;
                }
            }
        }
        System.out.println("Test step: The count of items with rating less than or equal to " + ratings + " stars is " + count);
    }

    public void productTitlesWithDiscountMore(int discountPercent) {

        List<WebElement> productParentElements = driver.findElements(By.xpath("//div[contains(@class, 'tUxRFH')]"));
        
        

        for (WebElement product : productParentElements) {
            try {
             String title = product.findElement(By.xpath(".//div[contains(@class, 'KzDlHZ')]")).getText();
             String discountText = product.findElement(By.xpath(".//span[contains(text(), '%')]")).getText();
            

            int discountValue = Integer.parseInt(discountText.replace("% off", " ").trim());
        
                if (discountValue > discountPercent) {
                    System.out.println("Test step: Product Title & Discount % of item with more than 17% discount is : " + title + ", Discount: " + discountValue + "%");
                }

            } catch (Exception e) {
                System.out.println("Test step: Error parsing discount value for product: " + " Error: " + e.getMessage());
            }
        }

    }

    public void fourStarAndAbove() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement fourStar = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//label[contains(@class, 'tJjCVx _3DvUAf')]//div[contains(@class, 'XqNaEv')])[1]")));
            

            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", fourStar);
            Thread.sleep(3000);
            fourStar.click();
            System.out.println("Test step: 4 star and above filter applied successfully");
        } catch (Exception e) {
            System.out.println("Test step: Error occurred while applying 4 star and above filter: " + e.getMessage());
        }
    }

    public void printTopFiveCofeeMugsByReview() {
        List<WebElement> products = driver.findElements(By.xpath("//div[contains(@class, '_75nlfW')]//div[contains(@class, 'slAVV4')]"));

        List<Map<String, String>> productInfo = new ArrayList<>();

        for (WebElement product : products) {
            try {
                String title = product.findElement(By.xpath(".//a[contains(@class, 'wjcEIp')]")).getText();
                String imageUrl = product.findElement(By.xpath(".//img")).getAttribute("src");
                String reviewText = product.findElement(By.xpath(".//span[contains(@class, 'Wphh3N')]")).getText();
                int count = extractReviewCount(reviewText);
                if (count > 0) {
                    Map<String, String> info = new HashMap<>();
                    info.put("title", title);
                    info.put("imageUrl", imageUrl);
                    info.put("count", String.valueOf(count));
                    productInfo.add(info);
                }


            } catch (Exception e) {
                System.out.println("Test step: Error occured while processing product: " + e.getMessage());
            }
        }
        System.out.println("Test step: Product details added to the list successfully");
        // sort reviews by descending order
        productInfo.sort((a, b) -> Integer.compare(
                Integer.parseInt(b.get("count")),
                Integer.parseInt(a.get("count"))));
        System.out.println("Test step: Top 5 coffee mugs by review count:");

        for (int i = 0; i < Math.min(5, productInfo.size()); i++) {
            Map<String, String> info = productInfo.get(i);
            System.out.println("Title: " + info.get("title") + ", Image URL: " + info.get("imageUrl") + ", Reviews: "
                    + info.get("count"));
        }
    }

    private int extractReviewCount(String reviewText) {
        try {
            String cleaned = reviewText.replaceAll(",", "").replaceAll("[^0-9]", " ");
            return Integer.parseInt(cleaned.trim());
        } catch (Exception e) {
            return 0;
        }
    }

}
