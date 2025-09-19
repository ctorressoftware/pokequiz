package com.ctorres.pokequiz.dto.pokeapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Type {

    @JsonProperty("damage_relations")
    private DamageRelations damageRelations;

    @JsonProperty("game_indices")
    private List<GameIndexEntry> gameIndices;

    private NamedAPIResource generation;

    private int id;

    @JsonProperty("move_damage_class")
    private NamedAPIResource moveDamageClass;

    private List<NamedAPIResource> moves;

    private String name;

    private List<NameEntry> names;

    @JsonProperty("past_damage_relations")
    private List<PastDamageRelationsEntry> pastDamageRelations;

    private List<TypePokemonEntry> pokemon;

    /** Claves dinámicas por generación y juego. */
    private Map<String, Map<String, TypeIcon>> sprites;

    public Type() {}

    public DamageRelations getDamageRelations() { return damageRelations; }
    public void setDamageRelations(DamageRelations damageRelations) { this.damageRelations = damageRelations; }

    public List<GameIndexEntry> getGameIndices() { return gameIndices; }
    public void setGameIndices(List<GameIndexEntry> gameIndices) { this.gameIndices = gameIndices; }

    public NamedAPIResource getGeneration() { return generation; }
    public void setGeneration(NamedAPIResource generation) { this.generation = generation; }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public NamedAPIResource getMoveDamageClass() { return moveDamageClass; }
    public void setMoveDamageClass(NamedAPIResource moveDamageClass) { this.moveDamageClass = moveDamageClass; }

    public List<NamedAPIResource> getMoves() { return moves; }
    public void setMoves(List<NamedAPIResource> moves) { this.moves = moves; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<NameEntry> getNames() { return names; }
    public void setNames(List<NameEntry> names) { this.names = names; }

    public List<PastDamageRelationsEntry> getPastDamageRelations() { return pastDamageRelations; }
    public void setPastDamageRelations(List<PastDamageRelationsEntry> pastDamageRelations) { this.pastDamageRelations = pastDamageRelations; }

    public List<TypePokemonEntry> getPokemon() { return pokemon; }
    public void setPokemon(List<TypePokemonEntry> pokemon) { this.pokemon = pokemon; }

    public Map<String, Map<String, TypeIcon>> getSprites() { return sprites; }
    public void setSprites(Map<String, Map<String, TypeIcon>> sprites) { this.sprites = sprites; }

    /* ===================== CLASES INTERNAS ===================== */

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DamageRelations {
        @JsonProperty("double_damage_from")
        private List<NamedAPIResource> doubleDamageFrom;
        @JsonProperty("double_damage_to")
        private List<NamedAPIResource> doubleDamageTo;
        @JsonProperty("half_damage_from")
        private List<NamedAPIResource> halfDamageFrom;
        @JsonProperty("half_damage_to")
        private List<NamedAPIResource> halfDamageTo;
        @JsonProperty("no_damage_from")
        private List<NamedAPIResource> noDamageFrom;
        @JsonProperty("no_damage_to")
        private List<NamedAPIResource> noDamageTo;

        public DamageRelations() {}
        public List<NamedAPIResource> getDoubleDamageFrom() { return doubleDamageFrom; }
        public void setDoubleDamageFrom(List<NamedAPIResource> v) { this.doubleDamageFrom = v; }
        public List<NamedAPIResource> getDoubleDamageTo() { return doubleDamageTo; }
        public void setDoubleDamageTo(List<NamedAPIResource> v) { this.doubleDamageTo = v; }
        public List<NamedAPIResource> getHalfDamageFrom() { return halfDamageFrom; }
        public void setHalfDamageFrom(List<NamedAPIResource> v) { this.halfDamageFrom = v; }
        public List<NamedAPIResource> getHalfDamageTo() { return halfDamageTo; }
        public void setHalfDamageTo(List<NamedAPIResource> v) { this.halfDamageTo = v; }
        public List<NamedAPIResource> getNoDamageFrom() { return noDamageFrom; }
        public void setNoDamageFrom(List<NamedAPIResource> v) { this.noDamageFrom = v; }
        public List<NamedAPIResource> getNoDamageTo() { return noDamageTo; }
        public void setNoDamageTo(List<NamedAPIResource> v) { this.noDamageTo = v; }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class GameIndexEntry {
        @JsonProperty("game_index")
        private int gameIndex;
        private NamedAPIResource generation;

        public GameIndexEntry() {}
        public int getGameIndex() { return gameIndex; }
        public void setGameIndex(int gameIndex) { this.gameIndex = gameIndex; }
        public NamedAPIResource getGeneration() { return generation; }
        public void setGeneration(NamedAPIResource generation) { this.generation = generation; }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class NameEntry {
        private NamedAPIResource language;
        private String name;

        public NameEntry() {}
        public NamedAPIResource getLanguage() { return language; }
        public void setLanguage(NamedAPIResource language) { this.language = language; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PastDamageRelationsEntry {
        @JsonProperty("damage_relations")
        private DamageRelations damageRelations;
        private NamedAPIResource generation;

        public PastDamageRelationsEntry() {}
        public DamageRelations getDamageRelations() { return damageRelations; }
        public void setDamageRelations(DamageRelations damageRelations) { this.damageRelations = damageRelations; }
        public NamedAPIResource getGeneration() { return generation; }
        public void setGeneration(NamedAPIResource generation) { this.generation = generation; }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class TypePokemonEntry {
        private NamedAPIResource pokemon;
        private int slot;

        public TypePokemonEntry() {}
        public NamedAPIResource getPokemon() { return pokemon; }
        public void setPokemon(NamedAPIResource pokemon) { this.pokemon = pokemon; }
        public int getSlot() { return slot; }
        public void setSlot(int slot) { this.slot = slot; }
    }

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

    /** Ícono por juego (campo name_icon). */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class TypeIcon {
        @JsonProperty("name_icon")
        private String nameIcon;

        public TypeIcon() {}
        public String getNameIcon() { return nameIcon; }
        public void setNameIcon(String nameIcon) { this.nameIcon = nameIcon; }
    }
}