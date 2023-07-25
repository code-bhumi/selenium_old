package selenium_framework.TestComponent;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.selenium_framework.pageobjects.LandingPage;
import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {
    
    public WebDriver driver;
    public WebDriver initializeDriver()   throws IOException
        {
            Properties prop = new Properties();
            FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"/src/main/java/com/selenium_framework/selenium_framework.Properties/GlobalData.Properties");
            prop.load(fis);    
            String browserName = prop.getProperty("browser"); 

            if(browserName.equalsIgnoreCase("chrome"))
            {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();           
            }
            else if (browserName.equalsIgnoreCase("firefox"))
            {
                // firefox
            }
            else if (browserName.equalsIgnoreCase("edge")) 
            {
                // edge
            }           

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            driver.manage().window().maximize(); 
            return driver;
        }

    public List<HashMap<String, String>>getJsonDataToMap(String filePath)   throws IOException
    {
        String jsonContent = FileUtils.readFileToString(new File(filePath), StandardCharsets.UTF_8);
    
        ObjectMapper mapper = new ObjectMapper();

        List<HashMap<String, String>> data = mapper.readValue(jsonContent, new TypeReference<List<HashMap<String, String>>>() {});       
        return data;

    
    }

    @BeforeMethod(alwaysRun = true)
    public LandingPage launchApplication()   throws IOException
    {
        driver = initializeDriver();
        LandingPage landingPage = new LandingPage(driver);
        landingPage.goTo();
        return landingPage;       
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown()
    {
        driver.close();
    }
    
}

////src//main//java//com//selenium_framework//selenium_framework.Properties//GlobalData.Properties")