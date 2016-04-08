package hcm.tests.case1;

import static util.ReportLogger.log;
import static util.ReportLogger.logFailure;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import common.BasePage;
import common.BaseTest;
import common.BooleanCustomRunnable;
import common.CustomRunnable;
import common.DuplicateEntryException;
import common.TaskUtilities;
import hcm.pageobjects.FuseWelcomePage;
import hcm.pageobjects.LoginPage;
import hcm.pageobjects.TaskListManagerTopPage;

public class CreateImplementationProjectTest extends BaseTest{
	private static final int MAX_TIME_OUT = 30;
	private int inputLabel = 10;
	private int inputs = 11;
	private int colNum = 7;
	private int projectCol = 7;
	private String type, labelTag, taskUniqueId;
	private String projectName = "";
	private boolean expanded = true;
	
	private String labelLocator, labelLocatorPath, dataLocator, dataLocatorPath, dataToUpperCase;
	
	@Test
	public void a_test() throws Exception  {
		testReportFormat();
	
	try{
		createProject();
	  
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
	
	public void createProject() throws Exception{
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
		
		TaskUtilities.customWaitForElementVisibility("//a[text()='Manage Implementation Projects']", MAX_TIME_OUT);
		TaskUtilities.jsFindThenClick("//a[text()='Manage Implementation Projects']");
		TaskUtilities.customWaitForElementVisibility("//h1[text()='Manage Implementation Projects']", MAX_TIME_OUT);
		
		TaskUtilities.jsFindThenClick("//a/img[@title='Create']");
		TaskUtilities.customWaitForElementVisibility("//h1[contains(text(),'Create Implementation Project')]", MAX_TIME_OUT);
		
		try{
				createImplementation(task);
			} catch(DuplicateEntryException e){
				//Skips
			}
		
		Thread.sleep(5000);
		log("Department Creation has been finished...");
		System.out.print("Department Creation has been finished...");
	}
	
	public void createImplementation(TaskListManagerTopPage task) throws Exception{
		String nextLabelLocator, nextLabelLocatorPath;
		if(colNum <1) colNum = 7;
		
		//Set All Data Values
		setdataloop:
		while(getExcelData(inputLabel, colNum, "text").length() > 0){
			labelLocator = getExcelData(inputLabel, colNum, "text");
			labelLocator = TaskUtilities.filterDataLocator(labelLocator);
			labelLocatorPath = TaskUtilities.retryingSearchInput(labelLocator);
			labelTag = driver.findElement(By.xpath(labelLocatorPath)).getTagName();
			//Define input value
			type = TaskUtilities.getdataLocatorType(labelLocator);
			dataLocator = getExcelData(inputs, colNum, type);
			
			if(!labelTag.contentEquals("select")){
					TaskUtilities.consolidatedInputEncoder(task, labelLocatorPath, dataLocator);
				} else if(labelTag.contentEquals("select") && labelLocator.contentEquals("Assigned To")){
					dataToUpperCase = dataLocator.toUpperCase();
					dataLocatorPath = labelLocatorPath+"/option[@title='Search']";
					TaskUtilities.retryingFindClick(By.xpath(dataLocatorPath));
					TaskUtilities.customWaitForElementVisibility("//div[text()='Search and Select: User']", MAX_TIME_OUT);
					String selectLabel = "E-Mail";
					String selectLabelPath = TaskUtilities.retryingSearchInput(selectLabel);
					
					TaskUtilities.consolidatedInputEncoder(task, selectLabelPath, dataLocator);
					driver.findElement(By.xpath(selectLabelPath)).sendKeys(Keys.ENTER);
					TaskUtilities.jsFindThenClick("//button[text()='Search']");
					try{
							TaskUtilities.fluentWaitForElementInvisibility("//div[text()='No search conducted.']", "No search conducted.", MAX_TIME_OUT);
							Thread.sleep(1000);
							
						} catch(Exception e){
							TaskUtilities.jsFindThenClick("//button[text()='Reset']");
							Thread.sleep(750);
							TaskUtilities.consolidatedInputEncoder(task, selectLabelPath, dataLocator);
							TaskUtilities.jsFindThenClick("//button[text()='Search']");
						}
					
					//TaskUtilities.customWaitForElementVisibility("//td[@title='"+dataLocator+"']", MAX_TIME_OUT);
					TaskUtilities.retryingFindClick(By.xpath("//div/span[text()='E-Mail']"));
					TaskUtilities.retryingFindClick(By.xpath("//td[@title='"+dataLocator+"']"));
					TaskUtilities.jsFindThenClick("//button[text()='OK']");
					TaskUtilities.customWaitForElementVisibility("//select[@title='"+dataToUpperCase+"']", MAX_TIME_OUT);			
					
				} else {
					TaskUtilities.consolidatedInputSelector(labelLocatorPath, dataLocator);
				}
			
			nextLabelLocator = getExcelData(inputLabel, colNum+1, "text");
			nextLabelLocator = TaskUtilities.filterDataLocator(nextLabelLocator);
			if(nextLabelLocator.contentEquals("Status")){
				colNum += 2;
				System.out.println("I will skip this sheet....");
				continue setdataloop;
			}
			nextLabelLocatorPath = TaskUtilities.retryingSearchInput(nextLabelLocator);
			if(nextLabelLocatorPath == null || nextLabelLocatorPath.isEmpty()){ 
				colNum += 1;
				break setdataloop;
			}
			
			colNum += 1;
		}
		//
		TaskUtilities.jsFindThenClick("//button[text()='Ne']");
		if(projectName == null || projectName.isEmpty()){
			projectName = getExcelData(inputs, projectCol, "text");
		}
		TaskUtilities.customWaitForElementVisibility("//td[text()='"+projectName+"']", MAX_TIME_OUT, new CustomRunnable() {
			
			@Override
			public void customRun() throws Exception {
				// TODO Auto-generated method stub
				TaskUtilities.jsCheckMissedInput();
				TaskUtilities.jsCheckMessageContainer();
			}
		});
		
		//Iterate Over Tasks to implement
		while(getExcelData(inputLabel, colNum, "text").length()>0){
			labelLocator = getExcelData(inputLabel, colNum, "text");
			dataLocator = getExcelData(inputs, colNum, "text");
			
			if(dataLocator.equalsIgnoreCase("Yes")){
				TaskUtilities.jsFindThenClick("//div[text()='"+labelLocator+"']");
				Thread.sleep(500);
				TaskUtilities.retryingFindClick(By.xpath("//img[@title='Show as Top']/..[@href='#']"));
				TaskUtilities.retryingFindClick(By.xpath("//div[text()='"+labelLocator+"']"));
				TaskUtilities.customWaitForElementVisibility("//a[@title='Show Hierarchy']", MAX_TIME_OUT);
				
				String uniqueID = task.findMainTaskUniqueID(inputLabel, colNum);
				int attempts = 0;
				/*tring taskPath = "//div[text()='"+labelLocator+"']";
				String expansionPath = "//a[@title='Expand']";
				
				//Expands Main Task folder...
				if(is_element_visible(taskPath+expansionPath, "xpath")){
					TaskUtilities.jsFindThenClick(taskPath+"//a");
					TaskUtilities.customWaitForElementVisibility(taskPath+"//a[@title='Collapse']", MAX_TIME_OUT);
				}
				//Then tick the main task checkbox...
				System.out.println("Now ticking the checkbox...");
				if(!is_element_visible(taskPath+"/../..//input[@checked='']", "xpath")){
					TaskUtilities.jsFindThenClick(taskPath+"/../..//input");
					Thread.sleep(750);
				}*/

				System.out.println("Checking all visible folder");
				while(expanded){
					expanded = false;
					//Expands Expandable Sub Tasks...
					System.out.println("Querying all visible folder");
					java.util.List<WebElement> queryFolder = driver.findElements(By.xpath("//table[contains(@summary, 'Offerings')]/tbody/tr"));
					String comPath, afrap ="", afrrk="";
					
					for(WebElement elem: queryFolder){
					while(attempts < 3){
						try{
								afrrk = elem.getAttribute("_afrrk");
								afrap = elem.getAttribute("_afrap");
							} catch(StaleElementReferenceException e ){
								//Skips the process
								attempts += 1;
							}
					}
					attempts = 0;
						
						if(afrap == null || afrap.isEmpty()){
								comPath = "//tr[@_afrrk='"+afrrk+"']";
								TaskUtilities.retryingFindClick(By.xpath(comPath));
							} else {
								comPath = "//tr[@_afrrk='"+afrrk+"' and @_afrap='"+afrap+"']";
								TaskUtilities.retryingFindClick(By.xpath(comPath));
							}
						
						TaskUtilities.jsScrollIntoView(comPath);
						//Tick checkbox...
						if(!is_element_visible(comPath+"//input[@checked='']", "xpath")){
							System.out.println("Ticking all visible folder");
							TaskUtilities.retryingFindClick(By.xpath(comPath+"//input"));
						}
						//Expands if expandable...
						if(is_element_visible(comPath+"//a[@title='Expand']", "xpath")){
							System.out.println("Expanding all visible expandable folder");
							TaskUtilities.retryingFindClick(By.xpath(comPath+"//a"));
							TaskUtilities.customWaitForElementVisibility(comPath+"//a[@title='Collapse']", MAX_TIME_OUT);
							expanded = true;
						}
					}
					if(expanded) TaskUtilities.scrollDownToElement(true, "small");
				}
				
				/*String [][] subTaskfolder = task.queryAllsubtaskFolder(uniqueID);
				int count = 0;
				while(subTaskfolder.length > count){
					String subTask = subTaskfolder[count][0];
					String subTaskPath = "//div[text()='"+subTask+"']";
					TaskUtilities.jsScrollIntoView(subTaskPath);
					if(is_element_visible(subTaskPath+expansionPath, "xpath")){
						TaskUtilities.jsFindThenClick(subTaskPath+"//a");
						TaskUtilities.customWaitForElementVisibility(subTaskPath+"//a[@title='Collapse']", MAX_TIME_OUT);

					}
					if(!is_element_visible(subTaskPath+"/../..//input[@checked='']", "xpath")){
						TaskUtilities.jsFindThenClick(subTaskPath+"/../..//input");
						Thread.sleep(750);
					}
					count += 1;
				}*/
				
			}
			
			colNum += 1;
		}
	}
	
}
