package pages;

public enum Sorting {
	RELEVANCE("Релевантность"), 
	NAME_FROM_A_TO_Z("Названию: от А к Я"), 
	NAME_FROM_Z_TO_A("Названию: от Я к А"),
	PRICE_FROM_LOW_TO_HIGH("Цене: от низкой к высокой"), 
	PRICE_FROM_HIGH_TO_LOW("Цене: от высокой к низкой");

	private final String description;

	Sorting(String description) {
		this.description = description;
	}

	String getDescription() {
		return description;
	}

}
