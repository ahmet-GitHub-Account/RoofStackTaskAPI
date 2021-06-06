package com.roofStacks.utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.sun.tools.xjc.Driver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;

import java.io.IOException;

import static io.restassured.RestAssured.baseURI;

public class TestBase {

    protected static ExtentReports report;
    protected static ExtentHtmlReporter htmlReporter;
    protected static ExtentTest extentLogger;


    @BeforeClass
    public void beforeClass() {
        baseURI = ConfigurationReader.getProperty("base_URI");
    }


    @BeforeTest
    public void setUpTest() {
        report = new ExtentReports();

        String projectPath = System.getProperty("user.dir");
        String path = projectPath + "/test-output/report.html";

        htmlReporter = new ExtentHtmlReporter(path);

        report.attachReporter(htmlReporter);

        htmlReporter.config().setReportName("RoofStacks API Test");

        report.setSystemInfo("Environment", "QA");
        report.setSystemInfo("OS", System.getProperty("os.name"));

    }

    // ITestResult class describe the result of a test in TestNG
    @AfterMethod
    public void tearDown(ITestResult result) {
        // if test fails
        if (result.getStatus() == ITestResult.FAILURE) {
            // record the name of failed test case
            extentLogger.fail(result.getName());
        }

    }

    @AfterTest
    public void tearDownTest() {
        //this is when the report is actually created
        report.flush();
    }

}