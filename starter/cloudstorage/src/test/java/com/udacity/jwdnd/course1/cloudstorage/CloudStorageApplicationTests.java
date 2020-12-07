package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.apachecommons.CommonsLog;
import lombok.extern.java.Log;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@CommonsLog
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private Integer port;

	private static WebDriver driver;
	private static SignUpPage signUpPage;
	private static LoginPage loginPage;



	@BeforeAll
	public static void beforeAll() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
	}

	@AfterAll
	public static void afterAll() {
		if (driver != null) {
			driver.quit();
		}
	}

	@BeforeEach
	public void beforeEach() {

	}



	@Test
	public void testSignUp() {
		//setup
		driver.get("http://localhost:" + port + "/signup");
		signUpPage = new SignUpPage(driver);

		//test successfull singup
		signUpPage.fillSignUpUserForm("Jose", "Perez", "jperez", "abcdefg");
		signUpPage.signUp();
		Assertions.assertEquals(TestConstants.SIGNUP_SUCCESS, signUpPage.getAlertSignupSuccess());

		//test user already exist singup error
		signUpPage.fillSignUpUserForm("Jose", "Perez", "jperez", "abcdefg");
		signUpPage.signUp();
		Assertions.assertEquals(TestConstants.SIGNUP_ERROR_USER_ALREADY_EXIST, signUpPage.getAlertSignupError());
	}


	@Test
	public void testLogIn() {
		//setup
		driver.get("http://localhost:" + port + "/login");
		loginPage = new LoginPage(driver);

		//test login fail user not exist
		loginPage.fillLoginForm("fulano", "detal");
		loginPage.login();
		log.info(loginPage.getResultError());
		Assertions.assertEquals( TestConstants.INVALID_PASSWORD, loginPage.getResultError() );


		//test login fail with wrong password
		loginPage.fillLoginForm(TestConstants.DEFAULT_USERNAME, "badpassword");
		loginPage.login();
		log.info(loginPage.getResultError());
		Assertions.assertEquals( TestConstants.INVALID_PASSWORD, loginPage.getResultError() );

		//test login success
		loginPage.fillLoginForm(TestConstants.DEFAULT_USERNAME, TestConstants.DEFAULT_PASSWORD);
		loginPage.login();
		log.info(loginPage.getResultSuccess());
		Assertions.assertEquals( TestConstants.HOME_TITLE, loginPage.getResultSuccess() );


	}

}
