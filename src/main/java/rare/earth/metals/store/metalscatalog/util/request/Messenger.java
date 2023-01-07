package rare.earth.metals.store.metalscatalog.util.request;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@Data
public class Messenger {
   private String message;
}