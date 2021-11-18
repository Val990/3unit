package ru.netology.web;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardTest {

    private WebDriver driver;

    @BeforeAll
    static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setupTest() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
    }

    @AfterEach
    void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void shouldSendForm() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("Иванов Иван");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79999999999");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector("button")).click();
        String actualText = driver.findElement(By.cssSelector("[data-test-id='order-success']")).getText();
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        assertEquals(expected, actualText.trim(), "Текст не совпадает");
    }

    @Test
    public void shouldSendFormEnglishName() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("ivanov ivan");
        driver.findElement(By.cssSelector("button")).click();
        String actualText = driver.findElement(By.cssSelector(".input__sub")).getText();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        assertEquals(expected, actualText.trim(), "Текст не совпадает");
    }

    @Test
    public void shouldSendFormEmptyName() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79999999999");
        driver.findElement(By.cssSelector("button")).click();
        String actualText = driver.findElement(By.cssSelector(".input__sub")).getText();
        String expected = "Поле обязательно для заполнения";
        assertEquals(expected, actualText.trim(), "Текст не совпадает");
    }

    @Test
    public void shouldSendFormZeroNumber() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("Петров Олег");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("00000");
        driver.findElement(By.cssSelector("button")).click();
        String actualText = driver.findElement(By.cssSelector("[data-test-id='phone'] .input__sub")).getText();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        assertEquals(expected, actualText.trim(), "Текст не совпадает");
    }

    @Test
    public void shouldSendFormEmptyNumber() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("Петров Олег");
        driver.findElement(By.cssSelector("button")).click();
        String actualText = driver.findElement(By.cssSelector("[data-test-id='phone'] .input__sub")).getText();
        String expected = "Поле обязательно для заполнения";
        assertEquals(expected, actualText.trim(), "Текст не совпадает");
    }

    @Test
    public void shouldSendEmptyForm() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("button")).click();
        String actualText = driver.findElement(By.cssSelector(".input__sub")).getText();
        String expected = "Поле обязательно для заполнения";
        assertEquals(expected, actualText.trim(), "Текст не совпадает");
    }

    @Test
    public void shouldSendFormNoCheckbox() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("Валуева Александра");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79999999900");
        driver.findElement(By.cssSelector("button")).click();
        String actualText = driver.findElement(By.cssSelector(".input_invalid .checkbox__text")).getText();
        String expected = "Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй";
        assertEquals(expected, actualText.trim(), "Текст не совпадает");
    }

    @Test
    public void shouldSendFormЁLetterName() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("Александрова Алёна");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79999990000");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector("button")).click();
        String actualText = driver.findElement(By.cssSelector(".input__sub")).getText();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        assertEquals(expected, actualText.trim(), "Текст не совпадает");

    }
}
