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

import rare.earth.metals.store.metalscatalog.model.entities.Company;
import rare.earth.metals.store.metalscatalog.model.dto.CompanyDTO;
import rare.earth.metals.store.metalscatalog.service.impl.CompanyServiceImpl;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
public class CompanyController {

	@Autowired
	private CompanyServiceImpl service;
	
	@Autowired
	private ModelMapper mapper;

	@PostMapping("/companies")
	@ResponseStatus(HttpStatus.CREATED)
	public Mono<CompanyDTO> save(@RequestBody final CompanyDTO dto) {
		return service
				.save(convertToEntity(dto))
				.map(this::convertToDto)
				.doOnNext(o -> log.debug(
						"Save company - {}", o));
	}
	
	@PutMapping("/companies")
	@ResponseStatus(HttpStatus.OK)
	public Mono<CompanyDTO> update(@RequestBody final CompanyDTO dto) {
		return service
				.update(convertToEntity(dto))
				.map(this::convertToDto)
				.doOnNext(c -> log.debug(
						"Save company - {}", c));
	}

	@GetMapping("/companies/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Mono<CompanyDTO> findById(@PathVariable final String id) {
		return service
				.findById(id)
				.map(this::convertToDto)
				.doOnNext(c -> log.debug(
						"Find company by id - {}", c));
	}
	
	@PostMapping(path = "/companiesByExample",
			     produces = MediaType.APPLICATION_NDJSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Flux<CompanyDTO> findAllByExample(@RequestBody final CompanyDTO dto) {
		return service
				.findAll(convertToEntity(dto))
				.map(this::convertToDto)
				.doOnNext(c -> log.debug(
						"Find companies by example - {}", c));
	}
	
	@GetMapping(path = "/companies",
			produces = MediaType.APPLICATION_NDJSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Flux<CompanyDTO> findAll() {
		return service
				.findAll()
				.map(this::convertToDto)
				.doOnComplete(() -> log.debug(
						"Find all companies - {}"));
	}
	
	public CompanyDTO convertToDto(Company company) {
		return mapper.map(company, CompanyDTO.class);
	}

	public Company convertToEntity(CompanyDTO dto) {
		return mapper.map(dto, Company.class);
	}
}