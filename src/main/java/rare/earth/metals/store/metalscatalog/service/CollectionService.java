package rare.earth.metals.store.metalscatalog.service;

import java.util.List;
import rare.earth.metals.store.metalscatalog.model.entities.Collection;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CollectionService {

  Mono<Collection> save(Collection collection);
  
  Mono<Collection> update(Collection collection);
  
  Mono<Collection> findById(String id);
  
  Flux<Collection> findAllById(List<String> ids);
  
  Flux<Collection> findAll(Collection collection);
  
  Flux<Collection> findAll();

}