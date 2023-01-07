package rare.earth.metals.store.metalscatalog.unit.controller.collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import rare.earth.metals.store.metalscatalog.controller.CollectionController;
import rare.earth.metals.store.metalscatalog.model.dto.CollectionDTO;
import rare.earth.metals.store.metalscatalog.service.impl.CollectionServiceImpl;
import rare.earth.metals.store.metalscatalog.unit.controller.metal.PopulationControllerMetal;
import reactor.core.publisher.Flux;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebFluxTest(CollectionController.class)
@Import({CollectionServiceImpl.class,
		ModelMapper.class})
@AutoConfigureWebTestClient(timeout = "5000")
public class CollectionControllerTest {
	
	@Autowired
	WebTestClient client;
	@MockBean
	private CollectionServiceImpl service;

	@Test
	public void givenMetals_whenGetAll_thenReturn2() {
		var collection1 = PopulationControllerMetal
				.createAndSaveCollection("Six's Period");
		var collection2 = PopulationControllerMetal
				.createAndSaveCollection("Seven's Period");
		
		when(service.findAll())
				.thenReturn(Flux.just(collection1,
						              collection2));
		client
				.get()
				.uri("/collections")
				.accept(MediaType.valueOf(MediaType.APPLICATION_NDJSON_VALUE))
				.exchange()
				.expectStatus()
				.isOk()
				.expectBodyList(CollectionDTO.class)
				.value(List::size, equalTo(2));
	}
}
