package rare.earth.metals.store.metalscatalog.model.response;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder()
public class Args {

	@JsonIgnore
	private Map<String, Object> properties = new HashMap<>();

	@JsonAnyGetter
	public Map<String, Object> getProperties() {
		return this.properties;
	}

	@JsonAnySetter
	public void setProperty(String name,
							Object value) {
		this.properties.put(name, value);
	}
}
