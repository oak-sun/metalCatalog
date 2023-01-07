package rare.earth.metals.store.metalscatalog.model.dto.metal;

import java.math.BigDecimal;
import java.util.List;
import lombok.Data;
import rare.earth.metals.store.metalscatalog.model.dto.CollectionDTO;
import rare.earth.metals.store.metalscatalog.model.dto.CompanyDTO;
import rare.earth.metals.store.metalscatalog.model.dto.CountryDTO;

@Data
public class Metal_OUT_DTO {

	private String id;

	private String homepage;

	private String title;

	private String originalTitle;

	private String overview;

	private BigDecimal price;

	private String status;

	private CollectionDTO belongsToCollection;

	private List<CompanyDTO> productionCompanies;

	private List<CountryDTO> productionCountries;

	private double molecularWeight;

	private String appearance;

	private int meltingPoint;

	private int boilingPoint;

	private float density;

	private String solubilityInH2O;

	private float electricalResistivity;

	private float electronegativity;

}
