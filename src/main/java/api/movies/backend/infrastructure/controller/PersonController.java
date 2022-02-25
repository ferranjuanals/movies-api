package api.movies.backend.infrastructure.controller;

import api.movies.backend.application.dto.PersonDto;
import api.movies.backend.application.exception.InvalidParameterException;
import api.movies.backend.application.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/people")
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService)  {
        this.personService = personService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    Page<PersonDto> getPeople(@RequestParam Integer page,
                              @RequestParam Integer size,
                              @RequestParam(required = false) String job) {
        try{
            return personService.getPeople(page, size, job);
        } catch (Exception e) {
            throw new InvalidParameterException(e.getMessage());
        }
    }

}
