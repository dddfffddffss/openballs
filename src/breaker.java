package bin;

import java.io.*;

public class breaker extends Thread{
	boolean b=false;
	public void run(){
		for(;;){
			try{
				InputStreamReader re = new InputStreamReader(System.in); //Ű�����Է� ��Ʈ�� ����
				int c = re.read(); //Ű����κ��� ���� �ϳ� �б�. ���� ���� ���� C�� ����
				if(c==109){
					System.out.println("�����ϴ� ��...");
					b=true;
					break;
				}	
			} catch(Exception e){
				e.printStackTrace();
			}
		}
	}

	public boolean isend(){
		return b;
	}
}