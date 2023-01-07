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
import rare.earth.metals.store.metalscatalog.model.entities.Metal;
import rare.earth.metals.store.metalscatalog.model.dto.metal.Metal_IN_DTO;
import rare.earth.metals.store.metalscatalog.model.dto.metal.Metal_OUT_DTO;
import rare.earth.metals.store.metalscatalog.service.impl.MetalServiceImpl;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
public class MetalController {

	@Autowired
	private MetalServiceImpl service;
	
	@Autowired
	private ModelMapper mapper;

	@PostMapping("/metals")
	@ResponseStatus(HttpStatus.CREATED)
	public Mono<Metal_OUT_DTO> save(@RequestBody final Metal_IN_DTO dto) {
		return service
				.save(convertToEntity(dto))
				.map(this::convertToDto)
				.doOnNext(o -> log.debug(
						"Save metal - {}", o));
	}
	
	@PutMapping("/metals")
	@ResponseStatus(HttpStatus.OK)
	public Mono<Metal_OUT_DTO> update(@RequestBody final Metal_IN_DTO dto) {
		return service
				.update(convertToEntity(dto))
				.map(this::convertToDto)
				.doOnNext(c -> log.debug("Save metal - {}", c));
	}

	@GetMapping("/metals/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Mono<Metal_OUT_DTO> findById(@PathVariable final String id) {
		return service
				.findById(id)
				.map(this::convertToDto)
				.doOnNext(c -> log.debug(
						"Find metal by id - {}", c));
	}
	
	@PostMapping(path = "/metalsByExample",
			produces = MediaType.APPLICATION_NDJSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Flux<Metal_OUT_DTO> findAllByExample(@RequestBody final Metal_IN_DTO dto) {
		return service
				.findAll(convertToEntity(dto))
				.map(this::convertToDto)
				.doOnNext(c -> log.debug(
						"Find metals by example - {}", c));
	}
	
	@GetMapping(path = "/metals",
			produces = MediaType.APPLICATION_NDJSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Flux<Metal_OUT_DTO> findAll() {
		return service
				.findAll()
				.map(this::convertToDto)
				.doOnComplete(() -> log.debug(
						"Find all metals - {}"));
	}
	
	public Metal_OUT_DTO convertToDto(Metal metal) {
		return mapper.map(metal, Metal_OUT_DTO.class);
	}

	public Metal convertToEntity(Metal_IN_DTO dto) {
		return mapper.map(dto, Metal.class);
	}
}