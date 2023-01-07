package rare.earth.metals.store.metalscatalog.integration.service.metal;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import rare.earth.metals.store.metalscatalog.model.entities.Metal;
import rare.earth.metals.store.metalscatalog.service.MetalService;

import javax.validation.ValidationException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;



@RunWith(SpringRunner.class)
@SpringBootTest
@DisplayName("Persistence testes for entity Metal")
public class MetalServiceTest {
	@Autowired
	private MetalService service;

	@Autowired
	private PopulationServiceMetal test;
	
	@Before
	public void init() {
		test.clearMongoCollections();
	}

	@DisplayName("Test save cascade with required fields not null and not blank")
	@Test
	public void whenSaveCascadeWithRequiredFieldsNotBlank_thenReturnMetalFindById() {
		var title = "Thorium metal";
		var m = test.createAnSaveMetalWithAllChildren(title);
		var found = service.findById(m.getId()).block();
		assertThat(Objects.requireNonNull(found).getTitle())
				.isEqualTo(m.getTitle());
		assertThat(found.getOriginalTitle())
				.isEqualTo(String.format(
						"%s - Original ", title));
		assertThat(found.getPrice())
				.isEqualTo(new BigDecimal("57000000.53"));
		assertThat(found.getProductionCompanies().size())
				.isEqualTo(2);
		assertThat(found.getProductionCountries().size())
				.isEqualTo(2);
	}

	@DisplayName("Test save cascade with required fields blank and null")
	@Test
	public void whenSaveCascadeWithoutRequiredFieldsNotBlank_thenThrowsException() {
		var m = new Metal();
		m.setTitle(null);
		var exc = assertThrows(
				ValidationException.class, () -> service.save(m).block());
		assertThat(exc.getMessage())
				.contains("Production companies is required.");
		assertThat(exc.getMessage())
				.contains("Homepage is required.");
		assertThat(exc.getMessage())
				.contains("Title is required.");
		assertThat(exc.getMessage())
				.contains("Collection is required.");
		assertThat(exc.getMessage())
				.contains("Status is required.");
		assertThat(exc.getMessage())
				.contains("Production countries is required.");
		assertThat(exc.getMessage())
				.contains("Price is required.");
		assertThat(exc.getMessage())
				.contains("Original title is required.");
		assertThat(exc.getMessage())
				.contains("Overview is required.");
	}
	
	@DisplayName("Test save with required fields not null and not blank")
	@Test
	public void whenSaveWithRequiredFieldsNotBlank_thenReturnMetalFindById() {
		var title = "Thorium metal";
		var m = test.createMetal(title);
		var collectionName = "Six's Period";
		var collection = test.createAndSaveCollection(collectionName);
		var companyName = "RusDragMet";
		var company = test.createAndSaveCompany(companyName);
		var countryName = "Russian Federation";
		var country = test.createAndSaveCountry(countryName);
		m.setBelongsToCollectionId(collection.getId());
		m.setProductionCompaniesIds(Collections.singletonList(company.getId()));
		m.setProductionCountriesIds(Collections.singletonList(country.getId()));
		m = service.save(m).block();
		var found = service
			                  	.findById(Objects.requireNonNull(m).getId())
			                	.block();
		assertThat(Objects.requireNonNull(found).getTitle())
				.isEqualTo(m.getTitle());
		assertThat(found.getOriginalTitle())
				.isEqualTo(String.format("%s - Original ", title));
		assertThat(found.getPrice())
				.isEqualTo(new BigDecimal("57000000.53"));
		assertThat(found.getBelongsToCollection())
				.isNotNull();
		assertThat(found.getBelongsToCollection().getName())
				.isEqualTo(collectionName);
		assertThat(found.getProductionCompanies().size())
				.isEqualTo(1);
		assertThat(found
				     .getProductionCompanies()
				     .iterator()
				     .next()
				     .getName())
				.isEqualTo(companyName);
		assertThat(found.getProductionCountries().size())
				.isEqualTo(1);
		assertThat(found
				        .getProductionCountries()
				        .iterator()
				        .next()
				        .getName())
				.isEqualTo(countryName);
		
	}

	@DisplayName("Test update with existing id and field not blank")
	@Test
	public void whenUpdateWithExistIdAndFieldNotBlank_thenUpdateCollection() {
		var m = test.createAnSaveMetalWithAllChildren("Thorium metal");
		assertThat(m.getId()).isNotNull();
		var newTitle = "Thorium Collection - Updated";
		m.setTitle(newTitle);
		m.setBelongsToCollectionId(m.getBelongsToCollection().getId());
		m.setProductionCompaniesIds(
				Collections.singletonList(m
						.getProductionCompanies()
						.iterator()
						.next()
						.getId()));
		m.setProductionCountriesIds(
				Collections.singletonList(m
						.getProductionCountries()
						.iterator()
						.next()
						.getId()));
		var mUpdate = service.update(m).block();
		assertThat(Objects.requireNonNull(mUpdate).getId())
				.isEqualTo(m.getId());
		assertThat(mUpdate.getTitle())
				.isEqualTo(newTitle);
		assertThat(mUpdate.getProductionCompanies().size())
				.isEqualTo(1);
		assertThat(mUpdate.getProductionCountries().size())
				.isEqualTo(1);
	}

	@DisplayName("Test update with existing id and field blank and null")
	@Test
	public void whenUpdateWithExistIdAndFieldBlankAndNull_thenThrowsException() {
		var exc = assertThrows(
				ValidationException.class, () -> {
			var m = test
					.createAnSaveMetalWithAllChildren("Thorium metal");
			assertThat(m.getId()).isNotNull();
			var newTitle = "";
			m.setTitle(newTitle);
			var mU = service.update(m).block();
			System.out.println(mU);
		});
		assertThat(exc.getMessage())
				.contains("title: Title is required.");
	}

	@DisplayName("Test FindAll metal")
	@Test
	public void whenFindAll_thenReturn4Metals() {
		List<Metal> ms = new ArrayList<>();
		ms.add(test
				.createAnSaveMetalWithAllChildren("Thorium metal"));
		ms.add(test
				.createAnSaveMetalWithAllChildren("Seaborgium metal"));
		ms.add(test
				.createAnSaveMetalWithAllChildren("Hafnium"));
		ms.add(test
				.createAnSaveMetalWithAllChildren("Terbium"));
		test.saveAll(ms);
		List<Metal> found1 = service
				.findAll()
				.collectList()
				.block();
		assertThat(Objects.requireNonNull(found1).size())
				.isEqualTo(4);
	}

	@DisplayName("Test FindAllByExample metal")
	@Test
	public void whenFindByExample_thenReturnMetals() {
		List<Metal> ms = new ArrayList<>();
		ms.add(test
				.createAnSaveMetalWithAllChildren("Thorium metal"));
		ms.add(test
				.createAnSaveMetalWithAllChildren("Seaborgium metal"));
		ms.add(test
				.createAnSaveMetalWithAllChildren("Hafnium"));
		ms.add(test
				.createAnSaveMetalWithAllChildren("Terbium"));
		test.saveAll(ms);
		var nonexistentId = "7bd7e93a-b4d8-11ea-b3de-0242ac130004";
		List<Metal> foundByTitle = service
				.findAll(Metal
						.builder()
						.title("T")
						.build())
				.collectList()
				.block();
		assertThat(Objects.requireNonNull(foundByTitle).size())
				.isEqualTo(2);
		List<Metal> foundByCollection = service
				.findAll(Metal.builder().belongsToCollectionId(
								ms
								.iterator()
								.next()
								.getBelongsToCollection()
								.getId())
						.build())
				.collectList()
				.block();
		assertThat(Objects.requireNonNull(foundByCollection).size())
				.isEqualTo(1);
		List<Metal> notFoundByCollection = service
				.findAll(Metal
						.builder()
						.belongsToCollectionId(nonexistentId)
						.build())
				.collectList()
				.block();
		assertThat(Objects.requireNonNull(notFoundByCollection).size())
				.isEqualTo(0);
		List<Metal> foundByCompany = service
				.findAll(Metal.builder().productionCompaniesIds(
								Collections.singletonList(
										ms
										.iterator()
										.next()
										.getProductionCompanies()
										.iterator()
										.next()
										.getId()))
						.build())
				.collectList()
				.block();
		assertThat(Objects.requireNonNull(foundByCompany).size())
				.isEqualTo(1);
		List<Metal> notFoundByCompany = service
				.findAll(Metal
						.builder()
						.productionCompaniesIds(List.of(nonexistentId))
						.build())
				.collectList()
				.block();
		assertThat(Objects
				.requireNonNull(notFoundByCompany).size())
				.isEqualTo(0);
		List<Metal> foundByCountry = service
				.findAll(Metal.builder().productionCountriesIds(
								Collections.singletonList(
										ms
										.iterator()
										.next()
										.getProductionCountries()
										.iterator()
										.next()
										.getId()))
						.build())
				.collectList()
				.block();
		assertThat(Objects
				.requireNonNull(foundByCountry).size())
				.isEqualTo(1);
		List<Metal> notFoundByCountry = service
				.findAll(Metal
						.builder()
						.productionCountriesIds(List.of(nonexistentId))
						.build())
				.collectList()
				.block();
		assertThat(Objects
				.requireNonNull(notFoundByCountry).size())
				.isEqualTo(0);

	}
}
