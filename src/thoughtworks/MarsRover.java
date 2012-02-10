package thoughtworks;

/**
 * Created by IntelliJ IDEA.
 * User: david
 * Date: 1/4/12
 * Time: 8:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class MarsRover {

    static Grid grid = null;

    public static void runRover() {
        double x, y;
        Rover rover = null;
        String[] locdir;
        char[] moves;
        boolean done        = false;
        boolean moveDone    = false;
        int count = -1;
        int total = 0;
        int j = 0;
        int k = 0;
        int moveCount = 0;
        grid = new Grid();
        String[] locationsArray = new String[grid.direction.locationsList.size()];
        String[] movementsArray = new String[grid.direction.movementsList.size()];
        for(int i=0; i<grid.direction.locationsList.size(); i++) {
            locationsArray[i] = grid.direction.locationsList.get(i).toString();
            locdir = locationsArray[i].split(grid.direction.DELIMS_WS);
            x = new Double(locdir[0]);
            y = new Double(locdir[1]);
            grid.currentLoc.setX(x);
            grid.currentLoc.setY(y);
            grid.direction.setDirection(locdir[2]);
            rover = new Rover(grid, (float) grid.currentLoc.getX(), (float) grid.currentLoc.getY());
            while(!moveDone) {
                moveCount = grid.direction.movementsList.size();
                movementsArray[j] = grid.direction.movementsList.get(j).toString();
                moves = movementsArray[j].toCharArray();
                doMoves(rover, moves);
                if (j<moveCount) {
                    j++;
                    break;
                }
                else moveDone = true;
            }
        }
    }

    private static void doMoves(Rover rover, char[] moves) {
        for(int k=0; k<moves.length; k++) {
            switch (moves[k]) {
                case 'L' :
                    grid.direction.newDirection("L");
                    break;
                case 'R' :
                    grid.direction.newDirection("R");
                    break;
                case 'M' :
                    if (grid.direction.getDirection().equals("N") && !rover.move((float) grid.currentLoc.getX(),(float) grid.currentLoc.getY()+1))
                        System.out.println("N: bad move");
                    if (grid.direction.getDirection().equals("E") && !rover.move((float) grid.currentLoc.getX()+1,(float) grid.currentLoc.getY()))
                        System.out.println("E: bad move");
                    if (grid.direction.getDirection().equals("W") && !rover.move((float) grid.currentLoc.getX()-1,(float) grid.currentLoc.getY()))
                        System.out.println("W: bad move");
                    if (grid.direction.getDirection().equals("S") && !rover.move((float) grid.currentLoc.getX(),(float) grid.currentLoc.getY()-1))
                        System.out.println("S: bad move");
                break;
            }
        }
        display();
    }

    private static void display() {
        System.out.println((int) grid.currentLoc.getX() + " " + (int) grid.currentLoc.getY() + " " + grid.direction.getDirection());
    }

    static public void main(String[] args) {
        runRover();
    }

}
