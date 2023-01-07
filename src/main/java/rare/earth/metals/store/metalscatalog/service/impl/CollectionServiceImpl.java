package rare.earth.metals.store.metalscatalog.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Component;

import rare.earth.metals.store.metalscatalog.service.CollectionService;
import rare.earth.metals.store.metalscatalog.util.exception.DBObjectNotFoundException;
import rare.earth.metals.store.metalscatalog.model.entities.Collection;
import rare.earth.metals.store.metalscatalog.dao.CollectionDao;
import rare.earth.metals.store.metalscatalog.util.beans.BeanUtil;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class CollectionServiceImpl implements CollectionService {

	@Autowired
	private CollectionDao dao;

	@Autowired
	private BeanUtil utils;

	@Override
	public Mono<Collection> save(final Collection collection) {
		return dao.save(collection);
	}

	@Override
	public Mono<Collection> update(final Collection collection) {
		return dao
				.findById(collection.getId())
				.map(collectionToUpdate -> {
			             utils
					.copyProperties(collectionToUpdate,
							        collection);
			return collectionToUpdate;
		})
				.flatMap(dao::save)
		        .switchIfEmpty(Mono.error(
						new DBObjectNotFoundException(
								"Collection", collection.getId())));
	}

	@Override
	public Mono<Collection> findById(final String id) {
		if (id == null) return Mono.empty(); 
		return dao.findById(id);
	}
	
	@Override
	public Flux<Collection> findAllById(List<String> ids) {
		if (ids == null) return Flux.empty();
		return dao.findAllById(ids);
	}

	@Override
	public Flux<Collection> findAll(final Collection collection) {
		var matcher = ExampleMatcher
				.matching()
				.withIgnoreNullValues()
				.withStringMatcher(StringMatcher.STARTING);
		Example<Collection> example = Example.of(collection, matcher);
		return dao.findAll(example);
	}

	@Override
	public Flux<Collection> findAll() {
		return dao.findAll();
	}
}
