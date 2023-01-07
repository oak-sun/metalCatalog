package rare.earth.metals.store.metalscatalog.integration.service.metal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rare.earth.metals.store.metalscatalog.dao.*;
import rare.earth.metals.store.metalscatalog.model.entities.*;
import rare.earth.metals.store.metalscatalog.service.impl.*;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Component
public class PopulationServiceMetal {
	@Autowired
	private MetalDao metalDao;
	@Autowired
	private MetalServiceImpl metalService;
	@Autowired
	private CollectionDao collectionDao;

	@Autowired
	private CompanyDao companyDao;
	@Autowired
	private CountryDao countryDao;
	@Autowired
	private CollectionServiceImpl collectionService;

	@Autowired
	private CompanyServiceImpl companyService;

	@Autowired
	private CountryServiceImpl countryService;

	public Metal createAnSaveMetalWithAllChildren(String title) {

		var collection = createAndSaveCollection("Six's Period");
		var company1 = createAndSaveCompany("Polymetal");
		var company2 = createAndSaveCompany("Highland Gold Mining Ltd");
		var country1 = createAndSaveCountry("Russian Federation");
		var country2 = createAndSaveCountry("Canada");
		var m = Metal
				.builder()
				.homepage(String.format("www.%s.com.", title))
				.title(title)
				.originalTitle(String.format("%s - Original ", title))
				.overview(String.format("Overview of %s ", title))
				.price(new BigDecimal("57000000.53"))
				.status("Released")
				.belongsToCollectionId(collection.getId())
				.productionCompaniesIds(
						Arrays.asList(company1.getId(), company2.getId()))
				.productionCountriesIds(
						Arrays.asList(country1.getId(), country2.getId()));
		return metalService.save(m.build()).block();
	}

	public Metal createMetal(String title) {
		return Metal
				.builder()
				.homepage(String.format("www.%s.com.", title))
				.title(title)
				.originalTitle(String.format("%s - Original ", title))
				.overview(String.format("Overview of %s ", title))
				.price(new BigDecimal("57000000.53"))
				.status("Released")
				.build();
	}

	public Collection createAndSaveCollection(String name) {
		return collectionService.save(Collection
				.builder()
				.name(name)
				.build())
				.block();
	}

	public Company createAndSaveCompany(String name) {
		return companyService.save(Company
				.builder()
				.name(name)
				.originCountry("RUS")
				.build())
				.block();
	}

	public Country createAndSaveCountry(String name) {
		return countryService.save(Country
				.builder()
				.name(name)
				.iso31661("RUS")
				.build())
				.block();
	}

	public void saveAll(List<Metal> metal) {
		metalDao.saveAll(metal);
	}

	public void clearMongoCollections() {
		collectionDao.deleteAll();
		companyDao.deleteAll().block();
		countryDao.deleteAll().block();
		metalDao.deleteAll().block();
	}
}
