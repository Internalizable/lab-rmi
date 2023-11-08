package me.internalizable.services.qr;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface QRService extends Remote {
    boolean validateQRCode(String code) throws RemoteException;
    boolean registerQRCode(String code, String iso, String email) throws RemoteException;
    String createQRCode() throws RemoteException;
}
