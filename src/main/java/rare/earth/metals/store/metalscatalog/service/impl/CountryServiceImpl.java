package rare.earth.metals.store.metalscatalog.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Component;
import rare.earth.metals.store.metalscatalog.model.entities.Country;
import rare.earth.metals.store.metalscatalog.dao.CountryDao;
import rare.earth.metals.store.metalscatalog.service.CountryService;
import rare.earth.metals.store.metalscatalog.util.beans.BeanUtil;
import rare.earth.metals.store.metalscatalog.util.exception.DBObjectNotFoundException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class CountryServiceImpl implements CountryService {

	@Autowired
	private CountryDao dao;

	@Autowired
	private BeanUtil utils;

	@Override
	public Mono<Country> save(final Country country) {
		return dao.save(country);
	}

	@Override
	public Mono<Country> update(final Country country) {
		return dao
				.findById(country.getId())
				.map(countryToUpdate -> {
			        utils
					     .copyProperties(countryToUpdate, country);
					return countryToUpdate;
		})
				.flatMap(dao::save)
		        .switchIfEmpty(Mono.error(
						new DBObjectNotFoundException("Country",
								country.getId())));
	}

	@Override
	public Mono<Country> findById(final String id) {
		if (id == null) return Mono.empty(); 
		return dao.findById(id);
	}
	
	@Override
	public Flux<Country> findAllById(List<String> ids) {
		if (ids == null) return Flux.empty();
		return dao.findAllById(ids);
	}

	@Override
	public Flux<Country> findAll(final Country country) {
		var matcher = ExampleMatcher
				.matching()
				.withIgnoreNullValues()
				.withStringMatcher(StringMatcher.STARTING);
		Example<Country> example = Example.of(country, matcher);
		return dao.findAll(example);
	}

	@Override
	public Flux<Country> findAll() {
		return dao.findAll();
	}
}
