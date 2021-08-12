package variables.environment;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.ConfigFactory;

@Config.Sources({"file:~/APIMTestSuite/src/main/resources/environment/dev2.properties" })
public interface EnvironmentMapper extends Config{
    EnvironmentMapper ENVIRONMENT_MAPPER = ConfigFactory.create(EnvironmentMapper.class, System.getenv(), System.getProperties());

}
