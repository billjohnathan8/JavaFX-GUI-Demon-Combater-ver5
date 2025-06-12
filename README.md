Java GUI Demon

Project Structure: 
vttrpg-combat-manager/
├── build.gradle.kts
├── settings.gradle.kts
├── README.md
├── data/                               # ← JSON "database"
│   ├── characters/
│   │   ├── ledros.json
│   │   └── goblin.json
│   └── encounters/
│       └── cave_encounter.json
│
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── vttrpg/
│       │           ├── Main.java       # JavaFX application launcher
│       │           │
│       │           ├── model/          # Domain models (e.g., Combatant, Token)
│       │           ├── view/           # FXML views and JavaFX UI
│       │           ├── viewmodel/      # JavaFX ViewModels (bindable properties)
│       │           ├── controller/     # Optional UI logic handlers
│       │           ├── engine/         # Core combat logic, turn system, etc.
│       │           └── util/           # JSON utils, image loader, math helpers
│       │
│       └── resources/
│           ├── fxml/                   # FXML layout files
│           │   ├── MainView.fxml
│           │   └── CharacterSheet.fxml
│           ├── images/                # Token graphics, fallback icons
│           └── css/                   # UI stylesheets
│
└── .gitignore

database - local (not a single line of SQL needed)

TODO: 
Features: 


0. Character Creation - (StatBlock Creation), 
    -Stat Rolling 
    -Custom Form etc.

    -Enter PC Name 
    -Choose Playable Classes (Fixed)
    -Choose Playable Races (Presets)
    -Choose Background (Presets) -> Job Classes Automatically Assigned 
    -Choose Bond (Dependent on the Campaign)
    

1. Display Grid: 
    -Start a Combat Encounter with a Grid 
    -Input Grid Size (e.g. 2x2)
    -Tokens inside of Grid - drag and hover a 'ghost' token or 'green-selection area' before a token is spawned or loaded in
    -Tokens Snap to the grid unless otherwise specified. 
    -all players spawn with the same grid, sync'd to global events, but unique views of the grid. 
    -grid uses chebyshev distance (even when travelling vertically)

2. Camera
    -Drag Move Camera using Mouse Middle-Click
    -Pan Camera via Mouse Right-Click Selection
    -Zoom in and Out of the Grid
    -Each player has a unique camera view
    -Button so each player can always return to pan the camera back on their own token. (does not override the pan to the new token every turn <- future fix: make this optional)
    -Pan out on entire map during Lair Action
    -Calculate Pan-out Amount / Zoom during AOE Effects, Lair Actions, and Battlefield Actions.

    WIP: Scene Camera (for stream viewers, non-players)

3. Combat Initiative List
    -Keep track of initiative (turn order) on the side panel display when: 
        a) not selecting anything i.e, empty side panel
        b) turn end and change
    -Combat Carousel - keep track of 5 initiative cards (current, 2 passed 2 upcoming) 
    -Keep track of counters: round of combat / total rounds passed; turn in round; total turns passed; total AP passed, total ticks passed etc.
    -Keep track of current combatant (and their current turn), can only act during their turn. all other combatants can react or perform bonus actions or interrupt. 
    -move turn forward if turn is ended. 
    -reset turn index for every round passed, but keep track of total turns passed and AP passed / AP spent. 
    -display and signal current intiative and turn, panning camera to that token before every turn start. 

4. StatsPanel Display
    -Each player has a unique display view
    -right-hand-side has a StatsPanel whenever the player selects on a token or whenever the turn goes to that token. 
    -display health breakdown in order from top-bottom: 
        4.0. 'True AC' - just one bar that is always full (yellow coloured)
        4.1. Shield Layers (light blue)
        4.2. Armour Layers (grey color)
        4.3. Overhealed HP  / Temporary HP aka Absorption EFfect HP (green with a + sign)
        4.4. Health HP Bar (green)
    -change bar colours at different percentages: 
        - TrueAC <- yellow always
        - Shield Layers <- light blue always (once a layer is broken, it is removed from the list entirely, since shields are temporary)
        - Armour Layers <- grey->black gradient. background of the bar is completely black. (overlay cracked when at 0 armour for that layer)
        - Overhealed HP <- yellow (overlay glowing when 100%)->green->red. bar background is grey. 
        - HP <- green (100%)->orange->red. black when negative. (when 30%, 60%, 100% to deathHP <- add one skull until max:3.)
        - if there exists any Nonlethal damage -> add unique bar right above OverhealHP + HP Bars. Nonlethal Damage Bar: empty -> orange (fills up from left-to-right as damage is stacked.). if nonlethal damage bar is equal/longer than overhealHP + CurHP = unconscious until the nonlethal is removed. 
    -display AP as Action Orbs:
        - Green -> becomes grey when used. 
        - depletes from top->down.
        - when concentration reserve orbs from down->top.
        - when concentration reserves orbs (grey border and cage overlay on the orb)
        - animate release from cage when concentration is released actively or cage break when concentration is broken
        - store action orbs in an array (indexes matter so we know what actions are being concentrated on etc.)  
        - when stunned or paralysed <- AP Action Orbs have lightning cage overlay. cannot be spent for actions or used for concentration. goes from top-down. 
    -display MovementSPD as Boot Icons: 
        - always keep track of total movement already spent on turn <- important for conversion between different movement types
        - different movement types have different icons 
        - when converting between differnet movement types <- switch the icons, deplete the movement spent automatically. (basically changes the maxSPD always) 
        - adhere to movement feature
        - adhere to grid's chebyshev distance
    -display Summoning Slots as Blue Slots (Rectangular-shaped like cards / cages): 
        - similar to AP concentration
        - opposite colouration: green when empty -> blue when occupied
        - when full -> cannot summon anymore. 
        - indexes only matter to overlay icon of the summon occupying the slot.
        - always display maximum summoning range whenever casting - keep track of it on top-left corner on display.  
    -display other resources / scores as bars  (e.g. Mana, Sorcery Points) or icons/charges with numbers and fractional displays (e.g. Hit Dice, Sorcery Points) 
        -Mana Points (MP) -> purple->blue->red. grey background. 
        -Rage Points (RGP) -> red->empty. grey background. 
        -Ki Points (KI) -> bright blue->orange->red. grey background. (usually interchangable or replaces MP)
        -Karma Points (KP) -> green start icon. ticks upwards (unique resource). displays CurValue only. 
        -Hit Dice (HD) -> green dice with + sign. uses fraction display CurHD/maxHD. 
        -Sorcery Points (SP) -> orange orb. icon. uses fraction display CurSP/maxSP.
        -Bardic Inspiration Dice (BID) -> golden dice. icon. uses fraction display CurBID/maxBID.
        -Ammunition (AMMO) -> unique icon per ammuniiton. icon. uses fraction display CurAMMO/maxAMMO. 
    -for different detection status or missing information <- create mystery bar or mystery icon counter (question mark with bar that does not move).
        -only display bars that are known. then append one mystery bar above until all bars have been discovered per resource (e.g. all armour layers known or all shield layers known)
        -for HP bar <- display colour only (can roughly estimate how damaged a creature is). (nonlethal not displayed unless detected), no values unless detected. 
        -give access to reading certain stats only if the viewer is the owner of the character sheet of the token being selected or if the viewer has casted detect and has that permission to view the character sheet of the token being selected and detected on. 
        -unless otherwise timed or re-hidden, all detection is permanent for one entire combat encounter.

5. Sheet Displays: 
    -button to open All Available Character Sheets (if only one - then dynamically make that button just represent one character sheet) <- depends on total controllable tokens i.e, all tokens wholly and uniquely owned by the viewer on the battlefield that are NOT summons.
    -open character sheet = control panel for many other different displays -> opposite of HUD. it is a popup window that can be closed on keybind: 
        -overview (WIP)
        -current condition (displays all current status effects, statspanel values etc.)
        -full inventory (items and inventory) <- moving items around will result in taking an AP off if no special ability etc.
        -full skills, proficiencies, ability scores, and checks or skill challenges 
        -full ability list (discrete abilities, racial, spells, martial, item techniques etc.) 
        -full summon list 
        -full action list (all and exhaustive)
        -journal entries and lore etc.
        -secondary popup window (mid-combat crafting, mid-combat shop purchase, etc.) <- activating these will take AP off if no special ability etc. 
        -changing forms completely changes the sheet being viewed. 
    
6. Actions and Turns: 
    - active abilities and actions: 
        6.0. Resolve Resource Costs and Conditions: check whether all conditions are met and there are available resources for cast (unless otherwise specified that it drains resources to negative amounts). consume resources. 
        6.1. Cast (overlay AOE). Select Targets within AOE if applicable (e.g. resolve Cover). 
        6.2. Resolve Cast. Resolve DC Checks for Successful Cast, Determine Hits vs Misses etc. refund resources on failed cast - if applicable.
        6.3. Resolve Reactions. Resolve Dodge, Resolve Reactions, etc. Resolve Misses. Confirm Hit vs Miss Ratio. 
        6.4. Wait for the Cast Effect if applicable (Cast Time Ticks until completion unless it has no cast time or ticking delay for durational effects)
        6.4. Apply the Effect of the Cast <- Damage, Applying a Status Effect, Summoning a Creature in an Empty Space, Weapon Access, etc.
        6.5. Repeat if Active Ability results in chained attacks etc.

    - actions in combat (categories): 
        -attack
        -move (free) / Dash (Costs AP) <- cost depends on movement already spent and movement type used
        -cast spell
        -use racial abilities
        -use martial techniques 
        -use weapons
        -use weapons with ammo
        -use weapons with martial techniques
        -use weapons with ammo and martial techniques
        -use item (general - active ability)
        -detect (item, person, etc.)
        -hide (item, person, etc.)
        -concentrate on a skill -> concentration (some actions have CONCENTRATION tag while being casted or while an effect is active etc.)
        -manage inventory (item action)
        -combat maneuver 

        -response to help request -> help action 
        -general help action 
        -ready action and spring trigger reaction 
        -attack of opportunity
        -counter 
        -reaction 
        -interrupt 
        -bonus action 
        -interact (special) e.g. Terrain Alteration, Trap Setting, Trap Activation, Triggered Lair Action, Loot, Access External Inventory etc.
        -active disengage
        -active engage 
        -mount 
        -dismount 
        -combined action -> request for help. 
        -drop item 
        -displace token (throw item, drop and displace item, throw creature etc.)
        -stabilize
        -summon token (e.g. summon creature, place a trap, etc.)
        

    - partial actions in combat: 
        -interact (general)
        -communicate 
        -switch between movement types
        -switch between action types 
        
        -combo prompt (just happens, no partial action etc.)
        -Lair Action (by terrain)
        -Battlefield Action (by boss fight)
        -Legendary / Mythic Action (special 1-time per round use)
        -Ultimate Attack Action - (long CDR etc.)
        
    - effect triggers:
        -on-cast effect: once casting is resolved, the effect is applied. ignore all precedence. 
        -on-hit effect: applies when hit is successful (no dodge, no miss, etc.)
        -on-damage effect: apply secondary effect when damage is dealt (0 and lower means do not apply this secondary effect)
        -on-interact effect: when interact, immediately chain the next effect.
        -on-move effect: when moving, immediately chain the next effect
        -durational effect: depending on its tick rate, re-apply the effect periodically
        -Difficulty Class (DC) Trigger: forces a DC on the target, failure applies the effect. 
        -On-Receive effect: once a token is made a target, ignore all precedence except on-cast. the moment this effect is appliued to the target, it cannot be resisted. it will always be received. 
        -Attack of Opportunity (AoO) Trigger: if a creature is engaged and tries to exit without using disengage - trigger this effect on it. 
        -Conditional Trigger: given condition A -> apply effect B.  
        
    - log all actions in a chat (bottom-right)

5. Actions and Turns 
    



    log all actions  

6. Active Ability Casting 

3. Token Movement
    -Tokens can only move during their turn
    -Tokens are displaced when not on their turn
    -Highlight 
    -Movement Cost

Event Driven Architecture and Event Handling + Resolutions: 
    -Combat Events <- backend 
    -UI Events  <- unique for every single view or player (dependent on the token, etc.)



Map and Structures etc. 

10. CLI Text Chat: 
    -all actions are logged here
    -all messages are logged here
    -different people have different views of this text chat (based on proximity, global chat, whisperered messages, GMrolls, etc.)
    -logs can be downloaded and sent to an AI for combat summary
    -communicate with players. 
    -custom rolls 
    -WIP: session summary

11. Views: 
    -GM Layer, Map Layer, etc. Layers and Opacity. 
    -Detection and Stealth etc.
    -Elevation, overlap, birds-eye view
    -switch to isometric 
    -vertical view display  
    -fog of war + darkness/brightness + obscured + perceived etc.

12. Drawing Tool: 
    -freehand paint
    -shapes
    -add text 
    -ruler and measurements 
    -delete after X seconds. 



..
ping and camera pans 


13. Networking: 
    -URL Link 
    -Login Page for players vs GMs vs Campaigns etc.
    -personal views
    -personal compendiums (character knowledge and lore + entries)
    -personal language views (different fonts, hearing, etc. based on proficiency) - chat bubble?
    -personal settings 
    -personal player profile (e.g. display setting, connect to audio, connect to video, shortcuts, etc.)
    -Screen Capture for Streaming on Twitch / YouTube etc.
    -Security 

14. GM Panels: 
    -all assets
    -all images 
    -all character sheets / statblocks 
    -all entries (journal etc.)
    -cinematic video player
    -slideshow player 
    -visual novel display (scenes)
    -handouts / popupts etc.
    -encyclopedia <- etc.
    -Jukebox
    -Macros, Decks, Roll Tables 
    -Settings 
    -Page / Scene Management <- Party Ribbon etc.
    -Custom Panel: request roll, secret shit happens, GM Whisper, GM Rolls, etc.

    -wiki access <- main website. 


Ring Creation for Tokens. 
Custom Token Creator 
Languages <- real-life
Managing Time, Downtime, Rest. etc.  - Calendar + Party Inventory/Factions 
Shops 
CharacterImage <- bottom-left on hover? 
Players with speaking token etc.
Harvesting 

Custom Bonuses 
Duration Tracking 
Item Piles and Item Tokens 
AutoTrigger from Environment etc.
Riding + Mounting 
Custom Journal 
4X Factions and World-Conquering Management, Tech Trees, Factions, etc. 
Burning Wheel Screen <- Artha, Fate, Beliefs 

15. Art, Assets, and Animations: (the 'look') 
    -All dice rolls are animated and rolled visually (some values are hidden if the roll is meant to be hidden)
    -ambient noise
    -sequencer and animation macros. 
    -persona5 popup -> character animations and sync with music 
    -automatic music looping (based on different song chops etc.)
    -terrain music
    -overriding and precedence of music importance etc.

-Branding <- Design Communication, Types. 

16. Testing: 
Unit Testing 
Integration Testing  
Stress Test
Security Test (blueteam)
Policy Setting for Testing Policy (Social Engineering, Telemetry, etc.)
Data Aggregation and Automation to other Website (e.g. Wiki, CI/CD Pipelines, Data Highways/Pipelines, LLM Training for NPCs)
User Acceptance Test
Efficiency Tests <- optimizations and tradeoffs. 

Design A/B Testing <- for UI (on the side) <- iterative, incremental, informs most decisions about the look and feel.  


17. Analytics, Session Summary, Automation + AI RVC + NPC Personality Training 


