import java.io.*;
import java.lang.*;
import java.util.*;


public class Main {
	public static final Scanner scanner = new Scanner(System.in);
	
	public static void testCase(int caseIndex) {
		int N = scanner.nextInt();
		
		GameMap gameMap = new GameMap(N, N);
		
		for (int r = 0; r < N; r += 1) {
			for (int c = 0; c < N; c += 1) {
				int buildings = scanner.nextInt();
				gameMap.setBuildingsAt(r, c, buildings);
			}
		}
		
		int answer = 0;
		
		for (int r = 0; r < N; r += 1) {
			for (int c = 0; c < N; c += 1) {
				// �ش� ��ġ���� ����� �� �ִ� ��� �ǹ��� ��
				int numberOfBuildings = gameMap.getTotalPointAt(r, c);
				
				// �ִ밪�� �����Ѵ�
				answer = Math.max(answer, numberOfBuildings);
			}
		}
		
		System.out.println(answer);
	}
	
	public static void main(String[] args) throws Exception {
		int caseSize = scanner.nextInt();
		
		for (int caseIndex = 1; caseIndex <= caseSize; caseIndex += 1) {
			testCase(caseIndex);
		}
	}
	
}

class GameMap {
	public final int rows;
	public final int columns;
	private final int[][] buildings;   // buildings[r][c] := (r, c)�� �����ϴ� �ǹ� �� 
	
	private final int[] rowSums;    //rowSums[r] := r�࿡ �����ϴ� �ǹ��� ��
	private final int[] colSums;    //colSums[c] := c���� �����ϴ� �ǹ��� ��
	private final int[] diagonalSumA; //diagonalSumA[k] := k��° �� ���� �밢���� �����ϴ� �ǹ��� ��
	private final int[] diagonalSumB; //diagonalSumB[k] := k��° �� ���� �밢���� �����ϴ� �ǹ��� ��
	
	
	public GameMap(int rows, int columns) {
		this.rows = rows;
		this.columns = columns;
		this.buildings = new int[rows][columns];
		this.rowSums = new int[rows];
		this.colSums = new int[columns];
		this.diagonalSumA = new int[rows + columns];
		this.diagonalSumB = new int[rows + columns];
	}
	
	/**
	 * (r, c)ĭ�� �����ϴ� �ǹ��� ��
	 *
	 * @param r
	 * @param c
	 * @return �ش� ĭ�� �ǹ� ��
	 */
	public int getBuildingsAt(int r, int c) {
		if (this.isInside(r, c) == false)
			return 0;
		
		return this.buildings[r][c];
	}
	
	/**
	 * (r, c)ĭ�� �����ϴ� �ǹ� ���� �����ϴ� �޼ҵ�
	 *
	 * @param r
	 * @param c
	 * @param value �����ϴ� �ǹ� ��
	 */
	public void setBuildingsAt(int r, int c, int value) {
		if (this.isInside(r, c) == false) {
			return;
		}
		
		int origin = getBuildingsAt(r, c);  // ���� �� �ǹ� ��
		int delta = value - origin;         // ���� ��/�� �ǹ����� ��
		
		this.buildings[r][c] = value; // �ش� ĭ�� �ǹ� �� ���� ���� 
		this.rowSums[r] += delta;       // �ش� ���� �� �ǹ� �� ���� 
		this.colSums[c] += delta;       // �ش� ���� �� �ǹ� �� ����
		this.diagonalSumA[r + c] += delta;  // �ش� �밢���� �� �ǹ� �� ���� 
		this.diagonalSumB[c - r + rows - 1] += delta; // �ش� �밢���� �� �ǹ� �� ���� 
	}
	
	
	/**
	 * (r, c)ĭ�� Ÿ���� ������ �� ���� �� �ִ� �� ������ ����ϴ� �޼ҵ�
	 *
	 * @param r
	 * @param c
	 * @return �ش� �������� ��� ������ �ǹ��� ��
	 */
	public int getTotalPointAt(int r, int c) {
		int buildings = 0;
		buildings += this.rowSums[r];   // ���� �������� ��� ������ �ǹ� ��
		buildings += this.colSums[c];   // ���� �������� ��� ������ �ǹ� ��
		buildings += this.diagonalSumA[r + c];  // �� �������� ��� ������ �ǹ� ��
		buildings += this.diagonalSumB[c - r + rows - 1]; // �� �������� ��� ������ �ǹ� ��
		
		// ������ ��� �� �� �ߺ����� ���Ǿ��� ������ �������ش�.
		buildings -= getBuildingsAt(r, c) * 3;
		
		return buildings;
	}
	
	
	/**
	 * �ش� ĭ�� ���� ���ο� �����ϴ� ĭ���� �˻��ϴ� �޼ҵ�
	 *
	 * @param r
	 * @param c
	 * @return (r, c)�� ���� �� ��ȿ�� ĭ�̸� true, �ƴϸ� false
	 */
	public boolean isInside(int r, int c) {
		if (r < 0 || r >= this.rows)
			return false;
		if (c < 0 || c >= this.columns)
			return false;
		return true;
	}
}

