package api.movies.backend.application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MovieNotFoundInTmdbException extends RuntimeException {

    public MovieNotFoundInTmdbException(String message) {
        super(message);
    }

}
