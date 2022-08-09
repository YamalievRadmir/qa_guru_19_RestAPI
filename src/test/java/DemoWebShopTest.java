import config.CredentialsConfig;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
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
            /*curl 'http://demowebshop.tricentis.com/customer/info' \
           -H 'Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,
           -H 'Accept-Language: ru-RU,ru;q=0.9,en-US;q=0.8,en;q=0.7' \
           -H 'Cache-Control: max-age=0' \
           -H 'Connection: keep-alive' \
           -H 'Content-Type: application/x-www-form-urlencoded' \
           -H 'Cookie: ARRAffinity=92eb765899e80d8de4d490df907547e5cb10de899e8b754a4d5fa1a7122fad69; __utmc=78382081; __utmz=78382081.1659962655.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); __RequestVerificationToken=OIUW0kOQBayv18r01d3WNDiJdl6unMASOuw7ipomIQWE8kWPwOeJUQF0jYaoTSDgMGg5zFKKn9fOdeHGbKJz7a1m0iK1WCX88YAbANXs1_01; ASP.NET_SessionId=ygzhhhqrb1c1ooyiswyth0hh; NOPCOMMERCE.AUTH=39E3D4CF70AF8265411ED08A00FBB40904A2AD7B9673E7D794D79346953BD6BB75A1B2B73807EA8CCE834F47A9FF12E86688EDC238FC5E2D3596D877FD9A46EF664816380E726EE11EEAD6794FE77557701ACE6EADF7711EF4B957B96855AD4DEBAEC0F1AC48EA29ABD209506E963834E2F3AA560F697BBC307A63AA853AAC79BCBBC093AD00037D3D7E79014FCDC1D92AF0827B7AF8BE919199EFB1D5B8952A; Nop.customer=bf5c9535-734d-476a-bed2-9b47e42003c1; __utma=78382081.1983996418.1659962655.1660031922.1660034258.3; __utmt=1; __utmb=78382081.2.10.1660034258' \
           -H 'Origin: http://demowebshop.tricentis.com' \
           -H 'Referer: http://demowebshop.tricentis.com/customer/info' \
           -H 'Upgrade-Insecure-Requests: 1' \
           -H 'User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/103.0.0.0 Safari/537.36' \
           --data-raw '__RequestVerificationToken=YgcXNl9iyTtrT6HONuo_icX10_Rl0yj9PgSSf539LPUH2Qx5KZyt44EQDRtfYm70JyHHs4xSBJB-XpQ7Apr8KX8EVVBe-piACDQHr_0xZWWh0_PVkCW0mY3gPXYssQxQ0&Gender=M&FirstName=Mike&LastName=Lenon&Email=John.Lenon%40yandex.ru&save-info-button=Save' */
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
    }


    @Test
    @Tag("demowebshop")
    void logTestUI() {
        step("Open login page", () ->
                open("http://demowebshop.tricentis.com/login"));

        step("Fill login form", () -> {
            $("#Email").setValue(config.login());
            $("#Password").setValue(config.password())
                    .pressEnter();
        });

        step("Verify successful authorization", () ->
                $(".account").shouldHave(text(config.login())));
    }
}


