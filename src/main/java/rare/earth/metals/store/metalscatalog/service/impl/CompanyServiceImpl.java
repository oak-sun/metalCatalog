package rare.earth.metals.store.metalscatalog.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Component;
import rare.earth.metals.store.metalscatalog.model.entities.Company;
import rare.earth.metals.store.metalscatalog.dao.CompanyDao;
import rare.earth.metals.store.metalscatalog.service.CompanyService;
import rare.earth.metals.store.metalscatalog.util.beans.BeanUtil;

import rare.earth.metals.store.metalscatalog.util.exception.DBObjectNotFoundException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class CompanyServiceImpl implements CompanyService {

	@Autowired
	private CompanyDao dao;

	@Autowired
	private BeanUtil utils;
	
	@Override
	public Mono<Company> save(final Company company) {
		return dao.save(company);
	}

	@Override
	public Mono<Company> update(final Company company) {
		return dao
				.findById(company.getId())
				.map(companyToUpdate -> {
			          utils
					.copyProperties(companyToUpdate, company);
			return companyToUpdate;
		})
				.flatMap(dao::save)
				.switchIfEmpty(Mono.error(
						new DBObjectNotFoundException(
								"Company", company.getId())));
	}

	@Override
	public Mono<Company> findById(final String id) {
		if (id == null) return Mono.empty(); 
		return dao.findById(id);
	}
	
	@Override
	public Flux<Company> findAllById(List<String> ids) {
		if (ids == null) return Flux.empty();
		return dao.findAllById(ids);
	}

	@Override
	public Flux<Company> findAll(final Company company) {
		var matcher = ExampleMatcher
				.matching()
				.withIgnoreNullValues()
				.withStringMatcher(StringMatcher.STARTING);
		Example<Company> example = Example.of(company, matcher);
		return dao.findAll(example);
	}

	@Override
	public Flux<Company> findAll() {
		return dao.findAll();
	}
}
