package thoughtworks;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: david
 * Date: 1/4/12
 * Time: 3:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class Direction {
    public static final int N = 0;
    public static final int E = 1;
    public static final int W = 2;
    public static final int S = 3;

    public static final int L = 0;
    public static final int R = 1;

    public static final String[] turns = {"L","R"};

    public static final String[] directions = {"N","E","W","S"};
    public String currentDir    = "X";
    public HashMap dirMap       = new HashMap();
    public HashMap turnsMap     = new HashMap();

    public static final String   DELIMS_NL = "[\n]+";
    public static final String   DELIMS_WS = "[ ]+";

    public Direction() {
        for(int i=N; i<directions.length; i++) {
            dirMap.put(directions[i], new Integer(i));
        }
        for(int j=L; j<turns.length; j++) {
            turnsMap.put(turns[j], new Integer(j));
        }
    }

    ArrayList locationsList = new ArrayList();
    ArrayList movementsList = new ArrayList();

    public String getDirection() {
        return this.currentDir;
    }

    public void setDirection(String m) {
        this.currentDir = m;
    }

    public void newDirection(String turn) {
        Integer index = (Integer)turnsMap.get(turn);
        boolean done = false;
        switch (index.intValue()) {
            case L :
                if (getDirection().equals(directions[N]) && !done) {
                    setDirection("W");
                    done = true;
                }
                if (getDirection().equals(directions[E]) && !done) {
                    setDirection("N");
                    done = true;
                }
                if (getDirection().equals(directions[W]) && !done) {
                    setDirection("S");
                    done = true;
                }
                if (getDirection().equals(directions[S]) && !done) setDirection("E");
                break;
            case R :
                if (getDirection().equals(directions[N]) && !done) {
                    setDirection("E");
                    done = true;
                }
                if (getDirection().equals(directions[E]) && !done) {
                    setDirection("S");
                    done = true;
                }
                if (getDirection().equals(directions[W]) && !done) {
                    setDirection("N");
                    done = true;
                }
                if (getDirection().equals(directions[S]) && !done) setDirection("W");
                break;
        }
    }
}
