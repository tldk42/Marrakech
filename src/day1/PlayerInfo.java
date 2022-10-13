package day1;

public class PlayerInfo {
	private int number; // 플레이어 순서
	private int color; // 플레이어 카펫 색상
	private int dirham1;
	private int dirham5;
	private int carpet;
	private int totalDirham;
	private boolean isUser;
	private boolean isOver;

	public PlayerInfo(int number, int color, boolean isUser) {
		this.number = number;
		this.color = color;
		this.dirham1 = 5;
		this.dirham5 = 5;
		this.totalDirham = 30;
		this.carpet = 12;
		this.isUser = isUser;
	}

	// Getter Setter

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public int getDirham1() {

		return dirham1;
	}

	public void setDirham1() {
		if (this.dirham1 != 0)
			this.dirham1--;
	}

	public void addDirham1() {
		this.dirham1++;
	}

	public int getDirham5() {
		return dirham5;
	}

	public void setDirham5() {
		if (this.dirham5 != 0)
			this.dirham5--;
	}

	public void addDirham5() {
		this.dirham5++;
	}

	public int getCarpet() {
		return carpet;
	}

	public void setCarpet() {
		this.carpet--;
	}

	public void reset() {
		this.dirham1 = 0;
		this.dirham5 = 0;
	}


	public int getTotalDirham() {
		this.totalDirham = getDirham1() + getDirham5() * 5;
		return totalDirham;
	}

	public int getTotalScore() {
		return getTotalDirham() + getCarpet();
	}

	public void over() {
		this.isOver = true;
	}

	public boolean isOver() {
		if (getTotalDirham() <= 0)
			isOver = true;
		return isOver;
	}
	public void setOver(boolean t) {
		isOver = t;
	}

	public boolean isUser() {
		return isUser;
	}

}
