package hcm.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.TimeoutException;

import common.BasePage;
import common.BaseTest;

import static common.BaseTest.TestCaseRow;
import static common.ExcelUtilities.getCellData;
import static util.ReportLogger.log;

//public static final scrollerID = "pt1:USma:0:MAnt1:1:pt1:r1:0:ap1:ATT1:_ATTp:tt1::scroller";
//public static final scrollerID2 = "pt1:USma:0:MAnt1:1:pt1:r1:0:ap1:APpg1s";
//public static final divTaskClass = "x159";

public class TaskListManagerTopPage extends BasePage{
	
	public TaskListManagerTopPage(WebDriver driver){
		super(driver);
	}
	
	@Override
    public String getPageId()
    {
        return "/setup/faces/TaskListManagerTop";
    }

	public void clickTask(String menu){
		
		clickByXpath("//li/a[contains(text(),'"+ menu +"')]");		
		log("Clicking " + menu +"...");
		System.out.println("Clicking " + menu +"...");

	}
	
	public void enterSearch(int colNum) throws Exception{
		String value = getCellData(TestCaseRow, colNum);
		enterText("pt1:USma:0:MAnt1:1:pt1:r1:0:resultAppPanel:q1:value00::content", value); //
		log("Entered in the Search box...");
		System.out.println("Entered in the Search box...");
	}
	
	public void clickSearchButton(){
		
		clickByXpath("//button[text()='Search']");		
		log("Clicking Search button...");
		System.out.println("Clicking Search button...");

	}
	
	public void clickNextButton(){
		
		clickByXpath("//button[text()='Ne']");		
		log("Clicking Next button...");
		System.out.println("Clicking Next button...");

	}
	
	public void clickExpandFolder(int colNum) throws Exception{
		
		String folder = getCellData(TestCaseRow, colNum);
		clickByXpath("//tbody/tr/td/div[text() = '"+folder+"']/span/a");
		Thread.sleep(3000);	
		log("Clicking expand...");
		System.out.println("Clicking expand...");
	}
	
	public void clickCollapseFolder(int colNum) throws Exception{
		
		String folder = getCellData(TestCaseRow, colNum);
		clickByXpath("//tbody/tr/td/div[text() = '"+folder+"']/span/a");
		Thread.sleep(3000);	
		log("Clicking collapse...");
		System.out.println("Clicking collapse...");
	}
	
	public void clickSaveandOpenProject() throws Exception{
		clickByXpath("//span[text()='Save and Open Project']");
		Thread.sleep(3000);	
		log("Saving Project...");
		System.out.println("Saving Project...");
		
	}
	
	public boolean scrollDownToElement(boolean isScrollingDown, int taskNum) throws Exception{
			
		System.out.println("Initializing scroll down....");
		int scrollValue = 150;
		//String folder = getCellData(TestCaseRow, taskNum);
		if(taskNum == -1) scrollValue = 25;
		else if(taskNum == -2) scrollValue = 1000;
		else{scrollValue = 150;}
		System.out.println("Scroll is now moving....");	
		JavascriptExecutor js = (JavascriptExecutor)driver;
		boolean scrollDownAgain = (boolean) js.executeScript(/* 
			"var scrollerID =  'pt1:USma:0:MAnt1:1:pt1:r1:0:ap1:ATT1:_ATTp:tt1::scroller';"	+
			"var scrollerID2 = 'pt1:USma:0:MAnt1:1:pt1:r1:0:ap1:APpg1s';"					+
			"var divScroller = document.getElementById(scrollerID);"						+
			"var outerdivScroller = document.getElementsByTagName('div')[scrollerID2];"		+
			"if( outerdivScroller != undefined )"											+
			"	outerdivScroller.scrollTop = outerdivScroller.scrollHeight;"				+
			"if(divScroller != undefined){"													+
			"	if("+isScrollingDown+")divScroller.scrollTop += 150;"						+
			"	else if(!"+isScrollingDown+"){divScroller.scrollTop = 0;}"					+
			"	if(divScroller.scrollTop >= divScroller.scrollHeight * 0.85)return false;"	+
			"}return true;" */	
				
			"taskFolderArray=[];"+
			"taskFolderInt = -255;"+
			"queryFolderName = [];"+
			"queryFolderName = document.querySelectorAll('div');"+

			"for(var i=0; i<queryFolderName.length;i++){"+
			"	curFolderId = queryFolderName[i].id;"+
			"	curFolderId1 = queryFolderName[i].style.overflow;"+
			"	curFolderId2 = queryFolderName[i].style.position;"+
			"	if(taskFolderInt < 0)taskFolderInt = -1;"+
			"	if((curFolderId1 === 'auto' && curFolderId2 === 'absolute') || curFolderId.contains('scroller')){"+
			"		taskFolderInt += 1;	"+
			"		taskFolderArray[taskFolderInt] = [curFolderId, curFolderId1, curFolderId2];"+
			"}}"+
      
			"for(var j =0; j<taskFolderArray.length;j++){"+
			"	  newScroller = document.getElementById(taskFolderArray[j][0]);"+
			"	  if(newScroller.scrollTop != undefined){"+
			"			if("+isScrollingDown+") newScroller.scrollTop += "+scrollValue+";"+
			"			else if(!"+isScrollingDown+") newScroller.scrollTop = 0;"+
			"			if(newScroller.scrollTop >= newScroller.scrollHeight * 0.85"+
							"&& taskFolderArray[j][0].contains('scroller'))"+
								"return false;"+
			"	  		}"+
			"}return true;"
		);
		//SLOW INTERNET CONNECTION might REQUIRE -- Higher Wait time: Recommended(5*2)
		driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
		WebDriverWait waitLoadHandler = new WebDriverWait(driver, 5);
		try{
			waitLoadHandler.until(ExpectedConditions.invisibilityOfElementWithText(By.xpath("//*[@id='pt1:USma:0:MAnt1:1:pt1:r1:0:ap1:ATT1:_ATTp:tt1::sm']"), "Fetching Data..."));
		}catch(TimeoutException e){
			e.printStackTrace();
		}
		return scrollDownAgain;
	}
	
	public void scrollElementIntoView(int colNum) throws Exception{
		
		String folder = getCellData(TestCaseRow, colNum);
		System.out.println("Centering view.....");
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript(
			"	taskFolderArray=[];"									+
			"	taskFolderInt = -255;"									+
			"	queryFolderName = [];"									+
			"	scrollerFolderArray = [];"								+
			"	scrollerFolderInt = 0;"									+
			"	queryFolderName = document.querySelectorAll('div');"	+

			"for(var i=0; i<queryFolderName.length;i++){"				+
			"	curFolderId  = queryFolderName[i].className;"			+
			"	curFolderId1 = queryFolderName[i].textContent;"			+
			"	curFolderId2 = queryFolderName[i].style.overflow;"		+
			"	curFolderId3 = queryFolderName[i].style.position;"		+
			"	curFolderId4 = queryFolderName[i].id;"					+
			"	if(taskFolderInt < 0)taskFolderInt = -1;"				+
			"	if(curFolderId1 == '"+folder+"'){"						+
			"		taskFolderInt += 1;	"								+
			"		taskFolderArray[taskFolderInt] = [curFolderId1, curFolderId];"			+
			"	}if(curFolderId2 == 'auto' && curFolderId3 == 'absolute'){"					+
			"		scrollerFolderArray[scrollerFolderInt] = curFolderId4;"					+
			"		scrollerFolderInt += 1;}"												+
			"}"																				+
			
			//Originally harcoded;;;
			"divTaskClass = taskFolderArray[0][1];"											+
			//Upgraded to softcode;;
			"scrollerID2 = ''+scrollerFolderArray[0];"										+
			"outerdivScroller = document.getElementsByTagName('div')[scrollerID2];"			+
			"if( outerdivScroller != undefined )"											+
			"	outerdivScroller.scrollTop = outerdivScroller.scrollHeight;"				+
			"divTasks = document.getElementsByClassName(divTaskClass);"						+
			"for(var i =0; i < divTasks.length; i++){"										+
			"	if(divTasks[i].textContent === '"+folder+"')"								+
			"		divTasks[i].scrollIntoView(false);"										+ 
			"	}"
		);
	}
	
	public void waitForElementToBeClickable(int waitTime, int colNum, int subCol) throws Exception{
		
		String folder = getCellData(TestCaseRow, colNum);
		String subfolder = getCellData(TestCaseRow, subCol);
		WebDriverWait wait = new WebDriverWait(driver, waitTime);
		try{
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//tbody/tr/td/div[text() = '"+folder+"']/../../../../../../../../tr/td/div/table/tbody/tr/td/div[text() = '"+subfolder+"']")));
		}catch(TimeoutException e){
			System.out.println("Waiting for element has timed out... Trying alternative method.");
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//tbody/tr/td/div[text() = '"+folder+"']/../../../../../../../../tr/td/div/table/tbody/tr/td/div[text() = '"+subfolder+"']")));
			System.out.println("Workaround: SUCCESSFUL");
		}
		System.out.println("Check element presence is now finised...");
	}
	
	public void waitForElementToBeClickable(int waitTime, int colNum, int subCol, String elementPath) throws Exception{
		
		String folder = getCellData(TestCaseRow, colNum);
		String subfolder = getCellData(TestCaseRow, subCol);
		WebDriverWait wait = new WebDriverWait(driver, waitTime);
		try{
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath(elementPath)));
		}catch(TimeoutException e){
			System.out.println("Waiting for element has timed out... No alternative method available.");
		}
		System.out.println("Check element presence is now finised...");
	}
	
	//Added 2/28/2016...not yet done. Working as of 2/29/2016
	
	public String findMainTaskUniqueID(int colNum)throws Exception{
		
		System.out.println("Cracking Folder Schema....");
		String folder = getCellData(TestCaseRow, colNum);
		
		JavascriptExecutor js = (JavascriptExecutor)driver;
		
		String mainTaskUniqueID = (String)js.executeScript(
		
			"taskFolderArray=[];"+
			"taskFolderInt = -255;"+
			"queryFolderName = [];"+
			"queryFolderName = document.querySelectorAll('tr');"+
			"cInt = 0;"+
			"retArray = [];"+

			"for(var i=0; i<queryFolderName.length;i++){"+
			"	curFolderId = queryFolderName[i].attributes._afrap;"+
			"	curFolderId1 = queryFolderName[i].attributes._afrrk;"+
			"	if(taskFolderInt < 0)taskFolderInt = -1;"+
			"	if(curFolderId === undefined){"+
			"		taskFolderInt += 1;	"+
			"		taskFolderArray[taskFolderInt] = [queryFolderName[i].textContent, curFolderId, curFolderId1];"+
			"		}}"+

			"for(var j=0; j<taskFolderArray.length;j++){"+
			"	if(taskFolderArray[j][2] != undefined && taskFolderArray[j][0].contains('"+folder+"')){"+
			"	  retArray[cInt] = taskFolderArray[j];"+
			"	  return retArray[0][2].textContent}}"
			);
			
		System.out.println("UniqueID is now....."+mainTaskUniqueID);
		return mainTaskUniqueID;
	}
	
	public void clickMainTaskCheckbox(int colNum) throws Exception{
		
		String folder = getCellData(TestCaseRow, colNum);
		clickByXpath("//tbody/tr/td/div[text() = '"+folder+"']/../../td/span/span/span/input");
		log("Selecting "+folder+" task...");
		System.out.println("Selecting "+folder+" task...");
	}
	
	public void clickSubTaskCheckbox(int colNum, String commonPath) throws Exception{
		
		String task = getCellData(TestCaseRow, colNum);
		
		clickByXpath(commonPath+"/../../td/span/span/span/input");	
		
		log("Selecting "+task+" sub task...");
		System.out.println("Selecting sub "+task+" task...");
	}
	
	public void clickSubTaskCheckbox(int colNum, int colNum2) throws Exception{
		
		String folder = getCellData(TestCaseRow, colNum);
		String subfolder = getCellData(TestCaseRow, colNum2);
		
		/* if (""+folder == "Workforce Development"){//Employing Remedy for Test 18+19.. will be updated as new table schema is done.
			clickByXpath("//tr[@_afrrk='178']/td/div[text() = '"+folder+"']/../../../../../../../../../../table/tbody/tr/td/div/table/tbody/tr/td/div[text() = '"+subfolder+"']/../../td/span/span/span/input");	
			return ;
		}
		//clickByXpath("//tbody/tr/td/div[text() = '"+folder+"']/../../../../../../../../tr/td/div/table/tbody/tr/td/div[text() = '"+subfolder+"']/../../td/span/span/span/input");
		//Experimental;;; BUGFIX FOR Test 9: revert to above code if breaks others;;;
		try{
			clickByXpath("//tbody/tr/td/div[text() = '"+folder+"']/../../../../../../../../tr/td/div/table/tbody/tr/td/div[text() = '"+subfolder+"']/../../td/span/span/span/input");
		}catch(TimeoutException err1){
			try{
				System.out.println("search for element checkbox has timed out...trying other methods");
				clickByXpath("//tbody/tr/td/div[text() = '"+folder+"']/../../../../../../../../../../table/tbody/tr/td/div/table/tbody/tr/td/div[text() = '"+subfolder+"']/../../td/span/span/span/input");
				System.out.println("Workaround: SUCCESSFUL");
			}catch(TimeoutException err2){	
					System.out.println("UNABLE TO LOCATE/TICK CHECKBOX...\n"+err2);
					log(""+err2);
			}
		} */
		
		//clickByXpath(commonPath+"/../../td/span/span/span/input");	
		
		log("Selecting "+folder+" sub task...");
		System.out.println("Selecting sub "+folder+" task...");
	}
	
	public void clickSubTaskCheckbox(int colNum, int colNum2, String commonPath) throws Exception{
		
		String folder = getCellData(TestCaseRow, colNum);
		String subfolder = getCellData(TestCaseRow, colNum2);
		
		clickByXpath(commonPath+"/../../td/span/span/span/input");	
		
		log("Selecting "+folder+" sub task...");
		System.out.println("Selecting sub "+folder+" task...");
	}
	
	
	public void changeSubTaskStatus(int colNum, int colNum2, int colNum3) throws Exception{
		
		String folder = getCellData(TestCaseRow, colNum);
		String subfolder = getCellData(TestCaseRow, colNum2);
		//Employing Remedy for Test 18+19.. will be updated as soon as new table schema is done.
		/* if(""+folder == "Workforce Development"){
			System.out.println("applying Hotfix/Remedy....");
			clickByXpath("//tr[@_afrrk='178']/td/div[text() = '"+folder+"']/../../../../../../../../../../table/tbody/tr/td/div/table/tbody/tr/td/div[text() = '"+subfolder+"']/../../td/span/a");
			return ;
		}
		
		//clickByXpath("//tbody/tr/td/div[text() = '"+folder+"']/../../../../../../../../tr/td/div/table/tbody/tr/td/div[text() = '"+subfolder+"']/../../td/span/a");
		//Experimental;;; BUGFIX FOR Test 9: revert to above code if breaks others;;;
		try{
			clickByXpath("//tbody/tr/td/div[text() = '"+folder+"']/../../../../../../../../tr/td/div/table/tbody/tr/td/div[text() = '"+subfolder+"']/../../td/span/a");			
		
		}catch(TimeoutException err){	
			try{
				System.out.println("search for element status has timed out...trying other methods");
				clickByXpath("//tbody/tr/td/div[text() = '"+folder+"']/../../../../../../../../../../table/tbody/tr/td/div/table/tbody/tr/td/div[text() = '"+subfolder+"']/../../td/span/a");
				System.out.println("Workaround: SUCCESSFUL");
			}catch(TimeoutException err2){					
					System.out.println("UNABLE TO LOCATE STATUS BOX...\n"+err2);
					log(""+err2);
			}
		} */
		
		//clickByXpath(commonPath+"/../../td/span/a");
			
		
		log("Clicked "+folder+" subtask status...");
		System.out.println("Clicked "+folder+" subtask status...");
		
		String status = getCellData(TestCaseRow, colNum3);
		clickByXpath("//div/label[text()='"+status+"']/../span/input");
		log("Clicked "+status+"...");
		System.out.println("Clicked "+status+"...");
		
		clickByXpath("//button[text()='Save']");
		Thread.sleep(5000);
		log("Clicked Saved button...");
		System.out.println("Clicked Saved button...");
		
		//clickByXpath("//button[@title='Save and Close']");
		//Thread.sleep(5000);
		//log("Clicked Saved and Closed button...");
		//System.out.println("Clicked Saved and Closed button...");
			
		try{
			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button/span[text()='Y']"))).click();
		} catch (Exception e){
			
		}
	}
	
	public void changeSubTaskStatus(int colNum, int colNum2, int colNum3, String commonPath) throws Exception{
		
		String folder = getCellData(TestCaseRow, colNum);
		String subfolder = getCellData(TestCaseRow, colNum2);
		System.out.println("entered new command...");
		
		clickByXpath(commonPath+"/../../td/span/a");
			
		log("Clicked "+folder+" subtask status...");
		System.out.println("Clicked "+folder+" subtask status...");
		
		String status = getCellData(TestCaseRow, colNum3);
		clickByXpath("//div/label[text()='"+status+"']/../span/input");
		log("Clicked "+status+"...");
		System.out.println("Clicked "+status+"...");
		
		clickByXpath("//button[@title='Save and Close']");
		Thread.sleep(5000);
		log("Clicked Saved and Closed button...");
		System.out.println("Clicked Saved and Closed button...");
		
		if(status.contentEquals("Not Started")){
			clickByXpath("//button[text()='es']");
			Thread.sleep(5000);
			log("Clicked Yes button...");
			System.out.println("Clicked Yes button...");
		}
		
		try{
			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button/span[text()='Y']"))).click();
		} catch (Exception e){
			
		}
	}
	
	public void clickExpandWorkForceDevelopment(){
		
		click("pt1:USma:0:MAnt1:1:pt1:r1:1:topAppPanel:applicationsTable1:_ATTp:table1:2::di");
		log("Clicking expand...");
		System.out.println("Clicking expand...");
	}
	
	public void clickDoneButton() throws InterruptedException{
		
		clickByXpath("//button[text()='D']");
		log("Clicked Done button...");
		System.out.println("Clicked Done button...");
		Thread.sleep(2000);
	}
	
	public void clickSaveAndOpenProjectButton() throws InterruptedException{
		
		clickByXpath("//span[text()='Save and Open Project']");
		log("Clicked Save and Open Project button...");
		System.out.println("Clicked Save and Open Project button...");
		Thread.sleep(5000);
	}
	
	public void enterSearchName(int colNum) throws Exception{
		String value = getCellData(TestCaseRow, colNum);
		enterText("pt1:USma:0:MAnt1:0:AP2:r3:0:qryId1:value10::content", value); //
		log("Entered value in the Name...");
		System.out.println("Entered value in the Name...");
	}
	
	public void clickGoToTaskIcon(){
		
		clickByXpath("//td/a/img[contains(@src,'gototask')]");
		log("Clicked Go to Task icon...");
		System.out.println("Clicked Go to Task icon...");
		
	}
	
	public void clickCreateIcon(){
		
		clickByXpath("//div/a/img[contains(@src,'new_ena')]");
		log("Clicked Create icon...");
		System.out.println("Clicked Create icon...");
	}

	public void enterRole(int colNum) throws Exception{
		String value = getCellData(TestCaseRow, colNum);
		enterText("pt1:USma:0:MAnt2:1:r2:0:dynamicRegion1:1:AP1:inputText1::content", value); //
		log("Entered value in the Role...");
		System.out.println("Entered value in the Role...");
	}
	
	public void enterDescription(int colNum) throws Exception{
		String value = getCellData(TestCaseRow, colNum);
		enterText("pt1:USma:0:MAnt2:1:r2:0:dynamicRegion1:1:AP1:inputText2::content", value); //
		log("Entered value in the Description...");
		System.out.println("Entered value in the Description...");
	}
	
	public void enterFromDate(String date) throws Exception{
		enterText("pt1:USma:0:MAnt2:1:r2:0:dynamicRegion1:1:AP1:inputDate1::content", date); //
		log("Entered value in the From Date...");
		System.out.println("Entered value in the From Date...");
	}
	
	public void enterToDate(String date) throws Exception{
		enterText("pt1:USma:0:MAnt2:1:r2:0:dynamicRegion1:1:AP1:inputDate2::content", date); //
		log("Entered value in the To Date...");
		System.out.println("Entered value in the To Date...");
	}
	
	public void selectStatus(int colNum) throws Exception{
		String value = getCellData(TestCaseRow, colNum);
		selectDropdownByVisibleText("pt1:USma:0:MAnt2:1:r2:0:dynamicRegion1:1:AP1:soc2::content", value); //
		log("Selected value in Status...");
		System.out.println("Selected value in Status...");
	}

	public void clickSaveAndCloseButton() throws InterruptedException{
		
		clickByXpath("//button/span[text()='S']");
		Thread.sleep(5000);
		log("Clicked Save and Close button...");
		System.out.println("Clicked Save and Close button...");
		
	}
	
	public void clickSaveButton() throws InterruptedException{
		
		clickByXpath("//button[text()='Save']");
		Thread.sleep(5000);
		log("Clicked Save button...");
		System.out.println("Clicked Save button...");
		
	}
	
	public void clickOkButton() throws InterruptedException{
		
		clickByXpath("//button[text()='OK']");
		Thread.sleep(5000);
		log("Clicked OK button...");
		System.out.println("Clicked OK button...");
		
	}
	
}
