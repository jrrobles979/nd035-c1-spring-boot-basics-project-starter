package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
    private WebDriver driver = null;
    private WebDriverWait wait = null;


    @FindBy(id = "inputUsername")
    private WebElement inputUsername;

    @FindBy(id = "inputPassword")
    private WebElement inputPassword;

    @FindBy(id = "btnLogin")
    private WebElement btnSubmit;

    @FindBy(id = "alertLoginError")
    private WebElement alertLoginError;


    public LoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.wait = new WebDriverWait(driver, TestConstants.timeOutInSecs);
        this.driver = driver;
    }

    public void fillLoginForm(String username, String password){
        inputUsername.sendKeys(username);
        inputPassword.sendKeys(password);
    }

    public void clearLoginForm(){
        inputUsername.clear();
        inputPassword.clear();
    }

    public void login(){
        btnSubmit.click();
      //  wait.until(ExpectedConditions.or( ExpectedConditions.titleIs(TestConstants.HOME_TITLE),
      //      ExpectedConditions.presenceOfAllElementsLocatedBy(By.id("alertLoginError"))));



        wait.until(ExpectedConditions.or( ExpectedConditions.urlContains (TestConstants.HOME_TITLE.toLowerCase()),
              ExpectedConditions.presenceOfAllElementsLocatedBy(By.id("alertLoginError"))));

        //wait.until( webDriver -> {  webDriver.getTitle().equalsIgnoreCase(TestConstants.HOME_TITLE.toLowerCase()); }  );
    }

    public String getResultSuccess(){
      return this.driver.getTitle();
    }

    public String getResultError(){
        return alertLoginError.getText();
    }

}
