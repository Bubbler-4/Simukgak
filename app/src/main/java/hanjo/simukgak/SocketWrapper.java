package hanjo.simukgak;


import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

    private String[] restaurantList;

    public static SocketWrapper object() {
        return thisObj;
    }

    public void initSocket() {
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

    public void requestRestaurantList(String category) {
        socket.emit("restaurantList", category);
    }

    public String[] getRestaurantList() {
        return restaurantList;
    }
}
