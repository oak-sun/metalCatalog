package rare.earth.metals.store.metalscatalog.util.request;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import rare.earth.metals.store.metalscatalog.util.exception.DBObjectNotFoundException;
import rare.earth.metals.store.metalscatalog.util.exception.EmptyResponseException;

@RestControllerAdvice
public class ExceptionsHandler {

	@ExceptionHandler({ DBObjectNotFoundException.class })
	public ResponseEntity<Messenger> notFoundExc(final RuntimeException ex) {
		return new ResponseEntity<>(
				Messenger
						.builder()
						.message(ex.getMessage())
						.build(),
				HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler({ EmptyResponseException.class })
	public ResponseEntity<Messenger> emptyResponseExc(final RuntimeException ex) {
		return new ResponseEntity<>(
				Messenger
						.builder()
						.message(ex.getMessage())
						.build(),
				HttpStatus.OK);
	}
}