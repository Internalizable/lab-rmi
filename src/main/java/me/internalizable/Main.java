package me.internalizable;

import me.internalizable.database.DatabaseProvider;
import me.internalizable.services.ServiceRegistrar;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class Main {
    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(1099);
            DatabaseProvider databaseProvider = new DatabaseProvider();

            ServiceRegistrar serviceRegistrar = new ServiceRegistrar(databaseProvider);
            serviceRegistrar.registerServices();

            System.out.println("RMI Server is running.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}