package me.internalizable.services.country.response;

import me.internalizable.persistence.Country;

import java.io.Serializable;
import java.util.List;

public record DestinationResponse(List<Country> local, List<Country> regional) implements Serializable {
}
