package day1;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Assam {
	private int x,y;
	private int direction;
	
	private Image assamImage;
	
	public Image getAssamImage() {
		return assamImage;
	}
	
	public Assam() {
		this.x=3;
		this.y=3;
		setDirection(1);
	}
	
	//	아쌈의 위치 X
	public int getX()	{
		return x;
	}
	
	//	아쌈의 위치 Y
	public int getY()	{
		return y;
	}
	
	//	아쌈의 방향 반환
	public int getDirection() {
		return direction;
	}
	
	//	아쌈의 방향 설정
	public void setDirection(int n) {
		this.direction = n;
		if(n == 0)
			assamImage = new ImageIcon("images/assamImage0.png").getImage();
		else if(n==1)
			assamImage = new ImageIcon("images/assamImage1.png").getImage();
		else if(n==2)
			assamImage = new ImageIcon("images/assamImage2.png").getImage();
		else if(n==3)
			assamImage = new ImageIcon("images/assamImage3.png").getImage();
	}
	
	//	아쌈의 위치 설정
	public void setLocation(int x,int y) {
		this.x = x;
		this.y = y;
	}
	
	//	아쌈의 이동
	public void moveAssam()	{
		int X = this.x;
		int Y = this.y;
		if(direction == 0) {	// 0: up 1:down 2:left 3:right
			if(Y==0) {
				switch(X) {
				case 0:  setDirection(3); break;
				case 1:
					setDirection(1);
					setLocation(X + 1, Y);
					 break;
				case 2:  setDirection(1); setLocation(X-1,Y); break;
				case 3:  setDirection(1); setLocation(X+1,Y); break;
				case 4:  setDirection(1); setLocation(X-1,Y); break;
				case 5:  setDirection(1); setLocation(X+1,Y); break;
				case 6:  setDirection(1); setLocation(X-1,Y); break;
				}
			}
			else {
				setLocation(X,Y-1);
			}
		}
		else if(direction == 1) {	// 0: up 1:down 2:left 3:right	
				if(Y==6) {
					switch(X) {
					case 0: setDirection(0); setLocation(X+1,Y); break;
					case 1: setDirection(0); setLocation(X-1,Y); break;
					case 2: setDirection(0); setLocation(X+1,Y); break;
					case 3: setDirection(0); setLocation(X-1,Y); break;
					case 4: setDirection(0); setLocation(X+1,Y); break;
					case 5: setDirection(0); setLocation(X-1,Y); break;
					case 6: setDirection(2);  break;
					}
				}
				else {
					setLocation(X,Y+1);
				}
		}
		else if(direction == 2) {	// 0: up 1:down 2:left 3:right
				if(X==0) {
					switch(Y) {
					case 0:  setDirection(1); break;
					case 1:  setDirection(3); setLocation(X,Y+1); break;
					case 2:  setDirection(3); setLocation(X,Y-1);break;
					case 3:  setDirection(3); setLocation(X,Y+1);break;
					case 4:  setDirection(3); setLocation(X,Y-1);break;
					case 5:  setDirection(3); setLocation(X,Y+1);break;
					case 6:  setDirection(3); setLocation(X,Y-1);break;
					}
				}
				else {
					setLocation(X-1, Y);
				}
		}
		else if(direction == 3) {	// 0: up 1:down 2:left 3:right
				
				if(X==6) {
					switch(Y) {
					case 0: setDirection(2); setLocation(X,Y+1);break;
					case 1: setDirection(2); setLocation(X,Y-1);break;
					case 2: setDirection(2); setLocation(X,Y+1);break;
					case 3: setDirection(2); setLocation(X,Y-1);break;
					case 4: setDirection(2); setLocation(X,Y+1);break;
					case 5: setDirection(2); setLocation(X,Y-1);break;
					case 6: setDirection(0);  break;
					}
				}
				else {
					setLocation(X+1, Y);
				}
		}
	}
	
}
