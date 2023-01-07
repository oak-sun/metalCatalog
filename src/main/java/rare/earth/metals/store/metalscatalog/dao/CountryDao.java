package rare.earth.metals.store.metalscatalog.dao;

import org.springframework.data.domain.Example;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import rare.earth.metals.store.metalscatalog.model.entities.Country;

import reactor.core.publisher.Flux;

public interface CountryDao extends ReactiveCrudRepository<Country, String> {

	Flux<Country> findAll(Example<Country> example);
}