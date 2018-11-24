import java.io.*;
import javax.swing.*;
import java.util.*;
import java.awt.*;

public class CellularAutomata extends JPanel{
	public static void main (String args[]){
		JFrame j = new JFrame();  
        MyPanel m = new MyPanel();
        j.setSize(m.getSize());
        j.add(m); 

        j.setVisible(true);
        
        j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        j.getContentPane().setFocusable(true);
	}
}
class MyPanel extends JPanel {

	private final int width;
    private final int height;
    private boolean[][] gridspace;

    private int levels;
    private int rule;
    private ArrayList<Boolean> seq = new ArrayList<Boolean>();

	MyPanel(){
        height=800;
        width=height*2;
        // levels must be <dimensions
        levels=800;


        //Max: 255 Min: 0
        rule=167;
        //Good rule numbers: 110, 



        String bform = Integer.toString(rule,2);
        for (int k=0; k<bform.length(); k++){
        	if (bform.charAt(k)=='0')
        		seq.add(false);
        	if (bform.charAt(k)=='1')
        		seq.add(true);
        }
		while (seq.size()<8){
			seq.add(0, false);
		}

        setSize(width,height);
        setVisible(true);
	}

	public void paintComponent(Graphics g){
		fillgridspace();
		g.setColor(Color.BLACK);
		for (int k=0; k<levels; k++){
			for (int b=0; b<levels*2; b++){
				if (gridspace[k][b])
					g.fillRect(b*(width/2/levels),k*(height/levels),width/2/levels,height/levels);
			}
		}
	}

	public void fillgridspace(){
		

		gridspace = new boolean[levels][levels*2];
		gridspace[0][width/2-1]=true;
		for (int k=1; k<levels; k++){
			for (int b=1; b<levels*2-1; b++){
				gridspace[k][b]=conditional(gridspace[k-1][b-1],gridspace[k-1][b],gridspace[k-1][b+1]);
			}
		}
	}

	public boolean conditional(boolean l, boolean m, boolean r){
		if (l&&m&&r)
			return seq.get(0);
			//111
		if (l&&m&&!r)
			return seq.get(1);
			//110
		if (l&&!m&&r)
			return seq.get(2);
			//101
		if (l&&!m&&!r)
			return seq.get(3);
			//100
		if (!l&&m&&r)
			return seq.get(4);
			//011
		if (!l&&m&&!r)
			return seq.get(5);
			//010
		if (!l&&!m&&r)
			return seq.get(6);
			//001
		if (!l&&!r&&!m)
			return seq.get(7);
			//000
		return false;
	}
}