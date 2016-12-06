package hanjo.simukgak;


import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.net.URISyntaxException;
import java.util.Observable;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class SocketWrapper extends Observable {
    private static SocketWrapper thisObj = new SocketWrapper();

    private static final String DEBUG_TAG = "SocketWrapper";
    private Socket socket;
    private boolean socketAvailable;
    private boolean socketConnected;
    private MainActivity parent;

    private String id, login;
    private Bitmap profile;
    private String imageDataString;
    private String[] restaurantList;
    private String menuList;
    private String reviewList;

    public static SocketWrapper object() {
        return thisObj;
    }

    public void initSocket(MainActivity parent) {
        this.parent = parent;
        socketConnected = false;

        try {
            socket = IO.socket("http://bubbler-test.herokuapp.com");
            socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    Log.d(DEBUG_TAG, "SocketIO connected");
                    socketConnected = true;
                }
            }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    Log.d(DEBUG_TAG, "SocketIO disconnected");
                    socketConnected = false;
                }
            }).on("login", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    login = (String) args[0];
                    if(login.equals("success")) {
                        requestProfile(id);
                    }
                    else {
                        setChanged();
                        notifyObservers();
                    }
                }
            }).on("profile", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    imageDataString = (String) args[0];
                    byte[] byteArray = Base64.decode(imageDataString, Base64.NO_WRAP | Base64.URL_SAFE);
                    profile = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                    setChanged();
                    notifyObservers();
                }
            }).on("restaurantList", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    final JSONArray obj = (JSONArray) args[0];

                    restaurantList = new String[obj.length()];
                    for(int i = 0; i < obj.length(); i += 1) {
                        restaurantList[i] = obj.optString(i);
                    }

                    setChanged();
                    notifyObservers();
                }
            }).on("menuList", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    menuList = ((JSONObject) args[0]).toString();

                    setChanged();
                    notifyObservers();
                }
            }).on("reviewList", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    Log.d(DEBUG_TAG, "Get reviews");
                    reviewList = ((JSONArray) args[0]).toString();

                    setChanged();
                    notifyObservers();
                }
            }).on("DutchDismiss", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    final JSONArray obj = (JSONArray) args[0];

                    for(int i = 0; i < obj.length(); i += 1) {
                        int messageID = obj.optInt(i);
                    }

                    setChanged();
                    notifyObservers();
                }
            });
            socketAvailable = true;

            socket.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            socketAvailable = false;
        }
    }

    public void sendFBToken(String token) {
        Log.d(DEBUG_TAG, "Sending Firebase Token");
        SharedPreferences pref = parent.getApplicationContext().getSharedPreferences(FBConfig.SHARED_PREF, 0);
        String user = pref.getString("username", null);
        socket.emit("FBToken", token, user);
    }

    public void sendLogin(String id, String password) {
        Log.d(DEBUG_TAG, "Sending ID and password");
        this.id = id;
        socket.emit("login", id, password);
    }

    public String getLogin() {
        return login;
    }

    public void sendProfile(Bitmap profile) {
        Log.d(DEBUG_TAG, "Sending profile");

        this.profile = profile;

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        profile.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        imageDataString = Base64.encodeToString(byteArray, Base64.NO_WRAP | Base64.URL_SAFE);

        SharedPreferences pref = parent.getApplicationContext().getSharedPreferences(FBConfig.SHARED_PREF, 0);
        String id = pref.getString("userId", null);

        socket.emit("registerProfile", id, imageDataString);
    }

    public void requestProfile(String id) {
        socket.emit("requestProfile", id);
    }

    public Bitmap getProfile() {
        return profile;
    }

    public void sendOrder(JSONObject order) {
        Log.d(DEBUG_TAG, "Sending order: " + order.toString());
        socket.emit("Order", order);
    }

    public void sendReview(String job, String store, String food, String date, String grade, String comment) {
        JSONObject obj = new JSONObject();
        SharedPreferences pref = parent.getApplicationContext().getSharedPreferences(FBConfig.SHARED_PREF, 0);
        String id = pref.getString("userId", null);
        try {
            obj.put("id", id);
            obj.put("profile", imageDataString);
            obj.put("job", job);
            obj.put("store", store);
            obj.put("food", food);
            obj.put("date", date);
            obj.put("grade", grade);
            obj.put("comment", comment);
            obj.put("like", 0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        socket.emit("Review", obj);
    }

    public void requestReviews() {
        Log.d(DEBUG_TAG, "Request reviews");
        socket.emit("RequestReviews");
    }

    public String getReviewList() {
        return reviewList;
    }

    public void deleteReview(int index) {
        socket.emit("DeleteReview", index);
    }

    public void sendDutch(String nameTo, String price) {
        Log.d(DEBUG_TAG, "Sending dutch request");
        SharedPreferences pref = parent.getApplicationContext().getSharedPreferences(FBConfig.SHARED_PREF, 0);
        String nameFrom = pref.getString("userId", null);
        socket.emit("DutchRequest", nameFrom, nameTo, price);
    }

    public void sendDutchDismiss(String nameTo) {
        Log.d(DEBUG_TAG, "Sending dutch dismiss request");
        SharedPreferences pref = parent.getApplicationContext().getSharedPreferences(FBConfig.SHARED_PREF, 0);
        String nameFrom = pref.getString("userId", null);
        socket.emit("DutchDismiss", nameFrom, nameTo);
    }

    public void requestRestaurantList(String category) {
        socket.emit("restaurantList", category);
    }

    public String[] getRestaurantList() {
        return restaurantList;
    }

    public void requestMenuList(String category) {
        socket.emit("menuList", category);
    }

    public String getMenuList() {
        return menuList;
    }
}
