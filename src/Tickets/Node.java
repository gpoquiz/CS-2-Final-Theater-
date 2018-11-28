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

public class Node extends Payload
{
	private Node N;
	private Node S;
	private Node E;
	private Node W;
	
	public Node()
	{
		super();
		N = E = S = W = null;
	}
	
	public Node(char type)
	{
		this();
		this.type = type;
	}
	public Node(int row, int col, char type)
	{
		super(row, col, type);
		 N = E = S = W = null;
	}
	
	public Node getN() {return N;}
	public Node getS() {return S;}
	public Node getE() {return E;}
	public Node getW() {return W;}
	
	private void setN(Node N) {this.N = N;} 
	private void setS(Node S) {this.S = S;}
	private void setE(Node E) {this.E = E;}
	private void setW(Node W) {this.W = W;} 
	
	public void linkN(Node N)
	{
		setN(N);
		N.setS(this);
	}
	
	public void linkS(Node S)
	{
		setS(S);
		S.setN(this);
	}
	public void linkE(Node E)
	{
		setE(E);
		E.setW(this);
	}
	public void linkW(Node W)
	{
		setW(W);
		W.setE(this);
	}
	public void output()
	{
		System.out.println(row + ", " + col + ": " + type);
	}
	
}
