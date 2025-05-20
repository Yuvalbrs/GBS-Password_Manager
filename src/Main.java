
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

import PasswordManager.PasswordEntry;
import PasswordManager.PasswordManager;

public class Main {
    private static final int maxTries = 3;
    public static void main(String[] args) {
        PasswordManager passManager = new PasswordManager(StorageManager.loadIntoList());
        Scanner scanner = new Scanner(System.in);
        String service, userName, passWord;
        if (MasterPasswordManager.isFirstTime()) {
            String mp, sq, an;
            int c;
            while (MasterPasswordManager.isFirstTime()) {
                System.out.println("Enter your master password");
                mp = scanner.nextLine();
                System.out.println("Choose a safe question from the following list by number :");
                System.out.println("1 : What is your mother's mother's name?");
                System.out.println("2 : What was the name of your first pet?");
                System.out.println("3 : In what city were you born?");
                c = scanner.nextInt();
                scanner.nextLine();
                switch(c) {
                    case 1:
                        sq = "What is your mother's mother's name?";
                        break;
                    case 2:
                        sq = "What was the name of your first pet?";
                        break;
                    case 3:
                        sq = "In what city were you born?";
                        break;   
                    default :
                        System.out.println("invalid case");
                        continue;
                }
                System.out.println("and what is the answer?");
                an = scanner.nextLine();

                MasterPasswordManager.createMasterPassword(mp, sq, an);
                break;
            }
            
        } else {
            String pass;
            boolean bool = true;
            for (int i = 0; i < maxTries; i++) {
                System.out.println("Enter the password ?");
                pass = scanner.nextLine();
                if (MasterPasswordManager.checkPassword(pass)) {
                    System.out.println("welcome ");
                    bool = false;
                    break;
                } else {
                    System.out.println("wrong, try again");
                }
            }
            if (bool) {
                try {
                    Path path = Paths.get("masterPassword.hash");
                    List<String> lines = Files.readAllLines(path);
                    System.out.println(lines.get(1).trim());
                    String answer, newpass;
                    answer = scanner.nextLine();
                    System.out.println("enter your new password");
                    newpass = scanner.nextLine();
                    if (MasterPasswordManager.recoverPassword(answer, newpass)) {
                        System.out.println("updated");
                    } else {
                        System.out.println("wrong answer");
                    }
                } catch (Exception e) {
                    System.err.println();
                }
            }
        }
        
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
