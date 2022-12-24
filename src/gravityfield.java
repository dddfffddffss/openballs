package bin;

import java.util.*;

public class gravityfield
{
	object[] object;
	double[] ax,ay,z,a,b;
	double gc=3000,mag;
	double dx,abx,aby,abvx,abvy,k,Dp;

	double temp1,temp2;

	int count;
	
	public gravityfield(object[] object,double gc,double ec,double gec){
		this.object = object;
		this.gc = gc;

		count = object.length;

		ax = new double[count];
		ay = new double[count];
		z = new double[2];
	}

	public void setmag(double mag){
		for(object o:object)o.setmag(mag);
	}

	public double Dpow(object x,object y){
		return sizepow((y.Lx()-x.Lx()),(y.Ly()-x.Ly()));
	}

	public double sizepow(double x,double y){
		return x*x+y*y;
	}

	public double[] crash(double Dp,double dx,double abx,double aby,object x,object y){
		if(Dp>dx*dx||Dp<0.01)return z;

		abvx=(y.Vx()-x.Vx());
		abvy=(y.Vy()-x.Vy());
		//if(abvx*abx+abvy*aby>0)return z;

		k = (abvx*abx+abvy*aby)/sizepow(abx,aby);
		//k=2*x.M()*y.M()/(x.M()+y.M())*(abvx*abx+abvy*aby)/sizepow(abx,aby);
		return new double[]{k*abx,k*aby};
	}

	public double[] gravity(double Dp,double dx,double abx,double aby,object x,object y){
		if(Dp<dx)return z;

		k = gc*x.M()*y.M()/Dp;
		return new double[]{abx*k,aby*k};
	}

	public void moving(){
		for(int x=0;x<count;x++){
			for(int y=x+1;y<count;y++){
				Dp = Dpow(object[x],object[y]);
				dx = (object[y].S()+ object[x].S())/2;
				abx=object[y].Lx()-object[x].Lx();
				aby=object[y].Ly()-object[x].Ly();

				a = gravity(Dp,dx,abx,aby,object[x],object[y]);
				b = crash(Dp,dx,abx,aby,object[x],object[y]);
				//b = new double[2];

				temp1 = a[0]+b[0];
				temp2 = a[1]+b[1];

				ax[x] += temp1;
				ay[x] += temp2;

				ax[y] -= temp1;
				ay[y] -= temp2;
			}
		}
		for(int x=0;x<count;x++){
			object[x].nextL(ax[x],ay[x]);
			ax[x]=0;
			ay[x]=0;
		}
	}

	public object[] state(){
		return object;
	}

	public int length(){
		return count;
	}
}