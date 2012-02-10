package thoughtworks;

/**
 * Created by IntelliJ IDEA.
 * User: david
 * Date: 1/5/12
 * Time: 9:43 AM
 * To change this template use File | Settings | File Templates.
 */
public class Rover {
	/** The x position of this rover in terms of grid points */
	private float x;
	/** The y position of this rover in terms of grid points */
	private float y;
	/** The image to draw for this rover */
	private Grid grid;
	/** The angle to draw this rover at */
//	private float ang;

	/**
	 * Create a new rover
	 *
	 * @param grid The grid this entity is going to wander around
	 * @param x The initial x position of this entity in grid cells
	 * @param y The initial y position of this entity in grid cells
	 */
	public Rover (Grid grid, float x, float y) {
		this.grid = grid;
		this.x = x;
		this.y = y;
	}

	/**
	 * Move this entity a given amount. This may or may not succeed depending
	 * on collisions
	 *
	 * @param dx The amount to move on the x axis
	 * @param dy The amount to move on the y axis
	 * @return True if the move succeeded
	 */
	public boolean move(float dx, float dy) {
		// work out what the new position of this entity will be
		float nx = dx;
		float ny = dy;

		// check if the new position of the entity collides with
		// anything
		if (validLocation(nx, ny)) {
			// if it doesn't then change our position to the new position
			x = nx;
			y = ny;
            grid.currentLoc.setX(x);
            grid.currentLoc.setY(y);
			// and calculate the angle we're facing based on our last move commented out
//			ang = (float) (Math.atan2(dy, dx) - (Math.PI / 2));
			return true;
		}

		// if it wasn't a valid move don't do anything apart from
		// tell the caller
		return false;
	}

	/**
	 * Check if the rover would be at a valid location if its position
	 * was as specified
	 *
	 * @param nx The potential x position for the rover
	 * @param ny The potential y position for the rover
	 * @return True if the new position specified would be valid
	 */
	public boolean validLocation(float nx, float ny) {
        boolean valid = true;
        if(!grid.blocked(nx, ny)) valid = false;

		// if all the points checked are unblocked then we're in an ok
		// location
		return valid;
	}

}