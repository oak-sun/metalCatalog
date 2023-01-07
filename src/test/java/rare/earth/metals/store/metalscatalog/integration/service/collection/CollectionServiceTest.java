package rare.earth.metals.store.metalscatalog.integration.service.collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.validation.ValidationException;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import rare.earth.metals.store.metalscatalog.model.entities.Collection;
import rare.earth.metals.store.metalscatalog.dao.CollectionDao;
import rare.earth.metals.store.metalscatalog.service.CollectionService;
import rare.earth.metals.store.metalscatalog.util.exception.DBObjectNotFoundException;

@RunWith(SpringRunner.class)
@SpringBootTest
@DisplayName("Persistence testes for entity Collection")
public class CollectionServiceTest {
	@Autowired
	private CollectionDao dao;
	@Autowired
	private CollectionService service;

	@Before
	public void init() {
		dao.deleteAll().block();
	}

	@DisplayName("Test save with required field not blank")
	@Test
	public void whenSaveWithRequiredFieldNotBlank_thenReturnCollectionFindById() {
		var c = createCollection("Mad Max Collection");
		c = service.save(c).block();
		var found = service
				.findById(Objects.requireNonNull(c).getId())
				.block();
		assertThat(Objects.requireNonNull(found).getName())
				.isEqualTo(c.getName());
	}

	@DisplayName("Test save with required field blank")
	@Test
	public void whenSaveWithoutRequiredFieldNotBlank_thenThrowsException() {
		var exc = assertThrows(
				ValidationException.class, () -> {
			var c = createCollection("");
			service.save(c).block();
		});

		assertThat(exc.getMessage())
				.contains("Collection name is required.");

	}
	
	@DisplayName("Test save with required field null")
	@Test
	public void whenSaveWithoutRequiredFieldNotNull_thenThrowsException() {
		var ex = assertThrows(
				ValidationException.class, () -> {
			var c = createCollection(null);
			service.save(c).block();
		});
		assertThat(ex.getMessage())
				.contains("Collection name is required.");
	}

	@DisplayName("Test update with existing id and field not blank")
	@Test
	public void whenUpdateWithExistIdAndFieldNotBlank_thenUpdateCollection() {
		var c = createCollection("Mad Max Collection");
		c = service.save(c).block();
		assertThat(Objects.requireNonNull(c).getId())
				.isNotNull();
		var newName = "Mad Max Collection - Updated";
		c.setName(newName);
		var cUpdate = service.update(c).block();
		assertThat(Objects.requireNonNull(cUpdate).getId())
				.isEqualTo(c.getId());
		assertThat(cUpdate.getName()).isEqualTo(newName);
	}

	@DisplayName("Test update with existing id and field blank")
	@Test
	public void whenUptWithExistIdAndFieldBlank_thenThrowsException() {
		var ex = assertThrows(
				ValidationException.class, () -> {
			var c = createCollection("Mad Max Collection");
			c = service.save(c).block();
			assertThat(Objects.requireNonNull(c).getId())
					.isNotNull();
			c.setName("");
			service.update(c).block();
		});
		assertThat(ex.getMessage())
				.contains("Collection name is required.");
	}

	@DisplayName("Test update with not existing id and field not blank")
	@Test
	public void whenUptWithNotExistIdAndFieldNotBlank_thenThrowsException() {
		var idNotExists = "87re6t9876re9t87";
		var ex = assertThrows(
				DBObjectNotFoundException.class, () -> {
			var c = createCollection("Mad Max Collection");
			c.setId(idNotExists);
			c.setName("Mad Max Collection - Updated");
			service.update(c).block();
		});
		assertThat(ex.getMessage())
				.isEqualTo(
						(String.format("No %s found with the given id: %s.",
						"Collection",
						idNotExists)));
	}

	@DisplayName("Test FindAll Collection")
	@Test
	public void whenFindByAll_thenReturn4Collections() {
		List<Collection> cs = new ArrayList<>();
		cs.add(createCollection("Mad Max Collection"));
		cs.add(createCollection("Star Wars Collection"));
		cs.add(createCollection("Hannibal Lecter"));
		cs.add(createCollection("Mad Man"));
		dao.saveAll(cs).blockLast();
		List<Collection> found = service
				                     .findAll()
				                     .collectList()
				                     .block();
		assertThat(Objects.requireNonNull(found).size())
				.isEqualTo(4);
	}

	@DisplayName("Test FindAllByExample Collection")
	@Test
	public void whenFindAllByExample_thenReturn2Collections() {
		List<Collection> cs = new ArrayList<>();
		cs.add(createCollection("Mad Max Collection"));
		cs.add(createCollection("Star Wars Collection"));
		cs.add(createCollection("Hannibal Lecter"));
		cs.add(createCollection("Mad Man"));
		dao.saveAll(cs).blockLast();
		var cExample = createCollection("Mad");
		List<Collection> found = service
				.findAll(cExample)
				.collectList()
				.block();
		assertThat(Objects.requireNonNull(found).size())
				.isEqualTo(2);
	}

	private Collection createCollection(String name) {
		return Collection
				.builder()
				.name(name)
				.build();
	}
}
