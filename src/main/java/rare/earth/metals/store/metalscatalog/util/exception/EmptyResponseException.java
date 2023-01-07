package rare.earth.metals.store.metalscatalog.util.exception;

import java.io.Serial;

public class EmptyResponseException extends RuntimeException {

	@Serial
	private static final long serialVersionUID = 1L;

	public EmptyResponseException(final String object,
								  final String id) {
		super(String.format(
				"%s with the %s entered in the database" +
						" was not found.",
				object, id));
	}

}