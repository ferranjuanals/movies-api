package api.movies.backend.application.service;

import api.movies.backend.application.dto.TmdbCreditDto;
import api.movies.backend.application.dto.TmdbCreditsDto;
import api.movies.backend.domain.entity.Credit;
import api.movies.backend.domain.entity.Person;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CreditService {

    public List<Credit> createCreditsFromTmdbDto(TmdbCreditsDto tmdbCreditsDto) {
        return Stream.concat(getCrew(tmdbCreditsDto).stream(), getCast(tmdbCreditsDto).stream())
                .collect(Collectors.toList());
    }

    private List<Credit> getCast(TmdbCreditsDto tmdbCreditsDto) {
        return tmdbCreditsDto.getCast().stream()
                .filter(castOrder -> castOrder.getOrder() < 6)
                .map(this::createCreditFromTmdbCastDto)
                .collect(Collectors.toList());
    }

    private Credit createCreditFromTmdbCastDto(TmdbCreditDto tmdbCreditDto) {
        return Credit.builder()
                .id(tmdbCreditDto.getCreditId())
                .creditType("cast")
                .job("actor")
                .character(tmdbCreditDto.getCharacter())
                .person(createPersonFromTmdbDto(tmdbCreditDto))
                .build();
    }

    private List<Credit> getCrew(TmdbCreditsDto tmdbCreditsDto) {
        return tmdbCreditsDto.getCrew().stream()
                .peek(crewRole -> {if(crewRole.getJob().equals("Screenplay")) crewRole.setJob("Writer");
                })
                .filter(crewRole ->
                        crewRole.getJob().equals("Director") |
                                crewRole.getJob().equals("Writer") |
                                crewRole.getJob().equals("Screenplay"))
                .map(this::createCreditFromTmdbCrewDto)
                .collect(Collectors.toList());
    }

    private Credit createCreditFromTmdbCrewDto(TmdbCreditDto tmdbCrewDto) {
        return Credit.builder()
                .id(tmdbCrewDto.getCreditId())
                .creditType("crew")
                .job(tmdbCrewDto.getJob().toLowerCase())
                .person(createPersonFromTmdbDto(tmdbCrewDto))
                .build();
    }

    private Person createPersonFromTmdbDto(TmdbCreditDto cast) {
        return Person.builder()
                .id(cast.getId())
                .name(cast.getName())
                .knownFor(cast.getKnownForDepartment())
                .build();
    }

}
