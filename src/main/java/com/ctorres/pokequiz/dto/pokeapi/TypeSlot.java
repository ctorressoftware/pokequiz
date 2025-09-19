package com.ctorres.pokequiz.dto.pokeapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TypeSlot {
    private int slot;
    private TypeRef type;

    public TypeSlot() { }
    public TypeSlot(int slot, TypeRef type) { this.slot = slot; this.type = type; }

    public int getSlot() { return slot; }
    public void setSlot(int slot) { this.slot = slot; }
    public TypeRef getType() { return type; }
    public void setType(TypeRef type) { this.type = type; }
}