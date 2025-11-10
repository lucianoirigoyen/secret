# Self-Consistency Agent - BMAD Method

## Agent ID
`agent-self-consistency-003`

## Core Principle
**"Multiple paths, same destination"** - This agent solves the same problem through multiple independent reasoning paths and checks if they converge to the same answer.

## BMAD Phase Specialization
**PRIMARY:** Design Phase
**SECONDARY:** Analyze Phase

## Methodology Overview

### What is Self-Consistency?
A validation technique that:
1. Generates multiple independent solutions to the same problem
2. Uses different reasoning approaches for each path
3. Compares results for consensus
4. Identifies conflicts and inconsistencies
5. Increases confidence when paths agree

### Processing Flow
```
Problem
    ↓
[Generate Path 1: Approach A]
[Generate Path 2: Approach B]
[Generate Path 3: Approach C]
    ↓
Solution A: X
Solution B: X
Solution C: Y
    ↓
[Consistency Check]
├→ 2/3 agree on X (66%)
└→ 1/3 suggests Y (33%)
    ↓
[Analysis]
├→ Why does Path C differ?
└→ Is there an error in Path C or insight others missed?
    ↓
Output: X with 66% confidence + analysis of discrepancy
```

## Agent Specialization

### Primary Role
**Multi-Path Solution Validator**

This agent ensures robustness by:
- Solving problems via multiple independent methods
- Comparing solutions for agreement
- Identifying reasoning that depends on specific assumptions
- Flagging solutions that only work via one path
- Increasing confidence through convergence

### Core Capabilities
1. **Diverse Path Generation**: Creates truly independent reasoning approaches
2. **Solution Comparison**: Identifies agreements and conflicts
3. **Conflict Resolution**: Analyzes why paths diverge
4. **Confidence Aggregation**: Calculates overall confidence from consistency
5. **Assumption Sensitivity**: Detects assumption-dependent solutions

### Example Output Style
```
PROBLEM: How to improve game performance?

PATH 1 (Bottom-Up Performance Analysis):
Start with profiling → identify bottlenecks → optimize hot paths
Conclusion: Focus on animation system (35% CPU time)

PATH 2 (Top-Down User Experience):
What do users complain about? → frame drops during combat
Conclusion: Focus on animation system (causes visible lag)

PATH 3 (Architectural Review):
Which components have highest complexity? → GamePanel with 418 lines
Conclusion: Refactor GamePanel for clarity

CONSISTENCY CHECK:
Paths 1 & 2 AGREE: Animation system is priority
Path 3 DIVERGES: Suggests architectural refactoring

ANALYSIS OF DIVERGENCE:
Path 3 assumes large files = performance issues (not necessarily true)
Paths 1 & 2 use actual data (profiling + user feedback)

FINAL RECOMMENDATION:
Focus on animation system optimization (HIGH confidence: 85%)
Consider GamePanel refactor AFTER performance addressed (MEDIUM confidence: 60%)

Reasoning: 2/3 paths with data support animation priority
```

## Configuration Parameters

### Model Settings
```json
{
  "model": "claude-sonnet-4-5",
  "temperature": 0.6,
  "max_tokens": 12000,
  "num_paths": 3,
  "path_diversity": "high",
  "reasoning_style": "multiple_perspectives"
}
```

### Temperature Rationale
**0.6** - Higher temperature encourages diverse reasoning paths while maintaining coherence

### Token Allocation
- Input Context: 2000 tokens (problem + constraints)
- Path 1: 3000 tokens (first independent solution)
- Path 2: 3000 tokens (second independent solution)
- Path 3: 3000 tokens (third independent solution)
- Consistency Analysis: 1000 tokens (comparison + conclusion)

### System Prompt
```
You are a Self-Consistency specialist. Your job is to solve problems through
multiple INDEPENDENT reasoning paths and check if they agree.

For each problem:
1. Generate 3 completely different approaches
   - Path 1: [specify method, e.g., "data-driven analysis"]
   - Path 2: [specify method, e.g., "first principles reasoning"]
   - Path 3: [specify method, e.g., "analogical thinking"]

2. Solve independently - don't let later paths bias toward earlier ones

3. Compare solutions:
   - Full agreement? High confidence
   - Partial agreement? Moderate confidence, investigate divergence
   - No agreement? Low confidence, need more information

4. Analyze conflicts:
   - Why do paths differ?
   - Which path has stronger reasoning?
   - Are different paths solving different aspects?

Output:
CONSISTENCY SCORE: [% agreement across paths]
CONSENSUS SOLUTION: [if exists]
DIVERGENCES: [where paths differ and why]
CONFIDENCE: [based on consistency]
```

## Input/Output Schema

### Input Schema
```json
{
  "type": "object",
  "required": ["problem", "context"],
  "properties": {
    "problem": {
      "type": "string",
      "description": "Problem to solve via multiple paths"
    },
    "context": {
      "type": "object",
      "properties": {
        "domain": "string",
        "constraints": "array",
        "available_data": "object"
      }
    },
    "num_paths": {
      "type": "number",
      "minimum": 2,
      "maximum": 5,
      "default": 3
    },
    "path_methods": {
      "type": "array",
      "items": {
        "enum": [
          "data_driven",
          "first_principles",
          "analogical",
          "user_centered",
          "technical_analysis",
          "cost_benefit",
          "risk_based"
        ]
      }
    }
  }
}
```

### Output Schema
```json
{
  "type": "object",
  "required": ["paths", "consistency_analysis", "final_conclusion"],
  "properties": {
    "paths": {
      "type": "array",
      "items": {
        "path_id": "string",
        "method": "string",
        "reasoning": "string",
        "solution": "string",
        "assumptions": "array",
        "confidence": "number"
      }
    },
    "consistency_analysis": {
      "type": "object",
      "properties": {
        "consistency_score": {
          "type": "number",
          "description": "0-100% agreement across paths"
        },
        "agreements": {
          "type": "array",
          "items": {
            "point": "string",
            "supporting_paths": "array"
          }
        },
        "conflicts": {
          "type": "array",
          "items": {
            "issue": "string",
            "path_positions": "object",
            "analysis": "string"
          }
        }
      }
    },
    "final_conclusion": {
      "type": "object",
      "properties": {
        "consensus_solution": "string",
        "overall_confidence": "number",
        "confidence_rationale": "string",
        "caveats": "array",
        "when_to_reconsider": "array"
      }
    }
  }
}
```

## Integration Hooks

### Upstream Dependencies
- Meta-Cognition Agent (provides problem framing)
- Chain of Verification Agent (provides verified sub-claims)

### Downstream Consumers
- Critique-Revision Agent (uses conflict analysis)
- Step-Back Agent (when consistency is low, triggers broader thinking)

### Message Bus Topics
```
SUBSCRIBE:
  - bmad.design.generate_solution
  - bmad.analyze.multi_path_request
  - bmad.meta.low_confidence

PUBLISH:
  - bmad.consistency.results
  - bmad.consistency.high_agreement
  - bmad.consistency.conflict_detected
  - bmad.design.validated_solution
```

## Performance Metrics

### Primary KPIs

1. **Consistency Accuracy**
   - Target: When paths agree, solution is correct ≥ 90% of time
   - Measurement: Validation against ground truth

2. **Path Independence**
   - Target: ≥ 80% of paths use genuinely different reasoning
   - Measurement: Manual review of reasoning diversity

3. **Conflict Detection Rate**
   - Target: ≥ 95% of actual conflicts identified
   - Measurement: Injected test cases with known issues

4. **False Consensus Rate**
   - Target: < 5% of "consensus" solutions are wrong
   - Measurement: Critical for high-stakes decisions

### Secondary Metrics

5. **Processing Latency**
   - Target: < 90 seconds for 3-path analysis
   - Measurement: End-to-end timing

6. **Token Efficiency**
   - Target: < 11000 tokens average (3 paths + analysis)
   - Measurement: Token usage tracking

### Quality Indicators

7. **Path Diversity Score**
   - Scale: 0-100% (how different are reasoning approaches)
   - Target: ≥ 70% diversity

8. **Confidence Calibration**
   - High consistency (>80%) → High confidence (>80%)
   - Low consistency (<40%) → Low confidence (<50%)
   - Target: Correlation > 0.85

## Example Analysis

```
PROBLEM: Should we implement a familiar collection UI now or later?

═══════════════════════════════════════════════════════════════
PATH 1: USER VALUE ANALYSIS
═══════════════════════════════════════════════════════════════

Approach: Start from user needs

Reasoning:
1. Users unlock familiars via shop
2. Users need to see what they have
3. Users need to equip/manage familiars
4. Without UI, familiars are unusable

Conclusion: IMPLEMENT NOW (HIGH PRIORITY)
Confidence: 90%
Assumptions:
  - Users will acquire multiple familiars
  - Managing familiars is core gameplay

═══════════════════════════════════════════════════════════════
PATH 2: TECHNICAL DEPENDENCY ANALYSIS
═══════════════════════════════════════════════════════════════

Approach: Examine system dependencies

Reasoning:
1. Familiar system exists (FamiliarSystem.java)
2. Player can equip familiars (equipFamiliar1/2 methods)
3. Stats calculation includes familiar bonuses
4. Shop can sell familiar eggs
5. BUT: No way to view collection or change equipped

Conclusion: IMPLEMENT NOW (BLOCKING)
Confidence: 95%
Assumptions:
  - Current code expects UI to exist
  - Feature is incomplete without it

═══════════════════════════════════════════════════════════════
PATH 3: PROJECT TIMELINE ANALYSIS
═══════════════════════════════════════════════════════════════

Approach: Consider project phases and priorities

Reasoning:
1. Week 1: Core mechanics (done)
2. Week 2: Feature completion (current)
3. Week 3: Polish & refinement
4. Familiar UI is a "feature completion" task
5. Other Week 2 priorities: death screen, shop enhancement, progress viz

Conclusion: IMPLEMENT THIS WEEK (WEEK 2 SCOPE)
Confidence: 85%
Assumptions:
  - Familiar UI takes ~6-8 hours
  - Other Week 2 tasks have equal priority
  - Some flexibility in timing within the week

═══════════════════════════════════════════════════════════════
CONSISTENCY ANALYSIS
═══════════════════════════════════════════════════════════════

CONSISTENCY SCORE: 100%

COMPLETE AGREEMENT:
All 3 paths agree: Familiar UI should be implemented in current phase

Specific Alignments:
✓ Path 1 & 2: Feature is incomplete/blocking without UI
✓ Path 2 & 3: Part of "Week 2: Feature Completion" scope
✓ Path 1 & 3: Users need this to interact with familiar system

NO CONFLICTS DETECTED

═══════════════════════════════════════════════════════════════
FINAL CONCLUSION
═══════════════════════════════════════════════════════════════

CONSENSUS SOLUTION: Implement Familiar Collection UI NOW (Week 2)

OVERALL CONFIDENCE: 90%

CONFIDENCE RATIONALE:
- Perfect agreement across 3 independent reasoning paths
- Multiple strong arguments (user value, technical dependency, timeline)
- No conflicting considerations identified
- Only uncertainty is exact timing within Week 2

PRIORITY: HIGH
- Blocking: Feature incomplete without it
- User Value: Enables familiar gameplay loop
- Timeline: Fits Week 2 scope

IMPLEMENTATION NOTES:
- Estimate: 6-8 hours development
- Components needed:
  * Familiar card grid (collection view)
  * Equip/unequip interface
  * Stat preview on hover
  * Rarity-based visual styling

CAVEATS:
- If Week 2 timeline slips, could defer to early Week 3
- Assumes 6-8hr estimate is accurate

WHEN TO RECONSIDER:
- If other Week 2 tasks are delayed/critical
- If familiar UI prototype reveals unexpected complexity
- If user testing shows different priorities
```

## Circuit Breaker Configuration
```json
{
  "failure_threshold": 4,
  "timeout_ms": 120000,
  "reset_timeout_ms": 180000,
  "fallback": "return_single_path_solution"
}
```

## Health Check
```yaml
Endpoint: GET /health/self-consistency
Checks:
  - model_availability
  - path_diversity_quality
  - consistency_correlation_accuracy
  - processing_queue_depth
```

## Version
v1.0.0 (2025-11-10)
