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
    private String[] restaurantList;
    private String menuList;

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
                    String profileString = (String) args[0];
                    byte[] byteArray = Base64.decode(profileString, Base64.NO_WRAP | Base64.URL_SAFE);
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
                    /*.on("message", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    final JSONObject obj = (JSONObject) args[0];
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                tvMessageFrom.setText(String.format("Message from %s:", obj.getString("id")));
                                tvMessage.setText(obj.getString("msg"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }).on("messageFail", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvMessageFrom.setText("No such user");
                            tvMessage.setText("");
                        }
                    });
                }
            });*/
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

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        profile.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        String imageDataString = Base64.encodeToString(byteArray, Base64.NO_WRAP | Base64.URL_SAFE);

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

    public void sendDutch(String nameTo, String price) {
        Log.d(DEBUG_TAG, "Sending dutch request");
        SharedPreferences pref = parent.getApplicationContext().getSharedPreferences(FBConfig.SHARED_PREF, 0);
        String nameFrom = pref.getString("username", null);
        socket.emit("DutchRequest", nameFrom, nameTo, price);
    }

    public void sendDutchDismiss(String nameTo) {
        Log.d(DEBUG_TAG, "Sending dutch dismiss request");
        SharedPreferences pref = parent.getApplicationContext().getSharedPreferences(FBConfig.SHARED_PREF, 0);
        String nameFrom = pref.getString("username", null);
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
