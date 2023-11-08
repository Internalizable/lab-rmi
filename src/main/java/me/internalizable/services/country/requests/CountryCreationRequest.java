package me.internalizable.services.country.requests;

import java.io.Serializable;

public record CountryCreationRequest(String iso, String name, boolean regional) implements Serializable {

}
