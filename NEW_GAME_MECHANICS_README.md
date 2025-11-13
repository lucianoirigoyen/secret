# 🎮 Nouvelles Mécaniques de Jeu - Compagnons Évolutifs & Critiques Illimités

## 📅 Version 3.0.0 (2025-11-10)

---

## 🌟 Vue d'Ensemble

Deux systèmes majeurs ont été ajoutés au jeu:
1. **Système de Compagnons Évolutifs** (Style Pokémon)
2. **Système de Critique Amélioré** (100% + Bonus Post-100%)

---

## 🥚 SYSTÈME 1: COMPAGNONS ÉVOLUTIFS

###  Concept
Un système de compagnon de combat qui évolue avec le joueur, inspiré des Pokémon. Le compagnon attaque automatiquement avec le joueur et devient plus puissant au fil du temps!

### 📊 Stades d'Évolution

#### Stade 1: L'Œuf 🥚
- **Nom**: "Œuf Mystérieux"
- **Dégâts**: 1 (très faible)
- **Attaque**: "💥 L'œuf roule maladroitement vers l'ennemi!"
- **Évolution**: Après **5 combats**

#### Stade 2: Starter Pokémon (Aléatoire) 🐣
Après 5 combats, l'œuf éclot et révèle **aléatoirement** l'un des 3 starters:

| Pokémon | Emoji | Attaque Humoristique |
|---------|-------|---------------------|
| **Bulbizarre** | 🌱 | "Fouet Lianes Comique" |
| **Salamèche** | 🔥 | "Flammèche Timide" |
| **Carapuce** | 💧 | "Pistolet à Eau Éclaboussant" |

- **Dégâts**: 5
- **Évolution**: Après **20 combats** au total

#### Stade 3: Évolution 1 💪
Le starter évolue vers sa forme intermédiaire:

| Pokémon | Évolution | Attaque |
|---------|-----------|---------|
| Bulbizarre → **Herbizarre** | 🌿 | "Tempête de Pétales Parfumés!" |
| Salamèche → **Reptincel** | 🔥 | "Lance-Flammes Dramatique!" |
| Carapuce → **Carabaffe** | 💦 | "Canon à Eau Haute Pression!" |

- **Dégâts**: 15
- **Évolution**: Après **50 combats** au total

#### Stade 4: Évolution Finale ⭐
La forme finale et la plus puissante:

| Pokémon | Évolution Finale | Attaque Ultime |
|---------|------------------|----------------|
| Herbizarre → **Florizarre** | ☀️ | "Rayon Solaire Aveuglant!" |
| Reptincel → **Dracaufeu** | 🌪️ | "Souffle du Dragon Dévastateur!" |
| Carabaffe → **Tortank** | 🌊 | "Hydrocanon Tsunami!" |

- **Dégâts**: 40
- **Statut**: **MAX LEVEL ⭐**

### 🎯 Mécaniques du Compagnon

#### Attaques Automatiques
- Le compagnon attaque **à chaque clic** du joueur
- Les dégâts du compagnon s'ajoutent aux dégâts du joueur
- Les attaques ont des descriptions humoristiques/parodiques

#### Système de Progression
- **Compteur de Combats**: Chaque monstre tué = 1 combat
- **Évolutions Automatiques**: Le compagnon évolue automatiquement quand il atteint le seuil
- **Notification d'Évolution**: Un message s'affiche quand le compagnon évolue

#### Affichage
- **Emoji**: Montre l'état actuel du compagnon
- **Nom**: Affiche le nom du Pokémon
- **Progression**: "X/Y combats" jusqu'à la prochaine évolution
- **Barre de Compagnon**: Affichée dans l'UI principale

---

## ⚡ SYSTÈME 2: CRITIQUE AMÉLIORÉ (100% + BONUS POST-100%)

### 🎯 Concept
Le système de critique a été **complètement refondu**:
- **Suppression du cap 55%** → Peut atteindre **100%** (critiques garantis!)
- **Bonus Post-100%** → Les points de crit au-delà de 100% débloquent des bonus spéciaux

### 📈 Progression Jusqu'à 100%

#### Avant (Ancien Système)
- ❌ Cap à 55% de chance de critique
- ❌ Les points au-delà de 55% étaient perdus

#### Maintenant (Nouveau Système)
- ✅ Peut progresser jusqu'à **100% de crit**
- ✅ À 100%, **chaque attaque est un critique garanti**!
- ✅ Les points au-delà de 100% sont convertis en bonus spéciaux

### 🌟 Bonus Post-100% (Système Aléatoire)

Une fois à **100% de crit**, chaque point de crit supplémentaire est **réassigné aléatoirement** vers l'un de ces 3 bonus:

#### 1. Dégâts Critiques Augmentés 💥
- **Probabilité**: 40%
- **Effet**: +1% au multiplicateur de dégâts critiques
- **Exemple**: 150% → 151% → 152%...
- **Impact**: Chaque critique fait plus de dégâts

#### 2. Chance d'Attaque en Zone (AoE) 💣
- **Probabilité**: 35%
- **Effet**: +0.5% de chance d'AoE par point
- **Exemple**: À 10% AoE, 1 critique sur 10 touche en zone
- **Impact**: Les critiques peuvent toucher plusieurs ennemis (futur système multi-monstres)

#### 3. Chance d'Effet de Statut ☠️
- **Probabilité**: 25%
- **Effet**: +0.5% de chance d'appliquer un effet par point
- **Exemple**: À 5% proc, 1 critique sur 20 applique un effet
- **Impact**: Les critiques peuvent causer des effets spéciaux!

### 🎲 Effets de Statut Disponibles

Quand un critique proc un effet, l'un de ces 4 effets est appliqué aléatoirement:

| Effet | Emoji | Description |
|-------|-------|-------------|
| **Brûlure** | 🔥 | Le monstre prend des dégâts sur la durée! |
| **Étourdissement** | ⚡ | Le monstre est paralysé! |
| **Poison** | ☠️ | Le monstre est empoisonné! |
| **Ralentissement** | ❄️ | Le monstre attaque plus lentement! |

### 📊 Affichage des Bonus

Un nouvel écran/panneau montre:
```
Bonus Crit 100%+:
  +25% Dégâts Crit
  3.5% Chance AoE
  2.0% Chance Effet
```

### ⚙️ Conversion Automatique

Exemple de progression:
1. Joueur a 95% de crit
2. Achète un upgrade +10% crit
3. Système calcule: 95% + 10% = 105%
4. Crit fixé à 100% (max)
5. L'excès (5%) est converti: 5% ÷ 5 = **1 bonus point**
6. Le bonus point est assigné aléatoirement (Dégâts/AoE/Effet)

---

## 🏗️ Architecture Technique

### Nouvelles Classes

#### 1. `Companion.java`
```java
public class Companion implements Serializable {
    - EvolutionStage (EGG, STARTER, EVOLUTION_1, EVOLUTION_2)
    - StarterType (BULBASAUR, CHARMANDER, SQUIRTLE)
    - battlesParticipated
    - currentName
    - Methods: addBattleExperience(), getAttackDamage(), getAttackDescription()
}
```

#### 2. `CritSystem.java`
```java
public class CritSystem implements Serializable {
    - PostCritBonus (CRIT_DAMAGE, AOE_CHANCE, EFFECT_PROC)
    - extraCritDamage, aoeChance, effectProcChance
    - Methods: applyPostCritBonus(), rollAoE(), rollStatusEffect()
}
```

### Modifications des Classes Existantes

#### `Player.java`
```java
+ private Companion companion;
+ private CritSystem critSystem;
+ Modified: upgradeCritRate() - No more 55% cap, converts excess to bonuses
```

#### `CombatSystem.java`
```java
+ Enhanced AttackResult class with:
  - companionDamage, companionAttack
  - isAoE, statusEffect
  - companionEvolved
+ Modified: attack() method includes companion attacks and enhanced crits
```

---

## 🎮 Gameplay Impact

### Pour le Joueur

#### Début de Partie
- 🥚 Commence avec un œuf mignon qui fait 1 de dégâts
- 😄 Attaques humoristiques ("L'œuf roule maladroitement...")
- 🎯 Objectif: Faire 5 combats pour voir ce qui va éclore!

#### Milieu de Partie
- 🐣 L'œuf éclot! Quel starter allez-vous avoir?
- 💪 Le starter fait 5 dégâts (boost significatif)
- 📈 Progression vers l'évolution 1 (20 combats)
- ⚡ Le taux de crit peut maintenant dépasser 55%!

#### Fin de Partie
- ⭐ Compagnon en forme finale (40 dégâts!)
- 💯 Critiques garantis à 100%
- 🌟 Bonus post-100%: AoE, effets spéciaux
- 🔥 Combos dévastateurs avec compagnon + critiques améliorés

### Stratégies Émergentes

#### Build "Compagnon Rapide"
- Focus sur tuer rapidement pour faire évoluer le compagnon
- Priorité: Dégâts de base pour finir les monstres vite

#### Build "Critique Pur"
- Rush vers 100% de crit pour critiques garantis
- Puis stack les bonus post-100% pour multiplicateurs élevés

#### Build "Contrôle"
- Focus AoE et Effets de Statut
- Gère plusieurs ennemis avec contrôle

---

## 📊 Balancement

### Dégâts du Compagnon

| Stade | Dégâts | % du Joueur (Base 10) |
|-------|--------|----------------------|
| Œuf | 1 | 10% |
| Starter | 5 | 50% |
| Évolution 1 | 15 | 150% |
| Évolution Finale | 40 | 400% |

### Taux de Conversion Post-100%

- **Ratio**: 5% de crit excess = 1 bonus point
- **Probabilités**:
  - Dégâts Crit: 40%
  - AoE: 35%
  - Effets: 25%

### Effets de Statut

*À implémenter dans version future:*
- Brûlure: 5% HP/sec pendant 3 sec
- Étourdissement: Skip 1 attaque du boss
- Poison: 10% HP au total sur 5 sec
- Ralentissement: -50% vitesse d'attaque du boss

---

## 🎨 Visuels & Animations

### Compagnon

#### Affichage
- Petite icône animée à côté du joueur
- Emoji correspondant au stade/type
- Animation d'attaque synchronisée avec le joueur

#### Évolution
- ✨ Animation spéciale d'évolution
- 🎆 Particules colorées (vert/rouge/bleu selon le type)
- 📣 Message: "Votre [nom] évolue en [nom évolution]!"
- 🎵 Son (si implémenté)

### Critiques Améliorés

#### AoE (Zone d'Effet)
- 💥 Cercle d'explosion autour du monstre
- 🌊 Particules qui s'étendent en cercle
- ⚡ Flash plus large que les critiques normaux

#### Effets de Statut
- 🔥 **Brûlure**: Flammes sur le monstre
- ⚡ **Étourdissement**: Étoiles qui tournent
- ☠️ **Poison**: Bulles vertes/violettes
- ❄️ **Ralentissement**: Cristaux de glace bleus

#### Post-100% Indicateur
- 💎 Badge "CRIT MAX 100%" sur l'UI
- 📊 Panneau des bonus avec barres de progression
- ✨ Aura spéciale sur le joueur quand à 100%

---

## 🔧 Configuration

### Ajustements Possibles

#### Vitesse d'Évolution du Compagnon
```java
// Dans Companion.java, enum EvolutionStage
EGG(5, ...),          // 5 combats → starter
STARTER(20, ...),     // 20 combats → évolution 1
EVOLUTION_1(50, ...), // 50 combats → évolution finale
```

#### Dégâts du Compagnon
```java
// Dans Companion.java, enum EvolutionStage
EGG(..., 1, ...),           // 1 dégât
STARTER(..., 5, ...),       // 5 dégâts
EVOLUTION_1(..., 15, ...),  // 15 dégâts
EVOLUTION_2(..., 40, ...)   // 40 dégâts
```

#### Probabilités Post-100%
```java
// Dans CritSystem.java, enum PostCritBonus
CRIT_DAMAGE("...", 0.4),   // 40%
AOE_CHANCE("...", 0.35),   // 35%
EFFECT_PROC("...", 0.25)   // 25%
```

---

## 🐛 Considérations de Debug

### Tests Importants

1. **Évolution du Compagnon**
   - ✅ Vérifier que l'œuf éclot après exactement 5 combats
   - ✅ Vérifier que les 3 starters ont une probabilité égale (33.33% chacun)
   - ✅ Vérifier les seuils 20 et 50 combats

2. **Système de Crit**
   - ✅ Vérifier que le cap 55% est bien supprimé
   - ✅ Tester la progression jusqu'à 100%
   - ✅ Vérifier la conversion des points excess

3. **Bonus Post-100%**
   - ✅ Vérifier que les probabilités respectent 40/35/25
   - ✅ Tester les procs AoE et Effets
   - ✅ Vérifier l'affichage des bonus accumulés

### Commandes de Test (À ajouter)

```java
// Pour tester rapidement
player.getCompanion().setBattlesParticipated(4);   // Prochain combat = évolution
player.setCritRate(98.0);                           // Test du cap 100%
player.getCritSystem().setAoeChance(50.0);         // Test AoE fréquent
```

---

## 📈 Métriques & Analytics

### Données à Tracker

1. **Distribution des Starters**
   - Combien de joueurs ont chaque starter?
   - Distribution équitable?

2. **Temps d'Évolution**
   - Temps moyen pour atteindre chaque stade
   - Corrélation avec progression du joueur

3. **Utilisation des Critiques**
   - Combien de joueurs atteignent 100%?
   - Distribution des bonus post-100%
   - Fréquence des procs AoE/Effets

---

## 🚀 Futures Améliorations

### Court Terme

1. **UI du Compagnon**
   - Panneau dédié avec stats complètes
   - Historique des évolutions
   - Galerie de tous les starters

2. **Effets Visuels**
   - Animations d'attaque du compagnon
   - Particules pour les évolutions
   - Effets de statut animés

3. **Son & Musique**
   - Cris des Pokémon (parodie)
   - SFX pour les évolutions
   - Musique d'évolution dramatique

### Moyen Terme

4. **Système d'Œufs Multiple**
   - Possibilité d'avoir plusieurs compagnons
   - Système de changement actif
   - Collection de tous les starters

5. **Effets de Statut Fonctionnels**
   - Implémentation complète des DoTs
   - Stun qui skip les attaques boss
   - Slow qui réduit le DPS

6. **Multi-Monstres**
   - AoE qui touche réellement plusieurs monstres
   - Vagues d'ennemis
   - Boss avec adds

### Long Terme

7. **Compagnons Légendaires**
   - Formes Méga-Évolution
   - Compagnons légendaires (Mewtwo, Lugia, etc.)
   - Shiny variants (très rare)

8. **Talent Trees**
   - Arbre de talents pour le compagnon
   - Spécialisations (Tank/DPS/Support)
   - Respec possible

9. **PvP/Coop**
   - Montrer les compagnons des autres joueurs
   - Combats de compagnons
   - Raids coopératifs

---

## 💡 Conseils pour les Joueurs

### Optimisation du Compagnon

1. **Ne pas Farmer Trop Tôt**
   - Le compagnon évolue par nombre de combats, pas de temps
   - Tuer vite = évolution plus rapide
   - Priorité aux petits monstres au début

2. **Choix du Starter**
   - C'est aléatoire, mais tous sont équilibrés
   - Bulbizarre 🌱: Équilibré
   - Salamèche 🔥: Thème offensif
   - Carapuce 💧: Thème défensif (futur)

### Optimisation des Critiques

1. **Rush 100% d'Abord**
   - Critiques garantis = dégâts constants élevés
   - Ensuite seulement focus sur autres stats

2. **Stratégies Post-100%**
   - **Build DPS**: Espérer les bonus dégâts crit
   - **Build AoE**: Utile quand système multi-monstres arrive
   - **Build Contrôle**: Effets pour gérer les boss

3. **Éviter le Waste**
   - Ne pas acheter +crit si déjà à 100%...
   - ...SAUF si vous voulez les bonus post-100%!
   - Chaque 5% excess = 1 bonus garanti

---

## 📝 Changelog

### Version 3.0.0 (2025-11-10)

**Ajouts Majeurs:**
- ✨ Système de Compagnons Évolutifs complet
- ⚡ Suppression du cap de crit à 55%
- 🌟 Système de bonus post-100% (Dégâts/AoE/Effets)
- 🎨 Nouvelles classes: `Companion.java`, `CritSystem.java`

**Modifications:**
- 🔧 `Player.java`: Ajout companion + critSystem
- 🔧 `CombatSystem.java`: Support attaques compagnon + critiques améliorés
- 🔧 `AttackResult`: Nouveaux champs pour nouvelles mécaniques

**Balance:**
- 📊 Œuf: 1 dmg (très faible mais mignon)
- 📊 Starter: 5 dmg (boost early game)
- 📊 Évolution 1: 15 dmg (mid game spike)
- 📊 Évolution Finale: 40 dmg (late game dominance)

---

## 🎉 Conclusion

Ces deux systèmes transforment complètement la progression du jeu:

- **Compagnons**: Ajoutent une dimension émotionnelle (voir son œuf évoluer!), du RNG fun (quel starter?), et de la puissance progressive
- **Crit 100%+**: Ouvrent un end-game infini avec des builds variés et des mécaniques avancées

Le jeu passe d'un clicker simple à un **RPG évolutif avec progression émotionnelle et stratégique**! 🚀

---

**Bon Jeu! 🎮✨**
