import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class StorageReader {
	protected static WebDriver driver;

	public static void main(String[] args) throws Exception{
		String cookieData = "";
		String key = "lkey";
		
		driver = new FirefoxDriver();
		driver.get("http://www.google.com");
		
		JavascriptExecutor js = (JavascriptExecutor)driver;
		cookieData = (String)js.executeScript(
			"function getCookie("+key+") {"+
			"    var name = "+key+" + \"=\";"+
			"    var ca = document.cookie.split(';');"+
			"    for(var i=0; i<ca.length; i++) {"+
			"        var c = ca[i];"+
			"        while (c.charAt(0)==' ') c = c.substring(1);"+
			"        if (c.indexOf(name) == 0) return c.substring(name.length, c.length);"+
			"    }"+
			"    return \"\";"+
			"}"
		);
		
		System.out.println("cookie: "+key);
		//return cookieData;	
	}
}
