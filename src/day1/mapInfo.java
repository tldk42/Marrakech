package day1;

public class mapInfo {
	private int carpet[][] = new int[7][7];		//	이 곳에는 카페트의 색을 지정한다.	0: 없음 1:p1 2:p2	 3:p3 4:p4
	private boolean preCarpet[][] = new boolean[7][7];	// 이 곳에는 첫번 째 클릭 가능한 곳을 true로 반환한다.
	private boolean preview[][] = new boolean[7][7];	// 이 곳에는 두번 째로 클릭이 가능한 곳을 true로 반환한다.

	public mapInfo() {
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 7; j++)
				carpet[i][j] = 0; // 0:비어있음 1:player1 2:player2 3:player3 4:player4
		}
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 7; j++)
				preCarpet[i][j] = false;
		}
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 7; j++)
				preview[i][j] = false;
		}
	}

	//	첫 번째에 클릭할 떄는 우선 모든 동서남북 방향을 클릭 가능하도록 한다.
	public void firstClick(int x, int y) {
		// 아쌈의 위치 (x,y)를 기준으로 상 하 좌 우을 활성화 시켜준다.
		if (y != 0) // 상
			preCarpet[x][y - 1] = true;
		if (y != 6) // 하
			preCarpet[x][y + 1] = true;
		if (x != 0) // 좌
			preCarpet[x - 1][y] = true;
		if (x != 6) // 우
			preCarpet[x + 1][y] = true;

	}

	//	조건에 따라서 보여질 수 있는 곳은 두번째에 true로 값을 변경해준다.
	public void secondView(int x, int y) {
		if (y != 6)
			if ((getCarpetColor(x, y + 1) != getCarpetColor(x, y) || getCarpetColor(x, y + 1) == 0)
					&& (y + 1 != Game.assam.getY()))
				preCarpet[x][y + 1] = true;
		if (y != 0)
			if ((getCarpetColor(x, y - 1) != getCarpetColor(x, y) || getCarpetColor(x, y - 1) == 0)
					&& (y - 1 != Game.assam.getY()))
				preCarpet[x][y - 1] = true;
		if (x != 6)
			if ((getCarpetColor(x + 1, y) != getCarpetColor(x, y) || getCarpetColor(x + 1, y) == 0)
					&& (x + 1 != Game.assam.getX()))
				preCarpet[x + 1][y] = true;
		if (x != 0)
			if ((getCarpetColor(x - 1, y) != getCarpetColor(x, y) || getCarpetColor(x - 1, y) == 0)
					&& (x - 1 != Game.assam.getX()))
				preCarpet[x - 1][y] = true;

	}

	//	클릭할 때를 기준으로 클릭이 가능하면 true 불가능하면 false를 반환하여 클릭이 불가능하게 한다.
	public boolean secondClick(int x, int y, int x2, int y2) {
		if (((x + y - x2 - y2) == 1 || (x + y - x2 - y2) == -1)
				&& ((x - x2 <= 1 && x - x2 >= -1) && (y - y2 <= 1 && y - y2 >= -1))
				&& (x != Game.assam.getX() || y != Game.assam.getY())) {
			
			if (getCarpetColor(x, y) == 0)
				return true;

			else if (getCarpetColor(x, y) != getCarpetColor(x2, y2))
				return true;

			else
				return false;

		} else
			return false;
	}

	// 설치 가능한 곳을 초기화한다.
	public void InitPreCarpet() {
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 7; j++) {
				preCarpet[i][j] = false;
			}
		}
	}
	
	// getter setter
	public int getCarpetColor(int x, int y) {
		return carpet[x][y];
	}

	public void setCarpetColor(int x, int y, int color) {
		carpet[x][y] = color;
	}

	public boolean getPreCarpet(int x, int y) {
		return preCarpet[x][y];
	}

	public void setPreCarpet(int x, int y, boolean t) {
		preCarpet[x][y] = t;
	}

	public boolean getPreview(int x, int y) {
		return preview[x][y];
	}

	public void setPreview(int x, int y, boolean t) {
		preview[x][y] = t;
	}

}
