package pdbs3.csp8.cspapp;

/**
 * Created by DELL on 08-02-2018.
 */

public class Users {

    private String Uname;
    private String UmailId;
    private String Uphoto;
    public Users(){}

    public String getUname() {
        return Uname;
    }

    public void setUname(String uname) {
        Uname = uname;
    }

    public String getUmailId() {
        return UmailId;
    }

    public void setUmailId(String umailId) {
        UmailId = umailId;
    }

    public String getUphoto() {
        return Uphoto;
    }

    public void setUphoto(String uphoto) {
        Uphoto = uphoto;
    }

    public Users(String uname, String umailId, String uphoto) {

        Uname = uname;
        UmailId = umailId;
        Uphoto = uphoto;
    }
}
