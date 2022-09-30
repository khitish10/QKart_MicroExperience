package QKART_SANITY_LOGIN.Module1;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Checkout {
    RemoteWebDriver driver;
    String url = "https://crio-qkart-frontend-qa.vercel.app/checkout";

    public Checkout(RemoteWebDriver driver) {
        this.driver = driver;
    }

    public void navigateToCheckout() {
        if (!this.driver.getCurrentUrl().equals(this.url)) {
            this.driver.get(this.url);
        }
    }

    /*
     * Return Boolean denoting the status of adding a new address
     */
    public Boolean addNewAddress(String addresString) {
        try {
            /*
             * Click on the "Add new address" button, enter the addressString in the address
             * text box and click on the "ADD" button to save the address
             */
            driver.findElement(By.xpath("//button[text()='Add new address']")).click();
            Thread.sleep(1000);
            driver.findElement(By.xpath("//textarea[@placeholder='Enter your complete address']"))
                    .sendKeys(addresString);
            Thread.sleep(1000);
            driver.findElement(By.xpath("//button[text()='Add']")).click();
            return true;
        } catch (Exception e) {
            System.out.println("Exception occurred while entering address: " + e.getMessage());
            return false;

        }
    }

    /*
     * Return Boolean denoting the status of selecting an available address
     */
    public Boolean selectAddress(String addressToSelect) {
        try {
            /*
             * Iterate through all the address boxes to find the address box with matching
             * text, addressToSelect and click on it
             */
            if (addressToSelect.equalsIgnoreCase(driver
                    .findElement(By.xpath("//p[text()='Addr line 1 addr Line 2 addr line 3']"))
                    .getText())) {
                driver.findElement(By.xpath("//input[@name='address']")).click();
                return true;
                
            }
            System.out.println("Unable to find the given address");
            return false;
        } catch (Exception e) {
            System.out.println("Exception Occurred while selecting the given address: " + e.getMessage());
            return false;
        }

    }

    /*
     * Return Boolean denoting the status of place order action
     */
    public Boolean placeOrder() {
        try {
            String amt=driver.findElement(By.xpath("//div[@class='MuiBox-root css-1mqsyu2']")).getText();
		    // System.out.println(amt);
		    amt=amt.substring(1,amt.length());
		    // System.out.println(amt);
		    int num=Integer.parseInt(amt);
            if(num<5000){
                driver.findElement(By.xpath("//button[text()='PLACE ORDER']")).click();
            WebDriverWait wait = new WebDriverWait(driver,5);
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[text()='Order placed successfully!']")));
            return true;
            }
            driver.findElement(By.xpath("//button[text()='PLACE ORDER']")).click();
            return true;

        } catch (Exception e) {
            System.out.println("Exception while clicking on PLACE ORDER: " + e.getMessage());
            return false;
        }
    }

    /*
     * Return Boolean denoting if the insufficient balance message is displayed
     */
    public Boolean verifyInsufficientBalanceMessage() {
        try {
            String str = "You do not have enough balance in your wallet for this purchase";
            if (str.equalsIgnoreCase(driver.findElement(By.id("notistack-snackbar")).getText())) {
                return true;
            }
            return false;
        } catch (Exception e) {
            System.out.println("Exception while verifying insufficient balance message: " + e.getMessage());
            return false;
        }
    }
}
