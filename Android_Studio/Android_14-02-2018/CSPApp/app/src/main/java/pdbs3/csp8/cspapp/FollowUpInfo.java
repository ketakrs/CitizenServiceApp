package pdbs3.csp8.cspapp;

/**
 * Created by DELL on 28-01-2018.
 */

public class FollowUpInfo {




        public String imageURL;
        public String imageKey;




    public FollowUpInfo(){}

    public FollowUpInfo(String imageURL, String imageKey) {
        this.imageURL = imageURL;
        this.imageKey = imageKey;

    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getImageKey() {
        return imageKey;
    }

    public void setImageKey(String imageKey) {
        this.imageKey = imageKey;
    }
}
