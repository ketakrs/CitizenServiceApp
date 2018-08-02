package pdbs3.csp8.cspapp;

/**
 * Created by DELL on 26-01-2018.
 */

public class UserComplints {

  private String  imageName,strDate;

  public UserComplints(){

  }
    public UserComplints(String imageName, String strDate) {
        this.imageName = imageName;
        this.strDate = strDate;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getStrDate() {
        return strDate;
    }

    public void setStrDate(String strDate) {
        this.strDate = strDate;
    }
}
