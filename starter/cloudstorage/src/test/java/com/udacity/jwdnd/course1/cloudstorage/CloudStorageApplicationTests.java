package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.apachecommons.CommonsLog;
import lombok.extern.java.Log;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.util.Assert;

import java.io.File;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDateTime;

@CommonsLog
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private Integer port;

	private static WebDriver driver;




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
	  	SignUpPage signUpPage = new SignUpPage(driver);

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
		LoginPage loginPage = new LoginPage(driver);

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


	@Test
	public void testLogOut() {
		//setup
		driver.get("http://localhost:" + port + "/login");
		LoginPage loginPage = new LoginPage(driver);

		loginPage.fillLoginForm(TestConstants.DEFAULT_USERNAME, TestConstants.DEFAULT_PASSWORD);
		loginPage.login();
		log.info(loginPage.getResultSuccess());
		try{ Thread.sleep(1000); }catch ( InterruptedException ie ){}
		Assertions.assertEquals( TestConstants.HOME_TITLE, loginPage.getResultSuccess() );

		WebElement btnLogout = driver.findElement(By.id("btnLogout"));
		btnLogout.click();
		try{ Thread.sleep(1000); }catch ( InterruptedException ie ){}

		Assertions.assertEquals( TestConstants.LOGIN_TITLE, driver.getTitle() );

	}

	@Test
	public void testNoteCRUD() {
		//login
		Note noteToSave = TestConstants.generateNote();
		driver.get("http://localhost:" + port + "/login");
		LoginPage loginPage = new LoginPage(driver);

		loginPage.fillLoginForm(TestConstants.DEFAULT_USERNAME, TestConstants.DEFAULT_PASSWORD);
		loginPage.login();
		log.info(loginPage.getResultSuccess());

		//open notes
		HomeNotesPage notesPage = new HomeNotesPage(driver);
		notesPage.selectNoteTab();

		//fill notes form and save
		notesPage.openNewNoteModal();
		notesPage.fillNoteForm(noteToSave);
		notesPage.submitNote();
		Assertions.assertEquals(TestConstants.getDefaultUrlResultOk(port), notesPage.getResultSuccess());

		//search added note
		driver.get("http://localhost:" + port + "/home");
		notesPage.selectNoteTab();
		Assertions.assertTrue(notesPage.checkNoteInTable(noteToSave));

		//edit added note
		//search for given note
		Assertions.assertTrue( notesPage.openEditNoteModalWithTitle(noteToSave.getTitle()) );
		notesPage.clearNoteForm();
		Note editedNote = noteToSave;
		editedNote.setTitle( "Edited "+ editedNote.getTitle() );
		notesPage.fillNoteForm( editedNote) ;
		notesPage.submitNote();
		Assertions.assertEquals(TestConstants.getDefaultUrlResultOk(port), notesPage.getResultSuccess());

		//search edited note
		driver.get("http://localhost:" + port + "/home");
		notesPage.selectNoteTab();
		Assertions.assertTrue(notesPage.checkNoteInTable(editedNote));

		//delete note
		Assertions.assertTrue(notesPage.deleteNote(editedNote.getTitle()));
		Assertions.assertEquals(TestConstants.getDefaultUrlResultOk(port), notesPage.getResultSuccess());

		//assert note deleted
		driver.get("http://localhost:" + port + "/home");
		notesPage.selectNoteTab();
		Assertions.assertFalse(notesPage.checkNoteInTable(editedNote));

	}

	@Test
	public void testFiles() throws URISyntaxException {
		//login
		URL fileToUpdate = getClass().getClassLoader().getResource(TestConstants.DEFAULT_FILE);
		Assertions.assertNotNull(fileToUpdate);
		File file = new File(fileToUpdate.toURI());
		driver.get("http://localhost:" + port + "/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.fillLoginForm(TestConstants.DEFAULT_USERNAME, TestConstants.DEFAULT_PASSWORD);
		loginPage.login();
		log.info(loginPage.getResultSuccess());

		//upload file
		HomeFilesPage filesPage = new HomeFilesPage(driver);
		filesPage.selectFilesTab();

		filesPage.uploadFile( fileToUpdate.getPath() );
		Assertions.assertEquals(TestConstants.getDefaultUrlResultOk(port), filesPage.getResultSuccess());
		//assert file uploaded
		driver.get("http://localhost:" + port + "/home");
		Assertions.assertTrue(filesPage.checkFileInTable( file.getName() ));

		//Delete file
		Assertions.assertTrue(  filesPage.deleteFile( file.getName() ) );
		Assertions.assertEquals(TestConstants.getDefaultUrlResultOk(port), filesPage.getResultSuccess());

		//assert file removed
		driver.get("http://localhost:" + port + "/home");
		Assertions.assertFalse(filesPage.checkFileInTable( file.getName() ));

		System.out.println("");
	}


	@Test
	public void testCredentialsCRUD() {
		//login
		Credential credentialToSave = TestConstants.generateCredential();
		driver.get("http://localhost:" + port + "/login");
		LoginPage loginPage = new LoginPage(driver);


		loginPage.fillLoginForm(TestConstants.DEFAULT_USERNAME, TestConstants.DEFAULT_PASSWORD);
		loginPage.login();
		log.info(loginPage.getResultSuccess());

		//open notes
		HomeCredentialsPage credentialPage = new HomeCredentialsPage(driver);
		credentialPage.selectCredentialsTab();

		//fill notes form and save
		credentialPage.openNewCredentialModal();
		credentialPage.fillCredentialForm(credentialToSave);
		credentialPage.submitCredential();
		Assertions.assertEquals(TestConstants.getDefaultUrlResultOk(port), credentialPage.getResultSuccess());

		//search added note
		driver.get("http://localhost:" + port + "/home");
		credentialPage.selectCredentialsTab();
		Assertions.assertTrue( credentialPage.checkCredentialInTable(credentialToSave));

		//edit added note
		//search for given note
		Assertions.assertTrue(  credentialPage.openEditCredentialModalWithURL(credentialToSave.getUrl()));
		credentialPage.clearCredentialForm();
		Credential editedCredential = credentialToSave;

		editedCredential.setUsername("perengano");
		editedCredential.setPassword("zyxwvut");


		credentialPage.fillCredentialForm(editedCredential);
		credentialPage.submitCredential();

		Assertions.assertEquals(TestConstants.getDefaultUrlResultOk(port), credentialPage.getResultSuccess());

		//search edited note
		driver.get("http://localhost:" + port + "/home");
		credentialPage.selectCredentialsTab();
		Assertions.assertTrue( credentialPage.checkCredentialInTable(editedCredential));

		//delete note
		credentialPage.deleteCredential(editedCredential.getUrl());
		Assertions.assertEquals(TestConstants.getDefaultUrlResultOk(port), credentialPage.getResultSuccess());

		//assert credential removed
		driver.get("http://localhost:" + port + "/home");
		Assertions.assertFalse( credentialPage.checkCredentialInTable(editedCredential));

		System.out.println("");

	}

}
