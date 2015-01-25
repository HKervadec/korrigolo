/* Generated By:JJTree&JavaCC: Do not edit this line. ADTParser.java */
package lu.uni.adtool.adtree;

import java.io.StringReader;
import java.io.Reader;
/** An ADTerm Grammar. */
public class ADTParser/*@bgen(jjtree)*/implements ADTParserTreeConstants, ADTParserConstants {/*@bgen(jjtree)*/
  protected static JJTADTParserState jjtree = new JJTADTParserState();/**
   * Parse a string
   */
 public ADTParser(String s)
  {
      this((Reader)(new StringReader(s)));
  }
  /** Main entry point. */
  public static void main(String args[]) {
    System.out.println("Reading from standard input...");
    try {
      ADTNode n = ADTParser.parse();
      System.out.println(n.toString());
      System.out.println("Thank you.");
    } catch (Exception e) {
      System.out.println("Oops.");
      System.out.println(e.getMessage());
      e.printStackTrace();
    }
  }

/** Main production. */
  static final public ADTNode parse() throws ParseException {
  ADTNode root;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case OP:
    case AP:
    case CP:
    case IDENTIFIER:
      root = ADTPro();
      jj_consume_token(0);
    {if (true) return root;}
      break;
    case 0:
      jj_consume_token(0);
    {if (true) return null;}
      break;
    default:
      jj_la1[0] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

/** An ADTerm of opponent type. */
  static final public ADTNode ADTOpp() throws ParseException {
 /*@bgen(jjtree) ADTOpp */
  ADTNode jjtn000 = new ADTNode(JJTADTOPP);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);Token t;
    try {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case IDENTIFIER:
        t = jj_consume_token(IDENTIFIER);
        jjtn000.setType(ADTNode.Type.LEAFO);
        jjtn000.setName(t.image);
        break;
      case OO:
      case AO:
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case OO:
          jj_consume_token(OO);
          jjtn000.setType(ADTNode.Type.OO);
          jjtn000.setName("oo");
          break;
        case AO:
          jj_consume_token(AO);
            jjtn000.setType(ADTNode.Type.AO);
            jjtn000.setName("ao");
          break;
        default:
          jj_la1[1] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
        jj_consume_token(LPAREN);
        ADTOpp();
        label_1:
        while (true) {
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case COMMA:
            ;
            break;
          default:
            jj_la1[2] = jj_gen;
            break label_1;
          }
          jj_consume_token(COMMA);
          ADTOpp();
        }
        jj_consume_token(RPAREN);
        break;
      case CO:
        jj_consume_token(CO);
        jj_consume_token(LPAREN);
        ADTOpp();
        jj_consume_token(COMMA);
        ADTPro();
        jj_consume_token(RPAREN);
        jjtn000.setType(ADTNode.Type.CO);
        jjtn000.setName("co");
        break;
      default:
        jj_la1[3] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    jjtree.closeNodeScope(jjtn000, true);
    jjtc000 = false;
    {if (true) return jjtn000;}
    } catch (Throwable jjte000) {
    if (jjtc000) {
      jjtree.clearNodeScope(jjtn000);
      jjtc000 = false;
    } else {
      jjtree.popNode();
    }
    if (jjte000 instanceof RuntimeException) {
      {if (true) throw (RuntimeException)jjte000;}
    }
    if (jjte000 instanceof ParseException) {
      {if (true) throw (ParseException)jjte000;}
    }
    {if (true) throw (Error)jjte000;}
    } finally {
    if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, true);
    }
    }
    throw new Error("Missing return statement in function");
  }

/** An ADTerm of proponent type. */
  static final public ADTNode ADTPro() throws ParseException {
 /*@bgen(jjtree) ADTPro */
  ADTNode jjtn000 = new ADTNode(JJTADTPRO);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);Token t;
    try {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case IDENTIFIER:
        t = jj_consume_token(IDENTIFIER);
       jjtn000.setType(ADTNode.Type.LEAFP);
       jjtn000.setName(t.image);
        break;
      case OP:
      case AP:
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case OP:
          jj_consume_token(OP);
          jjtn000.setType(ADTNode.Type.OP);
          jjtn000.setName("op");
          break;
        case AP:
          jj_consume_token(AP);
             jjtn000.setType(ADTNode.Type.AP);
             jjtn000.setName("ap");
          break;
        default:
          jj_la1[4] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
        jj_consume_token(LPAREN);
        ADTPro();
        label_2:
        while (true) {
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case COMMA:
            ;
            break;
          default:
            jj_la1[5] = jj_gen;
            break label_2;
          }
          jj_consume_token(COMMA);
          ADTPro();
        }
        jj_consume_token(RPAREN);
        break;
      case CP:
        jj_consume_token(CP);
        jj_consume_token(LPAREN);
        ADTPro();
        jj_consume_token(COMMA);
        ADTOpp();
        jj_consume_token(RPAREN);
       jjtn000.setType(ADTNode.Type.CP);
       jjtn000.setName("cp");
        break;
      default:
        jj_la1[6] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    jjtree.closeNodeScope(jjtn000, true);
    jjtc000 = false;
     {if (true) return jjtn000;}
    } catch (Throwable jjte000) {
    if (jjtc000) {
      jjtree.clearNodeScope(jjtn000);
      jjtc000 = false;
    } else {
      jjtree.popNode();
    }
    if (jjte000 instanceof RuntimeException) {
      {if (true) throw (RuntimeException)jjte000;}
    }
    if (jjte000 instanceof ParseException) {
      {if (true) throw (ParseException)jjte000;}
    }
    {if (true) throw (Error)jjte000;}
    } finally {
    if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, true);
    }
    }
    throw new Error("Missing return statement in function");
  }

  static private boolean jj_initialized_once = false;
  /** Generated Token Manager. */
  static public ADTParserTokenManager token_source;
  static SimpleCharStream jj_input_stream;
  /** Current token. */
  static public Token token;
  /** Next token. */
  static public Token jj_nt;
  static private int jj_ntk;
  static private int jj_gen;
  static final private int[] jj_la1 = new int[7];
  static private int[] jj_la1_0;
  static {
      jj_la1_init_0();
   }
   private static void jj_la1_init_0() {
      jj_la1_0 = new int[] {0x4581,0x60,0x800,0x4260,0x180,0x800,0x4580,};
   }

  /** Constructor with InputStream. */
  public ADTParser(java.io.InputStream stream) {
     this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public ADTParser(java.io.InputStream stream, String encoding) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser.  ");
      System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new ADTParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 7; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  static public void ReInit(java.io.InputStream stream) {
     ReInit(stream, null);
  }
  /** Reinitialise. */
  static public void ReInit(java.io.InputStream stream, String encoding) {
    try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jjtree.reset();
    jj_gen = 0;
    for (int i = 0; i < 7; i++) jj_la1[i] = -1;
  }

  /** Constructor. */
  public ADTParser(java.io.Reader stream) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser. ");
      System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    jj_input_stream = new SimpleCharStream(stream, 1, 1);
    token_source = new ADTParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 7; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  static public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jjtree.reset();
    jj_gen = 0;
    for (int i = 0; i < 7; i++) jj_la1[i] = -1;
  }

  /** Constructor with generated Token Manager. */
  public ADTParser(ADTParserTokenManager tm) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser. ");
      System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 7; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(ADTParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jjtree.reset();
    jj_gen = 0;
    for (int i = 0; i < 7; i++) jj_la1[i] = -1;
  }

  static private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      jj_gen++;
      return token;
    }
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }


/** Get the next Token. */
  static final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    jj_gen++;
    return token;
  }

/** Get the specific Token. */
  static final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  static private int jj_ntk() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  static private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
  static private int[] jj_expentry;
  static private int jj_kind = -1;

  /** Generate ParseException. */
  static public ParseException generateParseException() {
    jj_expentries.clear();
    boolean[] la1tokens = new boolean[20];
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 7; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 20; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.add(jj_expentry);
      }
    }
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = jj_expentries.get(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  /** Enable tracing. */
  static final public void enable_tracing() {
  }

  /** Disable tracing. */
  static final public void disable_tracing() {
  }

}