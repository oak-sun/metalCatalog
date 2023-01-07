package rare.earth.metals.store.metalscatalog.unit.controller.metal;

import org.springframework.stereotype.Component;
import rare.earth.metals.store.metalscatalog.model.entities.*;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.UUID;

@Component
public class PopulationControllerMetal {

	public static Metal createMetalWithAllChildren(String title) {
		var collection = createAndSaveCollection("Six's Period");
		var company1 = createAndSaveCompany("Polymetal");
		var company2 = createAndSaveCompany("Highland Gold Mining Ltd");
		var country1 = createAndSaveCountry("Russian Federation");
		var country2 = createAndSaveCountry("Canada");
		return Metal
				.builder()
				.id(UUID.randomUUID().toString())
				.homepage(String.format("www.%s.com.", title))
				.title(title)
				.originalTitle(String.format("%s - Original ", title))
				.overview(String.format("Overview of %s ", title))
				.price(new BigDecimal("57000000.53"))
				.status("Released")
				.belongsToCollectionId(collection.getId())
				.productionCompanies(Arrays.asList(company1, company2))
				.productionCountries(Arrays.asList(country1, country2))
				.build();
	}

	public static Collection createAndSaveCollection(String name) {
		return Collection
				.builder()
				.id(UUID.randomUUID().toString())
				.name(name).build();
	}

	private static Company createAndSaveCompany(String name) {
		return Company
				.builder()
				.id(UUID.randomUUID().toString())
				.name(name)
				.originCountry("RUS")
				.build();
	}

	private static Country createAndSaveCountry(String name) {
		return Country
				.builder()
				.id(UUID.randomUUID().toString())
				.name(name)
				.iso31661("RUS")
				.build();
	}
}
