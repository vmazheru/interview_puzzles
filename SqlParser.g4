parser grammar SqlParser;

options { 
  tokenVocab=SqlLexer;
}

@parser::members {
    static enum Keyword {
      ALL,
      AND,
      AS,
      ASC,
      BETWEEN,
      BY,
      CASE,
      CAST,
      COLLATE,
      CROSS,
      DELETE,
      DESC,
      DISTINCT,
      ELSE,
      END,
      ESCAPE,
      EXISTS,
      FROM,
      GROUP,
      HAVING,
      IN,
      INNER,
      INSERT,
      INTERSECT,
      INTO,
      IS,
      JOIN,
      LEFT,
      LIKE,
      LIMIT,
      MINUS,
      NATURAL,
      NOT,
      NULL,
      ON,
      OR,
      ORDER,
      OUTER,
      REGEXP,
      RETURNING,
      RIGHT,
      SELECT,
      SET,
      THEN,
      UNION,
      UPDATE,
      USING,
      VALUES,
      WHEN,
      WHERE,
      TRUE,
      FALSE
    }

    static boolean isKeyword(String s) {
      try {
        Keyword.valueOf(s.toUpperCase());
      } catch (IllegalArgumentException e) {
        return false;
      }
      return true;
    }
}

statement
 : selectStatement
 | insertStatement
 | updateStatement
 | deleteStatement
 | otherStatement
 ; 


//////////////////// Select Statement /////////////////////

selectStatement : simpleSelectStatement (setOperator simpleSelectStatement)* orderBy? limit?;

setOperator
  : UNION (DISTINCT|ALL)?
  | INTERSECT
  | MINUS;

simpleSelectStatement : select (from join* where? groupBy?)?;

select   : SELECT selectList;
from     : FROM tableOrSubQuery (COMMA tableOrSubQuery)*;
join     : joinOperator tableOrSubQuery joinConstraint?;
where    : WHERE expression;
groupBy  : GROUP BY expressionList (having)?;
orderBy  : ORDER BY orderByListItem (COMMA orderByListItem)*;
limit    : LIMIT (integerLiteral | bindParameter);   

tableOrSubQuery : table (AS? tableAlias)? | subQuery;
subQuery  : OPEN selectStatement CLOSE AS? tableAlias;

joinOperator : (NATURAL | (LEFT|RIGHT) OUTER? | INNER | CROSS )? JOIN;

joinConstraint
 : ON expression
 | USING OPEN expression ( COMMA expression )* CLOSE
 ;

star                  : STAR;
tableDotStar          : table DOT STAR;
tableAlias            : identifier;
columnAlias           : identifier;
columnWithAlias       : (column|expression) (AS? columnAlias)?;

selectItems : (columnWithAlias|tableDotStar) (COMMA (columnWithAlias|tableDotStar))*;

selectList: (DISTINCT|ALL)? (
   star
 | selectItems
 | star COMMA selectItems
);

having : HAVING expression;

orderByListItem : expression (COLLATE collation)? (ASC|DESC)?;
 
 
//////////////////// Insert Statement /////////////////////
 
insertStatement : INSERT INTO? table insertColumnList? insertValues returning?;

insertValues
 : VALUES valuesList
 | selectStatement
 | OPEN selectStatement CLOSE
 ;

insertColumnList : OPEN (tableDotStar|(column (COMMA column)*)) CLOSE;
valuesList : values (COMMA values)*;
values : OPEN expressionList CLOSE;
returning : RETURNING column;

//////////////////// Update Statement /////////////////////

updateStatement : UPDATE table setColumns where?;
setColumns : SET expression (',' expression)*; 



//////////////////// Delete Statement /////////////////////

deleteStatement : DELETE FROM table where?;


//////////////////// Other Statements /////////////////////

otherStatement : ~(SELECT|INSERT|UPDATE|DELETE) (~SCOL)+;


/////////////////// Expressions ///////////////////

expressionListOrStar : ((DISTINCT|ALL)? expressionList) | STAR;

function : identifier OPEN expressionListOrStar? CLOSE;

expressionList : expression (COMMA expression)*;

expression
 : literal
 | column
 | cast
 | caseExpression
 | function
 | unaryOperator expression
 | expression concatOperator expression
 | expression multiplicativeOperator expression
 | expression additiveOperator expression
 | expression binaryOperator expression
 | expression equalityOperator expression
 | expression comparisonOperator expression
 | expression matchOperator expression
 | expression isOperator expression
 | expression AND expression
 | expression OR expression
 | column betweenOperator
 | column inOperator
 | OPEN expression CLOSE
 ;
 
cast           : CAST OPEN expression AS type CLOSE;
//between        : 
//in             : NOT? IN OPEN expressionList CLOSE;
caseExpression : CASE expression? (WHEN expression THEN expression)+ (ELSE expression)? END;


/////////////////// Identifiers ///////////////////

identifier : {!isKeyword(getCurrentToken().getText())}? IDENTIFIER;

database  : identifier;
table     : (database DOT)? identifier;
column    : (table DOT)? identifier;
collation : identifier;
type      : identifier (OPEN INTEGER CLOSE | OPEN INTEGER COMMA INTEGER CLOSE)?;


/////////////////// Operators ///////////////////

unaryOperator          : MINUS_OP | PLUS | TILDE | NOT;
concatOperator         : PIPE2;
multiplicativeOperator : STAR | DIV | MOD;
additiveOperator       : PLUS | MINUS_OP;

binaryOperator         : SHIFT_LEFT | SHIFT_RIGHT | AMP | PIPE;
equalityOperator       : EQ1 | EQ2 | NOT_EQ1 | NOT_EQ2;
comparisonOperator     : LT | LT_EQ | GT | GT_EQ;
matchOperator          : (NOT? LIKE | NOT? REGEXP) (ESCAPE STRING)?;
inOperator             : NOT? IN OPEN expressionList CLOSE;
betweenOperator        : NOT? BETWEEN expression AND expression;
isOperator             : IS | IS NOT;


/////////////////// Literals ///////////////////

literal : integerLiteral | realLiteral | stringLiteral | nullLiteral | trueLiteral | falseLiteral | bindParameter;

integerLiteral : INTEGER;
realLiteral    : REAL;
stringLiteral  : STRING;
nullLiteral    : NULL;
trueLiteral    : TRUE;
falseLiteral   : FALSE;
bindParameter  : (QUESTION | DOLLAR INTEGER);