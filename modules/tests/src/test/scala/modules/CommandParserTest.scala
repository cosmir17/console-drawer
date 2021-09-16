package modules

import modules.CommandParser._
import org.specs2.matcher.Matchers
import org.specs2.mutable.Specification

class CommandParserTest extends Specification with Matchers {

  "Parser" should {
    "not take empty space input" in {
      execute(" ") must throwA(new IllegalArgumentException("no input is provided"))
    }
    "not take empty space input 2" in {
      execute("  ") must throwA(new IllegalArgumentException("no input is provided"))
    }
    "not take zero input" in {
      execute("") must throwA(new IllegalArgumentException("no input is provided"))
    }
    "not take an unrecognised input" in {
      execute("a 33 55") must throwA(new IllegalArgumentException("not a supported command, please provide the right input"))
    }
    "not take a random input" in {
      execute("*** 22d s") must throwA(new IllegalArgumentException("not a supported command, please provide the right input"))
    }
    "not take a random input 2" in {
      execute("- 22d s") must throwA(new IllegalArgumentException("not a supported command, please provide the right input"))
    }

    "take canvas cmd" in {
      "standard input" in {
        execute("c 23 33") mustEqual CanvasCmd(23, 33)
      }
      "standard input plus a space" in {
        execute("c 23 33 ") mustEqual CanvasCmd(23, 33)
      }
      "with too many arguments" in {
        execute("c 23 33 77") must throwA(new IllegalArgumentException("c cmd has width and height that need to be numeric"))
      }
      "with a negative number" in {
        execute("c -23 77") must throwA(new IllegalArgumentException("c cmd has width and height that need to be numeric"))
      }
      "with a negative number 2" in {
        execute("c 23 -77") must throwA(new IllegalArgumentException("c cmd has width and height that need to be numeric"))
      }
      "with a negative number 3" in {
        execute("c -23 -77") must throwA(new IllegalArgumentException("c cmd has width and height that need to be numeric"))
      }
      "and the arguments are not numeric" in {
        execute("c sgd 33") must throwA(new IllegalArgumentException("c cmd has width and height that need to be numeric"))
      }
      "and the arguments are not special character" in {
        execute("c ^^ 33") must throwA(new IllegalArgumentException("c cmd has width and height that need to be numeric"))
      }
      "zero space input" in {
        execute("c ") must throwA(new IllegalArgumentException("c cmd has width and height that need to be numeric"))
      }
      "zero input" in {
        execute("c") must throwA(new IllegalArgumentException("c cmd has width and height that need to be numeric"))
      }
    }

    "take line cmd" in {
      "standard input" in {
        execute("l 23 33 44 77") mustEqual LineCmd(23, 33, 44, 77)
      }
      "standard input plus a space" in {
        execute("l 23 33 44 77 ") mustEqual LineCmd(23, 33, 44, 77)
      }
      "with a negative number" in {
        execute("l 23 -33 44 77") must throwA(new IllegalArgumentException("l cmd has 4 args that need to be numeric"))
      }
      "with a negative number 2" in {
        execute("l 23 33 44 -77") must throwA(new IllegalArgumentException("l cmd has 4 args that need to be numeric"))
      }
      "with too many arguments" in {
        execute("l 23 33 77 55 88") must throwA(new IllegalArgumentException("l cmd has 4 args that need to be numeric"))
      }
      "with not enough no of arguments" in {
        execute("l 23 33 77") must throwA(new IllegalArgumentException("l cmd has 4 args that need to be numeric"))
      }
      "and the arguments are not numeric" in {
        execute("l sgd 33 44 66") must throwA(new IllegalArgumentException("l cmd has 4 args that need to be numeric"))
      }
      "and the arguments are not special character" in {
        execute("l ^^ 33 44 66") must throwA(new IllegalArgumentException("l cmd has 4 args that need to be numeric"))
      }
      "zero space input" in {
        execute("l ") must throwA(new IllegalArgumentException("l cmd has 4 args that need to be numeric"))
      }
      "zero input" in {
        execute("l") must throwA(new IllegalArgumentException("l cmd has 4 args that need to be numeric"))
      }
    }

    "take rectangle cmd" in {
      "standard input" in {
        execute("r 23 33 44 77") mustEqual RectangleCmd(23, 33, 44, 77)
      }
      "standard input plus a space" in {
        execute("r 23 33 44 77 ") mustEqual RectangleCmd(23, 33, 44, 77)
      }
      "with a negative number" in {
        execute("r 23 -33 44 77") must throwA(new IllegalArgumentException("r cmd has 4 args that need to be numeric"))
      }
      "with a negative number 2" in {
        execute("r 23 33 44 -77") must throwA(new IllegalArgumentException("r cmd has 4 args that need to be numeric"))
      }
      "with too many arguments" in {
        execute("r 23 33 77 55 88") must throwA(new IllegalArgumentException("r cmd has 4 args that need to be numeric"))
      }
      "with not enough no of arguments" in {
        execute("r 23 33 77") must throwA(new IllegalArgumentException("r cmd has 4 args that need to be numeric"))
      }
      "and the arguments are not numeric" in {
        execute("r sgd 33 44 66") must throwA(new IllegalArgumentException("r cmd has 4 args that need to be numeric"))
      }
      "and the arguments are not special character" in {
        execute("r ^^ 33 44 66") must throwA(new IllegalArgumentException("r cmd has 4 args that need to be numeric"))
      }
      "zero space input" in {
        execute("r ") must throwA(new IllegalArgumentException("r cmd has 4 args that need to be numeric"))
      }
      "zero input" in {
        execute("r") must throwA(new IllegalArgumentException("r cmd has 4 args that need to be numeric"))
      }
    }

    "take bucket cmd" in {
      "standard input" in {
        execute("b 23 33 o") mustEqual BucketFillCmd(23, 33, "o")
      }
      "standard input plus a space" in {
        execute("b 23 33 o ") mustEqual BucketFillCmd(23, 33, "o")
      }
      "with too many arguments" in {
        execute("b 23 33 i o") must throwA(new IllegalArgumentException("b cmd has 3 args, the first two need to be numeric"))
      }
      "with a negative number" in {
        execute("b -23 77 i") must throwA(new IllegalArgumentException("b cmd has 3 args, the first two need to be numeric"))
      }
      "with a negative number 2" in {
        execute("b 23 -77 i") must throwA(new IllegalArgumentException("b cmd has 3 args, the first two need to be numeric"))
      }
      "with a negative number 3" in {
        execute("b -23 -77 i") must throwA(new IllegalArgumentException("b cmd has 3 args, the first two need to be numeric"))
      }
      "and the arguments are not numeric" in {
        execute("b sgd 33 u") must throwA(new IllegalArgumentException("b cmd has 3 args, the first two need to be numeric"))
      }
      "and the arguments are not special character" in {
        execute("b ^^ 33 u") must throwA(new IllegalArgumentException("b cmd has 3 args, the first two need to be numeric"))
      }
      "zero space input" in {
        execute("b ") must throwA(new IllegalArgumentException("b cmd has 3 args, the first two need to be numeric"))
      }
      "zero input" in {
        execute("b") must throwA(new IllegalArgumentException("b cmd has 3 args, the first two need to be numeric"))
      }
    }

    "take undo cmd" in {
      "standard input" in {
        execute("undo") mustEqual UndoCmd
      }
      "standard shortened input" in {
        execute("u") mustEqual UndoCmd
      }
    }

    "take q cmd" in {
      "standard input" in {
        execute("q") mustEqual Quit
      }
      "standard input plus a space" in {
        execute("q ") mustEqual Quit
      }
      "with extra numeric argument" in {
        execute("q 23") must throwA(new IllegalArgumentException("q cmd has no args, please just enter q"))
      }
      "with extra negative numeric argument" in {
        execute("q -23") must throwA(new IllegalArgumentException("q cmd has no args, please just enter q"))
      }
      "with extra arguments" in {
        execute("q sgd 33 u") must throwA(new IllegalArgumentException("q cmd has no args, please just enter q"))
      }
      "with extra special character argument" in {
        execute("q ^^") must throwA(new IllegalArgumentException("q cmd has no args, please just enter q"))
      }
    }

    "take help cmd" in {
      "standard input" in {
        execute("help") mustEqual Help
      }
      "standard input 2" in {
        execute("h") mustEqual Help
      }
      "standard input plus a space" in {
        execute("h ") mustEqual Help
      }
      "with extra numeric argument" in {
        execute("h 23") mustEqual Help
      }
      "with extra negative numeric argument" in {
        execute("h -23") mustEqual Help
      }
      "with extra arguments" in {
        execute("h sgd 33 u") mustEqual Help
      }
      "with extra special character argument" in {
        execute("h ^^") mustEqual Help
      }
    }

  }

}
