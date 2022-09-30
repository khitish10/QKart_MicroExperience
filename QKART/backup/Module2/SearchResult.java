package QKART_SANITY_LOGIN.Module1;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SearchResult {
    WebElement parentElement;
    WebDriver driver;


    public SearchResult(WebElement SearchResultElement) {
        this.parentElement = SearchResultElement;
        
    }

    /*
     * Return title of the parentElement denoting the card content section of a
     * search result
     */
    public String getTitleofResult(WebDriver driver) {
        String titleOfSearchResult = "";
        // TODO: CRIO_TASK_MODULE_TEST_AUTOMATION - TEST CASE 03: MILESTONE 1
        // Find the element containing the title (product name) of the search result and
        // assign the extract title text to titleOfSearchResult
        titleOfSearchResult=
                driver.findElement(By.xpath("//p[contains(@class,'MuiTypography-root MuiTypography-body1 css-yg30e6')]")).getText();
        return titleOfSearchResult;
    }

    /*
     * Return Boolean denoting if the open size chart operation was successful
     */
    public Boolean openSizechart(WebDriver driver) {
        try {

            // TODO: CRIO_TASK_MODULE_TEST_AUTOMATION - TEST CASE 04: MILESTONE 2
            // Find the link of size chart in the parentElement and click on it
            driver.findElement(By.xpath("//button[text()='Size chart']")).click();
            Thread.sleep(1000);
            boolean val=driver.findElement(By.xpath("//p[contains(@class,'MuiTypography-root MuiTypography-body1 t')]")).isDisplayed();
            if (val) {
                return true;
            }
            return false;
        } catch (Exception e) {
            System.out.println("Exception while opening Size chart: " + e.getMessage());
            return false;
        }
    }

    /*
     * Return Boolean denoting if the close size chart operation was successful
     */
    public Boolean closeSizeChart(WebDriver driver) {
        try {
            Thread.sleep(2000);
            Actions action = new Actions(driver);

            // Clicking on "ESC" key closes the size chart modal
            action.sendKeys(Keys.ESCAPE);
            action.perform();
            Thread.sleep(2000);
            return true;
        } catch (Exception e) {
            System.out.println("Exception while closing the size chart: " + e.getMessage());
            return false;
        }
    }

    /*
     * Return Boolean based on if the size chart exists
     */
    public Boolean verifySizeChartExists(WebDriver driver) {
        Boolean status = false;
        try {
            // TODO: CRIO_TASK_MODULE_TEST_AUTOMATION - TEST CASE 04: MILESTONE 2
            /*
             * Check if the size chart element exists. If it exists, check if the text of
             * the element is "SIZE CHART". If the text "SIZE CHART" matches for the
             * element, set status = true , else set to false
             */
            //System.out.println(driver.findElement(By.xpath("//p[text()='Roadster Mens Running Shoes']")).getText());
            WebElement size_chart=driver.findElement(By.xpath("//button[text()='Size chart']"));
            if (size_chart.isDisplayed()) {
                String size_text = driver
                        .findElement(By.xpath("//button[contains(text(),'Size chart')]")).getText();
                if (size_text.equalsIgnoreCase("Size chart")) {
                            status = true;
                        }
                
          }
            return status;
        } catch (Exception e) {
            return status;
        }
    }

    /*
     * Return Boolean if the table headers and body of the size chart matches the
     * expected values
     */
    public Boolean validateSizeChartContents(List<String> expectedTableHeaders, List<List<String>> expectedTableBody,
            WebDriver driver) {
        Boolean status = false;
        try {
            // TODO: CRIO_TASK_MODULE_TEST_AUTOMATION - TEST CASE 04: MILESTONE 2
            /*
             * Locate the table element when the size chart modal is open
             * 
             * Validate that the contents of expectedTableHeaders is present as the table
             * header in the same order
             * 
             * Validate that the contents of expectedTableBody are present in the table body
             * in the same order
             */
            //Verify Headers 
            int count=0;
            List<WebElement> ls = driver.findElements(
                    By.xpath("//tr[@class='MuiTableRow-root MuiTableRow-head css-mnddxn']/th"));
            int countHeader = 0;
            for (int i = 0; i < ls.size(); i++) {

                if ((ls.get(i).getText()).equalsIgnoreCase(expectedTableHeaders.get(i))) {
                    countHeader++;
                }
            }
            //Verify Body
            int countBody = 0;
            String temp = "";
            for (int c1 = 0; c1 < 7; c1++) {
                for (int r1 = 0; r1 < 4; r1++) {

                    temp = expectedTableBody.get(c1).get(r1);
                    String s = driver.findElement(
                            By.xpath("//tbody[contains(@class,'MuiTableBody-root css-1xnox0e')]/tr["
                                    + (c1 + 1) + "]/td[" + (r1 + 1) + "]"))
                            .getText();

                    if (temp.equalsIgnoreCase(s)) {
                        countBody++;
                    }

                }
            }
            if (countHeader == 4 && countBody == 28) {
                return true;         
             }
            return status;

        } catch (Exception e) {
            System.out.println("Error while validating chart contents");
            return false;
        }
    }

    /*
     * Return Boolean based on if the Size drop down exists
     */
    public Boolean verifyExistenceofSizeDropdown(WebDriver driver) {
        Boolean status = false;
        try {
            // TODO: CRIO_TASK_MODULE_TEST_AUTOMATION - TEST CASE 04: MILESTONE 2
            // If the size dropdown exists and is displayed return true, else return false
            if (driver.findElement(By.xpath("//select[@name='age']")).isDisplayed()) {
                status = true;
            }
            return status;
        } catch (Exception e) {
            return status;
        }
    }
}