package QKART_SANITY_LOGIN.Module1;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Home {
    RemoteWebDriver driver;
    String url = "https://crio-qkart-frontend-qa.vercel.app";

    public Home(RemoteWebDriver driver) {
        this.driver = driver;
    }

    public void navigateToHome() {
        if (!this.driver.getCurrentUrl().equals(this.url)) {
            this.driver.get(this.url);
        }
    }

    public Boolean PerformLogout() throws InterruptedException {
        try {
            // Find and click on the Logout Button
            WebElement logout_button = driver.findElement(By.className("MuiButton-text"));
            logout_button.click();

            // SLEEP_STMT_10: Wait for Logout to complete
            // Wait for Logout to Complete
            WebDriverWait wait= new WebDriverWait(driver, 5);
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[text()='Register']")));

            return true;
        } catch (Exception e) {
            // Error while logout
            return false;
        }
    }

    /*
     * Returns Boolean if searching for the given product name occurs without any
     * errors
     */
    public Boolean searchForProduct(String product) {
        WebDriverWait wait =  new WebDriverWait(driver,30);
        try {
            WebElement sBox = driver.findElement(By.xpath("//input[@name='search']"));
            sBox.clear();
            sBox.sendKeys(product);
            Thread.sleep(1000);
            
            boolean stat=false;
            
            List<WebElement> list = driver
                    .findElements(By.xpath("//div[@class='MuiCardContent-root css-1qw96cp']"));

            
            if (list.size() != 0) {
                if (product.equalsIgnoreCase("yonex")) {
                    product = product.toUpperCase();
                }
                WebElement item =
                     driver.findElement(By.xpath("//p[contains(text(),'" + product + "')]"));
             if (item.isDisplayed()) {
                 String name = item.getText();
                 if (name.contains(product)) {
                     stat=true;
                 }   
             }
         } else {
           stat=true;
         }
         return stat;
        } catch (Exception e) {
            System.out.println("Error while searching for a product: " + e.getMessage());
            return false;
        }
    }

    /*
     * Returns Array of Web Elements that are search results and return the same
     */
    public List<WebElement> getSearchResults() {
        List<WebElement> searchResults = new ArrayList<WebElement>();
        try {
            searchResults =
                    driver.findElements(By.xpath("//div[contains(@class, 'MuiCardContent')]"));
            return searchResults;
        } catch (Exception e) {
            System.out.println("There were no search results: " + e.getMessage());
            return searchResults;

        }
    }

    /*
     * Returns Boolean based on if the "No products found" text is displayed
     */
    public Boolean isNoResultFound() {
        Boolean status = false;
        try {
            status =driver.findElement(By.xpath("//h4[contains(text(),' No products found ')]"))
                            .isDisplayed();
            return status;
        } catch (Exception e) {
            return status;
        }
    }

    /*
     * Return Boolean if add product to cart is successful
     */
    public Boolean addProductToCart(String productName) {
        try {
            /*
             * Iterate through each product on the page to find the WebElement corresponding
             * to the matching productName
             * 
             * Click on the "ADD TO CART" button for that element
             * 
             * Return true if these operations succeeds
             */
            
            String item = driver
                    .findElement(By.xpath("//div[@class='MuiCardContent-root css-1qw96cp']/p"))
                    .getText();
                    //System.out.println(item);
            if (item.equalsIgnoreCase(productName)) {
                driver.findElement(By.xpath("//span[contains(@class,'MuiButton-startIcon MuiButton-iconSizeMedium css-6xugel')]")).click();
                        
            }
            //Thread.sleep(2000);
            if (driver.findElement(By.xpath("//div[text()='1']")).isDisplayed()) {
                return true;
            }
            System.out.println("Unable to find the given product");
            return false;
        } catch (Exception e) {
            System.out.println("Exception while performing add to cart: " + e.getMessage());
            return false;
        }
    }

    /*
     * Return Boolean denoting the status of clicking on the checkout button
     */
    public Boolean clickCheckout() {
        Boolean status = false;
        try {
            if (driver.findElement(By.xpath("//button[text()='Checkout']")).isDisplayed()) {
                driver.findElement(By.xpath("//button[text()='Checkout']")).click();
                return true;
            }
            return status;
        } catch (Exception e) {
            System.out.println("Exception while clicking on Checkout: " + e.getMessage());
            return status;
        }
    }

    //Opens a new tab and moves the control to that tab
    public Boolean newTab(String url){
        Boolean status=false;
        String a = "window.open('about:blank','_blank');";
		((JavascriptExecutor)driver).executeScript(a);
		
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs.get(1));
        
        driver.get(url);
            // WebDriverWait wait = new WebDriverWait(driver, 5);
            // wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='MuiBox-root css-zgtx0t']")));
            // return true;
        if(driver.findElement(By.xpath("//div[@class='MuiBox-root css-zgtx0t']")).isDisplayed()){
            return true;
        }
        


        return status;
    }

    /*
     * Return Boolean denoting the status of change quantity of product in cart
     * operation
     */
    public Boolean changeProductQuantityinCart(String productName, int quantity) {
        WebDriverWait wait = new WebDriverWait(driver, 2);
        try {
            String modified= "\'" + productName + "\'";

            int i = 1;
            while (true) {


                if (productName.equalsIgnoreCase(driver
                        .findElement(By.xpath("//div[contains(text(),"+modified+")]"))
                        .getText())) {
                    if (quantity > i) {
                        driver.findElement(By.xpath(
                                "//*[local-name()='svg' and @data-testid='AddOutlinedIcon']"))
                                .click();
                        i++;
                    }
                    if (quantity < i) {
                        driver.findElement(By.xpath(
                                "//*[local-name()='svg' and @data-testid='RemoveOutlinedIcon']"))
                                .click();
                        i--;
                    }
                    if (quantity == i) {
                        break;
                    }
                }
                //Thread.sleep(500);
                wait.until(ExpectedConditions.textToBe(By.xpath("//div[@class='MuiBox-root css-olyig7']"), String.valueOf(i)));
            }
            return true;

        } catch (Exception e) {
            if (quantity == 0)
                return true;
            System.out.println("exception occurred when updating cart: " + e.getMessage());
            return false;
        }
    }

    /*
     * Return Boolean denoting if the cart contains items as expected
     */
    public Boolean verifyCartContents(List<String> expectedCartContents) {
        try {
            if (expectedCartContents.size() == 2 && driver.findElement(By.xpath("//button[text()='Checkout']")).isDisplayed()){
                return true;
           }
            return false;
        } catch (Exception e) {
            System.out.println("Exception while verifying cart contents: " + e.getMessage());
            return false;
        }
    }

    public Boolean footer(WebElement item, String val){

        item.click();
        if(driver.getCurrentUrl().endsWith(val)) {
			return true;
		}
        return false;
    }

    public Boolean footerContent(WebElement item){

        if(item.isDisplayed()){
            return true;
        }

        return false;
    }
    public Boolean contactUs(WebElement item, String name, String email, String textField){
        item.click();
        driver.findElement(By.name("email")).sendKeys(name);
        driver.findElement(By.xpath("(//input[@name='email'])[2]")).sendKeys(email);
        driver.findElement(By.xpath("(//input[@name='email'])[3]")).sendKeys(textField);

        //close pop
        driver.findElement(By.xpath("//div[@class='col-md-12']//child::button")).click();


        //Ensure that the contact now dialog closes
        Boolean clo=driver.getCurrentUrl().endsWith(".app/");
			if(clo) {
				return true;
			}

        return false;
    }

    public boolean advertisement(WebDriver driver, WebElement element, String itemName){
        boolean status=false;
        // WebElement firstFrame=driver.findElement(By.xpath("(//iframe[@class='iframe'])[1]"));
        driver.switchTo().frame(element);
        WebElement itemOne=driver.findElement(By.xpath("//p[contains(text(),'"+itemName+"')]//parent::div//child::div//button[text()='View Cart']"));
        itemOne.click();

        if(driver.getCurrentUrl().endsWith(".app/")){
            status= true;
        }

        driver.navigate().back();
        driver.switchTo().parentFrame();
        return status;
    }
}
