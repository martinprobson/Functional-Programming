
import Test.HUnit
test1 = TestCase (assertEqual "test" (fromList []) (fromList []))
test2 = TestCase (assertEqual "test2" (fromList [(1,2)]) (fromList [(1,2)]))
test3 = TestCase (assertEqual "test3" (fromList [(1,3)]) (fromList [(1,2)]))
tests = TestList [TestLabel "test1" test1,TestLabel "test2" test2, TestLabel
runTestTT tests
--Cases: 3  Tried: 3  Errors: 0  Failures: 0
--Counts {cases = 3, tried = 3, errors = 0, failures = 0}
