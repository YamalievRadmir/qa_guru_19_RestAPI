package properties;

import config.CredentialsConfig;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.Test;

public class Owner {
    CredentialsConfig config = ConfigFactory.create(CredentialsConfig.class);

    @Test
    void credentialTest() {
        String login = config.login();
        String password = config.password();
        System.out.println("Login: "+login);
        System.out.println("Password: "+password);
    }
}