package com.ctorres.pokequiz.dto.pokeapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Pokemon {

    private int id;
    private String name;

    @JsonProperty("base_experience")
    private int baseExperience;

    private int height;

    @JsonProperty("is_default")
    private boolean isDefault;

    private int order;
    private int weight;

    private List<AbilitySlot> abilities;
    private Cries cries;
    private List<FormRef> forms;

    @JsonProperty("game_indices")
    private List<GameIndex> gameIndices;

    @JsonProperty("location_area_encounters")
    private String locationAreaEncounters;

    private List<MoveEntry> moves;

    @JsonProperty("past_abilities")
    private List<PastAbility> pastAbilities;

    @JsonProperty("past_types")
    private List<PastType> pastTypes;

    private SpeciesRef species;
    private Sprites sprites;
    private List<StatEntry> stats;
    private List<TypeSlot> types;

    @JsonProperty("held_items")
    private List<HeldItem> heldItems;

    // ---- ctors
    public Pokemon() { }
    public Pokemon(int id, String name, int baseExperience, int height, boolean isDefault, int order, int weight,
                   List<AbilitySlot> abilities, Cries cries, List<FormRef> forms, List<GameIndex> gameIndices,
                   String locationAreaEncounters, List<MoveEntry> moves, List<PastAbility> pastAbilities,
                   List<PastType> pastTypes, SpeciesRef species, Sprites sprites, List<StatEntry> stats,
                   List<TypeSlot> types, List<HeldItem> heldItems) {
        this.id = id;
        this.name = name;
        this.baseExperience = baseExperience;
        this.height = height;
        this.isDefault = isDefault;
        this.order = order;
        this.weight = weight;
        this.abilities = abilities;
        this.cries = cries;
        this.forms = forms;
        this.gameIndices = gameIndices;
        this.locationAreaEncounters = locationAreaEncounters;
        this.moves = moves;
        this.pastAbilities = pastAbilities;
        this.pastTypes = pastTypes;
        this.species = species;
        this.sprites = sprites;
        this.stats = stats;
        this.types = types;
        this.heldItems = heldItems;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getBaseExperience() { return baseExperience; }
    public void setBaseExperience(int baseExperience) { this.baseExperience = baseExperience; }
    public int getHeight() { return height; }
    public void setHeight(int height) { this.height = height; }
    public boolean isDefault() { return isDefault; }
    public void setDefault(boolean aDefault) { isDefault = aDefault; }
    public int getOrder() { return order; }
    public void setOrder(int order) { this.order = order; }
    public int getWeight() { return weight; }
    public void setWeight(int weight) { this.weight = weight; }
    public List<AbilitySlot> getAbilities() { return abilities; }
    public void setAbilities(List<AbilitySlot> abilities) { this.abilities = abilities; }
    public Cries getCries() { return cries; }
    public void setCries(Cries cries) { this.cries = cries; }
    public List<FormRef> getForms() { return forms; }
    public void setForms(List<FormRef> forms) { this.forms = forms; }
    public List<GameIndex> getGameIndices() { return gameIndices; }
    public void setGameIndices(List<GameIndex> gameIndices) { this.gameIndices = gameIndices; }
    public String getLocationAreaEncounters() { return locationAreaEncounters; }
    public void setLocationAreaEncounters(String locationAreaEncounters) { this.locationAreaEncounters = locationAreaEncounters; }
    public List<MoveEntry> getMoves() { return moves; }
    public void setMoves(List<MoveEntry> moves) { this.moves = moves; }
    public List<PastAbility> getPastAbilities() { return pastAbilities; }
    public void setPastAbilities(List<PastAbility> pastAbilities) { this.pastAbilities = pastAbilities; }
    public List<PastType> getPastTypes() { return pastTypes; }
    public void setPastTypes(List<PastType> pastTypes) { this.pastTypes = pastTypes; }
    public SpeciesRef getSpecies() { return species; }
    public void setSpecies(SpeciesRef species) { this.species = species; }
    public Sprites getSprites() { return sprites; }
    public void setSprites(Sprites sprites) { this.sprites = sprites; }
    public List<StatEntry> getStats() { return stats; }
    public void setStats(List<StatEntry> stats) { this.stats = stats; }
    public List<TypeSlot> getTypes() { return types; }
    public void setTypes(List<TypeSlot> types) { this.types = types; }
    public List<HeldItem> getHeldItems() { return heldItems; }
    public void setHeldItems(List<HeldItem> heldItems) { this.heldItems = heldItems; }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class NamedAPIResource {
    private String name;
    private String url;

    public NamedAPIResource() { }
    public NamedAPIResource(String name, String url) { this.name = name; this.url = url; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class AbilityRef extends NamedAPIResource {
    public AbilityRef() { super(); }
    public AbilityRef(String name, String url) { super(name, url); }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class MoveRef extends NamedAPIResource {
    public MoveRef() { super(); }
    public MoveRef(String name, String url) { super(name, url); }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class VersionRef extends NamedAPIResource {
    public VersionRef() { super(); }
    public VersionRef(String name, String url) { super(name, url); }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class VersionGroupRef extends NamedAPIResource {
    public VersionGroupRef() { super(); }
    public VersionGroupRef(String name, String url) { super(name, url); }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class MoveLearnMethodRef extends NamedAPIResource {
    public MoveLearnMethodRef() { super(); }
    public MoveLearnMethodRef(String name, String url) { super(name, url); }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class TypeRef extends NamedAPIResource {
    public TypeRef() { super(); }
    public TypeRef(String name, String url) { super(name, url); }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class SpeciesRef extends NamedAPIResource {
    public SpeciesRef() { super(); }
    public SpeciesRef(String name, String url) { super(name, url); }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class FormRef extends NamedAPIResource {
    public FormRef() { super(); }
    public FormRef(String name, String url) { super(name, url); }
}

// ============== ABILITIES ==============
@JsonIgnoreProperties(ignoreUnknown = true)
class AbilitySlot {
    private AbilityRef ability;

    @JsonProperty("is_hidden")
    private boolean hidden;

    private int slot;

    public AbilitySlot() { }
    public AbilitySlot(AbilityRef ability, boolean hidden, int slot) {
        this.ability = ability; this.hidden = hidden; this.slot = slot;
    }

    public AbilityRef getAbility() { return ability; }
    public void setAbility(AbilityRef ability) { this.ability = ability; }
    public boolean isHidden() { return hidden; }
    public void setHidden(boolean hidden) { this.hidden = hidden; }
    public int getSlot() { return slot; }
    public void setSlot(int slot) { this.slot = slot; }
}

// ============== CRIES ==============
@JsonIgnoreProperties(ignoreUnknown = true)
class Cries {
    private String latest;
    private String legacy;

    public Cries() { }
    public Cries(String latest, String legacy) { this.latest = latest; this.legacy = legacy; }

    public String getLatest() { return latest; }
    public void setLatest(String latest) { this.latest = latest; }
    public String getLegacy() { return legacy; }
    public void setLegacy(String legacy) { this.legacy = legacy; }
}

// ============== GAME INDICES ==============
@JsonIgnoreProperties(ignoreUnknown = true)
class GameIndex {
    @JsonProperty("game_index")
    private int gameIndex;
    private VersionRef version;

    public GameIndex() { }
    public GameIndex(int gameIndex, VersionRef version) {
        this.gameIndex = gameIndex; this.version = version;
    }

    public int getGameIndex() { return gameIndex; }
    public void setGameIndex(int gameIndex) { this.gameIndex = gameIndex; }
    public VersionRef getVersion() { return version; }
    public void setVersion(VersionRef version) { this.version = version; }
}

// ============== MOVES ==============
@JsonIgnoreProperties(ignoreUnknown = true)
class MoveEntry {
    private MoveRef move;

    @JsonProperty("version_group_details")
    private List<VersionGroupDetail> versionGroupDetails;

    public MoveEntry() { }
    public MoveEntry(MoveRef move, List<VersionGroupDetail> versionGroupDetails) {
        this.move = move; this.versionGroupDetails = versionGroupDetails;
    }

    public MoveRef getMove() { return move; }
    public void setMove(MoveRef move) { this.move = move; }
    public List<VersionGroupDetail> getVersionGroupDetails() { return versionGroupDetails; }
    public void setVersionGroupDetails(List<VersionGroupDetail> versionGroupDetails) { this.versionGroupDetails = versionGroupDetails; }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class VersionGroupDetail {
    @JsonProperty("level_learned_at")
    private int levelLearnedAt;

    @JsonProperty("move_learn_method")
    private MoveLearnMethodRef moveLearnMethod;

    private Integer order;

    @JsonProperty("version_group")
    private VersionGroupRef versionGroup;

    public VersionGroupDetail() { }
    public VersionGroupDetail(int levelLearnedAt, MoveLearnMethodRef moveLearnMethod, Integer order, VersionGroupRef versionGroup) {
        this.levelLearnedAt = levelLearnedAt;
        this.moveLearnMethod = moveLearnMethod;
        this.order = order;
        this.versionGroup = versionGroup;
    }

    public int getLevelLearnedAt() { return levelLearnedAt; }
    public void setLevelLearnedAt(int levelLearnedAt) { this.levelLearnedAt = levelLearnedAt; }
    public MoveLearnMethodRef getMoveLearnMethod() { return moveLearnMethod; }
    public void setMoveLearnMethod(MoveLearnMethodRef moveLearnMethod) { this.moveLearnMethod = moveLearnMethod; }
    public Integer getOrder() { return order; }
    public void setOrder(Integer order) { this.order = order; }
    public VersionGroupRef getVersionGroup() { return versionGroup; }
    public void setVersionGroup(VersionGroupRef versionGroup) { this.versionGroup = versionGroup; }
}

// ============== TYPES ==============
@JsonIgnoreProperties(ignoreUnknown = true)
class TypeSlot {
    private int slot;
    private TypeRef type;

    public TypeSlot() { }
    public TypeSlot(int slot, TypeRef type) { this.slot = slot; this.type = type; }

    public int getSlot() { return slot; }
    public void setSlot(int slot) { this.slot = slot; }
    public TypeRef getType() { return type; }
    public void setType(TypeRef type) { this.type = type; }
}

// ============== STATS ==============
@JsonIgnoreProperties(ignoreUnknown = true)
class StatRef extends NamedAPIResource {
    public StatRef() { super(); }
    public StatRef(String name, String url) { super(name, url); }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class StatEntry {
    @JsonProperty("base_stat")
    private int baseStat;

    private int effort;
    private StatRef stat;

    public StatEntry() { }
    public StatEntry(int baseStat, int effort, StatRef stat) {
        this.baseStat = baseStat; this.effort = effort; this.stat = stat;
    }

    public int getBaseStat() { return baseStat; }
    public void setBaseStat(int baseStat) { this.baseStat = baseStat; }
    public int getEffort() { return effort; }
    public void setEffort(int effort) { this.effort = effort; }
    public StatRef getStat() { return stat; }
    public void setStat(StatRef stat) { this.stat = stat; }
}

// ============== HELD ITEMS ==============
@JsonIgnoreProperties(ignoreUnknown = true)
class HeldItem {
    private NamedAPIResource item;

    @JsonProperty("version_details")
    private List<HeldItemVersionDetail> versionDetails;

    public HeldItem() { }
    public HeldItem(NamedAPIResource item, List<HeldItemVersionDetail> versionDetails) {
        this.item = item; this.versionDetails = versionDetails;
    }

    public NamedAPIResource getItem() { return item; }
    public void setItem(NamedAPIResource item) { this.item = item; }
    public List<HeldItemVersionDetail> getVersionDetails() { return versionDetails; }
    public void setVersionDetails(List<HeldItemVersionDetail> versionDetails) { this.versionDetails = versionDetails; }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class HeldItemVersionDetail {
    private int rarity;
    private VersionRef version;

    public HeldItemVersionDetail() { }
    public HeldItemVersionDetail(int rarity, VersionRef version) { this.rarity = rarity; this.version = version; }

    public int getRarity() { return rarity; }
    public void setRarity(int rarity) { this.rarity = rarity; }
    public VersionRef getVersion() { return version; }
    public void setVersion(VersionRef version) { this.version = version; }
}

// ============== PAST ABILITIES/TYPES (minimal) ==============
@JsonIgnoreProperties(ignoreUnknown = true)
class PastAbility {
    private List<AbilitySlot> abilities;
    private NamedAPIResource generation;

    public PastAbility() { }
    public PastAbility(List<AbilitySlot> abilities, NamedAPIResource generation) {
        this.abilities = abilities; this.generation = generation;
    }

    public List<AbilitySlot> getAbilities() { return abilities; }
    public void setAbilities(List<AbilitySlot> abilities) { this.abilities = abilities; }
    public NamedAPIResource getGeneration() { return generation; }
    public void setGeneration(NamedAPIResource generation) { this.generation = generation; }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class PastType {
    private NamedAPIResource generation;
    private List<TypeSlot> types;

    public PastType() { }
    public PastType(NamedAPIResource generation, List<TypeSlot> types) {
        this.generation = generation; this.types = types;
    }

    public NamedAPIResource getGeneration() { return generation; }
    public void setGeneration(NamedAPIResource generation) { this.generation = generation; }
    public List<TypeSlot> getTypes() { return types; }
    public void setTypes(List<TypeSlot> types) { this.types = types; }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class OtherSprites {

    @JsonProperty("dream_world")
    private DreamWorldSprites dreamWorld;

    private HomeSprites home;

    @JsonProperty("official-artwork")
    private OfficialArtworkSprites officialArtwork;

    private ShowdownSprites showdown;

    public OtherSprites() { }
    public OtherSprites(DreamWorldSprites dreamWorld, HomeSprites home,
                        OfficialArtworkSprites officialArtwork, ShowdownSprites showdown) {
        this.dreamWorld = dreamWorld; this.home = home; this.officialArtwork = officialArtwork; this.showdown = showdown;
    }

    public DreamWorldSprites getDreamWorld() { return dreamWorld; }
    public void setDreamWorld(DreamWorldSprites dreamWorld) { this.dreamWorld = dreamWorld; }
    public HomeSprites getHome() { return home; }
    public void setHome(HomeSprites home) { this.home = home; }
    public OfficialArtworkSprites getOfficialArtwork() { return officialArtwork; }
    public void setOfficialArtwork(OfficialArtworkSprites officialArtwork) { this.officialArtwork = officialArtwork; }
    public ShowdownSprites getShowdown() { return showdown; }
    public void setShowdown(ShowdownSprites showdown) { this.showdown = showdown; }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class DreamWorldSprites {
    @JsonProperty("front_default")
    private String frontDefault;
    @JsonProperty("front_female")
    private String frontFemale;

    public DreamWorldSprites() { }
    public DreamWorldSprites(String frontDefault, String frontFemale) { this.frontDefault = frontDefault; this.frontFemale = frontFemale; }
    public String getFrontDefault() { return frontDefault; }
    public void setFrontDefault(String frontDefault) { this.frontDefault = frontDefault; }
    public String getFrontFemale() { return frontFemale; }
    public void setFrontFemale(String frontFemale) { this.frontFemale = frontFemale; }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class HomeSprites {
    @JsonProperty("front_default")
    private String frontDefault;
    @JsonProperty("front_female")
    private String frontFemale;
    @JsonProperty("front_shiny")
    private String frontShiny;
    @JsonProperty("front_shiny_female")
    private String frontShinyFemale;

    public HomeSprites() { }
    public HomeSprites(String frontDefault, String frontFemale, String frontShiny, String frontShinyFemale) {
        this.frontDefault = frontDefault; this.frontFemale = frontFemale; this.frontShiny = frontShiny; this.frontShinyFemale = frontShinyFemale;
    }
    public String getFrontDefault() { return frontDefault; }
    public void setFrontDefault(String frontDefault) { this.frontDefault = frontDefault; }
    public String getFrontFemale() { return frontFemale; }
    public void setFrontFemale(String frontFemale) { this.frontFemale = frontFemale; }
    public String getFrontShiny() { return frontShiny; }
    public void setFrontShiny(String frontShiny) { this.frontShiny = frontShiny; }
    public String getFrontShinyFemale() { return frontShinyFemale; }
    public void setFrontShinyFemale(String frontShinyFemale) { this.frontShinyFemale = frontShinyFemale; }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class OfficialArtworkSprites {
    @JsonProperty("front_default")
    private String frontDefault;
    @JsonProperty("front_shiny")
    private String frontShiny;

    public OfficialArtworkSprites() { }
    public OfficialArtworkSprites(String frontDefault, String frontShiny) { this.frontDefault = frontDefault; this.frontShiny = frontShiny; }
    public String getFrontDefault() { return frontDefault; }
    public void setFrontDefault(String frontDefault) { this.frontDefault = frontDefault; }
    public String getFrontShiny() { return frontShiny; }
    public void setFrontShiny(String frontShiny) { this.frontShiny = frontShiny; }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class ShowdownSprites {
    @JsonProperty("back_default")
    private String backDefault;
    @JsonProperty("back_female")
    private String backFemale;
    @JsonProperty("back_shiny")
    private String backShiny;
    @JsonProperty("back_shiny_female")
    private String backShinyFemale;
    @JsonProperty("front_default")
    private String frontDefault;
    @JsonProperty("front_female")
    private String frontFemale;
    @JsonProperty("front_shiny")
    private String frontShiny;
    @JsonProperty("front_shiny_female")
    private String frontShinyFemale;

    public ShowdownSprites() { }
    public ShowdownSprites(String backDefault, String backFemale, String backShiny, String backShinyFemale,
                           String frontDefault, String frontFemale, String frontShiny, String frontShinyFemale) {
        this.backDefault = backDefault; this.backFemale = backFemale; this.backShiny = backShiny; this.backShinyFemale = backShinyFemale;
        this.frontDefault = frontDefault; this.frontFemale = frontFemale; this.frontShiny = frontShiny; this.frontShinyFemale = frontShinyFemale;
    }

    public String getBackDefault() { return backDefault; }
    public void setBackDefault(String backDefault) { this.backDefault = backDefault; }
    public String getBackFemale() { return backFemale; }
    public void setBackFemale(String backFemale) { this.backFemale = backFemale; }
    public String getBackShiny() { return backShiny; }
    public void setBackShiny(String backShiny) { this.backShiny = backShiny; }
    public String getBackShinyFemale() { return backShinyFemale; }
    public void setBackShinyFemale(String backShinyFemale) { this.backShinyFemale = backShinyFemale; }
    public String getFrontDefault() { return frontDefault; }
    public void setFrontDefault(String frontDefault) { this.frontDefault = frontDefault; }
    public String getFrontFemale() { return frontFemale; }
    public void setFrontFemale(String frontFemale) { this.frontFemale = frontFemale; }
    public String getFrontShiny() { return frontShiny; }
    public void setFrontShiny(String frontShiny) { this.frontShiny = frontShiny; }
    public String getFrontShinyFemale() { return frontShinyFemale; }
    public void setFrontShinyFemale(String frontShinyFemale) { this.frontShinyFemale = frontShinyFemale; }
}
