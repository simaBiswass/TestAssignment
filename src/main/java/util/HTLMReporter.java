package util;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.CodeLanguage;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.Base64;
import java.util.Objects;

public class HTLMReporter {

    private static ExtentReports extentReports;
    private static String reportPath;
    private static final String fileSeparator = FileSystems.getDefault().getSeparator();
    private static final ThreadLocal<ExtentTest> tlTest = new ThreadLocal<>();

    /**
     * Initializes the ExtentReports instance and configures the report path and settings.
     *
     * <p>This method checks if an ExtentReports instance already exists. If not, it creates one,
     * sets the report path based on a custom or default location, and ensures the necessary directory
     * structure is present. It also attaches an {@code ExtentSparkReporter} for generating the report
     * and loads additional configuration from an XML file.</p>
     */
    public static void startReport() {
        ExtentSparkReporter html;
        String USER_DIRECTORY = "user.dir";
        if (Objects.isNull(extentReports)) {
            extentReports = new ExtentReports();
            if (System.getProperty("ReportPath") != null) {
                System.out.println("Custom Report Path: " + System.getProperty("ReportPath"));
                reportPath = System.getProperty("ReportPath");
            } else {
                reportPath = System.getProperty(USER_DIRECTORY) + fileSeparator + "Results" + fileSeparator + "Report.html";
                System.out.println("Default Report Path: " + reportPath);
            }
            // Ensure Results directory exists
            File resultsDir = new File(System.getProperty(USER_DIRECTORY) + fileSeparator + "Results");
            if (!resultsDir.exists()) {
                resultsDir.mkdirs();
            }
        }
        html = new ExtentSparkReporter(reportPath);
        try {
            html.loadXMLConfig(System.getProperty(USER_DIRECTORY) + fileSeparator + "extent-config.xml");
        } catch (IOException e) {
            System.err.println("Failed to load XML Config: " + e.getMessage());
        }
        extentReports.attachReporter(html);
        extentReports.setSystemInfo("AUT", String.valueOf(SetProperties.getProperties().getProperty("Amazon_URL")));
    }

    /**
     * Creates and initializes a new test case in the ExtentReports instance.
     *
     * <p>This method creates a test case with the specified name and description
     * and associates it with the current thread using a {@code ThreadLocal} object.</p>
     *
     * @param testCaseName the name of the test case
     * @param testDescription a brief description of the test case
     */
    public void startTestCase(String testCaseName, String testDescription){
        ExtentTest test = extentReports.createTest(testCaseName, testDescription);
        tlTest.set(test);
    }

    /**
     * Logs a message and its associated status to the ExtentReports instance.
     *
     * <p>This method adds a log entry to the current test case with the specified description,
     * JSON content, and status. Depending on the status, it also captures a screenshot
     * and attaches it to the report.</p>
     *
     * @param driver the WebDriver instance for capturing screenshots
     * @param desc the description of the log entry
     * @param json the JSON content to be included in the log
     * @param status the status of the log entry (e.g., PASS, FAIL, WARNING, SKIP)
     */
    public static void reportLog(WebDriver driver, String desc, String json, Status status){
        Markup m = MarkupHelper.createCodeBlock(json, CodeLanguage.JSON);
        switch (status) {
            case PASS:
                attachSSToReport("Pass test",driver);
                tlTest.get().pass(desc);
                break;
            case FAIL:
                attachSSToReport("Fail test",driver);
                tlTest.get().fail(desc);
                break;
            case WARNING:
                tlTest.get().info(desc);
                tlTest.get().warning(m);
                break;
            case SKIP:
                tlTest.get().info(desc);
                tlTest.get().skip(m);
                break;
            default:
                tlTest.get().info(desc);
        }
    }
    /**
     * Captures a screenshot and attaches it to the current test case in the report.
     *
     * @param ssName the name or description of the screenshot
     * @param driver the WebDriver instance used for capturing the screenshot
     */
    public static void attachSSToReport(String ssName, WebDriver driver){
        String strBase64 = captureSS(driver);
        tlTest.get().info(ssName, MediaEntityBuilder.createScreenCaptureFromBase64String(strBase64).build());
    }
    /**
     * Captures a screenshot from the WebDriver instance and encodes it as a Base64 string.
     *
     * <p>The captured screenshot is converted to Base64 format for easy embedding in the ExtentReports
     * instance. If the capture fails, a log entry is added to indicate the failure.</p>
     *
     * @param driver the WebDriver instance used for capturing the screenshot
     * @return the Base64-encoded string representation of the screenshot
     */
    public static String captureSS(WebDriver driver){
        String encodedBase64 = null;
        if(null != driver){
            File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            try {
                FileInputStream fileInputStream = new FileInputStream(scrFile);
                byte[] bytes = new byte[(int) scrFile.length()];
                fileInputStream.read(bytes);
                encodedBase64 = new String(Base64.getEncoder().encode(bytes));
            } catch (IOException e){
                reportLog(driver,"Screenshot Capture unsuccessful","",Status.INFO);
            }
        }
        return "data:image/png;base64,"+encodedBase64;
    }
    /**
     * Finalizes the ExtentReports instance and flushes all pending logs to the report.
     *
     * <p>This method ensures that all logs are written to the report file and clears
     * the {@code ThreadLocal} reference to the current test case.</p>
     */
    public void endResult(){
        if(Objects.nonNull(extentReports))
            extentReports.flush();
        tlTest.remove();
    }

}
