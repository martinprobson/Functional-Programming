import Test.Tasty
import Test.Tasty.HUnit

import AssocList 

type AssocListIntInt = AssocList Int Int

main :: IO ()
main = defaultMain unitTests 

unitTests :: TestTree
unitTests = testGroup "fromList tests" $  fromListTests

fromListTests :: [TestTree]
fromListTests = 
  [ testCase "fromList 1" $ assertBool
                            "fromList [('fred','fred2')] == fromList [('fred','fred2')]" 
                            (fromList [("fred","fred2")] == fromList [("fred","fred2")] ),
    testCase "fromList 2" $ assertBool
                            "fromList [] == fromList []"
                            ((fromList []::AssocList String String) == fromList []),
    testCase "fromList 3" $ assertBool
                            "fromList [(1,2),(3,4),(10,11)] == fromList [(1,2),(3,4),(10,11)]"
                            ((fromList [(1,2),(3,4),(10,11)]::AssocListIntInt) == fromList [(1,2),(3,4),(10,11)]),
    testCase "fromList 4" $ assertBool
                            "fromList [(1,2),(3,4),(10,11)] /= fromList [(1,2),(3,4),(10,11)]"
                            ((fromList [(1,2),(3,4),(10,12)]::AssocListIntInt) /= fromList [(1,2),(3,4),(10,11)]),
    testCase "fromList 5" $ assertBool
                            "fromList [] == Empty"
                            ((fromList []::AssocListIntInt) == Empty),
    testCase "fromList 6" $ assertBool
                            ""
                            (fromList (take 1000 $ zip [1,2..] [1,2..]) == fromList (take 1000 $ zip [1,2..] [1,2..])),
    testCase "empty on empty AssocList" $ assertBool
                                         "expected True"
                                         ((empty (fromList [])) == True),
    testCase "empty on non-empty AssocList" $ assertBool
                                         "expected False"
                                         ((empty (fromList [(1,2)])) == False),
    testCase "insert into empty AssocList" $ assertBool
                                         "expecting fromList [(1,2)]"
                                         ((insert 1 2 (fromList [])) == fromList [(1,2)]),
    testCase "insert into non-empty AssocList" $ assertBool
                                         "expecting fromList [(1,2),(3,4)]"
                                         ((insert 3 4 (fromList [(1,2)])) == fromList [(3,4),(1,2)]),
    testCase "insert' into empty AssocList" $ assertBool
                                         "expecting fromList [(1,2)]"
                                         ((insert' 1 2 (fromList [])) == fromList [(1,2)]),
    testCase "insert' into non-empty AssocList" $ assertBool
                                         "expecting fromList [(1,2),(3,4)]"
                                         ((insert' 3 4 (fromList [(1,2)])) == fromList [(3,4),(1,2)])
       
  ]
