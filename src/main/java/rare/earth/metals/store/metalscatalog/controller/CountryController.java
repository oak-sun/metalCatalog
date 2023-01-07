package rare.earth.metals.store.metalscatalog.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import rare.earth.metals.store.metalscatalog.model.entities.Country;
import rare.earth.metals.store.metalscatalog.model.dto.CountryDTO;
import rare.earth.metals.store.metalscatalog.service.impl.CountryServiceImpl;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
public class CountryController {

	@Autowired
	private CountryServiceImpl service;
	
	@Autowired
	private ModelMapper mapper;

	@PostMapping("/countries")
	@ResponseStatus(HttpStatus.CREATED)
	public Mono<CountryDTO> save(@RequestBody final CountryDTO dto) {
		return service
				.save(convertToEntity(dto))
				.map(this::convertToDto)
				.doOnNext(o -> log.debug(
						"Save country - {}", o));
	}
	
	@PutMapping("/countries")
	@ResponseStatus(HttpStatus.OK)
	public Mono<CountryDTO> update(@RequestBody final CountryDTO dto) {
		return service
				.update(convertToEntity(dto))
				.map(this::convertToDto)
				.doOnNext(c -> log.debug(
						"Save country - {}", c));
	}

	@GetMapping("/countries/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Mono<CountryDTO> findById(@PathVariable final String id) {
		return service
				.findById(id)
				.map(this::convertToDto)
				.doOnNext(c -> log.debug(
						"Find country by id - {}", c));
	}
	
	@PostMapping(path = "/countriesByExample",
			produces = MediaType.APPLICATION_NDJSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Flux<CountryDTO> findAllByExample(@RequestBody final CountryDTO dto) {
		return service
				.findAll(convertToEntity(dto))
				.map(this::convertToDto)
				.doOnNext(c -> log.debug(
						"Find countries by example - {}", c));
	}
	
	@GetMapping(path = "/countries",
			    produces = MediaType.APPLICATION_NDJSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Flux<CountryDTO> findAll() {
		return service
				.findAll()
				.map(this::convertToDto)
				.doOnComplete(() -> log.debug(
						"Find all countries - {}"));
	}
	
	public CountryDTO convertToDto(Country country) {
		return mapper.map(country, CountryDTO.class);
	}

	public Country convertToEntity(CountryDTO dto) {
		return mapper.map(dto, Country.class);
	}
}