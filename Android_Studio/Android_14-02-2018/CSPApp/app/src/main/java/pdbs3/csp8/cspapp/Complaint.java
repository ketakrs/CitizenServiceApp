package pdbs3.csp8.cspapp;

/**
 * Created by DELL on 20-01-2018.
 */

public class Complaint {



    private String imageName, imageDesc, imageURL,uname;

    public Complaint()
    {

    }

    public Complaint(String imageName, String imageDesc, String imageUrl , String uname) {
        this.imageName = imageName;
        this.imageDesc = imageDesc;
        this.imageURL = imageUrl;
        this.uname=uname;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImageDesc() {
        return imageDesc;
    }

    public void setImageDesc(String imageDesc) {
        this.imageDesc = imageDesc;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageUrl) {
        this.imageURL = imageUrl;
    }


}
