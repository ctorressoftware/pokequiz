package com.ctorres.pokequiz.service.generator;

import java.util.Objects;

public class GeneratedQuestion {

    private String description;
    private String pokemonFrontImage;
    private String pokemonBackImage;

    public GeneratedQuestion() {
    }

    public GeneratedQuestion(String description) {
        this.description = description;
    }

    public GeneratedQuestion(String description, String pokemonFrontImage, String pokemonBackImage) {
        this.description = description;
        this.pokemonFrontImage = pokemonFrontImage;
        this.pokemonBackImage = pokemonBackImage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPokemonFrontImage() {
        return pokemonFrontImage;
    }

    public void setPokemonFrontImage(String pokemonFrontImage) {
        this.pokemonFrontImage = pokemonFrontImage;
    }

    public String getPokemonBackImage() {
        return pokemonBackImage;
    }

    public void setPokemonBackImage(String pokemonBackImage) {
        this.pokemonBackImage = pokemonBackImage;
    }

    @Override
    public String toString() {
        return "GeneratedQuestion{" +
                "description='" + description + '\'' +
                ", pokemonFrontImage='" + pokemonFrontImage + '\'' +
                ", pokemonBackImage='" + pokemonBackImage + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof GeneratedQuestion))
            return false;

        GeneratedQuestion that = (GeneratedQuestion) o;

        if (description != null ? !description.equals(that.description) : that.description != null)
            return false;
        if (pokemonFrontImage != null ? !pokemonFrontImage.equals(that.pokemonFrontImage)
                : that.pokemonFrontImage != null)
            return false;
        return pokemonBackImage != null ? pokemonBackImage.equals(that.pokemonBackImage)
                : that.pokemonBackImage == null;
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, pokemonFrontImage, pokemonBackImage);
    }

}
