package pdbs3.csp8.cspapp;


import android.location.Location;

import java.util.HashMap;





public class UploadFormInfo {

    public String imageName;

    public String imageURL;

    public String imageDesc;

//    public String imageState;
//
    public String imageCity;

    public String imsgeArea;

    public String uname;



    public  String uemail;
    public  String uphotoUrl;
    public  String uid;
    public String strDate;
    public Location location;
    public String latitude;
    public String longitude;



    public UploadFormInfo(String imageName, String imageDesc,  String imageURL, String imsgeArea, String uname, String uemail, String uphotoUrl,String uid,String strDate,Location location,String latitude,String longitude,String imageCity) {
        this.imageName = imageName;
        this.imageURL = imageURL;
        this.imageDesc = imageDesc;
//        this.imageState = imageState;
        this.imageCity = imageCity;
        this.imsgeArea = imsgeArea;
        this.uname = uname;
        this.uemail = uemail;
        this.uphotoUrl = uphotoUrl;
        this.uid=uid;
        this.strDate=strDate;
        this.location=location;
        this.latitude=latitude;
        this.longitude=longitude;


    }

    public String getImageCity() {
        return imageCity;
    }

    public void setImageCity(String imageCity) {
        this.imageCity = imageCity;
    }

    public String getImageName() {
        return imageName;
    }
    public String getImageDesc() {
        return imageDesc;
    }
    public String getImsgeArea() {
        return imsgeArea;
    }
//    public String getImageState() {
//        return imageState;
////    }
//    public String getImageCity() {
//        return imageCity;
//    }

    public String getImageURL() {
        return imageURL;
    }

    public String getUname() {
        return uname;
    }

    public String getUemail() {
        return uemail;
    }

    public String getUphotoUrl() {
        return uphotoUrl;
    }
    public String getUid(){return uid;}

    public String getStrDate() {
        return strDate;
    }

    public void setStrDate(String strDate) {
        this.strDate = strDate;



    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public UploadFormInfo() {

    }
}
