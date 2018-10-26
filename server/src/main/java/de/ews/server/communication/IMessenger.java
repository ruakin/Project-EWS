package de.ews.server.communication;

public interface IMessenger {

    public String getNextMessage();

    public void sendMessage(String message);

}
