package rare.earth.metals.store.metalscatalog.util.exception;

import java.io.Serial;

public class DBObjectNotFoundException extends RuntimeException {

	@Serial
	private static final long serialVersionUID = 1L;

	public DBObjectNotFoundException(final String object,
									 final String id) {
		super(String.format(
				"No %s found with the given id: %s.",
				object, id));
	}
}