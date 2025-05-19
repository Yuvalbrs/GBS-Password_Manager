
import java.util.Scanner;

import PasswordManager.PasswordEntry;
import PasswordManager.PasswordManager;

public class Main {
    public static void main(String[] args) {
        PasswordManager passManager = new PasswordManager(StorageManager.loadIntoList());
        Scanner scanner = new Scanner(System.in);
        String service, userName, passWord;
        while (true) {
            System.out.println("\nadd/delete/get/update/exit");
            switch (scanner.nextLine()) {
                case "add":
                    System.out.println("Enter Service: ");
                    service = scanner.nextLine();
                    System.out.println("Enter UserName: ");
                    userName = scanner.nextLine();
                    System.out.println("Recomended Password: " + PasswordGenerator.generatePassword());
                    System.out.println("Enter Password: ");
                    passWord = scanner.nextLine();
                    passManager.addPassword(new PasswordEntry(service, userName, passWord));
                    StorageManager.savePasswordList(passManager.getAllEntries());
                    break;
                case "delete":
                    System.out.println("Enter Service: ");
                    service = scanner.nextLine();
                    System.out.println("Enter UserName: ");
                    userName = scanner.nextLine();
                    passManager.deletePassword(service, userName);
                    StorageManager.savePasswordList(passManager.getAllEntries());
                    break;
                case "get":
                    for (String s : passManager.getAllServices()){
                        System.out.println(s);
                    }
                    System.out.println("Enter Service: ");
                    service = scanner.nextLine();
                    for (String s : passManager.getAllUsrsForService(service)){
                        System.out.println(s);
                    }
                    System.out.println("Enter UserName: ");
                    userName = scanner.nextLine();
                    System.out.println(passManager.getPassword(service, userName));
                    break;
                case "update":
                    System.out.println("Enter Service: ");
                    service = scanner.nextLine();
                    System.out.println("Enter UserName: ");
                    userName = scanner.nextLine();
                    System.out.println("Enter Updated Password: ");
                    passWord = scanner.nextLine();
                    passManager.updatePass(service, userName, passWord);
                    StorageManager.savePasswordList(passManager.getAllEntries());
                    break;
                case "exit":
                    scanner.close();
                    return;
                default:
                    System.out.println("Dont play with me bitch");
                    break;
            }
        }
    }
}
