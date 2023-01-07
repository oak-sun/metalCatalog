package rare.earth.metals.store.metalscatalog.dao;

import org.springframework.data.domain.Example;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import rare.earth.metals.store.metalscatalog.model.entities.Collection;
import reactor.core.publisher.Flux;

public interface CollectionDao extends ReactiveCrudRepository<Collection, String> {

	Flux<Collection> findAll(Example<Collection> example);

}