package com.ctorres.pokequiz.dto.pokeapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Evolution {

    @JsonProperty("baby_trigger_item")
    private NamedAPIResource babyTriggerItem;

    private ChainLink chain;
    private int id;

    public Evolution() {}

    public NamedAPIResource getBabyTriggerItem() { return babyTriggerItem; }
    public void setBabyTriggerItem(NamedAPIResource babyTriggerItem) { this.babyTriggerItem = babyTriggerItem; }

    public ChainLink getChain() { return chain; }
    public void setChain(ChainLink chain) { this.chain = chain; }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class NamedAPIResource {
        private String name;
        private String url;

        public NamedAPIResource() {}
        public NamedAPIResource(String name, String url) {
            this.name = name;
            this.url = url;
        }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getUrl() { return url; }
        public void setUrl(String url) { this.url = url; }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ChainLink {
        @JsonProperty("evolution_details")
        private List<EvolutionDetail> evolutionDetails;

        @JsonProperty("evolves_to")
        private List<ChainLink> evolvesTo;

        @JsonProperty("is_baby")
        private boolean isBaby;

        private NamedAPIResource species;

        public ChainLink() {}

        public List<EvolutionDetail> getEvolutionDetails() { return evolutionDetails; }
        public void setEvolutionDetails(List<EvolutionDetail> evolutionDetails) { this.evolutionDetails = evolutionDetails; }

        public List<ChainLink> getEvolvesTo() { return evolvesTo; }
        public void setEvolvesTo(List<ChainLink> evolvesTo) { this.evolvesTo = evolvesTo; }

        public boolean isBaby() { return isBaby; }
        public void setBaby(boolean baby) { isBaby = baby; }

        public NamedAPIResource getSpecies() { return species; }
        public void setSpecies(NamedAPIResource species) { this.species = species; }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class EvolutionDetail {
        private Integer gender;

        @JsonProperty("held_item")
        private NamedAPIResource heldItem;

        private NamedAPIResource item;

        @JsonProperty("known_move")
        private NamedAPIResource knownMove;

        @JsonProperty("known_move_type")
        private NamedAPIResource knownMoveType;

        private NamedAPIResource location;

        @JsonProperty("min_affection")
        private Integer minAffection;

        @JsonProperty("min_beauty")
        private Integer minBeauty;

        @JsonProperty("min_happiness")
        private Integer minHappiness;

        @JsonProperty("min_level")
        private Integer minLevel;

        @JsonProperty("needs_overworld_rain")
        private boolean needsOverworldRain;

        @JsonProperty("party_species")
        private NamedAPIResource partySpecies;

        @JsonProperty("party_type")
        private NamedAPIResource partyType;

        @JsonProperty("relative_physical_stats")
        private Integer relativePhysicalStats;

        @JsonProperty("time_of_day")
        private String timeOfDay;

        @JsonProperty("trade_species")
        private NamedAPIResource tradeSpecies;

        private NamedAPIResource trigger;

        @JsonProperty("turn_upside_down")
        private boolean turnUpsideDown;

        public EvolutionDetail() {}

        public Integer getGender() { return gender; }
        public void setGender(Integer gender) { this.gender = gender; }

        public NamedAPIResource getHeldItem() { return heldItem; }
        public void setHeldItem(NamedAPIResource heldItem) { this.heldItem = heldItem; }

        public NamedAPIResource getItem() { return item; }
        public void setItem(NamedAPIResource item) { this.item = item; }

        public NamedAPIResource getKnownMove() { return knownMove; }
        public void setKnownMove(NamedAPIResource knownMove) { this.knownMove = knownMove; }

        public NamedAPIResource getKnownMoveType() { return knownMoveType; }
        public void setKnownMoveType(NamedAPIResource knownMoveType) { this.knownMoveType = knownMoveType; }

        public NamedAPIResource getLocation() { return location; }
        public void setLocation(NamedAPIResource location) { this.location = location; }

        public Integer getMinAffection() { return minAffection; }
        public void setMinAffection(Integer minAffection) { this.minAffection = minAffection; }

        public Integer getMinBeauty() { return minBeauty; }
        public void setMinBeauty(Integer minBeauty) { this.minBeauty = minBeauty; }

        public Integer getMinHappiness() { return minHappiness; }
        public void setMinHappiness(Integer minHappiness) { this.minHappiness = minHappiness; }

        public Integer getMinLevel() { return minLevel; }
        public void setMinLevel(Integer minLevel) { this.minLevel = minLevel; }

        public boolean isNeedsOverworldRain() { return needsOverworldRain; }
        public void setNeedsOverworldRain(boolean needsOverworldRain) { this.needsOverworldRain = needsOverworldRain; }

        public NamedAPIResource getPartySpecies() { return partySpecies; }
        public void setPartySpecies(NamedAPIResource partySpecies) { this.partySpecies = partySpecies; }

        public NamedAPIResource getPartyType() { return partyType; }
        public void setPartyType(NamedAPIResource partyType) { this.partyType = partyType; }

        public Integer getRelativePhysicalStats() { return relativePhysicalStats; }
        public void setRelativePhysicalStats(Integer relativePhysicalStats) { this.relativePhysicalStats = relativePhysicalStats; }

        public String getTimeOfDay() { return timeOfDay; }
        public void setTimeOfDay(String timeOfDay) { this.timeOfDay = timeOfDay; }

        public NamedAPIResource getTradeSpecies() { return tradeSpecies; }
        public void setTradeSpecies(NamedAPIResource tradeSpecies) { this.tradeSpecies = tradeSpecies; }

        public NamedAPIResource getTrigger() { return trigger; }
        public void setTrigger(NamedAPIResource trigger) { this.trigger = trigger; }

        public boolean isTurnUpsideDown() { return turnUpsideDown; }
        public void setTurnUpsideDown(boolean turnUpsideDown) { this.turnUpsideDown = turnUpsideDown; }
    }
}