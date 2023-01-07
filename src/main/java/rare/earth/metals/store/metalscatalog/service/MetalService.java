package rare.earth.metals.store.metalscatalog.service;

import rare.earth.metals.store.metalscatalog.model.entities.Metal;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MetalService {

  Mono<Metal> save(Metal metal);
  
  Mono<Metal> update(Metal metal);
  
  Mono<Metal> findById(String id);
  
  Flux<Metal> findAll(Metal metal);
  
  Flux<Metal> findAll();
}