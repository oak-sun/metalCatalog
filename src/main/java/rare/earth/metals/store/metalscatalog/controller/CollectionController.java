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

import rare.earth.metals.store.metalscatalog.model.entities.Collection;
import rare.earth.metals.store.metalscatalog.model.dto.CollectionDTO;
import rare.earth.metals.store.metalscatalog.service.impl.CollectionServiceImpl;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
public class CollectionController {
	@Autowired
	private CollectionServiceImpl service;
	@Autowired
	private ModelMapper mapper;

	@PostMapping("/collections")
	@ResponseStatus(HttpStatus.CREATED)
	public Mono<CollectionDTO> save(@RequestBody final CollectionDTO dto) {
		return service
                .save(convertToEntity(dto))
				.map(this::convertToDto)
				.doOnNext(o -> log.debug(
                        "Save collection - {}", o));
	}
	
	@PutMapping("/collections")
	@ResponseStatus(HttpStatus.OK)
	public Mono<CollectionDTO> update(@RequestBody final CollectionDTO dto) {
		return service
                .update(convertToEntity(dto))
				.map(this::convertToDto)
				.doOnNext(c -> log.debug(
                        "Save collection - {}", c));
	}

	@GetMapping("/collections/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Mono<CollectionDTO> findById(@PathVariable final String id) {
		return service
                .findById(id)
				.map(this::convertToDto)
				.doOnNext(c -> log.debug(
                        "Find collection by id - {}", c));
	}
	
	@PostMapping(path = "/collectionsByExample",
                 produces = MediaType.APPLICATION_NDJSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Flux<CollectionDTO> findAllByExample(@RequestBody final CollectionDTO dto) {
		return service
                .findAll(convertToEntity(dto))
				.map(this::convertToDto)
				.doOnNext(c -> log.debug(
                        "Find collections by example - {}", c));
	}
	
	@GetMapping(path = "/collections",
            produces = MediaType.APPLICATION_NDJSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Flux<CollectionDTO> findAll() {
		return service
                .findAll()
				.map(this::convertToDto)
				.doOnComplete(() -> log.debug(
                        "Find all collections - {}"));
	}
	
	public CollectionDTO convertToDto(Collection collection) {
		return mapper.map(collection, CollectionDTO.class);
	}

	public Collection convertToEntity(CollectionDTO dto) {
		return mapper.map(dto, Collection.class);
	}
}