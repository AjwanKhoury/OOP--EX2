package api;

public class EdgeData implements edge_data{
	
	int src, dest, tag;
	double weight;
	String info;
	
	/**
	 * main constructor
	 */
	public EdgeData() {
		this.src = -1;
		this.dest = -1;
		this.weight = 0;
		this.tag = 0;
		this.info = "";
	}
	
	/**
	 * constructor 
	 * @param src
	 * @param dest
	 * @param weight
	 */
	public EdgeData(int src , int dest , double weight) {
		this.src = src;
		this.dest = dest;
		this.weight = weight;
		this.tag = 0;
		this.info = "";
	}
	
	/**
	 * full constructor
	 * @param src
	 * @param dest
	 * @param weight
	 * @param tag
	 * @param info
	 */
	public EdgeData(int src , int dest , double weight , int tag , String info) {
		this.src = src;
		this.dest = dest;
		this.weight = weight;
		this.tag = tag;
		this.info = info;
	}
	
	@Override
	public int getSrc() {
		return src;
	}

	@Override
	public int getDest() {
		return dest;
	}

	@Override
	public double getWeight() {
		return this.weight;
	}

	@Override
	public String getInfo() {
		return this.info;
	}

	@Override
	public void setInfo(String s) {
		this.info = s;
	}

	@Override
	public int getTag() {
		return this.tag;
	}

	@Override
	public void setTag(int t) {
		this.tag = t;
	}

}
