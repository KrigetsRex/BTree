public class Sequence {

	private long bases;
	private int freq;
	
	public Sequence(long bases){
		this.bases = bases;
			
	}
	
	public long getBases() {
		return bases;
	}
	public void setBases(long bases) {
		this.bases = bases;
	}
	
	public int getFreq(){
		return freq;
	}
	
	public void incrementFreq(){
		freq++;
	}

	
	
}
