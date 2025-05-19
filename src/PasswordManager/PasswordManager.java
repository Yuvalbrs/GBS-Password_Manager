package PasswordManager;

import java.util.ArrayList;
import java.util.List;

public class PasswordManager {
    private List<PasswordEntry> entries;

    public PasswordManager(List<PasswordEntry> initialEntries) {
        this.entries = new ArrayList<>(initialEntries);
    }

    public void addPassword(PasswordEntry pe) {
        if (find(pe.getService(), pe.getUsr()) != null) {
            System.out.println("already exist");
            return;
        }
        
        entries.add(pe);
        System.out.println("added :)");
    }
    public void deletePassword(String service, String username) {
        if (entries.isEmpty()) {
            System.out.println("List is empty");
            return;
        }
        PasswordEntry pe = find(service, username);
        if (pe != null) {
            entries.remove(pe);
            System.out.println("removed");
            return;
        }

        System.out.println("service/username dont exist");

    }
    public List<PasswordEntry> getAllEntries() {
        return new ArrayList<>(entries);
    }
    public String getPassword(String service, String username) {
        if (entries.isEmpty()) {
            System.out.println("List is empty");
            return null;
        }
        PasswordEntry pe = find(service, username);
        if (pe != null) {
            return pe.getPass();
        }

        System.out.println("service/username dont exist");
        return null;
    }
    private PasswordEntry find(String service, String username) {
        for (PasswordEntry entry : entries) {
            if ((entry.getService().equalsIgnoreCase(service)) &&
             (entry.getUsr().equalsIgnoreCase(username))) {
                return entry;
                
            }
        }
        return null;
    }
    public void updatePass(String service, String username, String newP) {
        if (entries.isEmpty()) {
            System.out.println("List is empty");
            return;
        }
        PasswordEntry pe = find(service, username);
        if (pe != null) {
            pe.update(newP);
            return;
        }
        System.out.println("service/username dont exist");
        return;
    }
}
