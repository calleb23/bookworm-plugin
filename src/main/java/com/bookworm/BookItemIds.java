package com.bookworm;

import java.util.HashMap;
import java.util.Map;

/**
 * Maps OSRS item IDs → Bookworm book IDs (from bookData.js).
 *
 * Sources: OSRS Wiki (oldschool.runescape.wiki) – item IDs verified via
 * action=query API and individual page fetches, February 2026.
 *
 * Notes:
 *   - Some books have multiple item IDs (charged/uncharged variants, page
 *     count variants, etc.) – all are mapped to the same Bookworm book ID.
 *   - Books 201-220 are zone-duplicates of existing books (same item, different
 *     unlock condition) – they share the same item IDs as their primary entry.
 *   - The plugin works fine with a partial mapping; unregistered items are ignored.
 */
public class BookItemIds
{
	public static final Map<Integer, Integer> ITEM_TO_BOOK  = new HashMap<>();
	/** Bookworm book ID → human-readable name (for panel + chat notifications). */
	public static final Map<Integer, String>  BOOK_NAMES    = new HashMap<>();
	/** Total number of distinct Bookworm book IDs (for panel progress display). */
	public static int TOTAL_BOOKS = 0;

	static
	{
		// ── ID 1: Security book ───────────────────────────────────────────────
		ITEM_TO_BOOK.put(9003, 1);

		// ── ID 2: Goblin symbol book ──────────────────────────────────────────
		ITEM_TO_BOOK.put(5009, 2);

		// ── ID 3: Master Scroll Book ──────────────────────────────────────────
		// zones=[] in bookData.js — not tracked, skip

		// ── ID 4: Stronghold notes ────────────────────────────────────────────
		ITEM_TO_BOOK.put(9004, 4);

		// ── ID 5: Collection log ──────────────────────────────────────────────
		ITEM_TO_BOOK.put(22711, 5);

		// ── ID 6: Pie recipe book ─────────────────────────────────────────────
		ITEM_TO_BOOK.put(7162, 6);

		// ── ID 7: Goblin book ─────────────────────────────────────────────────
		ITEM_TO_BOOK.put(10999, 7);

		// ── ID 8: Book (Shield of Arrav) ──────────────────────────────────────
		ITEM_TO_BOOK.put(757, 8);

		// ── ID 9: Newspaper ───────────────────────────────────────────────────
		ITEM_TO_BOOK.put(11171, 9);

		// ── ID 10: Chronicle ─────────────────────────────────────────────────
		ITEM_TO_BOOK.put(13660, 10);

		// ── ID 11: Book on chickens ───────────────────────────────────────────
		ITEM_TO_BOOK.put(7464, 11);

		// ── ID 12: Strange book ───────────────────────────────────────────────
		ITEM_TO_BOOK.put(5507, 12);

		// ── ID 13: Atlax's diary ──────────────────────────────────────────────
		ITEM_TO_BOOK.put(26876, 13);

		// ── ID 14: Construction guide ─────────────────────────────────────────
		// Also used by zone-duplicates 201-205 (same item, different zones)
		ITEM_TO_BOOK.put(8463, 14);

		// ── ID 15: Book on chemicals ──────────────────────────────────────────
		ITEM_TO_BOOK.put(711, 15);

		// ── ID 16: The desert trout - ship's log ──────────────────────────────
		ITEM_TO_BOOK.put(25567, 16);

		// ── ID 17: Spirit anglers research notes ──────────────────────────────
		ITEM_TO_BOOK.put(25569, 17);

		// ── ID 18: Tome of water (charged and empty variants) ─────────────────
		ITEM_TO_BOOK.put(25574, 18);  // charged
		ITEM_TO_BOOK.put(25576, 18);  // empty

		// ── ID 19: Ballad ─────────────────────────────────────────────────────
		ITEM_TO_BOOK.put(6793, 19);

		// ── ID 20: Translation ────────────────────────────────────────────────
		ITEM_TO_BOOK.put(4655, 20);

		// ── ID 21: Embalming manual ───────────────────────────────────────────
		ITEM_TO_BOOK.put(4686, 21);

		// ── ID 22: Entomologist's diary ───────────────────────────────────────
		// Also used by zone-duplicates 213-218 (same item, different zones)
		ITEM_TO_BOOK.put(11904, 22);

		// ── ID 23: Arena book ─────────────────────────────────────────────────
		ITEM_TO_BOOK.put(6891, 23);

		// ── ID 24: Mage's book ────────────────────────────────────────────────
		ITEM_TO_BOOK.put(6889, 24);

		// ── ID 25: Bird book ──────────────────────────────────────────────────
		ITEM_TO_BOOK.put(10173, 25);

		// ── ID 26: Feathered journal ──────────────────────────────────────────
		ITEM_TO_BOOK.put(10179, 26);

		// ── ID 27: Book of folklore ───────────────────────────────────────────
		ITEM_TO_BOOK.put(5508, 27);

		// ── ID 28: Journal (Making History) ──────────────────────────────────
		ITEM_TO_BOOK.put(6755, 28);

		// ── ID 29: Instruction manual ─────────────────────────────────────────
		ITEM_TO_BOOK.put(5, 29);

		// ── ID 30: Sacraments of ent folk ────────────────────────────────────
		ITEM_TO_BOOK.put(31016, 30);

		// ── ID 31: On migration patterns ─────────────────────────────────────
		ITEM_TO_BOOK.put(31077, 31);

		// ── ID 32: Moonrise wines ─────────────────────────────────────────────
		ITEM_TO_BOOK.put(30035, 32);

		// ── ID 33: Gianne's cook book ─────────────────────────────────────────
		ITEM_TO_BOOK.put(2167, 33);

		// ── ID 34: A handwritten book ─────────────────────────────────────────
		ITEM_TO_BOOK.put(9627, 34);

		// ── ID 35: Cocktail guide ─────────────────────────────────────────────
		ITEM_TO_BOOK.put(2023, 35);

		// ── ID 36: Yewnock's notes ────────────────────────────────────────────
		ITEM_TO_BOOK.put(28579, 36);

		// ── ID 37: Book of spyology ───────────────────────────────────────────
		ITEM_TO_BOOK.put(19515, 37);

		// ── ID 38: The hueycoatl (book) ───────────────────────────────────────
		ITEM_TO_BOOK.put(30123, 38);

		// ── ID 39: Tome of earth (charged and empty variants) ─────────────────
		ITEM_TO_BOOK.put(30064, 39);  // charged
		ITEM_TO_BOOK.put(30066, 39);  // empty

		// ── ID 40: Guide book ─────────────────────────────────────────────────
		ITEM_TO_BOOK.put(1856, 40);

		// ── ID 41: Astronomy book ─────────────────────────────────────────────
		ITEM_TO_BOOK.put(600, 41);

		// ── ID 42: Glassblowing book ──────────────────────────────────────────
		ITEM_TO_BOOK.put(11656, 42);

		// ── ID 43: Battered book ──────────────────────────────────────────────
		ITEM_TO_BOOK.put(2886, 43);

		// ── ID 44: Slashed book ───────────────────────────────────────────────
		ITEM_TO_BOOK.put(9715, 44);

		// ── ID 45: Beaten book ────────────────────────────────────────────────
		ITEM_TO_BOOK.put(9717, 45);

		// ── ID 46: God book ───────────────────────────────────────────────────
		// Six god books — all map to the same Bookworm ID
		ITEM_TO_BOOK.put(3840, 46);   // Holy book
		ITEM_TO_BOOK.put(3842, 46);   // Unholy book
		ITEM_TO_BOOK.put(3844, 46);   // Book of balance
		ITEM_TO_BOOK.put(12610, 46);  // Book of law
		ITEM_TO_BOOK.put(12608, 46);  // Book of war
		ITEM_TO_BOOK.put(12612, 46);  // Book of darkness

		// ── ID 47: Manual ─────────────────────────────────────────────────────
		ITEM_TO_BOOK.put(3847, 47);

		// ── ID 48: Journal (Horror from the Deep) ─────────────────────────────
		ITEM_TO_BOOK.put(3845, 48);

		// ── ID 49: Diary (Horror from the Deep) ───────────────────────────────
		ITEM_TO_BOOK.put(3846, 49);

		// ── ID 50: My notes ───────────────────────────────────────────────────
		ITEM_TO_BOOK.put(11339, 50);

		// ── ID 51: Queen help book ────────────────────────────────────────────
		ITEM_TO_BOOK.put(10562, 51);

		// ── ID 52: Book on baxtorian ──────────────────────────────────────────
		ITEM_TO_BOOK.put(292, 52);

		// ── ID 53: Diary (Witch's House) ──────────────────────────────────────
		ITEM_TO_BOOK.put(2408, 53);

		// ── ID 54: Auburn valley ecological reports ───────────────────────────
		ITEM_TO_BOOK.put(31079, 54);

		// ── ID 55-61: Gielinor's flora series ────────────────────────────────
		ITEM_TO_BOOK.put(23015, 55);  // allotments
		ITEM_TO_BOOK.put(23021, 56);  // fruit
		ITEM_TO_BOOK.put(23011, 57);  // bushes
		ITEM_TO_BOOK.put(23009, 58);  // flowers
		ITEM_TO_BOOK.put(23019, 59);  // trees
		ITEM_TO_BOOK.put(23017, 60);  // herbs
		ITEM_TO_BOOK.put(23013, 61);  // hops

		// ── ID 62: Neilan's journal ───────────────────────────────────────────
		ITEM_TO_BOOK.put(25152, 62);

		// ── ID 63: Soul wars guide ────────────────────────────────────────────
		ITEM_TO_BOOK.put(25197, 63);

		// ── ID 64: Game book ──────────────────────────────────────────────────
		ITEM_TO_BOOK.put(7681, 64);

		// ── ID 65: Abyssal book ───────────────────────────────────────────────
		ITEM_TO_BOOK.put(5520, 65);

		// ── ID 66: Dagon'hai history ──────────────────────────────────────────
		ITEM_TO_BOOK.put(11001, 66);

		// ── ID 67: Sin'keth's diary ───────────────────────────────────────────
		ITEM_TO_BOOK.put(11002, 67);

		// ── ID 68: Book of knowledge ──────────────────────────────────────────
		ITEM_TO_BOOK.put(11640, 68);

		// ── ID 69: Tome of fire (charged and empty variants) ──────────────────
		ITEM_TO_BOOK.put(20714, 69);  // charged
		ITEM_TO_BOOK.put(20716, 69);  // empty

		// ── ID 70: Smelly journal ─────────────────────────────────────────────
		ITEM_TO_BOOK.put(20798, 70);

		// ── ID 71: Kasonde's journal ──────────────────────────────────────────
		ITEM_TO_BOOK.put(27511, 71);

		// ── ID 72: Varlamore envoy ────────────────────────────────────────────
		ITEM_TO_BOOK.put(21756, 72);

		// ── ID 73: Byrne's coronation speech ─────────────────────────────────
		ITEM_TO_BOOK.put(13531, 73);

		// ── IDs 221-234: Arceuus Library books ───────────────────────────────
		ITEM_TO_BOOK.put(13526, 221);  // Eathram & rada extract
		ITEM_TO_BOOK.put(13528, 222);  // Hosidius letter
		ITEM_TO_BOOK.put(13532, 223);  // Ideology of darkness
		ITEM_TO_BOOK.put(13527, 224);  // Killing of a king
		ITEM_TO_BOOK.put(13524, 225);  // Rada's census
		ITEM_TO_BOOK.put(13533, 226);  // Rada's journey
		ITEM_TO_BOOK.put(13525, 227);  // Ricktor's diary (7)
		ITEM_TO_BOOK.put(19637, 228);  // Soul journey
		ITEM_TO_BOOK.put(13537, 229);  // Transportation incantations
		ITEM_TO_BOOK.put(13534, 230);  // Transvergence theory
		ITEM_TO_BOOK.put(13536, 231);  // Treachery of royalty
		ITEM_TO_BOOK.put(13535, 232);  // Tristessa's tragedy
		ITEM_TO_BOOK.put(13530, 233);  // Twill accord
		ITEM_TO_BOOK.put(13529, 234);  // Wintertodt parable

		// ── ID 74: Dwarven lore ───────────────────────────────────────────────
		ITEM_TO_BOOK.put(4568, 74);

		// ── ID 75-77: Kourend tomes ───────────────────────────────────────────
		ITEM_TO_BOOK.put(23504, 75);  // Tome of the moon
		ITEM_TO_BOOK.put(23506, 76);  // Tome of the sun
		ITEM_TO_BOOK.put(23508, 77);  // Tome of the temple

		// ── ID 78: Kharedst's memoirs ─────────────────────────────────────────
		ITEM_TO_BOOK.put(21760, 78);

		// ── ID 79: Mount karuulm diary ────────────────────────────────────────
		ITEM_TO_BOOK.put(26154, 79);

		// ── ID 80: Book of the dead ───────────────────────────────────────────
		ITEM_TO_BOOK.put(25818, 80);

		// ── ID 81-87: Chambers of Xeric journals ─────────────────────────────
		ITEM_TO_BOOK.put(20886, 81);  // Creature keeper's journal
		ITEM_TO_BOOK.put(20899, 82);  // Dark journal
		ITEM_TO_BOOK.put(20897, 83);  // Houndmaster's diary
		ITEM_TO_BOOK.put(20895, 84);  // Vanguard judgement
		ITEM_TO_BOOK.put(20888, 85);  // Nistirio's manifesto
		ITEM_TO_BOOK.put(20893, 86);  // Transdimensional notes
		ITEM_TO_BOOK.put(20890, 87);  // Tekton's journal

		// ── ID 88: History of iban ────────────────────────────────────────────
		ITEM_TO_BOOK.put(1494, 88);

		// ── ID 89: Old journal ────────────────────────────────────────────────
		ITEM_TO_BOOK.put(1493, 89);

		// ── ID 90: Silver book (two variants) ────────────────────────────────
		ITEM_TO_BOOK.put(1802, 90);
		ITEM_TO_BOOK.put(1803, 90);

		// ── ID 91: Ballad of the basilisk ─────────────────────────────────────
		ITEM_TO_BOOK.put(24263, 91);

		// ── ID 92: Parchment (Olaf's Quest) ──────────────────────────────────
		ITEM_TO_BOOK.put(11036, 92);

		// ── ID 93: Moonclan manual ────────────────────────────────────────────
		ITEM_TO_BOOK.put(9078, 93);

		// ── ID 94: Book (Ratpits) ─────────────────────────────────────────────
		ITEM_TO_BOOK.put(6767, 94);

		// ── ID 95: Captain's Log (Port Sarim) ────────────────────────────────
		// Also used by zone-duplicates 206-212, 220 (same item, different zones)
		ITEM_TO_BOOK.put(31985, 95);  // during quest
		ITEM_TO_BOOK.put(31986, 95);  // after quest

		// ── ID 96: Glough's journal ───────────────────────────────────────────
		ITEM_TO_BOOK.put(785, 96);

		// ── ID 97: Translation book ───────────────────────────────────────────
		ITEM_TO_BOOK.put(784, 97);

		// ── ID 98: Clockwork book ─────────────────────────────────────────────
		ITEM_TO_BOOK.put(10594, 98);

		// ── ID 99: Ancient diary ──────────────────────────────────────────────
		ITEM_TO_BOOK.put(21631, 99);

		// ── ID 100: Archaeologist's diary ─────────────────────────────────────
		ITEM_TO_BOOK.put(21629, 100);

		// ── ID 101: Fossil island note book ───────────────────────────────────
		ITEM_TO_BOOK.put(21662, 101);

		// ── ID 102: Ash covered tome ──────────────────────────────────────────
		ITEM_TO_BOOK.put(21697, 102);

		// ── ID 103: Farming manual ────────────────────────────────────────────
		ITEM_TO_BOOK.put(9903, 103);

		// ── ID 104: Knight's notes (two variants) ────────────────────────────
		ITEM_TO_BOOK.put(11842, 104);
		ITEM_TO_BOOK.put(11843, 104);

		// ── ID 105: Weiss fire notes ──────────────────────────────────────────
		ITEM_TO_BOOK.put(22591, 105);

		// ── ID 106: Al kharid flyer ───────────────────────────────────────────
		ITEM_TO_BOOK.put(7922, 106);

		// ── ID 107: Varmen's notes ────────────────────────────────────────────
		ITEM_TO_BOOK.put(4616, 107);

		// ── ID 108: Demonic tome ──────────────────────────────────────────────
		ITEM_TO_BOOK.put(6749, 108);

		// ── ID 109: Tarn's diary ──────────────────────────────────────────────
		ITEM_TO_BOOK.put(10587, 109);

		// ── ID 110: Diary (Shades of Mort'ton) ───────────────────────────────
		ITEM_TO_BOOK.put(3395, 110);

		// ── ID 111: Journal (Filliman Tarlock) ────────────────────────────────
		ITEM_TO_BOOK.put(2967, 111);

		// ── ID 112: Bloody notes ──────────────────────────────────────────────
		ITEM_TO_BOOK.put(25476, 112);

		// ── ID 113: Tree wizards' journal ─────────────────────────────────────
		ITEM_TO_BOOK.put(25474, 113);

		// ── ID 114: Book of haricanto ─────────────────────────────────────────
		ITEM_TO_BOOK.put(4248, 114);

		// ── ID 115: Translation manual ────────────────────────────────────────
		ITEM_TO_BOOK.put(4249, 115);

		// ── ID 116: Crumbling tome ────────────────────────────────────────────
		ITEM_TO_BOOK.put(4707, 116);

		// ── ID 117-121: Slepe books (Sins of the Father quest) ───────────────
		ITEM_TO_BOOK.put(22420, 117);  // Bloody grimoire
		ITEM_TO_BOOK.put(22424, 118);  // Buried alive
		ITEM_TO_BOOK.put(22426, 119);  // Deed
		ITEM_TO_BOOK.put(22418, 120);  // Explosive discovery
		ITEM_TO_BOOK.put(22416, 121);  // The turncloak

		// ── ID 122-124: Burgh de Rott books ──────────────────────────────────
		ITEM_TO_BOOK.put(7634, 122);   // Histories of the hallowland
		ITEM_TO_BOOK.put(7633, 123);   // The sleeping seven
		ITEM_TO_BOOK.put(7635, 124);   // Modern day morytania

		// ── ID 125: Book o' piracy ────────────────────────────────────────────
		ITEM_TO_BOOK.put(7144, 125);

		// ── ID 126: Brewin' guide ─────────────────────────────────────────────
		ITEM_TO_BOOK.put(8989, 126);

		// ── ID 127: Castlewars manual ─────────────────────────────────────────
		ITEM_TO_BOOK.put(4055, 127);

		// ── ID 128: Necromancy book ───────────────────────────────────────────
		ITEM_TO_BOOK.put(4837, 128);

		// ── ID 129: Book of 'h.a.m' ───────────────────────────────────────────
		ITEM_TO_BOOK.put(4829, 129);

		// ── ID 130: Book of portraiture ───────────────────────────────────────
		ITEM_TO_BOOK.put(4817, 130);

		// ── ID 131: Prayer book ───────────────────────────────────────────────
		ITEM_TO_BOOK.put(10890, 131);

		// ── ID 132: Tome of experience (3) ────────────────────────────────────
		// The "(3)" suffix refers to the 3-charge variant of the Tome of experience
		ITEM_TO_BOOK.put(9656, 132);   // 3 charges remaining
		ITEM_TO_BOOK.put(9657, 132);   // 2 charges remaining
		ITEM_TO_BOOK.put(9658, 132);   // 1 charge remaining

		// ── ID 133: Haemalchemy volume 1 ──────────────────────────────────────
		ITEM_TO_BOOK.put(9652, 133);

		// ── ID 134: Old diary ─────────────────────────────────────────────────
		ITEM_TO_BOOK.put(22411, 134);

		// ── ID 135: A taste of hope ───────────────────────────────────────────
		ITEM_TO_BOOK.put(24842, 135);

		// ── ID 136: Elixir of everlasting ────────────────────────────────────
		ITEM_TO_BOOK.put(22422, 136);

		// ── ID 137: Strange hallowed tome ────────────────────────────────────
		ITEM_TO_BOOK.put(24761, 137);

		// ── ID 138-143: Theatre of Blood books ───────────────────────────────
		ITEM_TO_BOOK.put(22514, 138);  // Verzik vitur - patient record
		ITEM_TO_BOOK.put(22506, 139);  // The butcher
		ITEM_TO_BOOK.put(22512, 140);  // The wild hunt
		ITEM_TO_BOOK.put(22510, 141);  // The shadow realm
		ITEM_TO_BOOK.put(22504, 142);  // Serafina's diary
		ITEM_TO_BOOK.put(22508, 143);  // Arachnids of vampyrium

		// ── ID 144: Explorer's notes ──────────────────────────────────────────
		ITEM_TO_BOOK.put(11677, 144);

		// ── ID 145: Book on costumes ──────────────────────────────────────────
		ITEM_TO_BOOK.put(5065, 145);

		// ── ID 146: Ghrim's book ──────────────────────────────────────────────
		ITEM_TO_BOOK.put(3901, 146);

		// ── ID 147: Burnt diary (5) ───────────────────────────────────────────
		// The diary has 5 variants (1-5 pages); map all to same Bookworm ID
		ITEM_TO_BOOK.put(7961, 147);   // 1 page
		ITEM_TO_BOOK.put(7962, 147);   // 2 pages
		ITEM_TO_BOOK.put(7963, 147);   // 3 pages
		ITEM_TO_BOOK.put(7964, 147);   // 4 pages
		ITEM_TO_BOOK.put(7965, 147);   // 5 pages

		// ── ID 148: Lift Manual ───────────────────────────────────────────────
		ITEM_TO_BOOK.put(7972, 148);

		// ── ID 149: Binding book ──────────────────────────────────────────────
		ITEM_TO_BOOK.put(730, 149);

		// ── ID 150: Shaman's tome ─────────────────────────────────────────────
		ITEM_TO_BOOK.put(729, 150);

		// ── ID 151: Big book of bangs ─────────────────────────────────────────
		ITEM_TO_BOOK.put(3230, 151);

		// ── ID 152: Cadarn lineage ────────────────────────────────────────────
		ITEM_TO_BOOK.put(4209, 152);

		// ── ID 153: Crystal singing for beginners ─────────────────────────────
		ITEM_TO_BOOK.put(4313, 153);

		// ── ID 154-158: Elven books (from Grand Library / Prif quest chain) ───
		ITEM_TO_BOOK.put(6075, 154);   // Eastern discovery
		ITEM_TO_BOOK.put(6079, 155);   // The great divide
		ITEM_TO_BOOK.put(6077, 156);   // Eastern settlement
		ITEM_TO_BOOK.put(6073, 157);   // Prifddinas' history
		ITEM_TO_BOOK.put(6649, 158);   // Edern's journal

		// ── ID 159: Ohn's diary ───────────────────────────────────────────────
		ITEM_TO_BOOK.put(12935, 159);

		// ── ID 160: Herman's book ─────────────────────────────────────────────
		ITEM_TO_BOOK.put(7951, 160);

		// ── ID 161-170: Dragon Slayer II / Myths' Guild books ─────────────────
		ITEM_TO_BOOK.put(22043, 161);  // Imafore's betrayal
		ITEM_TO_BOOK.put(22033, 162);  // Aivas' diary
		ITEM_TO_BOOK.put(25702, 163);  // Ungael lab notes
		ITEM_TO_BOOK.put(22045, 164);  // Lutwidge and the moonfly
		ITEM_TO_BOOK.put(22037, 165);  // Malumac's journal
		ITEM_TO_BOOK.put(22039, 166);  // Ablenkian's escape
		ITEM_TO_BOOK.put(22049, 167);  // The weeping
		ITEM_TO_BOOK.put(22047, 168);  // Serafina (book)
		ITEM_TO_BOOK.put(25704, 169);  // Lithkren vault notes
		ITEM_TO_BOOK.put(22041, 170);  // Imcandoria's fall

		// ── ID 171-177: Tombs of Amascut books ───────────────────────────────
		ITEM_TO_BOOK.put(27304, 171);  // Apmeken's capture
		ITEM_TO_BOOK.put(27308, 172);  // Crondis' capture
		ITEM_TO_BOOK.put(27302, 173);  // Het's capture
		ITEM_TO_BOOK.put(27312, 174);  // The jackal's torch
		ITEM_TO_BOOK.put(27300, 175);  // Akila's journal
		ITEM_TO_BOOK.put(27310, 176);  // The wardens
		ITEM_TO_BOOK.put(27306, 177);  // Scabaras' capture

		// ── ID 178-193: Prifddinas / Grand Library books ──────────────────────
		ITEM_TO_BOOK.put(24055, 178);  // Bloody diary
		ITEM_TO_BOOK.put(24049, 179);  // Crazed scribbles
		ITEM_TO_BOOK.put(24051, 180);  // A dear friend
		ITEM_TO_BOOK.put(24065, 181);  // Ebrill's journal
		ITEM_TO_BOOK.put(24057, 182);  // The eight clans
		ITEM_TO_BOOK.put(24059, 183);  // Gollwyn's final statement
		ITEM_TO_BOOK.put(24529, 184);  // Harmony (book)
		ITEM_TO_BOOK.put(24075, 185);  // Legends of the mountain
		ITEM_TO_BOOK.put(24071, 186);  // The living statues
		ITEM_TO_BOOK.put(24061, 187);  // Niff & harry
		ITEM_TO_BOOK.put(23796, 188);  // Ode to eternity
		ITEM_TO_BOOK.put(24053, 189);  // On leprechauns
		ITEM_TO_BOOK.put(24063, 190);  // Soggy journal
		ITEM_TO_BOOK.put(24073, 191);  // The spurned demon
		ITEM_TO_BOOK.put(24067, 192);  // Stained journal
		ITEM_TO_BOOK.put(24069, 193);  // The truth behind the myth (excerpt)

		// ── ID 194: Overseer's book ───────────────────────────────────────────
		ITEM_TO_BOOK.put(13279, 194);

		// ── ID 195-197: Desert Treasure II – Temple books ─────────────────────
		ITEM_TO_BOOK.put(29525, 195);  // Movario's notes (volume 1)
		ITEM_TO_BOOK.put(29526, 196);  // Movario's notes (volume 2)
		ITEM_TO_BOOK.put(29596, 197);  // Duradel's notes

		// ── ID 198-200: Desert Treasure II – The Scar tablets ─────────────────
		ITEM_TO_BOOK.put(28438, 198);  // Old tablet
		ITEM_TO_BOOK.put(28465, 199);  // Slimy tablet (1) — part 1
		ITEM_TO_BOOK.put(28439, 200);  // Damp tablet (1) — incident report

		// ── IDs 201-205: Construction guide (zone duplicates) ─────────────────
		// Same item (8463) as ID 14 — already mapped above.

		// ── IDs 206-212, 220: Captain's Log (zone duplicates) ─────────────────
		// Same items (31985, 31986) as ID 95 — already mapped above.

		// ── IDs 213-218: Entomologist's diary (zone duplicates) ───────────────
		// Same item (11904) as ID 22 — already mapped above.

		// ── ID 219: List of repairs ───────────────────────────────────────────
		ITEM_TO_BOOK.put(31397, 219);

		// ── Book names (for panel display + chat notifications) ───────────────
		BOOK_NAMES.put(1,   "Security book");
		BOOK_NAMES.put(2,   "Goblin symbol book");
		// ID 3 (Master Scroll Book) has no zones — not tracked
		BOOK_NAMES.put(4,   "Stronghold notes");
		BOOK_NAMES.put(5,   "Collection log");
		BOOK_NAMES.put(6,   "Pie recipe book");
		BOOK_NAMES.put(7,   "Goblin book");
		BOOK_NAMES.put(8,   "Book (Shield of Arrav)");
		BOOK_NAMES.put(9,   "Newspaper");
		BOOK_NAMES.put(10,  "Chronicle");
		BOOK_NAMES.put(11,  "Book on chickens");
		BOOK_NAMES.put(12,  "Strange book");
		BOOK_NAMES.put(13,  "Atlax's diary");
		BOOK_NAMES.put(14,  "Construction guide");
		BOOK_NAMES.put(15,  "Book on chemicals");
		BOOK_NAMES.put(16,  "The desert trout - ship's log");
		BOOK_NAMES.put(17,  "Spirit anglers research notes");
		BOOK_NAMES.put(18,  "Tome of water");
		BOOK_NAMES.put(19,  "Ballad");
		BOOK_NAMES.put(20,  "Translation");
		BOOK_NAMES.put(21,  "Embalming manual");
		BOOK_NAMES.put(22,  "Entomologist's diary");
		BOOK_NAMES.put(23,  "Arena book");
		BOOK_NAMES.put(24,  "Mage's book");
		BOOK_NAMES.put(25,  "Bird book");
		BOOK_NAMES.put(26,  "Feathered journal");
		BOOK_NAMES.put(27,  "Book of folklore");
		BOOK_NAMES.put(28,  "Journal (Making History)");
		BOOK_NAMES.put(29,  "Instruction manual");
		BOOK_NAMES.put(30,  "Sacraments of ent folk");
		BOOK_NAMES.put(31,  "On migration patterns");
		BOOK_NAMES.put(32,  "Moonrise wines");
		BOOK_NAMES.put(33,  "Gianne's cook book");
		BOOK_NAMES.put(34,  "A handwritten book");
		BOOK_NAMES.put(35,  "Cocktail guide");
		BOOK_NAMES.put(36,  "Yewnock's notes");
		BOOK_NAMES.put(37,  "Book of spyology");
		BOOK_NAMES.put(38,  "The hueycoatl (book)");
		BOOK_NAMES.put(39,  "Tome of earth");
		BOOK_NAMES.put(40,  "Guide book");
		BOOK_NAMES.put(41,  "Astronomy book");
		BOOK_NAMES.put(42,  "Glassblowing book");
		BOOK_NAMES.put(43,  "Battered book");
		BOOK_NAMES.put(44,  "Slashed book");
		BOOK_NAMES.put(45,  "Beaten book");
		BOOK_NAMES.put(46,  "God book");
		BOOK_NAMES.put(47,  "Manual");
		BOOK_NAMES.put(48,  "Journal (Horror from the Deep)");
		BOOK_NAMES.put(49,  "Diary (Horror from the Deep)");
		BOOK_NAMES.put(50,  "My notes");
		BOOK_NAMES.put(51,  "Queen help book");
		BOOK_NAMES.put(52,  "Book on baxtorian");
		BOOK_NAMES.put(53,  "Diary (Witch's House)");
		BOOK_NAMES.put(54,  "Auburn valley ecological reports");
		BOOK_NAMES.put(55,  "Gielinor's flora - allotments");
		BOOK_NAMES.put(56,  "Gielinor's flora - fruit");
		BOOK_NAMES.put(57,  "Gielinor's flora - bushes");
		BOOK_NAMES.put(58,  "Gielinor's flora - flowers");
		BOOK_NAMES.put(59,  "Gielinor's flora - trees");
		BOOK_NAMES.put(60,  "Gielinor's flora - herbs");
		BOOK_NAMES.put(61,  "Gielinor's flora - hops");
		BOOK_NAMES.put(62,  "Neilan's journal");
		BOOK_NAMES.put(63,  "Soul wars guide");
		BOOK_NAMES.put(64,  "Game book");
		BOOK_NAMES.put(65,  "Abyssal book");
		BOOK_NAMES.put(66,  "Dagon'hai history");
		BOOK_NAMES.put(67,  "Sin'keth's diary");
		BOOK_NAMES.put(68,  "Book of knowledge");
		BOOK_NAMES.put(69,  "Tome of fire");
		BOOK_NAMES.put(70,  "Smelly journal");
		BOOK_NAMES.put(71,  "Kasonde's journal");
		BOOK_NAMES.put(72,  "Varlamore envoy");
		BOOK_NAMES.put(73,  "Byrne's coronation speech");
		BOOK_NAMES.put(74,  "Dwarven lore");
		BOOK_NAMES.put(75,  "Tome of the moon");
		BOOK_NAMES.put(76,  "Tome of the sun");
		BOOK_NAMES.put(77,  "Tome of the temple");
		BOOK_NAMES.put(78,  "Kharedst's memoirs");
		BOOK_NAMES.put(79,  "Mount karuulm diary");
		BOOK_NAMES.put(80,  "Book of the dead");
		BOOK_NAMES.put(81,  "Creature keeper's journal");
		BOOK_NAMES.put(82,  "Dark journal");
		BOOK_NAMES.put(83,  "Houndmaster's diary");
		BOOK_NAMES.put(84,  "Vanguard judgement");
		BOOK_NAMES.put(85,  "Nistirio's manifesto");
		BOOK_NAMES.put(86,  "Transdimensional notes");
		BOOK_NAMES.put(87,  "Tekton's journal");
		BOOK_NAMES.put(88,  "History of iban");
		BOOK_NAMES.put(89,  "Old journal");
		BOOK_NAMES.put(90,  "Silver book");
		BOOK_NAMES.put(91,  "Ballad of the basilisk");
		BOOK_NAMES.put(92,  "Parchment (Olaf's Quest)");
		BOOK_NAMES.put(93,  "Moonclan manual");
		BOOK_NAMES.put(94,  "Book (Ratpits)");
		BOOK_NAMES.put(95,  "Captain's Log");
		BOOK_NAMES.put(96,  "Glough's journal");
		BOOK_NAMES.put(97,  "Translation book");
		BOOK_NAMES.put(98,  "Clockwork book");
		BOOK_NAMES.put(99,  "Ancient diary");
		BOOK_NAMES.put(100, "Archaeologist's diary");
		BOOK_NAMES.put(101, "Fossil island note book");
		BOOK_NAMES.put(102, "Ash covered tome");
		BOOK_NAMES.put(103, "Farming manual");
		BOOK_NAMES.put(104, "Knight's notes");
		BOOK_NAMES.put(105, "Weiss fire notes");
		BOOK_NAMES.put(106, "Al kharid flyer");
		BOOK_NAMES.put(107, "Varmen's notes");
		BOOK_NAMES.put(108, "Demonic tome");
		BOOK_NAMES.put(109, "Tarn's diary");
		BOOK_NAMES.put(110, "Diary (Shades of Mort'ton)");
		BOOK_NAMES.put(111, "Journal (Filliman Tarlock)");
		BOOK_NAMES.put(112, "Bloody notes");
		BOOK_NAMES.put(113, "Tree wizards' journal");
		BOOK_NAMES.put(114, "Book of haricanto");
		BOOK_NAMES.put(115, "Translation manual");
		BOOK_NAMES.put(116, "Crumbling tome");
		BOOK_NAMES.put(117, "Bloody grimoire");
		BOOK_NAMES.put(118, "Buried alive");
		BOOK_NAMES.put(119, "Deed");
		BOOK_NAMES.put(120, "Explosive discovery");
		BOOK_NAMES.put(121, "The turncloak");
		BOOK_NAMES.put(122, "Histories of the hallowland");
		BOOK_NAMES.put(123, "The sleeping seven");
		BOOK_NAMES.put(124, "Modern day morytania");
		BOOK_NAMES.put(125, "Book o' piracy");
		BOOK_NAMES.put(126, "Brewin' guide");
		BOOK_NAMES.put(127, "Castlewars manual");
		BOOK_NAMES.put(128, "Necromancy book");
		BOOK_NAMES.put(129, "Book of 'h.a.m'");
		BOOK_NAMES.put(130, "Book of portraiture");
		BOOK_NAMES.put(131, "Prayer book");
		BOOK_NAMES.put(132, "Tome of experience (3)");
		BOOK_NAMES.put(133, "Haemalchemy volume 1");
		BOOK_NAMES.put(134, "Old diary");
		BOOK_NAMES.put(135, "A taste of hope");
		BOOK_NAMES.put(136, "Elixir of everlasting");
		BOOK_NAMES.put(137, "Strange hallowed tome");
		BOOK_NAMES.put(138, "Verzik vitur - patient record");
		BOOK_NAMES.put(139, "The butcher");
		BOOK_NAMES.put(140, "The wild hunt");
		BOOK_NAMES.put(141, "The shadow realm");
		BOOK_NAMES.put(142, "Serafina's diary");
		BOOK_NAMES.put(143, "Arachnids of vampyrium");
		BOOK_NAMES.put(144, "Explorer's notes");
		BOOK_NAMES.put(145, "Book on costumes");
		BOOK_NAMES.put(146, "Ghrim's book");
		BOOK_NAMES.put(147, "Burnt diary (5)");
		BOOK_NAMES.put(148, "Lift Manual");
		BOOK_NAMES.put(149, "Binding book");
		BOOK_NAMES.put(150, "Shaman's tome");
		BOOK_NAMES.put(151, "Big book of bangs");
		BOOK_NAMES.put(152, "Cadarn lineage");
		BOOK_NAMES.put(153, "Crystal singing for beginners");
		BOOK_NAMES.put(154, "Eastern discovery");
		BOOK_NAMES.put(155, "The great divide");
		BOOK_NAMES.put(156, "Eastern settlement");
		BOOK_NAMES.put(157, "Prifddinas' history");
		BOOK_NAMES.put(158, "Edern's journal");
		BOOK_NAMES.put(159, "Ohn's diary");
		BOOK_NAMES.put(160, "Herman's book");
		BOOK_NAMES.put(161, "Imafore's betrayal");
		BOOK_NAMES.put(162, "Aivas' diary");
		BOOK_NAMES.put(163, "Ungael lab notes");
		BOOK_NAMES.put(164, "Lutwidge and the moonfly");
		BOOK_NAMES.put(165, "Malumac's journal");
		BOOK_NAMES.put(166, "Ablenkian's escape");
		BOOK_NAMES.put(167, "The weeping");
		BOOK_NAMES.put(168, "Serafina (book)");
		BOOK_NAMES.put(169, "Lithkren vault notes");
		BOOK_NAMES.put(170, "Imcandoria's fall");
		BOOK_NAMES.put(171, "Apmeken's capture");
		BOOK_NAMES.put(172, "Crondis' capture");
		BOOK_NAMES.put(173, "Het's capture");
		BOOK_NAMES.put(174, "The jackal's torch");
		BOOK_NAMES.put(175, "Akila's journal");
		BOOK_NAMES.put(176, "The wardens");
		BOOK_NAMES.put(177, "Scabaras' capture");
		BOOK_NAMES.put(178, "Bloody diary");
		BOOK_NAMES.put(179, "Crazed scribbles");
		BOOK_NAMES.put(180, "A dear friend");
		BOOK_NAMES.put(181, "Ebrill's journal");
		BOOK_NAMES.put(182, "The eight clans");
		BOOK_NAMES.put(183, "Gollwyn's final statement");
		BOOK_NAMES.put(184, "Harmony (book)");
		BOOK_NAMES.put(185, "Legends of the mountain");
		BOOK_NAMES.put(186, "The living statues");
		BOOK_NAMES.put(187, "Niff & harry");
		BOOK_NAMES.put(188, "Ode to eternity");
		BOOK_NAMES.put(189, "On leprechauns");
		BOOK_NAMES.put(190, "Soggy journal");
		BOOK_NAMES.put(191, "The spurned demon");
		BOOK_NAMES.put(192, "Stained journal");
		BOOK_NAMES.put(193, "The truth behind the myth (excerpt)");
		BOOK_NAMES.put(194, "Overseer's book");
		BOOK_NAMES.put(195, "Movario's notes (volume 1)");
		BOOK_NAMES.put(196, "Movario's notes (volume 2)");
		BOOK_NAMES.put(197, "Duradel's notes");
		BOOK_NAMES.put(198, "Old tablet");
		BOOK_NAMES.put(199, "Slimy tablet (1)");
		BOOK_NAMES.put(200, "Damp tablet (1)");
		// Zone duplicates 201-220 (same item IDs as primary entries above)
		BOOK_NAMES.put(201, "Construction guide");
		BOOK_NAMES.put(202, "Construction guide");
		BOOK_NAMES.put(203, "Construction guide");
		BOOK_NAMES.put(204, "Construction guide");
		BOOK_NAMES.put(205, "Construction guide");
		BOOK_NAMES.put(206, "Captain's Log");
		BOOK_NAMES.put(207, "Captain's Log");
		BOOK_NAMES.put(208, "Captain's Log");
		BOOK_NAMES.put(209, "Captain's Log");
		BOOK_NAMES.put(210, "Captain's Log");
		BOOK_NAMES.put(211, "Captain's Log");
		BOOK_NAMES.put(212, "Captain's Log");
		BOOK_NAMES.put(213, "Entomologist's diary");
		BOOK_NAMES.put(214, "Entomologist's diary");
		BOOK_NAMES.put(215, "Entomologist's diary");
		BOOK_NAMES.put(216, "Entomologist's diary");
		BOOK_NAMES.put(217, "Entomologist's diary");
		BOOK_NAMES.put(218, "Entomologist's diary");
		BOOK_NAMES.put(219, "List of repairs");
		BOOK_NAMES.put(220, "Captain's Log");
		BOOK_NAMES.put(221, "Eathram & rada extract");
		BOOK_NAMES.put(222, "Hosidius letter");
		BOOK_NAMES.put(223, "Ideology of darkness");
		BOOK_NAMES.put(224, "Killing of a king");
		BOOK_NAMES.put(225, "Rada's census");
		BOOK_NAMES.put(226, "Rada's journey");
		BOOK_NAMES.put(227, "Ricktor's diary (7)");
		BOOK_NAMES.put(228, "Soul journey");
		BOOK_NAMES.put(229, "Transportation incantations");
		BOOK_NAMES.put(230, "Transvergence theory");
		BOOK_NAMES.put(231, "Treachery of royalty");
		BOOK_NAMES.put(232, "Tristessa's tragedy");
		BOOK_NAMES.put(233, "Twill accord");
		BOOK_NAMES.put(234, "Wintertodt parable");

		// ── Compute total distinct book IDs ───────────────────────────────────
		TOTAL_BOOKS = (int) ITEM_TO_BOOK.values().stream().distinct().count();
	}
}
