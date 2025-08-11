package tests.featuresTest;
import org.testng.asserts.SoftAssert;
import pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.*;
import listeners.MyListeners;
import tests.base.BaseDriver;
import utility.LogsUtility;

@Listeners(MyListeners.class)
public class SearchTest extends BaseDriver {
    private HomePage homePage;
    SoftAssert softAssert = new SoftAssert();
    @BeforeClass
    public void setup(){
        driver = baseDriver();
        homePage = new HomePage(driver);
    }
    @Test (priority = 0)
    public void TC1_validSearch(){
        homePage.setSearchInput("Tee");
        homePage.clickSearchIcon();
        LogsUtility.info("Validate correct product list");
        softAssert.assertTrue(homePage.validateSearchResults("Tee"), "'Tee' should be in the search results");
        LogsUtility.info("Validate search url");
        softAssert.assertEquals(driver.getCurrentUrl(),"https://magento.softwaretestingboard.com/catalogsearch/result/?q=Tee");
        softAssert.assertAll();
    }
    @Test
    public void TC2_NoResultSearch(){
        homePage.clearSearchField();
        homePage.setSearchInput("lamp");
        homePage.clickSearchIcon();
        LogsUtility.info("Validate search for non existing products return nothing");
        Assert.assertEquals(homePage.getNoResultsMsg(),"Your search returned no results.");
    }
    @AfterClass
    public void quit(){
        quitDriver();
    }
}
