package src;

import java.io.*;

public class breaker extends Thread{
	boolean b=false;
	public void run(){
		for(;;){
			try{
				InputStreamReader re = new InputStreamReader(System.in); //키보드입력 스트림 생성
				int c = re.read(); //키보드로부터 문자 하나 읽기. 읽은 문자 값은 C에 저장
				if(c==109){
					System.out.println("종료하는 중...");
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