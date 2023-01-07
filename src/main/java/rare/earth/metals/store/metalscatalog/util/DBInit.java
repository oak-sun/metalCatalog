package rare.earth.metals.store.metalscatalog.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rare.earth.metals.store.metalscatalog.model.entities.Collection;
import rare.earth.metals.store.metalscatalog.model.entities.Company;
import rare.earth.metals.store.metalscatalog.model.entities.Country;
import rare.earth.metals.store.metalscatalog.model.entities.Metal;
import rare.earth.metals.store.metalscatalog.service.impl.CollectionServiceImpl;
import rare.earth.metals.store.metalscatalog.service.impl.CompanyServiceImpl;
import rare.earth.metals.store.metalscatalog.service.impl.CountryServiceImpl;
import rare.earth.metals.store.metalscatalog.service.impl.MetalServiceImpl;
import java.math.BigDecimal;
import java.util.Arrays;

@Component
public class DBInit {
	private final MetalServiceImpl metalService;
	private final CollectionServiceImpl collectionService;
	private final CompanyServiceImpl companyService;

	private final CountryServiceImpl countryService;
@Autowired
	public DBInit(MetalServiceImpl metalService,
				  CollectionServiceImpl collectionService,
				  CompanyServiceImpl companyService,
				  CountryServiceImpl countryService) {
		this.metalService = metalService;
		this.collectionService = collectionService;
		this.companyService = companyService;
		this.countryService = countryService;
	}

	public void init() {
		createChildren();
		createMetal("Hafnium");
		createMetal("Dysprosium");
	}

	private Collection collection = null;
	private Company company1 = null;
	private Company company2 = null;
	private Country country1 = null;
	private Country country2 = null;

	
	private void createMetal(String title) {
		var metal = Metal
				.builder()
				.homepage(String.format("www.%s.com.", title))
				.title(title)
				.originalTitle(String.format("%s - Original ", title))
				.overview(String.format("Overview of %s ", title))
				.price(new BigDecimal("57000000.53"))
				.status("Released")
				.belongsToCollectionId(collection.getId())
				.productionCompaniesIds(
						Arrays.asList(company1.getId(),
								     company2.getId()))
				.productionCountriesIds
						(Arrays.asList(country1.getId(),
								      country2.getId()));
		metalService.save(metal.build()).block();
	}
	
	private void createChildren() {
		collection = createAndSaveCollection();
		company1 = createAndSaveCompany("Polymetal");
		company2 = createAndSaveCompany("Highland Gold Mining Ltd");
		country1 = createAndSaveCountry("Russian Federation");
		country2 = createAndSaveCountry("Canada");
	}
	
	private Collection createAndSaveCollection() {
		return collectionService
				.save(Collection
						.builder()
						.name("Six's Period")
						.build())
				.block();
	}

	private Company createAndSaveCompany(String name) {
		return companyService
				.save(Company
						.builder()
						.name(name)
						.originCountry("RUS")
						.build())
				.block();
	}

	private Country createAndSaveCountry(String name) {
		return countryService
				.save(Country
						.builder()
						.name(name)
						.iso31661("RUS")
						.build())
				.block();
	}
}