package api.movies.backend.application.service;

import api.movies.backend.application.dto.CreditDto;
import api.movies.backend.application.dto.MovieDto;
import api.movies.backend.application.dto.PersonDto;
import api.movies.backend.application.repository.CreditRepository;
import api.movies.backend.domain.entity.Credit;
import api.movies.backend.domain.entity.Person;
import api.movies.backend.domain.repository.IPersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonService {

    private final IPersonRepository personRepository;
    private final CreditRepository creditRepository;

    @Autowired
    public PersonService(IPersonRepository personRepository, CreditRepository creditRepository) {
        this.personRepository = personRepository;
        this.creditRepository = creditRepository;
    }

    public Page<PersonDto> getPeople(Integer page, Integer size, String job) throws Exception {
        if (job == null) return getAllPeople(createPageable(page, size));
        List<String> possibleJobs = Arrays.asList("actor", "director", "writer");
        if (!possibleJobs.contains(job)) throw new Exception("Job must be one of: 'actor', 'director', 'writer'.");
        return getAllPeopleByJob(page, size, job);
    }

    private Pageable createPageable(Integer page, Integer size) {
        return PageRequest.of(page, size);
    }

    private Page<PersonDto> getAllPeople(Pageable pageable) {
        return personRepository.findAll(pageable)
                .map(this::mapPersonDtoFromPerson);
    }
    private Page<PersonDto> getAllPeopleByJob(Integer page, Integer size, String job) {
        List<Person> people = personRepository.findAllByCreditsIn(creditRepository.findAllByJob(job));
        return convertListToPage(filterPersonCreditsByJob(people, job), PageRequest.of(page, size))
                .map(this::mapPersonDtoFromPerson);
    }

    private List<Person> filterPersonCreditsByJob(List<Person> people, String job) {
        return people.stream()
                .peek(person -> person.setCredits(person.getCredits().stream()
                        .filter(credit -> credit.getJob().equals(job))
                        .collect(Collectors.toList())))
                .collect(Collectors.toList());
    }

    private Page<Person> convertListToPage(List<Person> people, Pageable pageable) {
        return new PageImpl<>(people, pageable, people.size());
    }

    private PersonDto mapPersonDtoFromPerson(Person person) {
        return PersonDto.builder()
                .personId(person.getId())
                .name(person.getName())
                .knownFor(person.getKnownFor())
                .credits(person.getCredits().stream()
                        .map(this::mapCreditDtoFromCredit)
                        .collect(Collectors.toList()))
                .build();
    }

    private CreditDto mapCreditDtoFromCredit(Credit credit) {
        return CreditDto.builder()
                .creditId(credit.getId())
                .job(credit.getJob())
                .character(credit.getCharacter())
                .movie(MovieDto.builder()
                        .movieId(credit.getMovie().getId())
                        .title(credit.getMovie().getTitle())
                        .build())
                .build();
    }

}
