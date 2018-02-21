package com.arkodestudio.matematikan;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import com.arkodestudio.framework.FileIO;
import com.arkodestudio.matematikan.Settings.Stage.Lumayan;
import com.arkodestudio.matematikan.Settings.Stage.Mudah;
import com.arkodestudio.matematikan.Settings.Stage.Susah;

public class Settings {	
	public final static int EMPTY_INT = 0xffffd8f1; //-9999;
	public final static int SCORE_DEC = 10;
	public final static float[] menuMoveSpeed = {45,75,90};
	public final static int[] menuBgStaticY = {0,330,415};
	public static float[] menuBgMoveX = {0,0,0};
	public static float[] menuBgNewMoveX = {0,0,0};
	public final static float[] gameMoveSpeed = {45,75,90}; // made it different with menuMoveSpeed
	public final static float[] gameBgStaticY = {0,330,415}; // so make it handy if this game gonna make its squel
	public static float[] gameBgMoveX = {0,0,0};
	public static float[] gameBgNewMoveX = {0,0,0};
	public static boolean isSoundEnabled = true;
	public static class Stage {
		public static enum State {
			STAR_LOCKED, STAR_0, STAR_1, STAR_2, STAR_3
		}
		public static class Mudah {
			public static boolean isThisLevelPlayable = true;
			public final static boolean[] isPlayableStage = {
				true,false,false,false,false,false,false,false,false,false				
			};
			public final static State[] stateStage = {
				State.STAR_0,State.STAR_LOCKED,State.STAR_LOCKED,State.STAR_LOCKED,State.STAR_LOCKED,
				State.STAR_LOCKED,State.STAR_LOCKED,State.STAR_LOCKED,State.STAR_LOCKED,State.STAR_LOCKED
			};		
			public final static int targetStage[] =  {44,12,50,25,44,5,1,30,148,-89};
			public final static SetOpr oprStage[] = {
				new SetOpr(1,"+"),
				new SetOpr(1,"-"),
				new SetOpr(2,"++"),
				new SetOpr(2,"+-"),
				new SetOpr(3,"+++"),
				new SetOpr(3,"---"),
				new SetOpr(4,"+-+-"),
				new SetOpr(5,"-+-+-"),
				new SetOpr(5,"+++++"),
				new SetOpr(5,"-----")
			};
			public final static int bestScoreStage[] = {
				EMPTY_INT,EMPTY_INT,EMPTY_INT,EMPTY_INT,EMPTY_INT,
				EMPTY_INT,EMPTY_INT,EMPTY_INT,EMPTY_INT,EMPTY_INT
			};
 		}
		public static class Lumayan {
			public static boolean isThisLevelPlayable = false;
			public final static boolean[] isPlayableStage = {
				false,false,false,false,false,false,false,false,false,false				
			};
			public final static State[] stateStage = {
				State.STAR_LOCKED,State.STAR_LOCKED,State.STAR_LOCKED,State.STAR_LOCKED,State.STAR_LOCKED,
				State.STAR_LOCKED,State.STAR_LOCKED,State.STAR_LOCKED,State.STAR_LOCKED,State.STAR_LOCKED
			};
			public final static int targetStage[] = {250,261,308,270,10,0,8,-6,142,-43};
			public final static SetOpr oprStage[] = {
				new SetOpr(2,"xx"),
				new SetOpr(3,"xx+"),
				new SetOpr(3,"x+x"),
				new SetOpr(4,"x+x-"),
				new SetOpr(2,"+:"),
				new SetOpr(3,":-+"),
				new SetOpr(3,":+:"),
				new SetOpr(4,":+:-"),
				new SetOpr(4,"+-x+"),
				new SetOpr(4,"-+:-")
			};
			public final static int bestScoreStage[] = {
				EMPTY_INT,EMPTY_INT,EMPTY_INT,EMPTY_INT,EMPTY_INT,
				EMPTY_INT,EMPTY_INT,EMPTY_INT,EMPTY_INT,EMPTY_INT
			};
		}
		public static class Susah {
			public static boolean isThisLevelPlayable = false;
			public final static boolean[] isPlayableStage = {				
				false,false,false,false,false,false,false,false,false,false
			};
			public final static State[] stateStage = {
				State.STAR_LOCKED,State.STAR_LOCKED,State.STAR_LOCKED,State.STAR_LOCKED,State.STAR_LOCKED,
				State.STAR_LOCKED,State.STAR_LOCKED,State.STAR_LOCKED,State.STAR_LOCKED,State.STAR_LOCKED
			};
			public final static int targetStage[] = {25,10,85,112,220,1,65,3,27,313};
			public final static SetOpr oprStage[] = {
				new SetOpr(3,"x:+"),
				new SetOpr(4,"x:x:"),
				new SetOpr(4,":x+x"),
				new SetOpr(4,"x:-:"),
				new SetOpr(5,"x:x:x"),
				new SetOpr(5,":x:x:"),
				new SetOpr(5,"+x+:-"),
				new SetOpr(5,"-:-x+"),
				new SetOpr(6,"x:x:x:"),
				new SetOpr(6,":+x+:x")
			};
			public final static int bestScoreStage[] = {
				EMPTY_INT,EMPTY_INT,EMPTY_INT,EMPTY_INT,EMPTY_INT,
				EMPTY_INT,EMPTY_INT,EMPTY_INT,EMPTY_INT,EMPTY_INT
			};
		}
	}	
	public static void load(FileIO files) {
		BufferedReader in = null;
		try{
			in = new BufferedReader(new InputStreamReader(files.readFile(".matematikan")));
			isSoundEnabled = Boolean.parseBoolean(in.readLine());
			Mudah.isThisLevelPlayable = Boolean.parseBoolean(in.readLine());
			Lumayan.isThisLevelPlayable = Boolean.parseBoolean(in.readLine());
			Susah.isThisLevelPlayable = Boolean.parseBoolean(in.readLine());
			for(int c = 0; c < 10; c++) {				
				Mudah.isPlayableStage[c] = Boolean.parseBoolean(in.readLine());
				switch (Integer.parseInt(in.readLine())) {
					case 0 :
						Mudah.stateStage[c] = Stage.State.STAR_LOCKED;
						break;
					case 1 :
						Mudah.stateStage[c] = Stage.State.STAR_0;
						break;
					case 2 : 
						Mudah.stateStage[c] = Stage.State.STAR_1;
						break;
					case 3 :
						Mudah.stateStage[c] = Stage.State.STAR_2;
						break;
					case 4 :
						Mudah.stateStage[c] = Stage.State.STAR_3;
						break;
										
				}
				Mudah.bestScoreStage[c] = Integer.parseInt(in.readLine());
				Lumayan.isPlayableStage[c] = Boolean.parseBoolean(in.readLine());
				switch (Integer.parseInt(in.readLine())) {
					case 0 :
						Lumayan.stateStage[c] = Stage.State.STAR_LOCKED;
						break;
					case 1 :
						Lumayan.stateStage[c] = Stage.State.STAR_0;
						break;
					case 2 : 
						Lumayan.stateStage[c] = Stage.State.STAR_1;
						break;
					case 3 :
						Lumayan.stateStage[c] = Stage.State.STAR_2;
						break;
					case 4 :
						Lumayan.stateStage[c] = Stage.State.STAR_3;
						break;
										
				}
				Lumayan.bestScoreStage[c] = Integer.parseInt(in.readLine());
				Susah.isPlayableStage[c] = Boolean.parseBoolean(in.readLine());
				switch (Integer.parseInt(in.readLine())) {
					case 0 :
						Susah.stateStage[c] = Stage.State.STAR_LOCKED;
						break;
					case 1 :
						Susah.stateStage[c] = Stage.State.STAR_0;
						break;
					case 2 : 
						Susah.stateStage[c] = Stage.State.STAR_1;
						break;
					case 3 :
						Susah.stateStage[c] = Stage.State.STAR_2;
						break;
					case 4 :
						Susah.stateStage[c] = Stage.State.STAR_3;
						break;
										
				}
				Susah.bestScoreStage[c] = Integer.parseInt(in.readLine());
			}
			for (int c = 0; c < 8; c++) { // 8 =  total achievement
				switch (Integer.parseInt(in.readLine())) {
					case 0:
						Achievement.achievementStatusAtIndex[c] = Achievement.Status.NEW_ACHIEVEMENT;
						break;
					case 1:
						Achievement.achievementStatusAtIndex[c] = Achievement.Status.NO_ACHIEVEMENT;
						break;
					case 2:
						Achievement.achievementStatusAtIndex[c] = Achievement.Status.OLD_ACHIEVEMENT;
						break;								
				}
			}
		} catch(IOException e) {
		
		} catch(NumberFormatException e) {
		
		} finally{
			try{
				if(in != null)
					in.close();
		} 	catch(IOException e) {
			
			}
		}
	}
	
	
	public static void save(FileIO files) {
		BufferedWriter out = null;
		
		try{
			out = new BufferedWriter(new OutputStreamWriter(files.writeFile(".matematikan")));
			out.write(Boolean.toString(isSoundEnabled) + "\n");	
			out.write(Boolean.toString(Mudah.isThisLevelPlayable) + "\n");
			out.write(Boolean.toString(Lumayan.isThisLevelPlayable) + "\n");
			out.write(Boolean.toString(Susah.isThisLevelPlayable) + "\n");
			for(int c = 0; c < 10; c++) {				
				out.write(Boolean.toString(Mudah.isPlayableStage[c]) + "\n");				
				switch (Mudah.stateStage[c]) {
					case STAR_LOCKED:
						out.write(Integer.toString(0)+ "\n");
						break;	
					case STAR_0:
						out.write(Integer.toString(1)+ "\n");
						break;
					case STAR_1:
						out.write(Integer.toString(2)+ "\n");
						break;
					case STAR_2:
						out.write(Integer.toString(3)+ "\n");
						break;
					case STAR_3:
						out.write(Integer.toString(4)+ "\n");
						break;										
				}	
				out.write(Integer.toString(Mudah.bestScoreStage[c]) + "\n");
				out.write(Boolean.toString(Lumayan.isPlayableStage[c]) + "\n");				
				switch (Lumayan.stateStage[c]) {
					case STAR_LOCKED:
						out.write(Integer.toString(0)+ "\n");
						break;	
					case STAR_0:
						out.write(Integer.toString(1)+ "\n");
						break;
					case STAR_1:
						out.write(Integer.toString(2)+ "\n");
						break;
					case STAR_2:
						out.write(Integer.toString(3)+ "\n");
						break;
					case STAR_3:
						out.write(Integer.toString(4)+ "\n");
						break;										
				}
				out.write(Integer.toString(Lumayan.bestScoreStage[c]) + "\n");
				out.write(Boolean.toString(Susah.isPlayableStage[c])  + "\n");				
				switch (Susah.stateStage[c]) {
					case STAR_LOCKED:
						out.write(Integer.toString(0)+ "\n");
						break;	
					case STAR_0:
						out.write(Integer.toString(1)+ "\n");
						break;
					case STAR_1:
						out.write(Integer.toString(2)+ "\n");
						break;
					case STAR_2:
						out.write(Integer.toString(3)+ "\n");
						break;
					case STAR_3:
						out.write(Integer.toString(4)+ "\n");
						break;										
				}				
				out.write(Integer.toString(Susah.bestScoreStage[c]) + "\n");
			}
			for (int c = 0; c < 8; c++) { // 8 =  total achievement
				switch (Achievement.achievementStatusAtIndex[c]) {
					case NEW_ACHIEVEMENT:
						out.write(Integer.toString(0)+ "\n");
						break;
					case NO_ACHIEVEMENT:
						out.write(Integer.toString(1)+ "\n");
						break;
					case OLD_ACHIEVEMENT:
						out.write(Integer.toString(2)+ "\n");
						break;								
				}
			}
		} catch(IOException e) {
		
		} finally{
			try{
				if(out != null)
					out.close();
			} catch(IOException e) {
			
			}
		}
	}
	public static void resetSaveState() {		
		Mudah.isThisLevelPlayable = true;
		Lumayan.isThisLevelPlayable = false;
		Susah.isThisLevelPlayable = false;
		for(int c = 0; c < 10; c++) {
			if (c == 0) {
				Mudah.isPlayableStage[c] = true;			
				Mudah.stateStage[c] = Stage.State.STAR_0;
			} else {
				Mudah.isPlayableStage[c] = false;			
				Mudah.stateStage[c] = Stage.State.STAR_LOCKED;
			}		
			Mudah.bestScoreStage[c] = 0;
			Lumayan.isPlayableStage[c] = false;
			Lumayan.stateStage[c] = Stage.State.STAR_LOCKED;
			Lumayan.bestScoreStage[c] = 0;
			Susah.isPlayableStage[c] = false;
			Susah.stateStage[c] = Stage.State.STAR_LOCKED;
			Susah.bestScoreStage[c] = 0;
		}		
		for (int c = 0; c< Achievement.TOTAL_ACHIEVEMENT; c++) {
			Achievement.achievementStatusAtIndex[c] = Achievement.Status.NO_ACHIEVEMENT;
		}
	}
	public static class Achievement {
		public enum Status {
			OLD_ACHIEVEMENT, NEW_ACHIEVEMENT, NO_ACHIEVEMENT
		}
		public final static int TOTAL_ACHIEVEMENT = 8; 
		public static Achievement.Status[] achievementStatusAtIndex = {
			Achievement.Status.NO_ACHIEVEMENT,Achievement.Status.NO_ACHIEVEMENT, 
			Achievement.Status.NO_ACHIEVEMENT,Achievement.Status.NO_ACHIEVEMENT,
			Achievement.Status.NO_ACHIEVEMENT,Achievement.Status.NO_ACHIEVEMENT,
			Achievement.Status.NO_ACHIEVEMENT,Achievement.Status.NO_ACHIEVEMENT
		};
		public static boolean isClearingStage1 = false; // 0 // achievement index (list)
		public static boolean isClearingMudah = false; // 1
		public static boolean isCompletingMudah = false; // 2
		public static boolean isClearingLumayan = false; // 3
		public static boolean isCompletingLumayan = false; // 4
		public static boolean isClearingSusah = false; // 5
		public static boolean isCompletingSusah = false; // 6
		public static boolean isCompletingAll = false; // 7		 
		
		public static void checkAchievement() {		
			if (Mudah.stateStage[0] == Stage.State.STAR_1 || Mudah.stateStage[0] == Stage.State.STAR_2 ||
					Mudah.stateStage[0] == Stage.State.STAR_3 ){
				Achievement.isClearingStage1 = true;
				if (Achievement.achievementStatusAtIndex[0] == Achievement.Status.NO_ACHIEVEMENT) {
					Achievement.achievementStatusAtIndex[0] = Achievement.Status.NEW_ACHIEVEMENT;
				}
			} else {
				Achievement.isClearingStage1 = false;
			}
			if (Lumayan.isThisLevelPlayable) {
				Achievement.isClearingMudah = true;
				if (Achievement.achievementStatusAtIndex[1] == Achievement.Status.NO_ACHIEVEMENT) {
					Achievement.achievementStatusAtIndex[1] = Achievement.Status.NEW_ACHIEVEMENT;
				}
				boolean checker = true;
				for (int c = 0; c < 10; c++) { // 10 = total stage
					if (Mudah.stateStage[c] == Stage.State.STAR_3) {
						checker &= true;
					} else {
						checker &= false;
					}
				}
				if (checker) {
					Achievement.isCompletingMudah = true;
					if (Achievement.achievementStatusAtIndex[2] == Achievement.Status.NO_ACHIEVEMENT) {
						Achievement.achievementStatusAtIndex[2] = Achievement.Status.NEW_ACHIEVEMENT;
					}
				} else {
					Achievement.isCompletingMudah = false;
				}
				
			} else {
				Achievement.isClearingMudah = false;
			}
			if (Susah.isThisLevelPlayable) {
				Achievement.isClearingLumayan = true;
				if (Achievement.achievementStatusAtIndex[3] == Achievement.Status.NO_ACHIEVEMENT) {
					Achievement.achievementStatusAtIndex[3] = Achievement.Status.NEW_ACHIEVEMENT;
				}
				boolean checker = true;
				for (int c = 0; c < 10; c++) { // 10 = total stage
					if (Lumayan.stateStage[c] == Stage.State.STAR_3) {
						checker &= true;
					} else {
						checker &= false;
					}
				}
				if (checker) {
					Achievement.isCompletingLumayan = true;
					if (Achievement.achievementStatusAtIndex[4] == Achievement.Status.NO_ACHIEVEMENT) {
						Achievement.achievementStatusAtIndex[4] = Achievement.Status.NEW_ACHIEVEMENT;
					}
				} else {
					Achievement.isCompletingLumayan = false;
				}
				
			} else {
				Achievement.isClearingLumayan = false;
			}
			if (Susah.stateStage[9] == Stage.State.STAR_1 || Susah.stateStage[9] == Stage.State.STAR_2 ||
					Susah.stateStage[9] == Stage.State.STAR_3 ){
				Achievement.isClearingSusah = true;
				if (Susah.stateStage[9] == Stage.State.STAR_3) {
					boolean checker = true;
					if (Achievement.achievementStatusAtIndex[5] == Achievement.Status.NO_ACHIEVEMENT) {
						Achievement.achievementStatusAtIndex[5] = Achievement.Status.NEW_ACHIEVEMENT;
					}
					for (int c = 0; c < 10; c++) { // 10 = total stage
						if (Susah.stateStage[c] == Stage.State.STAR_3) {
							checker &= true;
						} else {
							checker &= false;
						}
					}
					if (checker) {
						Achievement.isCompletingSusah = true;
						if (Achievement.achievementStatusAtIndex[6] == Achievement.Status.NO_ACHIEVEMENT) {
							Achievement.achievementStatusAtIndex[6] = Achievement.Status.NEW_ACHIEVEMENT;
						}
					} else {
						Achievement.isCompletingSusah = false;
					}
				}
			} else {
				Achievement.isClearingSusah = false;
			}
			if (Achievement.isClearingMudah && Achievement.isCompletingLumayan && Achievement.isCompletingSusah) {
				Achievement.isCompletingAll = true;
				if (Achievement.achievementStatusAtIndex[7] == Achievement.Status.NO_ACHIEVEMENT) {
					Achievement.achievementStatusAtIndex[7] = Achievement.Status.NEW_ACHIEVEMENT;
				}
			} else {
				Achievement.isCompletingAll = false;
			}
		}
	}
}

class SetOpr {	
	public int numOpr;
	public int slot;
	public String operator;
	
	public SetOpr(int numOpr, String setOpr) {
		this.operator = setOpr;
		this.numOpr = numOpr;
		slot = numOpr+1;
	}
}

