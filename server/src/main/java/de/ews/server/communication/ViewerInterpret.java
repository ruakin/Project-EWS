package de.ews.server.communication;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ViewerInterpret implements IInterpret {

    private ViewerMessenger     vm;
    private static final Logger LOGGER = LogManager.getLogger(ViewerInterpret.class);

    public ViewerInterpret(ViewerMessenger vm) {
        this.vm = vm;
    }

    public IStatement getStatement() throws NullPointerException {
        String data[] = vm.getNextMessage().split(",");
        IStatement sret = null;

        switch (data[0]) {
        case "INSERT":
            for (int i = 5; i < 11; i++) {
                if (data[i].toLowerCase().equals("null")) {
                    data[i] = "0";
                }
            }
            sret = new InsertViewerStatement(data[1], data[2], data[3], data[4], Float.parseFloat(data[5]),
                    Float.parseFloat(data[6]), Integer.parseInt(data[7]), Integer.parseInt(data[8]),
                    Integer.parseInt(data[9]), Integer.parseInt(data[10]));
            break;

        case "FETCH":
            sret = new FetchViewerStatement();
            break;

        case "FETCHALL":
            sret = new FetchAllViewerStatement();
            break;

        case "DELETE":
            sret = new DeleteViewerStatement(Integer.parseInt(data[1]));
            break;

        default:
            LOGGER.error("Unknown Keyword: " + data[0]);
        }

        LOGGER.info("Created statement: " + data[0]);
        return sret;
    }

}
