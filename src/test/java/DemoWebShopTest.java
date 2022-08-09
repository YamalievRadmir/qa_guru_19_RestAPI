import config.CredentialsConfig;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.value;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;


public class DemoWebShopTest extends TestBase {
    //
    static CredentialsConfig config = ConfigFactory.create(CredentialsConfig.class);
    static String login = config.login(),
            password = config.password();

    @Test
    @Tag("demowebshop1")
    void registerUser() {
        step("Заполняем и отправляем форму регистрации через API",
                () -> {
                    given()
                            .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                            .cookie("ARRAffinity=92eb765899e80d8de4d490df907547e5cb10de899e8b754a4d5fa1a7122fad69")
                            .formParam("FirstName", "John")
                            .formParam("LastName", "Lenon")
                            .formParam("Email", login)
                            .formParam("Password", password)
                            .formParam("ConfirmPassword", password)
                            .when()
                            .post("http://demowebshop.tricentis.com/register")
                            .then()
                            .statusCode(302)
                            .extract().header("Location");

                    step("Проверяем регистрацию", () -> {

                        open("http://demowebshop.tricentis.com/registerresult/1");
                        $(".page-body").shouldHave(text("Your registration completed"));
                    });
                });
    }

    @Test
    @Tag("demowebshop1")
    void userRegistrationEdit() {
        step("Авторизируемся как пользователь", () -> {
            given()
                    .contentType("application/x-www-form-urlencoded")
                    .cookie("ARRAffinity=92eb765899e80d8de4d490df907547e5cb10de899e8b754a4d5fa1a7122fad69")
                    .body("Email" + login + "Password" + password + "RememberMe=false")
                    .when()
                    .post("http://demowebshop.tricentis.com/login")
                    .then()
                    .log().all()
                    .statusCode(200);
        });

        step("Меняем имя пользователя", () -> {
            String body = "__RequestVerificationToken=YgcXNl9iyTtrT6HONuo" +
                    "_icX10_Rl0yj9PgSSf539LPUH2Qx5KZyt44EQDRtfYm70JyHHs4xSBJB" +
                    "-XpQ7Apr8KX8EVVBe-piACDQHr_0xZWWh0_PVkCW0mY3gPXYssQxQ0&Gender" +
                    "=M&FirstName=Mike&LastName=Lenon&Email=John.Lenon%40yandex.ru&save" +
                    "-info-button=Save";
            given()
                    .contentType("application/x-www-form-urlencoded")
                    .cookie("ARRAffinity=92eb765899e80d8de4d490df907547e5cb10de899e8b754a4d5fa1a7122fad69")
                    .body(body)
                    .when()
                    .post("http://demowebshop.tricentis.com/customer/info")
                    .then()
                    .log().all()
                    .statusCode(302)
                    .extract().header("Mike");

        });

        step("Open login page", () ->
                open("http://demowebshop.tricentis.com/login"));

        step("Fill login form", () -> {
            $("#Email").setValue(config.login());
            $("#Password").setValue(config.password())
                    .pressEnter();
        });

        step("Verify successful authorization", () ->
                $(".account").shouldHave(text(config.login())));

        open("http://demowebshop.tricentis.com/customer/info");
        $("#FirstName").shouldHave(value("Mike"));
    }
}


