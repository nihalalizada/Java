package project;

import com.google.maps.errors.ApiException;
import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import project.Doctor;

/**
 *
 * @author Dwipa
 */
public class Geocoding {

    public static final String API_Key = "AIzaSyA_SuYOSfVI0eEkAJOHUv0sFaKLifcM3oo";

    public static String geolocation(String Street, String Number, String PC, String City) throws IOException, ApiException, InterruptedException {

        String address = Street + "+" + Number + ",+" + PC + "+" + City;
        System.out.println(address);
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        Request request = new Request.Builder()
                .url("https://maps.googleapis.com/maps/api/geocode/json?address=" + address + "&key=" + API_Key)
                .method("GET", null)
                .build();
        Response response = client.newCall(request).execute();
        String jsonData = response.body().string();
        JSONObject json = new JSONObject(jsonData);
        JSONArray results = json.getJSONArray("results");
        JSONObject result = results.getJSONObject(0);
        JSONObject geometry = result.getJSONObject("geometry");
        JSONObject location = geometry.getJSONObject("location");
        float lat = location.getFloat("lat");
        float lng = location.getFloat("lng");
        String ret = Double.toString(lat) + "%2C" + Double.toString(lng);
        return ret;
    }

    public static Doctor[] generate_doctors_list(String LatLng, int radius, String keyword, String Insurance_Type) throws IOException {

        OkHttpClient client = new OkHttpClient().newBuilder().build();
        Request request = new Request.Builder()
                .url("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + LatLng + "&radius=" + radius + "&type=doctor" + "&keyword=" + Insurance_Type + "+" + keyword + "&key=" + API_Key)
                .method("GET", null)
                .build();
        Response response = client.newCall(request).execute();
        if (response.code() == 400){
            Doctor[] empty_list = new Doctor[1];
            empty_list[0].setName("No results");
            return empty_list;
        }
        else if (response.code() != 400){
        String jsonData = response.body().string();
        JSONObject json = new JSONObject(jsonData);
        JSONArray results = json.getJSONArray("results");
        //System.out.print(jsonData);
        Doctor[] doctors_list = new Doctor[20];
        for (int i = 0; i < results.length(); i++) {
            JSONObject result = results.getJSONObject(i);
            String place_id = result.getString("place_id");
            Request id_request = new Request.Builder()
                    .url("https://maps.googleapis.com/maps/api/place/details/json?place_id=" + place_id + "&fields=name%2Cformatted_phone_number%2Cformatted_address" + "&key=" + API_Key)
                    .method("GET", null)
                    .build();
            Response place_details = client.newCall(id_request).execute();
            String jsonDetails = place_details.body().string();
            //System.out.println(jsonDetails);
            JSONObject detailed_result = new JSONObject(jsonDetails);
            JSONObject final_result = detailed_result.getJSONObject("result");
            //String[] final_result_fieldname = JSONObject.getNames(final_result);
            //System.out.println(final_result_fieldname[2]);
            String name = final_result.getString("name");
            String address = final_result.getString("formatted_address");
            if (final_result.isNull("formatted_phone_number")) {
                String phone_number = "Not available";
                doctors_list[i] = new Doctor(name, address, phone_number);
                System.out.println("Name: " + doctors_list[i].getName() + ", Address: " + doctors_list[i].getAddress() + ", Phone Number: " + doctors_list[i].getPhone_number());
            } else {
                String phone_number = final_result.getString("formatted_phone_number");
                doctors_list[i] = new Doctor(name, address, phone_number);
                System.out.println("Name: " + doctors_list[i].getName() + ", Address: " + doctors_list[i].getAddress() + ", Phone Number: " + doctors_list[i].getPhone_number());
            }
        }
        return doctors_list;
        }
        return null;
    }

    public static String[] generate_name_list(Doctor[] doctors_list) {
        int counter = array_elements(doctors_list);
        String[] name_list = new String[counter];
        int i = 0;
        while (i != 20 && doctors_list[i] != null) {
            name_list[i] = doctors_list[i].getName();
            i++;
        }
        return name_list;
    }

    public static int array_elements(Doctor[] doctors_list) {
        int counter = 0;
        while (counter != 20 && doctors_list[counter] != null) {
            counter++;
        }
        return counter;
    }

    public static void main(String[] args) throws ApiException, InterruptedException, IOException {
        String LngLat = geolocation("Unter den Akazien", "2", "60596", "Frankfurt am Main");
        //System.out.print(LngLat);
        Doctor[] list = generate_doctors_list(LngLat, 10000, "nichts", "Public");
        String[] name_list = generate_name_list(list);
        for (int i = 0; i < name_list.length; i++) {
            System.out.println(name_list[i]);
        }
    }

}
