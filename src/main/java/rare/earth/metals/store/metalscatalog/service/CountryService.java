package rare.earth.metals.store.metalscatalog.service;

import java.util.List;

import rare.earth.metals.store.metalscatalog.model.entities.Country;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CountryService{

  Mono<Country> save(Country country);
  
  Mono<Country> update(Country country);
  
  Mono<Country> findById(String id);
  
  Flux<Country> findAllById(List<String> ids);
  
  Flux<Country> findAll(Country country);
  
  Flux<Country> findAll();

}