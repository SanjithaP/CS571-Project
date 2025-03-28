import java.util.EnumMap;
import java.util.Map;
import java.lang.reflect.Field;

public class CompilerFrontendImpl extends CompilerFrontend {

    public CompilerFrontendImpl() {
        super();
    }

    public CompilerFrontendImpl(boolean debug_) {
        super(debug_);
    }

    @Override
    protected void init_lexer() {
        // Construct finite automata for each token according to the specification.

        // NUM: [0-9]*\.[0-9]+
        AutomatonImpl numAutomaton = new AutomatonImpl();
        numAutomaton.addState(0, true, false);  // state 0: start state
        numAutomaton.addState(1, false, false);   // state 1: after dot
        numAutomaton.addState(2, false, true);     // state 2: accepting state
        // From state 0: loop on digits (optional part)
        for (char d = '0'; d <= '9'; d++) {
            numAutomaton.addTransition(0, d, 0);
        }
        // Transition on dot from state 0 to state 1
        numAutomaton.addTransition(0, '.', 1);
        // From state 1: require at least one digit to go to state 2, then loop on digits
        for (char d = '0'; d <= '9'; d++) {
            numAutomaton.addTransition(1, d, 2);
            numAutomaton.addTransition(2, d, 2);
        }

        // PLUS: \+
        AutomatonImpl plusAutomaton = new AutomatonImpl();
        plusAutomaton.addState(0, true, false);
        plusAutomaton.addState(1, false, true);
        plusAutomaton.addTransition(0, '+', 1);

        // MINUS: -
        AutomatonImpl minusAutomaton = new AutomatonImpl();
        minusAutomaton.addState(0, true, false);
        minusAutomaton.addState(1, false, true);
        minusAutomaton.addTransition(0, '-', 1);

        // TIMES: \*
        AutomatonImpl timesAutomaton = new AutomatonImpl();
        timesAutomaton.addState(0, true, false);
        timesAutomaton.addState(1, false, true);
        timesAutomaton.addTransition(0, '*', 1);

        // DIV: /
        AutomatonImpl divAutomaton = new AutomatonImpl();
        divAutomaton.addState(0, true, false);
        divAutomaton.addState(1, false, true);
        divAutomaton.addTransition(0, '/', 1);

        // LPAREN: \(
        AutomatonImpl lparenAutomaton = new AutomatonImpl();
        lparenAutomaton.addState(0, true, false);
        lparenAutomaton.addState(1, false, true);
        lparenAutomaton.addTransition(0, '(', 1);

        // RPAREN: \)
        AutomatonImpl rparenAutomaton = new AutomatonImpl();
        rparenAutomaton.addState(0, true, false);
        rparenAutomaton.addState(1, false, true);
        rparenAutomaton.addTransition(0, ')', 1);

        // WHITE_SPACE: (' ' | '\n' | '\r' | '\t')+
        AutomatonImpl wsAutomaton = new AutomatonImpl();
        wsAutomaton.addState(0, true, false);
        wsAutomaton.addState(1, false, true);
        wsAutomaton.addTransition(0, ' ', 1);
        wsAutomaton.addTransition(0, '\n', 1);
        wsAutomaton.addTransition(0, '\r', 1);
        wsAutomaton.addTransition(0, '\t', 1);
        wsAutomaton.addTransition(1, ' ', 1);
        wsAutomaton.addTransition(1, '\n', 1);
        wsAutomaton.addTransition(1, '\r', 1);
        wsAutomaton.addTransition(1, '\t', 1);

        // Build the mapping from TokenType to Automaton using an EnumMap.
        Map<TokenType, Automaton> automata = new EnumMap<>(TokenType.class);
        automata.put(TokenType.NUM, numAutomaton);
        automata.put(TokenType.PLUS, plusAutomaton);
        automata.put(TokenType.MINUS, minusAutomaton);
        automata.put(TokenType.TIMES, timesAutomaton);
        automata.put(TokenType.DIV, divAutomaton);
        automata.put(TokenType.LPAREN, lparenAutomaton);
        automata.put(TokenType.RPAREN, rparenAutomaton);
        automata.put(TokenType.WHITE_SPACE, wsAutomaton);

        // Create the lexer using its no-argument constructor.
        lex = new LexerImpl();

        // Use reflection to set the automata mapping in LexerImpl.
        try {
            Field field = LexerImpl.class.getDeclaredField("automata");
            field.setAccessible(true);
            field.set(lex, automata);
        } catch (Exception e) {
            throw new RuntimeException("Unable to set automata mapping in LexerImpl", e);
        }
    }
}