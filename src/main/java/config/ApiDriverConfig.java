package config;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.LoadPolicy;
import org.aeonbits.owner.Config.Sources;


@LoadPolicy(Config.LoadType.MERGE)
@Sources({
        "system:properties",
        "classpath:config.properties",
        "file:C:\\local.properties"
})
public interface ApiDriverConfig extends Config {

    @Key("token")
    String token();

    @Key("baseUri")
    String baseUri();
}
