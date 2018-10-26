package de.ews.server.communication;

public interface IStatement {

    public void perform() throws NullPointerException;

    public void asynchronousPerform() throws NullPointerException;

    public boolean hasAnswer();

}
