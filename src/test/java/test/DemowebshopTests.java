package test;

import base.TestBase;
import data.UserData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import page.DemowebshopPage;

public class DemowebshopTests extends TestBase {
    static UserData userData = new UserData();
    static UserData userDataAfterEdit = new UserData();
    static DemowebshopPage demowebshopPage = new DemowebshopPage();

    @Test
    @DisplayName("Регистрация нового пользователя и вход под ним")
    public void registrationTest() {
        demowebshopPage.registration(userData);
        demowebshopPage.login(userData);
        demowebshopPage.openPageWithAuth(userData);
    }

    @Test
    @DisplayName("Редактирование профиля с проверкой через UI")
    public void editProfileTest() {
        demowebshopPage.registration(userData);
        demowebshopPage.login(userData);
        demowebshopPage.openPageWithAuth(userData);
        demowebshopPage.editAndCheckProfile(userDataAfterEdit);
    }
}
