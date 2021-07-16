data Term = Con Int | Div Term Term deriving (Show)

eval :: Term -> Int
eval (Con a) = a
eval (Div t u) = (eval t) `div` (eval u)

answer, answer', err :: Term
answer = (Div(Div(Con 1972) (Con 2)) (Con 23))
err = (Div(Con 1)(Con 0))
answer' = Div (Div answer answer) (Div answer answer)

--
-- This is our 'Try' class
--
data M a = Raise Exception | Return a deriving (Show)
type Exception = String 

eval' :: Term -> M Int
eval' (Con a) = Return a
eval' (Div t u) = case eval' t of
                    Raise e -> Raise e
                    Return a  -> 
                            case eval' u of
                                Raise e -> Raise e
                                Return b ->
                                        if b == 0
                                        then Raise "divide by zero"
                                        else Return (a `div` b)

--
-- This is our State type
--
type M' a = State -> (a,State) 
type State = Int

eval'' :: Term -> M' Int
eval'' (Con a) x = (a,x)
eval'' (Div t u) x = let (a,y) = eval'' t x in
                     let (b,z) = eval'' u y in
                     ( a `div` b, z + 1)

--
-- This is our 'output' type
--
type M'' a = (Output,a)
type Output = String

eval''' :: Term -> M'' Int
eval''' (Con a) = (line (Con a) a, a)
eval''' (Div t u) = let (x,a) = eval''' t in
                    let (y,b) = eval''' u in
                        (x ++ y ++ line (Div t u) (a `div` b), a `div` b)

line :: Term -> Int -> Output
line t a = "eval (" ++ (show t) ++ ") " ++ (show a) ++ "\n"

class MyMonad m where
        unit :: a -> m a
        flatMap' :: m a -> (a -> m b) -> m b

instance MyMonad M  where
--        unit :: a -> id a 
        unit a = Return a
        flatMap' (Return a) f = (f a)
        flatMap' (Raise a) _ = (Raise a)
--        flatMap' :: id a -> (a -> id b) -> id b
--        flatMap' a f = f a
--data M a = Raise Exception | Return a deriving (Show)


--unit :: a -> M a
--class MyMyMonad a where
--         unit ::
--         fmap' :: (a -> b) -> f a -> f b
--
--class Functor' f where
--         fmap' :: (a -> b) -> f a -> f b
--
--IO
--
main = do
        print answer
        putStrLn (fst (eval''' answer))
