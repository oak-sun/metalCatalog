package rare.earth.metals.store.metalscatalog.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Component;
import rare.earth.metals.store.metalscatalog.model.entities.Metal;
import rare.earth.metals.store.metalscatalog.dao.MetalDao;
import rare.earth.metals.store.metalscatalog.service.MetalService;
import rare.earth.metals.store.metalscatalog.util.beans.BeanUtil;
import rare.earth.metals.store.metalscatalog.util.exception.DBObjectNotFoundException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class MetalServiceImpl implements MetalService {

	private final MetalDao dao;
	private final CollectionServiceImpl collectionService;

	private final CompanyServiceImpl companyService;

	private final CountryServiceImpl countryService;

	private final BeanUtil utils;

	@Autowired
	public MetalServiceImpl(MetalDao dao,
							CollectionServiceImpl collectionService,
							CompanyServiceImpl companyService,
							CountryServiceImpl countryService,
							BeanUtil utils) {
		this.dao = dao;
		this.collectionService = collectionService;
		this.companyService = companyService;
		this.countryService = countryService;
		this.utils = utils;
	}

	@Override
	public Mono<Metal> save(Metal metal) {
		return dao
				.save(metal)
				.flatMap(composeMono());
	}

	@Override
	public Mono<Metal> update(final Metal metal) {
		return dao
				.findById(metal.getId())
				.map(metalToUpdate -> {
			            utils
					    .copyProperties(metalToUpdate,
							             metal);
			            return metalToUpdate;
		})
				.flatMap(dao::save)
		.switchIfEmpty(Mono.error(
				new DBObjectNotFoundException(
						"Metal", metal.getId())))
		.flatMap(composeMono());
	}

	@Override
	public Mono<Metal> findById(final String id) {
		if (id == null) return Mono.empty();
		return dao
				.findById(id)
				.flatMap(composeMono());
	}

	@SuppressWarnings("All")
	@Override
	public Flux<Metal> findAll(final Metal metal) {

		var matcher = ExampleMatcher
				.matching()
				.withIgnoreNullValues()
				.withStringMatcher(
						StringMatcher.STARTING)
				.withMatcher("productionCompaniesIds",
						match -> match
								.transform(source ->
					Optional.of(((List) source
							.get())
							.iterator()
							.next())
				)
								.contains())

				.withMatcher("productionCountriesIds",
						match -> match
								.transform(source ->
					Optional.of(((List) source
							.get())
							.iterator()
							.next())
				)
								.contains());

		return dao
				.findAll(Example.of(metal, matcher))
				.flatMap(composeMono());

	}

	@Override
	public Flux<Metal> findAll() {
		return dao
				.findAll()
				.flatMap(composeMono());
	}
	
	private Function<? super Metal,
		   	? extends Mono<? extends Metal>>
	                               composeMono() {
		return met -> Mono
				.just(met)
		         .zipWith(collectionService
				       .findById(met
							   .getBelongsToCollectionId()),
						 (u, p) -> {
		                	u.setBelongsToCollection(p);
			    return u;
		})
				.zipWith(companyService
						.findAllById(met
								.getProductionCompaniesIds())
						.collectList(), (u, p) -> {
			             u.setProductionCompanies(p);
			return u;
		})
				.zipWith(countryService
						.findAllById(met
								.getProductionCountriesIds())
						.collectList(), (u, p) -> {
			            u.setProductionCountries(p);
			return u;
		});
	}
}
