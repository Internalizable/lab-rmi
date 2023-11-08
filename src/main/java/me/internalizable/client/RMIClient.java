package me.internalizable.client;

import me.internalizable.persistence.Country;
import me.internalizable.services.country.CountryService;
import me.internalizable.services.country.requests.CountryCreationRequest;
import me.internalizable.services.country.response.DestinationResponse;
import me.internalizable.services.qr.QRService;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Scanner;

public class RMIClient {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);

            CountryService countryService = (CountryService) registry.lookup("CountryService");
            QRService qrService = (QRService) registry.lookup("QRService");

            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("Options:");
                System.out.println("1. Search for destinations");
                System.out.println("2. Register a QR code");
                System.out.println("3. Populate countries");
                System.out.println("4. Exit");
                System.out.print("Enter the number of the desired action: ");

                String input = scanner.nextLine();

                if (input.equals("1")) {
                    // Search for destinations
                    System.out.print("Enter a search term: ");
                    String search = scanner.nextLine();

                    // Call the remote method to get destinations
                    DestinationResponse response = countryService.getDestinations(search);

                    // Display non-regional destinations
                    System.out.println("Non-Regional Destinations:");
                    List<Country> nonRegionalDestinations = response.local();
                    for (int i = 0; i < nonRegionalDestinations.size(); i++) {
                        Country country = nonRegionalDestinations.get(i);
                        System.out.println((i + 1) + ". ISO: " + country.getIso() + ", Name: " + country.getName());
                    }

                    // Display regional destinations
                    System.out.println("\nRegional Destinations:");
                    List<Country> regionalDestinations = response.regional();
                    for (int i = 0; i < regionalDestinations.size(); i++) {
                        Country country = regionalDestinations.get(i);
                        System.out.println((i + 1) + ". ISO: " + country.getIso() + ", Name: " + country.getName());
                    }
                } else if (input.equals("2")) {
                    String qrCode = qrService.createQRCode();
                    System.out.println("Your generated QRCode is " + qrCode);

                    System.out.print("Enter an ISO code for registration: ");
                    String iso = scanner.nextLine();

                    System.out.print("Enter a QR code: ");
                    String code = scanner.nextLine();

                    System.out.print("Enter an email: ");
                    String email = scanner.nextLine();

                    boolean success = qrService.registerQRCode(code, iso, email);

                    if (success) {
                        System.out.println("QR code registered successfully!");
                    } else {
                        System.out.println("Failed to register QR code. Check ISO code and email.");
                    }
                } else if (input.equals("3")) {
                    countryService.createCountry(new CountryCreationRequest("FRA", "France", false));
                    countryService.createCountry(new CountryCreationRequest("GUI", "French Guinea", false));
                    countryService.createCountry(new CountryCreationRequest("TUR", "Turkey", false));
                    countryService.createCountry(new CountryCreationRequest("USA", "United States of America", false));
                    countryService.createCountry(new CountryCreationRequest("EU", "Europe", true));

                    System.out.println("Created countries!");
                } else if(input.equals("4")) {
                    break;
                } else {
                    System.out.println("Invalid option. Please enter a valid number.");
                }
            }

            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
