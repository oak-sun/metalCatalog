package rare.earth.metals.store.metalscatalog.dao;

import org.springframework.data.domain.Example;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import rare.earth.metals.store.metalscatalog.model.entities.Company;
import reactor.core.publisher.Flux;

public interface CompanyDao extends ReactiveCrudRepository<Company, String> {

	Flux<Company> findAll(Example<Company> example);
}