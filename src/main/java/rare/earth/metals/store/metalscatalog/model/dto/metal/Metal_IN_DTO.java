package rare.earth.metals.store.metalscatalog.model.dto.metal;

import java.math.BigDecimal;
import java.util.List;
import lombok.Builder;
import lombok.Data;


@Builder
@Data
public class Metal_IN_DTO {

	private String id;

	private String homepage;

	private String title;

	private String originalTitle;

	private String overview;

	private BigDecimal price;

	private String imdbId;

	private String status;

	private String belongsToCollectionId;

	private List<String> productionCompaniesIds;

	private List<String> productionCountriesIds;

	private double molecularWeight;

	private String appearance;

	private int meltingPoint;

	private int boilingPoint;

	private float density;

	private String solubilityInH2O;

	private float electricalResistivity;

	private float electronegativity;
}
