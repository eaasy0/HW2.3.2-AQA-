package ru.netology.test;

import com.codeborne.selenide.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.openqa.selenium.By.cssSelector;

public class AuthorizedTest {

    @BeforeEach
    void setUpAll() {
        open("http://localhost:9999");
    }

    @Test
    void UserIsActive() {
        User user = Data.generateNewActiveUser();
        SelenideElement form = $("[action]");
        form.$(cssSelector("[name=login]")).sendKeys(user.getLogin());
        form.$(cssSelector("[name=password]")).sendKeys(user.getPassword());
        form.$(cssSelector("[type=button]")).click();
        $(byText("Личный кабинет")).shouldBe(Condition.visible);
    }

    @Test
    void UserIsBlocked() {
        User user = Data.generateNewBlockedUser();
        SelenideElement form = $("[action]");
        form.$(cssSelector("[name=login]")).sendKeys(user.getLogin());
        form.$(cssSelector("[name=password]")).sendKeys(user.getPassword());
        form.$(cssSelector("[type=button]")).click();
        $("[data-test-id='error-notification']").shouldBe(Condition.visible);
        $("[data-test-id='error-notification'] .notification__content").shouldHave(Condition.text("Пользователь заблокирован"));
    }

    @Test
    void LoginInvalid() {
        User user = Data.generateNewUserWithInvalidLogin();
        SelenideElement form = $("[action]");
        form.$(cssSelector("[name=login]")).sendKeys(user.getLogin());
        form.$(cssSelector("[name=password]")).sendKeys(user.getPassword());
        form.$(cssSelector("[type=button]")).click();
        $("[data-test-id='error-notification']").shouldBe(Condition.visible);
        $("[data-test-id='error-notification'] .notification__content").shouldHave(Condition.text("Неверно указан логин или пароль"));
    }

    @Test
    void PasswordInvalid() {
        User user = Data.generateNewUserWithInvalidPassword();
        SelenideElement form = $("[action]");
        form.$(cssSelector("[name=login]")).sendKeys(user.getLogin());
        form.$(cssSelector("[name=password]")).sendKeys(user.getPassword());
        form.$(cssSelector("[type=button]")).click();
        $("[data-test-id='error-notification']").shouldBe(Condition.visible);
        $("[data-test-id='error-notification'] .notification__content").shouldHave(Condition.text("Неверно указан логин или пароль"));
    }
}