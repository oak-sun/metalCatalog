package rare.earth.metals.store.metalscatalog.unit.controller.metal;

import org.junit.Test;
import org.junit.runner.RunWith;
import reactor.core.publisher.Flux;
import java.util.List;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import rare.earth.metals.store.metalscatalog.controller.MetalController;
import rare.earth.metals.store.metalscatalog.model.dto.metal.Metal_OUT_DTO;
import rare.earth.metals.store.metalscatalog.service.impl.MetalServiceImpl;

@RunWith(SpringRunner.class)
@WebFluxTest(MetalController.class)
@Import(
		{MetalServiceImpl.class,
		 ModelMapper.class})
@AutoConfigureWebTestClient(timeout = "5000")
public class MetalControllerTest {
	@Autowired
	WebTestClient client;

	@MockBean
	private MetalServiceImpl service;

	@Test
	public void givenMetals_whenGetAll_thenReturn2() {
	     
		var m1 = PopulationControllerMetal
				.createMetalWithAllChildren("Hafnium");
		var m2 = PopulationControllerMetal
				.createMetalWithAllChildren("Thorium Collection");
		when(service.findAll())
				.thenReturn(Flux.just(m1, m2));
		client
				.get()
				.uri("/metals")
				.accept(MediaType
						.valueOf(MediaType.APPLICATION_NDJSON_VALUE))
				.exchange()
				.expectStatus()
				.isOk()
				.expectBodyList(Metal_OUT_DTO.class)
				.value(List::size, equalTo(2));
	}
}
