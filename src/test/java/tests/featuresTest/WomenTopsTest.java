package tests.featuresTest;
import org.testng.asserts.SoftAssert;
import pages.HomePage;
import org.testng.annotations.*;
import pages.WomenTeesPage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import listeners.MyListeners;
import tests.base.BaseDriver;

@Listeners(MyListeners.class)
public class WomenTopsTest extends BaseDriver {
    SoftAssert softAssert = new SoftAssert();
    @BeforeClass
    public void setup(){
        driver = baseDriver();
        HomePage homePage = new HomePage(driver);
        homePage.clickWomenTops();
    }
    @Test
    public void TC1_SortByPriceAsc(){
        WomenTeesPage womenTeesPage = new WomenTeesPage(driver);
        womenTeesPage.sortBy("Price");
        womenTeesPage.setSortOrder("asc");
        List<Float> prices = womenTeesPage.getProductPrices();
        List<Float> sortedPrices = new ArrayList<>(prices);
        Collections.sort(sortedPrices);
        softAssert.assertEquals(prices, sortedPrices);
        softAssert.assertEquals(driver.getCurrentUrl(),"https://magento.softwaretestingboard.com/women/tops-women.html?product_list_order=price");
        softAssert.assertAll();
    }
    @Test
    public void TC2_SortByProductNameAsc(){
        WomenTeesPage womenTeesPage = new WomenTeesPage(driver);
        womenTeesPage.sortBy("Product Name");
        womenTeesPage.setSortOrder("asc");
        List<String> names = womenTeesPage.getProductNames();
        List<String> sortedNames = new ArrayList<>(names);
        Collections.sort(sortedNames);
        softAssert.assertEquals(names, sortedNames);
        softAssert.assertEquals(driver.getCurrentUrl(),"https://magento.softwaretestingboard.com/women/tops-women.html?product_list_order=name");
        softAssert.assertAll();
    }
    @Test
    public void TC3_SortByPriceDesc(){
        WomenTeesPage womenTeesPage = new WomenTeesPage(driver);
        womenTeesPage.sortBy("Price");
        womenTeesPage.setSortOrder("desc");
        List<Float> prices = womenTeesPage.getProductPrices();
        List<Float> sortedPrices = new ArrayList<>(prices);
        sortedPrices.sort(Collections.reverseOrder());
        softAssert.assertEquals(prices, sortedPrices);
        String currentUrl = driver.getCurrentUrl();
        softAssert.assertTrue(currentUrl.contains("product_list_dir=desc"));
        softAssert.assertTrue(currentUrl.contains("product_list_order=price"));
        softAssert.assertAll();
    }
    @Test
    public void TC4_SortByProductNameDesc(){
        WomenTeesPage womenTeesPage = new WomenTeesPage(driver);
        womenTeesPage.sortBy("Product Name");
        womenTeesPage.setSortOrder("desc");
        List<String> names = womenTeesPage.getProductNames();
        List<String> sortedNames = new ArrayList<>(names);
        sortedNames.sort(Collections.reverseOrder());
        softAssert.assertEquals(names, sortedNames);
        String currentUrl = driver.getCurrentUrl();
        softAssert.assertTrue(currentUrl.contains("product_list_dir=desc"));
        softAssert.assertTrue(currentUrl.contains("product_list_order=name"));
        softAssert.assertAll();
    }

    @AfterClass
    public void quit(){
        quitDriver();
    }
}
