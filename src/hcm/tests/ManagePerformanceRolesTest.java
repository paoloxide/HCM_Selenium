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
            //takeScreenshot();
            logFailure(ae.getMessage());

            throw ae;
        }
        catch (Exception e)
        {
            //takeScreenshot();
            logFailure(e.getMessage());

            throw e;
        }
    }

	public void managePerf() throws Exception{
			
		LoginPage login = new LoginPage(driver);
		//takeScreenshot();
		login.enterUserID(5);
		login.enterPassword(6);
		login.clickSignInButton();
		
		FuseWelcomePage welcome = new FuseWelcomePage(driver);
		//takeScreenshot();
		welcome.clickNavigator("More...");
		clickNavigationLink("Setup and Maintenance");
		
		TaskListManagerTopPage task = new TaskListManagerTopPage(driver);
		//takeScreenshot();
		
		task.clickTask("Manage Implementation Projects");
		Thread.sleep(3000);
		//takeScreenshot();
		
		/*startTableRep("Validation of the Current Screen");
		assertTextInElementPresent("//td[2]/div/h1", "Manage Implementation Projects");
		assertPageElementPresent("//span/div/div[2]", "Search");
		assertPageElementPresent("//span/div[2]/div[2]", "Search Results");
		endTableRep();*/
				
		//task.enterSearch(7);
		//takeScreenshot();
		
		//task.clickSearchButton();
		//takeScreenshot();
		
		//clickNavigationLink(7);
		//Thread.sleep(3000);
		//takeScreenshot();
		
		/*startTableRep("Validation of the Current Screen");
		assertTextInElementPresent("//td[2]/div/h1", "Implementation Project: "+getExcelData(7));
		assertTextInElementPresent("//div[2]/table/tbody/tr/td[2]/div/table/tbody/tr/td/div", 8);
		assertTextInElementPresent("//tr[2]/td[2]/div/table/tbody/tr/td", 9);
		assertTextInElementPresent("//tr[3]/td[2]/div/table/tbody/tr/td/div", 10);
		endTableRep();*/
		
		//task.clickExpandWorkForceDevelopment();
		//Thread.sleep(3000);
		//takeScreenshot();
		
		/*startTableRep("Validation of the Tasks");
		assertTextInElementPresent("//table[2]/tbody/tr/td[2]/div/table/tbody/tr/td/div", 11);
		assertTextInElementPresent("//table[2]/tbody/tr[2]/td[2]/div/table/tbody/tr/td/div", 12);
		assertTextInElementPresent("//table[2]/tbody/tr[3]/td[2]/div/table/tbody/tr/td/div", 13);
		assertTextInElementPresent("//table[2]/tbody/tr[4]/td[2]/div/table/tbody/tr/td/div", 14);
		assertTextInElementPresent("//table[2]/tbody/tr[5]/td[2]/div/table/tbody/tr/td/div", 15);
		endTableRep();*/
		
		
		//task.clickDoneButton();
		//takeScreenshot();
		
		//task.clickDoneButton();
		//takeScreenshot();
		
		//task.enterSearchName(16);
		//takeScreenshot();
		
		//task.clickSearchButton();
		//takeScreenshot();
		
		//task.clickGoToTaskIcon();
		//Thread.sleep(5000);
		//takeScreenshot();
		
		//startTableRep("Validate the Current Screen");
		//assertTextInElementPresent("//td[2]/div/h1", 17);
		//endTableRep();
		
		task.clickCreateIcon();
		//Thread.sleep(3000);
		//takeScreenshot();
		
		/*startTableRep("Validate the Current Screen");
		assertTextInElementPresent("//td[2]/div/h1", 18);
		endTableRep();*/
		
		//task.enterRole(19);
		//task.enterDescription(20);
		//task.enterFromDate(todaysDate());
		//task.enterToDate(tomorrowsDate());
		//task.selectStatus(21);
		//task.clickSaveAndCloseButton();
		
		//takeScreenshot();
		//waitForElement("//*[@id='d1::msgDlg']", "xpath");
		
		//startTableRep("Validate the changes are saved");
		//assertTextInElementPresent("//*[@id='d1::msgDlg::_cnt']/div/table/tbody/tr/td/table/tbody/tr/td[2]/div", "Your changes were saved.");
		//endTableRep();
	
		//task.clickOkButton();
		
		task.waitForElementToBeClickable(15,1,1,"//button[text()='Ne']");
		task.clickNextButton();
		task.clickNextButton();
		
		int expectedTaskValue = 8;
		int expandableFolders = 8;
		boolean isScrollingDown = true;
		boolean hasExpandedAll = false;
		
		task.waitForElementToBeClickable(15,1,1,"//div[@class='x159' and text()='"+getExcelData(expectedTaskValue)+"']");
		
		while (getExcelData(expectedTaskValue).length() > 0){
			String commonPath = "//div[@class='x159' and text()='"+getExcelData(expectedTaskValue)+"']";
			System.out.println("Now Ticking..."+getExcelData(expectedTaskValue));
			
			//Expand All visible(actually first visible) expandable folder;;;
			while(is_element_visible("//div[@class='x159']/span/a[contains(@title,'Expand')]","xpath") 
					&& getExcelData(expandableFolders).length() > 0 && !hasExpandedAll){

				System.out.println("Expanding All expandable folders...");
				
				task.scrollDownToElement(true, 0);
				isScrollingDown = task.scrollDownToElement(isScrollingDown, expandableFolders);
				task.scrollElementIntoView(expandableFolders);
				if(is_element_visible("//div[@class='x159' and text()='"+getExcelData(expectedTaskValue)+"']/span/a[contains(@title,'Expand')]","xpath")) task.clickExpandFolder(expandableFolders);
				expandableFolders += 1;
			}
			
			if(getExcelData(expectedTaskValue).contentEquals("Workforce Development") || getExcelData(expectedTaskValue).contentEquals("Workforce Deployment")){
				task.scrollDownToElement(true, -2);}
			
			else if(getExcelData(expectedTaskValue).contentEquals("Compensation Management")){
				task.scrollDownToElement(false, -1);}
			
			//Scroll down to the expectedTask;
			if(!is_element_visible(commonPath,"xpath")){
				System.out.println("Making "+getExcelData(expectedTaskValue)+" visible.");
				isScrollingDown = task.scrollDownToElement(isScrollingDown, expectedTaskValue);
				if(is_element_visible(commonPath, "xpath")){
					task.scrollElementIntoView(expectedTaskValue);
				}	
			}
			
			//Ticking checkbox of all targets
			System.out.println("Ticking Target: "+getExcelData(expectedTaskValue));
			if(!is_element_visible(commonPath+"/../../td/span/span/span/input[@checked='']" , "xpath")){

						System.out.println("Check presence before ticking...");
						//updated from if;;;
						if(!is_element_visible(commonPath,"xpath")){
							System.out.println("Now Searching for: "+getExcelData(expectedTaskValue));
							isScrollingDown = task.scrollDownToElement(isScrollingDown, expectedTaskValue);
						}
						task.scrollElementIntoView(expectedTaskValue);
						System.out.println("ELEMENT FOUND: Proceed to ticking...");
						task.clickSubTaskCheckbox(expectedTaskValue, commonPath);
						Thread.sleep(5000);
			}
		
			expectedTaskValue += 1;
			hasExpandedAll = true;
		}
		
		task.clickSaveandOpenProject();
		task.waitForElementToBeClickable(15,1,1,"//button[text()='D']");
		task.clickDoneButton();
		
	} 	  
}

