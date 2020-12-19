package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class HomeNotesPage {
    private WebDriver driver = null;
    private WebDriverWait wait = null;

    @FindBy(id = "nav-notes-tab")
    private WebElement tabNotes;


    @FindBy(id = "note-title")
    private WebElement noteTitle;

    @FindBy(id = "note-description")
    private WebElement noteDescription;

   // @FindBy(id = "btnSave")
   // private WebElement btnSave;


    @FindBy(id = "btnNewNote")
    private WebElement btnOpenNoteModal;

    @FindBy(id = "btnNoteSave")
    private WebElement btnNoteSave;

    @FindBy(id = "userTable")
    private WebElement notesTable;




    public HomeNotesPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.wait = new WebDriverWait(driver, TestConstants.timeOutInSecs);
        this.driver = driver;
    }

    public void selectNoteTab(){
        tabNotes.click();
        threadSleep( 1000l );

    }

    public void openNewNoteModal(){
        btnOpenNoteModal.click();
        threadSleep( 1000l );
    }

    public void fillNoteForm(String title, String description){
        noteTitle.sendKeys(title);
        noteDescription.sendKeys(description);
    }

    public void fillNoteForm(Note note){
        noteTitle.sendKeys(note.getTitle());
        noteDescription.sendKeys(note.getDescription());
    }

    public void clearNoteForm(){
        noteTitle.clear();
        noteDescription.clear();
    }

    public void submitNote(){
        btnNoteSave.click();
        threadSleep( 1000l );

    }

    public String getResultSuccess(){
        return this.driver.getCurrentUrl();
    }

    public boolean checkNoteInTable(Note note){
        List<WebElement> notesList = notesTable.findElements(By.tagName("th"));
        Boolean noteInList = false;
        for (int i=0; i < notesList.size(); i++) {
            WebElement element = notesList.get(i);
            if (element.getAttribute("innerHTML").equalsIgnoreCase( note.getTitle() )) {
                noteInList = true;
                break;
            }
        }
        return noteInList;
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

    public boolean openEditNoteModalWithTitle(String noteTitle){
        List<WebElement> notesList = notesTable.findElements(By.tagName("tr"));
        boolean noteFound = false;
        for (int i=0; i < notesList.size(); i++) {
            WebElement element = notesList.get(i);
            System.out.println( element.findElement(By.tagName("th")) );
            if (element.findElement(By.tagName("th")).getAttribute("innerHTML").equalsIgnoreCase( noteTitle )) {
                WebElement btnEdit = element.findElement(By.className("btn-success"));
                btnEdit.click();
                noteFound = true;
                threadSleep( 1000l );
                break;
            }
        }
        return noteFound;
    }

    public boolean deleteNote(String noteTitle){
        List<WebElement> notesList = notesTable.findElements(By.tagName("tr"));
        boolean noteFound = false;
        for (int i=0; i < notesList.size(); i++) {
            WebElement element = notesList.get(i);
            System.out.println( element.findElement(By.tagName("th")) );
            if (element.findElement(By.tagName("th")).getAttribute("innerHTML").equalsIgnoreCase( noteTitle )) {
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
