package thoughtworks;

import java.io.*;

/**
 * Created by IntelliJ IDEA.
 * User: david
 * Date: 1/4/12
 * Time: 9:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class Grid extends Direction {

	/** The width in grid cells of our grid */
	private static final int WIDTH = 1;
	/** The height in grid cells of our grid */
	private static final int HEIGHT = 1;

	/** The rendered size of the tile (in pixels) */
	public static final int TILE_SIZE = 1;

	/** The actual data for our grid */
	private int[][] data = new int[WIDTH][HEIGHT];
    private int[][] temp = new int[WIDTH][HEIGHT];
    Point upperRight = new Point();
    Point currentLoc = new Point();
    Direction direction = new Direction();

	/**
	 * Create a new map with some default contents
	 */
	public Grid() {
        // uncomment the dataFolder definition below if '.' does not work.
//        private String dataFolder = System.getProperty("user.dir") + System.getProperty("file.separator") + "input.txt";
        String dataFolder = "./input.txt";
        ReadWriteTextFile rwt = new ReadWriteTextFile();
        File inputFile = new File(dataFolder);
        String inputText = rwt.getContents(inputFile);
        int lineCount = rwt.getLineCount(inputFile);
        if(lineCount < 3)
            throw new Error("Insufficient line data");

        String[] lines = inputText.split(DELIMS_NL);
        String[] parms;
        String[] args;

        boolean done     = false;
        boolean finished = false;

        for(int i=0; i<lineCount; i++) {
            parms = lines[i].split(DELIMS_WS);
            if (i==0 && parms.length != 2)
                throw new Error("Wrong parm count");
            else if (i%2==1 && parms.length != 3)
                throw new Error("Wrong parm count");

            if (i==0 && !done) {
                int coordX = Integer.parseInt(parms[0]);
                int coordY = Integer.parseInt(parms[1]);
                data = java.util.Arrays.copyOf(data, coordX);
                for(int p=0; p<data.length; p++) { // resize the array
                    if (data[p]==null)
                        data[p] = new int[coordY];
                    else data[p] = java.util.Arrays.copyOf(data[p], coordY);
                }

                upperRight.setX(coordX);
                upperRight.setY(coordY);
                if (upperRight.getX()==0 && upperRight.getY()==0)
                    throw new Error("Grid < 2 points");
                // commented out code designed to display ASCII character grid path.
                /*for (int x = 0; x < upperRight.getX()-1; x++) {
                    data[x][0] =;
                    data[x][(int) upperRight.getY()-1] = ;
                }
                for (int y = 0; y < upperRight.getY()-1; y++) {
                    data[0][y] = ;
                    data[(int) upperRight.getX()-1][y] = ;
                }
                data[(int) upperRight.getX()-1][(int) upperRight.getY()-1] = ;*/
                done = true;
            }
            else if (i%2==1) {
                args = lines[i].split(DELIMS_WS);
                if (args.length != 3)
                    throw new Error("Wrong arg count");

                /*currentLoc.setX(Integer.parseInt(args[0]));
                currentLoc.setY(Integer.parseInt(args[1]));
                direction.setDirection(args[2]);*/
                direction.locationsList.add(lines[i]);
            } else if (i%2==0) {
                direction.movementsList.add(lines[i]);
            }
        }
	}

	/**
	 * Check if a particular location on the map is blocked. Note
	 * that the x and y parameters are floating point numbers meaning
	 * that we can be checking partially across a grid cell.
	 *
	 * @param x The x position to check for blocking
	 * @param y The y position to check for blocking
	 * @return True if the location is blocked
	 */
	public boolean blocked(float x, float y) {
		// look up the right cell (based on simply rounding the floating
		// values) and check the value
        boolean valid = true;
        if(x<0.0 || y <0.0) return false;
        Integer index = (Integer) direction.dirMap.get(direction.getDirection());
        switch (index.intValue()) {
            case N : if(currentLoc.getY()>upperRight.getY()) valid = false;
                break;
            case E : if(currentLoc.getX()>upperRight.getX()) valid = false;
                break;
            case W : if(currentLoc.getX()<0.0) valid = false;
                break;
            case S : if(currentLoc.getY()<0.0) valid = false;
                break;
        }
		return valid;
	}

    private class ReadWriteTextFile {

        /**
         * Fetch the entire contents of a text file, and return it in a String.
         * This style of implementation does not throw Exceptions to the caller.
         *
         * @param aFile is a file which already exists and can be read.
         */
        public String getContents(File aFile) {
            //...checks on aFile are elided
            StringBuilder contents = new StringBuilder();

            try {
                //use buffering, reading one line at a time
                //FileReader always assumes default encoding is OK!
                BufferedReader input = new BufferedReader(new FileReader(aFile));
                try {
                    String line = null; //not declared within while loop
                    /*
                    * readLine is a bit quirky :
                    * it returns the content of a line MINUS the newline.
                    * it returns null only for the END of the stream.
                    * it returns an empty String if two newlines appear in a row.
                    */
                    while ((line = input.readLine()) != null) {
                        contents.append(line);
                        contents.append(System.getProperty("line.separator"));
                    }
                } finally {
                    input.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            return contents.toString();
        }

        /**
         * Change the contents of text file in its entirety, overwriting any
         * existing text.
         * <p/>
         * This style of implementation throws all exceptions to the caller.
         *
         * @param aFile is an existing file which can be written to.
         * @throws IllegalArgumentException if param does not comply.
         * @throws FileNotFoundException    if the file does not exist.
         * @throws IOException              if problem encountered during write.
         */
        public void setContents(File aFile, String aContents)
                throws FileNotFoundException, IOException {
            if (aFile == null) {
                throw new IllegalArgumentException("File should not be null.");
            }
            if (!aFile.exists()) {
//              throw new FileNotFoundException ("File does not exist: " + aFile);
                aFile.createNewFile();
            }
            if (!aFile.isFile()) {
                throw new IllegalArgumentException("Should not be a directory: " + aFile);
            }
            if (!aFile.canWrite()) {
                throw new IllegalArgumentException("File cannot be written: " + aFile);
            }

            //use buffering
            Writer output = new BufferedWriter(new FileWriter(aFile));
            try {
                //FileWriter always assumes default encoding is OK!
                output.write(aContents);
            } finally {
                output.close();
            }
        }

        public int getLineCount(File aFile) {
//            StringBuilder contents = new StringBuilder();
            int count = 0;

            try {
                //use buffering, reading one line at a time
                //FileReader always assumes default encoding is OK!
                BufferedReader input = new BufferedReader(new FileReader(aFile));
                try {
                    String line = null; //not declared within while loop
                    /*
                    * readLine is a bit quirky :
                    * it returns the content of a line MINUS the newline.
                    * it returns null only for the END of the stream.
                    * it returns an empty String if two newlines appear in a row.
                    */
                    while ((line = input.readLine()) != null) {
                        count++;
                    }
                } finally {
                    input.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            return count;
        }

        /** Simple test harness.   */
        /*public static void main (String... aArguments) throws IOException {
          String dataFolder = System.getProperty("user.dir") +
                      System.getProperty("file.separator") + "test.txt";
          File testFile = new File(dataFolder);
          System.out.println("Original file contents: " + getContents(testFile));
          setContents(testFile, "The content of this file has been overwritten...");
          System.out.println("New file contents: " + getContents(testFile));
        }*/
    }

}
