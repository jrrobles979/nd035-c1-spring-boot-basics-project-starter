package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WrapsDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class SignUpPage {

    public static final String SIGNUP_SUCCESS="You successfully signed up! Please continue to the login page.";
    public static final String SIGNUP_ERROR_USER_ALREADY_EXIST="User already exist";
    public static final String SIGNUP_INVALID="ups something went wrong";
    private WebDriver driver = null;
    private WebDriverWait wait = null;

    @FindBy(id = "inputFirstName")
    private WebElement inputFirstName;

    @FindBy(id = "inputLastName")
    private WebElement inputLastName;

    @FindBy(id = "inputUsername")
    private WebElement inputUserName;

    @FindBy(id = "inputPassword")
    private WebElement inputPassword;

    @FindBy(id = "btnSignUp")
    private WebElement btnSubmit;

    @FindBy(id = "alertSignUpSuccess")
    private WebElement alertSignUpSuccess;

    @FindBy(id = "alertSignUpError")
    private WebElement alertSignUpError;

    @FindBys({@FindBy(id = "alertSignUpSuccess")})
    private List<WebElement> alertSignUpSuccessList;

    @FindBys({@FindBy(id = "alertSignUpError")})
    private List<WebElement> alertSignUpErrorList;

    public SignUpPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.wait = new WebDriverWait(driver, TestConstants.timeOutInSecs);
        this.driver = driver;
    }

    public void fillSignUpUserForm(String firstName, String lastName, String username, String password){
        inputFirstName.sendKeys(firstName);
        inputLastName.sendKeys(lastName);
        inputUserName.sendKeys(username);
        inputPassword.sendKeys(password);
    }


    public void signUp(){
        btnSubmit.click();



       // wait.until(ExpectedConditions.or( ExpectedConditions.visibilityOf(alertSignUpSuccess),
       //         ExpectedConditions.visibilityOf(alertSignUpError)));

        wait.until(ExpectedConditions.or(
                ExpectedConditions.presenceOfAllElementsLocatedBy(By.id("alertSignUpSuccess")),
                ExpectedConditions.presenceOfAllElementsLocatedBy(By.id("alertSignUpError"))
        ));

    }

    public String getAlertSignupSuccess(){
        return this.alertSignUpSuccess.getText();
    }

    public String getAlertSignupError(){
        return this.alertSignUpError.getText();
    }



}





