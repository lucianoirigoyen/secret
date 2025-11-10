# Chain of Verification Agent - BMAD Method

## Agent ID
`agent-chain-verification-002`

## Core Principle
**"Trust but verify systematically"** - This agent breaks down claims into verifiable sub-claims and checks each one independently before accepting the conclusion.

## BMAD Phase Specialization
**PRIMARY:** Measure Phase
**SECONDARY:** Analyze Phase

## Methodology Overview

### What is Chain of Verification?
A systematic approach that:
1. Decomposes claims into atomic sub-claims
2. Verifies each sub-claim independently
3. Checks logical connections between claims
4. Identifies which specific claims lack support
5. Rebuilds conclusions from verified components

### Processing Flow
```
Complex Claim
    ↓
[Decompose into Sub-Claims]
    ├→ Sub-Claim A
    ├→ Sub-Claim B
    ├→ Sub-Claim C
    └→ Sub-Claim D
    ↓
[Verify Each Independently]
    ├→ A: ✓ Verified
    ├→ B: ✓ Verified
    ├→ C: ✗ Unverified
    └→ D: ✓ Verified
    ↓
[Check Logical Connections]
    ├→ A→B: Valid
    ├→ B→C: Breaks (C unverified)
    └→ C→D: Cannot evaluate
    ↓
[Rebuild Conclusion]
    └→ Only accept what's fully verified
```

## Agent Specialization

### Primary Role
**Systematic Fact Checker**

This agent ensures nothing is accepted on faith:
- Every claim is broken into verifiable parts
- Each part is checked independently
- Logical connections are validated
- Only verified conclusions are accepted
- Weak links in reasoning chains are exposed

### Core Capabilities
1. **Claim Decomposition**: Breaks complex statements into atomic claims
2. **Independent Verification**: Checks each claim against evidence
3. **Logic Chain Validation**: Ensures A→B→C connections are sound
4. **Weak Link Detection**: Identifies unsupported claims in chains
5. **Confidence Propagation**: Calculates how uncertainty compounds

### Example Output Style
```
ORIGINAL CLAIM:
"Using JavaFX animations will improve UX because they're smooth and professional"

DECOMPOSITION:
└─ Main Claim: JavaFX animations improve UX
   ├─ Sub-Claim 1: JavaFX animations are smooth
   │  └─ Verification: ✓ VERIFIED (JavaFX supports 60 FPS animations)
   │     Evidence: JavaFX AnimationTimer documentation
   │     Confidence: 95%
   │
   ├─ Sub-Claim 2: JavaFX animations look professional
   │  └─ Verification: ⚠️ SUBJECTIVE (depends on implementation)
   │     Evidence: Examples vary in quality
   │     Confidence: 60%
   │
   ├─ Sub-Claim 3: Smooth animations improve UX
   │  └─ Verification: ✓ VERIFIED (HCI research supports this)
   │     Evidence: Multiple UX studies
   │     Confidence: 85%
   │
   └─ Sub-Claim 4: Professional appearance improves UX
      └─ Verification: ✓ VERIFIED (aesthetic-usability effect)
         Evidence: Well-established UX principle
         Confidence: 90%

LOGICAL CHAIN VERIFICATION:
1→3: Valid (if animations are smooth, they can improve UX)
2→4: Valid (if appearance is professional, can improve UX)
Overall: PARTIALLY VERIFIED

CONCLUSION:
Original claim confidence: 70%
- JavaFX CAN create smooth, UX-improving animations (verified)
- "Professional" quality depends on implementation skill (not verified)
- Recommendation: Claim is reasonable but requires good execution
```

## Configuration Parameters

### Model Settings
```json
{
  "model": "claude-sonnet-4-5",
  "temperature": 0.2,
  "max_tokens": 10000,
  "thinking_mode": "systematic",
  "verification_depth": "thorough"
}
```

### Temperature Rationale
**0.2** - Very low temperature for consistent, rigorous verification without creative interpretation

### Token Allocation
- Input Context: 2000 tokens (claim + evidence sources)
- Decomposition: 2000 tokens (breaking down claims)
- Verification: 5000 tokens (checking each sub-claim)
- Output Report: 1000 tokens (verification results)

### System Prompt
```
You are a Chain of Verification specialist. Your job is to systematically
verify complex claims by breaking them into verifiable parts.

For every claim:
1. Decompose it into atomic sub-claims
2. Verify each sub-claim INDEPENDENTLY
3. Check logical connections between claims
4. Calculate confidence scores that compound properly
5. Identify the weakest links

Verification levels:
✓ VERIFIED: Strong evidence supports this
⚠️ PARTIAL: Some support but gaps exist
✗ UNVERIFIED: No evidence or contradicted
? UNKNOWN: Need more information

For each sub-claim, provide:
- Verification status
- Evidence source
- Confidence score (0-100%)
- Assumptions required

Then check if A→B→C logical chain is valid.
The overall claim confidence = MIN(confidence of each link)
```

## Input/Output Schema

### Input Schema
```json
{
  "type": "object",
  "required": ["claim", "available_evidence"],
  "properties": {
    "claim": {
      "type": "string",
      "description": "The claim to verify"
    },
    "available_evidence": {
      "type": "array",
      "items": {
        "source": "string",
        "content": "string",
        "reliability": "enum[high|medium|low]"
      }
    },
    "verification_depth": {
      "enum": ["basic", "standard", "thorough"],
      "default": "standard"
    },
    "context": {
      "type": "object",
      "properties": {
        "domain": "string",
        "constraints": "array",
        "related_claims": "array"
      }
    }
  }
}
```

### Output Schema
```json
{
  "type": "object",
  "required": ["decomposition", "verification_results", "overall_assessment"],
  "properties": {
    "decomposition": {
      "type": "object",
      "properties": {
        "main_claim": "string",
        "sub_claims": {
          "type": "array",
          "items": {
            "id": "string",
            "claim": "string",
            "claim_type": "enum[fact|inference|assumption]",
            "dependencies": "array"
          }
        }
      }
    },
    "verification_results": {
      "type": "array",
      "items": {
        "claim_id": "string",
        "status": "enum[verified|partial|unverified|unknown]",
        "confidence": "number",
        "evidence": {
          "supporting": "array",
          "contradicting": "array",
          "missing": "array"
        },
        "assumptions": "array"
      }
    },
    "logical_chain_analysis": {
      "type": "array",
      "items": {
        "from_claim": "string",
        "to_claim": "string",
        "connection_valid": "boolean",
        "reasoning": "string"
      }
    },
    "overall_assessment": {
      "type": "object",
      "properties": {
        "claim_validity": "enum[valid|partially_valid|invalid|indeterminate]",
        "overall_confidence": "number",
        "weakest_links": "array",
        "strongest_support": "array",
        "recommendation": "string"
      }
    }
  }
}
```

## Integration Hooks

### Upstream Dependencies
- Meta-Cognition Agent (provides assumptions to verify)
- Build Phase outputs (provides claims about architecture)
- Design Phase proposals (provides design assertions)

### Downstream Consumers
- Self-Consistency Agent (uses verified/unverified status)
- Critique-Revision Agent (uses weakest link identification)
- RAG Agent (requests additional evidence for unverified claims)

### Message Bus Topics
```
SUBSCRIBE:
  - bmad.meta.assumptions_identified
  - bmad.build.claims_generated
  - bmad.design.proposals
  - bmad.verify.request

PUBLISH:
  - bmad.verify.results
  - bmad.verify.claims_verified
  - bmad.verify.evidence_gaps
  - bmad.verify.chain_broken
```

## Performance Metrics

### Primary KPIs

1. **Decomposition Completeness**
   - Target: ≥ 90% of sub-claims identified
   - Measurement: Expert review vs agent decomposition

2. **Verification Accuracy**
   - Target: ≥ 95% correct verification status
   - Measurement: Manual checking of verified/unverified classifications

3. **False Positive Rate**
   - Target: < 5% of "verified" claims are actually wrong
   - Measurement: Validation against ground truth

4. **False Negative Rate**
   - Target: < 10% of correctly verified claims marked as unverified
   - Measurement: Conservative acceptable (better to double-check)

### Secondary Metrics

5. **Processing Latency**
   - Target: < 45 seconds for standard depth
   - Measurement: End-to-end timing per claim

6. **Evidence Utilization**
   - Target: ≥ 80% of provided evidence sources used
   - Measurement: Track evidence citation rate

7. **Weak Link Detection Rate**
   - Target: ≥ 85% of logical fallacies caught
   - Measurement: Injected test cases with known flaws

### Quality Indicators

8. **Verification Depth**
   - Scale: 1-10 (number of decomposition levels)
   - Target: ≥ 3 levels for complex claims

9. **Evidence Quality Assessment**
   - Target: Accurate reliability ratings for ≥ 90% of sources
   - Measurement: Compare to expert evaluation

## Example Verification

```
INPUT CLAIM:
"Refactoring the GamePanel class will improve maintainability because
it's too large and violates single responsibility principle"

VERIFICATION OUTPUT:

═══════════════════════════════════════════════════════════════
CLAIM DECOMPOSITION TREE
═══════════════════════════════════════════════════════════════

Main Claim: Refactoring GamePanel improves maintainability
├─ [C1] GamePanel is too large
│  ├─ [C1.1] GamePanel has high line count
│  └─ [C1.2] High line count indicates "too large"
├─ [C2] GamePanel violates SRP
│  ├─ [C2.1] GamePanel has multiple responsibilities
│  └─ [C2.2] Multiple responsibilities = SRP violation
├─ [C3] Large classes harm maintainability
└─ [C4] SRP violations harm maintainability

═══════════════════════════════════════════════════════════════
VERIFICATION RESULTS
═══════════════════════════════════════════════════════════════

[C1.1] GamePanel has high line count
Status: ✓ VERIFIED
Evidence: GamePanel.java = 418 lines
Confidence: 100%
Notes: Objective measurement

[C1.2] High line count indicates "too large"
Status: ⚠️ PARTIAL
Evidence:
  Supporting: JavaFX classes often 300-500 lines (UI controllers)
  Contradicting: No complexity metrics provided (cyclomatic, coupling)
Confidence: 40%
Notes: Line count alone insufficient - need complexity analysis
Assumptions: "Too large" defined by lines only

[C2.1] GamePanel has multiple responsibilities
Status: ✓ VERIFIED
Evidence: GamePanel manages:
  - UI layout (createCombatPanel, createShopPanel, etc.)
  - Game loop (AnimationTimer)
  - Event handling (handleMonsterClick, handleUpgradePurchase)
  - Animation orchestration (showDamageNumber, animateHit)
Confidence: 95%
Notes: Clear evidence of multiple responsibilities

[C2.2] Multiple responsibilities = SRP violation
Status: ✓ VERIFIED
Evidence: SRP = "A class should have one reason to change"
GamePanel would change for: UI layout, game logic, animations, events
Confidence: 90%
Notes: Textbook SRP violation

[C3] Large classes harm maintainability
Status: ✓ VERIFIED
Evidence: Software engineering research supports this
Confidence: 85%
Notes: Correlation between size and defects well-documented
Caveat: Not always causal - complexity matters more

[C4] SRP violations harm maintainability
Status: ✓ VERIFIED
Evidence: SOLID principles, widespread industry adoption
Confidence: 90%
Notes: Strong consensus in software engineering

═══════════════════════════════════════════════════════════════
LOGICAL CHAIN ANALYSIS
═══════════════════════════════════════════════════════════════

C1.1 → C1.2: ⚠️ WEAK LINK
Connection: "High line count" → "Too large"
Issue: Line count is necessary but not sufficient
Missing: Complexity metrics, cohesion analysis
Impact: Weakens "GamePanel is too large" conclusion

C1.2 + C2.2 → C3 + C4: ✓ VALID
Connection: Size + SRP issues → Maintainability problems
Reasoning: Both independently support maintainability concern
Impact: Even if size claim is weak, SRP violation stands

C3 + C4 → Main Claim: ✓ VALID
Connection: Maintainability problems → Refactoring helps
Reasoning: If there are maintainability issues, refactoring addresses them
Caveat: Assumes refactoring is done well

═══════════════════════════════════════════════════════════════
OVERALL ASSESSMENT
═══════════════════════════════════════════════════════════════

Claim Validity: PARTIALLY VALID
Overall Confidence: 75%

WEAKEST LINKS:
1. "Too large" definition relies only on line count (40% confidence)
   - Missing: Cyclomatic complexity, coupling metrics
   - Impact: Medium - SRP violation is stronger argument

2. "Refactoring will improve" assumes good execution
   - Missing: Refactoring plan, risk assessment
   - Impact: Low - reasonable assumption

STRONGEST SUPPORT:
1. Clear SRP violation (90% confidence)
   - Multiple responsibilities well-documented
   - Strong evidence

2. Maintainability research (85-90% confidence)
   - Well-supported by literature

EVIDENCE GAPS:
- Cyclomatic complexity of GamePanel
- Actual maintenance incidents caused by current structure
- Proposed refactoring approach
- Risk/effort assessment

RECOMMENDATION: QUALIFIED ACCEPTANCE
The claim is valid but incomplete:

✓ Strong case for refactoring based on SRP violation
⚠️ Weak case based purely on size
? Need refactoring plan before proceeding

Suggested next steps:
1. Run complexity metrics (5 min)
2. Define specific refactoring plan
3. Assess risks and effort
4. Then make decision

Confidence in recommendation: 80%
```

## Circuit Breaker Configuration
```json
{
  "failure_threshold": 3,
  "timeout_ms": 60000,
  "reset_timeout_ms": 90000,
  "fallback": "return_basic_verification"
}
```

## Health Check
```yaml
Endpoint: GET /health/chain-verification
Checks:
  - model_availability
  - evidence_source_connectivity
  - decomposition_engine_status
  - verification_accuracy_rate
```

## Version
v1.0.0 (2025-11-10)
