% parsing/parse.pl
parse(X) :- lines(X, []).

lines(S, Rem) :-
    line(S, S1),
    (   S1 = [';' | S2],
        lines(S2, Rem)
    ;   Rem = S1
    ).

line(S, Rem) :-
    num(S, S1),
    (   S1 = [',' | S2],
        line(S2, Rem)
    ;   Rem = S1
    ).

num([D | T], Rem) :-
    digit(D),
    ( num(T, Rem) ; Rem = T ).

digit(D) :- memberchk(D, ['0','1','2','3','4','5','6','7','8','9']).