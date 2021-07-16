--
-- Define and instance of Functor for the following type
--
data Tree a = Node a [Tree a] deriving (Show)

instance Functor Tree where
  fmap f (Node x ts) = Node (f x) (fmap (fmap f) ts) 

--
-- Build a new (single node tree)
new :: a -> Tree a
new x = Node x []

ins :: a -> Tree a -> Tree a
ins x (Node a []) = Node a [Node x []]
ins x (Node a [t] ) = Node a [ins x t]

--
-- Build a sample Tree
--
buildTree:: (Num a, Enum a) => Tree a
buildTree = foldl (flip ins) (new 0) (take 100 [1,2..])

--
-- main
--

main = do
    print  buildTree 
    print $ fmap (*1000)  buildTree
