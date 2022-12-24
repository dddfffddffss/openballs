package bin;

import java.util.*;

public class object
{
	double x,y,vx=0,vy=0;
	double m=1;
	int ballsize=10;
	double mag;

	public object(double x,double y,double vx,double vy,double m,int ballsize){
		this.x = x;
		this.y = y;
		this.vx = vx;
		this.vy = vy;
		this.m=m;
		this.ballsize = ballsize;
	}

	public double Lx(){
		return x;
	}

	public double Ly(){
		return y;
	}

	public double Vx(){
		return vx;
	}

	public double Vy(){
		return vy;
	}

	public double M(){
		return m;
	}

	public int S(){
		return ballsize;
	}

	public void setmag(double mag){
		this.mag = mag;
	}

	public void nextL(double ax,double ay){
		vx+=ax/m;
		vy+=ay/m;
		x+=vx;
		y+=vy;

		if(x<0 && vx<0){
			vx=-vx;
		}else if(x>1875*mag && vx>0){
			vx=-vx;
		}

		if(y<0 && vy<0){
			vy=-(vy);
		}else if(y>950*mag && vy>0){
			vy=-(vy);
		}
	}
}