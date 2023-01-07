package rare.earth.metals.store.metalscatalog.model.entities;

import java.math.BigDecimal;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(value = {
		"belongsToCollectionId",
		"productionCompaniesIds",
		"productionCountriesIds"
		 })
@Document(collection = "metals")
public class Metal {

	@Id
	private String id;

	@NotNull(message = "Price is required.")
	private BigDecimal price;

	@Transient
	private List<Company> productionCompanies;

	@NotNull(message = "Production companies is required.")
	private List<String> productionCompaniesIds;

	@Transient
	private List<Country> productionCountries;

	@NotNull(message = "Production countries is required.")
	private List<String> productionCountriesIds;

	@NotBlank(message = "Homepage is required.")
	private String homepage;

	@NotBlank(message = "Title is required.")
	private String title;

	@NotBlank(message = "Original title is required.")
	private String originalTitle;

	@NotBlank(message = "Overview is required.")
	private String overview;

	@NotBlank(message = "Status is required.")
	private String status;

	@Transient
	private Collection belongsToCollection;

	@NotNull(message = "Collection is required.")
	private String belongsToCollectionId;

	@NotNull(message = "Molecular Weight is required.")
	private double molecularWeight;

	private String appearance;

	@NotNull(message = "Melting Point in °C.")
	private int meltingPoint;

	@NotNull(message = "Boiling Point in °C.")
	private int boilingPoint;

	@NotNull(message = "Density in kg/m3.")
	private float density;

	@NotNull(message = "solubility in water = Y/N")
	private String solubilityInWater;

	@NotNull(message = "electrical resistivity")
	private float electricalResistivity;

	@NotNull(message = "electronegativity")
	private float electronegativity;
}
