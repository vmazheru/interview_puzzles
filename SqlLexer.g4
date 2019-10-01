lexer grammar SqlLexer;

SCOL        : ';';
DOT         : '.';
OPEN        : '(';
CLOSE       : ')';
COMMA       : ',';
STAR        : '*';
PLUS        : '+';
MINUS_OP    : '-'; // add _OP because there is a keyword MINUS below
TILDE       : '~';
PIPE2       : '||';
DIV         : '/';
MOD         : '%';
SHIFT_LEFT  : '<<';
SHIFT_RIGHT : '>>';
AMP         : '&';
PIPE        : '|';
LT          : '<';
LT_EQ       : '<=';
GT          : '>';
GT_EQ       : '>=';
EQ1         : '=';
EQ2         : '==';
NOT_EQ1     : '!=';
NOT_EQ2     : '<>';
QUESTION    : '?';
DOLLAR      : '$';

// Keywords

ALL       : A L L;
AND       : A N D;
AS        : A S;
ASC       : A S C;
BETWEEN   : B E T W E E N;
BY        : B Y;
CASE      : C A S E;
CAST      : C A S T;
COLLATE   : C O L L A T E;
CROSS     : C R O S S;
DELETE    : D E L E T E;
DESC      : D E S C;
DISTINCT  : D I S T I N C T;
ELSE      : E L S E;
END       : E N D;
ESCAPE    : E S C A P E;
EXISTS    : E X I S T S;
FROM      : F R O M;
GROUP     : G R O U P;
HAVING    : H A V I N G;
IN        : I N;
INNER     : I N N E R;
INSERT    : I N S E R T;
INTERSECT : I N T E R S E C T;
INTO      : I N T O;
IS        : I S;
JOIN      : J O I N;
LEFT      : L E F T;
LIKE      : L I K E;
LIMIT     : L I M I T;
MINUS     : M I N U S;
NATURAL   : N A T U R A L;
NOT       : N O T;
NULL      : N U L L;
ON        : O N;
OR        : O R;
ORDER     : O R D E R;
OUTER     : O U T E R;
REGEXP    : R E G E X P;
RETURNING : R E T U R N I N G;
RIGHT     : R I G H T;
SELECT    : S E L E C T;
SET       : S E T;
THEN      : T H E N;
UNION     : U N I O N;
UPDATE    : U P D A T E;
USING     : U S I N G;
VALUES    : V A L U E S;
WHEN      : W H E N;
WHERE     : W H E R E;
TRUE      : T R U E;
FALSE     : F A L S E;


// Literals

IDENTIFIER
 : '"' (~'"' | '""')* '"'
 | '`' (~'`' | '``')* '`'
 | '[' ~']'* ']'
 | [a-zA-Z_] [a-zA-Z_0-9]*
;

INTEGER : DIGIT+;
REAL    : DIGIT* ( '.' DIGIT* )? ( E [-+]? DIGIT+ )? | '.' DIGIT+ ( E [-+]? DIGIT+ )?;
STRING  : '\'' ( ~'\'' | '\'\'' )* '\'';

// Spaces and Comments

SPACE               : [ \t\r\n]+             -> channel(HIDDEN);
SINGLE_LINE_COMMENT : ('--' | '#') ~[\r\n]*  -> channel(HIDDEN);
MULTI_LINE_COMMENT  : '/*' .*? ('*/' | EOF)  -> channel(HIDDEN);

fragment DIGIT  : [0-9];
fragment LETTER : [a-zA-Z_];

fragment A : [aA];
fragment B : [bB];
fragment C : [cC];
fragment D : [dD];
fragment E : [eE];
fragment F : [fF];
fragment G : [gG];
fragment H : [hH];
fragment I : [iI];
fragment J : [jJ];
fragment K : [kK];
fragment L : [lL];
fragment M : [mM];
fragment N : [nN];
fragment O : [oO];
fragment P : [pP];
fragment Q : [qQ];
fragment R : [rR];
fragment S : [sS];
fragment T : [tT];
fragment U : [uU];
fragment V : [vV];
fragment W : [wW];
fragment X : [xX];
fragment Y : [yY];
fragment Z : [zZ];
