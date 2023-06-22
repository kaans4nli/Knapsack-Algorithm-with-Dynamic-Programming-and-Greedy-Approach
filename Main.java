package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class Okss {

	int GOLD_AMOUNT;
	int MAX_LEVEL_ALLOWED;
	int NUMBER_OF_AVAILABLE_PIECES_PER_LEVEL;
	String selected_piece_names_r;
	String[][] selected_pieces_names_d;
	String selected_piece_names_g;

	Random rnd;

	public double greedyApproach(int[] val, int[] wt, int capacity, String[] heros, String[] heros_piece_type,
			int piece_bound) {

		selected_piece_names_g = "";
		ItemValue[] iVal = new ItemValue[wt.length];

		for (int i = 0; i < wt.length; i++) {
			iVal[i] = new ItemValue(wt[i], val[i], i);
		}

		// sorting items by value;
		Arrays.sort(iVal, new Comparator<ItemValue>() {
			public int compare(ItemValue o1, ItemValue o2) {
				return o2.cost.compareTo(o1.cost);
			}
		});

		double totalValue = 0d;

		String last_piece_type = "";
		for (ItemValue i : iVal) {

			int curWt = (int) i.wt;
			int curVal = (int) i.val;

			//take value when has enough gold.
			if (capacity - curWt >= 0 && last_piece_type != heros_piece_type[(int) i.ind]) {
				// this weight can be picked while
				capacity = capacity - curWt;
				totalValue += curVal;
				selected_piece_names_g += heros_piece_type[(int) i.ind] + " - " + heros[(int) i.ind] + "\n";
				last_piece_type = heros_piece_type[(int) i.ind];
			}
			/*
			 * else { // item cant be picked whole double fraction = ((double)capacity /
			 * (double)curWt); totalValue += (curVal * fraction); selected_piece_names_g +=
			 * heros_piece_type[(int)i.ind] + " - " + heros[(int)i.ind] + "\n"; capacity =
			 * (int)(capacity - (curWt * fraction)); break; }
			 */

		}

		return totalValue;
	}

	// item value class
	static class ItemValue {
		Double cost;
		double wt, val, ind;

		// item value function
		@SuppressWarnings("deprecation")
		public ItemValue(int wt, int val, int ind) {
			this.wt = wt;
			this.val = val;
			this.ind = ind;
			cost = new Double((double) val / (double) wt);
		}

	}

	public int dynamicProgrammingApproach(int[] heros_attack_point, int[] heros_gold, int gold, String[] heros,
			String[] heros_piece_type) {
		// basic checks
		if (gold <= 0 || heros_gold.length == 0 || heros_attack_point.length != heros_gold.length)
			return 0;

		int n = heros_attack_point.length;
		int[][] dp = new int[n][gold + 1];
		selected_pieces_names_d = new String[n][gold + 1];

		// populate the gold=0 columns, with '0' gold we have '0' hero
		// heros_attack_point
		for (int i = 0; i < n; i++)
			dp[i][0] = 0;

		// if we have only one heros_gold, we will take it if it is not more than
		// the gold
		for (int c = 0; c <= gold; c++) {
			if (heros_gold[0] <= c) {
				dp[0][c] = heros_attack_point[0];
				selected_pieces_names_d[0][c] = heros_piece_type[0] + " - " + heros[0] + "\n";
			}
		}

		// process all sub-arrays for all the goldes
		for (int i = 1; i < n; i++) {
			for (int c = 1; c <= gold; c++) {
				int profit1 = 0, profit2 = 0;
				String profit1_piece = "", profit2_piece = "";
				// include the item, if it is not more than the gold
				if (heros_gold[i] <= c) {
					profit1 = heros_attack_point[i] + dp[i - 1][c - heros_gold[i]];
					profit1_piece = heros_piece_type[i] + " - " + heros[i] + "\n"
							+ selected_pieces_names_d[i - 1][c - heros_gold[i]] + "\n";
				}
				// exclude the item
				profit2 = dp[i - 1][c];
				profit2_piece = selected_pieces_names_d[i - 1][c];
				// take maximum
				dp[i][c] = Math.max(profit1, profit2);
				if (profit1 > profit2)
					selected_pieces_names_d[i][c] = profit1_piece;
				else
					selected_pieces_names_d[i][c] = profit2_piece;
			}
		}

		// maximum heros_attack_point will be at the bottom-right corner.
		return dp[n - 1][gold];
	}

	public int randomApproach(int[] heros_attack_point, int[] heros_gold, int gold, int rnd_bound, String[] heros,
			String[] heros_piece_type) {
		rnd = new Random();
		int rnd_num = rnd.nextInt(rnd_bound + 1);
		int total_attack_point = 0;
		selected_piece_names_r = "";
		int temp_rnd_bound = 0;

		while (rnd_num < heros_gold.length && heros_gold[rnd_num] <= gold) {
			// rnd_num, max üretilen random değere eşitse seçim yapılmıyor.
			//örnek allowed level 3se ve rnd_num 3 gelirse
			if (rnd_num != rnd_bound) {
				total_attack_point += heros_attack_point[rnd_num];
				gold -= heros_gold[rnd_num];
				selected_piece_names_r += heros_piece_type[rnd_num] + " - " + heros[rnd_num] + "\n";
			}
			//for that have't take the same pieces.
			temp_rnd_bound = rnd_bound;
			rnd_bound += rnd_bound;
			rnd_num = rnd.nextInt(rnd_bound + 1) + temp_rnd_bound;
		}

		return total_attack_point;
	}

	public static void main(String[] args) throws IOException {

		Okss oks = new Okss();

		// values control
		while (!(5 <= oks.getGOLD_AMOUNT() && oks.getGOLD_AMOUNT() <= 1200)) {
			try {
				System.out.print("Enter the gold amount : ");
				BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
				oks.setGOLD_AMOUNT(Integer.parseInt(new String(input.readLine())));
			} catch (Exception e) {
				
			}
		}
		while (!(1 <= oks.getMAX_LEVEL_ALLOWED() && oks.getMAX_LEVEL_ALLOWED() <= 9)) {
			try {
				System.out.print("Enter the maximum allowed level : ");
				BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
				oks.setMAX_LEVEL_ALLOWED(Integer.parseInt(new String(input.readLine())));
			} catch (Exception e) {
				
			}
		}
		while (!(1 <= oks.getNUMBER_OF_AVAILABLE_PIECES_PER_LEVEL()
				&& oks.getNUMBER_OF_AVAILABLE_PIECES_PER_LEVEL() <= 10)) {
			try {
				System.out.print("Enter the number of pieces for each level : ");
				BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
				oks.setNUMBER_OF_AVAILABLE_PIECES_PER_LEVEL(Integer.parseInt(new String(input.readLine())));
			} catch (Exception e) {
				
			}
		}

		//necessary arrays
		String[] heros = new String[oks.getNUMBER_OF_AVAILABLE_PIECES_PER_LEVEL() * oks.getMAX_LEVEL_ALLOWED()];
		String[] heros_piece_type = new String[oks.getNUMBER_OF_AVAILABLE_PIECES_PER_LEVEL()
				* oks.getMAX_LEVEL_ALLOWED()];
		int[] heros_gold = new int[oks.getNUMBER_OF_AVAILABLE_PIECES_PER_LEVEL() * oks.getMAX_LEVEL_ALLOWED()];
		int[] heros_attack_point = new int[oks.getNUMBER_OF_AVAILABLE_PIECES_PER_LEVEL() * oks.getMAX_LEVEL_ALLOWED()];

		String line = "";
		String splitBy = ",";
		String[] lines;
		int countLen = 0;
		int allowed_level = oks.getMAX_LEVEL_ALLOWED();
		BufferedReader br = new BufferedReader(new FileReader("input_1.csv"));
		br.readLine();
		while ((line = br.readLine()) != null) {
			lines = line.split(splitBy);
			heros[countLen] = lines[0];
			heros_piece_type[countLen] = lines[1];
			heros_gold[countLen] = Integer.parseInt(lines[2]);
			heros_attack_point[countLen] = Integer.parseInt(lines[3]);
			/*
			 * System.out.println(heros[countLen] + " " + heros_piece_type[countLen] + " " +
			 * heros_gold[countLen] + " " + heros_attack_point[countLen]);
			 */
			countLen++;
			//control that allowed level for taking the entering value.
			if (countLen % oks.getNUMBER_OF_AVAILABLE_PIECES_PER_LEVEL() == 0) {
				allowed_level--;
				if (allowed_level == 0)
					break;
				for (int i = 0; i < 10; i++)
					br.readLine();
			}
		}
		br.close();

		// execution times
		long startTime = System.nanoTime();
		int maxProfit_d = oks.dynamicProgrammingApproach(heros_attack_point, heros_gold, oks.getGOLD_AMOUNT(), heros,
				heros_piece_type);
		long estimatedTime = System.nanoTime() - startTime;
		String calculatedTimes = "Dynamic Programming ex. time is " + estimatedTime + "\n";

		startTime = System.nanoTime();
		double maxProfit_g = oks.greedyApproach(heros_attack_point, heros_gold, oks.getGOLD_AMOUNT(), heros,
				heros_piece_type, oks.getNUMBER_OF_AVAILABLE_PIECES_PER_LEVEL());
		estimatedTime = System.nanoTime() - startTime;
		calculatedTimes += "Greedy Approach ex. time is " + estimatedTime + "\n";

		startTime = System.nanoTime();
		int maxProfit_r = oks.randomApproach(heros_attack_point, heros_gold, oks.getGOLD_AMOUNT(),
				oks.getNUMBER_OF_AVAILABLE_PIECES_PER_LEVEL(), heros, heros_piece_type);
		estimatedTime = System.nanoTime() - startTime;
		calculatedTimes += "Random Approach ex. time is " + estimatedTime + "\n";

		//prints
		System.out.println("----- TRIAL #1 -----");
		System.out
				.println("Computer's Greedy Approach result\n" + maxProfit_g + "\n" + oks.getSelected_piece_names_g());
		System.out.print("User's Dynamic Programming result\n" + maxProfit_d + "\n"
				+ oks.getSelected_pieces_names_d()[heros_attack_point.length - 1][oks.getGOLD_AMOUNT()]);
		System.out.println("----- TRIAL #2 -----");
		System.out
				.println("Computer's Random Approach result\n" + maxProfit_r + "\n" + oks.getSelected_piece_names_r());
		System.out.print("User's Dynamic Programming result\n" + maxProfit_d + "\n"
				+ oks.getSelected_pieces_names_d()[heros_attack_point.length - 1][oks.getGOLD_AMOUNT()]);
		System.out.println(calculatedTimes);
	}

	public int getGOLD_AMOUNT() {
		return GOLD_AMOUNT;
	}

	public void setGOLD_AMOUNT(int gOLD_AMOUNT) {
		GOLD_AMOUNT = gOLD_AMOUNT;
	}

	public int getMAX_LEVEL_ALLOWED() {
		return MAX_LEVEL_ALLOWED;
	}

	public void setMAX_LEVEL_ALLOWED(int mAX_LEVEL_ALLOWED) {
		MAX_LEVEL_ALLOWED = mAX_LEVEL_ALLOWED;
	}

	public int getNUMBER_OF_AVAILABLE_PIECES_PER_LEVEL() {
		return NUMBER_OF_AVAILABLE_PIECES_PER_LEVEL;
	}

	public void setNUMBER_OF_AVAILABLE_PIECES_PER_LEVEL(int nUMBER_OF_AVAILABLE_PIECES_PER_LEVEL) {
		NUMBER_OF_AVAILABLE_PIECES_PER_LEVEL = nUMBER_OF_AVAILABLE_PIECES_PER_LEVEL;
	}

	public String getSelected_piece_names_r() {
		return selected_piece_names_r;
	}

	public String[][] getSelected_pieces_names_d() {
		return selected_pieces_names_d;
	}

	public String getSelected_piece_names_g() {
		return selected_piece_names_g;
	}

}
