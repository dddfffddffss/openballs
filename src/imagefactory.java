package bin;

import java.io.*;
import java.awt.*;
import javax.imageio.*;
import java.awt.image.*;

public class imagefactory
{
	int x,y;
	gravityfield gf;
	breaker b;

	public imagefactory(gravityfield gf,int x,int y){
		this.x = x;
		this.y = y;
		this.gf = gf;

		gf.setbound(x,y);

		b =  new breaker();
		b.start();
	}

	/*x	
	 - calnextpicture���� ����ϰ� makeframeimage�� ��� ����� ����Ѵ�.
	   - ��� ������ calnextpicture( gf ) ���� �̷����Ƿ�, makeframeimage( imagefactory )�� �ܼ��� �̹��� ���� �����̴�.
	*/

	private void calnextpicture(){
		gf.moving();
	}

	//���ο� �������� �̹��� ��������
	private BufferedImage makeframeimage(){
		BufferedImage bi = new BufferedImage(x, y, BufferedImage.TYPE_INT_RGB);
		Graphics g1 = bi.getGraphics();
		g1.setColor(Color.black);

		try{g1.drawImage(ImageIO.read(new File("res\\white1a1.jpg")),0,0,x,y,0,0,1,1,null);}
		catch (IOException e) {e.printStackTrace();}
		
		for(object ob:gf.state()){
			g1.fillOval((int)(ob.Lx()),(int)(ob.Ly()),ob.S(),ob.S());
		}

		return bi;
	}

	//���� ������ gf�� �̹����� ����Ѵ�.
	private void makecurrentstateoutput(String dir,String subdir,String name){
        try {ImageIO.write(makeframeimage(),"JPG", new File(dir+"\\"+subdir+"\\"+name+".jpg")); }
		catch (IOException e) {e.printStackTrace();}
	}

	/*
	 - dir�� ���丮+�̸� ������, �����ڸ� �� �ʿ� ����.
	 - subdir�� �� �������� id�� �����Ǿ� ������ϰ� ���� ���� �� �ְ� �Ѵ�.
	   - ��������� �̸��� subdir�� �̸��� id�� �����ϴ�.
	   - id�� �̸��� ó������ ������� �ð����� �Ѵ�.
	*/

	public void startfactory(String dir,int count){
		String subdir = gf.getid();
		File f  = new File(dir+"\\"+subdir);
		f.mkdirs();

		for(int i=gf.getprogress();(count<0||i<count)&&!b.isend();i++){
			System.out.println(i);
			makecurrentstateoutput(dir,subdir,i+"");
			calnextpicture();
		}
		System.out.println("�����մϴ�.");
	}
}