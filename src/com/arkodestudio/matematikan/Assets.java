package com.arkodestudio.matematikan;

import java.io.File;

import com.arkodestudio.framework.Music;
import com.arkodestudio.framework.Pixmap;
import com.arkodestudio.framework.Sound;

public class Assets {
	public static Pixmap splashScreen;
	public static Pixmap loadingScreen;	
	public static Pixmap gameLogo;
	
	public static class Bg {
		public static Pixmap menu1;
		public static Pixmap menu2;
		public static Pixmap menu3;
		public static Pixmap levelMudah1;
		public static Pixmap levelMudah2;
		public static Pixmap levelMudah3;
		public static Pixmap levelLumayan1;
		public static Pixmap levelLumayan2;
		public static Pixmap levelLumayan3;
		public static Pixmap levelSusah1;
		public static Pixmap levelSusah2;
		public static Pixmap levelSusah3;
		
		public static class Tutorial {
			public static Pixmap inHelp1;
			public static Pixmap inHelp2;
			public static Pixmap inGame1;
			public static Pixmap inGame2;
		}
		public static class Credit {
			public static Pixmap screen1;
			public static Pixmap screen2;
			public static Pixmap screen3;
		}
	}
	
	public static class InGame {
		public static class Finished {
			public static Pixmap clear3;
			public static Pixmap clear2;
			public static Pixmap clear1;
			public static Pixmap failed;
			public static Pixmap scoreInfo;
			public static Pixmap scoreBest;
		}
		public static class Operator {
			public static Pixmap tambah;
			public static Pixmap bagi;
			public static Pixmap kurang;
			public static Pixmap kali;
			public static Pixmap samaDengan;		
		}
	}
	
	public static class Penyelam {
		public static Pixmap act1;
		public static Pixmap act2;
		public static Pixmap act3;
		public static Pixmap tombak;
		public static Pixmap jaring;
	}
	
	public static class Ikan {		
		public static Pixmap merahAction1;
		public static Pixmap merahAction2;
		public static Pixmap merahAction3;
		public static Pixmap hijauAction1;
		public static Pixmap hijauAction2;
		public static Pixmap hijauAction3;
		public static Pixmap kuningAction1;
		public static Pixmap kuningAction2;
		public static Pixmap kuningAction3;
		public static Pixmap unguAction1;
		public static Pixmap unguAction2;
		public static Pixmap unguAction3;
		
		public static class Effect {
			public static Pixmap min10;
		}
	}
	
	public static class Container {
		public static Pixmap kapalKecil;
		public static Pixmap kapalBesar;
		public static Pixmap tong;
	}
	
	public static class Font {		
		public static Pixmap ikan;
		public static Pixmap container;
		public static Pixmap general;
	}
	
	public static class Achievement {
		public static Pixmap achievedSign;
		public static Pixmap unAchievedSign;
		public static Pixmap clearingStage1;
		public static Pixmap clearingMudah;
		public static Pixmap completingMudah;
		public static Pixmap clearingLumayan;
		public static Pixmap completingLumayan;
		public static Pixmap clearingSusah;
		public static Pixmap completingSusah;
		public static Pixmap completingAll;
	}
	
	public static class Button {
		public static Pixmap back;
		
		public static class Game {
			public static Pixmap tembak;
			public static Pixmap tangkap;
			public static Pixmap pause;
		}	
		public static class Setttings {
			public static Pixmap reset;
			public static Pixmap soundOn; 
			public static Pixmap soundOff; 
		}
		public static class Main {
			public static Pixmap play;
			public static Pixmap achievement;
			public static Pixmap help; 
			public static Pixmap settings; 
			public static Pixmap soundOff; // ??
			public static Pixmap exit;
			public static Pixmap about;
		}
		public static class Level {			
			public static Pixmap mudah;
			public static Pixmap lumayan;
			public static Pixmap susah;	
			public static Pixmap locked;
		}		
		public static class Stage {
			public static Pixmap ke1;
			public static Pixmap ke2;
			public static Pixmap ke3;
			public static Pixmap ke4;
			public static Pixmap ke5;
			public static Pixmap ke6;
			public static Pixmap ke7;
			public static Pixmap ke8;
			public static Pixmap ke9;
			public static Pixmap ke10;
			public static Pixmap locked;
			public static Pixmap starLocked;
			public static Pixmap star0;
			public static Pixmap star1;
			public static Pixmap star2;
			public static Pixmap star3;
		}		
		public static class Confirmation {
			public static class Help {
				public static Pixmap lanjutkan;
				public static Pixmap berikutnya;				
				public static Pixmap keMenu;
			}
			public static class Game {
				public static Pixmap yesExit;
				public static Pixmap noExit;
				public static Pixmap yesReset;
				public static Pixmap noReset;
				public static Pixmap lanjutkan;
				public static Pixmap berikutnya;
				public static Pixmap ulangi;
				public static Pixmap keMenu;
			}		
		}
	}
	
	public static class Knowledge {
		public static Pixmap frame;
		public static class Content {
			public static Pixmap info1;
			public static Pixmap info2;
			public static Pixmap info3;
			public static Pixmap info4;
			public static Pixmap info5;
			public static Pixmap info6;
			public static Pixmap info7;
			public static Pixmap info8;
			public static Pixmap info9;
			public static Pixmap info10;			
		}
	}
	
	public static class Notification {
		public static Pixmap exitGame;
		public static Pixmap resetGame;
	}
	
	public static class Audio {
		public static class BGM {
			public static Music menuGeneral;
			public static Music menuMudah;
			public static Music menuLumayan;
			public static Music menuSusah;
		}
		public static class Effect {
			public static Sound shootTombak;
			public static Sound shootJaring;
			public static Sound hitFishTombak;
			public static Sound hitFishJaring;
			public static Sound collideFish;
			public static Sound click;
		}
		
		public static void dispose() {
			BGM.menuGeneral.dispose();
			BGM.menuMudah.dispose();
			BGM.menuLumayan.dispose();
			BGM.menuSusah.dispose();		
			Effect.shootTombak.dispose();
			Effect.shootJaring.dispose();
			Effect.hitFishTombak.dispose();
			Effect.hitFishJaring.dispose();
			Effect.collideFish.dispose();
			Effect.click.dispose();
		}
		public static void reload(com.arkodestudio.framework.Audio audio) {
			String sparator = File.separator;
			Assets.Audio.BGM.menuGeneral = audio.createMusic("audio" + sparator +"bgm_general.ogg");
			Assets.Audio.BGM.menuMudah = audio.createMusic("audio" + sparator +"bgm_mudah.ogg");
			Assets.Audio.BGM.menuLumayan = audio.createMusic("audio" + sparator +"bgm_lumayan.ogg");
			Assets.Audio.BGM.menuSusah = audio.createMusic("audio" + sparator +"bgm_susah.ogg");
			Assets.Audio.Effect.click = audio.createSound("audio" + sparator + "effect_button_click.ogg");
			Assets.Audio.Effect.collideFish = audio.createSound("audio" + sparator + "effect_collide_fish_penyelam.ogg");
			Assets.Audio.Effect.hitFishJaring = audio.createSound("audio" + sparator + "effect_collide_fish_jaring.ogg");
			Assets.Audio.Effect.hitFishTombak = audio.createSound("audio" + sparator + "effect_collide_fish_tombak.ogg");
			Assets.Audio.Effect.shootJaring = audio.createSound("audio" + sparator + "effect_press_jaring.ogg");
			Assets.Audio.Effect.shootTombak = audio.createSound("audio" + sparator + "effect_press_tombak.ogg");
		}
	}
}
