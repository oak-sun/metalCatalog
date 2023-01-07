package rare.earth.metals.store.metalscatalog;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.mapping.event.ValidatingMongoEventListener;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.reactive.function.client.WebClient;
import rare.earth.metals.store.metalscatalog.util.DBInit;
import rare.earth.metals.store.metalscatalog.util.beans.NullAwareBeanUtils;
import rare.earth.metals.store.metalscatalog.model.response.Body;
import reactor.core.publisher.Flux;

import java.net.URI;

@SpringBootApplication
public class MetalsCatalogApplication {

	public static void main(String[] args) {
		
		var metalsApp = SpringApplication.run(
				MetalsCatalogApplication.class, args);
		var dbInit = metalsApp.getBean(DBInit.class);
		dbInit.init();
		Flux<Body> flux = WebClient
				             .create()
				             .get()
				             .uri(URI.create("https://httpbin.org/get"))
				             .retrieve()
				             .bodyToFlux(Body.class);
		flux.subscribe(System.out::println);
	}

	@Bean
	public LocalValidatorFactoryBean localValidatorFactoryBean() {
		return new LocalValidatorFactoryBean();
	}

	@Bean
	public ValidatingMongoEventListener validatingMongoEventListener(LocalValidatorFactoryBean lFactoryBean) {
		return new ValidatingMongoEventListener(lFactoryBean);
	}

	@Bean
	public BeanUtilsBean beansUtils(LocalValidatorFactoryBean lFactoryBean) {
		return new NullAwareBeanUtils();
	}

	@Bean
	public ModelMapper modelMapper() {
		var mapper = new ModelMapper();
		mapper
				.getConfiguration()
				.setMatchingStrategy(MatchingStrategies.STRICT)
				.setSkipNullEnabled(true);
		return mapper;
	}
}
