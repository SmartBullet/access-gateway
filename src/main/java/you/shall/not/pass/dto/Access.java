package you.shall.not.pass.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Access {
    boolean authenticated;
}
