import java.util.*;

public class AutomatonImpl implements Automaton {
    // All states added to the automaton.
    private Set<Integer> states;
    // Transitions: from state -> (input symbol -> set of next states)
    private Map<Integer, Map<Character, Set<Integer>>> transitions;
    // Start states and accepting states.
    private Set<Integer> startStates;
    private Set<Integer> acceptStates;
    // Current states during execution.
    private Set<Integer> currentStates;

    public AutomatonImpl() {
        states = new HashSet<>();
        transitions = new HashMap<>();
        startStates = new HashSet<>();
        acceptStates = new HashSet<>();
        currentStates = new HashSet<>();
    }

    @Override
    public void addState(int s, boolean is_start, boolean is_accept) {
        states.add(s);
        // Ensure there is an entry in the transitions map.
        if (!transitions.containsKey(s)) {
            transitions.put(s, new HashMap<>());
        }
        if (is_start) {
            startStates.add(s);
        }
        if (is_accept) {
            acceptStates.add(s);
        }
    }

    @Override
    public void addTransition(int s_initial, char label, int s_final) {
        // If the states haven't been explicitly added, add them as non-start, non-accept.
        if (!states.contains(s_initial)) {
            addState(s_initial, false, false);
        }
        if (!states.contains(s_final)) {
            addState(s_final, false, false);
        }
        Map<Character, Set<Integer>> trans = transitions.get(s_initial);
        if (trans == null) {
            trans = new HashMap<>();
            transitions.put(s_initial, trans);
        }
        Set<Integer> destStates = trans.get(label);
        if (destStates == null) {
            destStates = new HashSet<>();
            trans.put(label, destStates);
        }
        destStates.add(s_final);
    }

    @Override
    public void reset() {
        // Reset current states to the set of start states.
        currentStates = new HashSet<>(startStates);
    }

    @Override
    public void apply(char input) {
        Set<Integer> newStates = new HashSet<>();
        // For each current state, collect all possible transitions with the input.
        for (int state : currentStates) {
            Map<Character, Set<Integer>> trans = transitions.get(state);
            if (trans != null) {
                Set<Integer> nextStates = trans.get(input);
                if (nextStates != null) {
                    newStates.addAll(nextStates);
                }
            }
        }
        currentStates = newStates;
    }

    @Override
    public boolean accepts() {
        // Returns true if any of the current states is an accepting state.
        for (int state : currentStates) {
            if (acceptStates.contains(state)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean hasTransitions(char label) {
        // Check if any current state has a transition labeled with the given input.
        for (int state : currentStates) {
            Map<Character, Set<Integer>> trans = transitions.get(state);
            if (trans != null && trans.containsKey(label) && !trans.get(label).isEmpty()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Automaton:\n");
        sb.append("States: ").append(states).append("\n");
        sb.append("Start States: ").append(startStates).append("\n");
        sb.append("Accepting States: ").append(acceptStates).append("\n");
        sb.append("Transitions:\n");
        for (int from : transitions.keySet()) {
            Map<Character, Set<Integer>> trans = transitions.get(from);
            for (char label : trans.keySet()) {
                for (int to : trans.get(label)) {
                    sb.append(from)
                      .append(" --")
                      .append(label)
                      .append("--> ")
                      .append(to)
                      .append("\n");
                }
            }
        }
        return sb.toString();
    }
}