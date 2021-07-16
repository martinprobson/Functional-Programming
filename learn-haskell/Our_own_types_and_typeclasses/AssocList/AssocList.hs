module AssocList 
  ( AssocList(Empty,AssocList)
  , fromList
  ) where

data AssocList k v = Empty | AssocList [(k,v)] deriving (Show)

fromList :: [(a,b)] -> AssocList a b
fromList [] = Empty
fromList x = AssocList x
