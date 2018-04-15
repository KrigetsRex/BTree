//Luke Grice
//CS321
//HashObject.java
//Contains the properties and methods related to an object which will be placed
//into a hash table

public class HashObject<T>{
   //constants
   public static final byte NULL = 0;
   public static final byte USED = 1;
   public static final byte DELD = 2;
   
   //Private Variables
   private T Obj;
   private int freqCount;
   private byte state;
   
   //constructor
   public HashObject(T o){
	   this.Obj = o;
	   this.freqCount = 1;
	   this.state = USED;
   }
   
   //Public Methods
   
   /*
    * checks if object's key is same as this one's
	* @param o: the object to be compared
	* @return an int representing the relation of this key to the other object for
	* comparison's key
	*/
   @Override
   @SuppressWarnings("unchecked")
   public boolean equals(Object o){
      return this.Obj.equals(((HashObject)o).getKey());
   }
   
   /*
    * returns a string showing this obj.toString, key, and freqCount
	* @return a string
	*/
   @Override
   public String toString(){
		return (this.Obj.toString() + " " + this.freqCount);
   }
   
   /*
    * returns the key value
	* @return key
	*/
	public T getKey(){
		return this.Obj;
	}
	
	/*
    * returns the state of the object
	* @return state
	*/
	public byte getState(){
		return this.state;
	}
	
	/*
    * sets the state of the object
	* @param state: the state to be set if valid
	*/
	public void setState(byte state){
		if (state >= NULL | state <= DELD){
			this.state = state;
		}
	}
	
	/*
	 * increments the freqCount - called when a duplicate is found instead of
	 * inserting a duplicate element
	 */
	public void countPlusOne(){
		this.freqCount++;
	}
}