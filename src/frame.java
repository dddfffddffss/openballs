package src;

import java.awt.*;
import javax.swing.*;

public class frame extends JFrame
{	
	double mag;
	double[] z;

	public frame(gravityfield gf,int time,double mag,int x,int y){
		setSize(x,y);
		setLocationRelativeTo(null);
		setVisible(true);
		setTitle("1234");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.mag = mag;
		gf.setmag(mag);

		panel p = new panel(gf);
		add(p,BorderLayout.CENTER);

		while(true){
			p.moving();
			p.revalidate();
			p.repaint();
			try{
				Thread.sleep(time);
			}catch(Exception e){}
		}
	}

	class panel extends JPanel
	{
		gravityfield gf;
		
		panel(gravityfield gf){
			this.gf = gf;
		}

		public void paintComponent(Graphics g1){
			super.paintComponent(g1);
			for(object ob:gf.state())g1.fillOval((int)(ob.Lx()/mag),(int)(ob.Ly()/mag),ob.S(),ob.S());
		}

		void moving(){
			gf.moving();
		}
	}
}