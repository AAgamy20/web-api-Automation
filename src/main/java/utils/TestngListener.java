package utils;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import org.openqa.selenium.WebDriver;
import org.testng.*;


public class TestngListener implements ISuiteListener, ITestListener, IInvokedMethodListener{

    @Override
    public void onStart(ISuite suite) {
        System.out.println("-------------------------------------------------------");
        System.out.println("Test Suite Started");
        System.out.println("-------------------------------------------------------");
        ExtentReport.initReports();

    }

    @Override
    public void onFinish(ISuite suite) {
        System.out.println("\n" + "-------------------------------------------------------");
        System.out.println("Test Suite Finished");
        System.out.println("-------------------------------------------------------" + "\n");
        ExtentReport.flushReports();
    }

    @Override
    public void onStart(ITestContext context) {
        System.out.println("\n" + "-----------------------------------------------" + "Test: ["
                + context.getName() + "] Started" + "-----------------------------------------------" + "\n");

    }

    @Override
    public void onFinish(ITestContext context) {
        System.out.println("\n" + "-----------------------------------------------" + "Test: ["
                + context.getName() + "] Finished" + "-----------------------------------------------" + "\n");

    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentReport.pass(MarkupHelper.createLabel(result.getMethod().getMethodName() + ": Passed", ExtentColor.GREEN));
    }

    @Override
    public void onTestFailure(ITestResult result) {
        // this part is tricky as I need to pass the driver I'm using on test execution to be here
        try {
            //Note in case of the driver was inherited please use get field instead of get declared field
            WebDriver driver = (WebDriver) result.getTestClass().getRealClass().getDeclaredField("driver").get(result.getInstance());
            System.out.println(driver.getTitle());
        if (driver != null) {
           // System.out.println(driver.getTitle()+"hiiiii");
           ExtentReport.fail(Logger.attachScreenshotToExtentReport(driver));
          // ExtentReport.attachScreenShot(Logger.getScreenshot(driver,result.getName()));
        }
        }catch (Exception E){
            E.printStackTrace();
        }
        ExtentReport.fail(MarkupHelper.createLabel(result.getMethod().getMethodName() + " Failed!", ExtentColor.RED));
        if (result.getThrowable() != null) {
            ExtentReport.fail(result.getThrowable());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentReport.skip(result.getMethod().getMethodName() + " Skipped");
        ExtentReport.skip(MarkupHelper.createLabel(result.getMethod().getMethodName() + " Skipped", ExtentColor.YELLOW));
        if (result.getThrowable() != null) {
            ExtentReport.skip(result.getThrowable());
        }
    }
    // Invocation better than test start or test finish
    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        ITestNGMethod testMethod = method.getTestMethod();
        if (testMethod.getDescription() != null && !testMethod.getDescription().equals("")) {
            ExtentReport.createTest(testMethod.getDescription());
        } else {
            ExtentReport.createTest(testResult.getName());
        }
        // for none testcase method setup or teardown
        System.out.println("\n" + "============================================================================================");
        if (method.isConfigurationMethod()) {
            System.out.println("Starting Configuration Method (Setup or Teardown): [" + testResult.getName() + "]");
            if (testMethod.getDescription() != null && !testMethod.getDescription().equals("")) {
                ExtentReport.removeTest(testMethod.getDescription());
            } else {
                ExtentReport.removeTest(testResult.getName());
            }
        } else {
            System.out.println("Starting Test Case: [" + testResult.getName() + "]");
        }
        System.out.println("============================================================================================" + "\n");
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        System.out.println("\n" + "===========================================================================================");
        if (method.isConfigurationMethod()) {
            System.out.println("Finished Configuration Method (Setup or Teardown): [" + testResult.getName() + "]");
        } else {
            System.out.println("Finished Test Case: [" + testResult.getName() + "]");
        }
        System.out.println("===========================================================================================" + "\n");
    }

}
