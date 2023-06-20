package site.iparse.downloadservice.dto;

import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;


//@Value
@ConfigurationProperties(prefix = "db")
public record DbProperties(String username,
                           String password,
                           String driver,
                           String url,
                           String host,
                           Map<String, Object> properties,
                           PoolProperties pool,
                           List<PoolProperties> pools) {

    public record PoolProperties(Integer size, Integer timeout) {
    }

//@Data // Equivalent to @Getter @Setter @RequiredArgsConstructor @ToString @EqualsAndHashCode.
//@NoArgsConstructor
//@Component
//@Value
//@ConfigurationProperties(prefix = "db")
//public class DbProperties {
//    String username;
//    String password;
//    String driver;
//    String url;
//    String host;
//    Map<String, Object> properties;
//    PoolProperties pool;
//    List<PoolProperties> pools;
//
//    @Value
//    public static class PoolProperties {
//        Integer size;
//        Integer timeout;
//    }
}
