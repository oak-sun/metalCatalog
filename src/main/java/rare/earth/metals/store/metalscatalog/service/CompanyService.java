package rare.earth.metals.store.metalscatalog.service;

import java.util.List;

import rare.earth.metals.store.metalscatalog.model.entities.Company;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CompanyService{

  Mono<Company> save(Company company);
  
  Mono<Company> update(Company company);
  
  Mono<Company> findById(String id);
  
  Flux<Company> findAllById(List<String> ids);
  
  Flux<Company> findAll(Company company);
  
  Flux<Company> findAll();

}