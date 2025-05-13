% search/search.pl

search(Actions) :-
    initial(Start),
    bfs_queue([[Start, [], []]], [], Actions).

bfs_queue([[Room, Keys, RevActions] | Rest], Visited, Actions) :-
    ( treasure(Room) ->
        reverse(RevActions, Actions)
    ;
        findall([Next, NewKeys, NewRevActions],
                ( adjacent(Room, Next, Type),
                  ( ( Type = unlocked,
                      NewRevActions = [move(Room, Next) | RevActions],
                      ( key(Next, KeyColor) 
                        -> add_key(KeyColor, Keys, NewKeys)
                        ; NewKeys = Keys
                      )
                    )
                  ; ( Type = locked(Color),
                      member(Color, Keys),
                      NewRevActions = [move(Room, Next), unlock(Color) | RevActions],
                      ( key(Next, KeyColor) 
                        -> add_key(KeyColor, Keys, NewKeys)
                        ; NewKeys = Keys
                      )
                    )
                  ),
                  \+ member([Next, NewKeys], Visited)
                ),
                NextStates),
        append(Rest, NextStates, NewQueue),
        bfs_queue(NewQueue, [[Room, Keys] | Visited], Actions)
    ).

bfs_queue([], _, _) :- fail.

adjacent(Room, Next, unlocked) :- door(Room, Next).
adjacent(Room, Next, unlocked) :- door(Next, Room).
adjacent(Room, Next, locked(Color)) :- locked_door(Room, Next, Color).
adjacent(Room, Next, locked(Color)) :- locked_door(Next, Room, Color).

add_key(Key, Keys, NewKeys) :-
    ( memberchk(Key, Keys) 
      -> NewKeys = Keys
      ; sort([Key | Keys], NewKeys)
    ).