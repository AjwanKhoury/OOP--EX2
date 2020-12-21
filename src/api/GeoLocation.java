package api;

import gameClient.util.Point3D;

public class GeoLocation implements geo_location {

	Point3D myPoint;

	/**
	 * main constructor
	 */
	public GeoLocation() {
		this.myPoint = new Point3D(0, 0, 0);
	}

	/**
	 * constructor that accept full location of point.
	 * @param x
	 * @param y
	 * @param z
	 */
	public GeoLocation(double x , double y , double z) {
		this.myPoint = new Point3D(x , y , z);
	}

	/**
	 *  Copy constructor
	 * @param p other Point
	 */
	public GeoLocation(Point3D p) {
		this.myPoint = new Point3D(p);
	}

	/**
	 * Copy constructor
	 * @param g other geo_location
	 */
	public GeoLocation(geo_location g) {
		this.myPoint = new Point3D(g.x() , g.y() , g.z());
	}

	/**
	 * constructor to location by getting the location in string
	 * @param loc string of location
	 */
	public GeoLocation(String loc) {
		this.myPoint = new Point3D(loc);
	}

	@Override
	public double x() {
		return this.myPoint.x();
	}

	@Override
	public double y() {
		return this.myPoint.y();
	}

	@Override
	public double z() {
		return this.myPoint.z();
	}

	@Override
	public double distance(geo_location g) {
		return this.myPoint.distance(g);
	}

	@Override
	public String toString() {
		return this.x() +","+ this.y() +","+ this.z();
	}

}
