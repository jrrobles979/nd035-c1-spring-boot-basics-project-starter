package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomeNotesPage {
    private WebDriver driver = null;
    private WebDriverWait wait = null;

    @FindBy(id = "nav-notes-tab")
    private WebElement tabNotes;


    @FindBy(id = "note-title")
    private WebElement noteTitle;

    @FindBy(id = "note-description")
    private WebElement noteDescription;

    @FindBy(id = "noteSubmit")
    private WebElement btnNoteSubmit;


    @FindBy(id = "btnNewNote")
    private WebElement btnNewNote;



    public HomeNotesPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.wait = new WebDriverWait(driver, TestConstants.timeOutInSecs);
        this.driver = driver;
    }

    public void fillNewNote(String title, String description){
        noteTitle.sendKeys(title);
        noteDescription.sendKeys(description);
    }

    public void submitNote(){
        btnNoteSubmit.click();
    }




}
