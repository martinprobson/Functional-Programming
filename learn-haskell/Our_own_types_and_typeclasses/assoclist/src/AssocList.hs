module AssocList 
  ( AssocList(Empty, AssocList)
  , fromList
  , empty
  , insert
  , insert'
  , replace'
  , AssocList.lookup
  ) where

data AssocList k v = Empty | AssocList [(k,v)] deriving (Show, Eq)


fromList :: [(a,b)] -> AssocList a b
fromList [] = Empty
fromList x = AssocList x

empty :: AssocList a b -> Bool
empty Empty = True
empty _ = False

insert :: a -> b -> AssocList a b -> AssocList a b
insert k v Empty = fromList [(k,v)]
insert k v (AssocList l)  = fromList $ (k,v) : l

insert' :: (Eq a) => a -> b -> AssocList a b -> AssocList a b
insert' k v Empty = fromList [(k,v)]
insert' k v (AssocList l)  = case  AssocList.lookup k (AssocList l) of
                              Nothing -> fromList $ (k,v) : l
                              Just _ -> fromList $ (k,v) : l


replace' :: (Eq a) => a -> b -> AssocList a b -> AssocList a b
replace' a b Empty = fromList [(a,b)]
replace' a b al@(AssocList ((k,v): xs))  = if (a == k) then
                                            fromList $ (k,b) : xs
                                           else
                                            replace' a b $ fromList xs

lookup :: (Eq a) => a -> AssocList a b -> Maybe b
lookup _ Empty = Nothing
lookup a (AssocList ((k,v): xs)) = if (a == k) then
                                      Just v
                                   else 
                                      AssocList.lookup a (fromList xs)
                                      

