/*
     * Grant Poquiz
     * gep160230
     * 2336.003
     * Jason Smith
     * 10/16/2018
     * This program is intended to read in a movie theater, prompt for sales, and then output
     * Updated to use Quadruply linked lists 
*/
package Tickets;

abstract class Payload
{
	protected int row;
	protected int col;
	protected char type;
	
	public Payload(){row = col = type = 0;}
	
	public int getRow() {return row;}
	public int getCol() {return col;}
	public char getType() {return type;}
	public void setType(char type) {this.type = type;}
	
	public Payload(int row, int col, char type) throws IndexOutOfBoundsException
	{
		if (row < 0 || col < 0)
			throw new IndexOutOfBoundsException();
		this.row = row;
		this.col = col;
		this.type = type;
	}
	
	@Override
	public String toString()
	{
		return "[" + row + ", " + ('@' + col) + "]";
	}
}
