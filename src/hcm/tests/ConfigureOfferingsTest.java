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
		
		boolean isScrollingDown = true;
		boolean isNowClickable = false;
		boolean hasSetMainTask = false;
			
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
		Thread.sleep(1500);//3000
		//takeScreenshot();
			
		//Enable task
		int mainTask = 8;
		int mainStatus = 9;
		int subTask = 10;
		int status = 11;
		String mainRef = "afrrk";
		String subRef = "afrap";
		String mainTaskCommonPath, subTaskCommonPath;
		//NOTE: Use afrrk to identify a mainTask, succeeding subfolder use as afrap;;
		
		//Identifying referenceID for the Task group;;
		task.waitForElementToBeClickable(60, mainTask, subTask, "//table/tbody/tr/td/a/img[contains(@title,'View') or contains(@title, 'Feature')]");
		
		for(int testrow = 2; getExcelData(testrow, mainTask).length()>0;testrow++){
			System.out.println("\n***************\nNow peforming tests on Task: "+getExcelData(testrow ,mainTask));
			
				while(!is_element_visible("//tr/td/div/table/tbody/tr/td/div[text()='"+getExcelData(testrow ,mainTask)+"']", "xpath")){
					isScrollingDown = task.scrollDownToElement(isScrollingDown, mainTask);
				}
					String refID = task.findMainTaskUniqueID(testrow, mainTask);
				
				while (getExcelData(mainTask).length() > 0){
					System.out.println("\n***************\nNow peforming tests on subtask: "+getExcelData(testrow ,subTask));
					//Crack folder group
					//Initializing variable conditions;
					mainTaskCommonPath = "//tr[@_"+mainRef+"='"+refID+"']/td/div/table/tbody/tr/td/div[text()='"+getExcelData(testrow, mainTask)+"']";
					subTaskCommonPath  = "//tr[@_"+subRef+"='"+refID+"' or contains(@_"+subRef+",'"+refID+"_')]/td/div/table/tbody/tr/td/div[text() = '"+getExcelData(testrow, subTask)+"']";
					//System.out.println("subTaskCommonPath has been set to:" +subTaskCommonPath);
				
					
					//Revamping searching method....
					//Make sure that mainTask
					while(!is_element_visible(mainTaskCommonPath, "xpath") && !hasSetMainTask){
						isScrollingDown = task.scrollDownToElement(isScrollingDown, mainTask);
					}
					
					if(is_element_visible(mainTaskCommonPath, "xpath")){
						task.scrollElementIntoView(mainTaskCommonPath);
						task.clickMainTaskCheckbox(testrow, mainTask, mainTaskCommonPath);
						hasSetMainTask = true;
						if(getTextinElement(mainTaskCommonPath+"/../../td/span/a", "xpath").equalsIgnoreCase(getExcelData(testrow, mainStatus))){
							//takeScreenshot();
							System.out.println(getExcelData(testrow, mainTask)+" is already in "+getExcelData(testrow, mainStatus)+" status");
							log(getExcelData(testrow, mainTask)+" is already in "+getExcelData(testrow, mainStatus)+" status");
						}else {
							System.out.println("SubTask Status updates in progress....");
							task.changeSubTaskStatus(testrow, mainTask, subTask, mainStatus, mainTaskCommonPath);
							//takeScreenshot();
						}
					}
					
					//Expand main task folder
		 			if (is_element_visible(mainTaskCommonPath+"/span/a[contains(@title,'Expand')]", "xpath")){
						task.clickExpandFolder(mainTaskCommonPath);
						hasSetMainTask = true;
					}
					
					isScrollingDown = true;
					System.out.println("Subtask adjustments preparation in progress.....");
					//Ensure succeeding sub task will be visible;;
					while(!is_element_visible(subTaskCommonPath, "xpath") 
								&& (getExcelData(testrow, subTask).length()>0 
									&& getExcelData(testrow, status).length()>0)){
										
						System.out.println("adjustments in progresss.....");
						isScrollingDown = task.scrollDownToElement(isScrollingDown, subTask);
						
						if(is_element_visible(subTaskCommonPath, "xpath")){
							task.scrollElementIntoView(subTaskCommonPath);
						}
						//Experimental;;; BUGFIX: Test # 9: Comment if breaks other tests;;
						if(is_element_visible(subTaskCommonPath, "xpath")){
							task.scrollElementIntoView(subTaskCommonPath);
							break;
						}
					}
					
					//Adjust scroll bar to center the task
					if(is_element_visible(subTaskCommonPath, "xpath")){
						task.scrollElementIntoView(subTaskCommonPath);
					}
					
					//Expanding expandable folders
					if (is_element_visible(subTaskCommonPath+"/span/a[contains(@title,'Expand')]", "xpath")){
						System.out.println("found the inner directory...");
						task.clickExpandFolder(subTaskCommonPath);
					} 
					
					System.out.println("Preparing subtasks to be run....");
					if(getExcelData(testrow, subTask).length() > 0 && getExcelData(testrow, status).length() > 0){
				
						System.out.println("Customization of subtask in progress.......");
						if(getExcelData(testrow, mainTask).length() > 0){
							
							System.out.println("Ticking checkbox in progress.....");
							if(!is_element_visible(subTaskCommonPath+"/../../td/span/span/span/input[@checked='']" , "xpath")){
							
								task.clickSubTaskCheckbox(mainTask, subTask, subTaskCommonPath);
								Thread.sleep(1500); //3000
								task.clickSaveButton();
								Thread.sleep(2500); //3000
								//takeScreenshot();
												
							}else{
								
								if(!is_element_visible(subTaskCommonPath+"/../../td/span/span/span/input[@checked='']" , "xpath")){
									task.clickSubTaskCheckbox(mainTask, subTask, subTaskCommonPath);
									Thread.sleep(1500); //3000
									task.clickSaveButton();
									Thread.sleep(2500); //3000
									//takeScreenshot();
									break;				
								}
								
								System.out.println("updating task notes.....");
								//takeScreenshot();
								System.out.println(getExcelData(testrow, subTask)+" task is already enabled");
								log(getExcelData(testrow, subTask)+" task is already enabled");
							}	
							
							System.out.println("status adjustment in progress......");
							task.scrollElementIntoView(subTaskCommonPath);
							if(getTextinElement(subTaskCommonPath+"/../../td/span/a", "xpath").equalsIgnoreCase(getExcelData(testrow, status))){
												
								//takeScreenshot();
								System.out.println(getExcelData(testrow, subTask)+" is already in "+getExcelData(testrow, status)+" status");
								log(getExcelData(testrow, subTask)+" is already in "+getExcelData(testrow, status)+" status");
							} else {
								System.out.println("SubTask Status updates in progress....");
								task.changeSubTaskStatus(testrow, mainTask, subTask, status, subTaskCommonPath);
								//takeScreenshot();
							}
						
						}
					} else{
						
						break;
					}
					
					subTask += 2;
					status += 2;
				}
				
			subTask = 10;
			status = 11;
		}
				//Revised...2/26/2016
				
				Thread.sleep(1000);//1000
				//takeScreenshot();
				
				task.clickSaveAndCloseButton();
				
				System.out.println("Configuration Completed\n***************\n");
				log("Configuration Completed");
				
				Thread.sleep(1500);//3000
				//takeScreenshot();
						
			}
		 	  
}