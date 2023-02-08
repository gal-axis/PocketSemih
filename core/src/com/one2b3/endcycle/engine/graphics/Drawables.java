package com.one2b3.endcycle.engine.graphics;

import com.one2b3.endcycle.engine.graphics.data.DrawableLoader;
import com.one2b3.endcycle.utils.ID;

public enum Drawables {
	Template(new ID(0, 0)),

	intro_battle_bg(new ID(0, 42)),

	Grenade(new ID(0, 54)),

	Cannonshot(new ID(0, 55)),

	sword_slash_1(new ID(0, 57)),

	battle_portal_fg(new ID(0, 60)),

	battle_portal_bg(new ID(0, 61)),

	voc_shield(new ID(0, 62)),

	voc_boulder(new ID(0, 63)),

	voc_dummy(new ID(0, 64)),

	Lance(new ID(0, 65)),

	reaction_iii(new ID(0, 70)),

	reaction_i(new ID(0, 71)),

	reaction_ip(new ID(0, 72)),

	reaction_p(new ID(0, 73)),

	reaction____(new ID(0, 74)),

	reaction_love(new ID(0, 75)),

	reaction_blush(new ID(0, 76)),

	reaction_happy(new ID(0, 77)),

	reaction_unsure(new ID(0, 78)),

	reaction_crying(new ID(0, 79)),

	reaction_cheeky(new ID(0, 80)),

	reaction_surprise(new ID(0, 81)),

	reaction_uncomfortable(new ID(0, 82)),

	reaction_annoyed(new ID(0, 83)),

	reaction_angry(new ID(0, 84)),

	reaction_laughing(new ID(0, 85)),

	reaction_tongue(new ID(0, 86)),

	reaction_smile(new ID(0, 87)),

	reaction_sad(new ID(0, 88)),

	Menu_Background(new ID(0, 110)),

	Menu_Header(new ID(0, 111)),

	Menu_Header_Back(new ID(0, 112)),

	Menu_Header_Back_Arrow(new ID(0, 113)),

	menu_sidebar(new ID(0, 114)),

	menu_button_fg(new ID(0, 115)),

	Voc_Details(new ID(0, 116)),

	Radial_Button(new ID(0, 127)),

	Icon_Library(new ID(0, 128)),

	Icon_Saved(new ID(0, 129)),

	Icon_Bag(new ID(0, 132)),

	Icon_Voc(new ID(0, 135)),

	bbg_lgbt(new ID(0, 144)),

	bbg_bat(new ID(0, 145)),

	bbg_pumpkin(new ID(0, 146)),

	Toxic_Wave(new ID(0, 151)),

	Elec_Wave(new ID(0, 152)),

	Electric_Ball(new ID(0, 153)),

	b_eye(new ID(0, 155)),

	b_juri_1(new ID(0, 156)),

	b_juri_2(new ID(0, 157)),

	Crush(new ID(0, 158)),

	Hit_Normal(new ID(0, 159)),

	Hit_Resist(new ID(0, 160)),

	Visuals_Heal_Big(new ID(0, 161)),

	Visuals_Heal_Middle(new ID(0, 162)),

	Visuals_Heal_Small(new ID(0, 163)),

	Visuals_Poison(new ID(0, 164)),

	Visuals_Buff(new ID(0, 165)),

	Visuals_Nerf(new ID(0, 166)),

	Ailment_Poison(new ID(0, 170)),

	Ailment_Regen(new ID(0, 171)),

	Ailment_Blind(new ID(0, 172)),

	Ailment_Stun(new ID(0, 173)),

	Ailment_Floating(new ID(0, 174)),

	Style_Sand(new ID(0, 175)),

	Ailment_Speed_Down(new ID(0, 176)),

	Ailment_Speed_Up(new ID(0, 177)),

	Mail_Icon(new ID(0, 192)),

	Online_Host(new ID(0, 194)),

	checkmark_checked(new ID(0, 195)),

	checkmark_unchecked(new ID(0, 196)),

	Spectator(new ID(0, 498)),

	Online_Chair(new ID(0, 499)),

	Spectator_Stand_BG(new ID(0, 500)),

	Spectator_Stand_FG(new ID(0, 501)),

	Fin_Idle(new ID(0, 400)),

	Fin_Flinch(new ID(0, 401)),

	Fin_Cast(new ID(0, 402)),

	Fin_Stun_Start(new ID(0, 403)),

	Fin_Stun_End(new ID(0, 404)),

	QT_Idle(new ID(0, 406)),

	QT_Flinch(new ID(0, 407)),

	QT_Cast(new ID(0, 408)),

	QT_Attack(new ID(0, 409)),

	QT_Start(new ID(0, 410)),

	QT_Stun_End(new ID(0, 411)),

	QT_Hatch_Open(new ID(0, 412)),

	QT_Hatch_Close(new ID(0, 413)),

	Sofa_Idle(new ID(0, 416)),

	Sofa_Flinch(new ID(0, 417)),

	Sofa_Cast(new ID(0, 418)),

	Sofa_Stun_Start(new ID(0, 419)),

	Sofa_Stun_End(new ID(0, 420)),

	Sofa_Couch(new ID(0, 421)),

	Greed_Idle(new ID(0, 424)),

	Greed_Flinch(new ID(0, 425)),

	Greed_Cast(new ID(0, 426)),

	Greed_Stun_Start(new ID(0, 427)),

	Greed_Stun_End(new ID(0, 428)),

	Bombarbara_Idle(new ID(0, 434)),

	Bombarbara_Flinch(new ID(0, 435)),

	Bombarbara_Cast(new ID(0, 436)),

	Bombarbara_Stun_Start(new ID(0, 437)),

	Bombarbara_Stun_End(new ID(0, 438)),

	AV_atar_Idle(new ID(0, 441)),

	AV_atar_Flinch(new ID(0, 442)),

	AV_atar_Cast(new ID(0, 443)),

	AV_atar_Stun_Start(new ID(0, 444)),

	AV_atar_Stun_End(new ID(0, 445)),

	Spark_Idle(new ID(0, 472)),

	Spark_Flinch(new ID(0, 473)),

	Spark_Cast(new ID(0, 474)),

	Spark_Attack(new ID(0, 475)),

	Spark_Stun_Start(new ID(0, 476)),

	Spark_Stun_End(new ID(0, 477)),

	Run_Door_Idle(new ID(0, 484)),

	Run_Door_Flinch(new ID(0, 485)),

	Loot_Box(new ID(0, 488)),

	Lightmass(new ID(0, 491)),

	Lukor_Idle(new ID(0, 493)),

	Lukor_Cast(new ID(0, 494)),

	Talking(new ID(0, 495)),

	Field_Particles(new ID(0, 496)),

	Color_Explosion(new ID(0, 497)),

	Pointer_Down(new ID(0, 531)),

	Scroller_Y(new ID(0, 533)),

	Volume_Off_0(new ID(0, 534)),

	Volume_Off_1(new ID(0, 535)),

	Volume_Off_2(new ID(0, 536)),

	Volume_Off_3(new ID(0, 537)),

	Volume_Off_4(new ID(0, 538)),

	Volume_Off_5(new ID(0, 539)),

	Volume_Off_6(new ID(0, 540)),

	Volume_Off_7(new ID(0, 541)),

	Volume_On_0(new ID(0, 542)),

	Volume_On_1(new ID(0, 543)),

	Volume_On_2(new ID(0, 544)),

	Volume_On_3(new ID(0, 545)),

	Volume_On_4(new ID(0, 546)),

	Volume_On_5(new ID(0, 547)),

	Volume_On_6(new ID(0, 548)),

	Volume_On_7(new ID(0, 549)),

	Slider_0(new ID(0, 550)),

	Slider_1(new ID(0, 551)),

	Slider_2(new ID(0, 552)),

	Slider_Knob(new ID(0, 553)),

	Slider_Segment(new ID(0, 554)),

	Stat_Bar(new ID(0, 572)),

	Battle_HP_BG(new ID(0, 573)),

	Crush_Bar(new ID(0, 574)),

	Cursor(new ID(0, 582)),

	Indicator(new ID(0, 583)),

	Style_BG(new ID(0, 992)),

	Voc_Container(new ID(0, 995)),

	_12B3_Logo(new ID(0, 999)),

	Normal_Tile_Neutral(new ID(0, 1032)),

	Connection_0(new ID(0, 1015)),

	Connection_1(new ID(0, 1016)),

	Connection_2(new ID(0, 1017)),

	Connection_3(new ID(0, 1018)),

	Cracked_Tile_Neutral(new ID(0, 1033)),

	Broken_Tile_Neutral(new ID(0, 1034)),

	Hole_Tile(new ID(0, 1036)),

	Frame_Enemy(new ID(0, 1038)),

	Frame_Ally(new ID(0, 1039)),

	Tile_Mark_Ally(new ID(0, 1040)),

	Tile_Mark_Enemy(new ID(0, 1041)),

	Frame_Neutral(new ID(0, 1042)),

	BattleField_Select_BG(new ID(0, 1043)),

	BattleField_Select_FG(new ID(0, 1044)),

	Mini_Panel_Ally(new ID(0, 1045)),

	Mini_Panel_Neutral(new ID(0, 1046)),

	Mini_Panel_Enemy(new ID(0, 1047)),

	Locked_On(new ID(0, 1090)),

	Role_Container_Strike(new ID(0, 1094)),

	Role_Container_Guard(new ID(0, 1095)),

	Role_Container_Effect(new ID(0, 1096)),

	Role_Container_Exe(new ID(0, 1097)),

	VOC_Level_BG_1(new ID(0, 1099)),

	VOC_Level_BG_2(new ID(0, 1100)),

	VOC_Level_BG_3(new ID(0, 1101)),

	VOC_Level_BG_4(new ID(0, 1102)),

	VOC_Level_BG_5(new ID(0, 1103)),

	Element_Water(new ID(0, 1104)),

	Element_Elec(new ID(0, 1105)),

	Element_Fire(new ID(0, 1106)),

	Element_Normal(new ID(0, 1107)),

	Element_Nature(new ID(0, 1108)),

	Element_Special(new ID(0, 1109)),

	Role_Strike(new ID(0, 1112)),

	Role_Style(new ID(0, 1113)),

	Role_Effect(new ID(0, 1114)),

	Role_Protect(new ID(0, 1115)),

	Role_Control(new ID(0, 1117)),

	button_9patch(new ID(0, 1119)),

	message_9patch(new ID(0, 1120)),

	voxel_pack_9patch(new ID(0, 1121)),

	description_9patch(new ID(0, 1122)),

	Voc_Icons(new ID(0, 1124)),

	Scroller_X(new ID(0, 1125)),

	battle_death_0(new ID(0, 1138)),

	battle_death_1(new ID(0, 1139)),

	Normal_Tile_Ally(new ID(0, 1140)),

	Normal_Tile_Enemy(new ID(0, 1141)),

	b_barrier(new ID(0, 1142)),

	Ice_Spear(new ID(0, 1143)),

	Ice_Chunk_0(new ID(0, 1144)),

	Ice_Chunk_1(new ID(0, 1145)),

	Ice_Chunk_2(new ID(0, 1146)),

	Vinepull_Vine(new ID(0, 1147)),

	New_Logo(new ID(0, 1148)),

	Cracked_Tile_Enemy(new ID(0, 1149)),

	Cracked_Tile_Ally(new ID(0, 1150)),

	Broken_Tile_Enemy(new ID(0, 1153)),

	Broken_Tile_Ally(new ID(0, 1154)),

	Move_Tile_Up(new ID(0, 1156)),

	Move_Tile_Down(new ID(0, 1157)),

	Move_Tile_Left(new ID(0, 1158)),

	Move_Tile_Right(new ID(0, 1159)),

	b_mosquito(new ID(0, 1160)),

	b_mosquito_wings_1(new ID(0, 1161)),

	b_mosquito_wings_2(new ID(0, 1162)),

	b_energy_shot(new ID(0, 1163)),

	Libgdx_Logo(new ID(0, 1164)),

	Ice_Tile(new ID(0, 1166)),

	Metal_Tile(new ID(0, 1167)),

	b_mine(new ID(0, 1168)),

	Bomb_1(new ID(0, 1169)),

	b_seed(new ID(0, 1170)),

	b_speaker(new ID(0, 1172)),

	b_note_1(new ID(0, 1173)),

	b_note_2(new ID(0, 1174)),

	intro_battle_bg_stars_1(new ID(0, 1175)),

	intro_battle_bg_stars_2(new ID(0, 1176)),

	Style_Push(new ID(0, 1177)),

	Syle_Pull(new ID(0, 1178)),

	Ice_Cube(new ID(0, 1185)),

	Explosion(new ID(0, 1186)),

	arrow_up(new ID(0, 1187)),

	arrow_down(new ID(0, 1188)),

	arrow_left(new ID(0, 1189)),

	arrow_right(new ID(0, 1190)),

	Boulder_Chunk_0(new ID(0, 1191)),

	Boulder_Chunk_1(new ID(0, 1192)),

	Boulder_Chunk_2(new ID(0, 1193)),

	Boulder_Chunk_3(new ID(0, 1194)),

	Boulder_Chunk_4(new ID(0, 1195)),

	Slime_Chunk_0(new ID(0, 1196)),

	Hit_Blocked(new ID(0, 1197)),

	reaction_grin(new ID(0, 1199)),

	reaction_cat(new ID(0, 1200)),

	reaction_why(new ID(0, 1201)),

	reaction_sbahj(new ID(0, 1202)),

	icon_controller(new ID(0, 1203)),

	icon_disconnected(new ID(0, 1204)),

	bbg_cane(new ID(0, 1211)),

	bbg_xmashat(new ID(0, 1212)),

	bbg_xmaslights(new ID(0, 1213)),

	bbg_xmaslightred(new ID(0, 1214)),

	bbg_xmaslightblue(new ID(0, 1215)),

	bbg_xmaslightyellow(new ID(0, 1216)),

	bbg_xmaslightgreen(new ID(0, 1217)),

	bbg_xmas(new ID(0, 1218)),

	b_chipper_shot(new ID(0, 1219)),

	b_spreader_shot(new ID(0, 1220)),

	Icon_Shop(new ID(0, 1221)),

	Style_SuperArmor(new ID(0, 1222)),

	Style_Eject(new ID(0, 1223)),

	Humidity_Startup(new ID(0, 1225)),

	b_leaf(new ID(0, 1226)),

	Rozu_Idle(new ID(0, 1228)),

	Tri_Idle(new ID(0, 1229)),

	Tri_Cast(new ID(0, 1230)),

	Tri_Flinch(new ID(0, 1231)),

	character_guest(new ID(0, 1232)),

	Style_Up(new ID(0, 1233)),

	Style_Down(new ID(0, 1234)),

	Style_Cyber(new ID(0, 1235)),

	Tri_Move_Forward(new ID(0, 1236)),

	Tri_Move_Back(new ID(0, 1237)),

	b_meteor(new ID(0, 1238)),

	b_leech_vines(new ID(0, 1239)),

	Visuals_Speed_Up(new ID(0, 1241)),

	Meteor_Chunk_0(new ID(0, 1242)),

	Meteor_Chunk_1(new ID(0, 1243)),

	Meteor_Chunk_2(new ID(0, 1244)),

	Meteor_Chunk_3(new ID(0, 1245)),

	Meteor_Chunk_4(new ID(0, 1246)),

	Meteor_Chunk_5(new ID(0, 1247)),

	Rozu_Cast(new ID(0, 1248)),

	Rozu_Attack(new ID(0, 1249)),

	Rozu_Flinch(new ID(0, 1250)),

	Rozu_Stun(new ID(0, 1251)),

	b_charging(new ID(0, 1253)),

	b_charged(new ID(0, 1254)),

	b_loglaunch(new ID(0, 1255)),

	b_mantle(new ID(0, 1257)),

	Visuals_Star_1(new ID(0, 1258)),

	Visuals_Star_2(new ID(0, 1259)),

	b_laser_startup(new ID(0, 1260)),

	b_laser(new ID(0, 1262)),

	b_laser_over(new ID(0, 1263)),

	b_laser_startup_end(new ID(0, 1264)),

	b_laser_end(new ID(0, 1265)),

	b_jet(new ID(0, 1266)),

	b_clock(new ID(0, 1267)),

	Style_Rush_2(new ID(0, 1268)),

	Style_Rush_3(new ID(0, 1269)),

	Style_Storm(new ID(0, 1270)),

	Style_Escape(new ID(0, 1271)),

	Fist_Animation(new ID(0, 1274)),

	Dropdown_9P(new ID(0, 1275)),

	checkmark_nobg(new ID(0, 1276)),

	simple_9patch(new ID(0, 1277)),

	Frame_Enemy_Clock(new ID(0, 1278)),

	Frame_Ally_Clock(new ID(0, 1279)),

	Frame_Neutral_Clock(new ID(0, 1280)),

	BG_Clock_A(new ID(0, 1281)),

	BG_Clock_B(new ID(0, 1282)),

	Projectile_Fireball(new ID(0, 8)),

	panel_9patch(new ID(0, 9)),

	b_pickup(new ID(0, 10)),

	desert_bg(new ID(0, 11)),

	Frame_Ally_Desert(new ID(0, 12)),

	Frame_Neutral_Desert(new ID(0, 13)),

	Frame_Enemy_Desert(new ID(0, 14)),

	Water_Bomb(new ID(0, 15)),

	Water_Bomb_Particle_1(new ID(0, 16)),

	Water_Bomb_Particle_2(new ID(0, 17)),

	Text_Cursor(new ID(0, 18)),

	Grass_Tile(new ID(0, 19)),

	Lava_Tile(new ID(0, 20)),

	white_9patch(new ID(0, 22)),

	VS_Logo(new ID(0, 23)),

	big_circle(new ID(0, 24)),

	small_circle(new ID(0, 25)),

	Del_Idle(new ID(0, 26)),

	Del_Flinch(new ID(0, 27)),

	Del_Cast(new ID(0, 28)),

	Del_Attack(new ID(0, 29)),

	Del_Flinch_Start(new ID(0, 30)),

	Del_Flinch_End(new ID(0, 31)),

	reaction_cool(new ID(0, 32)),

	reaction_owo(new ID(0, 33)),

	node_star(new ID(0, 99)),

	Particle_Fire(new ID(0, 100)),

	Particle_Spark(new ID(0, 101)),

	Particle_Steam(new ID(0, 102)),

	small_message_9patch(new ID(0, 103)),

	Azure_Header(new ID(0, 147)),

	Azure_Header_Back(new ID(0, 148)),

	Azure_Button_9P(new ID(0, 149)),

	Azure_Big_Message_9P(new ID(0, 150)),

	Azure_Title_Container_9P(new ID(0, 168)),

	Azure_Small_Message_9P(new ID(0, 169)),

	Azure_Container_9P(new ID(0, 191)),

	menu_button_bg(new ID(0, 200)),

	ASGrunt_Idle(new ID(0, 202)),

	Style_Infuse(new ID(0, 209)),

	hp(new ID(0, 210)),

	Style_Lava(new ID(0, 211)),

	Style_Counter(new ID(0, 212)),

	Style_Turn(new ID(0, 213)),

	Style_Move_Back(new ID(0, 214)),

	Training_Dummy(new ID(0, 215)),

	Fire_Gate(new ID(0, 216)),

	ASGrunt_Attack(new ID(0, 217)),

	ASGrunt_Flinch(new ID(0, 218)),

	ASGrunt_Stun_Start(new ID(0, 219)),

	ASGrunt_Stun_End(new ID(0, 220)),

	ASGrunt_Cast(new ID(0, 221)),

	ASGrunt_FullCast(new ID(0, 222)),

	ASGrunt_Idle_2(new ID(0, 223)),

	ASGrunt_Attack_2(new ID(0, 224)),

	ASGrunt_Flinch_2(new ID(0, 225)),

	ASGrunt_Stun_Start_2(new ID(0, 226)),

	ASGrunt_Stun_End_2(new ID(0, 227)),

	ASGrunt_Cast_2(new ID(0, 228)),

	star_big(new ID(0, 229)),

	ASGrunt_FullCast_2(new ID(0, 230)),

	Tri_Header(new ID(0, 231)),

	Tri_Header_Back(new ID(0, 232)),

	Tri_Button_9P(new ID(0, 233)),

	Tri_Title_Container_9P(new ID(0, 235)),

	Tri_Container_9P(new ID(0, 236)),

	Tri_Small_Message_9P(new ID(0, 237)),

	Tri_Panel_9P(new ID(0, 238)),

	Tri_Background(new ID(0, 239)),

	Data_Server(new ID(0, 246)),

	Galaxy_Stars(new ID(0, 248)),

	Galaxy_Star(new ID(0, 249)),

	Galaxy_Planet_1(new ID(0, 250)),

	Galaxy_Planet_2(new ID(0, 251)),

	Galaxy_Nebula(new ID(0, 252)),

	Energy_BG_Left(new ID(0, 247)),

	Energy_Beam_S(new ID(0, 253)),

	Energy_Beam_M(new ID(0, 254)),

	Energy_Beam_L(new ID(0, 255)),

	Energy_BG_Right(new ID(0, 256)),

	Snow_BG_1(new ID(0, 258)),

	Snow_BG_2(new ID(0, 259)),

	Snow_BG_3(new ID(0, 260)),

	Snow_BG_Ice(new ID(0, 261)),

	reaction_think(new ID(0, 262)),

	New_Desert_BG_Sun(new ID(0, 263)),

	New_Desert_BG_Dark_Sand(new ID(0, 264)),

	New_Desert_BG_Sand(new ID(0, 265)),

	New_Forest_BG_1(new ID(0, 266)),

	New_Forest_BG_2(new ID(0, 267)),

	New_Forest_BG_Trees_1(new ID(0, 268)),

	New_Forest_BG_Trees_2(new ID(0, 269)),

	New_Forest_BG_Trees_4(new ID(0, 270)),

	New_Forest_BG_Trees_3(new ID(0, 271)),

	Menu_Header_Bar(new ID(0, 272)),

	Azure_Header_Bar(new ID(0, 273)),

	Tri_Header_Bar(new ID(0, 274)),

	Move_Left(new ID(0, 277)),

	Move_Right(new ID(0, 278)),

	Move_Up(new ID(0, 279)),

	Move_Down(new ID(0, 280)),

	Move_Turn(new ID(0, 281)),

	Move_Left_Up(new ID(0, 282)),

	Move_Left_Down(new ID(0, 283)),

	Move_Right_Up(new ID(0, 284)),

	Move_Right_Down(new ID(0, 285)),

	Button_Pause(new ID(0, 286)),

	Move_Target(new ID(0, 287)),

	Button_Chat(new ID(0, 117)),

	Kill_Tile(new ID(0, 118)),

	Ailment_Berserk(new ID(0, 124)),

	Ailment_Meak(new ID(0, 125)),

	Style_Flip(new ID(0, 126)),

	Style_Heal(new ID(0, 178)),

	Ailment_Freeze(new ID(0, 179)),

	Cloud(new ID(0, 180)),

	Blinded(new ID(0, 181)),

	Frozen(new ID(0, 182)),

	Frame_Enemy_Sand(new ID(0, 183)),

	Frame_Neutral_Sand(new ID(0, 184)),

	Frame_Ally_Sand(new ID(0, 185)),

	Frame_Ally_Space(new ID(0, 186)),

	Frame_Neutral_Space(new ID(0, 187)),

	Frame_Enemy_Space(new ID(0, 188)),

	Frame_Enemy_Everod(new ID(0, 189)),

	Frame_Neutral_Everod(new ID(0, 190)),

	Frame_Ally_Everod(new ID(0, 197)),

	Frame_Ally_Forest(new ID(0, 199)),

	Frame_Neutral_Forest(new ID(0, 288)),

	Frame_Enemy_Forest(new ID(0, 289)),

	City_BG_1(new ID(0, 290)),

	City_BG_2(new ID(0, 291)),

	City_BG_3(new ID(0, 292)),

	City_BG_4(new ID(0, 293)),

	City_BG_5(new ID(0, 294)),

	City_BG_6(new ID(0, 295)),

	Counter(new ID(0, 296)),

	Weak(new ID(0, 298)),

	Hit_Weak(new ID(0, 299)),

	Crimson_Header(new ID(0, 300)),

	Crimson_Header_Back(new ID(0, 301)),

	Crimson_Button_9P(new ID(0, 302)),

	Crimson_Big_Message_9P(new ID(0, 303)),

	Crimson_Small_Message_9P(new ID(0, 304)),

	Crimson_Container_9P(new ID(0, 305)),

	Crimson_Header_Bar(new ID(0, 306)),

	Crimson_Title_Container_9P(new ID(0, 308)),

	Crimson_Background(new ID(0, 309)),

	Azure_Background(new ID(0, 310)),

	Greed_Move(new ID(0, 311)),

	Companion(new ID(0, 313)),

	Lite_Lock(new ID(0, 314)),

	Style_Yoink(new ID(0, 315)),

	Style_Crack(new ID(0, 316)),

	Style_Metal(new ID(0, 317)),

	Style_Ice(new ID(0, 318)),

	Style_Grass(new ID(0, 319)),

	Flash(new ID(0, 321)),

	Coffee_Bean(new ID(0, 322)),

	Spike_Idle(new ID(0, 323)),

	Spike_Activate(new ID(0, 324)),

	Visuals_Speed_Down(new ID(0, 325)),

	Frame_Enemy_City(new ID(0, 326)),

	Frame_Neutral_City(new ID(0, 327)),

	Frame_Ally_City(new ID(0, 328)),

	Regen_Pickup(new ID(0, 329)),

	Button_Replay_Pause(new ID(0, 330)),

	Button_Replay_Play(new ID(0, 331)),

	Button_Replay_Frame_Forward(new ID(0, 332)),

	Button_Replay_Frame_Backward(new ID(0, 333)),

	Button_Replay_End(new ID(0, 334)),

	Button_Replay_Reset(new ID(0, 335)),

	Ice_Cube_Chunk_0(new ID(0, 336)),

	Ice_Cube_Chunk_1(new ID(0, 337)),

	Ice_Cube_Chunk_2(new ID(0, 338)),

	Ice_Cube_Chunk_3(new ID(0, 339)),

	Ice_Cube_Chunk_4(new ID(0, 340)),

	Halloween_Menu(new ID(0, 341)),

	Ice_Cave_0(new ID(0, 342)),

	Ice_Cave_1(new ID(0, 343)),

	reaction_sweat(new ID(0, 344)),

	reaction_f(new ID(0, 345)),

	Voc_New(new ID(0, 346)),

	Online_Disconnected(new ID(0, 1)),

	icon_training(new ID(0, 2)),

	white(new ID(0, 3)),

	g9patch(new ID(0, 4)),

	Cress_Idle(new ID(0, 5)),

	Cress_Attack(new ID(0, 6)),

	Cress_Cast(new ID(0, 7)),

	Cress_Flinch(new ID(0, 43)),

	Cress_Stun_Start(new ID(0, 44)),

	Cress_Stun_End(new ID(0, 45)),

	Cress_Cast_Followup(new ID(0, 46)),

	Bomb_2(new ID(0, 47)),

	Resistor_Idle(new ID(0, 48)),

	Cress_Move(new ID(0, 49)),

	Style_Anti(new ID(0, 50)),

	Style_Ninja(new ID(0, 51)),

	Style_Mindgame(new ID(0, 813)),

	Resistor_Hit(new ID(0, 92)),

	Punk_00_Idle(new ID(0, 94)),

	Punk_00_Attack(new ID(0, 95)),

	Punk_00_Cast(new ID(0, 96)),

	Punk_00_Flinch(new ID(0, 97)),

	Punk_00_Stun_Start(new ID(0, 98)),

	Punk_00_Stun_End(new ID(0, 104)),

	Punk_01_Idle(new ID(0, 105)),

	Punk_01_Attack(new ID(0, 108)),

	Punk_01_Cast(new ID(0, 109)),

	Punk_01_Flinch(new ID(0, 130)),

	Punk_01_Stun_Start(new ID(0, 131)),

	Punk_01_Stun_End(new ID(0, 133)),

	Punk_10_Idle(new ID(0, 134)),

	Punk_10_Attack(new ID(0, 136)),

	Punk_10_Cast(new ID(0, 137)),

	Punk_10_Flinch(new ID(0, 138)),

	Punk_10_Stun_Start(new ID(0, 139)),

	Punk_10_Stun_End(new ID(0, 140)),

	Punk_11_Idle(new ID(0, 141)),

	Punk_11_Attack(new ID(0, 142)),

	Punk_11_Cast(new ID(0, 143)),

	Punk_11_Flinch(new ID(0, 154)),

	Punk_11_Stun_Start(new ID(0, 193)),

	Punk_11_Stun_End(new ID(0, 198)),

	Transition_Rogue(new ID(0, 21)),

	Counter_Loop(new ID(0, 356)),

	Cherry_Idle(new ID(0, 357)),

	Crush_Loop(new ID(0, 120)),

	Cherry_Cast(new ID(0, 358)),

	Cherry_Attack(new ID(0, 121)),

	Cherry_Flinch(new ID(0, 257)),

	Cherry_Stun_Start(new ID(0, 347)),

	Cherry_Stun_End(new ID(0, 348)),

	Cherry_Recover(new ID(0, 349)),

	Cherry_Melt(new ID(0, 350)),

	Cherry_Melt_Idle(new ID(0, 351)),

	Cherry_Melt_Cast(new ID(0, 352)),

	Cherry_Melt_Flinch(new ID(0, 353)),

	Cherry_Melt_Stun_Start(new ID(0, 354)),

	Cherry_Melt_Stun_End(new ID(0, 355)),

	Mirror_Idle(new ID(0, 119)),

	Mirror_Left(new ID(0, 359)),

	Mirror_Right(new ID(0, 360)),

	Style_Invis(new ID(0, 361)),

	Style_Guard(new ID(0, 362)),

	Thunder_Bolt(new ID(0, 363)),

	Whistle(new ID(0, 364)),

	Frame_Enemy_Destroyed(new ID(0, 365)),

	Frame_Neutral_Destroyed(new ID(0, 366)),

	Frame_Ally_Destroyed(new ID(0, 367)),

	Dark_BG_1(new ID(0, 368)),

	Dark_BG_2(new ID(0, 369)),

	Dark_BG_3(new ID(0, 370)),

	Dark_BG_4(new ID(0, 371)),

	Yoink(new ID(0, 372)),

	Controller_Silhouette(new ID(0, 373)),

	Slime_Chunk_1(new ID(0, 374)),

	Slime_Chunk_2(new ID(0, 375)),

	Slime_Chunk_3(new ID(0, 376)),

	Sofa_Move(new ID(0, 377)),

	Style_Grab(new ID(0, 378)),

	Panel_Energy(new ID(0, 379)),

	Minimap_Panel(new ID(0, 380)),

	Minimap_Marker(new ID(0, 381)),

	Minimap_Death(new ID(0, 382)),

	Teleporter(new ID(0, 383)),

	Ice_Shot(new ID(0, 384)),

	scroller_9patch(new ID(0, 385)),

	Bombarbara_Attack(new ID(0, 386)),

	Battle_Crush_BG(new ID(0, 387)),

	Battle_Crush_FG(new ID(0, 388)),

	Battle_HP_FG(new ID(0, 389)),

	Rozu_Card(new ID(0, 390)),

	Cherry_Card(new ID(0, 391)),

	Bomby_Card(new ID(0, 392)),

	Cannon_Card(new ID(0, 393)),

	Cress_Card(new ID(0, 394)),

	Del_Card(new ID(0, 395)),

	Greed_Card(new ID(0, 396)),

	Agent_Card(new ID(0, 397)),

	Punk_Card(new ID(0, 398)),

	Tri_Wing_Card(new ID(0, 399)),

	Fin_Card(new ID(0, 405)),

	Spark_Card(new ID(0, 414)),

	Sofa_Card(new ID(0, 415)),

	AV_atar_Card(new ID(0, 422)),

	Icon_Close_Transparent(new ID(0, 433)),

	Icon_Close_Filled(new ID(0, 439)),

	Underline(new ID(0, 440)),

	Icon_Directory(new ID(0, 446)),

	Icon_File(new ID(0, 447)),

	Icon_Revo(new ID(0, 448)),

	Icon_Open(new ID(0, 449)),

	Icon_Close(new ID(0, 450)),

	Icon_Doc_Abstract(new ID(0, 451)),

	Icon_Doc_Class(new ID(0, 452)),

	Icon_Doc_Final(new ID(0, 453)),

	Icon_Doc_Interface(new ID(0, 454)),

	Icon_Doc_Enum(new ID(0, 455)),

	Icon_Warning(new ID(0, 456)),

	Modern_Button_9P(new ID(0, 457)),

	Modern_Big_Message_9P(new ID(0, 458)),

	Modern_Title_Container_9P(new ID(0, 459)),

	Modern_Container_9P(new ID(0, 460)),

	Modern_Small_Message_9P(new ID(0, 461)),

	Modern_Header(new ID(0, 462)),

	Modern_Header_Back(new ID(0, 463)),

	Modern_Header_Bar(new ID(0, 464)),

	Modern_Background(new ID(0, 465)),

	Jeremy_Card(new ID(0, 201)),

	Jeremy_Idle(new ID(0, 234)),

	Jeremy_Attack(new ID(0, 307)),

	Jeremy_Cast(new ID(0, 312)),

	Jeremy_Flinch(new ID(0, 466)),

	Jeremy_Stun_Start(new ID(0, 468)),

	Jeremy_Stun_End(new ID(0, 469)),

	Icon_Doc_Static(new ID(0, 470)),

	Icon_Eraser(new ID(0, 471)),

	Revo_Logo(new ID(0, 478)),

	Barricade_Barrier(new ID(0, 93)),

	Diagonal_Up_Sword_Slash(new ID(0, 106)),

	Potato_Bomb(new ID(0, 107)),

	Ink_Land(new ID(0, 122)),

	Ink_Initial(new ID(0, 275)),

	Popcorn(new ID(0, 123)),

	Kernel(new ID(0, 276)),

	Cold_Drop(new ID(0, 479)),

	Cold_Drop_Crash(new ID(0, 480)),

	Fossil_Card(new ID(0, 481)),

	Fossil_Idle(new ID(0, 482)),

	Fossil_Cast(new ID(0, 483)),

	Fossil_Attack(new ID(0, 486)),

	Fossil_Flinch(new ID(0, 487)),

	Fossil_Stun_Start(new ID(0, 489)),

	Fossil_Stun_End(new ID(0, 490)),

	Icon_Character(new ID(0, 34)),

	Diagonal_Down_Sword_Slash(new ID(0, 35)),

	Spark_Cut_In(new ID(0, 38)),

	Battle_HUD(new ID(0, 39)),

	Battle_Ultra_Bar(new ID(0, 40)),

	Battle_Ultra_Start(new ID(0, 41)),

	Battle_Ultra_End(new ID(0, 53)),

	Rozu_Cut_In(new ID(0, 56)),

	Fin_Cut_In(new ID(0, 58)),

	Lunar_Background(new ID(0, 59)),

	Lunar_Button_9P(new ID(0, 66)),

	Lunar_Container_Title(new ID(0, 67)),

	Lunar_Container(new ID(0, 69)),

	Lunar_Panel(new ID(0, 89)),

	Lunar_Header_Bar(new ID(0, 90)),

	Lunar_Header(new ID(0, 91)),

	Lunar_Header_Back(new ID(0, 203)),

	Bella_Idle(new ID(0, 204)),

	Bella_Attack(new ID(0, 205)),

	Bella_Cast(new ID(0, 206)),

	Bella_Flinch(new ID(0, 207)),

	Bella_Stun_Start(new ID(0, 208)),

	Bella_Stun_End(new ID(0, 240)),

	Bella_Card(new ID(0, 241)),

	Bella_Cut_In(new ID(0, 242)),

	Battle_Ultra_Energy(new ID(0, 243)),

	Battle_Ultra_Energy_2(new ID(0, 36)),

	Jeremy_Strum_Start(new ID(0, 37)),

	Jeremy_Strum(new ID(0, 244)),

	Jeremy_Strum_End(new ID(0, 245)),

	Note_Guitar_1(new ID(0, 423)),

	Jeremy_Cut_In(new ID(0, 429)),

	Cannon_Cut_In(new ID(0, 430)),

	Tri_Laser_Start_Left(new ID(0, 431)),

	Tri_Laser_Start(new ID(0, 432)),

	Tri_Laser_Start_Right(new ID(0, 492)),

	Tri_Laser_Left(new ID(0, 502)),

	Tri_Laser(new ID(0, 503)),

	Tri_Laser_Right(new ID(0, 504)),

	Tri_Laser_End_Left(new ID(0, 505)),

	Tri_Laser_End(new ID(0, 506)),

	Tri_Laser_End_Right(new ID(0, 507)),

	Lunar_Lock_On(new ID(0, 508)),

	Fin_Attack(new ID(0, 509)),

	Ice_Cream_Bomb_Choco(new ID(0, 510)),

	Ice_Cream_Bomb_Vanilla(new ID(0, 511)),

	Ice_Cream_Bomb_Strawbebby(new ID(0, 512)),

	Tankitty_Idle(new ID(0, 513)),

	Tankitty_Move(new ID(0, 514)),

	Tankitty_Attack(new ID(0, 515)),

	Tankitty_Cast(new ID(0, 516)),

	Tankitty_Flinch(new ID(0, 517)),

	Tankitty_Stun_Start(new ID(0, 518)),

	Tankitty_Stun_End(new ID(0, 519)),

	Tankitty_Card(new ID(0, 520)),

	Water_Crusher(new ID(0, 521)),

	Electric_Ball_Short(new ID(0, 522)),

	Beach_BG_Sand(new ID(0, 523)),

	Beach_BG_Sea_0(new ID(0, 524)),

	Beach_BG_Sun(new ID(0, 525)),

	Beach_BG_Clouds_1(new ID(0, 526)),

	Beach_BG_Clouds_2(new ID(0, 527)),

	Beach_BG_Clouds_0(new ID(0, 528)),

	Beach_BG_Sea_1(new ID(0, 529)),

	Frame_Enemy_Beach(new ID(0, 530)),

	Frame_Neutral_Beach(new ID(0, 532)),

	Frame_Ally_Beach(new ID(0, 555)),

	Beach_BG_Waves(new ID(0, 556)),

	Networld_BG(new ID(0, 557)),

	Frame_Enemy_Networld(new ID(0, 558)),

	Frame_Neutral_Networld(new ID(0, 559)),

	Frame_Ally_Networld(new ID(0, 560)),

	Map_Anikka(new ID(0, 561)),

	Map_Pointer(new ID(0, 562)),

	Map_Networld(new ID(0, 563)),

	Icon_Azure(new ID(0, 564)),

	Icon_Crimson(new ID(0, 565)),

	Icon_Cross(new ID(0, 566)),

	Icon_Noise(new ID(0, 567)),

	Icon_Adventure_Shop(new ID(0, 568)),

	Icon_Unknown(new ID(0, 569)),

	Data_Spark(new ID(0, 570)),

	Icon_Treasure(new ID(0, 571)),

	AV_atar_Attack(new ID(0, 575)),

	AV_atar_Caped(new ID(0, 576)),

	Panel_Snow(new ID(0, 577)),

	Panel_Sand(new ID(0, 578)),

	Bomb_3(new ID(0, 579)),

	Bomb_4(new ID(0, 580)),

	Panel_Net(new ID(0, 581)),

	Voc_Pickup_Spin(new ID(0, 584)),

	Warning_Lunar_Stripe(new ID(0, 585)),

	Warning_Lunar_Frame(new ID(0, 586)),

	Warning_Lunar_Warning(new ID(0, 587)),

	Icon_Upgrade(new ID(0, 588)),

	Icon_Silhouette(new ID(0, 589)),

	Icon_Code(new ID(0, 590)),

	Icon_Consumable(new ID(0, 591)),

	Panel_Grass(new ID(0, 592)),

	Panel_City(new ID(0, 593)),

	Panel_Destroyed(new ID(0, 594)),

	Panel_Space(new ID(0, 595)),

	Panel_Sand_2(new ID(0, 596)),

	BG_Phantom_Sky(new ID(0, 597)),

	BG_Phantom_FG(new ID(0, 598)),

	Panel_Lunar(new ID(0, 599)),

	Map_Unknown(new ID(0, 600)),

	Panel_Clock(new ID(0, 601)),

	Icon_Boost(new ID(0, 602)),

	Icon_Close_All_Transparent(new ID(0, 603)),

	Icon_Close_All_Filled(new ID(0, 604)),

	Icon_Item_HP(new ID(0, 605)),

	Icon_Item_Str(new ID(0, 606)),

	Icon_Item_Spd(new ID(0, 607)),

	Icon_Item_Res(new ID(0, 608)),

	Particles_Murk(new ID(0, 609)),

	Particle_Fire_Loop(new ID(0, 610)),

	Adventure_Fragment_Full(new ID(0, 611)),

	Adventure_Fragment_1(new ID(0, 612)),

	Adventure_Fragment_2(new ID(0, 613)),

	Adventure_Fragment_3(new ID(0, 614)),

	BG_Tower(new ID(0, 616)),

	Panel_Tower(new ID(0, 617)),

	Icon_Boss(new ID(0, 618)),

	Desert_Cave_Back(new ID(0, 619)),

	Desert_Cave_Front(new ID(0, 620)),

	Desert_Cave_Gate(new ID(0, 621)),

	Desert_Cave_Gate_Open(new ID(0, 622)),

	Data_Server_Ancient(new ID(0, 623)),

	Ice_Cave_Back(new ID(0, 624)),

	Ice_Cave_Front(new ID(0, 625)),

	Particles_Fire_Rising(new ID(0, 626)),

	Weather_Snow_1(new ID(0, 627)),

	Weather_Snow_2(new ID(0, 628)),

	Weather_Snow_3(new ID(0, 629)),

	Weather_Desert(new ID(0, 630)),

	Icon_Item_Azure(new ID(0, 631)),

	Help_Call(new ID(0, 632)),

	Rozu_Move(new ID(0, 633)),

	Cherry_Cut_In(new ID(0, 634)),

	Bombarbara_Cut_In(new ID(0, 635)),

	Fossil_Fuel_Cut_In(new ID(0, 636)),

	Greed_Cut_In(new ID(0, 637)),

	Tri_Wing_Cut_In(new ID(0, 638)),

	Sofa_Cut_In(new ID(0, 639)),

	Tankitty_Cut_In(new ID(0, 640)),

	Cress_Cut_In(new ID(0, 641)),

	Del_Cut_In(new ID(0, 642)),

	AV_atar_Cut_In(new ID(0, 643)),

	Punk_Cut_In(new ID(0, 644)),

	Agent_Cut_In(new ID(0, 645)),

	Capture_Orb_Start(new ID(0, 646)),

	Capture_Orb_Loop(new ID(0, 647)),

	Soundwave_Particle_Explode(new ID(0, 648)),

	Soundwave_Particle_Startup(new ID(0, 649)),

	Minimap_Panel_Small(new ID(0, 650)),

	ASGrunt_Move_2(new ID(0, 652)),

	Earth_Boulder(new ID(0, 615)),

	Style_Copy(new ID(0, 651)),

	Hacking_Gate(new ID(0, 653)),

	Bombarbara_Jump(new ID(0, 654)),

	Bombarbara_Land(new ID(0, 655)),

	LifeHook_Chain(new ID(0, 656)),

	Punk_00_Move(new ID(0, 657)),

	Punk_01_Move(new ID(0, 658)),

	Punk_10_Move(new ID(0, 659)),

	Punk_11_Move(new ID(0, 660)),

	Godius_Body(new ID(0, 661)),

	Godius_Left_Arm(new ID(0, 662)),

	Godius_Right_Arm(new ID(0, 663)),

	Godius_Body_Spawn_1(new ID(0, 664)),

	Godius_Body_Spawn_2(new ID(0, 665)),

	Godius_Left_Spawn(new ID(0, 666)),

	Godius_Right_Spawn(new ID(0, 667)),

	Godius_Sickle(new ID(0, 668)),

	Godius_Right_Hammer(new ID(0, 669)),

	Godius_Hammer_Spin(new ID(0, 670)),

	Godius_Hammer_Impact(new ID(0, 671)),

	Godius_Right_Hammer_Revert(new ID(0, 672)),

	ASGrunt_Move_1(new ID(0, 673)),

	QT_Move(new ID(0, 674)),

	Spark_Move(new ID(0, 675)),

	Fin_Move(new ID(0, 676)),

	Bella_Move(new ID(0, 677)),

	Spark_Win(new ID(0, 678)),

	Fin_Win(new ID(0, 679)),

	Del_Win(new ID(0, 680)),

	Jeremy_Win(new ID(0, 681)),

	Punk_Win(new ID(0, 682)),

	Agent_Win(new ID(0, 683)),

	Cress_Win(new ID(0, 684)),

	Rozu_Win(new ID(0, 685)),

	Cherry_Win(new ID(0, 686)),

	Bombarbara_Win(new ID(0, 687)),

	QT_Win(new ID(0, 688)),

	Fossil_Fuel_Win(new ID(0, 689)),

	Greed_Win(new ID(0, 690)),

	Sofa_Win(new ID(0, 691)),

	Tankitty_Win(new ID(0, 692)),

	Del_Move(new ID(0, 693)),

	Godius_Left_Sickle(new ID(0, 696)),

	Godius_Right_Sickle_Revert(new ID(0, 695)),

	Godius_Left_Sickle_Revert(new ID(0, 697)),

	Godius_Body_Cast(new ID(0, 698)),

	Bella_Win(new ID(0, 699)),

	Tankitty_Sleep(new ID(0, 700)),

	Gateway_Portal(new ID(0, 701)),

	Tri_Move(new ID(0, 702)),

	Fossil_Fuel_Move(new ID(0, 703)),

	Jeremy_Move(new ID(0, 704)),

	Cherry_Move(new ID(0, 705)),

	Bombarbara_Move(new ID(0, 706)),

	AV_atar_Move(new ID(0, 707)),

	Tile_Mark_Neutral(new ID(0, 708)),

	Ailment_Flip(new ID(0, 709)),

	Online_Status(new ID(0, 710)),

	AV_atar_Win(new ID(0, 711)),

	Azure_Spawn(new ID(0, 712)),

	Icon_Deathmatch_Life(new ID(0, 713)),

	Icon_Deathmatch_Life_Player(new ID(0, 714)),

	Icon_Deathmatch_Life_Border(new ID(0, 715)),

	Icon_Trophy(new ID(0, 716)),

	Lite_Lock_Mini(new ID(0, 717)),

	Hit_Marker(new ID(0, 718)),

	Mobile_Button_9P(new ID(0, 719)),

	Panel_Run_Down(new ID(0, 720)),

	Panel_Run_Right(new ID(0, 721)),

	Panel_Run_Left(new ID(0, 722)),

	Panel_Run_Up(new ID(0, 723)),

	Farmadile_Idle(new ID(0, 724)),

	Farmadile_Flinch(new ID(0, 725)),

	Farmadile_Stun_Start(new ID(0, 726)),

	Farmadile_Stun_End(new ID(0, 727)),

	Farmadile_Cast(new ID(0, 728)),

	Farmadile_Attack(new ID(0, 729)),

	Farmadile_Card(new ID(0, 730)),

	Spike_Deactivate(new ID(0, 731)),

	Panel_Run_Down_Closed(new ID(0, 732)),

	Panel_Run_Up_Closed(new ID(0, 733)),

	Panel_Run_Right_Closed(new ID(0, 734)),

	Panel_Run_Left_Closed(new ID(0, 735)),

	Panel_Run_Down_Open(new ID(0, 736)),

	Panel_Run_Up_Open(new ID(0, 737)),

	Panel_Run_Right_Open(new ID(0, 738)),

	Panel_Run_Left_Open(new ID(0, 739)),

	gicon(new ID(0, 740)),

	Particle_Push_Right(new ID(0, 741)),

	Particle_Push_Left(new ID(0, 742)),

	Particle_Push_Down(new ID(0, 743)),

	Particle_Push_Up(new ID(0, 744)),

	Particle_Poison(new ID(0, 745)),

	Particle_Freeze(new ID(0, 746)),

	Particle_Eject(new ID(0, 747)),

	Particle_Turn(new ID(0, 748)),

	Particle_Invert(new ID(0, 749)),

	Particle_Flash(new ID(0, 750)),

	Particle_Flinch(new ID(0, 751)),

	Particle_Panel(new ID(0, 752)),

	Particle_Anti(new ID(0, 753)),

	Particle_Copy(new ID(0, 754)),

	Farmadile_Win(new ID(0, 755)),

	Farmadile_Cut_In(new ID(0, 756)),

	Blinded_Reverse(new ID(0, 757)),

	Dialogue_9P(new ID(0, 758)),

	Panel_Fist(new ID(0, 759)),

	Mobile_Axis(new ID(0, 760)),

	Mobile_Axis_Touch(new ID(0, 761)),

	Sofa_Furniture(new ID(0, 762)),

	Farmadile_Walk(new ID(0, 763)),

	Battle_HUD_Big(new ID(0, 764)),

	Battle_HUD_Line(new ID(0, 765)),

	Customizable(new ID(0, 68)),

	Role_Container_Border(new ID(0, 766)),

	Role_Container_None(new ID(0, 767)),

	Rolling_Meteor(new ID(0, 768)),

	Rolling_Fireball(new ID(0, 769)),

	Tree(new ID(0, 770)),

	Confetti(new ID(0, 771)),

	Fist_Still(new ID(0, 772)),

	Straw_Hat(new ID(0, 773)),

	Crystal(new ID(0, 774)),

	Pentris(new ID(0, 775)),

	Grand_Dad(new ID(0, 776)),

	Sun_Symbol(new ID(0, 777)),

	Battle_Symbol(new ID(0, 778)),

	Energy_Hit(new ID(0, 779)),

	Energy_Trail(new ID(0, 780)),

	Yven_Idle(new ID(0, 781)),

	Yven_Cast(new ID(0, 782)),

	Yven_Attack(new ID(0, 783)),

	Yven_Move(new ID(0, 784)),

	Yven_Flinch(new ID(0, 785)),

	Yven_Stun_Start(new ID(0, 786)),

	Yven_Stun_End(new ID(0, 787)),

	Yven_Card(new ID(0, 788)),

	Yven_Win(new ID(0, 789)),

	Yven_Cut_In(new ID(0, 790)),

	Shard_Counter(new ID(0, 791)),

	Crystal_Strike(new ID(0, 792)),

	Crystal_Effect(new ID(0, 793)),

	Crystal_Protect(new ID(0, 794)),

	Crystal_Control(new ID(0, 795)),

	Capture_Orb_Reverse(new ID(0, 796)),

	AV_atar_Charge_Intro(new ID(0, 797)),

	AV_atar_Charge_Loop(new ID(0, 798)),

	AV_atar_Charge_Stop(new ID(0, 799)),

	Energy_Charge(new ID(0, 800)),

	Style_Loot(new ID(0, 801)),

	Style_Poison(new ID(0, 802)),

	Style_Regen(new ID(0, 803)),

	Style_Berserk(new ID(0, 804)),

	Style_Meak(new ID(0, 805)),

	Style_Haste(new ID(0, 806)),

	Style_Brake(new ID(0, 807)),

	Style_Blind(new ID(0, 808)),

	Style_Float(new ID(0, 809)),

	Style_Freeze(new ID(0, 810)),

	Style_Invert(new ID(0, 811)),

	Mindgame_Lock(new ID(0, 812)),

	Training_Dummy_Stun_Start(new ID(0, 167)),

	Training_Dummy_Stun_End(new ID(0, 297)),

	Sisters_Cut_In_1(new ID(0, 320)),

	Sisters_Cut_In_2(new ID(0, 814)),

	Cress_Hurt(new ID(0, 815)),

	Del_Sad(new ID(0, 816)),

	Del_Bed(new ID(0, 817)),

	Cress_Patient(new ID(0, 818)),

	Del_Apartment(new ID(0, 819)),

	Crystal_Rainbow(new ID(0, 820)),

	Everod_BG_1(new ID(0, 821)),

	Everod_BG_2(new ID(0, 822)),

	Everod_BG_3(new ID(0, 823)),

	Pickup_Bad(new ID(0, 824)),

	Loot_Box_Open(new ID(0, 825)),

	Midius_BG_N1(new ID(0, 826)),

	Midius_BG_N2(new ID(0, 827)),

	Icon_Downloaded(new ID(0, 828)),

	Icon_Settings(new ID(0, 829)),

	Style_Tempo(new ID(0, 830)),

	Style_Impact(new ID(0, 831)),

	Icon_Miniboss(new ID(0, 832)),

	Icon_Barrier(new ID(0, 833)),

	Icon_Bomb(new ID(0, 834)),

	Icon_Heal(new ID(0, 835)),

	Icon_Crush(new ID(0, 836)),

	Icon_Chrono(new ID(0, 467)),

	Icon_Icognito(new ID(0, 837)),

	Icon_Hard_to_Miss(new ID(0, 839)),

	Icon_Panels(new ID(0, 840)),

	Icon_Weak(new ID(0, 841)),

	Icon_Pack_Change(new ID(0, 842)),

	Icon_Music(new ID(0, 843)),

	Flu_Top(new ID(0, 844)),

	Flu_Connect(new ID(0, 845)),

	VOC_Bubble(new ID(0, 846)),

	Ink_White_Initial(new ID(0, 847)),

	Ink_White_Land(new ID(0, 848)),

	Ink_White_Mid(new ID(0, 849)),

	Corn(new ID(0, 850)),

	Corn_Stuck(new ID(0, 851)),

	Shutter(new ID(0, 852)),

	Tuger_Right(new ID(0, 853)),

	Tuger_Down(new ID(0, 854)),

	Tuger_Up(new ID(0, 855)),

	Tuger_Right_Down(new ID(0, 856)),

	Tuger_Right_Up(new ID(0, 857)),

	TugerHook_Right(new ID(0, 858)),

	TugerHook_Up(new ID(0, 859)),

	TugerHook_Down(new ID(0, 860)),

	Tsuntsun(new ID(0, 861)),

	Tsuntsun_Angry(new ID(0, 862)),

	Sandstorm(new ID(0, 863)),

	Tuger_Up_Right(new ID(0, 864)),

	Tuger_Up_Left(new ID(0, 865)),

	Tuger_Down_Right(new ID(0, 866)),

	Tuger_Down_Left(new ID(0, 867)),

	Tuger_Left(new ID(0, 868)),

	Tuger_Left_Down(new ID(0, 869)),

	Tuger_Left_Up(new ID(0, 870)),

	Logo_Panel_9P(new ID(0, 871)),

	Kali_Normal(new ID(0, 872)),

	Kali_Angry(new ID(0, 873)),

	Popcorn_Machine(new ID(0, 874)),

	Magnetize_Particle(new ID(0, 875)),

	AntiMagnetize_Particle(new ID(0, 876)),

	Style_Magnet(new ID(0, 877)),

	Style_Repel(new ID(0, 878));

	private final ID id;

	private Drawables(ID id) {
		this.id = id;
	}

	public ID getId() {
		return this.id;
	}

	public Drawable get() {
		return DrawableLoader.get().createDrawable(this.id);
	}

	public DrawableImage getImage() {
		return DrawableLoader.get().getImage(this.id);
	}

	public static Drawable[] array(Drawables... drawables) {
		Drawable[] drawableArray = new Drawable[drawables.length];
		for (int i = 0; i < drawables.length; i++) {
			drawableArray[i] = drawables[i].get();
		}
		return drawableArray;
	}
}
