package me.internalizable.services.country;

import dev.morphia.Datastore;
import dev.morphia.query.MorphiaCursor;
import dev.morphia.query.Query;
import dev.morphia.query.filters.Filters;
import lombok.RequiredArgsConstructor;
import me.internalizable.persistence.Country;
import me.internalizable.services.country.requests.CountryCreationRequest;
import me.internalizable.services.country.response.DestinationResponse;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.stream.Collectors;

public class CountryServiceImpl extends UnicastRemoteObject implements CountryService, Serializable {

    private final Datastore datastore;

    public CountryServiceImpl(Datastore datastore) throws RemoteException {
        this.datastore = datastore;
    }

    @Override
    public DestinationResponse getDestinations(String search) throws RemoteException {
        List<Country> countries = searchCountries(search);

        return new DestinationResponse(
                countries.stream().filter(c -> !c.isRegional()).collect(Collectors.toList()),
                countries.stream().filter(Country::isRegional).collect(Collectors.toList())
        );
    }

    @Override
    public boolean createCountry(CountryCreationRequest countryCreationRequest) throws RemoteException {
        Country country = new Country();

        country.setIso(countryCreationRequest.iso());
        country.setName(countryCreationRequest.name());
        country.setImage("https://firebasestorage.googleapis.com/v0/b/simly-dedfe.appspot.com/o/countries%2F" + countryCreationRequest.iso() + ".png?alt=media&token=59cdd766-c6d6-4787-a22a-55a62d571c2b");
        country.setRegional(countryCreationRequest.regional());

        country.save(datastore);

        return true;
    }

    public List<Country> searchCountries(String searchTerm) {
        try (MorphiaCursor<Country> cursor = datastore.find(Country.class)
                .filter(Filters.or(
                        Filters.regex("iso", ".*" + searchTerm + ".*"),
                        Filters.regex("name", ".*" + searchTerm + ".*")
                ))
                .iterator()) {
            return cursor.toList();
        }
    }
}
