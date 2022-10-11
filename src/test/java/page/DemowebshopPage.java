package page;

import com.codeborne.selenide.WebDriverRunner;
import data.UserData;
import io.qameta.allure.Step;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.value;
import static com.codeborne.selenide.Selenide.*;
import static io.restassured.RestAssured.given;


public class DemowebshopPage {

    private final String headerAndParamName = "__RequestVerificationToken";
    private final String headerValue = "PynBwHJHHyE7NvukB2MS2E9qsBJ__lB_Z1O6XpZ2-rKosYrhvIo11z4xz0vMDhoedru0FkeItqJcjckdwMKWILIJZrld-v_GQ5I1AEtRs0c1";
    private final String paramValue = "58mBp8S83XW634lGOjTPfqAtCiWD3Ff_YPwqRlJm-YwcfGXkw7U8g_BOd-Kja2n-uSVW8Mmx8dao38JdIj56b-Gb00uoy0Hg0e2dtIp2SKM1";
    private final String authCookieName = "NOPCOMMERCE.AUTH";


    @Step("Регистрация через API")
    public void registration(UserData userData) {
        given()
                .when()
                .contentType("application/x-www-form-urlencoded; charset=utf-8")
                .formParam(headerAndParamName, paramValue)
                .formParam("FirstName", userData.firstName)
                .formParam("LastName", userData.lastName)
                .formParam("Email", userData.email)
                .formParam("Password", userData.password)
                .formParam("ConfirmPassword", userData.password)
                .cookie(headerAndParamName, headerValue)
                .post("/register")
                .then();
    }

    @Step("Вход по API")
    public String login(UserData userData) {
        String authToken = given()
                .when()
                .contentType("application/x-www-form-urlencoded; charset=utf-8")
                .formParam("Email", userData.email)
                .formParam("Password", userData.password)
                .post("/login")
                .then()
                .extract()
                .cookie(authCookieName);
        return authToken;
    }

    @Step("Открытие UI страницы с авторизацией")
    public void openPageWithAuth(UserData userData) {
        open("/Themes/DefaultClean/Content/images/logo.png");
        Cookie authCookie = new Cookie(authCookieName, login(userData));
        WebDriverRunner.getWebDriver().manage().addCookie(authCookie);
        open("");
        $(".account").shouldHave(text(userData.email));
    }

    @Step("Редактирование профиля и проверка отредактированных данных")
    public void editAndCheckProfile(UserData userDataAfterEdit) {
        open("/customer/info");
        $("#FirstName").setValue(userDataAfterEdit.firstName);
        $("#LastName").setValue(userDataAfterEdit.lastName);
        $("#Email").setValue(userDataAfterEdit.email);
        $("[value='Save']").click();
        refresh();
        $("#FirstName").shouldHave(value(userDataAfterEdit.firstName));
        $("#LastName").shouldHave(value(userDataAfterEdit.lastName));
        $("#Email").shouldHave(value(userDataAfterEdit.email));
    }
}
