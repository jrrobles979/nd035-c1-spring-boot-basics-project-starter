package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class HomeCredentialsPage {
    private WebDriver driver = null;
    private WebDriverWait wait = null;

    @FindBy(id = "nav-credentials-tab")
    private WebElement tabCredentials;


    @FindBy(id = "credential-url")
    private WebElement credentialUrl;

    @FindBy(id = "credential-username")
    private WebElement credentialUserName;

    @FindBy(id = "credential-password")
    private WebElement credentialPassword;

    @FindBy(id = "btnSaveCredential")
    private WebElement btnSaveCredential;

    @FindBy(id = "btnNewCredential")
    private WebElement btnNewCredential;

    @FindBy(id = "credentialTable")
    private WebElement credentialTable;


    public HomeCredentialsPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.wait = new WebDriverWait(driver, TestConstants.timeOutInSecs);
        this.driver = driver;
    }

    public void selectCredentialsTab(){
        tabCredentials.click();
        threadSleep( 1000l );
    }


    public void openNewCredentialModal(){
        btnNewCredential.click();
        threadSleep( 1000l );
    }

    public void fillCredentialForm(String url, String username, String password){
       credentialUrl.sendKeys(url);
       credentialUserName.sendKeys(username);
       credentialPassword.sendKeys(password);
    }

    public void fillCredentialForm(Credential credential){
        credentialUrl.sendKeys(credential.getUrl());
        credentialUserName.sendKeys(credential.getUsername());
        credentialPassword.sendKeys(credential.getPassword());
    }

    public void clearCredentialForm(){
        credentialUrl.clear();
        credentialUserName.clear();
        credentialPassword.clear();
    }

    public void submitCredential(){
        btnSaveCredential.click();
        threadSleep( 1000l );
    }

    public String getResultSuccess(){
        return this.driver.getCurrentUrl();
    }

    public boolean checkCredentialInTable(Credential credential){
        List<WebElement> notesList = credentialTable.findElements(By.tagName("th"));
        Boolean credentialInList = false;
        for (int i=0; i < notesList.size(); i++) {
            WebElement element = notesList.get(i);
            if (element.getAttribute("innerHTML").equalsIgnoreCase( credential.getUrl())) {
                credentialInList = true;
                break;
            }
        }
        return credentialInList;
    }

    /*public WebElement getNoteInTable(Note note){
        List<WebElement> notesList = notesTable.findElements(By.tagName("th"));
        WebElement noteElement = null;
        for (int i=0; i < notesList.size(); i++) {
            WebElement element = notesList.get(i);

            if (element.getAttribute("innerHTML").equalsIgnoreCase( note.getTitle() )) {
                noteElement = element;
                break;
            }
        }
        return noteElement;
    }*/

    public boolean openEditCredentialModalWithURL(String credentialUrl){
        List<WebElement> notesList = credentialTable.findElements(By.tagName("tr"));
        boolean credentialFound = false;
        for (int i=0; i < notesList.size(); i++) {
            WebElement element = notesList.get(i);
            System.out.println( element.findElement(By.tagName("th")) );
            if (element.findElement(By.tagName("th")).getAttribute("innerHTML").equalsIgnoreCase( credentialUrl )) {
                WebElement btnEdit = element.findElement(By.className("btn-success"));
                btnEdit.click();
                credentialFound = true;
                threadSleep( 1000l );
                break;
            }
        }
        return credentialFound;
    }

    public boolean deleteCredential(String credentialUrl){
        List<WebElement> notesList = credentialTable.findElements(By.tagName("tr"));
        boolean noteFound = false;
        for (int i=0; i < notesList.size(); i++) {
            WebElement element = notesList.get(i);
            System.out.println( element.findElement(By.tagName("th")) );
            if (element.findElement(By.tagName("th")).getAttribute("innerHTML").equalsIgnoreCase( credentialUrl )) {
                WebElement btnDelete = element.findElement(By.name("btnDelete"));
                btnDelete.click();
                noteFound = true;
                threadSleep( 1000l );
                break;
            }
        }
        return noteFound;
    }


    public void threadSleep(long milis){
        try{ Thread.sleep(milis); }catch ( InterruptedException ie ){}
    }
}
