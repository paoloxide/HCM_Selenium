package hcm.tests;

import org.testng.annotations.Test;


import common.BaseTest;
import hcm.pageobjects.FuseWelcomePage;
import hcm.pageobjects.LoginPage;
import hcm.pageobjects.TaskListManagerTopPage;
import static util.ReportLogger.log;
import static util.ReportLogger.logFailure;

public class ConfigureOfferingsTest extends BaseTest {
	
	@Test
	public void a_test() throws Exception  {
		testReportFormat();
	
	try{
		configureOfferings();
	  
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

	public void configureOfferings() throws Exception{
			
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
		
		task.clickTask("Configure Offerings");
		Thread.sleep(3000);
		//takeScreenshot();
		
		
		//Expand folder
		if (is_element_visible("//tbody/tr/td/div[contains(text(),'"+getExcelData(7)+"')]/span/a[contains(@title,'Expand')]", "xpath")){
			
		task.clickExpandFolder(7);
		} 
		
		//Enable task
		
		int mainTask = 7;
		int subTask = 8;
		int status = 9;
		
		while (getExcelData(mainTask).length() > 0){
			
			if(getExcelData(subTask).length() > 0 && getExcelData(status).length() > 0){
		
				if(getExcelData(mainTask).length() > 0){
			
					Thread.sleep(3000);
					if(!is_element_visible("//tbody/tr/td/div[contains(text(),'"+getExcelData(mainTask)+"')]/../../../../../../../../tr/td/div/table/tbody/tr/td/div[contains(text(),'"+getExcelData(subTask)+"')]/../../td/span/span/span/input[@checked='']" , "xpath")){
						
						task.clickSubTaskCheckbox(mainTask, subTask);
						Thread.sleep(3000);
						task.clickSaveButton();
						Thread.sleep(5000);
						//takeScreenshot();
										
					} else{
										
						//takeScreenshot();
						System.out.println(getExcelData(subTask)+" task is already enabled");
						log(getExcelData(subTask)+" task is already enabled");
					}
					
					if(getTextinElement("//tbody/tr/td/div[contains(text(),'"+getExcelData(mainTask)+"')]/../../../../../../../../tr/td/div/table/tbody/tr/td/div[contains(text(),'"+getExcelData(subTask)+"')]/../../td/span/a", "xpath").equalsIgnoreCase(getExcelData(status))){
										
						//takeScreenshot();
						System.out.println(getExcelData(subTask)+" is already in "+getExcelData(status)+" status");
						log(getExcelData(subTask)+" is already in "+getExcelData(status)+" status");
					} else {
						
						task.changeSubTaskStatus(mainTask, subTask, status);
						//takeScreenshot();
					}
				
				}
			} else{
				
				break;
			}
			
			subTask += 2;
			status += 2;
		}		
		
		Thread.sleep(1000);
		//takeScreenshot();
		
		task.clickSaveAndCloseButton();
		
		System.out.println("Configuration Completed");
		log("Configuration Completed");
		
		Thread.sleep(3000);
		//takeScreenshot();
				
		}
		 	  
	  }

