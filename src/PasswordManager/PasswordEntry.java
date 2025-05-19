package PasswordManager;

public class PasswordEntry {
    private String service ;
    private String usr ;
    private String pass ;

    public PasswordEntry(String Service, String Usr, String Pass) {
        service = Service;
        usr = Usr;
        pass = Pass;
    }
    public String getService() {
        return service;
    }
    public String getUsr() {
        return usr;
    }
    public String getPass() {
        return pass;
    }
    public boolean equals(PasswordEntry pe) {
        if ((pe.getService() == this.service) && ((pe.getUsr() == this.usr))) {
            return true;
        }
        return false;
    }
    public void update(String Pass) {
        this.pass = Pass;
    }

}

