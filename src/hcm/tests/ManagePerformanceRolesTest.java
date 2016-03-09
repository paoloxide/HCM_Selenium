package hcm.tests;

import org.testng.annotations.Test;


import common.BaseTest;
import hcm.pageobjects.FuseWelcomePage;
import hcm.pageobjects.LoginPage;
import hcm.pageobjects.TaskListManagerTopPage;

import static util.ReportLogger.logFailure;

public class ManagePerformanceRolesTest extends BaseTest {
	
	@Test
	public void a_test() throws Exception  {
		testReportFormat();
	
	try{
		managePerf();
	  
	  	}
	
        catch (AssertionError ae)
        {
            takeScreenshot();
            logFailure(ae.getMessage());

            throw ae;
        }
        catch (Exception e)
        {
            takeScreenshot();
            logFailure(e.getMessage());

            throw e;
        }
    }

	public void managePerf() throws Exception{
			
		LoginPage login = new LoginPage(driver);
		takeScreenshot();
		login.enterUserID(5);
		login.enterPassword(6);
		login.clickSignInButton();
		
		FuseWelcomePage welcome = new FuseWelcomePage(driver);
		takeScreenshot();
		welcome.clickNavigator("More...");
		clickNavigationLink("Setup and Maintenance");
		
		TaskListManagerTopPage task = new TaskListManagerTopPage(driver);
		takeScreenshot();
		
		task.clickTask("Manage Implementation Projects");
		Thread.sleep(3000);
		takeScreenshot();
		
		startTableRep("Validation of the Current Screen");
		assertTextInElementPresent("//td[2]/div/h1", "Manage Implementation Projects");
		assertPageElementPresent("//span/div/div[2]", "Search");
		assertPageElementPresent("//span/div[2]/div[2]", "Search Results");
		endTableRep();
				
		task.enterSearch(7);
		takeScreenshot();
		
		task.clickSearchButton();
		takeScreenshot();
		
		clickNavigationLink(7);
		Thread.sleep(3000);
		takeScreenshot();
		
		startTableRep("Validation of the Current Screen");
		assertTextInElementPresent("//td[2]/div/h1", "Implementation Project: "+getExcelData(7));
		assertTextInElementPresent("//div[2]/table/tbody/tr/td[2]/div/table/tbody/tr/td/div", 8);
		assertTextInElementPresent("//tr[2]/td[2]/div/table/tbody/tr/td", 9);
		assertTextInElementPresent("//tr[3]/td[2]/div/table/tbody/tr/td/div", 10);
		endTableRep();
		
		task.clickExpandWorkForceDevelopment();
		Thread.sleep(3000);
		takeScreenshot();
		
		startTableRep("Validation of the Tasks");
		assertTextInElementPresent("//table[2]/tbody/tr/td[2]/div/table/tbody/tr/td/div", 11);
		assertTextInElementPresent("//table[2]/tbody/tr[2]/td[2]/div/table/tbody/tr/td/div", 12);
		assertTextInElementPresent("//table[2]/tbody/tr[3]/td[2]/div/table/tbody/tr/td/div", 13);
		assertTextInElementPresent("//table[2]/tbody/tr[4]/td[2]/div/table/tbody/tr/td/div", 14);
		assertTextInElementPresent("//table[2]/tbody/tr[5]/td[2]/div/table/tbody/tr/td/div", 15);
		endTableRep();
		
		
		task.clickDoneButton();
		takeScreenshot();
		
		task.clickDoneButton();
		takeScreenshot();
		
		task.enterSearchName(16);
		takeScreenshot();
		
		task.clickSearchButton();
		takeScreenshot();
		
		task.clickGoToTaskIcon();
		Thread.sleep(5000);
		takeScreenshot();
		
		startTableRep("Validate the Current Screen");
		assertTextInElementPresent("//td[2]/div/h1", 17);
		endTableRep();
		
		task.clickCreateIcon();
		Thread.sleep(3000);
		takeScreenshot();
		
		startTableRep("Validate the Current Screen");
		assertTextInElementPresent("//td[2]/div/h1", 18);
		endTableRep();
		
		task.enterRole(19);
		task.enterDescription(20);
		task.enterFromDate(todaysDate());
		task.enterToDate(tomorrowsDate());
		task.selectStatus(21);
		task.clickSaveAndCloseButton();
		
		takeScreenshot();
		waitForElement("//*[@id='d1::msgDlg']", "xpath");
		
		startTableRep("Validate the changes are saved");
		assertTextInElementPresent("//*[@id='d1::msgDlg::_cnt']/div/table/tbody/tr/td/table/tbody/tr/td[2]/div", "Your changes were saved.");
		endTableRep();
	
		task.clickOkButton();
		
		
		}
		 	  
	  }

