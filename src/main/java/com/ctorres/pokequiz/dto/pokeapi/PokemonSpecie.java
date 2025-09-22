package com.ctorres.pokequiz.dto.pokeapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PokemonSpecie {

    private int id;
    private String name;
    private int order;

    @JsonProperty("gender_rate")
    private int genderRate;

    @JsonProperty("capture_rate")
    private int captureRate;

    @JsonProperty("base_happiness")
    private int baseHappiness;

    @JsonProperty("is_baby")
    private boolean isBaby;

    @JsonProperty("is_legendary")
    private boolean isLegendary;

    @JsonProperty("is_mythical")
    private boolean isMythical;

    @JsonProperty("hatch_counter")
    private int hatchCounter;

    @JsonProperty("has_gender_differences")
    private boolean hasGenderDifferences;

    @JsonProperty("forms_switchable")
    private boolean formsSwitchable;

    @JsonProperty("growth_rate")
    private NamedAPIResource growthRate;

    @JsonProperty("pokedex_numbers")
    private List<PokemonSpeciesDexEntry> pokedexNumbers;

    @JsonProperty("egg_groups")
    private List<NamedAPIResource> eggGroups;

    private NamedAPIResource color;
    private NamedAPIResource shape;

    @JsonProperty("evolves_from_species")
    private NamedAPIResource evolvesFromSpecies;

    @JsonProperty("evolution_chain")
    private APIResource evolutionChain;

    private NamedAPIResource habitat;
    private NamedAPIResource generation;

    private List<Name> names;

    @JsonProperty("pal_park_encounters")
    private List<PalParkEncounterArea> palParkEncounters;

    @JsonProperty("form_descriptions")
    private List<Description> formDescriptions;

    @JsonProperty("flavor_text_entries")
    private List<FlavorTextEntry> flavorTextEntries;

    private List<Genus> genera;

    private List<PokemonSpeciesVariety> varieties;

    public PokemonSpecie() {}

    // Getters & setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getOrder() { return order; }
    public void setOrder(int order) { this.order = order; }

    public int getGenderRate() { return genderRate; }
    public void setGenderRate(int genderRate) { this.genderRate = genderRate; }

    public int getCaptureRate() { return captureRate; }
    public void setCaptureRate(int captureRate) { this.captureRate = captureRate; }

    public int getBaseHappiness() { return baseHappiness; }
    public void setBaseHappiness(int baseHappiness) { this.baseHappiness = baseHappiness; }

    public boolean isBaby() { return isBaby; }
    public void setBaby(boolean baby) { isBaby = baby; }

    public boolean isLegendary() { return isLegendary; }
    public void setLegendary(boolean legendary) { isLegendary = legendary; }

    public boolean isMythical() { return isMythical; }
    public void setMythical(boolean mythical) { isMythical = mythical; }

    public int getHatchCounter() { return hatchCounter; }
    public void setHatchCounter(int hatchCounter) { this.hatchCounter = hatchCounter; }

    public boolean isHasGenderDifferences() { return hasGenderDifferences; }
    public void setHasGenderDifferences(boolean hasGenderDifferences) { this.hasGenderDifferences = hasGenderDifferences; }

    public boolean isFormsSwitchable() { return formsSwitchable; }
    public void setFormsSwitchable(boolean formsSwitchable) { this.formsSwitchable = formsSwitchable; }

    public NamedAPIResource getGrowthRate() { return growthRate; }
    public void setGrowthRate(NamedAPIResource growthRate) { this.growthRate = growthRate; }

    public List<PokemonSpeciesDexEntry> getPokedexNumbers() { return pokedexNumbers; }
    public void setPokedexNumbers(List<PokemonSpeciesDexEntry> pokedexNumbers) { this.pokedexNumbers = pokedexNumbers; }

    public List<NamedAPIResource> getEggGroups() { return eggGroups; }
    public void setEggGroups(List<NamedAPIResource> eggGroups) { this.eggGroups = eggGroups; }

    public NamedAPIResource getColor() { return color; }
    public void setColor(NamedAPIResource color) { this.color = color; }

    public NamedAPIResource getShape() { return shape; }
    public void setShape(NamedAPIResource shape) { this.shape = shape; }

    public NamedAPIResource getEvolvesFromSpecies() { return evolvesFromSpecies; }
    public void setEvolvesFromSpecies(NamedAPIResource evolvesFromSpecies) { this.evolvesFromSpecies = evolvesFromSpecies; }

    public APIResource getEvolutionChain() { return evolutionChain; }
    public void setEvolutionChain(APIResource evolutionChain) { this.evolutionChain = evolutionChain; }

    public NamedAPIResource getHabitat() { return habitat; }
    public void setHabitat(NamedAPIResource habitat) { this.habitat = habitat; }

    public NamedAPIResource getGeneration() { return generation; }
    public void setGeneration(NamedAPIResource generation) { this.generation = generation; }

    public List<Name> getNames() { return names; }
    public void setNames(List<Name> names) { this.names = names; }

    public List<PalParkEncounterArea> getPalParkEncounters() { return palParkEncounters; }
    public void setPalParkEncounters(List<PalParkEncounterArea> palParkEncounters) { this.palParkEncounters = palParkEncounters; }

    public List<Description> getFormDescriptions() { return formDescriptions; }
    public void setFormDescriptions(List<Description> formDescriptions) { this.formDescriptions = formDescriptions; }

    public List<FlavorTextEntry> getFlavorTextEntries() { return flavorTextEntries; }
    public void setFlavorTextEntries(List<FlavorTextEntry> flavorTextEntries) { this.flavorTextEntries = flavorTextEntries; }

    public List<Genus> getGenera() { return genera; }
    public void setGenera(List<Genus> genera) { this.genera = genera; }

    public List<PokemonSpeciesVariety> getVarieties() { return varieties; }
    public void setVarieties(List<PokemonSpeciesVariety> varieties) { this.varieties = varieties; }

    /* ===================== CLASES INTERNAS PÚBLICAS ===================== */

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class NamedAPIResource {
        private String name;
        private String url;

        public NamedAPIResource() {}
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getUrl() { return url; }
        public void setUrl(String url) { this.url = url; }
    }

    /** Recurso que solo trae url (p.ej. evolution_chain). */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class APIResource {
        private String url;

        public APIResource() {}
        public String getUrl() { return url; }
        public void setUrl(String url) { this.url = url; }
    }

    /** Entrada de flavor text por versión/idioma. */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class FlavorTextEntry {
        @JsonProperty("flavor_text")
        private String flavorText;
        private NamedAPIResource language;
        private NamedAPIResource version;

        public FlavorTextEntry() {}
        public String getFlavorText() { return flavorText; }
        public void setFlavorText(String flavorText) { this.flavorText = flavorText; }
        public NamedAPIResource getLanguage() { return language; }
        public void setLanguage(NamedAPIResource language) { this.language = language; }
        public NamedAPIResource getVersion() { return version; }
        public void setVersion(NamedAPIResource version) { this.version = version; }
    }

    /** Nombre localizado. */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Name {
        private NamedAPIResource language;
        private String name;

        public Name() {}
        public NamedAPIResource getLanguage() { return language; }
        public void setLanguage(NamedAPIResource language) { this.language = language; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
    }

    /** Descripción localizada (para forms). */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Description {
        private String description;
        private NamedAPIResource language;

        public Description() {}
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        public NamedAPIResource getLanguage() { return language; }
        public void setLanguage(NamedAPIResource language) { this.language = language; }
    }

    /** Género (Genus) por idioma. */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Genus {
        private String genus;
        private NamedAPIResource language;

        public Genus() {}
        public String getGenus() { return genus; }
        public void setGenus(String genus) { this.genus = genus; }
        public NamedAPIResource getLanguage() { return language; }
        public void setLanguage(NamedAPIResource language) { this.language = language; }
    }

    /** Número en una Pokédex específica. */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PokemonSpeciesDexEntry {
        @JsonProperty("entry_number")
        private int entryNumber;
        private NamedAPIResource pokedex;

        public PokemonSpeciesDexEntry() {}
        public int getEntryNumber() { return entryNumber; }
        public void setEntryNumber(int entryNumber) { this.entryNumber = entryNumber; }
        public NamedAPIResource getPokedex() { return pokedex; }
        public void setPokedex(NamedAPIResource pokedex) { this.pokedex = pokedex; }
    }

    /** Encuentros Pal Park. */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PalParkEncounterArea {
        private int baseScore;
        private int rate;
        private NamedAPIResource area;

        public PalParkEncounterArea() {}

        @JsonProperty("base_score")
        public int getBaseScore() { return baseScore; }
        public void setBaseScore(int baseScore) { this.baseScore = baseScore; }

        public int getRate() { return rate; }
        public void setRate(int rate) { this.rate = rate; }

        public NamedAPIResource getArea() { return area; }
        public void setArea(NamedAPIResource area) { this.area = area; }
    }

    /** Variedades (formas) disponibles. */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PokemonSpeciesVariety {
        @JsonProperty("is_default")
        private boolean isDefault;
        private NamedAPIResource pokemon;

        public PokemonSpeciesVariety() {}
        public boolean isDefault() { return isDefault; }
        public void setDefault(boolean aDefault) { isDefault = aDefault; }
        public NamedAPIResource getPokemon() { return pokemon; }
        public void setPokemon(NamedAPIResource pokemon) { this.pokemon = pokemon; }
    }
}