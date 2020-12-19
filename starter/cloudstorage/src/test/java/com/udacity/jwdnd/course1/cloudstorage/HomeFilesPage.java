package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class HomeFilesPage {
    private WebDriver driver = null;
    private WebDriverWait wait = null;

    @FindBy(id = "nav-files-tab")
    private WebElement tabFiles;


    @FindBy(id = "fileUpload")
    private WebElement fileUpload;

    @FindBy(id = "btnUpload")
    private WebElement btnUpload;

    @FindBy(id = "fileTable")
    private WebElement fileTable;


    public HomeFilesPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.wait = new WebDriverWait(driver, TestConstants.timeOutInSecs);
        this.driver = driver;
    }

    public void selectFilesTab(){
        tabFiles.click();
        threadSleep( 1000l );
    }

    public void uploadFile(String filePath){
        fileUpload.sendKeys(filePath);
        btnUpload.click();
        threadSleep(1000l);
    }


    public boolean checkFileInTable(String filename){
        List<WebElement> fileList = fileTable.findElements(By.tagName("th"));
        Boolean fileInList = false;
        for (int i=0; i < fileList.size(); i++) {
            WebElement element = fileList.get(i);
            if (element.getAttribute("innerHTML").equalsIgnoreCase( filename )) {
                fileInList = true;
                break;
            }
        }
        return fileInList;
    }

    public boolean deleteFile(String filename){
        List<WebElement> notesList = fileTable.findElements(By.tagName("tr"));
        boolean fileDeleted = false;
        for (int i=0; i < notesList.size(); i++) {
            WebElement element = notesList.get(i);
            System.out.println( element.findElement(By.tagName("th")) );
            if (element.findElement(By.tagName("th")).getAttribute("innerHTML").equalsIgnoreCase( filename )) {
                WebElement btnDelete = element.findElement(By.name("btnDelete"));
                btnDelete.click();
                fileDeleted = true;
                threadSleep( 1000l );
                break;
            }
        }
        return fileDeleted;
    }

    public String getResultSuccess(){
        return this.driver.getCurrentUrl();
    }

    public void threadSleep(long milis){
        try{ Thread.sleep(milis); }catch ( InterruptedException ie ){}
    }


}
