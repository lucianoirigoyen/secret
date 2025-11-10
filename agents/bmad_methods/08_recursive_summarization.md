# Recursive Summarization Agent - BMAD Method

## Agent ID
`agent-recursive-summary-008`

## Core Principle
**"Condense without losing essence"** - This agent hierarchically summarizes large amounts of information, maintaining key insights while drastically reducing cognitive load.

## BMAD Phase Specialization
**PRIMARY:** Measure Phase
**SECONDARY:** Analyze Phase

## Methodology Overview

### What is Recursive Summarization?
1. Breaks large information into chunks
2. Summarizes each chunk independently
3. Summarizes the summaries (recursive)
4. Continues until reaching manageable size
5. Maintains hierarchical access to details

### Processing Flow
```
Large Document (10,000 words)
    ↓
[Split into Chunks]
├→ Chunk 1 (2000 words)
├→ Chunk 2 (2000 words)
├→ Chunk 3 (2000 words)
├→ Chunk 4 (2000 words)
└→ Chunk 5 (2000 words)
    ↓
[Summarize Each]
├→ Summary 1 (400 words)
├→ Summary 2 (400 words)
├→ Summary 3 (400 words)
├→ Summary 4 (400 words)
└→ Summary 5 (400 words)
    ↓
[Summarize Summaries]
├→ Meta-Summary 1 (from S1+S2) (160 words)
├→ Meta-Summary 2 (from S3+S4) (160 words)
└→ Meta-Summary 3 (from S5) (80 words)
    ↓
[Final Executive Summary]
Single coherent summary (100 words)
    ↓
Hierarchical structure:
  - Executive: 100 words (read in 30s)
  - High-level: 400 words (3 min)
  - Detailed: 2000 words (15 min)
  - Full: 10000 words (reference)
```

## Agent Specialization

### Primary Role
**Information Distiller**

This agent:
- Extracts signal from noise
- Preserves critical information
- Enables quick scanning (executives love this)
- Maintains detail access when needed
- Reduces information overload

### Core Capabilities
1. **Chunk Decomposition**: Splits logically at section boundaries
2. **Key Insight Extraction**: Identifies what matters most
3. **Hierarchical Structure**: Creates layers of detail
4. **Coherence Maintenance**: Summaries read naturally
5. **Lossless Drilling**: Can dive into details without re-reading everything

## Configuration Parameters

### Model Settings
```json
{
  "model": "claude-sonnet-4-5",
  "temperature": 0.3,
  "max_tokens": 8000,
  "summarization_ratio": 0.2,
  "hierarchy_levels": 3
}
```

### Temperature Rationale
**0.3** - Low temperature for consistent, accurate summarization without hallucination

### System Prompt
```
You are a Recursive Summarization specialist. Your job is to distill
large information into hierarchical summaries.

For every document:
1. IDENTIFY key insights (what actually matters)
2. SUMMARIZE to ~20% of original length
3. MAINTAIN structure (if doc has sections, summary should too)
4. PRESERVE critical details (numbers, decisions, action items)
5. ENABLE drilling (reader can go deeper if needed)

Summarization priorities:
CRITICAL (always keep):
  - Decisions made
  - Action items
  - Key numbers/metrics
  - Problems identified
  - Recommendations

KEEP IF SPACE:
  - Context and reasoning
  - Alternatives considered
  - Trade-offs

CUT FIRST:
  - Examples (keep 1, cut redundant)
  - Repetition
  - Background (if well-known)
  - Verbose explanations

Format:
EXECUTIVE SUMMARY (1 paragraph):
[The absolute essence - what, why, so what]

KEY POINTS:
- [Most important takeaway 1]
- [Most important takeaway 2]
- [Most important takeaway 3]

DETAILS: [Hierarchical sections below]
```

## Input/Output Schema

### Input Schema
```json
{
  "type": "object",
  "required": ["content"],
  "properties": {
    "content": {
      "type": "string",
      "description": "The large document/information to summarize"
    },
    "summarization_target": {
      "enum": ["executive", "brief", "detailed"],
      "description": "How much detail to preserve"
    },
    "preserve_sections": {
      "type": "boolean",
      "default": true
    },
    "extract_action_items": {
      "type": "boolean",
      "default": true
    }
  }
}
```

### Output Schema
```json
{
  "type": "object",
  "required": ["executive_summary", "key_points", "hierarchical_details"],
  "properties": {
    "executive_summary": {
      "type": "string",
      "max_words": 150
    },
    "key_points": {
      "type": "array",
      "items": "string",
      "maxItems": 5
    },
    "action_items": {
      "type": "array",
      "items": {
        "action": "string",
        "priority": "enum[critical|high|medium|low]",
        "owner": "string",
        "deadline": "string"
      }
    },
    "hierarchical_details": {
      "type": "object",
      "properties": {
        "sections": "array",
        "original_length": "number",
        "summary_length": "number",
        "compression_ratio": "number"
      }
    },
    "metadata": {
      "type": "object",
      "properties": {
        "read_time_executive": "string",
        "read_time_full": "string",
        "information_density": "number"
      }
    }
  }
}
```

## Integration Hooks

### Upstream Dependencies
- Long documents from Build/Analyze/Design phases
- Meeting notes, research findings
- Multi-agent conversation logs

### Downstream Consumers
- Decision makers (use executive summaries)
- Other agents (use hierarchical navigation)
- Documentation systems

### Message Bus Topics
```
SUBSCRIBE:
  - bmad.document.needs_summary
  - bmad.meeting.transcript_ready
  - bmad.research.results_large

PUBLISH:
  - bmad.summary.executive_ready
  - bmad.summary.hierarchical_complete
  - bmad.summary.action_items_extracted
```

## Performance Metrics

### Primary KPIs
1. **Information Preservation**: ≥ 90% of critical info retained
2. **Compression Ratio**: 15-25% of original length (typical)
3. **Readability**: Summaries understandable without original
4. **Action Item Extraction**: ≥ 95% of action items identified

## Example Output

```
INPUT: BMAD_METHOD.md (63,151 tokens ≈ 15,000 words)

═══════════════════════════════════════════════════════════════
EXECUTIVE SUMMARY (150 words, ~45 second read)
═══════════════════════════════════════════════════════════════

The BMAD Method document provides a comprehensive plan to transform
an Idle Clicker RPG from functional prototype to professional-grade game.
The game uses Java 25 + JavaFX 21 with solid MVC architecture but needs
significant UX/UI enhancement. The document proposes a 3-week roadmap:
Week 1 focuses on foundation (CSS styling, combat visuals, damage numbers),
Week 2 completes features (familiar UI, enhanced screens, boss indicators),
and Week 3 polishes (animations, sound, testing). All enhancements use
pure JavaFX without external dependencies. Key improvements include
modern color palette, sophisticated animations, familiar collection interface,
and comprehensive visual feedback. Success metrics: 60 FPS stable, professional
UI quality, intuitive UX, 80%+ code documentation. The plan is implementable
by following detailed code examples and maintains the "Java-only" principle.

═══════════════════════════════════════════════════════════════
KEY POINTS
═══════════════════════════════════════════════════════════════

1. **Current State**: Solid architecture (MVC, clean OOP) but basic
   visuals (rectangles, inline styles, limited animations)

2. **Enhancement Strategy**: BMAD Method (Build-Measure-Analyze-Design)
   guides systematic improvement with data-driven decisions

3. **Implementation Plan**: 3-week roadmap with daily task breakdown,
   prioritized into Critical UX (Week 1), Feature Completion (Week 2),
   Polish (Week 3)

4. **Technical Approach**: Pure JavaFX 21 - no external libraries,
   uses CSS stylesheets, built-in animations, JavaFX effects

5. **Success Criteria**: 60 FPS stable performance, professional
   visual quality, complete familiar system UI, comprehensive testing

═══════════════════════════════════════════════════════════════
ACTION ITEMS (Extracted from document)
═══════════════════════════════════════════════════════════════

CRITICAL:
[ ] Extract magic numbers to GameConstants class (Day 1-2)
[ ] Create main.css stylesheet (Day 1-2)
[ ] Implement enhanced monster visual with gradients (Day 3-4)
[ ] Create new damage number system (Day 3-4)

HIGH:
[ ] Redesign shop panel with modern styling (Day 5)
[ ] Create FamiliarPanel component (Day 6-7)
[ ] Implement familiar card grid (Day 6-7)
[ ] Redesign death overlay (Day 8-9)
[ ] Add boss ability visualizations (Day 10)

MEDIUM:
[ ] Create GameAnimations utility class (Day 11-12)
[ ] Implement SoundManager (optional) (Day 13)
[ ] Add tooltips to UI elements (Day 14)
[ ] Comprehensive testing and balancing (Day 14-15)

═══════════════════════════════════════════════════════════════
HIERARCHICAL DETAILS
═══════════════════════════════════════════════════════════════

LEVEL 1: SECTION SUMMARIES (3-5 min read)

1. **Executive Summary** (Original: 800 words → 150 words)
   Project overview, current strengths/weaknesses, BMAD goals, target outcomes

2. **Current Architecture Analysis** (Original: 2,000 words → 300 words)
   Technology: Java 25, JavaFX 21, Maven
   Pattern: MVC-like with Entities/Systems/UI separation
   Strengths: Clean architecture, good OOP, solid save system
   Issues: Magic numbers, inline styles, hardcoded strings

3. **Code Quality Assessment** (Original: 1,500 words → 250 words)
   Strengths: Separation of concerns, proper encapsulation, JavaFX best practices
   Issues: 50+ magic numbers, 100+ hardcoded strings, no logging framework
   Metrics: Target 60%+ test coverage, 80%+ documentation

4. **UX/UI Enhancement Plan** (Original: 6,000 words → 800 words)
   Color Palette: Dark theme (#1a1a2e primary) with gold accents (#f4a460)
   Components: Enhanced monster visuals, gradient health bars, particle damage
   numbers, redesigned shop, familiar collection UI
   Animations: Hit effects, dodge/shield animations, critical explosions

5. **BMAD Framework** (Original: 3,000 words → 400 words)
   Build: Refactoring, CSS creation, component modularization
   Measure: Performance metrics, UX analytics, balancing data
   Analyze: Data-driven insights, pain point identification
   Design: Implementation specifications with code examples

6. **Implementation Roadmap** (Original: 2,000 words → 300 words)
   Week 1: CSS integration, combat enhancement, shop redesign
   Week 2: Familiar UI, enhanced screens, progress visualization
   Week 3: Animation library, sound (optional), testing/polish

7. **Technical Specifications** (Original: 1,500 words → 250 words)
   Component architecture diagram, data flow, save system with backup

8. **Quality Assurance** (Original: 2,000 words → 300 words)
   Unit tests (Player, Combat, Shop), Integration tests, Performance benchmarks
   Target: 60 FPS, <200MB memory, <500ms save/load

LEVEL 2: DETAILED SECTION BREAKDOWNS
[Available on request - drill into specific sections for full detail]

LEVEL 3: FULL DOCUMENT
[Original 15,000 word document with all code examples and specifications]

═══════════════════════════════════════════════════════════════
METADATA
═══════════════════════════════════════════════════════════════

Original Length: ~15,000 words (63,151 tokens)
Executive Summary: 150 words (3% compression)
Key Points + Actions: 400 words (2.7% compression)
Level 1 Summaries: 2,650 words (17.7% compression)

Estimated Read Times:
  - Executive: 45 seconds
  - Key Points: 2 minutes
  - Level 1: 10 minutes
  - Full Document: 1 hour

Information Density:
  Executive: Very High (decision-ready)
  Level 1: High (understanding-ready)
  Full: Comprehensive (implementation-ready)

Recommendation: Read Executive + Key Points (3 min), then drill into
relevant sections as needed for implementation details.
```

## Circuit Breaker Configuration
```json
{
  "failure_threshold": 3,
  "timeout_ms": 60000,
  "reset_timeout_ms": 90000
}
```

## Version
v1.0.0 (2025-11-10)
