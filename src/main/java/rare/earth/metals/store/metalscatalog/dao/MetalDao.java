package rare.earth.metals.store.metalscatalog.dao;

import org.springframework.data.domain.Example;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import rare.earth.metals.store.metalscatalog.model.entities.Metal;
import reactor.core.publisher.Flux;

public interface MetalDao extends ReactiveCrudRepository<Metal, String> {

	Flux<Metal> findAll(Example<Metal> example);
}