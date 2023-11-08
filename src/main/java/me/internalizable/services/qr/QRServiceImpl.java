package me.internalizable.services.qr;

import dev.morphia.Datastore;
import dev.morphia.query.filters.Filters;
import lombok.RequiredArgsConstructor;
import me.internalizable.persistence.Country;
import me.internalizable.persistence.QRCode;
import me.internalizable.utils.RandomUtils;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class QRServiceImpl extends UnicastRemoteObject implements QRService {

    private final Datastore datastore;

    public QRServiceImpl(Datastore datastore) throws RemoteException {
        this.datastore = datastore;
    }

    @Override
    public boolean validateQRCode(String code) throws RemoteException {
        long numberOfCodes = datastore.find(QRCode.class).filter(
                Filters.and(
                        Filters.eq("code", code),
                        Filters.eq("used", false)
                )
        ).count();

        return numberOfCodes != 0;
    }

    @Override
    public boolean registerQRCode(String code, String iso, String email) throws RemoteException {
        QRCode qrCode = datastore.find(QRCode.class).filter(
                Filters.and(
                        Filters.eq("code", code),
                        Filters.eq("used", false)
                )
        ).first();

        Country country = datastore.find(Country.class).filter(
                Filters.eq("iso", iso)
        ).first();

        if(qrCode == null || country == null) {
            return false;
        }

        qrCode.setUsed(true);
        qrCode.setEmail(email);
        qrCode.setCountry(country);

        qrCode.save(datastore);
        return true;
    }

    @Override
    public String createQRCode() throws RemoteException {
        String code = null;
        long qrCodeCount = 0;

        do {
            code = RandomUtils.generateRandomCode(16);

            qrCodeCount = datastore.find(QRCode.class).filter(
                    Filters.eq("code", code)
            ).count();
        } while(qrCodeCount != 0);

        QRCode qrCode = new QRCode();

        qrCode.setCode(code);
        qrCode.setUsed(false);
        qrCode.save(datastore);

        return code;
    }

}
