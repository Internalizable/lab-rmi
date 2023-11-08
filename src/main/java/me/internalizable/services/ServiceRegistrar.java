package me.internalizable.services;

import lombok.RequiredArgsConstructor;
import me.internalizable.database.DatabaseProvider;
import me.internalizable.services.country.CountryService;
import me.internalizable.services.country.CountryServiceImpl;
import me.internalizable.services.qr.QRService;
import me.internalizable.services.qr.QRServiceImpl;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;

@RequiredArgsConstructor
public class ServiceRegistrar {

    private final DatabaseProvider databaseProvider;

    public void registerServices() {
        try {
            CountryService countryService = new CountryServiceImpl(databaseProvider.getDatastore());
            QRService qrService = new QRServiceImpl(databaseProvider.getDatastore());

            Naming.rebind("CountryService", countryService);
            Naming.rebind("QRService", qrService);
        } catch (RemoteException | MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
