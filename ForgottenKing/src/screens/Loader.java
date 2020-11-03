package screens;

import javafx.scene.image.Image;
import tools.ImageCrop;

public final class Loader {
	public static Image screenBorder = new Image(Screen.class.getResourceAsStream("resources/screenBorder.png"));
	public static Image screenSeparator = new Image(Screen.class.getResourceAsStream("resources/screen_separator.png"));
	public static Image targetBox = new Image(Screen.class.getResourceAsStream("resources/targetBox.png"));
	public static Image selectionBoxFull = new Image(Screen.class.getResourceAsStream("resources/selection.png"));
	public static Image yellowSelection = ImageCrop.cropImage(selectionBoxFull, 0, 0, 32, 32);
	public static Image redSelection = ImageCrop.cropImage(selectionBoxFull, 32, 0, 32, 32);
	private static Image startScreenMenuBoxFull = new Image(Screen.class.getResourceAsStream("resources/start-screen-menu-box.png"));
	public static Image startScreenMenuBox = ImageCrop.cropImage(startScreenMenuBoxFull, 0, 0, 600, 78);
	public static Image startScreenMenuBoxGold = ImageCrop.cropImage(startScreenMenuBoxFull, 0, 78, 600, 78);
	public static Image startScreenFull = new Image(Screen.class.getResourceAsStream("resources/full-screens/start-screen.png"));
	
	public static Image multi_item_icon = new Image(Screen.class.getResourceAsStream("resources/multi_item_icon.png"));
	public static Image armedEnemyIcon = new Image(Screen.class.getResourceAsStream("resources/armed_enemy.png"));
	
	public static Image healthBarIconFull = new Image(Screen.class.getResourceAsStream("resources/health_bar_icon_full.png"));
	public static Image healthBarFull = ImageCrop.cropImage(healthBarIconFull, 0, 0, 32, 8);
	public static Image healthBarThreeQuarter = ImageCrop.cropImage(healthBarIconFull, 32, 0, 32, 8);
	public static Image healthBarHalf = ImageCrop.cropImage(healthBarIconFull, 64, 0, 32, 8);
	public static Image healthBarQuarter = ImageCrop.cropImage(healthBarIconFull, 96, 0, 32, 8);
	public static Image poisonedHealthBarFull = ImageCrop.cropImage(healthBarIconFull, 0, 32, 32, 8);
	public static Image poisonedHealthBarThreeQuarter = ImageCrop.cropImage(healthBarIconFull, 32, 32, 32, 8);
	public static Image poisonedHealthBarHalf = ImageCrop.cropImage(healthBarIconFull, 64, 32, 32, 8);
	public static Image poisonedHealthBarQuarter = ImageCrop.cropImage(healthBarIconFull, 96, 32, 32, 8);
	public static Image allyHealthBarFull = ImageCrop.cropImage(healthBarIconFull, 0, 16, 32, 8);
	public static Image allyHealthBarThreeQuarter = ImageCrop.cropImage(healthBarIconFull, 32, 16, 32, 8);
	public static Image allyHealthBarHalf = ImageCrop.cropImage(healthBarIconFull, 64, 16, 32, 8);
	public static Image allyHealthBarQuarter = ImageCrop.cropImage(healthBarIconFull, 96, 16, 32, 8);

	public static Image attributesFull = new Image(Screen.class.getResourceAsStream("resources/attributes.png"));
	public static Image strengthIcon = ImageCrop.cropImage(attributesFull,0,0,32,32);
	public static Image dexterityIcon = ImageCrop.cropImage(attributesFull,32,0,32,32);
	public static Image intelligenceIcon = ImageCrop.cropImage(attributesFull,64,0,32,32);
	public static Image statsFull = new Image(Screen.class.getResourceAsStream("resources/stats.png"));
	public static Image toughnessIcon = ImageCrop.cropImage(statsFull, 0, 32, 32, 32);
	public static Image brawnIcon = ImageCrop.cropImage(statsFull, 0, 64, 32, 32);
	public static Image agilityIcon = ImageCrop.cropImage(statsFull, 32, 32, 32, 32);
	public static Image accuracyIcon = ImageCrop.cropImage(statsFull, 32, 64, 32, 32);
	public static Image willIcon = ImageCrop.cropImage(statsFull, 64, 32, 32, 32);
	public static Image spellcastingIcon = ImageCrop.cropImage(statsFull, 64, 64, 32, 32);
	
	public static Image playerUIFull = new Image(Screen.class.getResourceAsStream("resources/player_ui.png"));
	private static Image equipmentBoxFull = new Image(Screen.class.getResourceAsStream("resources/equipment_box.png"));
	public static Image equipmentBox = ImageCrop.cropImage(equipmentBoxFull, 0, 0, 240, 48);
	public static Image equipmentBoxBlue = ImageCrop.cropImage(equipmentBoxFull, 0, 48, 240, 48);
	public static Image effectBox = new Image(Screen.class.getResourceAsStream("resources/effect_box.png"));
	public static Image levelUpBox = new Image(Screen.class.getResourceAsStream("resources/level_up_menu.png"));
	public static Image levelUpOptionFull = new Image(Screen.class.getResourceAsStream("resources/level_up_option_full.png"));
	public static Image levelUpOptionHalf = new Image(Screen.class.getResourceAsStream("resources/level_up_option_half.png"));
	
	private static Image arrowsFull = new Image(Screen.class.getResourceAsStream("resources/arrows_full.png"));
	public static Image arrowUp = ImageCrop.cropImage(arrowsFull, 0, 0, 32, 32);
	public static Image arrowRight = ImageCrop.cropImage(arrowsFull, 0, 32, 32, 32);
	public static Image arrowDown = ImageCrop.cropImage(arrowsFull, 0, 64, 32, 32);
	public static Image arrowLeft = ImageCrop.cropImage(arrowsFull, 0, 96, 32, 32);
	
	private static Image typeIconsFull = new Image(Screen.class.getResourceAsStream("resources/type_full.png"));
	public static Image fireIcon = ImageCrop.cropImage(typeIconsFull, 0, 0, 32, 32);
	public static Image coldIcon = ImageCrop.cropImage(typeIconsFull, 32, 0, 32, 32);
	public static Image airIcon = ImageCrop.cropImage(typeIconsFull, 64, 0, 32, 32);
	public static Image poisonIcon = ImageCrop.cropImage(typeIconsFull, 0, 32, 32, 32);
	public static Image lightIcon = ImageCrop.cropImage(typeIconsFull, 32, 32, 32, 32);
	public static Image darkIcon = ImageCrop.cropImage(typeIconsFull, 64, 32, 32, 32);
	private static Image largeTypeIconsFull = new Image(Screen.class.getResourceAsStream("resources/type_full_large.png"));
	public static Image largeFireIcon = ImageCrop.cropImage(largeTypeIconsFull, 0, 0, 64, 64);
	public static Image largeColdIcon = ImageCrop.cropImage(largeTypeIconsFull, 64, 0, 64, 64);
	public static Image largeAirIcon = ImageCrop.cropImage(largeTypeIconsFull, 128, 0, 64, 64);
	public static Image largePoisonIcon = ImageCrop.cropImage(largeTypeIconsFull, 0, 64, 64, 64);
	public static Image largeLightIcon = ImageCrop.cropImage(largeTypeIconsFull, 64, 64, 64, 64);
	public static Image largeDarkIcon = ImageCrop.cropImage(largeTypeIconsFull, 128, 64, 64, 64);
	
	public static Image plusMinusIcon = new Image(Screen.class.getResourceAsStream("resources/plus_minus_full.png"));
	public static Image plusIcon = ImageCrop.cropImage(plusMinusIcon, 0, 0, 32, 32);
	public static Image minusIcon = ImageCrop.cropImage(plusMinusIcon, 32, 0, 32, 32);
	public static Image magicTypeBox = new Image(Screen.class.getResourceAsStream("resources/magic_type_box.png"));
	public static Image spellPointsBox = new Image(Screen.class.getResourceAsStream("resources/spell_points_box.png"));
	
	public static Image characterSelectionBackground = new Image(Screen.class.getResourceAsStream("resources/full-screens/character_selection_screen.png"));
	private static Image characterSelectionFull = new Image(Screen.class.getResourceAsStream("resources/character_selection_full.png"));
	public static Image characterSelectionBox = ImageCrop.cropImage(characterSelectionFull, 0, 0, 104, 104);
	public static Image characterSelectionUpArrow = ImageCrop.cropImage(characterSelectionFull, 104, 0, 104, 52);
	public static Image characterSelectionDownArrow = ImageCrop.cropImage(characterSelectionFull, 104, 52, 104, 52);
	private static Image tagBoxFull = new Image(Screen.class.getResourceAsStream("resources/tag_box_full.png"));
	public static Image perkBoxSmall = ImageCrop.cropImage(tagBoxFull, 0, 0, 530, 122);
	public static Image perkBox = ImageCrop.cropImage(tagBoxFull, 0, 122, 730, 122);
	public static Image perkBoxSelection = ImageCrop.cropImage(tagBoxFull, 0, 244, 730, 122);
	public static Image perkBoxBlue = ImageCrop.cropImage(tagBoxFull, 0, 366, 730, 122);
	public static Image itemTagBox = ImageCrop.cropImage(tagBoxFull, 0, 488, 730, 122);
	
	private static Image inventoryBoxFull = new Image(Screen.class.getResourceAsStream("resources/inventory_box_full.png"));
	public static Image inventoryBox = ImageCrop.cropImage(inventoryBoxFull, 0, 0, 892, 64);
	public static Image inventoryBoxBlue = ImageCrop.cropImage(inventoryBoxFull, 0, 64, 892, 64);
	public static Image inventoryBoxYellow = ImageCrop.cropImage(inventoryBoxFull, 0, 128, 892, 64);
	public static Image inventoryBoxGreen = ImageCrop.cropImage(inventoryBoxFull, 0, 192, 892, 64);
	public static Image inventoryBoxDarkGreen = ImageCrop.cropImage(inventoryBoxFull, 0, 256, 892, 64);
	
	//public static Image statsScreenStatBox = new Image(Screen.class.getResourceAsStream("resources/stats-screen-stat-box.png"));
	public static Image statsScreenFull = new Image(Screen.class.getResourceAsStream("resources/full-screens/stats-screen-full.png"));
	/**
	 * TAGS
	 */
	private static Image perksFull = new Image(Screen.class.getResourceAsStream("resources/perks_full.png"));
	public static Image quickLearnerIcon = ImageCrop.cropImage(perksFull, 0, 0, 32, 32);
	public static Image fastenArmorIcon = ImageCrop.cropImage(perksFull, 32, 0, 32, 32);
	public static Image improvedCriticalIcon = ImageCrop.cropImage(perksFull, 64, 0, 32, 32);
	public static Image deadlyCriticalIcon = ImageCrop.cropImage(perksFull, 96, 0, 32, 32);
	public static Image strongArrowsIcon = ImageCrop.cropImage(perksFull, 128, 0, 32, 32);
	public static Image lightArmorProficiencyIcon = ImageCrop.cropImage(perksFull, 160, 0, 32, 32);
	public static Image mediumArmorSkillIcon = ImageCrop.cropImage(perksFull, 192, 0, 32, 32);
	public static Image heavyArmorSkillIcon = ImageCrop.cropImage(perksFull, 224, 0, 32, 32);
	public static Image improvedCleaveIcon = ImageCrop.cropImage(perksFull, 0, 32, 32, 32);
	public static Image acolytesManaIcon = ImageCrop.cropImage(perksFull, 32, 32, 32, 32);
	
	private static Image tagsFull = new Image(Screen.class.getResourceAsStream("resources/creature_tags_full.png"));
	public static Image spellcasterIcon = ImageCrop.cropImage(tagsFull, 0, 0, 32, 32);
	public static Image erraticIcon = ImageCrop.cropImage(tagsFull, 32, 0, 32, 32);
	public static Image venomousIcon = ImageCrop.cropImage(tagsFull, 64, 0, 32, 32);
	public static Image undeadIcon = ImageCrop.cropImage(tagsFull, 96, 0, 32, 32);
	public static Image allyIcon = ImageCrop.cropImage(tagsFull, 128, 0, 32, 32);
}
