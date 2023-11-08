package me.internalizable.services.country;

import me.internalizable.services.country.requests.CountryCreationRequest;
import me.internalizable.services.country.response.DestinationResponse;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CountryService extends Remote {
    DestinationResponse getDestinations(String search) throws RemoteException;
    boolean createCountry(CountryCreationRequest country) throws RemoteException;
}
