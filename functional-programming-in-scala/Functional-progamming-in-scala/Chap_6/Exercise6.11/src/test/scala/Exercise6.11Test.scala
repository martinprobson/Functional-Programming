import org.scalatest.FunSuite
import Machine._

class Exercise611Test extends FunSuite {
  
  val l: List[Input] = List(Coin,Turn,Coin,Turn,Coin,Turn,Coin,Turn,Coin,Turn,Coin,Turn)

  test("1: simulateMachine: 5 candies/ 10 coins - buy candy until machine is empty.") {
    val res = simulateMachine(l).run(Machine(true,5,10))
    assert(res === ((0,15),Machine(true,0,15)))
  }

  test("2: simulateMachine: Machine is empty at start") {
    val res = simulateMachine(l).run(Machine(true,0,10))
    assert(res === ((0,10),Machine(true,0,10)))
  }

  test("3: simulateMachine: Turning the handle on a locked machine does nothing") {
    val inp = List(Turn,Turn,Turn)
    val res = simulateMachine(inp).run(Machine(true,10,10))
    assert(res === ((10,10),Machine(true,10,10)))
  }

  test("4: simulateMachine: Insert a coin into unlocked machine does nothing") {
    val inp = List(Coin,Coin,Coin)
    val res = simulateMachine(inp).run(Machine(false,10,10))
    assert(res === ((10,10),Machine(false,10,10)))
  }

  test("5: simulateMachine: Insert a coin into locked machine, unlocks it") {
    val inp = List(Coin)
    val res = simulateMachine(inp).run(Machine(true,10,10))
    assert(res === ((10,11),Machine(false,10,11)))
  }

  test("6: simulateMachine: turn handle on unlocked machine, gives candy, locks machine") {
    val inp = List(Turn)
    val res = simulateMachine(inp).run(Machine(false,1,10))
    assert(res === ((0,10),Machine(true,0,10)))
  }
}

