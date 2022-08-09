package helpers;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class Registration {

    public Registration openPage() {
        open("http://demowebshop.tricentis.com/register");
        return this;
    }

    public Registration setGender() {
        $("#gender-male").click();
        return this;
    }

    public Registration setFirstName() {
        $("#FirstName").setValue("John");
        return this;
    }

    public Registration setLastName() {
        $("#LastName").setValue("Lenon");
        return this;
    }

    public Registration setEmail(String value) {
        $("#Email").setValue(value);
        return this;
    }

    public Registration setPassword(String value) {
        $("#Password").setValue(value);
        return this;
    }

    public Registration setConfirmPassword(String value) {
        $("#ConfirmPassword").setValue(value);
        return this;
    }

    public Registration setRegister() {
        $("#register-button").click();
        return this;
    }

    public void checkResult() {
        $(".result").shouldHave(text("Your registration completed"));

    }

}
