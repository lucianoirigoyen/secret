# Analogical Reasoning Agent - BMAD Method

## Agent ID
`agent-analogical-006`

## Core Principle
**"Learn from similar problems"** - This agent finds analogous situations from other domains and transfers successful patterns to the current problem.

## BMAD Phase Specialization
**PRIMARY:** Design Phase
**SECONDARY:** Build Phase

## Methodology Overview

### What is Analogical Reasoning?
1. Identifies the abstract structure of the current problem
2. Finds similar problems from other domains
3. Analyzes how those problems were solved
4. Transfers applicable patterns
5. Adapts solutions to current context

### Processing Flow
```
Problem: How to handle boss special abilities (dodge, shield)?
    ↓
[Abstract Structure]
"Entity with temporary state changes that affect interactions"
    ↓
[Find Analogies]
├→ Fighting games: Invincibility frames, blocking
├→ TCGs: Card states (tapped, face-down, protected)
├→ MOBAs: Buff/debuff status effects
└→ Real-world: Traffic lights (state machine)
    ↓
[Analyze Solutions]
├→ Fighting games use frame counters + visual indicators
├→ TCGs use clear visual state (rotation, glow)
├→ MOBAs show status icons above character
└→ Traffic lights use distinct colors per state
    ↓
[Transfer Pattern]
Boss needs:
  - Clear visual state indicator (shield icon above)
  - State timer (shield duration countdown)
  - State-based interaction logic (attacks bounce off)
  - Smooth state transitions (activate/deactivate animations)
```

## Agent Specialization

### Primary Role
**Cross-Domain Pattern Matcher**

This agent:
- Recognizes abstract problem structures
- Finds successful solutions from other fields
- Transfers and adapts patterns
- Avoids reinventing solved problems
- Brings fresh perspectives

### Core Capabilities
1. **Problem Abstraction**: Strips away surface details to find essence
2. **Analogy Mining**: Searches diverse domains for similar structures
3. **Pattern Extraction**: Identifies what made other solutions work
4. **Transfer Validation**: Checks if analogies actually fit
5. **Adaptation**: Modifies patterns for new context

## Configuration Parameters

### Model Settings
```json
{
  "model": "claude-sonnet-4-5",
  "temperature": 0.7,
  "max_tokens": 8000,
  "analogy_domains": [
    "games", "UI_design", "real_world_systems",
    "mathematics", "biology", "physics"
  ]
}
```

### Temperature Rationale
**0.7** - Higher temperature for creative cross-domain connections

### System Prompt
```
You are an Analogical Reasoning specialist. Find successful patterns
from other domains and adapt them to current problems.

Process:
1. ABSTRACT: What is the STRUCTURE of this problem? (not surface details)
2. FIND ANALOGIES: What OTHER situations share this structure?
3. ANALYZE: How were those situations handled successfully?
4. TRANSFER: What patterns can we borrow?
5. ADAPT: How do we modify for our specific context?

Good analogies share STRUCTURE not surface features.
Bad analogy: "Boss is like chess because both are games"
Good analogy: "Boss states are like traffic lights (discrete states with clear transitions)"

Always check: Does the analogy HELP or just sound clever?
```

## Input/Output Schema

### Input Schema
```json
{
  "type": "object",
  "required": ["problem"],
  "properties": {
    "problem": "string",
    "current_context": "object",
    "analogy_domains": "array",
    "abstraction_level": {
      "enum": ["concrete", "structural", "abstract"]
    }
  }
}
```

### Output Schema
```json
{
  "type": "object",
  "properties": {
    "problem_abstraction": "string",
    "analogies": {
      "type": "array",
      "items": {
        "domain": "string",
        "analogy": "string",
        "structural_similarity": "string",
        "solution_pattern": "string",
        "relevance_score": "number"
      }
    },
    "transferred_patterns": "array",
    "adaptation_notes": "string"
  }
}
```

## Integration Hooks

### Message Bus Topics
```
SUBSCRIBE:
  - bmad.design.need_inspiration
  - bmad.build.stuck_on_problem
  - bmad.analyze.novel_situation

PUBLISH:
  - bmad.analogy.patterns_found
  - bmad.analogy.solution_adapted
```

## Performance Metrics

### Primary KPIs
1. **Analogy Relevance**: ≥ 75% of analogies are structurally similar
2. **Pattern Transfer Success**: ≥ 65% of transferred patterns work
3. **Novel Solution Rate**: ≥ 40% bring new perspectives

## Example Output

```
PROBLEM: How to visualize complex familiar stat bonuses clearly?

═══════════════════════════════════════════════════════════════
ABSTRACTION
═══════════════════════════════════════════════════════════════

Surface Problem: "Show familiar stats (HP+10, DMG+5, Crit+3%) in small card"

Abstract Structure:
"Display multidimensional data in compact space while maintaining readability
and instant comprehension"

Key Constraints:
- Limited space (small card)
- Multiple dimensions (4-5 stats)
- Need instant recognition (no reading required)
- Hierarchy (some stats more important)

═══════════════════════════════════════════════════════════════
ANALOGIES
═══════════════════════════════════════════════════════════════

ANALOGY 1: Weather App Icons [HIGHLY RELEVANT: 90%]
Domain: Mobile UI Design
Similarity: Must show temperature, precipitation, wind in tiny space
Solution Pattern:
  - Primary info: Large (temperature number)
  - Secondary info: Icons (cloud, rain)
  - Tertiary info: Small text (wind speed)
  - Color coding: Weather condition (sunny=yellow, rain=blue)

Transfer to Familiar Cards:
  - Primary stat: Large text ("+10 HP" if HP familiar)
  - Secondary stats: Small icons (⚔️ for damage, ✨ for crit)
  - Rarity: Color-coded border
  - Instant recognition: Icon + number pairs

ANALOGY 2: RPG Item Tooltips [RELEVANT: 85%]
Domain: Game UI
Similarity: Show complex item stats in compact tooltip
Solution Pattern:
  - Icon system: Standard icons for each stat type
  - Green/red coloring: Positive/negative effects
  - Grouping: Related stats grouped
  - Hierarchy: Primary effects larger/first

Transfer to Familiar Cards:
  - Use consistent icon language (❤️ HP, ⚔️ DMG, ✨ CRIT)
  - All bonuses = green text (always positive)
  - Group defensive (HP) vs offensive (DMG, CRIT)

ANALOGY 3: Nutrition Labels [RELEVANT: 70%]
Domain: Information Design
Similarity: Standard format for multidimensional nutritional data
Solution Pattern:
  - Standardized layout: Everyone knows where to look
  - Units explicit: "10g protein" not just "10"
  - % Daily Value: Relative scale
  - Bold important info

Transfer to Familiar Cards:
  - Standardized card layout: Stats always in same position
  - Units explicit: "+10 HP" not just "+10"
  - Consider showing % boost: "+10 HP (10% boost)"
  - Bold primary bonus

ANALOGY 4: Dashboard Gauges [RELEVANT: 60%]
Domain: Data Visualization
Similarity: Show multiple metrics in glanceable format
Solution Pattern:
  - Radial charts: Show % fill
  - Color zones: Green/yellow/red
  - Aggregation: Single "health score"

Transfer (PARTIAL):
  - Could use small progress bars for stat contributions
  - Could show "familiar power level" aggregate
  - Color rarity borders as visual hierarchy

═══════════════════════════════════════════════════════════════
TRANSFERRED PATTERNS
═══════════════════════════════════════════════════════════════

PATTERN 1: Icon + Number Pairs (from Weather + RPG)
```
❤️ +10    ⚔️ +5    ✨ +3%
```
- Icons provide instant category recognition
- Numbers show magnitude
- Compact horizontal layout

PATTERN 2: Visual Hierarchy (from all analogies)
```
┌─────────────────────┐
│ [Rarity Color Border]│
│                      │
│   🦅 Phoenix         │ <- Name (medium)
│                      │
│   ❤️ +50  ⚔️ +10    │ <- Primary stats (large)
│   ✨ +10%           │
│                      │
│   [EPIC]             │ <- Rarity (small)
└──────────────────────┘
```

PATTERN 3: Consistent Layout (from Nutrition Labels)
- Every card: Name at top
- Every card: Stats in middle (same order always)
- Every card: Rarity at bottom
- Users learn the pattern once, applies everywhere

PATTERN 4: Color Coding (from Weather)
- Rarity border: Immediate visual classification
- Stat icons: Consistent meaning across all cards
- Green text: All bonuses (positive reinforcement)

═══════════════════════════════════════════════════════════════
ADAPTATION NOTES
═══════════════════════════════════════════════════════════════

Java-Only Implementation:
```java
private VBox createFamiliarCard(Familiar familiar) {
    VBox card = new VBox(5);

    // Name (from all patterns: clear title)
    Label name = new Label(familiar.getName());
    name.setStyle("-fx-font-weight: bold;");

    // Stats with icons (Weather + RPG pattern)
    HBox statsBox = new HBox(10);
    if (familiar.getHpBonus() > 0) {
        statsBox.getChildren().add(
            new Label("❤️ +" + familiar.getHpBonus()));
    }
    if (familiar.getDamageBonus() > 0) {
        statsBox.getChildren().add(
            new Label("⚔️ +" + familiar.getDamageBonus()));
    }
    // ... etc

    // Rarity indicator (Weather pattern: color coding)
    card.setStyle("-fx-border-color: " +
        familiar.getRarity().getColorHex() + ";");

    card.getChildren().addAll(name, statsBox);
    return card;
}
```

WHY THESE ANALOGIES WORK:
- Weather apps: Proven solution to compact multi-dimensional display
- RPG tooltips: Familiar to game players
- Nutrition labels: Universal understanding of standardized layouts
- Dashboard gauges: Good for showing metrics at a glance

VALIDATION:
- Show mockup to 3-5 users
- Ask: "What does this familiar do?" (should answer instantly)
- Ask: "Which is better, this or that?" (should compare quickly)
- Measure recognition time (target: < 2 seconds to understand card)
```

## Circuit Breaker Configuration
```json
{
  "failure_threshold": 4,
  "timeout_ms": 40000,
  "reset_timeout_ms": 60000
}
```

## Version
v1.0.0 (2025-11-10)
