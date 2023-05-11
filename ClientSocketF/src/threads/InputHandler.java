package threads;

import clientEvents.ClientEventsListener;
import clientEvents.DisconnectionEvent;
import clientEvents.MessageReceivedEvent;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class InputHandler extends Thread{
    DataInputStream in;
    
    private ArrayList<ClientEventsListener> listeners;
    
    public InputHandler(InputStream inputStream){
        in = new DataInputStream(inputStream);
        listeners = new ArrayList<>();
    }
    
    @Override
    public void run() {
        boolean connected = true;
        //int count = 0;
        while (connected) { //(count < 5)
            try {
                System.out.println("Esperando mensaje...");
                String message = in.readUTF();
                System.out.println("Se ha recibido un mensaje "+message);
                triggerMessageReceivedEvent(message);
            } catch (IOException ex) {
                connected = false;
                //count++
                ex.printStackTrace();//Si el servidor cae aqui en el input ocurrira un excepcion o si llega mal? EOFException?
                triggerDisconnectedEvent(true);
            }
        }
    }
        
    public void addEventsListener(ClientEventsListener listener){
        listeners.add(listener);
    }
    
    public void removeMiEventoListener(ClientEventsListener listener) {
        listeners.remove(listener);
    }
    
    public void triggerMessageReceivedEvent(String message) {
        MessageReceivedEvent evt = new MessageReceivedEvent(this, message);
        for (ClientEventsListener listener : listeners) {
            listener.onReceivedMessage(evt);
        }
    }
    
    public void triggerDisconnectedEvent(boolean disconnect) {
        DisconnectionEvent evt = new DisconnectionEvent(this, disconnect);
        for (ClientEventsListener listener : listeners) {
            listener.onDisconnected(evt);
        }
    }
}
