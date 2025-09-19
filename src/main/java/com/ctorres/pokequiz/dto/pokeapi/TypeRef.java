package com.ctorres.pokequiz.dto.pokeapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TypeRef extends NamedAPIResource {
    public TypeRef() { super(); }
    public TypeRef(String name, String url) { super(name, url); }
}